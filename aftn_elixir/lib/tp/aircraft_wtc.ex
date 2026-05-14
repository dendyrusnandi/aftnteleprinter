defmodule Tp.AircraftWtc do
  use Ecto.Schema
  import Ecto.Changeset

  @wake_categories ~w(L M H J)

  schema "aircraft_wtc" do
    field :type9b, :string
    field :type9c, :string
  end

  def changeset(record, attrs) do
    record
    |> cast(attrs, [:type9b, :type9c])
    |> validate_required([:type9b, :type9c])
    |> validate_format(:type9b, ~r/^[A-Z0-9]{1,4}$/, message: "must be 1–4 uppercase letters/numbers")
    |> validate_inclusion(:type9c, @wake_categories, message: "must be L, M, H, or J")
    |> unique_constraint(:type9b, message: "aircraft type already exists")
  end

  def wake_categories, do: @wake_categories
end
