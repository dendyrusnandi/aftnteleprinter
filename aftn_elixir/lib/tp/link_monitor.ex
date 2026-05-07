defmodule Tp.LinkMonitor do
  use GenServer

  @interval_ms 5_000

  def start_link(opts \\ []) do
    GenServer.start_link(__MODULE__, opts, name: __MODULE__)
  end

  def status do
    GenServer.call(__MODULE__, :status, 1_000)
  catch
    :exit, _reason -> default_status()
  end

  @impl true
  def init(_opts) do
    state = default_status()
    send(self(), :check)
    {:ok, state}
  end

  @impl true
  def handle_call(:status, _from, state), do: {:reply, state, state}

  @impl true
  def handle_info(:check, _state) do
    state = check_destination()
    Process.send_after(self(), :check, @interval_ms)
    {:noreply, state}
  end

  defp check_destination do
    target = safe_target()
    host = to_string(target.host || "")

    result =
      if host == "" do
        {:down, "destination kosong"}
      else
        ping(host)
      end

    %{
      host: host,
      port: target.port,
      state: elem(result, 0),
      reason: elem(result, 1),
      checked_at: DateTime.utc_now()
    }
  end

  defp ping(host) do
    case System.cmd("ping", ["-c", "1", "-W", "1", host], stderr_to_stdout: true) do
      {_output, 0} -> {:up, "established"}
      {_output, _code} -> {:down, "rto"}
    end
  rescue
    _error -> {:down, "ping unavailable"}
  end

  defp safe_target do
    Tp.Settings.udp_target()
  rescue
    _error -> %{host: nil, port: nil}
  end

  defp default_status do
    %{host: nil, port: nil, state: :down, reason: "checking", checked_at: nil}
  end
end
