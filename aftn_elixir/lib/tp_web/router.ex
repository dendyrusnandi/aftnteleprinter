defmodule TpWeb.Router do
  use Plug.Router

  alias Tp.{Aftn, Settings}

  plug Plug.Parsers, parsers: [:urlencoded, :json], json_decoder: Jason
  plug :match
  plug :dispatch

  get "/" do
    filters = filters(conn.params)

    case dashboard_data(filters) do
      {:ok, messages, events, settings, received_messages, pagination} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.dashboard(messages, events, filters, notice(conn.params), settings, received_messages, pagination))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/health" do
    send_resp(conn, 200, "ok")
  end

  get "/compose" do
    selected = Map.get(conn.params, "form", "AFTN_FREE")

    conn
    |> put_resp_content_type("text/html")
    |> send_resp(200, TpWeb.Views.compose_page(notice(conn.params), selected, conn.params))
  end

  get "/queue" do
    q = Map.get(conn.params, "q", "")

    case safe_queue_messages(q: q) do
      {:ok, messages} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.queue_page(messages, q, notice(conn.params)))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/settings" do
    conn
    |> put_resp_content_type("text/html")
    |> send_resp(200, TpWeb.Views.settings_page(Settings.get_or_default(), notice(conn.params)))
  end

  post "/settings" do
    case Settings.update(conn.params) do
      {:ok, _setting} ->
        redirect(conn, "/?info=#{URI.encode_www_form("Setting saved")}")

      {:error, changeset} ->
        case changeset do
          {:udp_port, reason} ->
            conn
            |> put_resp_content_type("text/html")
            |> send_resp(422, TpWeb.Views.settings_page(Settings.get_or_default(), {:error, "Local receive port gagal dibuka: #{inspect(reason)}"}))

          changeset ->
            conn
            |> put_resp_content_type("text/html")
            |> send_resp(422, TpWeb.Views.settings_page(changeset, {:error, "Setting belum valid"}))
        end
    end
  end

  get "/messages/:id" do
    case safe_message(id) do
      {:ok, nil} ->
        send_resp(conn, 404, "message not found")

      {:ok, message} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.message_detail(message))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/messages/:id/pdf" do
    case safe_message(id) do
      {:ok, nil} ->
        send_resp(conn, 404, "message not found")

      {:ok, message} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.message_pdf(message))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/api/messages" do
    opts = filters(conn.params) ++ [page_size: 100]

    case safe_message_page(opts) do
      {:ok, messages, pagination} ->
        payload = %{
          data: Enum.map(messages, &message_json/1),
          pagination: Map.take(pagination, [:page, :page_size, :has_next, :next_cursor])
        }

        conn
        |> put_resp_content_type("application/json")
        |> send_resp(200, Jason.encode!(payload))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(503, Jason.encode!(%{error: "database_not_migrated", run: "mix ecto.migrate"}))
    end
  end

  get "/api/messages/recent" do
    opts = filters(conn.params) ++ [limit: parse_int(Map.get(conn.params, "page_size"), 15)]

    case safe_latest_messages(opts) do
      {:ok, messages} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(200, Jason.encode!(%{data: Enum.map(messages, &message_json/1)}))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(503, Jason.encode!(%{error: "database_not_migrated", run: "mix ecto.migrate"}))
    end
  end

  get "/api/messages/:id" do
    case safe_message(id) do
      {:ok, nil} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(404, Jason.encode!(%{error: "message_not_found"}))

      {:ok, message} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(200, Jason.encode!(message_json(message)))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(503, Jason.encode!(%{error: "database_not_migrated", run: "mix ecto.migrate"}))
    end
  end

  get "/api/udp-monitor" do
    payload = %{data: Enum.map(Tp.Udp.Monitor.drain(), &udp_monitor_json/1)}

    conn
    |> put_resp_content_type("application/json")
    |> send_resp(200, Jason.encode!(payload))
  end

  get "/api/status" do
    case safe_queue_count() do
      {:ok, queue_count} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(200, Jason.encode!(%{queue_count: queue_count}))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(503, Jason.encode!(%{error: "database_not_migrated", run: "mix ecto.migrate"}))
    end
  end

  post "/messages/send" do
    text = Map.get(conn.params, "message", "") |> to_string()

    if String.trim(text) == "" do
      redirect(conn, "/?error=#{URI.encode_www_form("Raw message wajib diisi")}")
    else
      with {:ok, attrs} <- transmit_attrs(conn.params),
           {:ok, _message} <- Aftn.enqueue_transmit(text, attrs) do
        Tp.Aftn.OutgoingQueue.kick()
        redirect(conn, "/?direction=outbound")
      else
        {:error, {:validation, errors}} ->
          redirect(conn, "/?error=#{URI.encode_www_form(Enum.join(errors, ", "))}")

        {:error, reason} ->
          send_resp(conn, 422, inspect(reason))
      end
    end
  end

  post "/messages/compose" do
    type = Map.get(conn.params, "compose_type", "FREE")
    action = Map.get(conn.params, "compose_action", "send")

    with {:ok, attrs} <- transmit_attrs(conn.params),
         {:ok, _message} <- compose_submit(action, type, conn.params, attrs) do
      if action != "save", do: Tp.Aftn.OutgoingQueue.kick()
      redirect(conn, redirect_after_compose(conn.params, type, action))
    else
      {:error, {:validation, errors}} ->
        message = Enum.join(errors, ", ")
        redirect(conn, error_redirect(conn.params, type, message))

      {:error, reason} ->
        send_resp(conn, 422, inspect(reason))
    end
  end

  post "/messages/:id/delete" do
    return_to = return_path(Map.get(conn.params, "return_to", "/"))

    case safe_delete_message(id) do
      {:ok, _deleted} ->
        redirect(conn, with_notice(return_to, :info, "Message deleted"))

      {:error, :not_found} ->
        redirect(conn, with_notice(return_to, :error, "Message tidak ditemukan"))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())

      {:error, _reason} ->
        redirect(conn, with_notice(return_to, :error, "Delete gagal"))
    end
  end

  post "/messages/delete-all" do
    filters = filters(conn.params)

    case safe_delete_all_messages(filters) do
      {:ok, count} ->
        redirect(conn, with_notice(filters_to_path(filters), :info, "#{count} message deleted"))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/queue/delete-all" do
    q = Map.get(conn.params, "q", "")

    case safe_delete_queue_messages(q: q) do
      {:ok, count} ->
        path = if q in [nil, ""], do: "/queue", else: "/queue?#{URI.encode_query(%{q: q})}"
        redirect(conn, with_notice(path, :info, "#{count} queue message deleted"))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  match _ do
    send_resp(conn, 404, "not found")
  end

  defp redirect(conn, location) do
    conn
    |> put_resp_header("location", location)
    |> send_resp(303, "")
  end

  defp dashboard_data(filters) do
    with {:ok, messages, pagination} <- safe_message_page(filters),
         {:ok, settings} <- safe_settings(),
         monitor_items <- Tp.Udp.Monitor.drain() do
      {:ok, messages, [], settings, monitor_items, pagination}
    end
  end

  defp safe_messages(opts) do
    {:ok, Aftn.list_messages(opts)}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_latest_messages(opts) do
    {:ok, Aftn.latest_messages(opts)}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_queue_messages(opts) do
    {:ok, Aftn.queue_messages(opts)}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_queue_count do
    {:ok, Aftn.queue_count()}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_delete_message(id) do
    with {id, ""} <- Integer.parse(id) do
      Aftn.delete_message(id)
    else
      _ -> {:error, :not_found}
    end
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_delete_all_messages(opts) do
    Aftn.delete_all_messages(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_delete_queue_messages(opts) do
    Aftn.delete_queue_messages(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_message_page(opts) do
    {:ok, messages, pagination} = Aftn.list_messages_page(opts)
    {:ok, messages, build_pagination_urls(opts, pagination)}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_message(id) do
    {:ok, Aftn.get_message(String.to_integer(id))}
  rescue
    ArgumentError -> {:ok, nil}
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
  end

  defp safe_settings do
    {:ok, Settings.get_or_default()}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:ok, Settings.default_setting()}, else: reraise(error, __STACKTRACE__)
  end

  defp filters(params) do
    [
      direction: Map.get(params, "direction", ""),
      type: Map.get(params, "type", ""),
      q: Map.get(params, "q", ""),
      date_from: Map.get(params, "date_from", ""),
      date_to: Map.get(params, "date_to", ""),
      page: Map.get(params, "page", "1"),
      page_size: Map.get(params, "page_size", "15"),
      after_id: Map.get(params, "after_id", ""),
      after_ts: Map.get(params, "after_ts", ""),
      history: Map.get(params, "history", "")
    ]
  end

  defp build_pagination_urls(opts, pagination) do
    page = Map.get(pagination, :page, parse_int(Keyword.get(opts, :page), 1))
    page_size = Map.get(pagination, :page_size, parse_int(Keyword.get(opts, :page_size), 15))
    total_pages = Map.get(pagination, :total_pages)

    if total_pages do
      pagination
      |> Map.put(:page, page)
      |> Map.put(:page_size, page_size)
      |> Map.put(:prev_url, numbered_page_url(opts, page - 1, page_size, total_pages))
      |> Map.put(:next_url, numbered_page_url(opts, page + 1, page_size, total_pages))
      |> Map.put(:page_urls, page_urls(opts, page_size, total_pages))
    else
      build_cursor_pagination_urls(opts, pagination, page, page_size)
    end
  end

  defp build_cursor_pagination_urls(opts, pagination, page, page_size) do
    current_cursor = cursor_from_opts(opts)
    history = decode_history(Keyword.get(opts, :history))
    next_cursor = Map.get(pagination, :next_cursor)

    prev_state = previous_state(history)
    next_history = encode_history(history ++ [current_cursor || %{"root" => true}])

    pagination
    |> Map.put(:page, page)
    |> Map.put(:page_size, page_size)
    |> Map.put(:prev_url, page_url(opts, page - 1, page_size, prev_state.cursor, prev_state.history))
    |> Map.put(:next_url, page_url(opts, page + 1, page_size, next_cursor, next_history))
  end

  defp numbered_page_url(_opts, page, _page_size, _total_pages) when page < 1, do: nil
  defp numbered_page_url(_opts, page, _page_size, total_pages) when page > total_pages, do: nil
  defp numbered_page_url(opts, page, page_size, _total_pages), do: page_url(opts, page, page_size, nil, "")

  defp page_urls(opts, page_size, total_pages) do
    for page <- 1..total_pages, do: {page, page_url(opts, page, page_size, nil, "")}
  end

  defp previous_state([]), do: %{cursor: nil, history: ""}

  defp previous_state(history) do
    {prev_cursor, prev_history} = List.pop_at(history, -1)
    %{cursor: normalize_history_cursor(prev_cursor), history: encode_history(prev_history)}
  end

  defp normalize_history_cursor(%{"root" => true}), do: nil
  defp normalize_history_cursor(cursor), do: cursor

  defp page_url(_opts, page, _page_size, _cursor, _history) when page < 1, do: nil
  defp page_url(_opts, _page, _page_size, nil, _history) when false, do: nil

  defp page_url(opts, page, page_size, cursor, history) do
    base =
      opts
      |> Keyword.take([:direction, :type, :q, :date_from, :date_to])
      |> Keyword.put(:page, page)
      |> Keyword.put(:page_size, page_size)
      |> Enum.reject(fn {_key, value} -> is_nil(value) or value == "" end)

    cursor_params =
      case cursor do
        nil -> []
        %{"id" => id, "inserted_at" => inserted_at} -> [after_id: id, after_ts: inserted_at]
        %{id: id, inserted_at: inserted_at} -> [after_id: id, after_ts: inserted_at]
        _ -> []
      end

    history_params = if history in [nil, ""], do: [], else: [history: history]

    "/?" <> URI.encode_query(base ++ cursor_params ++ history_params)
  end

  defp cursor_from_opts(opts) do
    after_id = Keyword.get(opts, :after_id)
    after_ts = Keyword.get(opts, :after_ts)

    if after_id in [nil, ""] or after_ts in [nil, ""] do
      nil
    else
      %{"id" => after_id, "inserted_at" => after_ts}
    end
  end

  defp decode_history(nil), do: []
  defp decode_history(""), do: []

  defp decode_history(encoded) do
    with {:ok, json} <- Base.url_decode64(encoded, padding: false),
         {:ok, list} when is_list(list) <- Jason.decode(json) do
      list
    else
      _ -> []
    end
  end

  defp encode_history([]), do: ""

  defp encode_history(history) do
    history
    |> Jason.encode!()
    |> Base.url_encode64(padding: false)
  end

  defp filters_to_path(filters) do
    params =
      filters
      |> Keyword.take([:direction, :type, :q, :date_from, :date_to, :page_size])
      |> Keyword.put(:page, 1)
      |> Enum.reject(fn {_key, value} -> is_nil(value) or value == "" end)

    case URI.encode_query(params) do
      "" -> "/"
      query -> "/?#{query}"
    end
  end

  defp return_path(path) when is_binary(path) do
    if String.starts_with?(path, "/"), do: path, else: "/"
  end

  defp return_path(_path), do: "/"

  defp with_notice(path, kind, message) do
    separator = if String.contains?(path, "?"), do: "&", else: "?"
    key = if kind == :error, do: "error", else: "info"
    "#{path}#{separator}#{key}=#{URI.encode_www_form(message)}"
  end

  defp message_json(message) do
    %{
      id: message.id,
      direction: safe_text(message.direction),
      cid: safe_text(message.cid),
      priority: safe_text(message.priority),
      message_type: message.message_type,
      originator: message.originator,
      destinations: message.destinations,
      aircraft_id: message.aircraft_id,
      departure: message.departure,
      departure_time: message.departure_time,
      destination: message.destination,
      route: message.route,
      sequence_no: message.sequence_no,
      status: safe_text(message.status),
      note: safe_text(Map.get(message, :note)),
      filed_by: safe_text(message.filed_by),
      raw_text: safe_text(message.raw_text),
      inserted_at: safe_time(message.inserted_at)
    }
  end

  defp udp_monitor_json(item) do
    %{
      kind: safe_text(Map.get(item, :kind)),
      source_ip: safe_text(Map.get(item, :source_ip)),
      source_port: safe_text(Map.get(item, :source_port)),
      raw: safe_text(Map.get(item, :raw)),
      time: safe_time(Map.get(item, :time))
    }
  end

  defp safe_time(nil), do: ""
  defp safe_time(%DateTime{} = value), do: DateTime.to_iso8601(value)
  defp safe_time(%NaiveDateTime{} = value), do: NaiveDateTime.to_iso8601(value)
  defp safe_time(value), do: safe_text(value)

  defp safe_text(nil), do: ""

  defp safe_text(value) when is_binary(value) do
    if String.valid?(value), do: value, else: inspect(value)
  end

  defp safe_text(value), do: to_string(value)

  defp parse_int(value, _default) when is_integer(value), do: value

  defp parse_int(value, default) when is_binary(value) do
    case Integer.parse(value) do
      {number, _} -> number
      :error -> default
    end
  end

  defp parse_int(_value, default), do: default

  defp transmit_attrs(params) do
    {:ok,
     %{
       filed_by: Map.get(params, "filed_by", "WEB"),
       next_attempt_at: scheduled_send_at(params),
       udp_target_host: nil,
       udp_target_port: nil
     }}
  end

  defp scheduled_send_at(params) do
    value =
      params
      |> Map.get("transmission_id", "")
      |> to_string()
      |> String.replace(~r/\D/, "")

    if String.length(value) == 6 do
      now = DateTime.utc_now()

      with {day, ""} <- value |> String.slice(0, 2) |> Integer.parse(),
           {hour, ""} <- value |> String.slice(2, 2) |> Integer.parse(),
           {minute, ""} <- value |> String.slice(4, 2) |> Integer.parse(),
           true <- day >= 1 and day <= 31 and hour in 0..23 and minute in 0..59,
           {:ok, date} <- Date.new(now.year, now.month, day),
           {:ok, time} <- Time.new(hour, minute, 0),
           {:ok, scheduled} <- DateTime.new(date, time, "Etc/UTC") do
        if DateTime.compare(scheduled, now) == :gt, do: scheduled, else: nil
      else
        _ -> nil
      end
    end
  end

  defp compose_submit("save", type, params, attrs), do: Aftn.compose_and_save(type, params, attrs)
  defp compose_submit(_action, type, params, attrs), do: Aftn.compose_and_enqueue(type, params, attrs)

  defp redirect_after_compose(params, type, action \\ "send")

  defp redirect_after_compose(%{"return_to" => "compose"} = params, type, action) do
    form = Map.get(params, "return_form", type)
    verb = if action == "save", do: "saved", else: "queued"
    clear = if action == "send_clear", do: "&clear=1", else: ""
    "/compose?form=#{URI.encode_www_form(form)}&info=#{URI.encode_www_form("#{redirect_type(type)} #{verb}")}#{clear}"
  end

  defp redirect_after_compose(_params, type, _action) do
    "/?direction=outbound&type=#{URI.encode_www_form(redirect_type(type))}"
  end

  defp error_redirect(%{"return_to" => "compose"} = params, type, message) do
    form = Map.get(params, "return_form", type)
    "/compose?form=#{URI.encode_www_form(form)}&error=#{URI.encode_www_form(message)}"
  end

  defp error_redirect(_params, _type, message) do
    "/?error=#{URI.encode_www_form(message)}"
  end

  defp redirect_type("AFTN_FREE"), do: "FREE"
  defp redirect_type(type), do: type

  defp notice(params) do
    cond do
      error = Map.get(params, "error") -> {:error, error}
      info = Map.get(params, "info") -> {:info, info}
      true -> nil
    end
  end

  defp undefined_table?(%MyXQL.Error{mysql: %{code: 1146}}), do: true
  defp undefined_table?(%MyXQL.Error{mysql: %{name: :ER_NO_SUCH_TABLE}}), do: true
  defp undefined_table?(_error), do: false
end
