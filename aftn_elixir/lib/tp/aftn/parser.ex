defmodule Tp.Aftn.Parser do
  @status_prefixes ~w(Tseq Rseq Queu Line Link Conn warning QPrint Free)
  @message_types ~w(ALR RCF FPL CHG CNL DLA DEP ARR CPL EST CDN ACP LAM RQP RQS SPL ABI PAC MAC REJ TOC AOC EMG MIS TDM LRM ASM FAN FCN TRU ADS)

  def aftn?(raw) when is_binary(raw) do
    text = raw |> String.replace("\r\n", "\n") |> String.trim()
    parsed = parse_message(text)

    structured_aftn?(text) or
      (parsed.message_type in @message_types and
         (not is_nil(parsed.originator) or parsed.destinations != [])) or
      (not is_nil(parsed.priority) and not is_nil(parsed.originator) and parsed.destinations != [])
  end

  def aftn?(_raw), do: false

  def classify(raw) when is_binary(raw) do
    text = String.trim(raw)

    case Enum.find(@status_prefixes, &String.starts_with?(text, &1)) do
      nil -> {:message, parse_message(text)}
      prefix -> {:event, %{kind: prefix, payload: String.trim_leading(text, prefix) |> String.trim_leading(",") |> String.trim()}}
    end
  end

  def parse_message(text) do
    normalized = normalize_newlines(text)
    body = message_body(normalized)
    header = message_header(normalized)
    header_fields = parse_header(header)
    parse_text = if body == "", do: clean_controls(normalized), else: body
    tokens = Regex.scan(~r/[A-Z0-9\/.-]+/, clean_controls(normalized)) |> List.flatten()
    structured = parse_structured_body(parse_text)
    type = structured[:message_type] || Enum.find(@message_types, &Enum.member?(tokens, &1)) || detect_type_from_body(parse_text)

    %{
      raw_text: parse_text,
      message_type: type || "FREE",
      priority: header_fields[:priority] || find_priority(tokens),
      originator: header_fields[:originator] || find_originator(tokens),
      destinations: non_empty(header_fields[:destinations]) || find_destinations(tokens),
      aircraft_id: structured[:aircraft_id] || item_value(normalized, "7"),
      departure: structured[:departure] || first_four(item_value(normalized, "13")),
      departure_time: structured[:departure_time],
      destination: structured[:destination] || first_four(item_value(normalized, "16")),
      route: structured[:route] || item_value(normalized, "15"),
      filing_time: header_fields[:filing_time] || find_filing_time(tokens),
      sequence_no: header_fields[:sequence_no] || find_sequence(tokens),
      cid: header_fields[:cid],
      bell: String.contains?(text, <<7>>) or String.contains?(text, <<11>>),
      received_at: DateTime.utc_now()
    }
  end

  defp normalize_newlines(text), do: text |> to_string() |> String.replace("\r\n", "\n") |> String.replace("\r", "\n") |> String.trim()

  defp message_header(text) do
    text
    |> String.split(<<2>>, parts: 2)
    |> List.first()
    |> clean_controls()
  end

  defp message_body(text) do
    case String.split(text, <<2>>, parts: 2) do
      [_header, rest] ->
        rest
        |> String.split(<<3>>, parts: 2)
        |> List.first()
        |> String.replace(<<11>>, "")
        |> clean_controls()
        |> String.trim()

      _ ->
        ""
    end
  end

  defp clean_controls(text) do
    text
    |> to_string()
    |> String.replace(<<1>>, "")
    |> String.replace(<<2>>, "")
    |> String.replace(<<3>>, "")
    |> String.replace(<<7>>, "")
    |> String.replace(<<11>>, "")
  end

  defp parse_header(header) do
    lines =
      header
      |> String.split("\n", trim: true)
      |> Enum.map(&String.trim/1)
      |> Enum.reject(&(&1 == ""))

    tx = parse_transmission_line(List.first(lines) || "")
    filing = parse_filing_line(lines)

    %{
      cid: tx[:cid],
      sequence_no: tx[:sequence_no],
      priority: parse_priority(lines),
      destinations: parse_destinations(lines, filing[:line_index]),
      filing_time: filing[:filing_time],
      originator: filing[:originator]
    }
  end

  defp parse_transmission_line(line) do
    case Regex.run(~r/^([A-Z]{3,4})(\d{3,4})\s+(\d{6})\b/, line) do
      [_, cid, sequence_no, _time] -> %{cid: cid, sequence_no: sequence_no}
      _ -> %{}
    end
  end

  defp parse_filing_line(lines) do
    lines
    |> Enum.with_index()
    |> Enum.find_value(%{}, fn {line, index} ->
      case Regex.run(~r/^(\d{6})\s+([A-Z]{8})\b/, line) do
        [_, filing_time, originator] -> %{filing_time: filing_time, originator: originator, line_index: index}
        _ -> nil
      end
    end)
  end

  defp parse_priority(lines) do
    lines
    |> Enum.flat_map(&String.split(&1, ~r/\s+/, trim: true))
    |> Enum.find(&(&1 in ~w(SS DD FF GG KK)))
  end

  defp parse_destinations(lines, filing_index) do
    last_address_index = if is_integer(filing_index), do: filing_index - 1, else: length(lines) - 1

    lines
    |> Enum.with_index()
    |> Enum.filter(fn {_line, index} -> index <= last_address_index end)
    |> Enum.flat_map(fn {line, _index} -> String.split(line, ~r/\s+/, trim: true) end)
    |> drop_until_priority()
    |> Enum.filter(&Regex.match?(~r/^[A-Z]{8}$/, &1))
    |> Enum.take(21)
  end

  defp non_empty([]), do: nil
  defp non_empty(value), do: value

  defp drop_until_priority(tokens) do
    case Enum.split_while(tokens, &(&1 not in ~w(SS DD FF GG KK))) do
      {_before, [_priority | destinations]} -> destinations
      _ -> []
    end
  end

  defp find_priority(tokens), do: Enum.find(tokens, &(&1 in ~w(SS DD FF GG KK)))

  defp find_originator(tokens), do: Enum.find(tokens, &Regex.match?(~r/^[A-Z]{4}[A-Z]{4}$/, &1))

  defp find_destinations(tokens) do
    tokens
    |> Enum.filter(&Regex.match?(~r/^[A-Z]{4}[A-Z]{4}$/, &1))
    |> Enum.take(21)
  end

  defp find_filing_time(tokens), do: Enum.find(tokens, &Regex.match?(~r/^\d{6}$/, &1))

  defp find_sequence(tokens), do: Enum.find(tokens, &Regex.match?(~r/^\d{4}$/, &1))

  defp detect_type_from_body(text) do
    case Regex.run(~r/\(([A-Z]{3})-/, text) do
      [_, type] -> type
      _ -> nil
    end
  end

  defp structured_aftn?(text) do
    case Regex.run(~r/\(([A-Z]{3})-/, text) do
      [_, type] -> type in @message_types
      _ -> false
    end
  end

  defp parse_structured_body(text) do
    with [_, body] <- Regex.run(~r/\((.+?)\)/s, text),
         [type | fields] <- body |> String.replace("\n", "") |> String.split("-", trim: true) do
      parse_body_fields(type, fields)
    else
      _ -> %{}
    end
  end

  defp parse_body_fields("FPL", fields) do
    %{
      message_type: "FPL",
      aircraft_id: Enum.at(fields, 0),
      departure: fields |> Enum.at(4) |> first_four(),
      departure_time: fields |> Enum.at(4) |> slice(4, 4),
      route: fields |> Enum.at(5) |> route_without_speed_level(),
      destination: fields |> Enum.at(6) |> first_four()
    }
  end

  defp parse_body_fields(type, fields) when type in ~w(DLA CHG CNL DEP RQP RQS) do
    %{
      message_type: type,
      aircraft_id: Enum.at(fields, 0),
      departure: fields |> Enum.at(1) |> first_four(),
      departure_time: fields |> Enum.at(1) |> slice(4, 4),
      destination: fields |> Enum.at(2) |> first_four()
    }
  end

  defp parse_body_fields("ARR", fields) do
    %{
      message_type: "ARR",
      aircraft_id: Enum.at(fields, 0),
      departure: fields |> Enum.at(1) |> first_four(),
      destination: fields |> Enum.at(2) |> first_four()
    }
  end

  defp parse_body_fields(type, fields) when type in @message_types do
    %{message_type: type, aircraft_id: Enum.at(fields, 0)}
  end

  defp parse_body_fields(_type, _fields), do: %{}

  defp item_value(text, item) do
    case Regex.run(~r/(?:^|\n|-)#{item}\/?([A-Z0-9 .\/-]+)/, text) do
      [_, value] -> String.trim(value)
      _ -> nil
    end
  end

  defp first_four(nil), do: nil
  defp first_four(value), do: String.slice(value, 0, 4)

  defp slice(nil, _start, _length), do: nil
  defp slice(value, start, length), do: String.slice(value, start, length)

  defp route_without_speed_level(nil), do: nil

  defp route_without_speed_level(value) do
    value
    |> String.trim()
    |> String.split(~r/\s+/, parts: 2)
    |> case do
      [_speed_level, route] -> route
      [route] -> route
      _ -> nil
    end
  end
end
