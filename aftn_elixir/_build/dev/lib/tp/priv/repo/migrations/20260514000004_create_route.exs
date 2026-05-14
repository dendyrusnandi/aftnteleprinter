defmodule Tp.Repo.Migrations.CreateRoute do
  use Ecto.Migration

  def change do
    create_if_not_exists table(:route, primary_key: false) do
      add :id_number,    :bigserial, primary_key: true
      add :type13a,      :string
      add :type16a,      :string
      add :type15c,      :text
      for i <- 1..21 do
        add :"destination#{i}", :string
      end
    end
  end
end
