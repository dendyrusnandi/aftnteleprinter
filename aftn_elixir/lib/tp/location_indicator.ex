defmodule Tp.LocationIndicator do
  use Ecto.Schema
  import Ecto.Changeset

  schema "location_indicator" do
    field :indicator, :string
    field :location,  :string
  end

  def changeset(record, attrs) do
    record
    |> cast(attrs, [:indicator, :location])
    |> validate_required([:indicator, :location])
    |> validate_format(:indicator, ~r/^[A-Z]{4}$/, message: "must be exactly 4 uppercase letters")
    |> validate_length(:location, min: 1, max: 500)
    |> unique_constraint(:indicator, message: "indicator already exists")
  end
end
