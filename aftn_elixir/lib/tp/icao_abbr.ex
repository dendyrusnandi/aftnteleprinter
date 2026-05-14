defmodule Tp.IcaoAbbr do
  use Ecto.Schema

  schema "abbreviations" do
    field :abbr, :string
    field :mean, :string
  end
end
