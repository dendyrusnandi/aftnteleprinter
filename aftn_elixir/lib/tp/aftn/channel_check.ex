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
      tx_id       = "#{setting.cid}#{current_seq}"
      raw         = <<1>> <> tx_id <> " " <> filing_time <> "\r\n" <> <<2>> <> "CH\r\n" <> <<3>>
      target      = "#{setting.destination_ip_address}:#{setting.port}"

      queue_attrs = %{
        filed_by:        "SYSTEM",
        next_attempt_at: DateTime.utc_now(),
        udp_target_host: setting.destination_ip_address,
        udp_target_port: setting.port
      }

      link = Tp.LinkMonitor.status()

      if link.state == :up do
        transmit_attrs = %{
          filed_by:        "SYSTEM",
          udp_target_host: setting.destination_ip_address,
          udp_target_port: setting.port
        }

        case Aftn.transmit(raw, transmit_attrs) do
          {:ok, _} ->
            Logger.info("Channel check CH sent to #{target} seq=#{tx_id}")

          {:error, reason} ->
            Logger.warning("Channel check CH transmit failed (#{inspect(reason)}), queuing for retry")
            queue_channel_check(raw, queue_attrs, tx_id, target)
        end
      else
        Logger.info("Channel check CH: link #{link.state}, queuing for retry seq=#{tx_id}")
        queue_channel_check(raw, queue_attrs, tx_id, target)
      end
    end
  rescue
    error ->
      Logger.error("Channel check failed: #{Exception.message(error)}")
  end

  defp queue_channel_check(raw, attrs, tx_id, target) do
    case Aftn.enqueue_transmit(raw, attrs) do
      {:ok, _} ->
        Logger.info("Channel check CH queued for retry seq=#{tx_id} target=#{target}")
        Tp.Aftn.OutgoingQueue.kick()

      {:error, reason} ->
        Logger.error("Channel check CH queue failed seq=#{tx_id}: #{inspect(reason)}")
    end
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
