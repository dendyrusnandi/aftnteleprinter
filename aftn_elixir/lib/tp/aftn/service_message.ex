defmodule Tp.Aftn.ServiceMessage do
  import Ecto.Query, only: [from: 2]

  alias Tp.Aftn.{Builder, Message}
  alias Tp.Repo

  @svc_traf ~r/^SVC\s+TRAF\b/i
  @svc_qta_rpt1 ~r/^SVC\s+QTA\s+RPT\s+([A-Z]{3,4})(\d{3,4})\s*$/i
  @svc_qta_rpt2 ~r/^SVC\s+QTA\s+RPT\s+([A-Z]{3,4})(\d{3,4})-(\d{3,4})\s*$/i
  @svc_qta_rpt3 ~r/^SVC\s+QTA\s+RPT\s+([A-Z]{3,4})(\d{3,4})\/(\d{3,4})\s*$/i
  @svc_qta_rpx ~r/^SVC\s+QTA\s+RPX\b/i
  @svc_qta_mis1 ~r/^SVC\s+QTA\s+MIS\s+([A-Z]{3,4})(\d{3,4})\s*$/i
  @svc_qta_mis2 ~r/^SVC\s+QTA\s+MIS\s+([A-Z]{3,4})(\d{3,4})-(\d{3,4})\s*$/i

  def maybe_handle(%Message{} = message) do
    setting = Tp.Settings.get_or_default()

    if setting.svc_msg_generation do
      handle_svc(message, setting)
    else
      :ignored
    end
  rescue
    _error -> :error
  end

  defp handle_svc(message, setting) do
    text = message.raw_text |> to_string() |> String.trim()
    reply_address = reply_address(message, setting)

    cond do
      Regex.match?(@svc_traf, text) ->
        send_traf(setting, reply_address)

      match = Regex.run(@svc_qta_rpt1, text) ->
        [_, cid, seq] = match
        send_qta_one(text, cid, seq, setting, reply_address)

      match = Regex.run(@svc_qta_rpt2, text) ->
        [_, cid, first_seq, last_seq] = match
        send_qta_range(text, cid, first_seq, last_seq, setting, reply_address)

      match = Regex.run(@svc_qta_rpt3, text) ->
        [_, cid, first_seq, second_seq] = match
        send_qta_pair(text, cid, first_seq, second_seq, setting, reply_address)

      Regex.match?(@svc_qta_rpx, text) ->
        :handled

      match = Regex.run(@svc_qta_mis1, text) ->
        [_, cid, seq] = match
        send_qta_one(text, cid, seq, setting, reply_address)

      match = Regex.run(@svc_qta_mis2, text) ->
        [_, cid, first_seq, last_seq] = match
        send_qta_range(text, cid, first_seq, last_seq, setting, reply_address)

      true ->
        :ignored
    end
  end

  defp send_traf(setting, reply_address) do
    today = Date.utc_today()
    outbound = today_outbound_messages()
    inbound = Tp.Aftn.list_messages(direction: "inbound", date_from: Date.to_iso8601(today), date_to: Date.to_iso8601(today), limit: 1000)

    sent_count = length(outbound) + 1
    received_count = length(inbound)
    sent_chars = outbound |> Enum.map(&byte_size(to_string(&1.raw_text || ""))) |> Enum.sum()
    received_chars = inbound |> Enum.map(&byte_size(to_string(&1.raw_text || ""))) |> Enum.sum()
    next_tx = next_sequence(setting.tseq)
    next_rx = received_count

    text =
      [
        "STAT TRAF",
        "CCT     LR     LS      SENT(M:C)          RCV(M:C)",
        "#{pad3(1)}     #{pad4(next_rx)}   #{pad4(next_tx)}    #{pad4(sent_count)} #{sent_chars |> Integer.to_string() |> String.pad_leading(10, " ")}    #{pad4(received_count)} #{received_chars |> Integer.to_string() |> String.pad_leading(10, " ")}"
      ]
      |> Enum.join("\n")

    send_svc_text("FF", [reply_address], setting.originator, text, setting)
  end

  defp send_qta_one(req_text, cid, seq, setting, reply_address) do
    rows = find_outgoing(cid, seq)

    info =
      case rows do
        [] ->
          "NOT FOUND #{pad4(seq)}"

        rows ->
          Enum.each(rows, &resend_duplicate(&1, setting, reply_address))
          message_status(List.first(rows), seq)
      end

    text = "RTRVD FOR CCT #{setting.cid}#{pad4(seq)}\n#{info}\nREF YOURS \n#{req_text}"
    send_svc_text("FF", [reply_address], setting.originator, text, setting)
  end

  defp send_qta_range(req_text, cid, first_seq, last_seq, setting, reply_address) do
    range = seq_range(first_seq, last_seq)

    {executed, not_found, channel_checks} =
      Enum.reduce(range, {[], [], []}, fn seq, {executed, not_found, channel_checks} ->
        rows = find_outgoing(cid, seq)

        cond do
          rows == [] ->
            {executed, [seq | not_found], channel_checks}

          channel_check_row?(List.first(rows)) ->
            {executed, not_found, [seq | channel_checks]}

          true ->
            Enum.each(rows, &resend_duplicate(&1, setting, reply_address))
            {[seq | executed], not_found, channel_checks}
        end
      end)

    info =
      ""
      |> maybe_append_line(Enum.reverse(executed), "EXECUTED")
      |> maybe_append_line(Enum.reverse(not_found), "NOT FOUND")
      |> maybe_append_line(Enum.reverse(channel_checks), "CHNL CHECKS")

    text = "RTRVD FOR CCT #{setting.cid}#{join_pad4(range)}\n#{info}REF YOURS \n#{req_text}"
    send_svc_text("FF", [reply_address], setting.originator, text, setting)
  end

  defp send_qta_pair(req_text, cid, first_seq, second_seq, setting, reply_address) do
    rows = [to_int(first_seq), to_int(second_seq)]

    {executed, not_found, channel_checks} =
      Enum.reduce(rows, {[], [], []}, fn seq, {executed, not_found, channel_checks} ->
        messages = find_outgoing(cid, seq)

        cond do
          messages == [] ->
            {executed, [seq | not_found], channel_checks}

          channel_check_row?(List.first(messages)) ->
            {executed, not_found, [seq | channel_checks]}

          true ->
            Enum.each(messages, &resend_duplicate(&1, setting, reply_address))
            {[seq | executed], not_found, channel_checks}
        end
      end)

    info =
      ""
      |> maybe_append_line(Enum.reverse(executed), "EXECUTED")
      |> maybe_append_line(Enum.reverse(not_found), "NOT FOUND")
      |> maybe_append_line(Enum.reverse(channel_checks), "CHNL CHECKS")

    text = "RTRVD FOR CCT #{setting.cid}#{pad4(first_seq)} #{pad4(second_seq)} \n#{info}REF YOURS \n#{req_text}"
    send_svc_text("FF", [reply_address], setting.originator, text, setting)
  end

  defp resend_duplicate(%Message{} = message, setting, reply_address) do
    destinations =
      message.destinations
      |> List.wrap()
      |> Enum.reject(&blank?/1)
      |> case do
        [] -> [reply_address]
        values -> values
      end

    text = append_dupe(message.raw_text || "")
    send_svc_text(message.priority || "FF", destinations, message.originator || setting.originator, text, setting, message.filing_time)
  end

  defp send_svc_text(priority, destinations, originator, text, setting, filing_time \\ nil) do
    current_setting = Tp.Settings.get_or_default()

    params = %{
      "transmission_id" => "#{current_setting.cid}#{current_setting.tseq}",
      "priority" => priority || "FF",
      "addresses" => destinations |> List.wrap() |> Enum.reject(&blank?/1) |> Enum.join(" "),
      "filing_time" => filing_time || Calendar.strftime(DateTime.utc_now(), "%d%H%M"),
      "originator" => originator || setting.originator,
      "message" => text
    }

    raw = Builder.build("AFTN_FREE", params)
    attrs = %{filed_by: "SVC", udp_target_host: current_setting.destination_ip_address, udp_target_port: current_setting.port}

    case Tp.Aftn.transmit(raw, attrs) do
      {:ok, result} -> {:ok, result}
      {:error, _reason} -> Tp.Aftn.enqueue_transmit(raw, Map.put(attrs, :next_attempt_at, DateTime.utc_now()))
    end
  end

  defp find_outgoing(cid, seq) do
    seq = pad4(seq)
    cid = cid |> to_string() |> String.upcase()
    {from_time, to_time} = today_range()

    Repo.all(
      from m in Message,
        where: m.direction == :outbound,
        where: m.cid == ^cid,
        where: m.sequence_no == ^seq,
        where: m.inserted_at >= ^from_time and m.inserted_at <= ^to_time,
        order_by: [asc: m.inserted_at, asc: m.id]
    )
  end

  defp today_outbound_messages do
    {from_time, to_time} = today_range()

    Repo.all(
      from m in Message,
        where: m.direction == :outbound,
        where: m.inserted_at >= ^from_time and m.inserted_at <= ^to_time
    )
  end

  defp today_range do
    today = Date.utc_today()
    {DateTime.new!(today, ~T[00:00:00], "Etc/UTC"), DateTime.new!(today, ~T[23:59:59], "Etc/UTC")}
  end

  defp reply_address(message, setting) do
    cond do
      valid_address?(message.originator) -> message.originator
      valid_address?(setting.prev_st) -> setting.prev_st
      true -> setting.originator
    end
  end

  defp message_status(%Message{} = message, seq) do
    cond do
      channel_check_row?(message) -> "CHNL CHECKS #{pad4(seq)}"
      to_string(message.status) == "canceled" -> "PREVIOSLY CNCLD #{pad4(seq)}"
      true -> "EXECUTED #{pad4(seq)}"
    end
  end

  defp channel_check_row?(%Message{raw_text: text}) do
    text = text |> to_string() |> String.trim()
    String.contains?(text, "CH") and String.length(text) < 13
  end

  defp append_dupe(text) do
    text = to_string(text)
    if String.ends_with?(text, "\r\n\v"), do: text <> "DUPE", else: text <> "\nDUPE"
  end

  defp maybe_append_line(info, [], _label), do: info
  defp maybe_append_line(info, rows, label), do: info <> "#{label} #{join_pad4(rows)}\n"

  defp seq_range(first_seq, last_seq) do
    first = to_int(first_seq)
    last = to_int(last_seq)
    if first <= last, do: Enum.to_list(first..last), else: Enum.to_list(last..first)
  end

  defp join_pad4(rows), do: rows |> Enum.map(&pad4/1) |> Enum.join(" ")
  defp pad3(value), do: value |> to_int() |> Integer.to_string() |> String.pad_leading(3, "0")
  defp pad4(value), do: value |> to_int() |> Integer.to_string() |> String.pad_leading(4, "0")
  defp next_sequence(value), do: value |> to_int() |> Kernel.+(1)

  defp to_int(value) when is_integer(value), do: value

  defp to_int(value) do
    case Integer.parse(to_string(value || "0")) do
      {number, _} -> number
      :error -> 0
    end
  end

  defp valid_address?(value), do: Regex.match?(~r/^[A-Z]{8}$/, to_string(value || ""))
  defp blank?(value), do: String.trim(to_string(value || "")) == ""
end
