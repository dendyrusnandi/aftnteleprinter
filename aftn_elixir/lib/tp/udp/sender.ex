defmodule Tp.Udp.Sender do
  def send(payload, opts \\ []) when is_binary(payload) do
    udp = Application.get_env(:tp, :udp, [])
    target = Tp.Settings.udp_target()
    host = Keyword.get(opts, :host) || target.host || udp[:target_host]
    port = Keyword.get(opts, :port) || target.port || udp[:target_port]

    with {:ok, ip} <- :inet.parse_address(String.to_charlist(host)),
         {:ok, socket} <- :gen_udp.open(0, [:binary]) do
      result = :gen_udp.send(socket, ip, port, payload)
      :gen_udp.close(socket)

      case result do
        :ok -> {:ok, %{host: host, port: port, bytes: byte_size(payload)}}
        {:error, reason} -> {:error, reason}
      end
    end
  end
end
