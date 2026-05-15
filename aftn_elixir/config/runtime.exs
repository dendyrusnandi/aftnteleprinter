import Config

config :tp, Tp.Repo,
  username: System.get_env("MYSQL_USER") || "root",
  password: System.get_env("MYSQL_PASSWORD") || "Elsaelsa1#",
  hostname: System.get_env("MYSQL_HOST") || "localhost",
  database: System.get_env("MYSQL_DATABASE") || "tp",
  port: String.to_integer(System.get_env("MYSQL_PORT") || "3306"),
  pool_size: String.to_integer(System.get_env("POOL_SIZE") || "10"),
  connect_timeout: 5_000,
  pool_timeout: 3_000,
  backoff_type: :rand_exp,
  backoff_min: 500,
  backoff_max: 30_000

config :tp, :udp,
  port: String.to_integer(System.get_env("AFTN_UDP_PORT") || "101"),
  target_host: System.get_env("AFTN_UDP_TARGET_HOST") || "127.0.0.1",
  target_port: String.to_integer(System.get_env("AFTN_UDP_TARGET_PORT") || "101")

config :tp, :http,
  port: String.to_integer(System.get_env("HTTP_PORT") || System.get_env("PORT") || "4001")
