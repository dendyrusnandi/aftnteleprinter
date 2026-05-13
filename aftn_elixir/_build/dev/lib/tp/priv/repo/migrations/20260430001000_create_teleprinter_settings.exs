defmodule Tp.Repo.Migrations.CreateTeleprinterSettings do
  use Ecto.Migration

  def change do
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
  end
end
