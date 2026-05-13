defmodule Tp.Aftn.Legacy do
  alias Tp.Aftn.{Message, Parser}
  alias Tp.Repo

  @table_regex ~r/^air_message\d{4}_\d{2}$/
  @destinations Enum.map(1..21, &"destination#{&1}")
  @read_before_tgl ~w(priority originator filing_time)
  @read_after_tgl ~w(status note filedby filled_by cid seq sequence_no type3a type7a type13a type13b type15c type16a type16b msgall freetext bell) ++ @destinations

  def list_messages(opts \\ []) do
    {messages, _pagination} = list_messages_page(Keyword.put(opts, :page_size, Keyword.get(opts, :limit, 100)))
    messages
  end

  def latest_messages(opts \\ []) do
    limit = opts |> Keyword.get(:limit, Keyword.get(opts, :page_size, 15)) |> parse_int(15) |> clamp(1, 1000)

    opts
    |> query_rows(limit, 0)
    |> Enum.map(&row_to_message/1)
  end

  def list_messages_page(opts \\ []) do
    page_size = opts |> Keyword.get(:page_size, 15) |> parse_int(15) |> clamp(1, 1000)
    page = opts |> Keyword.get(:page, 1) |> parse_int(1) |> max(1)
    total_count = count_rows(opts)
    total_pages = total_count |> div_ceil(page_size) |> max(1)
    page = min(page, total_pages)
    offset = (page - 1) * page_size

    messages =
      opts
      |> query_rows(page_size, offset)
      |> Enum.map(&row_to_message/1)

    {messages,
     %{
       page: page,
       page_size: page_size,
       has_next: page < total_pages,
       total_count: total_count,
       total_pages: total_pages
     }}
  end

  def get_message(id) when is_integer(id) do
    with {table, legacy_id} <- decode_id(id),
         true <- legacy_table_name?(table),
         true <- table_exists?(table),
         %{rows: [row]} <- legacy_query(table, "id_ats = ?", [legacy_id], 1) do
      row_to_message(row)
    else
      _ -> nil
    end
  end

  def delete_message(id) when is_integer(id) do
    with {table, legacy_id} <- decode_id(id),
         true <- legacy_table_name?(table),
         true <- table_exists?(table) do
      result = Repo.query!("DELETE FROM #{quote_name(table)} WHERE id_ats = ?", [legacy_id])

      if affected_rows(result) > 0 do
        {:ok, %{id: id, legacy_table: table, legacy_id: legacy_id}}
      else
        {:error, :not_found}
      end
    else
      _ -> {:error, :not_found}
    end
  end

  def delete_all(opts \\ []) do
    tables = tables_for(opts)

    deleted =
      Enum.reduce(tables, 0, fn table, total ->
        columns = table_columns(table)
        {where_parts, params} = legacy_where(opts, columns)

        result =
          Repo.query!(
            "DELETE FROM #{quote_name(table)} WHERE #{Enum.join(where_parts, " AND ")}",
            params
          )

        total + affected_rows(result)
      end)

    {:ok, deleted}
  end

  def insert_inbound(attrs) do
    now = DateTime.utc_now()
    table = month_table(now)

    with {:ok, table} <- ensure_month_table(table),
         columns when columns != [] <- table_columns(table) do
      parsed = attrs[:parsed_fields] || attrs
      destinations = attrs[:destinations] || []

      values = %{
        "priority" => attrs[:priority],
        "originator" => attrs[:originator],
        "filing_time" => attrs[:filing_time],
        "jam" => time_string(now),
        "tgl" => naive_now(now),
        "filedby" => attrs[:filed_by] || "WEB",
        "filled_by" => attrs[:filed_by] || "WEB",
        "cid" => attrs[:cid],
        "seq" => attrs[:sequence_no],
        "sequence_no" => attrs[:sequence_no],
        "tbl_name" => table,
        "io" => "0",
        "status" => attrs[:status] || "received",
        "type3a" => legacy_type(attrs[:message_type]),
        "type7a" => attrs[:aircraft_id],
        "type13a" => attrs[:departure],
        "type13b" => attrs[:departure_time],
        "type15c" => attrs[:route],
        "type16a" => attrs[:destination],
        "type16b" => attrs[:departure_time],
        "msgall" => attrs[:raw_text],
        "freetext" => attrs[:raw_text],
        "bell" => bool_value(attrs[:bell]),
        "note" => source_note(parsed)
      }

      values =
        destinations
        |> Enum.take(21)
        |> Enum.with_index(1)
        |> Enum.reduce(values, fn {destination, index}, acc -> Map.put(acc, "destination#{index}", destination) end)

      insert_columns = columns |> Enum.filter(&Map.has_key?(values, &1))
      params = Enum.map(insert_columns, &Map.get(values, &1))
      placeholders = Enum.map_join(insert_columns, ", ", fn _ -> "?" end)
      quoted_columns = Enum.map_join(insert_columns, ", ", &quote_name/1)

      result = Repo.query!("INSERT INTO #{quote_name(table)} (#{quoted_columns}) VALUES (#{placeholders})", params)
      legacy_id = Map.get(result, :last_insert_id) || Map.get(result, :insert_id) || 0

      {:ok,
       struct(Message,
         id: encode_id(table, legacy_id),
         legacy_table: table,
         legacy_id: legacy_id,
         direction: :inbound,
         priority: attrs[:priority],
         destinations: destinations,
         filing_time: attrs[:filing_time],
         originator: attrs[:originator],
         message_type: attrs[:message_type] || "FREE",
         aircraft_id: attrs[:aircraft_id],
         departure: attrs[:departure],
         departure_time: attrs[:departure_time],
         destination: attrs[:destination],
         route: attrs[:route],
         raw_text: attrs[:raw_text],
         parsed_fields: parsed,
         status: attrs[:status] || "received",
         note: source_note(parsed),
         cid: attrs[:cid],
         sequence_no: attrs[:sequence_no],
         bell: attrs[:bell] || false,
         received_at: now,
         inserted_at: DateTime.to_naive(now),
         updated_at: DateTime.to_naive(now)
       )}
    else
      {:error, reason} -> {:error, reason}
      _ -> {:error, :legacy_table_unavailable}
    end
  rescue
    error in MyXQL.Error -> {:error, error}
  end

  def table_names do
    %{rows: rows} =
      Repo.query!(
        """
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = DATABASE()
          AND table_name REGEXP '^air_message[0-9]{4}_[0-9]{2}$'
        ORDER BY table_name DESC
        """,
        []
      )

    rows
    |> Enum.map(fn [name] -> name end)
    |> Enum.filter(&legacy_table_name?/1)
  end

  defp tables_for(opts) do
    existing = table_names()
    wanted = month_tables_for_dates(Keyword.get(opts, :date_from), Keyword.get(opts, :date_to))

    existing
    |> Enum.filter(&(&1 in wanted))
    |> case do
      [] -> wanted |> Enum.filter(&(&1 in existing))
      tables -> tables
    end
  end

  defp month_tables_for_dates(date_from, date_to) do
    {from_date, to_date} = date_range(date_from, date_to)

    from_date
    |> month_starts_until(to_date)
    |> Enum.map(&month_table/1)
  end

  defp date_range("", ""), do: current_month_range()
  defp date_range(nil, nil), do: current_month_range()

  defp date_range(date_from, date_to) do
    today = Date.utc_today()
    from_date = parse_date(date_from) || parse_date(date_to) || Date.beginning_of_month(today)
    to_date = parse_date(date_to) || today

    if Date.compare(from_date, to_date) == :gt do
      {to_date, from_date}
    else
      {from_date, to_date}
    end
  end

  defp current_month_range do
    today = Date.utc_today()
    {Date.beginning_of_month(today), Date.end_of_month(today)}
  end

  defp month_starts_until(from_date, to_date) do
    from_month = Date.beginning_of_month(from_date)
    to_month = Date.beginning_of_month(to_date)

    Stream.iterate(from_month, &Date.add(Date.end_of_month(&1), 1))
    |> Enum.take_while(&(Date.compare(&1, to_month) != :gt))
  end

  defp query_rows(opts, limit, offset) do
    tables = tables_for(opts)

    if tables == [] do
      []
    else
      {selects, params} =
        tables
        |> Enum.map(&legacy_select(&1, opts))
        |> Enum.unzip()

      sql = """
      SELECT *
      FROM (
      #{Enum.join(selects, "\nUNION ALL\n")}
      ) legacy_messages
      ORDER BY inserted_at DESC, synthetic_id DESC
      LIMIT ?
      OFFSET ?
      """

      %{rows: rows} = Repo.query!(sql, List.flatten(params) ++ [limit, offset])
      rows
    end
  end

  defp count_rows(opts) do
    tables = tables_for(opts)

    if tables == [] do
      0
    else
      {selects, params} =
        tables
        |> Enum.map(&legacy_count_select(&1, opts))
        |> Enum.unzip()

      sql = """
      SELECT COALESCE(SUM(row_count), 0)
      FROM (
      #{Enum.join(selects, "\nUNION ALL\n")}
      ) legacy_counts
      """

      %{rows: [[count]]} = Repo.query!(sql, List.flatten(params))
      to_int(count)
    end
  end

  defp legacy_select(table, opts) do
    columns = table_columns(table)
    {where_parts, params} = legacy_where(opts, columns)
    before_tgl = Enum.map_join(@read_before_tgl, ", ", &column_expr(columns, &1))
    after_tgl = Enum.map_join(@read_after_tgl, ", ", &column_expr(columns, &1))

    {"""
     SELECT
       #{encode_id_sql(table)} AS synthetic_id,
       #{quote_string(table)} AS legacy_table,
       id_ats AS legacy_id,
       #{before_tgl},
       tgl AS inserted_at,
       #{after_tgl}
     FROM #{quote_name(table)}
     WHERE #{Enum.join(where_parts, " AND ")}
     """, params}
  end

  defp legacy_count_select(table, opts) do
    columns = table_columns(table)
    {where_parts, params} = legacy_where(opts, columns)

    {"""
     SELECT COUNT(*) AS row_count
     FROM #{quote_name(table)}
     WHERE #{Enum.join(where_parts, " AND ")}
     """, params}
  end

  defp legacy_where(opts, columns) do
    {where, params} = {["id_ats != 0"], []}
    {where, params} = maybe_type_where(where, params, Keyword.get(opts, :type), columns)
    {where, params} = maybe_search_where(where, params, Keyword.get(opts, :q), columns)
    {where, params} = maybe_cid_where(where, params, Keyword.get(opts, :cid), columns)
    {where, params} = maybe_sequence_where(where, params, Keyword.get(opts, :seq_from), Keyword.get(opts, :seq_to), columns)
    {where, params} = maybe_text_where(where, params, Keyword.get(opts, :text), columns)
    {where, params} = maybe_filed_by_where(where, params, Keyword.get(opts, :filed_by), columns)
    maybe_date_where(where, params, Keyword.get(opts, :date_from), Keyword.get(opts, :date_to))
  end

  defp maybe_type_where(where, params, type, _columns) when type in [nil, ""], do: {where, params}

  defp maybe_type_where(where, params, type, columns) do
    if "type3a" in columns do
      {["type3a = ?" | where], [String.upcase(type) | params]}
    else
      {where, params}
    end
  end

  defp maybe_search_where(where, params, search, _columns) when search in [nil, ""], do: {where, params}

  defp maybe_search_where(where, params, search, columns) do
    term = "%#{String.downcase(search)}%"
    search_columns = ~w(msgall freetext priority originator type3a type7a type13a type15c type16a) ++ @destinations
    search_sql = "LOWER(CONCAT_WS(' ', #{Enum.map_join(search_columns, ", ", &column_expr(columns, &1))})) LIKE ?"
    {[search_sql | where], [term | params]}
  end


  defp maybe_cid_where(where, params, cid, _columns) when cid in [nil, ""], do: {where, params}

  defp maybe_cid_where(where, params, cid, columns) do
    term = "%#{String.downcase(to_string(cid))}%"
    sql = "LOWER(#{column_expr(columns, "cid")}) LIKE ?"
    {[sql | where], [term | params]}
  end

  defp maybe_sequence_where(where, params, seq_from, seq_to, _columns) when seq_from in [nil, ""] and seq_to in [nil, ""], do: {where, params}

  defp maybe_sequence_where(where, params, seq_from, seq_to, columns) do
    from_seq = parse_int(seq_from, nil) || parse_int(seq_to, nil)
    to_seq = parse_int(seq_to, nil) || parse_int(seq_from, nil)

    if from_seq && to_seq do
      {from_seq, to_seq} = if from_seq > to_seq, do: {to_seq, from_seq}, else: {from_seq, to_seq}
      seq_expr = column_expr(columns, "seq")
      alt_seq_expr = column_expr(columns, "sequence_no")
      sql = "CAST(COALESCE(NULLIF(#{seq_expr}, ''), NULLIF(#{alt_seq_expr}, ''), '0') AS UNSIGNED) BETWEEN ? AND ?"
      {[sql | where], [from_seq, to_seq | params]}
    else
      {where, params}
    end
  end

  defp maybe_text_where(where, params, text, _columns) when text in [nil, ""], do: {where, params}

  defp maybe_text_where(where, params, text, columns) do
    term = "%#{String.downcase(to_string(text))}%"
    search_columns = ~w(msgall freetext)
    search_sql = "LOWER(CONCAT_WS(' ', #{Enum.map_join(search_columns, ", ", &column_expr(columns, &1))})) LIKE ?"
    {[search_sql | where], [term | params]}
  end

  defp maybe_filed_by_where(where, params, filed_by, _columns) when filed_by in [nil, ""], do: {where, params}

  defp maybe_filed_by_where(where, params, filed_by, columns) do
    term = "%#{String.downcase(to_string(filed_by))}%"
    search_columns = ~w(filedby filled_by)
    search_sql = "LOWER(CONCAT_WS(' ', #{Enum.map_join(search_columns, ", ", &column_expr(columns, &1))})) LIKE ?"
    {[search_sql | where], [term | params]}
  end

  defp maybe_date_where(where, params, date_from, date_to) do
    {from_date, to_date} = date_range(date_from, date_to)
    from_time = NaiveDateTime.new!(from_date, ~T[00:00:00])
    to_time = NaiveDateTime.new!(to_date, ~T[23:59:59])

    {["tgl >= ? AND tgl <= ?" | where], [from_time, to_time | params]}
  end

  defp legacy_query(table, where, params, limit) do
    columns = table_columns(table)
    before_tgl = Enum.map_join(@read_before_tgl, ", ", &column_expr(columns, &1))
    after_tgl = Enum.map_join(@read_after_tgl, ", ", &column_expr(columns, &1))

    Repo.query!(
      """
      SELECT
        #{encode_id_sql(table)} AS synthetic_id,
        #{quote_string(table)} AS legacy_table,
        id_ats AS legacy_id,
        #{before_tgl},
        tgl AS inserted_at,
        #{after_tgl}
      FROM #{quote_name(table)}
      WHERE #{where}
      LIMIT ?
      """,
      params ++ [limit]
    )
  end

  defp row_to_message([
         synthetic_id,
         legacy_table,
         legacy_id,
         priority,
         originator,
         filing_time,
         inserted_at,
         status,
         note,
         filed_by,
         filled_by,
         cid,
         sequence_no,
         sequence_no_alt,
         type,
         aircraft_id,
         departure,
         departure_time,
         route,
         destination,
         destination_time,
         msgall,
         freetext,
         bell | destinations
       ]) do
    raw_text = present(msgall) || ""
    parsed = Parser.parse_message(raw_text)

    struct(Message,
      id: synthetic_id,
      direction: :inbound,
      priority: present(priority) || parsed[:priority],
      destinations: destinations |> Enum.map(&present/1) |> Enum.reject(&is_nil/1),
      filing_time: present(filing_time) || parsed[:filing_time],
      originator: present(originator) || parsed[:originator],
      message_type: present(type) || parsed[:message_type] || "FREE",
      aircraft_id: present(aircraft_id) || parsed[:aircraft_id],
      departure: present(departure) || parsed[:departure],
      route: present(route) || parsed[:route],
      raw_text: raw_text,
      parsed_fields: parsed,
      status: present(status),
      note: present(note),
      filed_by: present(filed_by) || present(filled_by),
      cid: present(cid),
      sequence_no: present(sequence_no) || present(sequence_no_alt),
      bell: truthy?(bell),
      legacy_table: legacy_table,
      legacy_id: legacy_id,
      received_at: inserted_at,
      inserted_at: inserted_at,
      updated_at: inserted_at,
      destination: present(destination),
      departure_time: present(departure_time) || present(destination_time) || parsed[:departure_time]
    )
  end

  defp ensure_month_table(table) do
    cond do
      table_exists?(table) ->
        {:ok, table}

      template = List.first(table_names()) ->
        Repo.query!("CREATE TABLE #{quote_name(table)} LIKE #{quote_name(template)}", [])
        {:ok, table}

      true ->
        {:error, :no_legacy_air_message_template}
    end
  end

  defp table_exists?(table), do: table in table_names()

  defp affected_rows(result), do: Map.get(result, :num_rows) || Map.get(result, :rows_affected) || 0

  defp table_columns(table) do
    %{rows: rows} =
      Repo.query!(
        """
        SELECT column_name
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = ?
        ORDER BY ordinal_position
        """,
        [table]
      )

    Enum.map(rows, fn [column] -> column end)
  end

  defp encode_id(table, id), do: table_code(table) * 10_000_000 + (id || 0)
  defp encode_id_sql(table), do: "(#{table_code(table)} * 10000000 + id_ats)"

  defp decode_id(id) do
    table_code = div(id, 10_000_000)
    legacy_id = rem(id, 10_000_000)
    year = div(table_code, 100)
    month = rem(table_code, 100)
    {"air_message#{year}_#{month |> Integer.to_string() |> String.pad_leading(2, "0")}", legacy_id}
  end

  defp table_code("air_message" <> year_month) do
    year_month
    |> String.replace("_", "")
    |> parse_int(0)
  end

  defp legacy_table_name?(name) when is_binary(name), do: Regex.match?(@table_regex, name)
  defp legacy_table_name?(_name), do: false

  defp month_table(%DateTime{} = datetime) do
    "air_message#{datetime.year}_#{datetime.month |> Integer.to_string() |> String.pad_leading(2, "0")}"
  end

  defp month_table(%Date{} = date) do
    "air_message#{date.year}_#{date.month |> Integer.to_string() |> String.pad_leading(2, "0")}"
  end

  defp parse_date(value) when value in [nil, ""], do: nil

  defp parse_date(value) when is_binary(value) do
    case Date.from_iso8601(value) do
      {:ok, date} -> date
      _ -> nil
    end
  end

  defp parse_date(_value), do: nil

  defp quote_name(name), do: "`#{String.replace(to_string(name), "`", "``")}`"
  defp quote_string(value), do: "'#{String.replace(to_string(value), "'", "''")}'"
  defp column_expr(columns, column), do: if(column in columns, do: quote_name(column), else: "NULL")

  defp present(nil), do: nil
  defp present(""), do: nil
  defp present(value) when is_binary(value), do: if(String.trim(value) == "", do: nil, else: String.trim(value))
  defp present(value), do: value

  defp truthy?(value), do: value in [true, 1, "1", "true", "TRUE", "Y", "y"]
  defp bool_value(value), do: if(truthy?(value), do: "1", else: "0")
  defp legacy_type(type) when type in [nil, "", "FREE"], do: ""
  defp legacy_type(type), do: type

  defp source_note(fields) do
    ip = Map.get(fields, :source_ip) || Map.get(fields, "source_ip")
    port = Map.get(fields, :source_port) || Map.get(fields, "source_port")
    if ip || port, do: "UDP #{ip}:#{port}", else: "UDP"
  end

  defp naive_now(%DateTime{} = now), do: DateTime.to_naive(now) |> NaiveDateTime.truncate(:second)
  defp time_string(%DateTime{} = now) do
    time = DateTime.to_time(now)
    "#{pad2(time.hour)}#{pad2(time.minute)}#{pad2(time.second)}"
  end

  defp pad2(value), do: value |> Integer.to_string() |> String.pad_leading(2, "0")

  defp parse_int(value, _default) when is_integer(value), do: value

  defp parse_int(value, default) when is_binary(value) do
    case Integer.parse(value) do
      {number, _} -> number
      :error -> default
    end
  end

  defp parse_int(_value, default), do: default
  defp to_int(%Decimal{} = value), do: value |> Decimal.to_integer()
  defp to_int(nil), do: 0
  defp to_int(value) when is_integer(value), do: value
  defp to_int(value) when is_binary(value), do: parse_int(value, 0)
  defp to_int(value), do: value |> to_string() |> parse_int(0)
  defp clamp(number, min_value, max_value), do: number |> max(min_value) |> min(max_value)
  defp div_ceil(number, divisor), do: div(number + divisor - 1, divisor)
end
