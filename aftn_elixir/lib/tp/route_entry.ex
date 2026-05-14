defmodule Tp.RouteEntry do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key {:id_number, :id, autogenerate: true}
  schema "route" do
    field :type13a,  :string
    field :type16a,  :string
    field :type15c,  :string

    for i <- 1..21 do
      field :"destination#{i}", :string
    end
  end

  @dest_fields Enum.map(1..21, &:"destination#{&1}")

  def changeset(record, attrs) do
    record
    |> cast(attrs, [:type13a, :type16a, :type15c | @dest_fields])
    |> validate_required([:type13a, :type16a])
    |> validate_format(:type13a, ~r/^[A-Z]{4}$/, message: "must be 4 uppercase letters")
    |> validate_format(:type16a, ~r/^[A-Z]{4}$/, message: "must be 4 uppercase letters")
  end

  def dest_fields, do: @dest_fields
end
