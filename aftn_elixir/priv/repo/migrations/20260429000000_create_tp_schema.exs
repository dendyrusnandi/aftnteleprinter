defmodule Tp.Repo.Migrations.CreateTpSchema do
  use Ecto.Migration

  def change do
    create_if_not_exists table(:air_messages) do
      add :direction, :string, null: false, default: "inbound"
      add :priority, :string, size: 2
      add :destinations, :string, size: 1024, null: false, default: "[]"
      add :filing_time, :string, size: 8
      add :originator, :string, size: 8
      add :message_type, :string, size: 8
      add :aircraft_id, :string, size: 16
      add :departure, :string, size: 8
      add :departure_time, :string, size: 8
      add :destination, :string, size: 8
      add :route, :text
      add :raw_text, :text, null: false
      add :parsed_fields, :map, null: false
      add :status, :string, size: 69
      add :status_reply, :string, size: 69
      add :sequence_no, :string, size: 8
      add :cid, :string, size: 8
      add :bell, :boolean, null: false, default: false
      add :transmit_attempts, :integer, null: false, default: 0
      add :last_error, :text
      add :next_attempt_at, :utc_datetime_usec
      add :udp_target_host, :string, size: 255
      add :udp_target_port, :integer
      add :filed_by, :string, size: 100, default: "SYSTEM"
      add :legacy_table, :string, size: 80
      add :legacy_id, :bigint
      add :received_at, :utc_datetime_usec
      add :sent_at, :utc_datetime_usec

      timestamps(type: :utc_datetime_usec)
    end

    create index(:air_messages, [:inserted_at])
    create index(:air_messages, [:direction])
    create index(:air_messages, [:message_type])
    create index(:air_messages, [:originator])
    create index(:air_messages, [:departure])
    create index(:air_messages, [:destination])
    create index(:air_messages, [:aircraft_id])
    create index(:air_messages, [:sequence_no])
    create index(:air_messages, [:status])
    create index(:air_messages, [:next_attempt_at])
    create unique_index(:air_messages, [:legacy_table, :legacy_id])

    create_if_not_exists table(:incoming_msg) do
      add :raw_text, :text, null: false
      add :source, :string, size: 80
      add :processed, :boolean, null: false, default: false
      timestamps(type: :utc_datetime_usec)
    end

    create_if_not_exists table(:queuelp) do
      add :data, :text, null: false
      add :status, :string, size: 40
      add :printed_at, :utc_datetime_usec
      timestamps(type: :utc_datetime_usec)
    end

    create_if_not_exists table(:warning) do
      add :message, :text, null: false
      add :severity, :text
      add :acknowledged_at, :utc_datetime_usec
      timestamps(type: :utc_datetime_usec)
    end

    create_if_not_exists table(:teleprinter_events) do
      add :kind, :string, null: false
      add :payload, :text, null: false
      add :source_ip, :string, size: 64
      add :source_port, :integer
      timestamps(type: :utc_datetime_usec, updated_at: false)
    end

    create index(:teleprinter_events, [:kind])
    create index(:teleprinter_events, [:inserted_at])

    create_if_not_exists table(:teleprinter_settings) do
      add :local_udp_port, :integer, null: false, default: 101
      add :destination_ip_address, :string, size: 64, null: false, default: "127.0.0.1"
      add :port, :integer, null: false, default: 101
      add :code, :string, size: 8, null: false, default: "IA5"
      add :digit_seq, :integer, null: false, default: 4
      add :cid, :string, size: 16, null: false, default: "RCJ"
      add :tseq, :string, size: 16, null: false, default: "0001"
      add :svc_msg_generation, :boolean, null: false, default: false
      add :prev_st, :string, size: 32
      add :channel_check, :boolean, null: false, default: true
      add :automatic_repeat, :boolean, null: false, default: false
      add :originator, :string, size: 8, null: false, default: "WAJJYFYC"

      timestamps(type: :utc_datetime_usec)
    end

    create_if_not_exists table(:abbreviations) do
      add :abbr, :string, size: 69, null: false
      add :mean, :text, null: false
    end

    create_if_not_exists table(:aircraft_reg) do
      add :registration, :string, size: 32
      add :aircraft_type, :string, size: 32
      add :operator, :string, size: 120
      add :raw_data, :map, null: false
    end

    create_if_not_exists table(:aircraft_wtc) do
      add :aircraft_type, :string, size: 16
      add :wake_turbulence_category, :string, size: 4
      add :raw_data, :map, null: false
    end

    create_if_not_exists table(:location_indicator) do
      add :indicator, :string, size: 8, null: false
      add :name, :string, size: 180
      add :raw_data, :map, null: false
    end

    create_if_not_exists table(:route) do
      add :name, :string, size: 80
      add :description, :text
      add :raw_data, :map, null: false
    end
  end
end
