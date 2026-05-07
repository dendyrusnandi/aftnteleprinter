defmodule Tp.Repo do
  use Ecto.Repo,
    otp_app: :tp,
    adapter: Ecto.Adapters.MyXQL
end
