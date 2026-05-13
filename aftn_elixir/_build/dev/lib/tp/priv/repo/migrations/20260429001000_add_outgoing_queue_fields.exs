defmodule Tp.Repo.Migrations.AddOutgoingQueueFields do
  use Ecto.Migration

  def up do
    add_column_unless_exists(:air_messages, :transmit_attempts, "`transmit_attempts` integer DEFAULT 0 NOT NULL")
    add_column_unless_exists(:air_messages, :last_error, "`last_error` text")
    add_column_unless_exists(:air_messages, :next_attempt_at, "`next_attempt_at` datetime(6)")
  end

  def down do
    :ok
  end

  defp add_column_unless_exists(table, column, definition) do
    unless column_exists?(table, column) do
      execute("ALTER TABLE `#{table}` ADD #{definition}")
    end
  end

  defp column_exists?(table, column) do
    %{rows: [[count]]} =
      Ecto.Adapters.SQL.query!(
        repo(),
        """
        SELECT COUNT(*)
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = ?
          AND column_name = ?
        """,
        [to_string(table), to_string(column)]
      )

    count > 0
  end
end
