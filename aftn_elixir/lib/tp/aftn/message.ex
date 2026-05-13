defmodule Tp.Aftn.Message do
  use Ecto.Schema
  import Ecto.Changeset

  @message_types ~w(CH ALR RCF FPL CHG CNL DLA DEP ARR CPL EST CDN ACP LAM RQP RQS SPL ABI PAC MAC REJ TOC AOC EMG MIS TDM LRM ASM FAN FCN TRU ADS FREE)

  schema "air_messages" do
    field :direction, Ecto.Enum, values: [:inbound, :outbound], default: :inbound
    field :priority, :string
    field :destinations, Tp.Aftn.DestinationList, default: []
    field :filing_time, :string
    field :originator, :string
    field :message_type, :string
    field :aircraft_id, :string
    field :departure, :string
    field :departure_time, :string
    field :destination, :string
    field :route, :string
    field :raw_text, :string
    field :parsed_fields, :map, default: %{}
    field :status, :string
    field :status_reply, :string
    field :sequence_no, :string
    field :cid, :string
    field :bell, :boolean, default: false
    field :transmit_attempts, :integer, default: 0
    field :last_error, :string
    field :next_attempt_at, :utc_datetime_usec
    field :udp_target_host, :string
    field :udp_target_port, :integer
    field :filed_by, :string, default: "SYSTEM"
    field :note, :string, virtual: true
    field :legacy_table, :string
    field :legacy_id, :integer
    field :received_at, :utc_datetime_usec
    field :sent_at, :utc_datetime_usec

    timestamps(type: :utc_datetime_usec)
  end

  def changeset(message, attrs) do
    message
    |> cast(attrs, [
      :direction,
      :priority,
      :destinations,
      :filing_time,
      :originator,
      :message_type,
      :aircraft_id,
      :departure,
      :departure_time,
      :destination,
      :route,
      :raw_text,
      :parsed_fields,
      :status,
      :status_reply,
      :sequence_no,
      :cid,
      :bell,
      :transmit_attempts,
      :last_error,
      :next_attempt_at,
      :udp_target_host,
      :udp_target_port,
      :filed_by,
      :note,
      :legacy_table,
      :legacy_id,
      :received_at,
      :sent_at
    ])
    |> validate_required([:direction, :raw_text])
    |> validate_inclusion(:message_type, @message_types)
  end
end
