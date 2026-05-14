defmodule Tp.Udp.Listener do
  use GenServer
  require Logger

  alias Tp.Aftn

  @retry_initial_ms 2_000
  @retry_max_ms 30_000

  def start_link(opts \\ []) do
    GenServer.start_link(__MODULE__, opts, name: __MODULE__)
  end

  def set_port(port) when is_integer(port) and port > 0 and port <= 65_535 do
    GenServer.call(__MODULE__, {:set_port, port})
  catch
    :exit, {:noproc, _} -> {:error, :not_started}
  end

  @impl true
  def init(_opts) do
    desired_port = Tp.Settings.udp_receive_port()

    state = %{
      socket: nil,
      port: nil,
      desired_port: desired_port,
      retry_ms: @retry_initial_ms,
      retry_ref: nil
    }

    {:ok, ensure_socket(state)}
  end

  @impl true
  def handle_call({:set_port, port}, _from, %{desired_port: port} = state) do
    {:reply, {:ok, port}, ensure_socket(%{state | desired_port: port})}
  end

  def handle_call({:set_port, port}, _from, state) do
    state = %{state | desired_port: port}

    case open_socket(port) do
      {:ok, socket} ->
        close_socket(state.socket)
        Logger.info("AFTN UDP listener moved to #{port}")
        {:reply, {:ok, port}, connected_state(state, socket, port)}

      {:error, reason} ->
        Logger.warning("Failed to move AFTN UDP listener to #{port}: #{inspect(reason)}. Auto-retry scheduled.")
        {:reply, {:ok, port}, schedule_retry(disconnected_state(state))}
    end
  end

  @impl true
  def handle_info({:udp, _socket, ip, source_port, payload}, state) do
    source_ip = ip |> :inet.ntoa() |> to_string()
    meta = %{source_ip: source_ip, source_port: source_port}
    kind = if Tp.Aftn.Parser.aftn?(payload), do: "AFTN", else: "UDP"

    Tp.Udp.Monitor.record(payload, meta, kind)

    case Aftn.ingest_udp(payload, meta) do
      {:ok, _record} -> :ok
      {:error, reason} -> Logger.error("Failed to persist UDP payload: #{inspect(reason)}")
    end

    {:noreply, state}
  end

  @impl true
  def handle_info({:udp_closed, socket}, %{socket: socket} = state) do
    Logger.warning("AFTN UDP listener socket on #{state.port || state.desired_port} closed. Auto-retry scheduled.")
    {:noreply, schedule_retry(disconnected_state(state))}
  end

  @impl true
  def handle_info({:udp_error, socket, reason}, %{socket: socket} = state) do
    Logger.error("AFTN UDP listener socket error on #{state.port || state.desired_port}: #{inspect(reason)}")
    close_socket(socket)
    {:noreply, schedule_retry(disconnected_state(state))}
  end

  @impl true
  def handle_info(:retry_open, state) do
    {:noreply, ensure_socket(%{state | retry_ref: nil})}
  end

  @impl true
  def terminate(_reason, %{socket: socket}), do: close_socket(socket)

  defp open_socket(port) do
    :gen_udp.open(port, [:binary, active: true, reuseaddr: true])
  end

  defp ensure_socket(%{socket: socket, desired_port: desired_port, port: port} = state)
       when not is_nil(socket) and port == desired_port do
    state
  end

  defp ensure_socket(state) do
    port = state.desired_port

    case open_socket(port) do
      {:ok, socket} ->
        close_socket(state.socket)
        Logger.info("AFTN UDP listener opened on #{port}")
        connected_state(state, socket, port)

      {:error, reason} ->
        Logger.warning("AFTN UDP listener failed to open on #{port}: #{inspect(reason)}. Retrying in #{state.retry_ms} ms.")
        schedule_retry(disconnected_state(state))
    end
  end

  defp connected_state(state, socket, port) do
    cancel_retry(state.retry_ref)

    %{
      state
      | socket: socket,
        port: port,
        desired_port: port,
        retry_ms: @retry_initial_ms,
        retry_ref: nil
    }
  end

  defp disconnected_state(state) do
    %{
      state
      | socket: nil,
        port: nil
    }
  end

  defp schedule_retry(%{retry_ref: retry_ref, retry_ms: retry_ms} = state) do
    cancel_retry(retry_ref)
    ref = Process.send_after(self(), :retry_open, retry_ms)
    next_retry_ms = min(retry_ms * 2, @retry_max_ms)
    %{state | retry_ref: ref, retry_ms: next_retry_ms}
  end

  defp cancel_retry(nil), do: :ok

  defp cancel_retry(ref) do
    Process.cancel_timer(ref)
    :ok
  end

  defp close_socket(nil), do: :ok

  defp close_socket(socket) do
    :gen_udp.close(socket)
    :ok
  catch
    :error, _reason -> :ok
  end
end
