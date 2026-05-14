defmodule Tp.Aftn.OutgoingQueue do
  use GenServer
  require Logger

  alias Tp.Aftn

  @interval_ms 2_000

  def start_link(opts \\ []) do
    GenServer.start_link(__MODULE__, opts, name: __MODULE__)
  end

  def kick do
    GenServer.cast(__MODULE__, :drain)
  end

  @impl true
  def init(_opts) do
    schedule()
    {:ok, %{}}
  end

  @impl true
  def handle_cast(:drain, state) do
    drain()
    {:noreply, state}
  end

  @impl true
  def handle_info(:tick, state) do
    drain()
    schedule()
    {:noreply, state}
  end

  defp drain do
    20
    |> Aftn.due_outgoing()
    |> Enum.each(fn message ->
      case Aftn.send_queued(message) do
        {:ok, updated} ->
          Logger.info("Outgoing message #{updated.id} status=#{updated.status} attempts=#{updated.transmit_attempts}")

        {:error, changeset} ->
          Logger.error("Failed to update outgoing message #{message.id}: #{inspect(changeset.errors)}")
      end
    end)
  rescue
    error ->
      Logger.debug("OutgoingQueue drain skipped: #{Exception.message(error)}")
  end

  defp schedule do
    Process.send_after(self(), :tick, @interval_ms)
  end
end

