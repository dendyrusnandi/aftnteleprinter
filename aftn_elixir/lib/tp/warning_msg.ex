defmodule Tp.WarningMsg do
  use Ecto.Schema

  @primary_key {:idcnt, :id, autogenerate: true}
  schema "warning" do
    field :message, :string
    field :reason,  :string
    field :tgl,     :naive_datetime
  end
end
