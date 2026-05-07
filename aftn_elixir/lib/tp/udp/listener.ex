defmodule Tp.Udp.Listener do
  use GenServer
  require Logger

  alias Tp.Aftn

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
    port = Tp.Settings.udp_receive_port()

    case open_socket(port) do
      {:ok, socket} ->
        Logger.info("AFTN UDP listener opened on #{port}")
        {:ok, %{socket: socket, port: port}}

      {:error, reason} ->
        {:stop, reason}
    end
  end

  @impl true
  def handle_call({:set_port, port}, _from, %{port: port} = state), do: {:reply, {:ok, port}, state}

  def handle_call({:set_port, port}, _from, %{socket: socket} = state) do
    case open_socket(port) do
      {:ok, socket} ->
        :gen_udp.close(state.socket)
        Logger.info("AFTN UDP listener moved to #{port}")
        {:reply, {:ok, port}, %{state | socket: socket, port: port}}

      {:error, reason} ->
        Logger.error("Failed to move AFTN UDP listener to #{port}: #{inspect(reason)}")
        {:reply, {:error, reason}, %{state | socket: socket}}
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
  def terminate(_reason, %{socket: socket}), do: :gen_udp.close(socket)

  defp open_socket(port) do
    :gen_udp.open(port, [:binary, active: true, reuseaddr: true])
  end
end
