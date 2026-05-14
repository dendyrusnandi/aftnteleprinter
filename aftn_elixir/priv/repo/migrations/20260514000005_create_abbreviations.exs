defmodule Tp.Repo.Migrations.CreateAbbreviations do
  use Ecto.Migration

  def change do
    create_if_not_exists table(:abbreviations) do
      add :abbr, :string, null: false
      add :mean, :text
    end
  end
end
