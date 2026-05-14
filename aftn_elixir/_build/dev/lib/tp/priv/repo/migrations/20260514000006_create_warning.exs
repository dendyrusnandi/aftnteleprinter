defmodule Tp.Repo.Migrations.CreateWarning do
  use Ecto.Migration

  def change do
    create_if_not_exists table(:warning, primary_key: false) do
      add :idcnt,   :bigserial, primary_key: true
      add :message, :text
      add :reason,  :string
      add :tgl,     :naive_datetime
    end
  end
end
