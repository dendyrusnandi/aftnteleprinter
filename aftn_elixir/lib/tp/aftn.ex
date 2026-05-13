defmodule Tp.Aftn do
  import Ecto.Query

  alias Tp.Aftn.{Builder, Event, Legacy, Message, Parser}
  alias Tp.Repo
  alias Tp.Udp.Sender

  def list_messages(opts \\ []) do
    cond do
      all_directions?(opts) -> combined_messages(opts, Keyword.get(opts, :limit, 100))
      legacy_read?(opts) -> Legacy.list_messages(opts)
      true -> list_new_messages(opts)
    end
  end

  def latest_messages(opts \\ []) do
    cond do
      all_directions?(opts) -> combined_messages(opts, Keyword.get(opts, :limit, Keyword.get(opts, :page_size, 15)))
      legacy_read?(opts) -> Legacy.latest_messages(opts)
      true -> list_new_messages(opts)
    end
  end

  def queue_messages(opts \\ []) do
    limit = opts |> Keyword.get(:limit, 200) |> parse_int(200) |> clamp(1, 500)
    q = opts |> Keyword.get(:q, "") |> to_string() |> String.downcase()

    Message
    |> where([m], m.direction == :outbound)
    |> where([m], m.status in ["queued", "retrying"])
    |> maybe_queue_search(q)
    |> order_by([m], asc: m.next_attempt_at, asc: m.inserted_at)
    |> limit(^limit)
    |> Repo.all()
  end

  def queue_count do
    Message
    |> where([m], m.direction == :outbound)
    |> where([m], m.status in ["queued", "retrying"])
    |> Repo.aggregate(:count, :id)
  end

  def delete_queue_messages(opts \\ []) do
    q = opts |> Keyword.get(:q, "") |> to_string() |> String.downcase()

    {count, _} =
      Message
      |> where([m], m.direction == :outbound)
      |> where([m], m.status in ["queued", "retrying"])
      |> maybe_queue_search(q)
      |> Repo.delete_all()

    {:ok, count}
  end

  defp list_new_messages(opts) do
    limit = opts |> Keyword.get(:limit, 100) |> parse_int(100) |> clamp(1, 1000)

    opts
    |> base_message_query()
    |> order_by([m], desc: m.inserted_at, desc: m.id)
    |> limit(^limit)
    |> Repo.all()
  end

  def list_messages_page(opts \\ []) do
    cond do
      all_directions?(opts) -> combined_messages_page(opts)
      legacy_read?(opts) ->
        {messages, pagination} = Legacy.list_messages_page(opts)
        {:ok, messages, pagination}

      true ->
        list_new_messages_page(opts)
    end
  end

  defp list_new_messages_page(opts) do
    page_size = opts |> Keyword.get(:page_size, 15) |> parse_int(15) |> clamp(1, 1000)
    page = opts |> Keyword.get(:page, 1) |> parse_int(1) |> max(1)
    cursor = cursor_from_opts(opts)

    rows =
      opts
      |> base_message_query()
      |> maybe_after_cursor(cursor)
      |> order_by([m], desc: m.inserted_at, desc: m.id)
      |> limit(^(page_size + 1))
      |> Repo.all()

    messages = Enum.take(rows, page_size)
    has_next = length(rows) > page_size

    {:ok,
     messages,
     %{
       page: page,
       page_size: page_size,
       has_next: has_next,
       next_cursor: cursor_from_message(List.last(messages))
     }}
  end

  defp combined_messages(opts, limit_value) do
    limit = limit_value |> parse_int(100) |> clamp(1, 1000)
    fetch_limit = limit * 2

    opts
    |> combined_pool(fetch_limit)
    |> Enum.take(limit)
  end

  defp combined_messages_page(opts) do
    page_size = opts |> Keyword.get(:page_size, 15) |> parse_int(15) |> clamp(1, 1000)
    page = opts |> Keyword.get(:page, 1) |> parse_int(1) |> max(1)
    offset = (page - 1) * page_size
    fetch_limit = offset + page_size

    messages =
      opts
      |> combined_pool(fetch_limit)
      |> Enum.drop(offset)
      |> Enum.take(page_size)

    total_count = legacy_count(opts) + new_count(Keyword.put(opts, :direction, "outbound"))
    total_pages = total_count |> div_ceil(page_size) |> max(1)

    {:ok,
     messages,
     %{
       page: min(page, total_pages),
       page_size: page_size,
       has_next: page < total_pages,
       total_count: total_count,
       total_pages: total_pages
     }}
  end

  defp combined_pool(opts, limit) do
    legacy_opts = Keyword.merge(opts, direction: "inbound", limit: limit, page_size: limit, page: 1)
    new_opts = Keyword.merge(opts, direction: "outbound", limit: limit)

    (Legacy.latest_messages(legacy_opts) ++ list_new_messages(new_opts))
    |> sort_messages_desc()
  end

  def get_message(id) do
    Legacy.get_message(id) || Repo.get(Message, id)
  end

  def delete_message(id) when is_integer(id) do
    case Legacy.delete_message(id) do
      {:ok, deleted} ->
        {:ok, deleted}

      {:error, :not_found} ->
        case Repo.get(Message, id) do
          nil -> {:error, :not_found}
          message -> Repo.delete(message)
        end
    end
  end

  def delete_all_messages(opts \\ []) do
    cond do
      all_directions?(opts) ->
        {:ok, legacy_deleted} = Legacy.delete_all(Keyword.put(opts, :direction, "inbound"))
        {new_deleted, _} = opts |> Keyword.put(:direction, "outbound") |> base_message_query() |> Repo.delete_all()
        {:ok, legacy_deleted + new_deleted}

      legacy_read?(opts) ->
        Legacy.delete_all(opts)

      true ->
        {count, _} =
          opts
          |> base_message_query()
          |> Repo.delete_all()

        {:ok, count}
    end
  end

  defp legacy_count(opts) do
    {_messages, pagination} = Legacy.list_messages_page(Keyword.merge(opts, direction: "inbound", page_size: 1, page: 1))
    Map.get(pagination, :total_count, 0)
  end

  defp new_count(opts) do
    opts
    |> base_message_query()
    |> Repo.aggregate(:count, :id)
  end

  defp sort_messages_desc(messages) do
    Enum.sort_by(messages, fn message -> {message_sort_time(message), message.id || 0} end, :desc)
  end

  defp message_sort_time(%{inserted_at: value}), do: sort_time(value)
  defp message_sort_time(%{received_at: value}), do: sort_time(value)
  defp message_sort_time(_message), do: 0

  defp sort_time(%DateTime{} = value), do: DateTime.to_unix(value, :microsecond)

  defp sort_time(%NaiveDateTime{} = value) do
    value
    |> DateTime.from_naive!("Etc/UTC")
    |> DateTime.to_unix(:microsecond)
  end

  defp sort_time(_value), do: 0

  def list_events(limit \\ 20) do
    Event
    |> order_by([e], desc: e.inserted_at)
    |> limit(^limit)
    |> Repo.all()
  end

  def ingest_udp(raw, meta \\ %{}) do
    if Parser.aftn?(raw) do
      persist_aftn_udp(raw, meta)
    else
      {:ok, :monitor_only}
    end
  end

  defp persist_aftn_udp(raw, meta) do
    case Parser.classify(raw) do
      {:event, _attrs} ->
        {:ok, :monitor_only}

      {:message, attrs} ->
        parsed_fields = Map.merge(attrs, meta)

        attrs
        |> Map.merge(%{direction: :inbound, parsed_fields: parsed_fields})
        |> create_inbound_message()
    end
  end

  def transmit(raw, attrs \\ %{}) do
    with {:ok, sent} <- Sender.send(raw, attrs_to_target_opts(attrs)),
         {:ok, message} <-
           raw
           |> Parser.parse_message()
           |> Map.merge(attrs)
           |> Map.merge(%{direction: :outbound, raw_text: raw, status: "sent", sent_at: DateTime.utc_now()})
           |> create_message() do
      {:ok, %{udp: sent, message: message}}
    end
  end

  def enqueue_transmit(raw, attrs \\ %{}) do
    raw
    |> Parser.parse_message()
    |> Map.merge(attrs)
    |> Map.merge(%{
      direction: :outbound,
      raw_text: raw,
      status: "queued",
      transmit_attempts: 0,
      last_error: nil,
      next_attempt_at: Map.get(attrs, :next_attempt_at) || DateTime.utc_now(),
      udp_target_host: Map.get(attrs, :udp_target_host),
      udp_target_port: Map.get(attrs, :udp_target_port)
    })
    |> create_message()
  end

  def compose_and_enqueue(type, params, attrs \\ %{}) do
    type = String.upcase(type)

    case Builder.validate(type, params) do
      [] ->
        type
        |> Builder.build(params)
        |> enqueue_transmit(attrs)

      errors ->
        {:error, {:validation, errors}}
    end
  end

  def compose_and_save(type, params, attrs \\ %{}) do
    type = String.upcase(type)

    case Builder.validate(type, params) do
      [] ->
        raw = Builder.build(type, params)

        raw
        |> Parser.parse_message()
        |> Map.merge(attrs)
        |> Map.merge(%{
          direction: :outbound,
          raw_text: raw,
          status: "saved",
          transmit_attempts: 0,
          last_error: nil,
          next_attempt_at: nil,
          udp_target_host: nil,
          udp_target_port: nil
        })
        |> create_message()

      errors ->
        {:error, {:validation, errors}}
    end
  end

  def due_outgoing(limit \\ 20) do
    now = DateTime.utc_now()

    Message
    |> where([m], m.direction == :outbound)
    |> where([m], m.status in ["queued", "retrying"])
    |> where([m], is_nil(m.next_attempt_at) or m.next_attempt_at <= ^now)
    |> order_by([m], asc: m.next_attempt_at, asc: m.inserted_at)
    |> limit(^limit)
    |> Repo.all()
  end

  def send_queued(%Message{} = message) do
    link = Tp.LinkMonitor.status()

    if link.state == :up do
      do_send_queued(message)
    else
      update_message(message, %{
        status: "retrying",
        last_error: "destination not established: #{link.reason}",
        next_attempt_at: DateTime.add(DateTime.utc_now(), 10, :second)
      })
    end
  end

  defp do_send_queued(%Message{} = message) do
    attempts = (message.transmit_attempts || 0) + 1

    case Sender.send(message.raw_text, target_opts(message)) do
      {:ok, _sent} ->
        update_message(message, %{
          status: "sent",
          sent_at: DateTime.utc_now(),
          transmit_attempts: attempts,
          last_error: nil,
          next_attempt_at: nil
        })

      {:error, reason} ->
        update_message(message, %{
          status: retry_status(attempts),
          transmit_attempts: attempts,
          last_error: inspect(reason),
          next_attempt_at: next_retry_at(attempts)
        })
    end
  end

  def create_message(attrs), do: %Message{} |> Message.changeset(attrs) |> Repo.insert()
  def create_inbound_message(attrs), do: Legacy.insert_inbound(attrs)
  def create_event(attrs), do: %Event{} |> Event.changeset(attrs) |> Repo.insert()
  def update_message(message, attrs), do: message |> Message.changeset(attrs) |> Repo.update()

  defp target_opts(message) do
    []
    |> maybe_put(:host, message.udp_target_host)
    |> maybe_put(:port, message.udp_target_port)
  end

  defp attrs_to_target_opts(attrs) do
    []
    |> maybe_put(:host, Map.get(attrs, :udp_target_host))
    |> maybe_put(:port, Map.get(attrs, :udp_target_port))
  end

  defp maybe_put(opts, _key, nil), do: opts
  defp maybe_put(opts, _key, ""), do: opts
  defp maybe_put(opts, key, value), do: Keyword.put(opts, key, value)

  defp retry_status(attempts) when attempts >= 5, do: "failed"
  defp retry_status(_attempts), do: "retrying"

  defp next_retry_at(attempts) when attempts >= 5, do: nil

  defp next_retry_at(attempts) do
    seconds = min(:math.pow(2, attempts) |> round(), 60)
    DateTime.add(DateTime.utc_now(), seconds, :second)
  end

  defp base_message_query(opts) do
    direction = Keyword.get(opts, :direction)
    type = Keyword.get(opts, :type)
    q = Keyword.get(opts, :q)
    cid = Keyword.get(opts, :cid)
    seq_from = Keyword.get(opts, :seq_from)
    seq_to = Keyword.get(opts, :seq_to)
    text = Keyword.get(opts, :text)
    date_from = Keyword.get(opts, :date_from)
    date_to = Keyword.get(opts, :date_to)
    filed_by = Keyword.get(opts, :filed_by)

    Message
    |> maybe_direction(direction)
    |> maybe_type(type)
    |> maybe_search(q)
    |> maybe_cid(cid)
    |> maybe_sequence_range(seq_from, seq_to)
    |> maybe_text(text)
    |> maybe_date(date_from, date_to)
    |> maybe_filed_by(filed_by)
  end

  defp legacy_read?(opts) do
    Keyword.get(opts, :direction) in [nil, "", "inbound"]
  end

  defp all_directions?(opts) do
    Keyword.get(opts, :direction) in [nil, ""]
  end

  defp maybe_direction(query, nil), do: query
  defp maybe_direction(query, ""), do: query
  defp maybe_direction(query, "inbound"), do: where(query, [m], m.direction == :inbound)
  defp maybe_direction(query, "outbound"), do: where(query, [m], m.direction == :outbound)
  defp maybe_direction(query, direction), do: where(query, [m], m.direction == ^direction)

  defp maybe_type(query, nil), do: query
  defp maybe_type(query, ""), do: query
  defp maybe_type(query, type), do: where(query, [m], m.message_type == ^String.upcase(type))


  defp maybe_cid(query, nil), do: query
  defp maybe_cid(query, ""), do: query

  defp maybe_cid(query, cid) do
    term = "%#{String.downcase(to_string(cid))}%"
    where(query, [m], like(fragment("LOWER(?)", m.cid), ^term))
  end

  defp maybe_sequence_range(query, seq_from, seq_to) when seq_from in [nil, ""] and seq_to in [nil, ""], do: query

  defp maybe_sequence_range(query, seq_from, seq_to) do
    from_seq = parse_int(seq_from, nil) || parse_int(seq_to, nil)
    to_seq = parse_int(seq_to, nil) || parse_int(seq_from, nil)

    if from_seq && to_seq do
      {from_seq, to_seq} = if from_seq > to_seq, do: {to_seq, from_seq}, else: {from_seq, to_seq}
      where(query, [m], fragment("CAST(? AS UNSIGNED)", m.sequence_no) >= ^from_seq and fragment("CAST(? AS UNSIGNED)", m.sequence_no) <= ^to_seq)
    else
      query
    end
  end

  defp maybe_text(query, nil), do: query
  defp maybe_text(query, ""), do: query

  defp maybe_text(query, text) do
    term = "%#{String.downcase(to_string(text))}%"
    where(query, [m], like(fragment("LOWER(?)", m.raw_text), ^term))
  end

  defp maybe_filed_by(query, nil), do: query
  defp maybe_filed_by(query, ""), do: query

  defp maybe_filed_by(query, filed_by) do
    term = "%#{String.downcase(to_string(filed_by))}%"
    where(query, [m], like(fragment("LOWER(?)", m.filed_by), ^term))
  end

  defp maybe_date(query, date_from, date_to) when date_from in [nil, ""] and date_to in [nil, ""], do: query

  defp maybe_date(query, date_from, date_to) do
    from_date = parse_date(date_from) || parse_date(date_to)
    to_date = parse_date(date_to) || parse_date(date_from)

    if from_date && to_date do
      {from_date, to_date} =
        if Date.compare(from_date, to_date) == :gt, do: {to_date, from_date}, else: {from_date, to_date}

      from_time = from_date |> NaiveDateTime.new!(~T[00:00:00]) |> DateTime.from_naive!("Etc/UTC")
      to_time = to_date |> NaiveDateTime.new!(~T[23:59:59]) |> DateTime.from_naive!("Etc/UTC")

      where(query, [m], m.inserted_at >= ^from_time and m.inserted_at <= ^to_time)
    else
      query
    end
  end

  defp maybe_after_cursor(query, nil), do: query

  defp maybe_after_cursor(query, %{inserted_at: inserted_at, id: id}) do
    where(query, [m], m.inserted_at < ^inserted_at or (m.inserted_at == ^inserted_at and m.id < ^id))
  end

  defp cursor_from_opts(opts) do
    after_id = Keyword.get(opts, :after_id)
    after_ts = Keyword.get(opts, :after_ts)

    with id when is_integer(id) and id > 0 <- parse_int(after_id, nil),
         {:ok, inserted_at} <- parse_cursor_time(after_ts) do
      %{id: id, inserted_at: inserted_at}
    else
      _ -> nil
    end
  end

  defp cursor_from_message(nil), do: nil

  defp cursor_from_message(message) do
    %{
      id: message.id,
      inserted_at: cursor_time_to_string(message.inserted_at)
    }
  end

  defp parse_cursor_time(%DateTime{} = value), do: {:ok, value}
  defp parse_cursor_time(%NaiveDateTime{} = value), do: {:ok, DateTime.from_naive!(value, "Etc/UTC")}

  defp parse_cursor_time(value) when is_binary(value) do
    case DateTime.from_iso8601(value) do
      {:ok, datetime, _offset} -> {:ok, datetime}
      _ -> :error
    end
  end

  defp parse_cursor_time(_value), do: :error

  defp cursor_time_to_string(%DateTime{} = value), do: DateTime.to_iso8601(value)
  defp cursor_time_to_string(%NaiveDateTime{} = value), do: value |> DateTime.from_naive!("Etc/UTC") |> DateTime.to_iso8601()
  defp cursor_time_to_string(value), do: to_string(value)

  defp parse_int(value, _default) when is_integer(value), do: value

  defp parse_int(value, default) when is_binary(value) do
    case Integer.parse(value) do
      {number, _} -> number
      :error -> default
    end
  end

  defp parse_int(_value, default), do: default

  defp parse_date(value) when value in [nil, ""], do: nil

  defp parse_date(value) when is_binary(value) do
    case Date.from_iso8601(value) do
      {:ok, date} -> date
      _ -> nil
    end
  end

  defp parse_date(_value), do: nil

  defp clamp(number, min_value, max_value), do: number |> max(min_value) |> min(max_value)

  defp div_ceil(number, divisor), do: div(number + divisor - 1, divisor)

  defp maybe_queue_search(query, ""), do: query

  defp maybe_queue_search(query, q) do
    term = "%#{q}%"

    where(
      query,
      [m],
      like(fragment("LOWER(?)", m.aircraft_id), ^term) or
        like(fragment("LOWER(?)", m.originator), ^term) or
        like(fragment("LOWER(?)", m.raw_text), ^term)
    )
  end

  defp maybe_search(query, nil), do: query
  defp maybe_search(query, ""), do: query

  defp maybe_search(query, search) do
    term = "%#{String.downcase(search)}%"

    where(
      query,
      [m],
      like(fragment("LOWER(?)", m.raw_text), ^term) or
        like(fragment("LOWER(?)", m.originator), ^term) or
        like(fragment("LOWER(?)", m.aircraft_id), ^term) or
        like(fragment("LOWER(?)", m.departure), ^term) or
        like(fragment("LOWER(?)", m.destination), ^term) or
        like(fragment("LOWER(?)", m.sequence_no), ^term)
    )
  end
end
