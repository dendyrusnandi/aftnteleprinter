defmodule Tp.Repo.Migrations.CreateAircraftReg do
  use Ecto.Migration

  def change do
    create_if_not_exists table(:aircraft_reg) do
      add :type18,     :string
      add :type9b,     :string
      add :type10a,    :string
      add :type10b,    :string
      add :type18Opr,  :string
      add :type18Pbn,  :string
      add :type18Sel,  :string
    end
  end
end
