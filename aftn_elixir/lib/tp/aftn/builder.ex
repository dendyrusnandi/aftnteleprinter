defmodule Tp.Aftn.Builder do
  @basic_flight_types ~w(DLA CHG CNL DEP RQP RQS)
  @fpl_like_types ~w(CPL SPL)
  @supported_types ~w(FREE AFTN_FREE FPL DLA CHG CNL DEP ARR CDN CPL EST ACP LAM RQP RQS SPL RCF ALR)

  def validate("FREE", params), do: require_fields(params, ["message"])

  def validate("AFTN_FREE", params) do
    params
    |> require_fields(["transmission_id", "priority", "originator", "message"])
    |> validate_format(params, "transmission_id", ~r/^[A-Z]{3,4}\d{3,4}$/, "TX ID harus format CID+SEQ, contoh RCJ0001 atau ACR0016")
    |> validate_optional_format(params, "filing_time", ~r/^\d{6}$/, "Filing Time harus DDHHMM 6 digit")
    |> validate_format(params, "priority", ~r/^(SS|DD|FF|GG|KK)$/, "Priority harus SS/DD/FF/GG/KK")
    |> validate_destinations(params)
    |> validate_format(params, "originator", ~r/^[A-Z]{4}[A-Z]{4}$/, "Originator harus 8 huruf AFTN")
  end

  def validate("FPL", params) do
    params
    |> require_fields([
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
      "dof"
    ])
    |> validate_alr_conditionals(params)
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_optional_format(params, "message_number", ~r/^[A-Z0-9\/]{0,12}$/, "Message number maksimal 12 huruf/angka atau /")
    |> validate_format(params, "aircraft_type", ~r/^[A-Z0-9]{2,4}$/, "Type aircraft harus 2-4 huruf/angka")
    |> validate_format(params, "departure", ~r/^[A-Z]{4}$/, "Departure harus ICAO 4 huruf")
    |> validate_format(params, "departure_time", ~r/^\d{4}$/, "Departure time harus HHMM 4 digit")
    |> validate_format(params, "destination", ~r/^[A-Z]{4}$/, "Destination harus ICAO 4 huruf")
    |> validate_format(params, "eet", ~r/^\d{4}$/, "EET harus HHMM 4 digit")
    |> validate_optional_format(params, "alternate", ~r/^[A-Z]{4}$/, "Alternate harus ICAO 4 huruf")
    |> validate_optional_format(params, "second_alternate", ~r/^[A-Z]{4}$/, "Second alternate harus ICAO 4 huruf")
    |> validate_optional_format(params, "ssr_code", ~r/^\d{4}$/, "SSR Code harus 4 angka")
    |> validate_format(params, "dof", ~r/^\d{6}$/, "DOF harus YYMMDD 6 digit")
    |> validate_aftn_header(params)
  end

  def validate(type, params) when type in @fpl_like_types do
    params
    |> require_fields([
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
      "dof"
    ])
    |> validate_alr_conditionals(params)
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_optional_format(params, "message_number", ~r/^[A-Z0-9\/]{0,12}$/, "Message number maksimal 12 huruf/angka atau /")
    |> validate_format(params, "aircraft_type", ~r/^[A-Z0-9]{2,4}$/, "Type aircraft harus 2-4 huruf/angka")
    |> validate_format(params, "departure", ~r/^[A-Z]{4}$/, "Departure harus ICAO 4 huruf")
    |> validate_format(params, "departure_time", ~r/^\d{4}$/, "Departure time harus HHMM 4 digit")
    |> validate_format(params, "destination", ~r/^[A-Z]{4}$/, "Destination harus ICAO 4 huruf")
    |> validate_format(params, "eet", ~r/^\d{4}$/, "EET harus HHMM 4 digit")
    |> validate_optional_format(params, "alternate", ~r/^[A-Z]{4}$/, "Alternate harus ICAO 4 huruf")
    |> validate_optional_format(params, "second_alternate", ~r/^[A-Z]{4}$/, "Second alternate harus ICAO 4 huruf")
    |> validate_optional_format(params, "ssr_code", ~r/^\d{4}$/, "SSR Code harus 4 angka")
    |> validate_format(params, "dof", ~r/^\d{6}$/, "DOF harus YYMMDD 6 digit")
    |> validate_aftn_header(params)
  end

  def validate("CHG", params) do
    validate_mini_flight(params, false)
    |> require_when_empty(params, "item22", "Item 22 wajib diisi")
  end

  def validate(type, params) when type in ~w(CNL) do
    validate_mini_flight(params, false)
  end

  def validate(type, params) when type in ~w(DLA DEP) do
    validate_mini_flight(params, true)
  end

  def validate(type, params) when type in @basic_flight_types do
    validate_basic_flight(params)
  end

  defp validate_mini_flight(params, requires_time?) do
    fields = ["aircraft_id", "departure", "destination"] ++ if(requires_time?, do: ["departure_time"], else: [])

    params
    |> require_fields(fields)
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_format(params, "departure", ~r/^[A-Z]{4}$/, "Departure harus ICAO 4 huruf")
    |> validate_format(params, "destination", ~r/^[A-Z]{4}$/, "Destination harus ICAO 4 huruf")
    |> validate_optional_format(params, "departure_time", ~r/^\d{4}$/, "Time harus HHMM 4 digit")
    |> validate_optional_format(params, "dof", ~r/^\d{6}$/, "DOF harus YYMMDD 6 digit")
    |> validate_aftn_header(params)
  end

  defp validate_basic_flight(params) do
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

  def validate("ACP", params) do
    params
    |> require_fields(["aircraft_id", "departure", "destination"])
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_format(params, "departure", ~r/^[A-Z]{4}$/, "Departure harus ICAO 4 huruf")
    |> validate_format(params, "destination", ~r/^[A-Z]{4}$/, "Destination harus ICAO 4 huruf")
    |> validate_optional_format(params, "ssr_code", ~r/^\d{4}$/, "SSR Code harus 4 angka")
    |> validate_aftn_header(params)
  end

  def validate(type, params) when type in ~w(ARR) do
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
    |> require_fields(["reference_data"])
    |> validate_aftn_header(params)
  end

  def validate("RCF", params) do
    params
    |> require_fields(["aircraft_id", "radio_failure"])
    |> validate_rcf_conditionals(params)
    |> validate_format(params, "aircraft_id", ~r/^[A-Z0-9]{2,7}$/, "Aircraft harus 2-7 huruf/angka")
    |> validate_optional_format(params, "message_number", ~r/^[A-Z0-9\/]{0,12}$/, "Message number maksimal 12 huruf/angka atau /")
    |> validate_optional_format(params, "reference_data", ~r/^[A-Z0-9\/]{0,12}$/, "Reference data maksimal 12 huruf/angka atau /")
    |> validate_optional_format(params, "ssr_code", ~r/^\d{4}$/, "SSR Code harus 4 angka")
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
    |> validate_alr_conditionals(params)
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
    |> fpl_body(type)
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

  def build("CDN", params), do: basic_flight_body("CDN", params, "-#{up(params, "item22")}") |> maybe_wrap_aftn(params)

  def build("EST", params) do
    [
      "(EST#{up(params, "message_number")}#{up(params, "reference_data")}",
      flight_item7(params),
      "-#{up(params, "departure")}#{digits(params, "departure_time")}",
      String.trim("-#{up(params, "fix")}#{digits(params, "fix_time")} #{up(params, "estimate_level")} #{up(params, "crossing_data")}"),
      "-#{up(params, "destination")}#{digits(params, "eet")})"
    ]
    |> Enum.reject(&(&1 in ["", "-"]))
    |> Enum.join("")
    |> maybe_wrap_aftn(params)
  end

  def build("ARR", params) do
    item3 = "ARR" <> up(params, "message_number") <> up(params, "reference_data")
    aerodrome = if text(params, "arrival_aerodrome") == "", do: "", else: " #{up(params, "arrival_aerodrome")}"

    [item3, flight_item7(params), "-#{up(params, "departure")}", "-#{up(params, "destination")}#{digits(params, "arrival_time")}#{aerodrome}"]
    |> Enum.reject(&(&1 in ["", "-"]))
    |> Enum.join("")
    |> then(&"(#{&1})")
    |> maybe_wrap_aftn(params)
  end

  def build(type, params) when type in ~w(ACP) do
    item3 = type <> up(params, "message_number") <> up(params, "reference_data")

    [item3, flight_item7(params), "-#{up(params, "departure")}", "-#{up(params, "destination")}"]
    |> Enum.reject(&(&1 in ["", "-"]))
    |> Enum.join("")
    |> then(&"(#{&1})")
    |> maybe_wrap_aftn(params)
  end

  def build("LAM", params), do: "(LAM#{up(params, "message_number")}#{up(params, "reference_data")})" |> maybe_wrap_aftn(params)

  def build("RCF", params) do
    params
    |> rcf_body()
    |> maybe_wrap_aftn(params)
  end

  def build("ALR", params) do
    params
    |> alr_body()
    |> maybe_wrap_aftn(params)
  end

  def build("FPL", params) do
    params
    |> fpl_body()
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

    item16 =
      [
        up(params, "destination"),
        digits(params, "eet"),
        up(params, "alternate"),
        up(params, "second_alternate")
      ]
      |> Enum.reject(&(&1 == ""))
      |> Enum.join(" ")

    [type <> up(params, "message_number") <> up(params, "reference_data"), flight_item7(params), "-#{up(params, "departure")}#{digits(params, "departure_time")}", "-#{item16}", dof, extra]
    |> Enum.reject(&(&1 in ["", "-"]))
    |> Enum.join("")
    |> then(&"(#{&1})")
  end

  defp flight_item7(params) do
    aircraft = up(params, "aircraft_id")
    mode = if text(params, "ssr_mode") == "", do: "", else: "/#{up(params, "ssr_mode")}"
    code = digits(params, "ssr_code")

    if aircraft == "" and mode == "" and code == "", do: "", else: "-#{aircraft}#{mode}#{code}"
  end

  defp rcf_body(params) do
    item3 = "RCF" <> up(params, "message_number") <> up(params, "reference_data")
    item7 = flight_item7(params)
    item21 = if text(params, "radio_failure") == "", do: "", else: "-#{up(params, "radio_failure")}"

    body =
      [item3 <> item7, item21]
      |> Enum.reject(&(&1 == ""))
      |> Enum.join("\r\n")

    "(#{body})"
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

  defp fpl_body(params, type \\ "FPL") do
    [
      "(#{type}#{up(params, "message_number")}",
      alr_item7(params),
      alr_item8(params),
      alr_item9_10(params),
      alr_item13(params),
      alr_item15(params),
      alr_item16(params),
      alr_item18(params),
      alr_item19(params),
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
      transmission_id(params),
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


  defp transmission_id(params) do
    tx_id = up(params, "transmission_id")

    if Regex.match?(~r/^[A-Z]{3,4}\d{3,4}$/, tx_id) do
      tx_id
    else
      default_transmission_id()
    end
  end

  defp default_transmission_id do
    setting = Tp.Settings.get_or_default()
    "#{setting.cid}#{setting.tseq}"
  rescue
    _error -> "RCJ0001"
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

  defp validate_rcf_conditionals(errors, _params) when errors != [], do: errors

  defp validate_rcf_conditionals(errors, params) do
    errors
    |> require_when(params, text(params, "ssr_mode") != "" and text(params, "ssr_code") == "", "SSR Code wajib diisi jika SSR Mode dipilih")
    |> require_when(params, text(params, "ssr_mode") == "" and text(params, "ssr_code") != "", "SSR Mode wajib dipilih jika SSR Code diisi")
  end

  defp validate_alr_conditionals(errors, _params) when errors != [], do: errors

  defp validate_alr_conditionals(errors, params) do
    errors
    |> require_when(params, text(params, "ssr_mode") != "" and text(params, "ssr_code") == "", "SSR Code wajib diisi jika SSR Mode dipilih")
    |> require_when(params, text(params, "ssr_mode") == "" and text(params, "ssr_code") != "", "SSR Mode wajib dipilih jika SSR Code diisi")
    |> require_when(params, params |> text("equipment") |> upcase() |> String.contains?("R"), "pbn", "PBN wajib diisi jika Equipment berisi R")
    |> require_when(params, (params |> text("aircraft_type") |> upcase()) == "ZZZZ", "typ", "TYP wajib diisi jika Type of Aircraft ZZZZ")
    |> require_when(params, (params |> text("departure") |> upcase()) in ["ZZZZ", "AFIL"], "dep_info", "DEP wajib diisi jika DEP AD ZZZZ/AFIL")
    |> require_when(params, (params |> text("destination") |> upcase()) == "ZZZZ", "dest_info", "DEST wajib diisi jika DEST AD ZZZZ")
    |> require_when(params, (params |> text("alternate") |> upcase()) == "ZZZZ", "altn_detail", "ALTN wajib diisi jika DEST ALTN AD ZZZZ")
    |> validate_alr_dinghy(params)
  end

  defp require_when(errors, _params, false, _message), do: errors
  defp require_when(errors, _params, true, message), do: [message | errors]

  defp require_when(errors, _params, false, _field, _message), do: errors

  defp require_when(errors, params, true, field, message) do
    if text(params, field) == "", do: [message | errors], else: errors
  end

  defp validate_alr_dinghy(errors, params) do
    has_dinghy_detail? =
      (["dinghy_number", "dinghy_capacity", "dinghy_colour"]
       |> Enum.any?(&(text(params, &1) != ""))) or checked?(params, "dinghy_cover")

    errors
    |> require_when(params, has_dinghy_detail? and text(params, "dinghy_number") == "", "Dinghy Number wajib diisi jika detail dinghy diisi")
    |> require_when(params, has_dinghy_detail? and text(params, "dinghy_capacity") == "", "Dinghy Capacity wajib diisi jika detail dinghy diisi")
  end

  defp validate_aftn_header(errors, _params) when errors != [], do: errors

  defp validate_aftn_header(errors, params) do
    if text(params, "addresses") == "" and text(params, "originator") == "" do
      errors
    else
      errors
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
