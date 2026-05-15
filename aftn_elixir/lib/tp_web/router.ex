defmodule TpWeb.Router do
  use Plug.Router

  alias Tp.{Aftn, Settings}

  plug Plug.Parsers, parsers: [:urlencoded, :json], json_decoder: Jason
  plug :match
  plug :dispatch

  get "/" do
    filters = filters(conn.params)

    case dashboard_data(filters) do
      {:ok, messages, events, settings, received_messages, pagination} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.dashboard(messages, events, filters, notice(conn.params), settings, received_messages, pagination))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/health" do
    send_resp(conn, 200, "ok")
  end

  get "/compose" do
    selected = Map.get(conn.params, "form", "AFTN_FREE")

    conn
    |> put_resp_content_type("text/html")
    |> send_resp(200, TpWeb.Views.compose_page(notice(conn.params), selected, conn.params))
  end

  get "/queue" do
    q = Map.get(conn.params, "q", "")
    page = Map.get(conn.params, "page", "1")

    case safe_queue_messages_page(q: q, page: page) do
      {:ok, messages, pagination} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.queue_page(messages, q, notice(conn.params), pagination))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/settings" do
    conn
    |> put_resp_content_type("text/html")
    |> send_resp(200, TpWeb.Views.settings_page(Settings.get_or_default(), notice(conn.params)))
  end

  post "/settings" do
    case Settings.update(conn.params) do
      {:ok, _setting} ->
        redirect(conn, "/?info=#{URI.encode_www_form("Setting saved")}")

      {:error, changeset} ->
        case changeset do
          {:udp_port, reason} ->
            conn
            |> put_resp_content_type("text/html")
            |> send_resp(422, TpWeb.Views.settings_page(Settings.get_or_default(), {:error, "Local receive port gagal dibuka: #{inspect(reason)}"}))

          changeset ->
            conn
            |> put_resp_content_type("text/html")
            |> send_resp(422, TpWeb.Views.settings_page(changeset, {:error, "Setting belum valid"}))
        end
    end
  end

  get "/messages/:id" do
    case safe_message(id) do
      {:ok, nil} ->
        send_resp(conn, 404, "message not found")

      {:ok, message} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.message_detail(message))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/messages/:id/pdf" do
    case safe_message(id) do
      {:ok, nil} ->
        send_resp(conn, 404, "message not found")

      {:ok, message} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.message_pdf(message))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/api/messages" do
    opts = filters(conn.params) ++ [page_size: 100]

    case safe_message_page(opts) do
      {:ok, messages, pagination} ->
        payload = %{
          data: Enum.map(messages, &message_json/1),
          pagination: Map.take(pagination, [:page, :page_size, :has_next, :next_cursor])
        }

        conn
        |> put_resp_content_type("application/json")
        |> send_resp(200, Jason.encode!(payload))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(503, Jason.encode!(%{error: "database_not_migrated", run: "mix ecto.migrate"}))
    end
  end

  get "/api/messages/recent" do
    opts = filters(conn.params) ++ [limit: parse_int(Map.get(conn.params, "page_size"), 15)]

    case safe_latest_messages(opts) do
      {:ok, messages} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(200, Jason.encode!(%{data: Enum.map(messages, &message_json/1)}))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(503, Jason.encode!(%{error: "database_not_migrated", run: "mix ecto.migrate"}))
    end
  end

  get "/api/messages/:id" do
    case safe_message(id) do
      {:ok, nil} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(404, Jason.encode!(%{error: "message_not_found"}))

      {:ok, message} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(200, Jason.encode!(message_json(message)))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(503, Jason.encode!(%{error: "database_not_migrated", run: "mix ecto.migrate"}))
    end
  end

  get "/api/udp-monitor" do
    payload = %{data: Enum.map(Tp.Udp.Monitor.drain(), &udp_monitor_json/1)}

    conn
    |> put_resp_content_type("application/json")
    |> send_resp(200, Jason.encode!(payload))
  end

  get "/api/status" do
    with {:ok, queue_count} <- safe_queue_count(),
         {:ok, setting} <- safe_settings() do
      link = Tp.LinkMonitor.status()
      link_up? = link.state == :up
      link_state = if link_up?, do: "Established", else: "Down"
      link_reason = if link_up?, do: "established", else: (link.reason || "down")

      payload = %{
        queue_count: queue_count,
        tseq: setting.tseq,
        cid: setting.cid,
        digit_seq: setting.digit_seq,
        local: "0.0.0.0:#{setting.local_udp_port}",
        destination: "#{setting.destination_ip_address}:#{setting.port}",
        check: link_reason,
        link_state: link_state,
        link_reason: link_reason,
        link_up: link_up?
      }

      conn
      |> put_resp_content_type("application/json")
      |> put_resp_header("cache-control", "no-store")
      |> send_resp(200, Jason.encode!(payload))
    else
      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(503, Jason.encode!(%{error: "database_not_migrated", run: "mix ecto.migrate"}))

      _error ->
        conn
        |> put_resp_content_type("application/json")
        |> send_resp(500, Jason.encode!(%{error: "status_unavailable"}))
    end
  end

  post "/messages/send" do
    text = Map.get(conn.params, "message", "") |> to_string()

    if String.trim(text) == "" do
      redirect(conn, "/?error=#{URI.encode_www_form("Raw message wajib diisi")}")
    else
      with {:ok, attrs} <- transmit_attrs(conn.params),
           {:ok, _message} <- Aftn.enqueue_transmit(text, attrs) do
        Tp.Aftn.OutgoingQueue.kick()
        redirect(conn, "/?direction=outbound")
      else
        {:error, {:validation, errors}} ->
          redirect(conn, "/?error=#{URI.encode_www_form(Enum.join(errors, ", "))}")

        {:error, reason} ->
          send_resp(conn, 422, inspect(reason))
      end
    end
  end

  post "/messages/compose" do
    type = Map.get(conn.params, "compose_type", "FREE")
    action = Map.get(conn.params, "compose_action", "send")

    with {:ok, attrs} <- transmit_attrs(conn.params),
         {:ok, _message} <- compose_submit(action, type, conn.params, attrs) do
      if action != "save", do: Tp.Aftn.OutgoingQueue.kick()
      redirect(conn, redirect_after_compose(conn.params, type, action))
    else
      {:error, {:validation, errors}} ->
        message = Enum.join(errors, ", ")
        redirect(conn, error_redirect(conn.params, type, message))

      {:error, reason} ->
        send_resp(conn, 422, inspect(reason))
    end
  end

  post "/messages/:id/delete" do
    return_to = return_path(Map.get(conn.params, "return_to", "/"))

    case safe_delete_message(id) do
      {:ok, _deleted} ->
        redirect(conn, with_notice(return_to, :info, "Message deleted"))

      {:error, :not_found} ->
        redirect(conn, with_notice(return_to, :error, "Message tidak ditemukan"))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())

      {:error, _reason} ->
        redirect(conn, with_notice(return_to, :error, "Delete gagal"))
    end
  end

  post "/queue/:id/delete" do
    q = Map.get(conn.params, "q", "")
    requested_return_to = Map.get(conn.params, "return_to", nil)

    return_to =
      cond do
        requested_return_to not in [nil, ""] ->
          return_path(requested_return_to)

        q in [nil, ""] ->
          "/queue"

        true ->
          "/queue?#{URI.encode_query(%{q: q})}"
      end

    case safe_delete_message(id) do
      {:ok, _deleted} ->
        redirect(conn, with_notice(return_to, :info, "Queue message deleted"))

      {:error, :not_found} ->
        redirect(conn, with_notice(return_to, :error, "Message tidak ditemukan"))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())

      {:error, _reason} ->
        redirect(conn, with_notice(return_to, :error, "Delete gagal"))
    end
  end

  post "/messages/delete-all" do
    filters = filters(conn.params)

    case safe_delete_all_messages(filters) do
      {:ok, count} ->
        redirect(conn, with_notice(filters_to_path(filters), :info, "#{count} message deleted"))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/queue/delete-all" do
    q = Map.get(conn.params, "q", "")

    case safe_delete_queue_messages(q: q) do
      {:ok, count} ->
        path = if q in [nil, ""], do: "/queue", else: "/queue?#{URI.encode_query(%{q: q})}"
        redirect(conn, with_notice(path, :info, "#{count} queue message deleted"))

      {:error, :database_not_migrated} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/aircraft-types" do
    q    = Map.get(conn.params, "q", "")
    wake = Map.get(conn.params, "wake", "")
    page = parse_int(Map.get(conn.params, "page", "1"), 1)

    case safe_list_aircraft(q: q, wake: wake, page: page) do
      {:ok, items, pagination} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.aircraft_type_page(items, pagination, q, wake, notice(conn.params)))

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/aircraft-types/save" do
    case safe_create_aircraft(conn.params) do
      {:ok, _} ->
        redirect(conn, "/aircraft-types?info=#{URI.encode_www_form("Aircraft type saved")}")

      {:error, {:validation, errors}} ->
        redirect(conn, "/aircraft-types?error=#{URI.encode_www_form(Enum.join(errors, ", "))}")

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/aircraft-types/:id/update" do
    case safe_update_aircraft(id, conn.params) do
      {:ok, _} ->
        redirect(conn, "/aircraft-types?info=#{URI.encode_www_form("Aircraft type updated")}")

      {:error, :not_found} ->
        redirect(conn, "/aircraft-types?error=#{URI.encode_www_form("Record not found")}")

      {:error, {:validation, errors}} ->
        redirect(conn, "/aircraft-types?error=#{URI.encode_www_form(Enum.join(errors, ", "))}")

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/aircraft-types/:id/delete" do
    return_to = return_path(Map.get(conn.params, "return_to", "/aircraft-types"))

    case safe_delete_aircraft(id) do
      {:ok, _} ->
        redirect(conn, with_notice(return_to, :info, "Aircraft type deleted"))

      {:error, :not_found} ->
        redirect(conn, with_notice(return_to, :error, "Record not found"))

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())

      {:error, _} ->
        redirect(conn, with_notice(return_to, :error, "Delete failed"))
    end
  end

  post "/aircraft-types/delete-all" do
    q    = Map.get(conn.params, "q", "")
    wake = Map.get(conn.params, "wake", "")

    case safe_delete_all_aircraft(q: q, wake: wake) do
      {:ok, count} ->
        redirect(conn, "/aircraft-types?info=#{URI.encode_www_form("#{count} record(s) deleted")}")

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/aircraft-reg" do
    q18  = Map.get(conn.params, "q18", "")
    q9b  = Map.get(conn.params, "q9b", "")
    page = parse_int(Map.get(conn.params, "page", "1"), 1)
    case safe_list_reg(q18: q18, q9b: q9b, page: page) do
      {:ok, items, pag} ->
        conn |> put_resp_content_type("text/html")
             |> send_resp(200, TpWeb.Views.aircraft_reg_page(items, pag, q18, q9b, notice(conn.params)))
      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/aircraft-reg/save" do
    case safe_crud_reg(:create, conn.params) do
      {:ok, _}  -> redirect(conn, "/aircraft-reg?info=#{URI.encode_www_form("Registration saved")}")
      {:error, {:validation, e}} -> redirect(conn, "/aircraft-reg?error=#{URI.encode_www_form(Enum.join(e, ", "))}")
      {:error, :database_not_migrated} -> conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/aircraft-reg/:id/update" do
    case safe_crud_reg(:update, id, conn.params) do
      {:ok, _}  -> redirect(conn, "/aircraft-reg?info=#{URI.encode_www_form("Registration updated")}")
      {:error, :not_found} -> redirect(conn, "/aircraft-reg?error=#{URI.encode_www_form("Not found")}")
      {:error, {:validation, e}} -> redirect(conn, "/aircraft-reg?error=#{URI.encode_www_form(Enum.join(e, ", "))}")
      {:error, :database_not_migrated} -> conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/aircraft-reg/:id/delete" do
    rt = return_path(Map.get(conn.params, "return_to", "/aircraft-reg"))
    case safe_crud_reg(:delete, id) do
      {:ok, _}  -> redirect(conn, with_notice(rt, :info, "Deleted"))
      {:error, :not_found} -> redirect(conn, with_notice(rt, :error, "Not found"))
      {:error, :database_not_migrated} -> conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
      {:error, _} -> redirect(conn, with_notice(rt, :error, "Delete failed"))
    end
  end

  post "/aircraft-reg/delete-all" do
    q18 = Map.get(conn.params, "q18", ""); q9b = Map.get(conn.params, "q9b", "")
    case safe_delete_all_reg(q18: q18, q9b: q9b) do
      {:ok, n} -> redirect(conn, "/aircraft-reg?info=#{URI.encode_www_form("#{n} record(s) deleted")}")
      {:error, :database_not_migrated} -> conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/routes" do
    dep  = Map.get(conn.params, "dep", "")
    dest = Map.get(conn.params, "dest", "")
    page = parse_int(Map.get(conn.params, "page", "1"), 1)
    case safe_list_route(dep: dep, dest: dest, page: page) do
      {:ok, items, pag} ->
        conn |> put_resp_content_type("text/html")
             |> send_resp(200, TpWeb.Views.route_page(items, pag, dep, dest, notice(conn.params)))
      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/routes/save" do
    case safe_crud_route(:create, conn.params) do
      {:ok, _}  -> redirect(conn, "/routes?info=#{URI.encode_www_form("Route saved")}")
      {:error, {:validation, e}} -> redirect(conn, "/routes?error=#{URI.encode_www_form(Enum.join(e, ", "))}")
      {:error, :database_not_migrated} -> conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/routes/:id/update" do
    case safe_crud_route(:update, id, conn.params) do
      {:ok, _}  -> redirect(conn, "/routes?info=#{URI.encode_www_form("Route updated")}")
      {:error, :not_found} -> redirect(conn, "/routes?error=#{URI.encode_www_form("Not found")}")
      {:error, {:validation, e}} -> redirect(conn, "/routes?error=#{URI.encode_www_form(Enum.join(e, ", "))}")
      {:error, :database_not_migrated} -> conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/routes/:id/delete" do
    rt = return_path(Map.get(conn.params, "return_to", "/routes"))
    case safe_crud_route(:delete, id) do
      {:ok, _}  -> redirect(conn, with_notice(rt, :info, "Deleted"))
      {:error, :not_found} -> redirect(conn, with_notice(rt, :error, "Not found"))
      {:error, :database_not_migrated} -> conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
      {:error, _} -> redirect(conn, with_notice(rt, :error, "Delete failed"))
    end
  end

  post "/routes/delete-all" do
    dep = Map.get(conn.params, "dep", ""); dest = Map.get(conn.params, "dest", "")
    case safe_delete_all_route(dep: dep, dest: dest) do
      {:ok, n} -> redirect(conn, "/routes?info=#{URI.encode_www_form("#{n} record(s) deleted")}")
      {:error, :database_not_migrated} -> conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/location-indicators" do
    q    = Map.get(conn.params, "q", "")
    loc  = Map.get(conn.params, "loc", "")
    page = parse_int(Map.get(conn.params, "page", "1"), 1)

    case safe_list_location(q: q, loc: loc, page: page) do
      {:ok, items, pagination} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.location_indicator_page(items, pagination, q, loc, notice(conn.params)))

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/location-indicators/save" do
    case safe_create_location(conn.params) do
      {:ok, _} ->
        redirect(conn, "/location-indicators?info=#{URI.encode_www_form("Location indicator saved")}")

      {:error, {:validation, errors}} ->
        redirect(conn, "/location-indicators?error=#{URI.encode_www_form(Enum.join(errors, ", "))}")

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/location-indicators/:id/update" do
    case safe_update_location(id, conn.params) do
      {:ok, _} ->
        redirect(conn, "/location-indicators?info=#{URI.encode_www_form("Location indicator updated")}")

      {:error, :not_found} ->
        redirect(conn, "/location-indicators?error=#{URI.encode_www_form("Record not found")}")

      {:error, {:validation, errors}} ->
        redirect(conn, "/location-indicators?error=#{URI.encode_www_form(Enum.join(errors, ", "))}")

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/location-indicators/:id/delete" do
    return_to = return_path(Map.get(conn.params, "return_to", "/location-indicators"))

    case safe_delete_location(id) do
      {:ok, _} ->
        redirect(conn, with_notice(return_to, :info, "Location indicator deleted"))

      {:error, :not_found} ->
        redirect(conn, with_notice(return_to, :error, "Record not found"))

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())

      {:error, _} ->
        redirect(conn, with_notice(return_to, :error, "Delete failed"))
    end
  end

  post "/location-indicators/delete-all" do
    q   = Map.get(conn.params, "q", "")
    loc = Map.get(conn.params, "loc", "")

    case safe_delete_all_location(q: q, loc: loc) do
      {:ok, count} ->
        redirect(conn, "/location-indicators?info=#{URI.encode_www_form("#{count} record(s) deleted")}")

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/icao-abbreviations" do
    q    = Map.get(conn.params, "q", "")
    mean = Map.get(conn.params, "mean", "")
    page = parse_int(Map.get(conn.params, "page", "1"), 1)

    case safe_list_abbr(q: q, mean: mean, page: page) do
      {:ok, items, pagination} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.icao_abbreviations_page(items, pagination, q, mean, notice(conn.params)))

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/warning-messages" do
    msg     = Map.get(conn.params, "msg", "")
    reason  = Map.get(conn.params, "reason", "")
    date_fr = Map.get(conn.params, "date_fr", "")
    date_to = Map.get(conn.params, "date_to", "")
    page    = parse_int(Map.get(conn.params, "page", "1"), 1)

    case safe_list_warnings(msg: msg, reason: reason, date_fr: date_fr, date_to: date_to, page: page) do
      {:ok, items, pagination} ->
        conn
        |> put_resp_content_type("text/html")
        |> send_resp(200, TpWeb.Views.warning_messages_page(items, pagination, msg, reason, date_fr, date_to, notice(conn.params)))

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  post "/warning-messages/:id/delete" do
    return_to = Map.get(conn.params, "return_to", "/warning-messages")

    case safe_delete_warning(String.to_integer(id)) do
      {:ok, _} ->
        redirect(conn, with_notice(return_to, :info, "Warning message deleted"))

      {:error, :not_found} ->
        redirect(conn, with_notice(return_to, :error, "Record not found"))

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())

      {:error, _} ->
        redirect(conn, with_notice(return_to, :error, "Delete failed"))
    end
  end

  post "/warning-messages/delete-all" do
    msg     = Map.get(conn.params, "msg", "")
    reason  = Map.get(conn.params, "reason", "")
    date_fr = Map.get(conn.params, "date_fr", "")
    date_to = Map.get(conn.params, "date_to", "")

    case safe_delete_all_warnings(msg: msg, reason: reason, date_fr: date_fr, date_to: date_to) do
      {:ok, count} ->
        redirect(conn, "/warning-messages?info=#{URI.encode_www_form("#{count} record(s) deleted")}")

      {:error, :database_not_migrated} ->
        conn |> put_resp_content_type("text/html") |> send_resp(503, TpWeb.Views.setup_required())
    end
  end

  get "/test-message" do
    settings = Settings.get_or_default()
    monitor_items = Tp.Udp.Monitor.drain()
    conn
    |> put_resp_content_type("text/html")
    |> send_resp(200, TpWeb.Views.test_message_page(notice(conn.params), settings, [], monitor_items))
  end

  post "/test-message/send-one" do
    settings   = Settings.get_or_default()
    destination = conn.params |> Map.get("address", "")    |> to_string() |> String.upcase() |> String.trim()
    originator  = conn.params |> Map.get("originator", "") |> to_string() |> String.upcase() |> String.trim()
    format      = Map.get(conn.params, "format", "qjh")

    result =
      with :ok <- validate_aftn_addr("Address", destination),
           :ok <- validate_aftn_addr("Originator", originator) do
        tx_id  = build_tx_id(settings)
        filing = Calendar.strftime(DateTime.utc_now(), "%d%H%M")
        body   = test_msg_body(format, originator)
        params = %{"transmission_id" => tx_id, "priority" => "GG", "address_1" => destination,
                   "originator" => originator, "filing_time" => filing, "message" => body}
        attrs  = %{filed_by: "WEB_TEST", next_attempt_at: nil, udp_target_host: nil, udp_target_port: nil}
        raw = Tp.Aftn.Builder.build("AFTN_FREE", params)

        case Aftn.compose_and_enqueue("AFTN_FREE", params, attrs) do
          {:ok, _}    -> Tp.Aftn.OutgoingQueue.kick(); {:ok, raw}
          {:error, e} -> {:error, e}
        end
      end

    payload = case result do
      {:ok, raw}                 -> %{ok: true, raw: raw, sent_at: DateTime.to_iso8601(DateTime.utc_now())}
      {:error, {:validation, e}} -> %{ok: false, error: Enum.join(e, ", ")}
      {:error, e}                -> %{ok: false, error: inspect(e)}
    end

    conn
    |> put_resp_content_type("application/json")
    |> put_resp_header("cache-control", "no-store")
    |> send_resp(200, Jason.encode!(payload))
  end

  post "/test-message/svc" do
    settings  = Settings.get_or_default()
    destination = conn.params |> Map.get("address", "")      |> to_string() |> String.upcase() |> String.trim()
    originator  = conn.params |> Map.get("originator", "")   |> to_string() |> String.upcase() |> String.trim()
    message     = conn.params |> Map.get("svc_message", "")  |> to_string() |> String.upcase() |> String.trim()

    with :ok <- validate_aftn_addr("Address", destination),
         :ok <- validate_aftn_addr("Originator", originator),
         :ok <- (if message == "", do: {:error, "Message is required"}, else: :ok) do
      tx_id  = build_tx_id(settings)
      filing = Calendar.strftime(DateTime.utc_now(), "%d%H%M")
      params = %{"transmission_id" => tx_id, "priority" => "FF", "address_1" => destination,
                 "originator" => originator, "filing_time" => filing, "message" => message}
      attrs  = %{filed_by: "WEB_SVC", next_attempt_at: nil, udp_target_host: nil, udp_target_port: nil}
      raw = Tp.Aftn.Builder.build("AFTN_FREE", params)

      case Aftn.compose_and_enqueue("AFTN_FREE", params, attrs) do
        {:ok, _} ->
          Tp.Aftn.OutgoingQueue.kick()
          if wants_json?(conn) do
            conn
            |> put_resp_content_type("application/json")
            |> put_resp_header("cache-control", "no-store")
            |> send_resp(200, Jason.encode!(%{ok: true, raw: raw, sent_at: DateTime.to_iso8601(DateTime.utc_now())}))
          else
            redirect(conn, "/test-message?info=#{URI.encode_www_form("SVC TRAF sent to #{destination}")}")
          end
        {:error, {:validation, errs}} ->
          test_message_error(conn, Enum.join(errs, ", "))
        {:error, reason} ->
          test_message_error(conn, inspect(reason))
      end
    else
      {:error, msg} -> test_message_error(conn, msg)
    end
  end

  match _ do
    send_resp(conn, 404, "not found")
  end

  defp redirect(conn, location) do
    conn
    |> put_resp_header("location", location)
    |> send_resp(303, "")
  end

  defp dashboard_data(filters) do
    with {:ok, messages, pagination} <- safe_message_page(filters),
         {:ok, settings} <- safe_settings(),
         monitor_items <- Tp.Udp.Monitor.drain() do
      {:ok, messages, [], settings, monitor_items, pagination}
    end
  end

  defp safe_messages(opts) do
    {:ok, Aftn.list_messages(opts)}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_latest_messages(opts) do
    {:ok, Aftn.latest_messages(opts)}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_queue_messages(opts) do
    {:ok, Aftn.queue_messages(opts)}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_queue_messages_page(opts) do
    Aftn.queue_messages_page(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_queue_count do
    {:ok, Aftn.queue_count()}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_delete_message(id) do
    with {id, ""} <- Integer.parse(id) do
      Aftn.delete_message(id)
    else
      _ -> {:error, :not_found}
    end
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_delete_all_messages(opts) do
    Aftn.delete_all_messages(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_delete_queue_messages(opts) do
    Aftn.delete_queue_messages(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_message_page(opts) do
    {:ok, messages, pagination} = Aftn.list_messages_page(opts)
    {:ok, messages, build_pagination_urls(opts, pagination)}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_message(id) do
    {:ok, Aftn.get_message(String.to_integer(id))}
  rescue
    ArgumentError -> {:ok, nil}
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:error, :database_not_migrated}
  end

  defp safe_settings do
    {:ok, Settings.get_or_default()}
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:ok, Settings.default_setting()}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError ->
      {:ok, Settings.default_setting()}
  end

  defp filters(params) do
    [
      direction: Map.get(params, "direction", ""),
      type: Map.get(params, "type", ""),
      q: Map.get(params, "q", ""),
      date_from: Map.get(params, "date_from", ""),
      date_to: Map.get(params, "date_to", ""),
      page: Map.get(params, "page", "1"),
      page_size: Map.get(params, "page_size", "15"),
      after_id: Map.get(params, "after_id", ""),
      after_ts: Map.get(params, "after_ts", ""),
      history: Map.get(params, "history", "")
    ]
  end

  defp build_pagination_urls(opts, pagination) do
    page = Map.get(pagination, :page, parse_int(Keyword.get(opts, :page), 1))
    page_size = Map.get(pagination, :page_size, parse_int(Keyword.get(opts, :page_size), 15))
    total_pages = Map.get(pagination, :total_pages)

    if total_pages do
      pagination
      |> Map.put(:page, page)
      |> Map.put(:page_size, page_size)
      |> Map.put(:prev_url, numbered_page_url(opts, page - 1, page_size, total_pages))
      |> Map.put(:next_url, numbered_page_url(opts, page + 1, page_size, total_pages))
      |> Map.put(:page_urls, page_urls(opts, page_size, total_pages))
    else
      build_cursor_pagination_urls(opts, pagination, page, page_size)
    end
  end

  defp build_cursor_pagination_urls(opts, pagination, page, page_size) do
    current_cursor = cursor_from_opts(opts)
    history = decode_history(Keyword.get(opts, :history))
    next_cursor = Map.get(pagination, :next_cursor)

    prev_state = previous_state(history)
    next_history = encode_history(history ++ [current_cursor || %{"root" => true}])

    pagination
    |> Map.put(:page, page)
    |> Map.put(:page_size, page_size)
    |> Map.put(:prev_url, page_url(opts, page - 1, page_size, prev_state.cursor, prev_state.history))
    |> Map.put(:next_url, page_url(opts, page + 1, page_size, next_cursor, next_history))
  end

  defp numbered_page_url(_opts, page, _page_size, _total_pages) when page < 1, do: nil
  defp numbered_page_url(_opts, page, _page_size, total_pages) when page > total_pages, do: nil
  defp numbered_page_url(opts, page, page_size, _total_pages), do: page_url(opts, page, page_size, nil, "")

  defp page_urls(opts, page_size, total_pages) do
    for page <- 1..total_pages, do: {page, page_url(opts, page, page_size, nil, "")}
  end

  defp previous_state([]), do: %{cursor: nil, history: ""}

  defp previous_state(history) do
    {prev_cursor, prev_history} = List.pop_at(history, -1)
    %{cursor: normalize_history_cursor(prev_cursor), history: encode_history(prev_history)}
  end

  defp normalize_history_cursor(%{"root" => true}), do: nil
  defp normalize_history_cursor(cursor), do: cursor

  defp page_url(_opts, page, _page_size, _cursor, _history) when page < 1, do: nil
  defp page_url(_opts, _page, _page_size, nil, _history) when false, do: nil

  defp page_url(opts, page, page_size, cursor, history) do
    base =
      opts
      |> Keyword.take([:direction, :type, :q, :date_from, :date_to])
      |> Keyword.put(:page, page)
      |> Keyword.put(:page_size, page_size)
      |> Enum.reject(fn {_key, value} -> is_nil(value) or value == "" end)

    cursor_params =
      case cursor do
        nil -> []
        %{"id" => id, "inserted_at" => inserted_at} -> [after_id: id, after_ts: inserted_at]
        %{id: id, inserted_at: inserted_at} -> [after_id: id, after_ts: inserted_at]
        _ -> []
      end

    history_params = if history in [nil, ""], do: [], else: [history: history]

    "/?" <> URI.encode_query(base ++ cursor_params ++ history_params)
  end

  defp cursor_from_opts(opts) do
    after_id = Keyword.get(opts, :after_id)
    after_ts = Keyword.get(opts, :after_ts)

    if after_id in [nil, ""] or after_ts in [nil, ""] do
      nil
    else
      %{"id" => after_id, "inserted_at" => after_ts}
    end
  end

  defp decode_history(nil), do: []
  defp decode_history(""), do: []

  defp decode_history(encoded) do
    with {:ok, json} <- Base.url_decode64(encoded, padding: false),
         {:ok, list} when is_list(list) <- Jason.decode(json) do
      list
    else
      _ -> []
    end
  end

  defp encode_history([]), do: ""

  defp encode_history(history) do
    history
    |> Jason.encode!()
    |> Base.url_encode64(padding: false)
  end

  defp filters_to_path(filters) do
    params =
      filters
      |> Keyword.take([:direction, :type, :q, :date_from, :date_to, :page_size])
      |> Keyword.put(:page, 1)
      |> Enum.reject(fn {_key, value} -> is_nil(value) or value == "" end)

    case URI.encode_query(params) do
      "" -> "/"
      query -> "/?#{query}"
    end
  end

  defp return_path(path) when is_binary(path) do
    if String.starts_with?(path, "/"), do: path, else: "/"
  end

  defp return_path(_path), do: "/"

  defp with_notice(path, kind, message) do
    separator = if String.contains?(path, "?"), do: "&", else: "?"
    key = if kind == :error, do: "error", else: "info"
    "#{path}#{separator}#{key}=#{URI.encode_www_form(message)}"
  end

  defp message_json(message) do
    %{
      id: message.id,
      direction: safe_text(message.direction),
      cid: safe_text(message.cid),
      priority: safe_text(message.priority),
      message_type: message.message_type,
      originator: message.originator,
      destinations: message.destinations,
      aircraft_id: message.aircraft_id,
      departure: message.departure,
      departure_time: message.departure_time,
      destination: message.destination,
      route: message.route,
      sequence_no: message.sequence_no,
      status: safe_text(message.status),
      note: safe_text(Map.get(message, :note)),
      filed_by: safe_text(message.filed_by),
      raw_text: safe_text(message.raw_text),
      inserted_at: safe_time(message.inserted_at)
    }
  end

  defp udp_monitor_json(item) do
    %{
      kind: safe_text(Map.get(item, :kind)),
      source_ip: safe_text(Map.get(item, :source_ip)),
      source_port: safe_text(Map.get(item, :source_port)),
      raw: safe_text(Map.get(item, :raw)),
      time: safe_time(Map.get(item, :time))
    }
  end

  defp safe_time(nil), do: ""
  defp safe_time(%DateTime{} = value), do: DateTime.to_iso8601(value)
  defp safe_time(%NaiveDateTime{} = value), do: NaiveDateTime.to_iso8601(value)
  defp safe_time(value), do: safe_text(value)

  defp safe_text(nil), do: ""

  defp safe_text(value) when is_binary(value) do
    if String.valid?(value), do: value, else: inspect(value)
  end

  defp safe_text(value), do: to_string(value)

  defp parse_int(value, _default) when is_integer(value), do: value

  defp parse_int(value, default) when is_binary(value) do
    case Integer.parse(value) do
      {number, _} -> number
      :error -> default
    end
  end

  defp parse_int(_value, default), do: default

  defp transmit_attrs(params) do
    {:ok,
     %{
       filed_by: Map.get(params, "filed_by", "WEB"),
       next_attempt_at: scheduled_send_at(params),
       udp_target_host: nil,
       udp_target_port: nil
     }}
  end

  defp scheduled_send_at(params) do
    value =
      params
      |> Map.get("transmission_id", "")
      |> to_string()
      |> String.replace(~r/\D/, "")

    if String.length(value) == 6 do
      now = DateTime.utc_now()

      with {day, ""} <- value |> String.slice(0, 2) |> Integer.parse(),
           {hour, ""} <- value |> String.slice(2, 2) |> Integer.parse(),
           {minute, ""} <- value |> String.slice(4, 2) |> Integer.parse(),
           true <- day >= 1 and day <= 31 and hour in 0..23 and minute in 0..59,
           {:ok, date} <- Date.new(now.year, now.month, day),
           {:ok, time} <- Time.new(hour, minute, 0),
           {:ok, scheduled} <- DateTime.new(date, time, "Etc/UTC") do
        if DateTime.compare(scheduled, now) == :gt, do: scheduled, else: nil
      else
        _ -> nil
      end
    end
  end

  defp compose_submit("save", type, params, attrs), do: Aftn.compose_and_save(type, params, attrs)
  defp compose_submit(_action, type, params, attrs), do: Aftn.compose_and_enqueue(type, params, attrs)

  defp redirect_after_compose(params, type, action \\ "send")

  defp redirect_after_compose(%{"return_to" => "compose"} = params, type, action) do
    form = Map.get(params, "return_form", type)
    verb = if action == "save", do: "saved", else: "queued"
    clear = if action == "send_clear", do: "&clear=1", else: ""
    "/compose?form=#{URI.encode_www_form(form)}&info=#{URI.encode_www_form("#{redirect_type(type)} #{verb}")}#{clear}"
  end

  defp redirect_after_compose(_params, type, _action) do
    "/?direction=outbound&type=#{URI.encode_www_form(redirect_type(type))}"
  end

  defp error_redirect(%{"return_to" => "compose"} = params, type, message) do
    form = Map.get(params, "return_form", type)
    "/compose?form=#{URI.encode_www_form(form)}&error=#{URI.encode_www_form(message)}"
  end

  defp error_redirect(_params, _type, message) do
    "/?error=#{URI.encode_www_form(message)}"
  end

  defp redirect_type("AFTN_FREE"), do: "FREE"
  defp redirect_type(type), do: type

  defp notice(params) do
    cond do
      error = Map.get(params, "error") -> {:error, error}
      info = Map.get(params, "info") -> {:info, info}
      true -> nil
    end
  end

  defp safe_list_reg(opts) do
    Tp.Registration.list(opts)
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_crud_reg(:create, params) do
    case Tp.Registration.create(params) do
      {:ok, r} -> {:ok, r}
      {:error, cs} -> {:error, {:validation, changeset_errors(cs)}}
    end
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_crud_reg(:update, id, params) do
    with {n, ""} <- Integer.parse(to_string(id)), r when not is_nil(r) <- Tp.Registration.get(n) do
      case Tp.Registration.update(r, params) do
        {:ok, rec} -> {:ok, rec}
        {:error, cs} -> {:error, {:validation, changeset_errors(cs)}}
      end
    else _ -> {:error, :not_found}
    end
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_crud_reg(:delete, id) do
    with {n, ""} <- Integer.parse(to_string(id)), do: Tp.Registration.delete(n)
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_delete_all_reg(opts) do
    Tp.Registration.delete_all(opts)
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_list_route(opts) do
    Tp.RouteData.list(opts)
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_crud_route(:create, params) do
    case Tp.RouteData.create(params) do
      {:ok, r} -> {:ok, r}
      {:error, cs} -> {:error, {:validation, changeset_errors(cs)}}
    end
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_crud_route(:update, id, params) do
    with {n, ""} <- Integer.parse(to_string(id)), r when not is_nil(r) <- Tp.RouteData.get(n) do
      case Tp.RouteData.update(r, params) do
        {:ok, rec} -> {:ok, rec}
        {:error, cs} -> {:error, {:validation, changeset_errors(cs)}}
      end
    else _ -> {:error, :not_found}
    end
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_crud_route(:delete, id) do
    with {n, ""} <- Integer.parse(to_string(id)), do: Tp.RouteData.delete(n)
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_delete_all_route(opts) do
    Tp.RouteData.delete_all(opts)
  rescue
    error in MyXQL.Error -> if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_list_location(opts) do
    Tp.Location.list(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_create_location(params) do
    case Tp.Location.create(params) do
      {:ok, r} -> {:ok, r}
      {:error, cs} -> {:error, {:validation, changeset_errors(cs)}}
    end
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_update_location(id, params) do
    with {n, ""} <- Integer.parse(to_string(id)),
         record  when not is_nil(record) <- Tp.Location.get(n) do
      case Tp.Location.update(record, params) do
        {:ok, r} -> {:ok, r}
        {:error, cs} -> {:error, {:validation, changeset_errors(cs)}}
      end
    else
      _ -> {:error, :not_found}
    end
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_delete_location(id) do
    with {n, ""} <- Integer.parse(to_string(id)) do
      Tp.Location.delete(n)
    else
      _ -> {:error, :not_found}
    end
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_delete_all_location(opts) do
    Tp.Location.delete_all(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_list_aircraft(opts) do
    Tp.Aircraft.list(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_create_aircraft(params) do
    case Tp.Aircraft.create(params) do
      {:ok, record} -> {:ok, record}
      {:error, changeset} -> {:error, {:validation, changeset_errors(changeset)}}
    end
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_update_aircraft(id, params) do
    with {n, ""} <- Integer.parse(to_string(id)),
         record  when not is_nil(record) <- Tp.Aircraft.get(n) do
      case Tp.Aircraft.update(record, params) do
        {:ok, r} -> {:ok, r}
        {:error, changeset} -> {:error, {:validation, changeset_errors(changeset)}}
      end
    else
      _ -> {:error, :not_found}
    end
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_delete_aircraft(id) do
    with {n, ""} <- Integer.parse(to_string(id)) do
      Tp.Aircraft.delete(n)
    else
      _ -> {:error, :not_found}
    end
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_delete_all_aircraft(opts) do
    Tp.Aircraft.delete_all(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp changeset_errors(changeset) do
    Ecto.Changeset.traverse_errors(changeset, fn {msg, opts} ->
      Enum.reduce(opts, msg, fn {key, value}, acc ->
        String.replace(acc, "%{#{key}}", to_string(value))
      end)
    end)
    |> Enum.flat_map(fn {_field, msgs} -> msgs end)
  end

  defp undefined_table?(%MyXQL.Error{mysql: %{code: 1146}}), do: true
  defp undefined_table?(%MyXQL.Error{mysql: %{name: :ER_NO_SUCH_TABLE}}), do: true
  defp undefined_table?(_error), do: false

  defp validate_aftn_addr(field, addr) do
    cond do
      addr == "" -> {:error, "#{field} is required"}
      String.length(addr) != 8 -> {:error, "#{field} must be 8 letters"}
      not String.match?(addr, ~r/^[A-Z]{8}$/) -> {:error, "#{field} must be 8 uppercase letters (A-Z)"}
      true -> :ok
    end
  end

  defp build_tx_id(settings) do
    cid = (settings.cid || "TST") |> to_string() |> String.upcase()
    seq = (settings.tseq || "0001") |> to_string() |> String.replace(~r/\D/, "") |> String.pad_leading(4, "0") |> String.slice(0, 4)
    cid <> seq
  end

  defp wants_json?(conn) do
    conn
    |> Plug.Conn.get_req_header("accept")
    |> Enum.any?(&String.contains?(&1, "application/json"))
  end

  defp test_message_error(conn, message) do
    if wants_json?(conn) do
      conn
      |> put_resp_content_type("application/json")
      |> put_resp_header("cache-control", "no-store")
      |> send_resp(200, Jason.encode!(%{ok: false, error: message}))
    else
      redirect(conn, "/test-message?error=#{URI.encode_www_form(message)}")
    end
  end

  defp test_times(str) do
    case Integer.parse(str) do
      {n, _} when n > 0 -> min(n, 20)
      _ -> 1
    end
  end

  defp test_msg_body("qjh", _orig),
    do: "QJH RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY"

  defp test_msg_body("fox", _orig),
    do: "THE QUICK BROWN FOX JUMPED OVER THE LAZY DOG 1234567890"

  defp test_msg_body("de", _orig),
    do: "DE RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY RYRY"

  defp test_msg_body(_, _), do: "TEST"

  defp safe_list_abbr(opts) do
    Tp.Abbreviations.list(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_list_warnings(opts) do
    Tp.Warnings.list(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_delete_warning(id) do
    Tp.Warnings.delete(id)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end

  defp safe_delete_all_warnings(opts) do
    Tp.Warnings.delete_all(opts)
  rescue
    error in MyXQL.Error ->
      if undefined_table?(error), do: {:error, :database_not_migrated}, else: reraise(error, __STACKTRACE__)
    _error in DBConnection.ConnectionError -> {:error, :database_not_migrated}
  end
end
