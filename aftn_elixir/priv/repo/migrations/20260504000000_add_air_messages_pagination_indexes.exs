defmodule Tp.Repo.Migrations.AddAirMessagesPaginationIndexes do
  use Ecto.Migration

  def up do
    create index(:air_messages, [:inserted_at, :id], name: :air_messages_inserted_at_id_idx)
    create index(:air_messages, [:direction, :inserted_at, :id], name: :air_messages_direction_inserted_at_id_idx)
    create index(:air_messages, [:message_type, :inserted_at, :id], name: :air_messages_message_type_inserted_at_id_idx)
    create index(:air_messages, [:direction, :message_type, :inserted_at, :id], name: :air_messages_direction_type_inserted_at_id_idx)
  end

  def down do
    drop index(:air_messages, [:direction, :message_type, :inserted_at, :id], name: :air_messages_direction_type_inserted_at_id_idx)
    drop index(:air_messages, [:message_type, :inserted_at, :id], name: :air_messages_message_type_inserted_at_id_idx)
    drop index(:air_messages, [:direction, :inserted_at, :id], name: :air_messages_direction_inserted_at_id_idx)
    drop index(:air_messages, [:inserted_at, :id], name: :air_messages_inserted_at_id_idx)
  end
end
