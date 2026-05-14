defmodule Tp.Repo.Migrations.CreateLocationIndicator do
  use Ecto.Migration

  def change do
    create_if_not_exists table(:location_indicator) do
      add :indicator, :string, null: false
      add :location,  :string, null: false
    end
  end
end
