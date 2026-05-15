defmodule Tp.Settings.TeleprinterSetting do
  use Ecto.Schema
  import Ecto.Changeset

  schema "teleprinter_settings" do
    field :local_udp_port, :integer
    field :destination_ip_address, :string
    field :port, :integer
    field :code, :string, default: "IA5"
    field :digit_seq, :integer, default: 4
    field :cid, :string, default: "RCJ"
    field :tseq, :string, default: "0001"
    field :svc_msg_generation, :boolean, default: false
    field :prev_st, :string
    field :channel_check, :boolean, default: true
    field :automatic_repeat, :boolean, default: false
    field :sound_enabled, :boolean, default: true
    field :alarm_repeat_count, :integer, default: 1
    field :originator, :string, default: "WAJJYFYC"

    timestamps(type: :utc_datetime_usec)
  end

  def changeset(setting, attrs) do
    setting
    |> cast(attrs, [
      :destination_ip_address,
      :local_udp_port,
      :port,
      :code,
      :digit_seq,
      :cid,
      :tseq,
      :svc_msg_generation,
      :prev_st,
      :channel_check,
      :automatic_repeat,
      :sound_enabled,
      :alarm_repeat_count,
      :originator
    ])
    |> validate_required([:destination_ip_address, :local_udp_port, :port, :code, :digit_seq, :cid, :tseq, :originator])
    |> validate_inclusion(:code, ["ITA2", "IA5"])
    |> validate_inclusion(:digit_seq, [3, 4])
    |> validate_number(:port, greater_than: 0, less_than_or_equal_to: 65_535)
    |> validate_number(:local_udp_port, greater_than: 0, less_than_or_equal_to: 65_535)
    |> validate_number(:alarm_repeat_count, greater_than_or_equal_to: 0, less_than_or_equal_to: 999)
    |> validate_format(:destination_ip_address, ~r/^[0-9A-Za-z\.\-:]+$/)
    |> validate_format(:originator, ~r/^[A-Z]{8}$/)
    |> validate_prev_st()
  end

  defp validate_prev_st(changeset) do
    if get_field(changeset, :svc_msg_generation) do
      changeset
      |> validate_required([:prev_st])
      |> validate_format(:prev_st, ~r/^[A-Z]{8}$/)
    else
      changeset
    end
  end
end
