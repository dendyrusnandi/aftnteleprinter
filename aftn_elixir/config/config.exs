import Config

config :tp,
  ecto_repos: [Tp.Repo]

config :tp, Tp.Repo,
  migration_timestamps: [type: :utc_datetime_usec]

config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

