defmodule Tp.Aftn.ChannelCheck do
  use GenServer
  require Logger

  alias Tp.Aftn
  alias Tp.Settings

  @tick_ms 1_000
  @interval_minutes 20

  def start_link(opts \\ []) do
    GenServer.start_link(__MODULE__, opts, name: __MODULE__)
  end

  @impl true
  def init(_opts) do
    schedule_tick()
    {:ok, %{last_key: nil}}
  end

  @impl true
  def handle_info(:tick, state) do
    state = maybe_send_channel_check(state, DateTime.utc_now())
    schedule_tick()
    {:noreply, state}
  end

  defp schedule_tick do
    Process.send_after(self(), :tick, @tick_ms)
  end

  defp maybe_send_channel_check(state, now) do
    key = {now.year, now.month, now.day, now.hour, div(now.minute, @interval_minutes)}

    cond do
      rem(now.minute, @interval_minutes) != 0 ->
        state

      state.last_key == key ->
        state

      true ->
        send_channel_check(now)
        %{state | last_key: key}
    end
  end

  defp send_channel_check(now) do
    setting = Settings.get_or_default()

    if setting.channel_check do
      filing_time = Calendar.strftime(now, "%d%H%M")
      current_seq = current_sequence(setting.tseq, setting.digit_seq || 4)
      tx_id = "#{setting.cid}#{current_seq}"
      raw = <<1>> <> tx_id <> " " <> filing_time <> "\r\n" <> <<2>> <> "CH\r\n" <> <<3>>

      attrs = %{
        filed_by: "SYSTEM",
        message_type: "CH",
        cid: setting.cid,
        sequence_no: current_seq,
        filing_time: filing_time,
        raw_text: raw,
        udp_target_host: setting.destination_ip_address,
        udp_target_port: setting.port
      }

      case Aftn.transmit(raw, attrs) do
        {:ok, _result} ->
          Logger.info("Channel check CH sent to #{setting.destination_ip_address}:#{setting.port} seq=#{tx_id}")

        {:error, reason} ->
          Logger.error("Channel check CH failed to #{setting.destination_ip_address}:#{setting.port}: #{inspect(reason)}")
      end
    end
  rescue
    error ->
      Logger.error("Channel check failed: #{Exception.message(error)}")
  end

  defp current_sequence(value, digits) do
    digits = if is_integer(digits) and digits > 0, do: digits, else: 4

    value
    |> to_int()
    |> Integer.to_string()
    |> String.pad_leading(digits, "0")
  end

  defp to_int(value) when is_integer(value), do: value

  defp to_int(value) do
    case Integer.parse(to_string(value || "0")) do
      {number, _} -> number
      :error -> 0
    end
  end
end
