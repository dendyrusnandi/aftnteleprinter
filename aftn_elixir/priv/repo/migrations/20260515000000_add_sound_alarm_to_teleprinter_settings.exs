defmodule Tp.Repo.Migrations.AddSoundAlarmToTeleprinterSettings do
  use Ecto.Migration

  def up do
    add_column_unless_exists(:teleprinter_settings, :sound_enabled, "`sound_enabled` boolean DEFAULT true NOT NULL")
    add_column_unless_exists(:teleprinter_settings, :alarm_repeat_count, "`alarm_repeat_count` integer DEFAULT 1 NOT NULL")
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
