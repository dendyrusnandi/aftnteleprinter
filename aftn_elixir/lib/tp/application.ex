defmodule Tp.Application do
  use Application

  @impl true
  def start(_type, _args) do
    http_port = Application.get_env(:tp, :http)[:port]

    children = [
      Tp.Repo,
      Tp.Udp.Monitor,
      Tp.Udp.Listener,
      Tp.LinkMonitor,
      Tp.Aftn.OutgoingQueue,
      {Bandit, plug: TpWeb.Router, scheme: :http, port: http_port}
    ]

    opts = [strategy: :one_for_one, name: Tp.Supervisor]
    Supervisor.start_link(children, opts)
  end
end
