defmodule Tp.Aftn.Event do
  use Ecto.Schema
  import Ecto.Changeset

  schema "teleprinter_events" do
    field :kind, :string
    field :payload, :string
    field :source_ip, :string
    field :source_port, :integer

    timestamps(type: :utc_datetime_usec, updated_at: false)
  end

  def changeset(event, attrs) do
    event
    |> cast(attrs, [:kind, :payload, :source_ip, :source_port])
    |> validate_required([:kind, :payload])
  end
end

