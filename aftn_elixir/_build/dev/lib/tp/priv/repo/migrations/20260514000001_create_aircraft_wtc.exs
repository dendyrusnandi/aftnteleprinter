defmodule Tp.Repo.Migrations.CreateAircraftWtc do
  use Ecto.Migration

  def change do
    create_if_not_exists table(:aircraft_wtc) do
      add :type9b, :string, null: false
      add :type9c, :string, null: false
    end
  end
end
