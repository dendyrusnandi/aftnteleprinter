defmodule Tp.Udp.Monitor do
  use GenServer

  @limit 10

  def start_link(opts \\ []) do
    GenServer.start_link(__MODULE__, opts, name: __MODULE__)
  end

  def record(raw, meta \\ %{}, kind \\ "UDP") do
    item = %{
      kind: kind,
      source_ip: Map.get(meta, :source_ip) || Map.get(meta, "source_ip"),
      source_port: Map.get(meta, :source_port) || Map.get(meta, "source_port"),
      raw: raw,
      time: DateTime.utc_now()
    }

    GenServer.cast(__MODULE__, {:record, item})
  catch
    :exit, {:noproc, _} -> :ok
  end

  def drain do
    GenServer.call(__MODULE__, :drain)
  catch
    :exit, {:noproc, _} -> []
  end

  @impl true
  def init(_opts), do: {:ok, []}

  @impl true
  def handle_cast({:record, item}, items) do
    {:noreply, [item | items] |> Enum.take(@limit)}
  end

  @impl true
  def handle_call(:drain, _from, items) do
    {:reply, Enum.reverse(items), []}
  end
end
