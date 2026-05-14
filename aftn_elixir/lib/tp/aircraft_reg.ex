defmodule Tp.AircraftReg do
  use Ecto.Schema
  import Ecto.Changeset

  schema "aircraft_reg" do
    field :type18,    :string
    field :type9b,    :string
    field :type10a,   :string
    field :type10b,   :string
    field :type18Opr, :string
    field :type18Pbn, :string
    field :type18Sel, :string
  end

  def changeset(record, attrs) do
    record
    |> cast(attrs, [:type18, :type9b, :type10a, :type10b, :type18Opr, :type18Pbn, :type18Sel])
    |> validate_required([:type18, :type9b])
    |> validate_format(:type9b,   ~r/^[A-Z0-9]{1,4}$/,  message: "must be 1-4 uppercase letters/numbers")
    |> validate_format(:type18,   ~r/^[A-Z0-9]{1,7}$/,  message: "must be 1-7 uppercase letters/numbers")
  end
end
