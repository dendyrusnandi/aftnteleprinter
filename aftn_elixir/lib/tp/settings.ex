defmodule Tp.Settings do
  import Ecto.Query, only: [from: 2]

  alias Tp.Repo
  alias Tp.Settings.TeleprinterSetting

  def get do
    Repo.one(setting_query()) || default_setting()
  end

  def get_or_default do
    get()
  rescue
    MyXQL.Error -> default_setting()
  end

  def update(attrs) do
    setting = Repo.one(setting_query()) || %TeleprinterSetting{}

    result =
      setting
      |> TeleprinterSetting.changeset(normalize(attrs))
      |> Repo.insert_or_update()

    with {:ok, setting} <- result do
      case Tp.Udp.Listener.set_port(setting.local_udp_port) do
        {:ok, _port} -> {:ok, setting}
        {:error, reason} -> {:error, {:udp_port, reason}}
      end
    end
  end

  def default_setting do
    udp = Application.get_env(:tp, :udp, [])

    %TeleprinterSetting{
      local_udp_port: udp[:port] || 101,
      destination_ip_address: udp[:target_host] || "127.0.0.1",
      port: udp[:target_port] || 101,
      code: "IA5",
      digit_seq: 4,
      cid: "RCJ",
      tseq: "0001",
      svc_msg_generation: false,
      prev_st: "WAJJYFYZ",
      channel_check: true,
      automatic_repeat: false,
      originator: "WAJJYFYC"
    }
  end

  def udp_target do
    setting = get_or_default()
    %{host: setting.destination_ip_address, port: setting.port}
  end

  def udp_receive_port do
    get_or_default().local_udp_port || Application.get_env(:tp, :udp, [])[:port] || 101
  end

  defp setting_query do
    from setting in TeleprinterSetting, order_by: [asc: setting.id], limit: 1
  end

  defp normalize(attrs) do
    %{
      local_udp_port: parse_int(Map.get(attrs, "local_udp_port")),
      destination_ip_address: Map.get(attrs, "destination_ip_address"),
      port: parse_int(Map.get(attrs, "port")),
      code: Map.get(attrs, "code", "IA5"),
      digit_seq: parse_int(Map.get(attrs, "digit_seq")),
      cid: attrs |> Map.get("cid", "") |> String.upcase(),
      tseq: Map.get(attrs, "tseq", "0001"),
      svc_msg_generation: checkbox?(Map.get(attrs, "svc_msg_generation")),
      prev_st: blank_to_nil(Map.get(attrs, "prev_st")),
      channel_check: checkbox?(Map.get(attrs, "channel_check")),
      automatic_repeat: checkbox?(Map.get(attrs, "automatic_repeat")),
      originator: attrs |> Map.get("originator", "") |> String.upcase()
    }
  end

  defp parse_int(nil), do: nil
  defp parse_int(""), do: nil
  defp parse_int(value) when is_integer(value), do: value

  defp parse_int(value) do
    case Integer.parse(to_string(value)) do
      {int, ""} -> int
      _ -> nil
    end
  end

  defp checkbox?(value), do: value in ["true", "on", "1", true]

  defp blank_to_nil(nil), do: nil

  defp blank_to_nil(value) do
    value = String.trim(to_string(value))
    if value == "", do: nil, else: String.upcase(value)
  end
end
