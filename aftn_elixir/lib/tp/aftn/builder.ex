defmodule Tp.Aftn.Builder do
  @basic_flight_types ~w(DLA CHG CNL DEP RQP RQS)
  @fpl_like_types ~w(FPL CPL SPL)
  @supported_types ~w(FREE AFTN_FREE FPL DLA CHG CNL DEP ARR CDN CPL EST ACP LAM RQP RQS SPL RCF ALR)

  def validate("FREE", params), do: require_fields(params, ["message"])

  def validate("AFTN_FREE", params) do
    params
    |> require_fields(["transmission_id", "priority", "originator", "message"])
    |> validate_format(params, "transmission_id", ~r/^[A-Z0-9]{3,12}$/, "TX ID harus 3-12 huruf/angka tanpa spasi")
    |> validate_optional_format(params, "filing_time", ~r/^\d{6}$/, "Filing Time harus DDHHMM 6 digit")
    |> validate_format(params, "priority", ~r/^(SS|DD|FF|GG|KK)$/, "Priority harus SS/DD/FF/GG/KK")
    |> validate_destinations(params)
    |> validate_format(params, "originator", ~r/^[A-Z]{4}[A-Z]{4}$/, "Originator harus 8 huruf AFTN")
  end

  def validate(type, params) when type in @fpl_like_types do
    params
    |> require_fields(["aircraft_id", "aircraft_type", "equipment", "departure", "departure_time", "destination", "speed_level", "route", "eet"])
    |> validate_common_flight(params)
    |> validate_format(params, "aircraft_type", ~r/^[A-Z0-9]{2,4}$/, "Type aircraft harus 2-4 huruf/angka")
    |> validate_format(params, "eet", ~r/^\d{4}$/, "EET harus HHMM 4 digit")
    |> validate_aftn_header(params)
  end

  def validate(type, params) when type in @basic_flight_types do
    params
    |> require_fields(["aircraft_id", "departure", "departure_time", "destination"])
    |> validate_common_flight(params)
    |> validate_optional_format(params, "dof", ~r/^\d{6}$/, "DOF harus YYMMDD 6 digit")
    |> validate_aftn_header(params)
  end

  def validate("CDN", params) do
    validate("DLA", params)
    |> require_when_empty(params, "item22", "Item 22 wajib diisi")
  end

  def validate("EST", params) do
    params
    |> require_fields(["aircraft_id", "departure", "departure_time", "fix", "fix_time", "destination"])
    |> validate_common_flight(params)
    |> validate_format(params, "fix_time", ~r/^\d{4}$/, "Fix time harus HHMM 4 digit")
    |> validate_aftn_header(params)
  end

  def validate(type, params) when type in ~w(ACP ARR) do
    params
    |> require_fields(["aircraft_id", "departure", "destination", "arrival_time"])
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_format(params, "departure", ~r/^[A-Z]{4}$/, "Departure harus ICAO 4 huruf")
    |> validate_format(params, "destination", ~r/^[A-Z]{4}$/, "Destination harus ICAO 4 huruf")
    |> validate_format(params, "arrival_time", ~r/^\d{4}$/, "Arrival/ACP time harus HHMM 4 digit")
    |> validate_aftn_header(params)
  end

  def validate("LAM", params) do
    params
    |> require_fields(["reference"])
    |> validate_aftn_header(params)
  end

  def validate("RCF", params) do
    params
    |> require_fields(["aircraft_id", "radio_failure"])
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_aftn_header(params)
  end

  def validate("ALR", params) do
    params
    |> require_fields([
      "phase",
      "alert_originator",
      "nature",
      "aircraft_id",
      "flight_rules",
      "flight_type",
      "aircraft_type",
      "wake",
      "equipment",
      "equipment_surveillance",
      "departure",
      "departure_time",
      "speed",
      "level",
      "route",
      "destination",
      "eet",
      "alternate",
      "dof",
      "sar_info"
    ])
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_format(params, "aircraft_type", ~r/^[A-Z0-9]{2,4}$/, "Type aircraft harus 2-4 huruf/angka")
    |> validate_format(params, "departure", ~r/^[A-Z]{4}$/, "Departure harus ICAO 4 huruf")
    |> validate_format(params, "departure_time", ~r/^\d{4}$/, "Departure time harus HHMM 4 digit")
    |> validate_format(params, "destination", ~r/^[A-Z]{4}$/, "Destination harus ICAO 4 huruf")
    |> validate_format(params, "eet", ~r/^\d{4}$/, "EET harus HHMM 4 digit")
    |> validate_format(params, "alternate", ~r/^[A-Z]{4}$/, "Alternate harus ICAO 4 huruf")
    |> validate_format(params, "dof", ~r/^\d{6}$/, "DOF harus YYMMDD 6 digit")
    |> validate_aftn_header(params)
  end

  def validate(type, params) when is_binary(type) do
    type = String.upcase(type)

    if type in @supported_types do
      validate(type, params)
    else
      ["Tipe message #{type} belum didukung"]
    end
  end

  def build("FREE", params) do
    message = text(params, "message")
    cid_seq = up(params, "cid_seq", "RCJ0001")
    filing_time = filing_time(params)

    [
      <<1>>,
      cid_seq,
      " ",
      filing_time,
      "\r\n",
      <<2>>,
      message,
      "\r\n",
      <<11>>,
      <<3>>
    ]
    |> IO.iodata_to_binary()
  end

  def build("AFTN_FREE", params) do
    params
    |> text("message")
    |> upcase()
    |> aftn_packet(params)
  end

  def build(type, params) when type in @fpl_like_types do
    params
    |> fpl_like_body(type)
    |> maybe_wrap_aftn(params)
  end

  def build("DLA", params), do: basic_flight_body("DLA", params) |> maybe_wrap_aftn(params)

  def build("CHG", params) do
    extra =
      case text(params, "item22") do
        "" -> ""
        value -> "-#{upcase(value)}"
      end

    basic_flight_body("CHG", params, extra) |> maybe_wrap_aftn(params)
  end

  def build(type, params) when type in ~w(CNL DEP RQP RQS), do: basic_flight_body(type, params) |> maybe_wrap_aftn(params)

  def build("CDN", params) do
    "(CDN-#{up(params, "aircraft_id")}-#{up(params, "departure")}#{digits(params, "departure_time")}-#{up(params, "destination")}-#{up(params, "item22")})"
    |> maybe_wrap_aftn(params)
  end

  def build("EST", params) do
    [
      "(EST-#{up(params, "aircraft_id")}",
      "-#{up(params, "departure")}#{digits(params, "departure_time")}",
      "-#{up(params, "fix")}#{digits(params, "fix_time")} #{up(params, "estimate_level")}",
      "-#{up(params, "destination")})"
    ]
    |> Enum.join("")
    |> maybe_wrap_aftn(params)
  end

  def build(type, params) when type in ~w(ACP ARR) do
    "(#{type}-#{up(params, "aircraft_id")}-#{up(params, "departure")}-#{up(params, "destination")}#{digits(params, "arrival_time")})"
    |> maybe_wrap_aftn(params)
  end

  def build("LAM", params), do: "(LAM-#{up(params, "reference")})" |> maybe_wrap_aftn(params)

  def build("RCF", params) do
    "(RCF-#{up(params, "aircraft_id")}-#{up(params, "radio_failure")})"
    |> maybe_wrap_aftn(params)
  end

  def build("ALR", params) do
    params
    |> alr_body()
    |> maybe_wrap_aftn(params)
  end

  def build(type, params) when is_binary(type) do
    type = String.upcase(type)

    if type in @supported_types do
      build(type, params)
    else
      raise ArgumentError, "unsupported message type #{type}"
    end
  end

  defp fpl_like_body(params, type) do
    [
      "(#{type}-#{up(params, "aircraft_id")}-#{up(params, "flight_rules", "I")}#{up(params, "flight_type", "S")}",
      "-#{up(params, "aircraft_type")}/#{up(params, "wake", "M")}-#{up(params, "equipment")}",
      "-#{up(params, "departure")}#{digits(params, "departure_time")}",
      "-#{up(params, "speed_level")} #{up(params, "route")}",
      "-#{up(params, "destination")}#{digits(params, "eet")}",
      item18(params),
      ")"
    ]
    |> Enum.reject(&(&1 in ["-", "- ", nil]))
    |> Enum.join("\r\n")
  end

  defp basic_flight_body(type, params, extra \\ "") do
    dof =
      case text(params, "dof") do
        "" -> ""
        value -> "-DOF/#{upcase(value)}"
      end

    "(#{type}-#{up(params, "aircraft_id")}-#{up(params, "departure")}#{digits(params, "departure_time")}-#{up(params, "destination")}#{dof}#{extra})"
  end

  defp alr_body(params) do
    [
      "(ALR",
      alr_item3(params),
      alr_item5(params),
      alr_item7(params),
      alr_item8(params),
      alr_item9_10(params),
      alr_item13(params),
      alr_item15(params),
      alr_item16(params),
      alr_item18(params),
      alr_item19(params),
      alr_item20(params),
      ")"
    ]
    |> Enum.reject(&(&1 in ["", nil]))
    |> Enum.join("\r\n")
  end

  defp alr_item3(params), do: alr_part([up(params, "alr_number"), up(params, "reference_data")])

  defp alr_item5(params) do
    alr_part([up(params, "phase"), up(params, "alert_originator"), up(params, "nature")], "/")
  end

  defp alr_item7(params), do: alr_part([up(params, "aircraft_id"), alr_ssr(params), up(params, "ssr_code")])

  defp alr_item8(params) do
    flight_rules = up(params, "flight_rules", "I")
    flight_type = up(params, "flight_type", "S")

    case flight_rules <> flight_type do
      "" -> nil
      value -> "-#{value}"
    end
  end

  defp alr_item9_10(params) do
    number = up(params, "aircraft_number")
    aircraft_type = up(params, "aircraft_type")
    wake = up(params, "wake")
    equipment = up(params, "equipment")
    surveillance = up(params, "equipment_surveillance")

    aircraft =
      cond do
        number != "" and aircraft_type != "" and wake != "" -> "#{number}#{aircraft_type}/#{wake}"
        number != "" and aircraft_type != "" -> "#{number}#{aircraft_type}"
        number != "" and wake != "" -> "#{number}/#{wake}"
        aircraft_type != "" and wake != "" -> "#{aircraft_type}/#{wake}"
        aircraft_type != "" -> aircraft_type
        wake != "" -> "/#{wake}"
        true -> ""
      end

    equipment =
      cond do
        equipment != "" and surveillance != "" -> "#{equipment}/#{surveillance}"
        equipment != "" -> equipment
        true -> surveillance
      end

    cond do
      aircraft == "" and equipment == "" -> nil
      aircraft == "" -> "-#{equipment}"
      equipment == "" -> "-#{aircraft}"
      true -> "-#{aircraft}-#{equipment}"
    end
  end

  defp alr_item13(params) do
    aerodrome = up(params, "departure")
    time = digits(params, "departure_time")

    case aerodrome <> time do
      "" -> nil
      value -> "-#{value}"
    end
  end

  defp alr_item15(params) do
    speed_level = up(params, "speed") <> up(params, "level")
    route = up(params, "route")

    cond do
      speed_level == "" and route == "" -> nil
      speed_level == "" -> "-#{route}"
      route == "" -> "-#{speed_level}"
      true -> "-#{speed_level} #{route}"
    end
  end

  defp alr_item16(params) do
    destination = up(params, "destination")
    eet = digits(params, "eet")
    first = destination <> eet
    alternates = [up(params, "alternate"), up(params, "second_alternate")] |> Enum.reject(&(&1 == ""))

    case [first | alternates] |> Enum.reject(&(&1 == "")) do
      [] -> nil
      fields -> "-#{Enum.join(fields, " ")}"
    end
  end

  defp alr_part(values, separator \\ " ") do
    values
    |> Enum.reject(&(&1 in [nil, ""]))
    |> case do
      [] -> nil
      present -> "-" <> Enum.join(present, separator)
    end
  end

  defp alr_ssr(params) do
    mode = up(params, "ssr_mode")
    if mode == "", do: "", else: "SSR/#{mode}"
  end

  defp alr_item18(params) do
    fields =
      [
        alr_slash("STS", params, "sts"),
        alr_slash("PBN", params, "pbn"),
        alr_slash("NAV", params, "nav"),
        alr_slash("COM", params, "com"),
        alr_slash("DAT", params, "dat"),
        alr_slash("SUR", params, "sur"),
        alr_slash("DEP", params, "dep_info"),
        alr_slash("DEST", params, "dest_info"),
        alr_slash("DOF", params, "dof"),
        alr_slash("REG", params, "reg"),
        alr_slash("EET", params, "eet_detail"),
        alr_slash("SEL", params, "sel"),
        alr_slash("TYP", params, "typ"),
        alr_slash("CODE", params, "code"),
        alr_slash("DLE", params, "dle"),
        alr_slash("OPR", params, "opr"),
        alr_slash("ORGN", params, "orgn"),
        alr_slash("PER", params, "per"),
        alr_slash("ALTN", params, "altn_detail"),
        alr_slash("RALT", params, "ralt"),
        alr_slash("TALT", params, "talt"),
        alr_slash("RIF", params, "rif"),
        alr_slash("RMK", params, "rmk")
      ]
      |> Enum.reject(&(&1 == ""))

    if fields == [], do: nil, else: "-#{Enum.join(fields, " ")}"
  end

  defp alr_item19(params) do
    fields =
      [
        alr_slash("E", params, "endurance"),
        alr_slash("P", params, "pob"),
        alr_flags("R", params, [{"U", "radio_uhf"}, {"V", "radio_vhf"}, {"E", "radio_elt"}]),
        alr_flags("S", params, [{"P", "survival_polar"}, {"D", "survival_desert"}, {"M", "survival_maritime"}, {"J", "survival_jungle"}]),
        alr_flags("J", params, [{"L", "jackets_light"}, {"F", "jackets_fluores"}, {"U", "jackets_uhf"}, {"V", "jackets_vhf"}]),
        alr_dinghy(params),
        alr_slash("A", params, "aircraft_colour"),
        alr_slash("N", params, "remarks"),
        alr_slash("C", params, "pilot")
      ]
      |> Enum.reject(&(&1 == ""))

    if fields == [], do: nil, else: "-#{Enum.join(fields, " ")}"
  end

  defp alr_item20(params) do
    case up(params, "sar_info") do
      "" -> nil
      value -> "-#{value}"
    end
  end

  defp alr_slash(label, params, key) do
    case up(params, key) do
      "" -> ""
      value -> "#{label}/#{value}"
    end
  end

  defp alr_dinghy(params) do
    number = up(params, "dinghy_number")
    capacity = up(params, "dinghy_capacity")
    cover = if checked?(params, "dinghy_cover"), do: "C", else: ""
    colour = up(params, "dinghy_colour")

    [number, capacity, cover, colour]
    |> Enum.reject(&(&1 == ""))
    |> case do
      [] -> ""
      values -> "D/#{Enum.join(values, " ")}"
    end
  end

  defp alr_flags(label, params, flags) do
    value =
      flags
      |> Enum.filter(fn {_flag, key} -> checked?(params, key) end)
      |> Enum.map_join("", fn {flag, _key} -> flag end)

    if value == "", do: "", else: "#{label}/#{value}"
  end

  defp maybe_wrap_aftn(body, params) do
    if text(params, "addresses") == "" and text(params, "originator") == "" do
      body
    else
      aftn_packet(body, params)
    end
  end

  defp aftn_packet(body, params) do
    filing_time = filing_time(params)

    [
      <<1>>,
      up(params, "transmission_id", "ACR0016"),
      " ",
      filing_time,
      "\r\n",
      up(params, "priority", "FF"),
      " ",
      destination_line(params),
      "\r\n",
      originator_line(filing_time, params),
      "\r\n",
      <<2>>,
      body,
      "\r\n",
      <<11>>,
      <<3>>
    ]
    |> IO.iodata_to_binary()
  end

  defp filing_time(params) do
    case digits(params, "filing_time") do
      "" -> Calendar.strftime(DateTime.utc_now(), "%d%H%M")
      value -> value
    end
  end

  defp originator_line(filing_time, params) do
    reference = up(params, "originator_reference")
    bell = if checked?(params, "bell"), do: [<<7>>, <<7>>], else: []
    reference_part = if reference == "", do: [], else: [" ", reference]

    [
      filing_time,
      " ",
      up(params, "originator"),
      bell,
      reference_part
    ]
  end

  defp item18(params) do
    params
    |> text("item18")
    |> case do
      "" -> nil
      value -> "-#{upcase(value)}"
    end
  end

  defp up(params, key, default \\ "") do
    params
    |> text(key, default)
    |> upcase()
  end

  defp digits(params, key) do
    params
    |> text(key)
    |> String.replace(~r/\D/, "")
  end

  defp checked?(params, key), do: text(params, key) in ~w(1 true TRUE on ON yes YES)

  defp text(params, key, default \\ "") do
    params
    |> Map.get(key, default)
    |> to_string()
    |> String.trim()
  end

  defp upcase(value), do: String.upcase(value)

  defp require_fields(params, fields) do
    fields
    |> Enum.reduce([], fn field, errors ->
      if text(params, field) == "", do: ["#{field} wajib diisi" | errors], else: errors
    end)
    |> Enum.reverse()
  end

  defp require_when_empty(errors, _params, _field, _message) when errors != [], do: errors

  defp require_when_empty(errors, params, field, message) do
    if text(params, field) == "", do: [message | errors], else: errors
  end

  defp validate_common_flight(errors, _params) when errors != [], do: errors

  defp validate_common_flight(errors, params) do
    errors
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_format(params, "departure", ~r/^[A-Z]{4}$/, "Departure harus ICAO 4 huruf")
    |> validate_format(params, "destination", ~r/^[A-Z]{4}$/, "Destination harus ICAO 4 huruf")
    |> validate_format(params, "departure_time", ~r/^\d{4}$/, "Time harus HHMM 4 digit")
  end

  defp validate_aftn_header(errors, _params) when errors != [], do: errors

  defp validate_aftn_header(errors, params) do
    if text(params, "addresses") == "" and text(params, "originator") == "" do
      errors
    else
      errors
      |> require_when_empty(params, "addresses", "AFTN destination/address wajib diisi")
      |> require_when_empty(params, "originator", "Originator wajib diisi")
      |> validate_destinations(params)
      |> validate_format(params, "originator", ~r/^[A-Z]{8}$/, "Originator harus 8 huruf AFTN")
      |> validate_optional_format(params, "filing_time", ~r/^\d{6}$/, "Filing Time harus DDHHMM 6 digit")
    end
  end

  defp validate_format(errors, _params, _field, _regex, _message) when errors != [], do: errors

  defp validate_format(errors, params, field, regex, message) do
    value = params |> text(field) |> upcase()
    if Regex.match?(regex, value), do: errors, else: [message | errors]
  end

  defp validate_destinations(errors, _params) when errors != [], do: errors

  defp validate_destinations(errors, params) do
    destinations = destinations(params)

    cond do
      destinations == [] ->
        ["Destination wajib diisi" | errors]

      Enum.all?(destinations, &Regex.match?(~r/^[A-Z]{8}$/, &1)) ->
        errors

      true ->
        ["Destination harus alamat AFTN 8 huruf, boleh banyak dipisah spasi/koma/baris baru" | errors]
    end
  end

  defp destination_line(params), do: params |> destinations() |> Enum.join(" ")

  defp destinations(params) do
    address_fields =
      1..21
      |> Enum.map(fn index -> text(params, "address_#{index}") |> upcase() end)
      |> Enum.reject(&(&1 == ""))

    if address_fields == [] do
      params
      |> text(destination_key(params))
      |> upcase()
      |> String.split(~r/[\s,;]+/, trim: true)
    else
      address_fields
    end
  end

  defp destination_key(params) do
    cond do
      text(params, "addresses") != "" -> "addresses"
      text(params, "aftn_destination") != "" -> "aftn_destination"
      true -> "destination"
    end
  end

  defp validate_optional_format(errors, _params, _field, _regex, _message) when errors != [], do: errors

  defp validate_optional_format(errors, params, field, regex, message) do
    value = params |> text(field) |> upcase()

    cond do
      value == "" -> errors
      Regex.match?(regex, value) -> errors
      true -> [message | errors]
    end
  end
end
