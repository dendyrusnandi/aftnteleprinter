defmodule TpWeb.Views do
  def dashboard(messages, events, filters \\ [], notice \\ nil, settings \\ nil, received_messages \\ [], pagination \\ %{}) do
    settings = settings || Tp.Settings.default_setting()

    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>AFTN Teleprinter</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        body{margin:0;padding:56px 0 62px;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{position:fixed;top:0;left:0;right:0;z-index:900;height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px;box-shadow:0 6px 18px rgba(20,50,74,.18)}
        header a{color:white}#{header_time_css()}.top-nav{display:flex;align-items:center;gap:16px}.top-menu{position:relative}.top-menu summary{cursor:pointer;color:white;font-weight:600;list-style:none;display:flex;align-items:center;gap:6px}.top-menu summary::-webkit-details-marker{display:none}.menu-icon{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.top-submenu{position:absolute;top:28px;left:0;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 12px 26px rgba(23,32,42,.16);z-index:20;min-width:260px;padding:6px}.top-submenu a{display:block;color:#17202a;padding:8px 10px;border-radius:4px;white-space:nowrap}.top-submenu a:hover{background:#edf4fa;text-decoration:none}.compose-submenu{display:grid;grid-template-columns:1fr 1fr;min-width:520px}.compose-sep{grid-column:1/-1;height:0;border:none;border-top:1px solid #e4e8ee;margin:3px 0}.top-direct-link{color:white;font-weight:600;display:inline-flex;align-items:center;gap:6px;text-decoration:none;white-space:nowrap}.top-direct-link:hover{color:#d8e8f3;text-decoration:none}
        main{display:grid;grid-template-columns:1fr 360px;gap:16px;padding:16px}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;overflow:hidden}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        table{width:100%;border-collapse:collapse;font-size:13px} th,td{border-bottom:1px solid #edf0f3;padding:8px;text-align:left;vertical-align:top}
        th{background:#f0f3f6;color:#435466;font-weight:600;white-space:nowrap} code,textarea{font-family:ui-monospace,SFMono-Regular,Consolas,monospace}
        .table-wrap{overflow:auto}tbody tr[data-message-id]{cursor:pointer}tbody tr.row-selected td{background:#eaf4ff}.table-actions{white-space:nowrap;text-align:center;vertical-align:middle}.btn-small{display:inline-flex;align-items:center;justify-content:center;gap:6px;background:#14324a;color:white;border:0;border-radius:4px;padding:5px 9px;font-size:12px;font-weight:700;cursor:pointer}.btn-small:hover{background:#195b86;text-decoration:none}.btn-small.pdf{background:#195b86}.btn-small.danger{background:#b42318}.btn-small.danger:hover{background:#8a1f11}.icon-action{width:30px;height:30px;padding:0}.icon-action svg,.btn-small svg{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.icon-action .pdf-text{font-size:7px;font-weight:800;fill:currentColor;stroke:none}.inline-form{display:inline;margin:0;padding:0}.empty-row{text-align:center;color:#6d7b88;padding:18px}.table-footer{display:flex;align-items:center;justify-content:space-between;gap:12px;padding:10px 12px;border-top:1px solid #e4e8ee;background:#fbfcfd;font-size:13px;flex-wrap:wrap}.footer-actions{display:flex;align-items:center;gap:8px}.pagination{display:flex;align-items:center;gap:6px;flex-wrap:wrap}.pagination a,.pagination span{border:1px solid #cbd3dc;background:white;border-radius:4px;padding:6px 9px;color:#17202a}.pagination a:hover{background:#edf4fa;text-decoration:none}.pagination .active{background:#14324a;color:white;border-color:#14324a;font-weight:700}.pagination .disabled{color:#9aa6b2;background:#f0f3f6}.page-size{display:flex;align-items:center;gap:6px;padding:0}.page-size select{padding:5px 7px}.modal-backdrop{display:none;position:fixed;inset:0;background:rgba(10,18,28,.55);z-index:1000;align-items:center;justify-content:center;padding:16px}.modal-backdrop.open{display:flex}.modal-card{background:white;border-radius:8px;box-shadow:0 22px 60px rgba(0,0,0,.28);width:min(920px,96vw);max-height:90vh;overflow:hidden;border:1px solid #d8dee6}.modal-head{display:flex;align-items:center;justify-content:space-between;gap:12px;background:#14324a;color:white;padding:11px 14px}.modal-head h3{margin:0;font-size:15px}.modal-close{background:transparent;color:white;border:0;font-size:22px;line-height:1;cursor:pointer;padding:0 4px}.modal-body{padding:14px;overflow:auto;max-height:calc(90vh - 48px)}.modal-grid{display:grid;grid-template-columns:140px minmax(0,1fr) 140px minmax(0,1fr);gap:0;border:1px solid #edf0f3;border-bottom:0;margin-bottom:12px}.modal-grid dt,.modal-grid dd{border-bottom:1px solid #edf0f3;margin:0;padding:7px 9px}.modal-grid dt{background:#f0f3f6;color:#435466;font-weight:700}.modal-raw{margin:0;border:1px solid #d8dee6;border-radius:6px;background:#fbfcfd;padding:12px;white-space:pre-wrap;max-height:430px;overflow:auto}.modal-error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11;border-radius:4px;padding:10px 12px}@media(max-width:720px){.modal-grid{grid-template-columns:130px minmax(0,1fr)}}
        .action-menu{position:relative;display:inline-flex}.action-menu .js-action-toggle{background:transparent;color:#435466;border:0;border-radius:0;box-shadow:none}.action-menu .js-action-toggle:hover{background:transparent;color:#14324a}.btn-outline{background:white;color:#435466;border:1px solid #cbd3dc}.btn-outline:hover{background:#edf4fa;color:#14324a}.action-popup{display:none;position:absolute;right:0;top:28px;z-index:60;min-width:132px;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 12px 26px rgba(23,32,42,.18);padding:5px}.action-menu.open .action-popup{display:block}.action-item{display:flex;align-items:center;gap:8px;width:100%;box-sizing:border-box;background:white;color:#17202a;border:0;border-radius:4px;padding:7px 9px;font-size:12px;font-weight:700;text-align:left;cursor:pointer}.action-item:hover{background:#edf4fa;text-decoration:none}.action-item.danger{color:#b42318}.action-item svg{width:15px;height:15px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.action-item .pdf-text{font-size:7px;font-weight:800;fill:currentColor;stroke:none}
        textarea,input,select{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:8px;background:white}
        textarea{width:100%;min-height:130px}
        form{padding:12px} button{background:#1c6b4f;color:white;border:0;border-radius:4px;padding:8px 12px;font-weight:600}
        label{display:block;font-size:12px;color:#435466;font-weight:600;margin-bottom:4px}.grid2{display:grid;grid-template-columns:1fr 1fr;gap:8px}.grid4{display:grid;grid-template-columns:repeat(4,1fr);gap:8px}.field{margin-bottom:8px}.field input,.field select{width:100%}
        .filters{display:flex;flex-wrap:wrap;gap:8px;padding:12px;border-bottom:1px solid #e4e8ee;align-items:end;background:#fbfcfd;max-width:100%;box-sizing:border-box}.filters .field{margin-bottom:0;min-width:0}.filters .filter-cid{flex:0 0 72px}.filters .filter-seq{flex:0 0 90px}.filters .filter-text{flex:1 1 220px}.filters .filter-date{flex:0 0 145px}.filters .filter-filed{flex:1 1 130px}.filters .filter-select{flex:0 0 112px}.filters input,.filters select{width:100%;height:34px}.filters button,.filters .clear-filter{height:36px;align-self:end;box-sizing:border-box;display:inline-flex;align-items:center;justify-content:center;gap:7px;white-space:nowrap;flex:0 0 auto;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1}.filters button{background:#14324a;color:white;border:1px solid #14324a}.filters button:hover{background:#195b86;border-color:#195b86}.filters .clear-filter{background:white;color:#17202a;border:1px solid #cbd3dc}.filters .clear-filter:hover{background:#edf4fa;text-decoration:none}.filters button i,.filters .clear-filter i{font-size:15px;line-height:1}.filters .search-label{font-size:11px;color:#435466;font-weight:800;text-transform:uppercase;letter-spacing:.02em}
        .notice{margin:12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
        .hint{font-size:12px;color:#6d7b88;margin:4px 0 10px}
        .message-cell{max-width:560px}.message-preview{display:inline;white-space:pre-wrap;word-break:break-word}.read-more{display:inline;border:0;background:transparent;color:#195b86;padding:0 0 0 6px;font-size:12px;font-weight:700;cursor:pointer}.read-more:hover{text-decoration:underline}.muted{color:#6d7b88}.events{padding:12px}.event{border-bottom:1px solid #edf0f3;padding:8px 0}
        .status-footer{position:fixed;left:12px;right:12px;bottom:10px;z-index:850;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 10px 28px rgba(20,50,74,.18);overflow:hidden}.status-panel{padding:7px 9px}.status-summary{display:flex;align-items:center;gap:7px;min-width:0;white-space:nowrap;overflow:auto}.status-card{display:flex;align-items:center;gap:6px;border:1px solid #e1e6ed;background:#fbfcfd;border-radius:5px;padding:5px 8px;min-width:max-content}.status-label{font-size:12px;color:#6d7b88;font-weight:700;text-transform:uppercase}.status-value{font-size:12px;font-weight:700;color:#17202a}.status-card.ok .status-value{color:#1c6b4f}.status-card.warn .status-value{color:#b42318}.status-card.info .status-value{color:#195b86}.status-pill{display:inline-flex;align-items:center;gap:5px;border-radius:999px;padding:2px 7px;background:#f0f3f6;font-size:11px;font-weight:700}.status-dot{width:7px;height:7px;border-radius:50%;background:#b42318;display:inline-block}.status-pill.up .status-dot{background:#1c6b4f}.status-pill.down .status-dot{background:#b42318}.status-actions{margin-left:auto;display:flex;align-items:center}.status-actions a{height:34px;box-sizing:border-box;display:inline-flex;align-items:center;justify-content:center;background:#14324a;color:white;border:0;border-radius:4px;padding:0 12px;font-size:13px;font-weight:700;line-height:1;white-space:nowrap;min-width:76px}.status-actions a:hover{text-decoration:none;background:#195b86}
        .udp-monitor{margin-top:0}.section-head{display:flex;align-items:center;justify-content:space-between;gap:10px;border-bottom:1px solid #e4e8ee}.section-head h2{border:0}.icon-button{display:inline-flex;align-items:center;justify-content:center;width:30px;height:30px;margin-right:10px;border:1px solid #cbd3dc;border-radius:4px;background:#fbfcfd;color:#14324a;padding:0;cursor:pointer}.icon-button:hover{background:#edf4fa}.udp-monitor-body{padding:9px}.udp-item{border:1px solid #e1e6ed;background:#fbfcfd;border-radius:6px;margin-bottom:7px;overflow:hidden}.udp-meta{display:flex;justify-content:space-between;gap:8px;padding:6px 8px;background:#f0f3f6;color:#435466;font-size:12px;font-weight:700}.udp-source{display:inline-flex;align-items:center;gap:4px}.udp-source i{color:#195b86;font-size:13px;line-height:1}.udp-raw{margin:0;padding:7px 8px;max-height:110px;overflow:auto;white-space:pre-wrap;font-size:12px;line-height:1.15}.udp-empty{padding:12px;border:1px dashed #cbd3dc;border-radius:6px;color:#6d7b88;font-size:13px}
        @media(max-width:900px){main{grid-template-columns:1fr}.grid2,.grid4{grid-template-columns:1fr}.filters{display:grid;grid-template-columns:repeat(2,minmax(0,1fr))}.filters .field,.filters .filter-cid,.filters .filter-seq,.filters .filter-text,.filters .filter-date,.filters .filter-filed,.filters .filter-select{flex:auto}.filters button,.filters .clear-filter{width:100%}}@media(max-width:560px){.filters{grid-template-columns:1fr}}
      </style>
    </head>
    <body>
      <header><h1>AFTN Teleprinter</h1><nav class="top-nav">#{compose_dropdown()}#{test_message_link()}#{maintenance_dropdown()}#{views_dropdown()}#{status_dropdown()}</nav>#{header_time()}</header>
      <div id="sound-settings" hidden data-sound-enabled="#{html(settings.sound_enabled)}" data-alarm-repeat="#{html(settings.alarm_repeat_count || 1)}"></div>
      <main>
        <section>
          <h2>AFTN Message</h2>
          #{notice_banner(notice)}
          #{filter_form(filters)}
          <div class="table-wrap">
            <table>
              <thead><tr><th>DateTime</th><th>IO</th><th>CID</th><th>Seq</th><th>Message</th><th>Status</th><th>Note</th><th>Filed By</th><th>Action</th></tr></thead>
              <tbody id="message-table-body" data-page-size="#{html(Map.get(pagination, :page_size, 15))}">#{message_rows(messages)}</tbody>
            </table>
          </div>
          #{message_detail_templates(messages)}
          #{pagination_controls(filters, pagination)}
        </section>
        <aside>
          #{udp_receive_monitor(received_messages, events)}
        </aside>
      </main>
      #{status_panel(events, settings)}
      #{header_clock_script()}
      #{status_footer_script()}
      #{message_modal_script()}
      #{auto_uppercase_script()}
    </body>
    </html>
    """
  end

  def message_detail(message) do
    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>AFTN Message #{html(message.id)}</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}.back-btn{display:inline-flex;align-items:center;gap:6px;font-weight:700;text-decoration:none}.back-btn:hover{color:#d8e8f3;text-decoration:none}.back-btn i{font-size:18px;line-height:1}.msg-title{display:inline-flex;align-items:center;gap:8px}.msg-title i{color:#9bd0ff;font-size:18px;line-height:1}
        #{header_time_css()} main{max-width:1100px;margin:16px auto;padding:0 16px}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:16px;overflow:visible}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        dl{display:grid;grid-template-columns:180px 1fr;margin:0} dt,dd{border-bottom:1px solid #edf0f3;padding:8px 12px;margin:0}
        dt{background:#f0f3f6;color:#435466;font-weight:600} pre{margin:0;padding:14px;white-space:pre-wrap;font-family:ui-monospace,SFMono-Regular,Consolas,monospace}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header><a class="back-btn" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a><h1 class="msg-title"><i class="bi bi-envelope-text"></i><span>Message #{html(message.id)}</span></h1>#{header_time()}</header>
      <main>
        <section>
          <h2>Header</h2>
          <dl>
            #{detail_item("Direction", message.direction)}
            #{detail_item("Priority", message.priority)}
            #{detail_item("Type", message.message_type)}
            #{detail_item("Originator", message.originator)}
            #{detail_item("Destinations", Enum.join(message.destinations || [], " "))}
            #{detail_item("Aircraft", message.aircraft_id)}
            #{detail_item("Departure", join_pair(message.departure, message.departure_time))}
            #{detail_item("Destination", message.destination)}
            #{detail_item("Route", message.route)}
            #{detail_item("Sequence", message.sequence_no)}
            #{detail_item("Status", message.status)}
            #{detail_item("Attempts", message.transmit_attempts)}
            #{detail_item("Last Error", message.last_error)}
            #{detail_item("Next Attempt", message.next_attempt_at)}
            #{detail_item("UDP Target", udp_target(message))}
            #{detail_item("Legacy", legacy_ref(message))}
            #{detail_item("Inserted", message.inserted_at)}
          </dl>
        </section>
        <section>
          <h2>Raw Message</h2>
          <pre>#{html(message.raw_text)}</pre>
        </section>
      </main>
      #{status_panel([], nil)}
      #{status_footer_script()}
      #{header_clock_script()}
      #{auto_uppercase_script()}
    </body>
    </html>
    """
  end

  def message_pdf(message) do
    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>AFTN Message PDF #{html(message.id)}</title>
      <style>
        body{margin:24px;font-family:Arial,sans-serif;color:#111}
        .head{display:flex;justify-content:space-between;gap:16px;border-bottom:2px solid #14324a;padding-bottom:10px;margin-bottom:14px}
        h1{font-size:18px;margin:0;color:#14324a}.muted{color:#555;font-size:12px}
        table{width:100%;border-collapse:collapse;margin-bottom:14px;font-size:12px}
        th,td{border:1px solid #cfd6df;padding:6px 8px;text-align:left;vertical-align:top}
        th{width:140px;background:#eef3f7;color:#24384a}
        pre{border:1px solid #cfd6df;background:#fafafa;padding:12px;white-space:pre-wrap;font-family:Consolas,monospace;font-size:12px;line-height:1.25}
        .actions{margin-bottom:14px}.actions button{background:#14324a;color:white;border:0;border-radius:4px;padding:8px 12px;font-weight:700}
        @media print{body{margin:12mm}.actions{display:none}}
      </style>
    </head>
    <body>
      <div class="actions"><button type="button" onclick="window.print()">Export PDF</button></div>
      <div class="head">
        <div>
          <h1>AFTN Message</h1>
          <div class="muted">ID #{html(message.id)}</div>
        </div>
        <div class="muted">#{html(format_time(message.inserted_at))}</div>
      </div>
      <table>
        #{pdf_row("DateTime", format_time(message.inserted_at))}
        #{pdf_row("IO", message.direction)}
        #{pdf_row("CID", message.cid)}
        #{pdf_row("Seq", message.sequence_no)}
        #{pdf_row("Type", message.message_type)}
        #{pdf_row("Priority", message.priority)}
        #{pdf_row("Originator", message.originator)}
        #{pdf_row("Status", message.status)}
        #{pdf_row("Note", Map.get(message, :note))}
        #{pdf_row("Filed By", message.filed_by)}
      </table>
      <pre>#{html(visible_aftn(message.raw_text))}</pre>
      <script>window.addEventListener('load', function ( ) { window.setTimeout(function ( ) { window.print(); }, 250); });</script>
    </body>
    </html>
    """
  end

  def compose_page(notice \\ nil, selected \\ "AFTN_FREE", params \\ %{}, received_messages \\ []) do
    selected = selected |> to_string() |> String.upcase()

    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Message</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        body{margin:0;padding-bottom:104px;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}.compose-back{display:inline-flex;align-items:center;gap:6px;font-weight:700}.compose-back:hover{text-decoration:none;color:#d8e8f3}.compose-back i{font-size:18px;line-height:1}.compose-title{display:inline-flex;align-items:center;gap:8px}.compose-title .menu-icon{width:18px;height:18px;stroke:#9bd0ff;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}
        #{header_time_css()} main{max-width:1280px;margin:0 auto;padding:16px}

        .compose-main section>h2,.amo-title,.aftn-title{background:#f7f9fb!important;color:#17202a!important;border-bottom:1px solid #e4e8ee;border-radius:8px 8px 0 0}.amo-title i,.aftn-title i{color:#195b86!important}.amo-window,.aftn-window,.compose-main section{border-radius:8px;overflow:visible!important}.compose-main section{box-shadow:0 12px 28px rgba(20,50,74,.08)}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:16px;overflow:visible}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        form{padding:12px} button{background:#1c6b4f;color:white;border:0;border-radius:4px;padding:8px 12px;font-weight:600}
        textarea,input,select{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:8px;background:white;width:100%}
        textarea{min-height:84px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace}
        label{display:block;font-size:12px;color:#435466;font-weight:600;margin-bottom:4px}
        .grid2{display:grid;grid-template-columns:1fr 1fr;gap:8px}.grid4{display:grid;grid-template-columns:repeat(4,1fr);gap:8px}
        .field{margin-bottom:8px}.toolbar{display:flex;flex-wrap:wrap;gap:8px;margin-bottom:12px}.toolbar a{background:white;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px}.toolbar a.active{background:#14324a;color:white;border-color:#14324a}
        .notice{margin:0 0 12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
        .hint{font-size:12px;color:#6d7b88;margin:4px 0 10px}.actions{position:sticky;top:8px;z-index:600;display:flex;justify-content:flex-end;gap:8px;align-items:center;margin:-12px -12px 12px;padding:10px 12px;background:#f7f9fb;border-bottom:1px solid #e4e8ee;box-shadow:0 8px 18px rgba(20,50,74,.10)}.subhead{background:#f0f3f6;border:1px solid #e4e8ee;border-radius:4px;padding:8px;margin:4px 0 10px;font-size:13px;color:#435466}
        .compose-layout{display:grid;grid-template-columns:minmax(0,1fr) 380px;gap:14px;align-items:start}.compose-main{min-width:0}.compose-side{position:sticky;top:12px;min-width:0}.compose-side .udp-monitor{margin:0;background:white;border:1px solid #d8dee6;border-radius:8px;overflow:hidden;box-shadow:0 12px 28px rgba(20,50,74,.08)}.section-head{display:flex;align-items:center;justify-content:space-between;gap:10px;border-bottom:1px solid #e4e8ee;background:#fbfcfd}.section-head h2{border:0}.icon-button{display:inline-flex;align-items:center;justify-content:center;width:30px;height:30px;margin-right:10px;border:1px solid #cbd3dc;border-radius:4px;background:white;color:#14324a;padding:0;cursor:pointer}.icon-button:hover{background:#edf4fa}.icon-button svg{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.udp-monitor-body{padding:9px;max-height:calc(100vh - 178px);overflow:auto}.udp-item{border:1px solid #e1e6ed;background:#fbfcfd;border-radius:6px;margin-bottom:7px;overflow:hidden}.udp-meta{display:flex;justify-content:space-between;gap:8px;padding:6px 8px;background:#f0f3f6;color:#435466;font-size:12px;font-weight:700}.udp-source{display:inline-flex;align-items:center;gap:4px}.udp-source i{color:#195b86;font-size:13px;line-height:1}.udp-raw{margin:0;padding:7px 8px;max-height:150px;overflow:auto;white-space:pre-wrap;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-size:12px;line-height:1.2}.udp-empty{padding:12px;border:1px dashed #cbd3dc;border-radius:6px;color:#6d7b88;font-size:13px;background:#fbfcfd}
        .compose-head{border-bottom:1px solid #e4e8ee;padding:10px 12px;background:#fbfcfd}.compose-head h2{border:0;padding:0 0 2px}.form-note{font-size:11px;color:#195b86;font-style:italic}.required label{color:#005ce6}.form-band{background:#fbfcfd;border:1px solid #e4e8ee;border-radius:6px;padding:10px;margin-bottom:10px}.send-row{display:grid;grid-template-columns:120px 1fr;gap:8px}.address-line{display:grid;grid-template-columns:110px 1fr;gap:10px;align-items:start}.address-grid{display:grid;grid-template-columns:repeat(7,1fr);gap:6px}.address-grid input{text-transform:uppercase;text-align:left;padding:6px}.header-meta{display:grid;grid-template-columns:120px 150px 1fr;gap:8px}.free-text-large textarea{min-height:230px}.filled-row{border-top:1px solid #e4e8ee;margin-top:10px;padding-top:10px;max-width:220px}.compose-submit{display:flex;gap:8px;align-items:center;margin-top:10px}.compose-submit button[type=button]{background:#f0f3f6;color:#17202a;border:1px solid #cbd3dc}.amo-window{background:white;border:1px solid #d8dee6;border-radius:8px;box-shadow:0 12px 28px rgba(20,50,74,.08);overflow:visible}.amo-title{display:flex;align-items:center;gap:9px;background:#14324a;color:white;font-size:15px;font-weight:800;padding:12px 14px}.amo-title i{font-size:18px;color:#9bd0ff}.amo-form{padding:0}.amo-toolbar{position:sticky;top:8px;z-index:600;display:flex;justify-content:flex-end;gap:8px;padding:10px 12px;background:#f7f9fb;border-bottom:1px solid #e4e8ee;box-shadow:0 8px 18px rgba(20,50,74,.10);flex-wrap:wrap}.amo-tool{display:inline-flex;align-items:center;justify-content:center;gap:7px;background:white;color:#17202a;border:1px solid #cbd3dc;border-radius:6px;height:36px;padding:0 14px;box-sizing:border-box;min-width:0;font-size:13px;font-weight:700;line-height:1;cursor:pointer}.amo-tool i{font-size:15px;line-height:1}.amo-tool:hover{background:#edf4fa;text-decoration:none}.amo-tool.primary{background:#1c6b4f;color:white;border-color:#1c6b4f}.amo-tool.primary:hover{background:#17583f}.amo-tool.save{color:#14324a}.amo-tool.discard{color:#8a1f11}.amo-tool.close{color:#435466}.amo-body{padding:12px}.amo-editor-head{display:flex;align-items:center;justify-content:space-between;gap:8px;margin-bottom:6px}.amo-editor-head label{margin:0;color:#435466;font-size:12px;font-weight:800;text-transform:uppercase}.amo-editor-head span{color:#6d7b88;font-size:12px}.aftn-textarea{min-height:320px;background:#fbfcfd;border-color:#cbd3dc;border-radius:6px;resize:vertical;font-size:14px;line-height:1.5}.amo-footer{display:flex;align-items:end;justify-content:flex-start;gap:12px;border-top:1px solid #e4e8ee;margin-top:12px;padding-top:12px;flex-wrap:wrap}.amo-filled{width:260px}.amo-filled label{color:#435466;font-size:12px;font-weight:800;text-transform:uppercase}.amo-filled input{height:36px}
        .aftn-window{background:white;border:1px solid #d8dee6;border-radius:8px;box-shadow:0 12px 28px rgba(20,50,74,.08);overflow:visible}.aftn-title{display:flex;align-items:center;gap:9px;background:#14324a;color:white;font-size:15px;font-weight:800;padding:12px 14px}.aftn-title i{font-size:18px;color:#9bd0ff}.aftn-form{padding:0}.aftn-toolbar{position:sticky;top:8px;z-index:600;display:flex;justify-content:flex-end;gap:8px;padding:10px 12px;background:#f7f9fb;border-bottom:1px solid #e4e8ee;box-shadow:0 8px 18px rgba(20,50,74,.10);flex-wrap:wrap}.aftn-tool{display:inline-flex;align-items:center;justify-content:center;gap:7px;background:white;color:#17202a;border:1px solid #cbd3dc;border-radius:6px;height:36px;padding:0 14px;box-sizing:border-box;font-size:13px;font-weight:700;line-height:1;cursor:pointer}.aftn-tool i{font-size:15px;line-height:1}.aftn-tool:hover{background:#edf4fa;text-decoration:none}.aftn-tool.primary{background:#1c6b4f;color:white;border-color:#1c6b4f}.aftn-tool.primary:hover{background:#17583f}.aftn-tool.save{color:#14324a}.aftn-tool.discard{color:#8a1f11}.aftn-tool.close{color:#435466}.aftn-body{padding:12px}.aftn-required-note{font-size:12px;color:#195b86;font-style:italic;margin-bottom:10px}.aftn-topline{display:grid;grid-template-columns:180px;gap:12px;align-items:end;margin-bottom:10px}.tx-id-display{min-height:34px;box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;background:#f0f3f6;color:#14324a;padding:7px 9px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-size:13px;font-weight:900;display:flex;align-items:center}.aftn-card{border:1px solid #e4e8ee;border-radius:7px;background:#fbfcfd;padding:10px;margin-bottom:12px}.aftn-card-title{font-size:12px;font-weight:800;text-transform:uppercase;color:#435466;margin-bottom:8px}.aftn-address-row{display:grid;grid-template-columns:90px 1fr;gap:12px;align-items:start}.aftn-address-grid{display:grid;grid-template-columns:repeat(7,1fr);gap:6px}.aftn-address-grid input{text-transform:uppercase;text-align:left;padding:6px}.aftn-meta{display:grid;grid-template-columns:150px 160px 1fr 90px;gap:8px;align-items:end;margin-top:10px}.aftn-bell{display:flex;align-items:center;gap:8px;height:36px}.aftn-bell input{width:auto}.time-control{display:flex;gap:6px}.time-control input{flex:1}.time-button{display:inline-flex;align-items:center;justify-content:center;width:38px;background:white;color:#14324a;border:1px solid #cbd3dc;border-radius:4px;padding:0;cursor:pointer}.time-button:hover{background:#edf4fa}.aftn-editor-head{display:flex;align-items:center;justify-content:space-between;gap:8px;margin-bottom:6px}.aftn-editor-head label{margin:0;color:#435466;font-size:12px;font-weight:800;text-transform:uppercase}.aftn-editor-head span{color:#6d7b88;font-size:12px}.aftn-textarea{min-height:320px;background:#fbfcfd;border-color:#cbd3dc;border-radius:6px;resize:vertical;font-size:14px;line-height:1.5}.aftn-footer{display:flex;align-items:end;justify-content:flex-start;gap:12px;border-top:1px solid #e4e8ee;margin-top:12px;padding-top:12px;flex-wrap:wrap}.aftn-filled{width:260px}.aftn-filled label{color:#435466;font-size:12px;font-weight:800;text-transform:uppercase}.aftn-filled input{height:36px}
        .alr-scroll{overflow:auto;border-top:1px solid #e4e8ee}.alr-page{padding:12px;border-bottom:1px solid #e4e8ee}.alr-page-title{font-size:13px;font-weight:900;color:#14324a;margin:0 0 10px;text-transform:uppercase}.alr-grid{display:flex;flex-direction:column;gap:10px}.alr-row{display:grid;gap:10px;align-items:end}.alr-row-head{grid-template-columns:1.25fr 1.1fr 1.2fr 1fr 1.8fr}.alr-row-5{grid-template-columns:1.15fr .9fr .85fr 1fr 1fr}.alr-row-5b{grid-template-columns:.8fr 1.1fr .9fr 1.7fr 1.5fr}.alr-row-2{grid-template-columns:1fr 1fr}.alr-row-3{grid-template-columns:repeat(3,1fr)}.alr-row-4{grid-template-columns:repeat(4,1fr)}.alr-row-18{grid-template-columns:1.4fr 1fr 1.4fr}.alr-row-sup{grid-template-columns:1fr 1fr 2fr}.alr-wide{grid-column:span 2}.alr-full{grid-column:1/-1}.alr-checks{display:flex;flex-wrap:wrap;align-items:center;gap:10px;min-height:36px}.alr-checks label,.alr-inline-check{display:inline-flex;align-items:center;gap:5px;margin:0;color:#17202a;font-size:13px;font-weight:700}.alr-checks input,.alr-inline-check input{width:auto}.alr-text{min-height:54px;resize:vertical}.alr-picker{margin-top:6px;padding:6px;font-size:12px}.alr-control{display:flex;align-items:stretch;width:100%}.alr-control input,.alr-control select,.alr-control textarea{flex:1;min-width:0}.alr-control.compact input{width:50%;flex:1 1 50%}.equipment-control .equipment-open{width:40px;flex:0 0 40px;background:white;color:#14324a;border:1px solid #cbd3dc;border-left:0;border-radius:0 4px 4px 0;padding:0;display:inline-flex;align-items:center;justify-content:center;cursor:pointer}.equipment-control .equipment-open:hover{background:#edf4fa}.equipment-control input{border-top-right-radius:0;border-bottom-right-radius:0}.alr-mark{display:inline-flex;align-items:center;justify-content:center;min-width:28px;padding:0 8px;border:1px solid #cbd3dc;background:#f0f3f6;color:#14324a;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-weight:800}.alr-mark:first-child{border-radius:4px 0 0 4px;border-right:0}.alr-mark:last-child{border-radius:0 4px 4px 0;border-left:0}.alr-control .alr-mark:first-child+input,.alr-control .alr-mark:first-child+select,.alr-control .alr-mark:first-child+textarea{border-top-left-radius:0;border-bottom-left-radius:0}.alr-control input:not(:last-child),.alr-control select:not(:last-child),.alr-control textarea:not(:last-child){border-top-right-radius:0;border-bottom-right-radius:0}
        .equipment-modal{display:none;position:fixed;inset:0;z-index:1200;background:rgba(10,18,28,.52);align-items:center;justify-content:center;padding:16px}.equipment-modal.open{display:flex}.equipment-card{width:min(760px,96vw);max-height:90vh;background:white;border:1px solid #d8dee6;border-radius:8px;box-shadow:0 24px 70px rgba(0,0,0,.28);display:flex;flex-direction:column;overflow:hidden}.equipment-head{display:flex;align-items:center;justify-content:space-between;gap:12px;background:#14324a;color:white;padding:11px 14px}.equipment-head strong{font-size:14px}.equipment-x{background:transparent;color:white;border:0;border-radius:0;padding:4px;display:inline-flex;align-items:center;justify-content:center;cursor:pointer}.equipment-body{padding:12px;overflow:auto}.equipment-group{border:1px solid #e4e8ee;border-radius:6px;margin-bottom:10px;overflow:hidden}.equipment-group-title{background:#f0f3f6;color:#14324a;font-size:12px;font-weight:900;text-transform:uppercase;padding:7px 9px}.equipment-list{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:0}.equipment-row{display:grid;grid-template-columns:22px 48px 1fr;gap:7px;align-items:center;margin:0;padding:7px 9px;border-top:1px solid #edf0f3;cursor:pointer;color:#17202a;font-size:12px;font-weight:600}.equipment-row:nth-child(odd){border-right:1px solid #edf0f3}.equipment-row:hover{background:#edf4fa}.equipment-row input{width:auto}.equipment-code{font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-weight:900;color:#14324a}.equipment-desc{font-weight:600;color:#435466}.equipment-notes{background:#fffbe6;border:1px solid #ffe58f;border-radius:6px;padding:8px 10px;color:#5f4400;font-size:12px}.equipment-notes p{margin:0 0 5px}.equipment-notes p:last-child{margin-bottom:0}.equipment-actions{display:flex;justify-content:flex-end;gap:8px;padding:10px 12px;background:#f7f9fb;border-top:1px solid #e4e8ee;flex-wrap:wrap}
        .rcf-body{border:1px solid #d8dee6;background:#fbfcfd;border-radius:6px;padding:12px;margin-top:10px}.rcf-row-main{grid-template-columns:1.25fr 1.2fr 1.1fr .8fr .8fr}.rcf-message-type input:first-of-type{max-width:64px}.rcf-message-type input:not(:first-of-type){min-width:0}.rcf-radio textarea{min-height:74px}.rcf-filled{max-width:260px;margin-top:10px}
        main{max-width:1560px;padding:14px}form{padding:10px}h2{font-size:15px;padding:11px 14px}button{padding:8px 12px;font-size:13px}textarea,input,select{padding:7px 9px;font-size:13px;min-height:34px}textarea{min-height:78px}label{font-size:12px;margin-bottom:4px}.field{margin-bottom:8px}.grid2,.grid4{gap:8px}.toolbar{gap:8px;margin-bottom:10px}.toolbar a{font-size:13px;padding:7px 10px}.actions,.aftn-toolbar,.amo-toolbar{top:0;gap:8px;padding:8px 10px}.aftn-tool,.amo-tool{gap:7px;height:36px;padding:0 14px;font-size:13px}.aftn-tool i,.amo-tool i{font-size:15px}.aftn-title,.amo-title{font-size:15px;padding:11px 14px}.aftn-title i,.amo-title i{font-size:18px}.aftn-body,.amo-body{padding:12px}.aftn-required-note{font-size:12px;margin-bottom:9px}.aftn-topline{grid-template-columns:170px;gap:9px;margin-bottom:9px}.tx-id-display{min-height:34px;padding:7px 9px}.aftn-card,.rcf-body,.form-band{padding:10px;margin-bottom:10px}.aftn-card-title{font-size:12px;margin-bottom:8px}.aftn-address-row{grid-template-columns:90px 1fr;gap:12px}.aftn-address-grid,.address-grid{gap:6px}.aftn-address-grid input,.address-grid input{padding:6px 7px;min-height:33px}.aftn-meta{grid-template-columns:145px 155px 1fr 102px;gap:8px;margin-top:9px}.aftn-bell{height:34px;display:flex;align-items:center;gap:7px;font-weight:800;color:#14324a}.aftn-bell i{font-size:16px;color:#b26a00}.time-control{gap:6px}.time-button{width:36px}.alr-scroll{}.alr-page{padding:10px}.alr-page-title{font-size:13px;margin-bottom:8px}.alr-grid{gap:8px}.alr-row{gap:8px}.alr-checks{gap:9px;min-height:34px}.alr-checks label,.alr-inline-check{font-size:13px}.alr-text{min-height:58px}.alr-picker{margin-top:6px;padding:6px;font-size:12px}.alr-mark{min-width:26px;padding:0 7px}.rcf-radio textarea{min-height:70px}.rcf-filled{margin-top:7px}.aftn-textarea{min-height:285px;font-size:14px}.amo-footer,.aftn-footer{margin-top:10px;padding-top:10px}.amo-filled input,.aftn-filled input{height:34px}
        .amo-toolbar,.aftn-toolbar{position:sticky!important;top:0!important;z-index:1200!important;background:rgba(247,249,251,.96)!important;backdrop-filter:blur(6px);border-bottom:1px solid #d8dee6!important;box-shadow:0 10px 22px rgba(20,50,74,.16)!important}.amo-window,.aftn-window{overflow:visible!important}.compose-layout,.compose-main{overflow:visible!important}.compose-main{padding-bottom:96px}.amo-body,.aftn-body{padding-bottom:20px}.amo-footer,.aftn-footer{margin-bottom:18px}.section-head{background:#f7f9fb!important;border-radius:8px 8px 0 0}
        @media(max-width:900px){.grid2,.grid4,.send-row,.header-meta{grid-template-columns:1fr}}
        @media(max-width:900px){.alr-row,.alr-row-head,.alr-row-5,.alr-row-5b,.alr-row-2,.alr-row-3,.alr-row-4,.alr-row-18,.alr-row-sup,.rcf-row-main{grid-template-columns:1fr}.alr-wide{grid-column:auto}}
        @media(max-width:1100px){.compose-layout{grid-template-columns:1fr}.compose-side{position:static}.udp-monitor-body{max-height:360px}}
        @media(max-width:900px){.compose-head,.address-line{display:block}.address-grid,.aftn-address-grid{grid-template-columns:repeat(2,1fr)}.aftn-topline,.aftn-address-row,.aftn-meta{grid-template-columns:1fr}}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header><a class="compose-back" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a><h1 class="compose-title">#{write_icon()}<span>Message</span></h1>#{header_time()}</header>
      <main>
        #{notice_banner(notice)}
        <nav class="toolbar">#{compose_type_links(selected)}</nav>
        <div class="compose-layout">
          <div class="compose-main">#{compose_form(selected, params)}</div>
          <aside class="compose-side">#{udp_receive_monitor(received_messages, [])}</aside>
        </div>
      </main>
      #{status_panel([], nil)}
      #{status_footer_script()}
      #{header_clock_script()}
      #{compose_page_script()}
      #{compose_udp_monitor_script()}
      #{auto_uppercase_script()}
    </body>
    </html>
    """
  end

  def queue_page(messages, q \\ "", notice \\ nil, pagination \\ %{}) do
    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Queue / Not Delivered Messages</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        body{margin:0;padding-bottom:104px;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}.queue-back{display:inline-flex;align-items:center;gap:6px;font-weight:700}.queue-back:hover{text-decoration:none;color:#d8e8f3}.queue-back i{font-size:18px;line-height:1}.queue-title{display:inline-flex;align-items:center;gap:8px}.queue-title i{color:#9bd0ff;font-size:18px;line-height:1}#{header_time_css()} main{max-width:1280px;margin:0 auto;}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:16px;overflow:visible}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        table{width:100%;border-collapse:collapse;font-size:13px} th,td{border-bottom:1px solid #edf0f3;padding:7px 8px;text-align:left;vertical-align:top}
        th{background:#f0f3f6;color:#435466;font-weight:700;white-space:nowrap}.table-wrap{overflow:auto}.muted{color:#6d7b88}.empty-row{text-align:center;color:#6d7b88;padding:18px}.message-preview{white-space:pre-wrap;word-break:break-word}
        form.queue-search{display:flex;align-items:end;gap:8px;padding:12px;border-bottom:1px solid #e4e8ee;background:#fbfcfd;flex-wrap:wrap}
        label{display:block;font-size:12px;color:#435466;font-weight:700;margin-bottom:4px} input{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:8px;background:white}
        button,.btn{display:inline-flex;align-items:center;justify-content:center;gap:7px;background:#14324a;color:white;border:1px solid #14324a;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer;height:36px;box-sizing:border-box}.queue-search .btn i{font-size:15px;line-height:1}.btn.secondary{background:white;color:#17202a;border:1px solid #cbd3dc}.btn:hover{text-decoration:none;background:#195b86;border-color:#195b86}.btn.secondary:hover{background:#edf4fa}
        .queue-footer{display:flex;align-items:center;justify-content:space-between;gap:12px;padding:10px 12px;border-top:1px solid #e4e8ee;background:#fbfcfd;font-size:13px;font-weight:700;color:#195b86;flex-wrap:wrap}.queue-footer-left{display:flex;align-items:center;gap:12px;flex-wrap:wrap}.queue-pagination{display:flex;align-items:center;gap:6px;flex-wrap:wrap}.queue-pagination a,.queue-pagination span{border:1px solid #cbd3dc;background:white;border-radius:4px;padding:6px 9px;color:#17202a;font-weight:700}.queue-pagination a:hover{background:#edf4fa;text-decoration:none}.queue-pagination .active{background:#14324a;color:white;border-color:#14324a}.queue-pagination .disabled{color:#9aa6b2;background:#f0f3f6}.queue-page-info{color:#435466;font-weight:700}tbody tr[data-message-id]{cursor:pointer}tbody tr.row-selected td{background:#eaf4ff}
        .table-actions{white-space:nowrap;text-align:center;vertical-align:middle}.btn-small{display:inline-flex;align-items:center;justify-content:center;gap:6px;background:#14324a;color:white;border:0;border-radius:4px;padding:5px 9px;font-size:12px;font-weight:700;cursor:pointer}.btn-small:hover{background:#195b86;text-decoration:none}.icon-action{width:30px;height:30px;padding:0}.icon-action svg,.btn-small svg{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.btn-small .pdf-text{font-size:7px;font-weight:800;fill:currentColor;stroke:none}.btn-outline{background:white;color:#435466;border:1px solid #cbd3dc}.btn-outline:hover{background:#edf4fa;color:#14324a}.inline-form{display:inline;margin:0;padding:0}
        .action-menu{position:relative;display:inline-flex}.action-menu .js-action-toggle{background:transparent;color:#435466;border:0;border-radius:0;box-shadow:none}.action-menu .js-action-toggle:hover{background:transparent;color:#14324a}.action-popup{display:none;position:absolute;right:0;top:28px;z-index:60;min-width:132px;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 12px 26px rgba(23,32,42,.18);padding:5px}.action-menu.open .action-popup{display:block}.action-item{display:flex;align-items:center;gap:8px;width:100%;box-sizing:border-box;background:white;color:#17202a;border:0;border-radius:4px;padding:7px 9px;font-size:12px;font-weight:700;text-align:left;cursor:pointer}.action-item:hover{background:#edf4fa;text-decoration:none}.action-item.danger{color:#b42318}.action-item svg{width:15px;height:15px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.action-item .pdf-text{font-size:7px;font-weight:800;fill:currentColor;stroke:none}
        .modal-backdrop{display:none;position:fixed;inset:0;background:rgba(10,18,28,.55);z-index:1000;align-items:center;justify-content:center;padding:16px}.modal-backdrop.open{display:flex}.modal-card{background:white;border-radius:8px;box-shadow:0 22px 60px rgba(0,0,0,.28);width:min(920px,96vw);max-height:90vh;overflow:hidden;border:1px solid #d8dee6}.modal-head{display:flex;align-items:center;justify-content:space-between;gap:12px;background:#14324a;color:white;padding:11px 14px}.modal-head h3{margin:0;font-size:15px}.modal-close{background:transparent;color:white;border:0;font-size:22px;line-height:1;cursor:pointer;padding:0 4px}.modal-body{padding:14px;overflow:auto;max-height:calc(90vh - 48px)}.modal-grid{display:grid;grid-template-columns:140px minmax(0,1fr) 140px minmax(0,1fr);gap:0;border:1px solid #edf0f3;border-bottom:0;margin-bottom:12px}.modal-grid dt,.modal-grid dd{border-bottom:1px solid #edf0f3;margin:0;padding:7px 9px}.modal-grid dt{background:#f0f3f6;color:#435466;font-weight:700}.modal-raw{margin:0;border:1px solid #d8dee6;border-radius:6px;background:#fbfcfd;padding:12px;white-space:pre-wrap;max-height:430px;overflow:auto}.modal-error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11;border-radius:4px;padding:10px 12px}@media(max-width:720px){.modal-grid{grid-template-columns:130px minmax(0,1fr)}}
        .status-waiting{color:#b26a00;font-weight:700}.status-retrying{color:#8a1f11;font-weight:700}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header><a class="queue-back" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a><h1 class="queue-title"><i class="bi bi-envelope-exclamation"></i><span>Queue / Not Delivered Messages</span></h1>#{header_time()}</header>
      <main>
        <section>
          <h2>Search Queue/Not Delivered Messages</h2>
          #{notice_banner(notice)}
          <form class="queue-search" method="get" action="/queue">
            <div>
              <label>AIRCRAFT ID</label>
              <input name="q" value="#{html(q)}" autofocus>
            </div>
            <button class="btn" type="submit"><i class="bi bi-search"></i><span>Search</span></button>
            <a class="btn secondary" href="/queue"><i class="bi bi-eraser"></i><span>Clear</span></a>
          </form>
        </section>
        <section>
          <h2>List of Queue/Not Delivered Messages</h2>
          <div class="table-wrap">
            <table>
              <thead><tr><th>Message Type</th><th>Filing</th><th>Send At</th><th>Originator</th><th>Aircraft</th><th>Message</th><th>Status</th><th>Date/Time</th><th>Action</th></tr></thead>
              <tbody>#{queue_message_rows(messages)}</tbody>
            </table>
          </div>
          #{message_detail_templates(messages)}
          <div class="queue-footer">
            <div class="queue-footer-left">
              <span>#{html(Map.get(pagination, :total_count, length(messages)))} Element(s) in this table</span>
              #{queue_pagination_controls(q, pagination)}
            </div>
            #{queue_delete_all_form(q)}
          </div>
        </section>
      </main>
      #{status_panel([], nil)}
      #{header_clock_script()}
      #{status_footer_script()}
      #{message_modal_script()}
      #{auto_uppercase_script()}
    </body>
    </html>
    """
  end

  def setup_required do
    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta http-equiv="refresh" content="5">
      <title>AFTN Teleprinter — Database Unavailable</title>
      <style>
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        #{header_time_css()} main{max-width:760px;margin:16px auto;background:white;border:1px solid #d8dee6;border-radius:6px;padding:24px}
        header h1{font-size:18px;margin:0} main h1{font-size:22px;margin:0 0 12px} p{line-height:1.55} code,pre{font-family:ui-monospace,SFMono-Regular,Consolas,monospace}
        pre{background:#eef2f6;border:1px solid #d8dee6;border-radius:4px;padding:12px;overflow:auto}
        .retry-bar{display:flex;align-items:center;gap:10px;background:#fff8e1;border:1px solid #ffe082;border-radius:6px;padding:10px 14px;margin-bottom:18px;font-size:13px;color:#5f4400}
        .spinner{width:16px;height:16px;border:2px solid #ffe082;border-top-color:#b26a00;border-radius:50%;animation:spin 1s linear infinite;flex-shrink:0}
        @keyframes spin{to{transform:rotate(360deg)}}
        .migrate-box{margin-top:16px;background:#f0f3f6;border:1px solid #d8dee6;border-radius:6px;padding:14px}
        .migrate-box h2{font-size:14px;margin:0 0 8px;color:#435466}
              #{status_footer_css()}
      </style>
    </head>
    <body>
      <header><h1>AFTN Teleprinter</h1>#{header_time()}</header>
      <main>
        <div class="retry-bar">
          <div class="spinner"></div>
          <span>Connecting to MySQL database&hellip; this page will automatically refresh every 5 seconds.</span>
        </div>
        <h1>Database Unavailable</h1>
        <p>The application is running, but the MySQL connection has not been established. Possible causes:</p>
        <ul>
          <li>MySQL is not running &mdash; please wait, the page will refresh automatically.</li>
          <li>Tables have not been created &mdash; run migrations after MySQL is up:</li>
        </ul>
        <div class="migrate-box">
          <h2>Run migrations ( once, after MySQL is running):</h2>
          <pre>cd aftn_elixir\nmix ecto.migrate</pre>
        </div>
      </main>
      #{status_panel([], nil)}
      #{status_footer_script()}
      #{header_clock_script()}
      #{auto_uppercase_script()}
    </body>
    </html>
    """
  end

  def test_message_page(notice \\ nil, settings \\ nil, recent \\ [], received_messages \\ [], _initial_outbox \\ nil) do
    settings = settings || Tp.Settings.default_setting()
    origin   = ( settings.originator || "WAJJYFYC") |> to_string() |> String.upcase()

    """
    <!doctype html>
    <html lang="en">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Test Message &amp; SVC TRAF</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}.tst-back{display:inline-flex;align-items:center;gap:6px;font-weight:700}.tst-back:hover{text-decoration:none;color:#d8e8f3}.tst-back i{font-size:18px;line-height:1}.tst-title{display:inline-flex;align-items:center;gap:8px}.tst-title i{color:#9bd0ff;font-size:18px;line-height:1}
        #{header_time_css()} main{max-width:1240px;margin:0 auto;padding:16px 16px 92px}.test-layout{display:grid;grid-template-columns:minmax(0,1fr) 360px;gap:14px;align-items:start}.test-main{min-width:0}.test-side{position:sticky;top:12px;min-width:0}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:14px;overflow:visible}
        h1{font-size:18px;margin:0} h2{font-size:14px;font-weight:800;margin:0;padding:10px 14px;border-bottom:1px solid #e4e8ee;background:#f7f9fb;border-radius:6px 6px 0 0}
        .tst-body{padding:12px}
        .tst-row{display:flex;align-items:center;gap:8px;margin-bottom:8px;flex-wrap:wrap}
        .tst-row-top{align-items:flex-start}
        .tst-label{min-width:110px;font-size:12px;font-weight:700;color:#435466;flex-shrink:0}
        .tst-times{width:58px;box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:5px 7px;font-size:13px;text-align:center}
        .tst-hint{font-size:12px;color:#6d7b88;font-style:italic}
        .tst-addr{width:120px;box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;text-transform:uppercase;letter-spacing:.06em}
        .format-col{display:flex;flex-direction:column;gap:5px}
        .radio-label{display:inline-flex;align-items:center;gap:5px;font-size:13px;cursor:pointer}
        .tst-btns{margin-top:6px;gap:6px}
        .btn-ok{display:inline-flex;align-items:center;justify-content:center;gap:7px;min-width:96px;height:36px;background:#14324a;color:white;border:1px solid #14324a;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer}.btn-ok:hover{background:#195b86;border-color:#195b86}
        .btn-cancel{display:inline-flex;align-items:center;justify-content:center;gap:7px;min-width:96px;height:36px;background:white;color:#435466;border:1px solid #cbd3dc;border-radius:6px;font-size:13px;font-weight:700;line-height:1;text-decoration:none}.btn-cancel:hover{background:#edf4fa;text-decoration:none}.btn-ok i,.btn-cancel i{font-size:15px;line-height:1}
        .tst-status{font-size:12px;font-weight:700;color:#195b86;min-height:16px;padding:4px 0 0}
        .tst-status.err{color:#b42318}.tst-status.ok{color:#1c6b4f}
        .svc-row{display:flex;gap:8px;align-items:flex-start;margin-top:6px}
        .tst-svc{flex:1;min-height:70px;box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:7px 8px;font-size:13px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;resize:vertical;text-transform:uppercase}
        .btn-send{display:inline-flex;align-items:center;justify-content:center;gap:7px;min-width:96px;height:36px;background:#1c6b4f;color:white;border:1px solid #1c6b4f;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer;white-space:nowrap;align-self:flex-start;box-sizing:border-box}.btn-send:hover{background:#17583f;border-color:#17583f}
        .outbox-pre{margin:0;padding:12px;max-height:240px;overflow:auto;white-space:pre-wrap;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-size:12px;line-height:1.4;background:#fbfcfd}
        .outbox-empty{padding:12px;color:#6d7b88;font-size:13px}
        .test-side .udp-monitor{margin:0;background:white;border:1px solid #d8dee6;border-radius:8px;overflow:hidden;box-shadow:0 12px 28px rgba(20,50,74,.08)}.section-head{display:flex;align-items:center;justify-content:space-between;gap:10px;border-bottom:1px solid #e4e8ee;background:#fbfcfd}.section-head h2{border:0;background:transparent}.icon-button{display:inline-flex;align-items:center;justify-content:center;width:30px;height:30px;margin-right:10px;border:1px solid #cbd3dc;border-radius:4px;background:white;color:#14324a;padding:0;cursor:pointer}.icon-button:hover{background:#edf4fa}.icon-button svg{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.udp-monitor-body{padding:9px;max-height:calc(100vh - 152px);overflow:auto}.udp-item{border:1px solid #e1e6ed;background:#fbfcfd;border-radius:6px;margin-bottom:7px;overflow:hidden}.udp-meta{display:flex;justify-content:space-between;gap:8px;padding:6px 8px;background:#f0f3f6;color:#435466;font-size:12px;font-weight:700}.udp-source{display:inline-flex;align-items:center;gap:4px}.udp-source i{color:#195b86;font-size:13px;line-height:1}.udp-raw{margin:0;padding:7px 8px;max-height:150px;overflow:auto;white-space:pre-wrap;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-size:12px;line-height:1.2}.udp-empty{padding:12px;border:1px dashed #cbd3dc;border-radius:6px;color:#6d7b88;font-size:13px;background:#fbfcfd}
        .notice{margin:0 0 12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
        input[type=text]{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;background:white;font-size:13px}
        @media(max-width:1100px){.test-layout{grid-template-columns:1fr}.test-side{position:static}.udp-monitor-body{max-height:360px}}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header>
        <a class="tst-back" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a>
        <h1 class="tst-title"><i class="bi bi-envelope-check"></i><span>Test Message &amp; SVC TRAF</span></h1>
        #{header_time()}
      </header>
      <main>
        #{notice_banner(notice)}
        <div class="test-layout">
          <div class="test-main">

            <section>
              <h2>Test Message</h2>
              <div class="tst-body">
                <div class="tst-row">
                  <span class="tst-label">State :</span>
                  <label class="radio-label"><input type="radio" id="state-start" name="state" value="start" checked> Start</label>
                  <label class="radio-label"><input type="radio" id="state-stop"  name="state" value="stop"> Stop</label>
                  <input class="tst-times" type="text" id="tst-times" value="1" maxlength="3" inputmode="numeric">
                  <span class="tst-hint">times &nbsp;( 0 = continuous )</span>
                </div>
                <div class="tst-row tst-row-top">
                  <span class="tst-label">Test format :</span>
                  <div class="format-col">
                    <label class="radio-label"><input type="radio" name="format" value="qjh" checked> QJH RYRY&hellip;</label>
                    <label class="radio-label"><input type="radio" name="format" value="fox"> QUICK BROWN FOX&hellip;</label>
                    <label class="radio-label"><input type="radio" name="format" value="de"> DE RYRY&hellip;</label>
                  </div>
                </div>
                <div class="tst-row">
                  <span class="tst-label">Origin :</span>
                  <input class="tst-addr" type="text" id="tst-originator" value="#{html(origin)}" maxlength="8" autocomplete="off">
                </div>
                <div class="tst-row tst-btns">
                  <span class="tst-label"></span>
                  <button class="btn-ok" type="button" id="btn-test-ok"><i class="bi bi-check2-circle"></i><span>OK</span></button>

                </div>
                <div class="tst-status" id="tst-status"></div>
              </div>
            </section>

            <section>
              <h2>SVC TRAF</h2>
              <form class="tst-body" method="post" action="/test-message/svc" id="svc-form">
                <div class="tst-row">
                  <span class="tst-label">Address :</span>
                  <input class="tst-addr" type="text" name="address" id="svc-address" value="#{html(origin)}" maxlength="8" autocomplete="off">
                </div>
                <input type="hidden" name="originator" id="svc-originator" value="#{html(origin)}">
                <div class="svc-row">
                  <textarea class="tst-svc" name="svc_message" rows="4" placeholder="SVC TRAF message..."></textarea>
                </div>
                 <br>
                  <button class="btn-send" type="submit"><i class="bi bi-send"></i><span>Send</span></button>
                   <a class="btn-cancel" href="/"><i class="bi bi-x-circle"></i><span>Cancel</span></a>
              </form>
            </section>

            <section>
              <h2>Outbox</h2>
              #{test_outbox(recent)}
            </section>
          </div>
          <aside class="test-side">#{udp_receive_monitor(received_messages, [])}</aside>
        </div>
      </main>
      #{status_panel([], nil)}
      #{header_clock_script()}
      #{status_footer_script()}
      #{auto_uppercase_script()}
      #{compose_udp_monitor_script()}
      <script>
        ( function ( ) {
          var timer    = null;
          var sent     = 0;
          var target   = 0;

          var btnOK      = document.getElementById('btn-test-ok');
          var stopRadio  = document.getElementById('state-stop');
          var statusEl   = document.getElementById('tst-status');
          var svcOrigEl  = document.getElementById('svc-originator');
          var svcForm    = document.getElementById('svc-form');
          var outboxItems = [];
          var divider    = '\\n----------------------------------------\\n';

          function setStatus(msg, cls) {
            if ( !statusEl) return;
            statusEl.textContent = msg;
            statusEl.className = 'tst-status' + ( cls ? ' ' + cls : '');
          }

          function clearTimer() {
            if ( timer) { clearInterval(timer); timer = null; }
          }

          function syncSvcOrigin() {
            var orig = document.getElementById('tst-originator');
            if ( orig && svcOrigEl) svcOrigEl.value = orig.value.trim().toUpperCase();
          }

          function readTestData() {
            var orig = document.getElementById('tst-originator');
            var fmt  = document.querySelector('[name="format"]:checked');
            return {
              originator: orig ? orig.value.trim().toUpperCase() : '',
              format:     fmt  ? fmt.value : 'qjh'
            };
          }

          function readSvcData() {
            var addr = document.getElementById('svc-address');
            var orig = document.getElementById('tst-originator');
            return {
              address:    addr ? addr.value.trim().toUpperCase() : '',
              originator: orig ? orig.value.trim().toUpperCase() : '',
              format:     'svc'
            };
          }

          function validate(data) {
            if ( !data.address    || data.address.length    !== 8 || !/^[A-Z]{8}$/.test(data.address))    return 'Address must be 8 letters ( A-Z)';
            if ( !data.originator || data.originator.length !== 8 || !/^[A-Z]{8}$/.test(data.originator)) return 'Originator must be 8 letters ( A-Z)';
            return null;
          }

          function validateOrigin(data) {
            if ( !data.originator || data.originator.length !== 8 || !/^[A-Z]{8}$/.test(data.originator)) return 'Originator must be 8 letters ( A-Z)';
            return null;
          }

          function visibleAftn(value) {
            return String(value == null ? '' : value)
              .replace(/\\u0001/g, '[SOH]')
              .replace(/\\u0002/g, '[STX]')
              .replace(/\\u0003/g, '[ETX]')
              .replace(/\\u000b/g, '[VT]')
              .replace(/\\r\\n/g, '\\n')
              .replace(/\\r/g, '\\n')
              .trim();
          }

          function testBody(format) {
            var uLine = 'U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*U*';
            var foxLine = 'THE QUICK BROWN FOX JUMPS OVER THE LAZY DOGS 1234567890';
            var line = format === 'fox' ? foxLine : uLine;
            return [line, line, line].join('\\n');
          }

          function testPreview(data) {
            var origin = data.originator || '';
            if ( data.format === 'de') return 'DE ' + origin + ' ' + origin + ' ' + origin + '\\n' + testBody(data.format);
            return 'QJH ' + origin + '\\n' + testBody(data.format);
          }

          function renderOutbox(items) {
            var outbox = document.getElementById('test-outbox-pre');
            if ( !outbox) return;
            if ( !items.length) {
              outbox.textContent = outbox.getAttribute('data-empty') || '';
              return;
            }
            outbox.textContent = items.map(function ( item) {
              return visibleAftn(item.raw || '');
            }).join(divider);
          }

          function addOutbox(raw, sentAt, kind) {
            if ( !raw) return;
            outboxItems = outboxItems.filter(function ( item) {
              return !( item.raw === raw && item.sent_at === sentAt);
            });
            outboxItems.unshift({kind: kind || 'MESSAGE', raw: raw, sent_at: sentAt || new Date().toISOString()});
            outboxItems = outboxItems.slice(0, 20);
            renderOutbox(outboxItems);
          }

          function formBody(form) {
            var pairs = [];
            var fields = form.querySelectorAll('input, textarea, select');
            for ( var i = 0; i < fields.length; i++) {
              var field = fields[i];
              if ( !field.name || field.disabled) continue;
              if (( field.type === 'checkbox' || field.type === 'radio') && !field.checked) continue;
              pairs.push(encodeURIComponent(field.name) + '=' + encodeURIComponent(field.value || ''));
            }
            return pairs.join('&');
          }

          function sendOne(data, cb) {
            fetch('/test-message/send-one', {
              method: 'POST',
              headers: {'Content-Type': 'application/x-www-form-urlencoded'},
              credentials: 'same-origin',
              body: 'originator=' + encodeURIComponent(data.originator) +
                    '&format='     + encodeURIComponent(data.format)
            })
            .then(parseJsonResponse)
            .then(function ( j) { cb(j.ok ? null : ( j.error || 'unknown error'), j); })
            .catch(function ( error) { cb(error && error.message ? error.message : 'Network error'); });
          }

          function parseJsonResponse(r) {
            return r.text().then(function ( text) {
              var json = {};
              try { json = text ? JSON.parse(text) : {}; }
              catch ( _error) { json = {ok:false, error:text || 'Invalid server response'}; }
              if ( !r.ok && !json.error) json.error = 'HTTP ' + r.status;
              return json;
            });
          }

          renderOutbox(outboxItems);

          if ( stopRadio) {
            stopRadio.addEventListener('change', function ( ) {
              if ( stopRadio.checked) { clearTimer(); setStatus('Stopped. Sent: ' + sent + ' message(s).', 'ok'); }
            });
          }

          if ( svcForm) {
            svcForm.addEventListener('submit', function ( event) {
              event.preventDefault();
              syncSvcOrigin();
              var data = readSvcData();
              var err = validate(data);
              var message = svcForm.querySelector('[name="svc_message"]');
              if ( err) { setStatus(err, 'err'); return; }
              if ( !message || !message.value.trim()) { setStatus('SVC message is required', 'err'); return; }
              setStatus('Sending SVC...', '');

              fetch('/test-message/svc', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', 'Accept': 'application/json'},
                credentials: 'same-origin',
                body: formBody(svcForm)
              })
                .then(parseJsonResponse)
                .then(function ( j) {
                  if ( !j.ok) { setStatus('Error: ' + ( j.error || 'unknown error'), 'err'); return; }
                  addOutbox(j.raw, j.sent_at, j.kind || 'SVC TRAF');
                  message.value = '';
                  setStatus('SVC sent.', 'ok');
                })
                .catch(function ( ) { setStatus('Network error', 'err'); });
            });
          }

          if ( btnOK) {
            btnOK.addEventListener('click', function ( ) {
              var stateEl = document.querySelector('[name="state"]:checked');
              if ( !stateEl) { setStatus('Please select State ( Start or Stop)', 'err'); return; }

              if ( stateEl.value === 'stop') {
                clearTimer();
                setStatus('Stopped. Sent: ' + sent + ' message(s).', 'ok');
                return;
              }

              var data = readTestData();
              var err  = validateOrigin(data);
              if ( err) { setStatus(err, 'err'); return; }

              var fmt = document.querySelector('[name="format"]:checked');
              if ( !fmt) { setStatus('Select a test format', 'err'); return; }

              var t = parseInt((document.getElementById('tst-times') || {value:'1'}).value, 10);
              target = ( isNaN(t) || t < 0) ? 1 : t;
              sent   = 0;

              clearTimer();
              syncSvcOrigin();
              setStatus(target > 0 ? 'Sending 1/' + target + '...' : 'Sending ( continuous)...', '');

              function tick() {
                sendOne(data, function ( e, payload) {
                  if ( e) { clearTimer(); setStatus('Error: ' + e, 'err'); return; }
                  addOutbox(( payload && payload.raw) || testPreview(data), payload && payload.sent_at, ( payload && payload.kind) || 'TEST MESSAGE');
                  sent++;
                  if ( target > 0) {
                    setStatus('Sending ' + sent + '/' + target + '...', '');
                    if ( sent >= target) { clearTimer(); setStatus('Done. Sent ' + sent + ' message(s).', 'ok'); }
                  } else {
                    setStatus('Sending ( continuous) - sent: ' + sent, '');
                  }
                });
              }

              tick();
              if ( target !== 1) timer = setInterval(tick, 1000);
            });
          }
        })();
      </script>
    </body>
    </html>
    """
  end

  defp test_outbox([]) do
    ~s(<pre id="test-outbox-pre" class="outbox-pre" data-empty="No local test/SVC messages yet.">No local test/SVC messages yet.</pre>)
  end

  defp test_outbox(_messages), do: test_outbox([])

  def icao_abbreviations_page(items, pagination, q, mean, notice) do
    total       = Map.get(pagination, :total, 0)
    page        = Map.get(pagination, :page, 1)
    total_pages = Map.get(pagination, :total_pages, 1)

    pl = fn p -> "/icao-abbreviations?" <> URI.encode_query(%{q: q, mean: mean, page: p}) end
    first_link = if page > 1,          do: ~s(<a href="#{pl.(1)}"            class="pg-btn" title="First">&#171;</a>),    else: ~s(<span class="pg-btn disabled">&#171;</span>)
    prev_link  = if page > 1,          do: ~s(<a href="#{pl.(page-1)}"       class="pg-btn" title="Previous">&#8249;</a>), else: ~s(<span class="pg-btn disabled">&#8249;</span>)
    next_link  = if page < total_pages, do: ~s(<a href="#{pl.(page+1)}"      class="pg-btn" title="Next">&#8250;</a>),    else: ~s(<span class="pg-btn disabled">&#8250;</span>)
    last_link  = if page < total_pages, do: ~s(<a href="#{pl.(total_pages)}" class="pg-btn" title="Last">&#187;</a>),     else: ~s(<span class="pg-btn disabled">&#187;</span>)

    rows = if items == [] do
      ~s(<tr><td colspan="2" class="ac-empty">No records found.</td></tr>)
    else
      Enum.map_join(items, "", fn r ->
        """
        <tr>
          <td style="width:180px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-weight:600">#{html(r.abbr)}</td>
          <td>#{html(r.mean)}</td>
        </tr>
        """
      end)
    end

    """
    <!doctype html>
    <html lang="en">
    <head>
      <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
      <title>ICAO Abbreviations and Codes</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        #{maint_page_css()}
        .abbr-input{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;text-transform:uppercase;letter-spacing:.06em;width:120px}
        .mean-input{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;width:340px}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header>
        <a class="back-btn" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a>
        <h1 class="ac-title"><i class="bi bi-book"></i><span>ICAO Abbreviations and Codes ( PANS-ABC)</span></h1>
        #{header_time()}
      </header>
      <main>
        #{notice_banner(notice)}
        <section>
          <h2>Search Abbreviations and Codes</h2>
          <form class="ac-search" method="get" action="/icao-abbreviations">
            <div class="ac-field">
              <label class="ac-label">Abbreviations</label>
              <input class="abbr-input" name="q" value="#{html(q)}" placeholder="e.g. ACC" autocomplete="off">
            </div>
            <div class="ac-field">
              <label class="ac-label">Meaning</label>
              <input class="mean-input" name="mean" value="#{html(mean)}" placeholder="Search meaning..." autocomplete="off">
            </div>
            <button class="btn btn-search" type="submit"><i class="bi bi-search"></i> Search</button>
            <a class="btn btn-clear" href="/icao-abbreviations"><i class="bi bi-eraser"></i><span>Clear</span></a>
          </form>
        </section>
        <section>
          <h2>List of Abbreviation(s)</h2>
          <table>
            <thead><tr><th>Abbreviations</th><th>Meaning</th></tr></thead>
            <tbody>#{rows}</tbody>
          </table>
          <div class="ac-footer">
            <span class="ac-count">#{total} Element(s) in this table</span>
            <div class="pg">
              #{first_link}#{prev_link}
              <span class="pg-info">Page #{page} of #{total_pages}</span>
              #{next_link}#{last_link}
              <span class="pg-goto">Go to <input id="abbr-goto" type="number" min="1" max="#{total_pages}" value="#{page}"></span>
            </div>
          </div>
        </section>
      </main>
      #{status_panel([], nil)}
      #{header_clock_script()}
      <script>
        ( function(){
          var gi = document.getElementById('abbr-goto');
          if ( gi) gi.addEventListener('keydown', function(e) {
            if ( e.key === 'Enter') {
              var p = parseInt(gi.value, 10) || 1;
              var params = new URLSearchParams(window.location.search || '');
              params.set('page', p);
              window.location.href = '/icao-abbreviations?' + params.toString();
            }
          });
        })();
      </script>
    </body>
    </html>
    """
  end

  def warning_messages_page(items, pagination, msg, reason, date_fr, date_to, notice) do
    total       = Map.get(pagination, :total, 0)
    page        = Map.get(pagination, :page, 1)
    total_pages = Map.get(pagination, :total_pages, 1)

    today = Date.utc_today() |> Date.to_string()
    eff_date_fr = if date_fr == "", do: today, else: date_fr
    eff_date_to = if date_to == "", do: today, else: date_to

    pl = fn p -> "/warning-messages?" <> URI.encode_query(%{msg: msg, reason: reason, date_fr: eff_date_fr, date_to: eff_date_to, page: p}) end
    first_link = if page > 1,          do: ~s(<a href="#{pl.(1)}"            class="pg-btn" title="First">&#171;</a>),    else: ~s(<span class="pg-btn disabled">&#171;</span>)
    prev_link  = if page > 1,          do: ~s(<a href="#{pl.(page-1)}"       class="pg-btn" title="Previous">&#8249;</a>), else: ~s(<span class="pg-btn disabled">&#8249;</span>)
    next_link  = if page < total_pages, do: ~s(<a href="#{pl.(page+1)}"      class="pg-btn" title="Next">&#8250;</a>),    else: ~s(<span class="pg-btn disabled">&#8250;</span>)
    last_link  = if page < total_pages, do: ~s(<a href="#{pl.(total_pages)}" class="pg-btn" title="Last">&#187;</a>),     else: ~s(<span class="pg-btn disabled">&#187;</span>)

    ret = html(URI.encode_query(%{msg: msg, reason: reason, date_fr: eff_date_fr, date_to: eff_date_to, page: page}))

    rows = if items == [] do
      ~s(<tr><td colspan="4" class="ac-empty">No records found.</td></tr>)
    else
      Enum.map_join(items, "", fn r ->
        ts = if r.tgl, do: NaiveDateTime.to_string(r.tgl) |> String.slice(0, 19), else: ""
        """
        <tr>
          <td style="max-width:380px;word-break:break-word;font-size:12px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace">#{html(r.message)}</td>
          <td style="max-width:260px;word-break:break-word;font-size:12px">#{html(r.reason)}</td>
          <td style="white-space:nowrap;font-size:12px;color:#6d7b88">#{html(ts)}</td>
          <td class="ac-act">
            <form class="inline-form" method="post" action="/warning-messages/#{r.idcnt}/delete"
                  onsubmit="return confirm('Delete this warning message?')">
              <input type="hidden" name="return_to" value="/warning-messages?#{ret}">
              <button class="ac-del-row" type="submit" title="Delete">&#10005;</button>
            </form>
          </td>
        </tr>
        """
      end)
    end

    """
    <!doctype html>
    <html lang="en">
    <head>
      <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Incoming Warning Messages</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        #{maint_page_css()}
        .warn-date{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;width:150px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header>
        <a class="back-btn" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a>
        <h1 class="ac-title"><i class="bi bi-exclamation-triangle"></i><span>Incoming Warning Messages</span></h1>
        #{header_time()}
      </header>
      <main>
        #{notice_banner(notice)}
        <section>
          <h2>Search Incoming Warning Messages</h2>
          <form class="ac-search" method="get" action="/warning-messages">
            <div class="ac-field">
              <label class="ac-label">Message</label>
              <input class="ac-input-lg" name="msg" value="#{html(msg)}" placeholder="Search by message..." autocomplete="off">
            </div>
            <div class="ac-field">
              <label class="ac-label">Reason</label>
              <input class="ac-input-lg" name="reason" value="#{html(reason)}" placeholder="Search by reason..." autocomplete="off">
            </div>
            <div class="ac-field">
              <label class="ac-label">From</label>
              <input class="warn-date" type="date" name="date_fr" value="#{html(eff_date_fr)}">
            </div>
            <div class="ac-field">
              <label class="ac-label">To</label>
              <input class="warn-date" type="date" name="date_to" value="#{html(eff_date_to)}">
            </div>
            <button class="btn btn-search" type="submit"><i class="bi bi-search"></i> Search</button>
            <a class="btn btn-clear" href="/warning-messages"><i class="bi bi-eraser"></i><span>Clear</span></a>
          </form>
        </section>
        <section>
          <h2>List of Incoming Warning Messages</h2>
          <table>
            <thead>
              <tr>
                <th>Message</th>
                <th>Reason</th>
                <th style="white-space:nowrap">Date/Time</th>
                <th style="width:36px"></th>
              </tr>
            </thead>
            <tbody>#{rows}</tbody>
          </table>
          <div class="ac-footer">
            <div style="display:flex;align-items:center;gap:8px;flex-wrap:wrap">
              <span class="ac-count">#{total} Element(s) in this table</span>
              <div class="ac-actions">
                <form class="inline-form" method="post" action="/warning-messages/delete-all"
                      onsubmit="return confirm('Delete ALL #{total} warning message(s) matching current filter?')">
                  <input type="hidden" name="msg"     value="#{html(msg)}">
                  <input type="hidden" name="reason"  value="#{html(reason)}">
                  <input type="hidden" name="date_fr" value="#{html(eff_date_fr)}">
                  <input type="hidden" name="date_to" value="#{html(eff_date_to)}">
                  <button class="btn btn-delall" type="submit">
                    <i class="bi bi-trash"></i> Delete All
                  </button>
                </form>
              </div>
            </div>
            <div class="pg">
              #{first_link}#{prev_link}
              <span class="pg-info">Page #{page} of #{total_pages}</span>
              #{next_link}#{last_link}
              <span class="pg-goto">Go to <input id="warn-goto" type="number" min="1" max="#{total_pages}" value="#{page}"></span>
            </div>
          </div>
        </section>
      </main>
      #{status_panel([], nil)}
      #{header_clock_script()}
      <script>
        ( function(){
          var gi = document.getElementById('warn-goto');
          if ( gi) gi.addEventListener('keydown', function(e) {
            if ( e.key === 'Enter') {
              var p = parseInt(gi.value, 10) || 1;
              var params = new URLSearchParams(window.location.search || '');
              params.set('page', p);
              window.location.href = '/warning-messages?' + params.toString();
            }
          });
        })();
      </script>
    </body>
    </html>
    """
  end

  def aircraft_reg_page(items, pagination, q18, q9b, notice) do
    total       = Map.get(pagination, :total, 0)
    page        = Map.get(pagination, :page, 1)
    total_pages = Map.get(pagination, :total_pages, 1)

    pl = fn p -> "/aircraft-reg?" <> URI.encode_query(%{q18: q18, q9b: q9b, page: p}) end
    first_link = if page > 1,          do: ~s(<a href="#{pl.(1)}"           class="pg-btn">&#171;</a>),    else: ~s(<span class="pg-btn disabled">&#171;</span>)
    prev_link  = if page > 1,          do: ~s(<a href="#{pl.(page-1)}"      class="pg-btn">&#8249;</a>),   else: ~s(<span class="pg-btn disabled">&#8249;</span>)
    next_link  = if page < total_pages, do: ~s(<a href="#{pl.(page+1)}"     class="pg-btn">&#8250;</a>),   else: ~s(<span class="pg-btn disabled">&#8250;</span>)
    last_link  = if page < total_pages, do: ~s(<a href="#{pl.(total_pages)}" class="pg-btn">&#187;</a>),   else: ~s(<span class="pg-btn disabled">&#187;</span>)

    ret = html(URI.encode_query(%{q18: q18, q9b: q9b, page: page}))
    rows = if items == [] do
      ~s(<tr><td colspan="9" class="ac-empty">No records found.</td></tr>)
    else
      Enum.map_join(items, "", fn r ->
        """
        <tr class="ac-row" data-id="#{r.id}"
            data-type18="#{html(r.type18)}" data-type9b="#{html(r.type9b)}"
            data-type10a="#{html(r.type10a)}" data-type10b="#{html(r.type10b)}"
            data-opr="#{html(r.type18Opr)}" data-pbn="#{html(r.type18Pbn)}" data-sel="#{html(r.type18Sel)}">
          <td>#{html(r.type18)}</td><td>#{html(r.type9b)}</td>
          <td>#{html(r.type10a)}</td><td>#{html(r.type10b)}</td>
          <td>#{html(r.type18Opr)}</td><td>#{html(r.type18Pbn)}</td><td>#{html(r.type18Sel)}</td>
          <td class="ac-act">
            <form class="inline-form" method="post" action="/aircraft-reg/#{r.id}/delete"
                  onsubmit="return confirm('Delete #{html(r.type18)}?')">
              <input type="hidden" name="return_to" value="/aircraft-reg?#{ret}">
              <button class="ac-del-row" type="submit">&#10005;</button>
            </form>
          </td>
        </tr>
        """
      end)
    end

    """
    <!doctype html>
    <html lang="en">
    <head>
      <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Aircraft Registration</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        #{maint_page_css()}
        .reg-input-sm{width:80px}.reg-input-md{width:120px}.reg-input-lg{width:200px}.reg-input-xl{width:280px}
        .reg-form-grid{display:grid;grid-template-columns:auto 1fr auto 1fr;gap:6px 10px;align-items:center;padding:12px}
        .reg-lbl{font-size:12px;font-weight:600;color:#195b86;white-space:nowrap}
        .reg-equip{display:flex;align-items:center;gap:4px}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header>
        <a class="back-btn" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a>
        <h1 class="ac-title"><i class="bi bi-card-list"></i><span>Aircraft Registration ( REG/)</span></h1>
        #{header_time()}
      </header>
      <main>
        #{notice_banner(notice)}
        <section>
          <h2>Search REG/</h2>
          <form class="ac-search" method="get" action="/aircraft-reg">
            <div class="ac-field"><span class="ac-label">TYPE</span>
              <input class="ac-input-sm" name="q9b" value="#{html(q9b)}" maxlength="4" autocomplete="off" placeholder="B737">
            </div>
            <div class="ac-field"><span class="ac-label">REG/</span>
              <input class="ac-input-sm" name="q18" value="#{html(q18)}" maxlength="7" autocomplete="off" placeholder="PKBDO">
            </div>
            <button class="btn btn-search" type="submit"><i class="bi bi-search" style="margin-right:4px"></i>Search</button>
            <a class="btn btn-clear" href="/aircraft-reg"><i class="bi bi-eraser"></i><span>Clear</span></a>
          </form>
        </section>
        <section>
          <h2>List of REG/(s)</h2>
          <div style="overflow-x:auto">
            <table>
              <thead><tr>
                <th>REG/</th><th>TYPE</th><th>EQUIP 10a</th><th>EQUIP 10b</th>
                <th>OPR/</th><th>PBN/</th><th>SEL/</th><th style="width:36px"></th>
              </tr></thead>
              <tbody id="reg-tbody">#{rows}</tbody>
            </table>
          </div>
          <div class="ac-footer">
            <div style="display:flex;align-items:center;gap:10px">
              <span class="ac-count">#{total} Element(s) in this table</span>
              <div class="ac-actions">
                <button class="btn btn-edit" onclick="regEdit()"><i class="bi bi-pencil-square"></i> Edit</button>
                <button class="btn btn-del"  onclick="regDelete()"><i class="bi bi-trash"></i> Delete</button>
                <form class="inline-form" method="post" action="/aircraft-reg/delete-all"
                      onsubmit="return confirm('Delete all #{total} record(s)?')">
                  <input type="hidden" name="q18" value="#{html(q18)}">
                  <input type="hidden" name="q9b" value="#{html(q9b)}">
                  <button class="btn btn-delall"><i class="bi bi-trash3"></i> Delete All</button>
                </form>
              </div>
            </div>
            <div class="pg">#{first_link}#{prev_link}
              <span class="pg-info">Page #{page} of #{total_pages}</span>
              #{next_link}#{last_link}
              <span class="pg-goto">Go to <input type="number" id="reg-goto" value="#{page}" min="1" max="#{total_pages}">
                <button class="btn" type="button" onclick="regGoto()">Go</button>
              </span>
            </div>
          </div>
        </section>
        <section>
          <h2>Add / Edit REG/</h2>
          <div class="reg-form-grid">
            <span class="reg-lbl">9. TYPE</span>
            <input class="ac-input reg-input-sm" id="reg-type9b" maxlength="4" placeholder="B737">
            <span class="reg-lbl">10. EQUIP</span>
            <div class="reg-equip">
              <input class="ac-input reg-input-md" id="reg-type10a" maxlength="20" placeholder="SACDR">
              <span style="color:#6d7b88;padding:0 4px">/</span>
              <input class="ac-input reg-input-md" id="reg-type10b" maxlength="20" placeholder="AB1">
            </div>
            <span class="reg-lbl">18. REG/</span>
            <input class="ac-input reg-input-md" id="reg-type18" maxlength="7" placeholder="PKBDO">
            <span class="reg-lbl">18. SEL/</span>
            <input class="ac-input reg-input-md" id="reg-type18Sel" maxlength="10">
            <span class="reg-lbl">18. PBN/</span>
            <input class="ac-input reg-input-md" id="reg-type18Pbn" maxlength="20">
            <span class="reg-lbl">18. OPR/</span>
            <input class="ac-input reg-input-xl" id="reg-type18Opr" maxlength="100" placeholder="GARUDA INDONESIA">
          </div>
          <div style="padding:0 12px 12px;display:flex;gap:6px">
            <button class="btn btn-save"   onclick="regSave()"><i class="bi bi-save"></i> Save</button>
            <button class="btn btn-update" onclick="regUpdate()"><i class="bi bi-arrow-repeat"></i> Update</button>
            <button class="btn btn-clr"    onclick="regClear()"><i class="bi bi-eraser"></i> Clear</button>
          </div>
          <form id="reg-save-form" method="post" action="/aircraft-reg/save" style="display:none">
            <input type="hidden" id="rs-type18" name="type18"><input type="hidden" id="rs-type9b" name="type9b">
            <input type="hidden" id="rs-type10a" name="type10a"><input type="hidden" id="rs-type10b" name="type10b">
            <input type="hidden" id="rs-opr" name="type18Opr"><input type="hidden" id="rs-pbn" name="type18Pbn">
            <input type="hidden" id="rs-sel" name="type18Sel">
          </form>
          <form id="reg-update-form" method="post" action="" style="display:none">
            <input type="hidden" id="ru-type18" name="type18"><input type="hidden" id="ru-type9b" name="type9b">
            <input type="hidden" id="ru-type10a" name="type10a"><input type="hidden" id="ru-type10b" name="type10b">
            <input type="hidden" id="ru-opr" name="type18Opr"><input type="hidden" id="ru-pbn" name="type18Pbn">
            <input type="hidden" id="ru-sel" name="type18Sel">
          </form>
          <form id="reg-del-form" method="post" action="" style="display:none"></form>
        </section>
      </main>
      #{status_panel([], nil)}#{header_clock_script()}#{status_footer_script()}#{auto_uppercase_script()}
      <script>
        ( function(){
          var sel=null,selId=null;
          var flds={type18:'reg-type18',type9b:'reg-type9b',type10a:'reg-type10a',type10b:'reg-type10b',
                    opr:'reg-type18Opr',pbn:'reg-type18Pbn',sel:'reg-type18Sel'};
          function g(id){return document.getElementById(id);}
          function v(id){return ( g(id)||{value:''}).value.trim().toUpperCase();}
          function setRow(row){
            if(sel) sel.classList.remove('selected');
            sel=row; selId=row?row.getAttribute('data-id'):null;
            if(!row) return;
            row.classList.add('selected');
            var d=row.dataset;
            var m={type18:d.type18,type9b:d.type9b,type10a:d.type10a,type10b:d.type10b,opr:d.opr,pbn:d.pbn,sel:d.sel};
            Object.keys(m).forEach(function(k){var el=g(flds[k]);if(el) el.value=m[k]||'';});
          }
          var tb=g('reg-tbody');
          if(tb) tb.addEventListener('click',function(e){
            var row=e.target&&e.target.closest?e.target.closest('tr.ac-row'):null;
            if(row&&!e.target.closest('form')) setRow(row);
          });
          function fillForm(prefix){
            g(prefix+'-type18').value=v('reg-type18'); g(prefix+'-type9b').value=v('reg-type9b');
            g(prefix+'-type10a').value=v('reg-type10a'); g(prefix+'-type10b').value=v('reg-type10b');
            var opr=g('reg-type18Opr');
            g(prefix+'-opr').value=opr?opr.value.trim():'';
            g(prefix+'-pbn').value=v('reg-type18Pbn'); g(prefix+'-sel').value=v('reg-type18Sel');
          }
          window.regEdit=function(){if(!sel){alert('Select a row.');return;}g('reg-type18').focus();};
          window.regDelete=function(){
            if(!selId){alert('Select a row.');return;}
            var t=sel?sel.getAttribute('data-type18'):selId;
            if(!confirm('Delete "'+t+'"?')) return;
            var f=g('reg-del-form'); f.action='/aircraft-reg/'+selId+'/delete'; f.submit();
          };
          window.regSave=function(){
            if(!v('reg-type18')){alert('REG/ is required.');g('reg-type18').focus();return;}
            if(!v('reg-type9b')){alert('TYPE is required.');g('reg-type9b').focus();return;}
            fillForm('rs'); g('reg-save-form').submit();
          };
          window.regUpdate=function(){
            if(!selId){alert('Select a row to update.');return;}
            if(!v('reg-type18')){alert('REG/ is required.');g('reg-type18').focus();return;}
            var f=g('reg-update-form'); f.action='/aircraft-reg/'+selId+'/update';
            fillForm('ru'); f.submit();
          };
          window.regClear=function(){
            if(sel) sel.classList.remove('selected'); sel=null; selId=null;
            Object.keys(flds).forEach(function(k){var el=g(flds[k]);if(el) el.value='';});
          };
          window.regGoto=function(){
            var p=parseInt((g('reg-goto')||{value:1}).value,10)||1;
            var params=new URLSearchParams(window.location.search||'');
            params.set('page',p); window.location.href='/aircraft-reg?'+params.toString();
          };
          var gi=g('reg-goto');
          if(gi) gi.addEventListener('keydown',function(e){if(e.key==='Enter') regGoto();});
        })();
      </script>
    </body></html>
    """
  end

  def route_page(items, pagination, dep, dest, notice) do
    total       = Map.get(pagination, :total, 0)
    page        = Map.get(pagination, :page, 1)
    total_pages = Map.get(pagination, :total_pages, 1)

    pl = fn p -> "/routes?" <> URI.encode_query(%{dep: dep, dest: dest, page: p}) end
    first_link = if page > 1,          do: ~s(<a href="#{pl.(1)}"           class="pg-btn">&#171;</a>),  else: ~s(<span class="pg-btn disabled">&#171;</span>)
    prev_link  = if page > 1,          do: ~s(<a href="#{pl.(page-1)}"      class="pg-btn">&#8249;</a>), else: ~s(<span class="pg-btn disabled">&#8249;</span>)
    next_link  = if page < total_pages, do: ~s(<a href="#{pl.(page+1)}"     class="pg-btn">&#8250;</a>), else: ~s(<span class="pg-btn disabled">&#8250;</span>)
    last_link  = if page < total_pages, do: ~s(<a href="#{pl.(total_pages)}" class="pg-btn">&#187;</a>), else: ~s(<span class="pg-btn disabled">&#187;</span>)

    dest_list = fn item ->
      1..8
      |> Enum.map(fn i -> Map.get(item, :"destination#{i}") || "" end)
      |> Enum.reject(&(&1 == ""))
      |> Enum.join(", ")
    end

    ret = html(URI.encode_query(%{dep: dep, dest: dest, page: page}))
    rows = if items == [] do
      ~s(<tr><td colspan="5" class="ac-empty">No records found.</td></tr>)
    else
      Enum.map_join(items, "", fn r ->
        dests_json = 1..21 |> Enum.map(fn i -> ~s("#{html(Map.get(r, :"destination#{i}") || "")}") end) |> Enum.join(",")
        """
        <tr class="ac-row" data-id="#{r.id_number}"
            data-dep="#{html(r.type13a)}" data-dest="#{html(r.type16a)}"
            data-route="#{html(r.type15c)}" data-dests="[#{dests_json}]">
          <td>#{html(r.type13a)}</td><td>#{html(r.type16a)}</td>
          <td style="max-width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis">#{html(r.type15c)}</td>
          <td style="max-width:260px;font-size:12px;color:#6d7b88">#{html(dest_list.(r))}</td>
          <td class="ac-act">
            <form class="inline-form" method="post" action="/routes/#{r.id_number}/delete"
                  onsubmit="return confirm('Delete #{html(r.type13a)}-#{html(r.type16a)}?')">
              <input type="hidden" name="return_to" value="/routes?#{ret}">
              <button class="ac-del-row" type="submit">&#10005;</button>
            </form>
          </td>
        </tr>
        """
      end)
    end

    addr_inputs = fn prefix ->
      Enum.map_join(1..21, "", fn i ->
        ~s(<input class="ac-input rte-addr" id="#{prefix}-dest#{i}" name="destination#{i}" maxlength="8" placeholder="ADDR #{i}">)
      end)
    end

    """
    <!doctype html>
    <html lang="en">
    <head>
      <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Route</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        #{maint_page_css()}
        .rte-addr{width:84px;font-size:12px;padding:4px 6px;height:30px}
        .rte-addr-grid{display:grid;grid-template-columns:repeat(7,84px);gap:5px;margin-top:4px}
        .rte-textarea{width:100%;min-height:64px;box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;resize:vertical;text-transform:uppercase}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header>
        <a class="back-btn" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a>
        <h1 class="ac-title"><i class="bi bi-sign-turn-right"></i><span>Route</span></h1>
        #{header_time()}
      </header>
      <main>
        #{notice_banner(notice)}
        <section>
          <h2>Search Route</h2>
          <form class="ac-search" method="get" action="/routes">
            <div class="ac-field"><span class="ac-label">DEP AD</span>
              <input class="ac-input-sm" name="dep" value="#{html(dep)}" maxlength="4" placeholder="WAJJ">
            </div>
            <div class="ac-field"><span class="ac-label">DEST AD</span>
              <input class="ac-input-sm" name="dest" value="#{html(dest)}" maxlength="4" placeholder="WADD">
            </div>
            <button class="btn btn-search" type="submit"><i class="bi bi-search" style="margin-right:4px"></i>Search</button>
            <a class="btn btn-clear" href="/routes"><i class="bi bi-eraser"></i><span>Clear</span></a>
          </form>
        </section>
        <section>
          <h2>List of Route(s)</h2>
          <div style="overflow-x:auto">
            <table>
              <thead><tr>
                <th style="width:80px">DEP AD</th><th style="width:80px">DEST AD</th>
                <th>ROUTE</th><th>Addresses ( 1–8)</th><th style="width:36px"></th>
              </tr></thead>
              <tbody id="rte-tbody">#{rows}</tbody>
            </table>
          </div>
          <div class="ac-footer">
            <div style="display:flex;align-items:center;gap:10px">
              <span class="ac-count">#{total} Element(s) in this table</span>
              <div class="ac-actions">
                <button class="btn btn-edit" onclick="rteEdit()"><i class="bi bi-pencil-square"></i> Edit</button>
                <button class="btn btn-del"  onclick="rteDelete()"><i class="bi bi-trash"></i> Delete</button>
                <form class="inline-form" method="post" action="/routes/delete-all"
                      onsubmit="return confirm('Delete all #{total} record(s)?')">
                  <input type="hidden" name="dep" value="#{html(dep)}">
                  <input type="hidden" name="dest" value="#{html(dest)}">
                  <button class="btn btn-delall"><i class="bi bi-trash3"></i> Delete All</button>
                </form>
              </div>
            </div>
            <div class="pg">#{first_link}#{prev_link}
              <span class="pg-info">Page #{page} of #{total_pages}</span>
              #{next_link}#{last_link}
              <span class="pg-goto">Go to <input type="number" id="rte-goto" value="#{page}" min="1" max="#{total_pages}">
                <button class="btn-sm" type="button" onclick="rteGoto()">Go</button>
              </span>
            </div>
          </div>
        </section>
        <section>
          <h2>Add / Edit Route</h2>
          <div style="padding:12px">
            <div style="display:flex;gap:12px;flex-wrap:wrap;margin-bottom:8px">
              <div class="ac-field"><span class="ac-label">DEP AD</span>
                <input class="ac-input-sm" id="rte-dep" maxlength="4" placeholder="WAJJ">
              </div>
              <div class="ac-field"><span class="ac-label">DEST AD</span>
                <input class="ac-input-sm" id="rte-dest" maxlength="4" placeholder="WADD">
              </div>
            </div>
            <div class="ac-field" style="margin-bottom:8px"><span class="ac-label">ROUTE</span>
              <textarea class="rte-textarea" id="rte-route" rows="2" placeholder="e.g. W1 W2 DCT POINT"></textarea>
            </div>
            <div class="ac-field"><span class="ac-label">ADDRESS ( 1 – 21)</span>
              <div class="rte-addr-grid" id="rte-addr-grid">#{addr_inputs.("af")}</div>
            </div>
            <div style="margin-top:10px;display:flex;gap:6px">
              <button class="btn btn-save"   onclick="rteSave()"><i class="bi bi-save"></i> Save</button>
              <button class="btn btn-update" onclick="rteUpdate()"><i class="bi bi-arrow-repeat"></i> Update</button>
              <button class="btn btn-clr"    onclick="rteClear()"><i class="bi bi-eraser"></i> Clear</button>
            </div>
          </div>
          <form id="rte-save-form"   method="post" action="/routes/save"  style="display:none">
            <input type="hidden" id="rfs-dep"  name="type13a"><input type="hidden" id="rfs-dest" name="type16a">
            <input type="hidden" id="rfs-route" name="type15c">
            #{Enum.map_join(1..21, "", fn i -> ~s(<input type="hidden" id="rfs-d#{i}" name="destination#{i}">) end)}
          </form>
          <form id="rte-update-form" method="post" action="" style="display:none">
            <input type="hidden" id="rfu-dep"  name="type13a"><input type="hidden" id="rfu-dest" name="type16a">
            <input type="hidden" id="rfu-route" name="type15c">
            #{Enum.map_join(1..21, "", fn i -> ~s(<input type="hidden" id="rfu-d#{i}" name="destination#{i}">) end)}
          </form>
          <form id="rte-del-form" method="post" action="" style="display:none"></form>
        </section>
      </main>
      #{status_panel([], nil)}#{header_clock_script()}#{status_footer_script()}#{auto_uppercase_script()}
      <script>
        ( function(){
          var sel=null,selId=null;
          function g(id){return document.getElementById(id);}
          function v(id){var e=g(id);return e?e.value.trim().toUpperCase():'';}
          function setRow(row){
            if(sel) sel.classList.remove('selected');
            sel=row; selId=row?row.getAttribute('data-id'):null;
            if(!row) return;
            row.classList.add('selected');
            var d=row.dataset;
            g('rte-dep').value=d.dep||''; g('rte-dest').value=d.dest||'';
            var rt=g('rte-route'); if(rt) rt.value=d.route||'';
            try{
              var dsts=JSON.parse(d.dests||'[]');
              for(var i=0;i<21;i++){var el=g('af-dest'+(i+1));if(el) el.value=dsts[i]||'';}
            } catch(e){}
          }
          var tb=g('rte-tbody');
          if(tb) tb.addEventListener('click',function(e){
            var row=e.target&&e.target.closest?e.target.closest('tr.ac-row'):null;
            if(row&&!e.target.closest('form')) setRow(row);
          });
          function fillHidden(prefix){
            g(prefix+'-dep').value=v('rte-dep'); g(prefix+'-dest').value=v('rte-dest');
            var rt=g('rte-route'); g(prefix+'-route').value=rt?rt.value.trim():'';
            for(var i=1;i<=21;i++){
              var el=g('af-dest'+i); var h=g(prefix.replace('r','r')+'s-d'+i)||g(prefix+'u-d'+i)||g(prefix+'-d'+i);
              if(h&&el) h.value=el.value.trim().toUpperCase();
            }
          }
          function fillSave(){
            g('rfs-dep').value=v('rte-dep'); g('rfs-dest').value=v('rte-dest');
            var rt=g('rte-route'); g('rfs-route').value=rt?rt.value.trim():'';
            for(var i=1;i<=21;i++){var el=g('af-dest'+i),h=g('rfs-d'+i);if(h&&el) h.value=el.value.trim().toUpperCase();}
          }
          function fillUpdate(){
            g('rfu-dep').value=v('rte-dep'); g('rfu-dest').value=v('rte-dest');
            var rt=g('rte-route'); g('rfu-route').value=rt?rt.value.trim():'';
            for(var i=1;i<=21;i++){var el=g('af-dest'+i),h=g('rfu-d'+i);if(h&&el) h.value=el.value.trim().toUpperCase();}
          }
          window.rteEdit=function(){if(!sel){alert('Select a row.');return;}g('rte-dep').focus();};
          window.rteDelete=function(){
            if(!selId){alert('Select a row.');return;}
            if(!confirm('Delete "'+v('rte-dep')+'-'+v('rte-dest')+'"?')) return;
            var f=g('rte-del-form'); f.action='/routes/'+selId+'/delete'; f.submit();
          };
          window.rteSave=function(){
            if(!v('rte-dep')){alert('DEP AD is required.');g('rte-dep').focus();return;}
            if(!v('rte-dest')){alert('DEST AD is required.');g('rte-dest').focus();return;}
            fillSave(); g('rte-save-form').submit();
          };
          window.rteUpdate=function(){
            if(!selId){alert('Select a row to update.');return;}
            if(!v('rte-dep')){alert('DEP AD is required.');g('rte-dep').focus();return;}
            var f=g('rte-update-form'); f.action='/routes/'+selId+'/update';
            fillUpdate(); f.submit();
          };
          window.rteClear=function(){
            if(sel) sel.classList.remove('selected'); sel=null; selId=null;
            g('rte-dep').value=''; g('rte-dest').value='';
            var rt=g('rte-route'); if(rt) rt.value='';
            for(var i=1;i<=21;i++){var el=g('af-dest'+i);if(el) el.value='';}
          };
          window.rteGoto=function(){
            var p=parseInt((g('rte-goto')||{value:1}).value,10)||1;
            var params=new URLSearchParams(window.location.search||'');
            params.set('page',p); window.location.href='/routes?'+params.toString();
          };
          var gi=g('rte-goto');
          if(gi) gi.addEventListener('keydown',function(e){if(e.key==='Enter') rteGoto();});
        })();
      </script>
    </body></html>
    """
  end

  def location_indicator_page(items, pagination, q, loc, notice) do
    total       = Map.get(pagination, :total, 0)
    page        = Map.get(pagination, :page, 1)
    total_pages = Map.get(pagination, :total_pages, 1)

    page_link = fn p ->
      "/location-indicators?" <> URI.encode_query(%{q: q, loc: loc, page: p})
    end

    first_link = if page > 1,         do: ~s(<a href="#{page_link.(1)}"           class="pg-btn" title="First">&#171;</a>),    else: ~s(<span class="pg-btn disabled">&#171;</span>)
    prev_link  = if page > 1,         do: ~s(<a href="#{page_link.(page - 1)}"     class="pg-btn" title="Previous">&#8249;</a>), else: ~s(<span class="pg-btn disabled">&#8249;</span>)
    next_link  = if page < total_pages, do: ~s(<a href="#{page_link.(page + 1)}"   class="pg-btn" title="Next">&#8250;</a>),    else: ~s(<span class="pg-btn disabled">&#8250;</span>)
    last_link  = if page < total_pages, do: ~s(<a href="#{page_link.(total_pages)}" class="pg-btn" title="Last">&#187;</a>),    else: ~s(<span class="pg-btn disabled">&#187;</span>)

    rows = if items == [] do
      ~s(<tr><td colspan="3" class="ac-empty">No records found.</td></tr>)
    else
      return_param = html(URI.encode_query(%{q: q, loc: loc, page: page}))
      Enum.map_join(items, "", fn item ->
        """
        <tr class="ac-row" data-id="#{item.id}" data-indicator="#{html(item.indicator)}" data-location="#{html(item.location)}">
          <td>#{html(item.indicator)}</td>
          <td>#{html(item.location)}</td>
          <td class="ac-act">
            <form class="inline-form" method="post" action="/location-indicators/#{item.id}/delete"
                  onsubmit="return confirm('Delete #{html(item.indicator)}?')">
              <input type="hidden" name="return_to" value="/location-indicators?#{return_param}">
              <button class="ac-del-row" type="submit" title="Delete">&#10005;</button>
            </form>
          </td>
        </tr>
        """
      end)
    end

    """
    <!doctype html>
    <html lang="en">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>ICAO Location Indicator</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        body{margin:0;padding-bottom:62px;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}.back-btn{display:inline-flex;align-items:center;gap:6px;font-weight:700;text-decoration:none}.back-btn:hover{color:#d8e8f3;text-decoration:none}.back-btn i{font-size:18px;line-height:1}
        .ac-title{display:inline-flex;align-items:center;gap:8px}.ac-title i{color:#9bd0ff;font-size:18px;line-height:1}
        #{header_time_css()} main{max-width:960px;margin:0 auto;padding:16px}
        h1{font-size:18px;margin:0}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:14px;overflow:visible}
        h2{font-size:15px;font-weight:600;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee;background:#f7f9fb;border-radius:6px 6px 0 0}
        .notice{margin:0 0 12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
        .ac-search{display:flex;align-items:flex-end;gap:8px;flex-wrap:wrap;padding:12px}
        .ac-field{display:flex;flex-direction:column;gap:4px}
        .ac-label{display:block;font-size:12px;color:#435466;font-weight:600;margin-bottom:2px}
        .ac-input-sm{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;text-transform:uppercase;letter-spacing:.06em;width:80px}
        .ac-input-lg{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;width:300px}
        .btn{display:inline-flex;align-items:center;justify-content:center;gap:7px;border:0;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer;height:36px;box-sizing:border-box}.btn i{font-size:15px;line-height:1}
        .btn-search{background:#14324a;color:white}.btn-search:hover{background:#195b86}
        .btn-clear{background:white;color:#435466;border:1px solid #cbd3dc}.btn-clear:hover{background:#edf4fa;text-decoration:none}
        table{width:100%;border-collapse:collapse;font-size:13px}
        th,td{border-bottom:1px solid #edf0f3;padding:8px 12px;text-align:left;vertical-align:middle}
        th{background:#f0f3f6;color:#435466;font-weight:600;font-size:13px;white-space:nowrap}
        .ac-row{cursor:pointer}.ac-row:hover td{background:#edf4fa}.ac-row.selected td{background:#dbeeff}
        .ac-act{width:36px;text-align:center;padding:4px}
        .ac-del-row{background:transparent;border:0;color:#b42318;cursor:pointer;font-size:14px;padding:2px 6px;border-radius:3px}.ac-del-row:hover{background:#fff1f0}
        .ac-empty{text-align:center;color:#6d7b88;padding:20px}
        .ac-footer{display:flex;align-items:center;justify-content:space-between;gap:12px;flex-wrap:wrap;padding:10px 12px;border-top:1px solid #e4e8ee;background:#fbfcfd;font-size:13px}
        .ac-count{color:#195b86;font-weight:700}.ac-actions{display:flex;align-items:center;gap:6px}
        .btn-sm{display:inline-flex;align-items:center;justify-content:center;gap:7px;border:0;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer;height:36px;box-sizing:border-box}.btn-sm i{font-size:15px;line-height:1}
        .btn-edit{background:#14324a;color:white}.btn-edit:hover{background:#195b86}
        .btn-del{background:#b42318;color:white}.btn-del:hover{background:#8a1f11}
        .btn-delall{background:white;color:#b42318;border:1px solid #b42318}.btn-delall:hover{background:#fff1f0}
        .pg{display:flex;align-items:center;gap:4px;flex-wrap:wrap}
        .pg-btn{display:inline-flex;align-items:center;justify-content:center;width:30px;height:30px;border:1px solid #cbd3dc;border-radius:4px;background:white;color:#17202a;font-size:14px;text-decoration:none}
        .pg-btn:hover{background:#edf4fa;text-decoration:none}.pg-btn.disabled{color:#9aa6b2;background:#f0f3f6;pointer-events:none}
        .pg-info{font-size:13px;color:#435466;white-space:nowrap;padding:0 4px}
        .pg-goto{display:inline-flex;align-items:center;gap:4px;font-size:13px}.pg-goto input{width:44px;text-align:center;border:1px solid #cbd3dc;border-radius:4px;padding:4px 5px;font-size:13px}
        .ac-form-body{padding:12px}
        .ac-form-row{display:flex;align-items:flex-end;gap:10px;flex-wrap:wrap}
        .ac-input-loc{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;width:360px;text-transform:uppercase}
        .btn-save{background:#1c6b4f;color:white}.btn-save:hover{background:#17583f}
        .btn-update{background:#195b86;color:white}.btn-update:hover{background:#1a4f72}
        .btn-clr{background:white;color:#435466;border:1px solid #cbd3dc}.btn-clr:hover{background:#edf4fa}
        .inline-form{display:inline;margin:0;padding:0}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header>
        <a class="back-btn" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a>
        <h1 class="ac-title"><i class="bi bi-geo-alt-fill"></i><span>ICAO Location Indicator</span></h1>
        #{header_time()}
      </header>
      <main>
        #{notice_banner(notice)}

        <section>
          <h2>Search ICAO Location Indicator</h2>
          <form class="ac-search" method="get" action="/location-indicators">
            <div class="ac-field">
              <span class="ac-label">Indicator</span>
              <input class="ac-input-sm" name="q" value="#{html(q)}" maxlength="4" autocomplete="off" placeholder="e.g. WAJJ">
            </div>
            <div class="ac-field">
              <span class="ac-label">Location</span>
              <input class="ac-input-lg" name="loc" value="#{html(loc)}" autocomplete="off" placeholder="e.g. JAKARTA">
            </div>
            <button class="btn btn-search" type="submit"><i class="bi bi-search" style="margin-right:4px"></i>Search</button>
            <a class="btn btn-clear" href="/location-indicators"><i class="bi bi-eraser"></i><span>Clear</span></a>
          </form>
        </section>

        <section>
          <h2>List of ICAO Location Indicator(s)</h2>
          <div style="overflow:auto">
            <table>
              <thead>
                <tr>
                  <th style="width:140px">Indicator</th>
                  <th>Location</th>
                  <th style="width:36px"></th>
                </tr>
              </thead>
              <tbody id="loc-tbody">#{rows}</tbody>
            </table>
          </div>
          <div class="ac-footer">
            <div style="display:flex;align-items:center;gap:12px">
              <span class="ac-count">#{total} Element(s) in this table</span>
              <div class="ac-actions">
                <button class="btn btn-edit" type="button" onclick="locEdit()"><i class="bi bi-pencil-square"></i> Edit</button>
                <button class="btn btn-del"  type="button" onclick="locDelete()"><i class="bi bi-trash"></i> Delete</button>
                <form class="inline-form" method="post" action="/location-indicators/delete-all"
                      onsubmit="return confirm('Delete all #{total} record(s) matching current filter?')">
                  <input type="hidden" name="q"   value="#{html(q)}">
                  <input type="hidden" name="loc" value="#{html(loc)}">
                  <button class="btn btn-delall" type="submit"><i class="bi bi-trash3"></i> Delete All</button>
                </form>
              </div>
            </div>
            <div class="pg">
              #{first_link}#{prev_link}
              <span class="pg-info">Page #{page} of #{total_pages}</span>
              #{next_link}#{last_link}
              <span class="pg-goto">Go to
                <input type="number" id="loc-goto" value="#{page}" min="1" max="#{total_pages}">
                <button class="btn-sm" type="button" onclick="locGoto()">Go</button>
              </span>
            </div>
          </div>
        </section>

        <section>
          <h2>Add / Edit ICAO Location Indicator</h2>
          <div class="ac-form-body">
            <div class="ac-form-row">
              <div class="ac-field">
                <span class="ac-label">Indicator</span>
                <input class="ac-input-sm" id="loc-indicator" maxlength="4" autocomplete="off" placeholder="e.g. WAJJ">
              </div>
              <div class="ac-field">
                <span class="ac-label">Location</span>
                <input class="ac-input-loc" id="loc-location" autocomplete="off" placeholder="Location name">
              </div>
              <div style="display:flex;gap:6px;align-self:flex-end">
                <button class="btn btn-save"   type="button" onclick="locSave()"><i class="bi bi-save"></i> Save</button>
                <button class="btn btn-update" type="button" onclick="locUpdate()"><i class="bi bi-arrow-repeat"></i> Update</button>
                <button class="btn btn-clr"    type="button" onclick="locClear()"><i class="bi bi-eraser"></i> Clear</button>
              </div>
            </div>
            <form id="loc-save-form"   method="post" action="/location-indicators/save"   style="display:none">
              <input type="hidden" id="loc-save-ind" name="indicator">
              <input type="hidden" id="loc-save-loc" name="location">
            </form>
            <form id="loc-update-form" method="post" action="" style="display:none">
              <input type="hidden" id="loc-upd-ind" name="indicator">
              <input type="hidden" id="loc-upd-loc" name="location">
            </form>
            <form id="loc-del-form" method="post" action="" style="display:none"></form>
          </div>
        </section>
      </main>
      #{status_panel([], nil)}
      #{header_clock_script()}
      #{status_footer_script()}
      #{auto_uppercase_script()}
      <script>
        ( function ( ) {
          var selRow = null;
          var selId  = null;

          var tbody   = document.getElementById('loc-tbody');
          var indEl   = document.getElementById('loc-indicator');
          var locEl   = document.getElementById('loc-location');

          function selectRow(row) {
            if ( selRow) selRow.classList.remove('selected');
            selRow = row;
            selId  = row ? row.getAttribute('data-id') : null;
            if ( row) {
              row.classList.add('selected');
              if ( indEl) indEl.value = row.getAttribute('data-indicator') || '';
              if ( locEl) locEl.value = row.getAttribute('data-location')  || '';
            }
          }

          if ( tbody) {
            tbody.addEventListener('click', function ( e) {
              var row = e.target && e.target.closest ? e.target.closest('tr.ac-row') : null;
              if ( row && !e.target.closest('form')) selectRow(row);
            });
          }

          window.locEdit   = function ( ) { if ( !selRow) { alert('Select a row first.'); return; } if ( indEl) indEl.focus(); };
          window.locDelete = function ( ) {
            if ( !selId) { alert('Select a row first.'); return; }
            var ind = selRow ? selRow.getAttribute('data-indicator') : selId;
            if ( !confirm('Delete "' + ind + '"?')) return;
            var f = document.getElementById('loc-del-form');
            f.action = '/location-indicators/' + selId + '/delete';
            f.submit();
          };
          window.locSave   = function ( ) {
            var ind = indEl ? indEl.value.trim().toUpperCase() : '';
            var loc = locEl ? locEl.value.trim().toUpperCase() : '';
            if ( !ind) { alert('Indicator is required.'); indEl && indEl.focus(); return; }
            if ( ind.length !== 4) { alert('Indicator must be exactly 4 letters.'); indEl && indEl.focus(); return; }
            if ( !loc) { alert('Location is required.'); locEl && locEl.focus(); return; }
            document.getElementById('loc-save-ind').value = ind;
            document.getElementById('loc-save-loc').value = loc;
            document.getElementById('loc-save-form').submit();
          };
          window.locUpdate = function ( ) {
            if ( !selId) { alert('Select a row to update first.'); return; }
            var ind = indEl ? indEl.value.trim().toUpperCase() : '';
            var loc = locEl ? locEl.value.trim().toUpperCase() : '';
            if ( !ind) { alert('Indicator is required.'); indEl && indEl.focus(); return; }
            if ( !loc) { alert('Location is required.'); locEl && locEl.focus(); return; }
            var f = document.getElementById('loc-update-form');
            f.action = '/location-indicators/' + selId + '/update';
            document.getElementById('loc-upd-ind').value = ind;
            document.getElementById('loc-upd-loc').value = loc;
            f.submit();
          };
          window.locClear  = function ( ) {
            if ( selRow) selRow.classList.remove('selected');
            selRow = null; selId = null;
            if ( indEl) indEl.value = '';
            if ( locEl) locEl.value = '';
          };
          window.locGoto   = function ( ) {
            var inp = document.getElementById('loc-goto');
            var p   = inp ? parseInt(inp.value, 10) : 1;
            if ( isNaN(p) || p < 1) p = 1;
            var params = new URLSearchParams(window.location.search || '');
            params.set('page', p);
            window.location.href = '/location-indicators?' + params.toString();
          };
          var gi = document.getElementById('loc-goto');
          if ( gi) gi.addEventListener('keydown', function ( e) { if ( e.key === 'Enter' || e.keyCode === 13) locGoto(); });
        })();
      </script>
    </body>
    </html>
    """
  end

  def aircraft_type_page(items, pagination, q, wake, notice) do
    total       = Map.get(pagination, :total, 0)
    page        = Map.get(pagination, :page, 1)
    total_pages = Map.get(pagination, :total_pages, 1)
    wake_opts   = Tp.AircraftWtc.wake_categories()

    wake_option = fn val, label ->
      sel = if wake == val, do: " selected", else: ""
      ~s(<option value="#{val}"#{sel}>#{val} – #{label}</option>)
    end

    wake_select = fn name, current ->
      sel = fn v -> if current == v, do: " selected", else: "" end
      """
      <select name="#{name}" class="ac-wake-sel">
        <option value=""#{sel.("")}></option>
        <option value="L"#{sel.("L")}>L – Light</option>
        <option value="M"#{sel.("M")}>M – Medium</option>
        <option value="H"#{sel.("H")}>H – Heavy</option>
        <option value="J"#{sel.("J")}>J – Super Heavy</option>
      </select>
      """
    end

    page_link = fn p ->
      params = URI.encode_query(%{q: q, wake: wake, page: p})
      ~s(/aircraft-types?#{params})
    end

    prev_link  = if page > 1, do: ~s(<a href="#{page_link.(page - 1)}" class="pg-btn" title="Previous">&#8249;</a>), else: ~s(<span class="pg-btn disabled">&#8249;</span>)
    next_link  = if page < total_pages, do: ~s(<a href="#{page_link.(page + 1)}" class="pg-btn" title="Next">&#8250;</a>), else: ~s(<span class="pg-btn disabled">&#8250;</span>)
    first_link = if page > 1, do: ~s(<a href="#{page_link.(1)}" class="pg-btn" title="First">&#171;</a>), else: ~s(<span class="pg-btn disabled">&#171;</span>)
    last_link  = if page < total_pages, do: ~s(<a href="#{page_link.(total_pages)}" class="pg-btn" title="Last">&#187;</a>), else: ~s(<span class="pg-btn disabled">&#187;</span>)

    rows = if items == [] do
      ~s(<tr><td colspan="3" class="ac-empty">No records found.</td></tr>)
    else
      Enum.map_join(items, "", fn item ->
        """
        <tr class="ac-row" data-id="#{item.id}" data-type9b="#{html(item.type9b)}" data-type9c="#{html(item.type9c)}">
          <td>#{html(item.type9b)}</td>
          <td>#{html(item.type9c)}</td>
          <td class="ac-act">
            <form class="inline-form" method="post" action="/aircraft-types/#{item.id}/delete"
                  onsubmit="return confirm('Delete #{html(item.type9b)}?')">
              <input type="hidden" name="return_to" value="/aircraft-types?#{html(URI.encode_query(%{q: q, wake: wake, page: page}))}">
              <button class="ac-del-row" type="submit" title="Delete">&#10005;</button>
            </form>
          </td>
        </tr>
        """
      end)
    end

    """
    <!doctype html>
    <html lang="en">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Type of Aircraft</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        body{margin:0;padding-bottom:62px;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}.back-btn{display:inline-flex;align-items:center;gap:6px;font-weight:700;text-decoration:none}.back-btn:hover{color:#d8e8f3;text-decoration:none}.back-btn i{font-size:18px;line-height:1}
        .ac-title{display:inline-flex;align-items:center;gap:8px}.ac-title i{color:#9bd0ff;font-size:18px;line-height:1}
        #{header_time_css()} main{max-width:900px;margin:0 auto;padding:16px}
        h1{font-size:18px;margin:0}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:14px;overflow:visible}
        h2{font-size:15px;font-weight:600;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee;background:#f7f9fb;border-radius:6px 6px 0 0}
        .notice{margin:0 0 12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
        .ac-search{display:flex;align-items:flex-end;gap:8px;flex-wrap:wrap;padding:12px}
        .ac-field{display:flex;flex-direction:column;gap:4px}
        .ac-label{display:block;font-size:12px;color:#435466;font-weight:600;margin-bottom:2px}
        .ac-input{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;text-transform:uppercase;letter-spacing:.06em}
        .ac-wake-sel{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:5px 7px;font-size:13px;height:34px;min-width:160px}
        .btn{display:inline-flex;align-items:center;justify-content:center;gap:7px;border:0;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer;height:36px;box-sizing:border-box}.btn i{font-size:15px;line-height:1}.btn-search{background:#14324a;color:white}.btn-search:hover{background:#195b86}.btn-clear{background:white;color:#435466;border:1px solid #cbd3dc}.btn-clear:hover{background:#edf4fa;text-decoration:none}
        table{width:100%;border-collapse:collapse;font-size:13px}
        th,td{border-bottom:1px solid #edf0f3;padding:8px 12px;text-align:left;vertical-align:middle}
        th{background:#f0f3f6;color:#435466;font-weight:600;font-size:13px;white-space:nowrap}
        .ac-row{cursor:pointer}.ac-row:hover td{background:#edf4fa}.ac-row.selected td{background:#dbeeff}
        .ac-act{width:36px;text-align:center;padding:4px}
        .ac-del-row{background:transparent;border:0;color:#b42318;cursor:pointer;font-size:14px;padding:2px 6px;border-radius:3px}.ac-del-row:hover{background:#fff1f0}
        .ac-empty{text-align:center;color:#6d7b88;padding:20px}
        .ac-footer{display:flex;align-items:center;justify-content:space-between;gap:12px;flex-wrap:wrap;padding:10px 12px;border-top:1px solid #e4e8ee;background:#fbfcfd;font-size:13px}
        .ac-count{color:#195b86;font-weight:700}.ac-actions{display:flex;align-items:center;gap:6px}
        .btn-sm{display:inline-flex;align-items:center;justify-content:center;gap:7px;border:0;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer;height:36px;box-sizing:border-box}.btn-sm i{font-size:15px;line-height:1}.btn-edit{background:#14324a;color:white}.btn-edit:hover{background:#195b86}.btn-del{background:#b42318;color:white}.btn-del:hover{background:#8a1f11}.btn-delall{background:white;color:#b42318;border:1px solid #b42318}.btn-delall:hover{background:#fff1f0}
        .pg{display:flex;align-items:center;gap:4px;flex-wrap:wrap}.pg-btn{display:inline-flex;align-items:center;justify-content:center;width:30px;height:30px;border:1px solid #cbd3dc;border-radius:4px;background:white;color:#17202a;font-size:14px;text-decoration:none}.pg-btn:hover{background:#edf4fa;text-decoration:none}.pg-btn.disabled{color:#9aa6b2;background:#f0f3f6;pointer-events:none}.pg-info{font-size:13px;color:#435466;white-space:nowrap;padding:0 4px}.pg-goto{display:inline-flex;align-items:center;gap:4px;font-size:13px}.pg-goto input{width:44px;text-align:center;border:1px solid #cbd3dc;border-radius:4px;padding:4px 5px;font-size:13px}
        .ac-form-body{padding:12px}
        .ac-form-row{display:flex;align-items:flex-end;gap:10px;flex-wrap:wrap;margin-bottom:4px}
        .btn-save{background:#1c6b4f;color:white}.btn-save:hover{background:#17583f}.btn-update{background:#195b86;color:white}.btn-update:hover{background:#1a4f72}.btn-clr{background:white;color:#435466;border:1px solid #cbd3dc}.btn-clr:hover{background:#edf4fa}
        .inline-form{display:inline;margin:0;padding:0}
        #{status_footer_css()}
      </style>
    </head>
    <body>
      <header>
        <a class="back-btn" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a>
        <h1 class="ac-title"><i class="bi bi-airplane"></i><span>Type of Aircraft</span></h1>
        #{header_time()}
      </header>
      <main>
        #{notice_banner(notice)}

        <section>
          <h2>Search Type of Aircraft</h2>
          <form class="ac-search" method="get" action="/aircraft-types">
            <div class="ac-field">
              <span class="ac-label">Type of Aircraft</span>
              <input class="ac-input" name="q" value="#{html(q)}" maxlength="4" autocomplete="off" placeholder="e.g. B737">
            </div>
            <div class="ac-field">
              <span class="ac-label">Wake Turb. Category</span>
              #{wake_select.("wake", wake)}
            </div>
            <button class="btn btn-search" type="submit"><i class="bi bi-search"></i> Search</button>
            <a class="btn btn-clear" href="/aircraft-types"><i class="bi bi-eraser"></i><span>Clear</span></a>
          </form>
        </section>

        <section>
          <h2>List of Type of Aircraft(s)</h2>
          <div style="overflow:auto">
            <table>
              <thead>
                <tr>
                  <th>Type of Aircraft</th>
                  <th>Wake Turb. Category</th>
                  <th></th>
                </tr>
              </thead>
              <tbody id="ac-tbody">#{rows}</tbody>
            </table>
          </div>
          <div class="ac-footer">
            <div style="display:flex;align-items:center;gap:12px">
              <span class="ac-count">#{total} Element(s) in this table</span>
              <div class="ac-actions">
                <button class="btn btn-edit" type="button" id="btn-edit" onclick="acEdit()"><i class="bi bi-pencil-square"></i> Edit</button>
                <button class="btn btn-del" type="button" id="btn-delete" onclick="acDelete()"><i class="bi bi-trash"></i> Delete</button>
                <form class="inline-form" method="post" action="/aircraft-types/delete-all"
                      onsubmit="return confirm('Delete all #{total} record(s) matching current filter?')">
                  <input type="hidden" name="q" value="#{html(q)}">
                  <input type="hidden" name="wake" value="#{html(wake)}">
                  <button class="btn btn-delall" type="submit"><i class="bi bi-trash3"></i> Delete All</button>
                </form>
              </div>
            </div>
            <div class="pg">
              #{first_link}#{prev_link}
              <span class="pg-info">Page #{page} of #{total_pages}</span>
              #{next_link}#{last_link}
              <span class="pg-goto">Go to
                <input type="number" id="pg-goto-input" value="#{page}" min="1" max="#{total_pages}">
                <button class="btn-sm" type="button" onclick="acGotoPage()">Go</button>
              </span>
            </div>
          </div>
        </section>

        <section>
          <h2>Add / Edit Type of Aircraft</h2>
          <div class="ac-form-body">
            <input type="hidden" id="ac-edit-id" value="">
            <div class="ac-form-row">
              <div class="ac-field">
                <span class="ac-label">Type of Aircraft</span>
                <input class="ac-input" id="ac-type9b" name="type9b" maxlength="4" autocomplete="off" placeholder="e.g. B737" style="width:110px">
              </div>
              <div class="ac-field">
                <span class="ac-label">Wake Turb. Category</span>
                #{wake_select.("ac-type9c-sel", "")}
              </div>
              <div style="display:flex;gap:6px;align-self:flex-end">
                <button class="btn btn-save" type="button" onclick="acSave()"><i class="bi bi-save"></i> Save</button>
                <button class="btn btn-update" type="button" onclick="acUpdate()"><i class="bi bi-arrow-repeat"></i> Update</button>
                <button class="btn btn-clr" type="button" onclick="acClear()"><i class="bi bi-eraser"></i> Clear</button>
              </div>
            </div>
            <form id="ac-save-form" method="post" action="/aircraft-types/save" style="display:none">
              <input type="hidden" name="type9b" id="ac-save-type9b">
              <input type="hidden" name="type9c" id="ac-save-type9c">
            </form>
            <form id="ac-update-form" method="post" action="" style="display:none">
              <input type="hidden" name="type9b" id="ac-upd-type9b">
              <input type="hidden" name="type9c" id="ac-upd-type9c">
            </form>
            <form id="ac-del-form" method="post" action="" style="display:none"></form>
          </div>
        </section>
      </main>
      #{status_panel([], nil)}
      #{header_clock_script()}
      #{status_footer_script()}
      #{auto_uppercase_script()}
      <script>
        ( function ( ) {
          var selectedRow = null;
          var selectedId  = null;

          var tbody       = document.getElementById('ac-tbody');
          var editIdEl    = document.getElementById('ac-edit-id');
          var type9bEl    = document.getElementById('ac-type9b');
          var type9cEl    = document.querySelector('[name="ac-type9c-sel"]');

          function selectRow(row) {
            if ( selectedRow) selectedRow.classList.remove('selected');
            selectedRow = row;
            selectedId  = row ? row.getAttribute('data-id') : null;
            if ( row) {
              row.classList.add('selected');
              type9bEl.value = row.getAttribute('data-type9b') || '';
              if ( type9cEl) type9cEl.value = row.getAttribute('data-type9c') || '';
              if ( editIdEl) editIdEl.value = selectedId;
            }
          }

          if ( tbody) {
            tbody.addEventListener('click', function ( e) {
              var row = e.target && e.target.closest ? e.target.closest('tr.ac-row') : null;
              if ( row && !e.target.closest('form')) selectRow(row);
            });
          }

          window.acEdit = function ( ) {
            if ( !selectedRow) { alert('Please select a row first.'); return; }
            if ( type9bEl) type9bEl.focus();
          };

          window.acDelete = function ( ) {
            if ( !selectedId) { alert('Please select a row first.'); return; }
            var type = selectedRow ? selectedRow.getAttribute('data-type9b') : selectedId;
            if ( !confirm('Delete "' + type + '"?')) return;
            var form = document.getElementById('ac-del-form');
            form.action = '/aircraft-types/' + selectedId + '/delete';
            form.submit();
          };

          window.acSave = function ( ) {
            var t = type9bEl ? type9bEl.value.trim().toUpperCase() : '';
            var w = type9cEl ? type9cEl.value : '';
            if ( !t) { alert('Type of Aircraft is required.'); type9bEl && type9bEl.focus(); return; }
            if ( !w) { alert('Wake Turb. Category is required.'); return; }
            var form = document.getElementById('ac-save-form');
            document.getElementById('ac-save-type9b').value = t;
            document.getElementById('ac-save-type9c').value = w;
            form.submit();
          };

          window.acUpdate = function ( ) {
            if ( !selectedId) { alert('Please select a row to update first.'); return; }
            var t = type9bEl ? type9bEl.value.trim().toUpperCase() : '';
            var w = type9cEl ? type9cEl.value : '';
            if ( !t) { alert('Type of Aircraft is required.'); type9bEl && type9bEl.focus(); return; }
            if ( !w) { alert('Wake Turb. Category is required.'); return; }
            var form = document.getElementById('ac-update-form');
            form.action = '/aircraft-types/' + selectedId + '/update';
            document.getElementById('ac-upd-type9b').value = t;
            document.getElementById('ac-upd-type9c').value = w;
            form.submit();
          };

          window.acClear = function ( ) {
            if ( selectedRow) selectedRow.classList.remove('selected');
            selectedRow = null; selectedId = null;
            if ( type9bEl) type9bEl.value = '';
            if ( type9cEl) type9cEl.value = '';
            if ( editIdEl) editIdEl.value = '';
          };

          window.acGotoPage = function ( ) {
            var inp = document.getElementById('pg-goto-input');
            var p   = inp ? parseInt(inp.value, 10) : 1;
            if ( isNaN(p) || p < 1) p = 1;
            var params = new URLSearchParams(window.location.search || '');
            params.set('page', p);
            window.location.href = '/aircraft-types?' + params.toString();
          };

          var gotoInp = document.getElementById('pg-goto-input');
          if ( gotoInp) {
            gotoInp.addEventListener('keydown', function ( e) {
              if ( e.key === 'Enter' || e.keyCode === 13) acGotoPage();
            });
          }
        })();
      </script>
    </body>
    </html>
    """
  end

  def settings_page(setting_or_changeset, notice \\ nil) do
    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Communication Setting</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <style>
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}.back-btn{display:inline-flex;align-items:center;gap:6px;font-weight:700;color:white;text-decoration:none}.back-btn:hover{color:#d8e8f3;text-decoration:none}.back-btn i{font-size:18px;line-height:1}.settings-title{display:inline-flex;align-items:center;gap:8px}.settings-title i{color:#9bd0ff;font-size:18px;line-height:1}
        #{header_time_css()} main{max-width:920px;margin:0 auto;padding:16px}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:16px;overflow:visible}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        form{padding:14px} label{display:block;font-size:12px;color:#435466;font-weight:700;margin-bottom:4px}
        input{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:8px;background:white}
        input.is-disabled{background:#eef2f6;color:#7f8c99;cursor:not-allowed}
        .grid2{display:grid;grid-template-columns:1fr 1fr;gap:12px}.grid4{display:grid;grid-template-columns:repeat(4,1fr);gap:12px}.field{margin-bottom:10px}.field input[type=text],.field input[type=number]{width:100%}
        .radio-row{display:flex;align-items:center;gap:12px;min-height:36px}.radio-row label{display:flex;align-items:center;gap:5px;margin:0;font-weight:600;color:#17202a}
        button{background:#1c6b4f;color:white;border:0;border-radius:4px;padding:9px 14px;font-weight:700}.actions{display:flex;gap:8px;align-items:center;margin-top:8px}.actions button,.actions a{display:inline-flex;align-items:center;justify-content:center;gap:7px;min-width:96px;height:36px;box-sizing:border-box;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;text-decoration:none}.actions button{background:#1c6b4f;color:white;border:1px solid #1c6b4f}.actions button:hover{background:#17583f;border-color:#17583f}.actions a{background:white;color:#435466;border:1px solid #cbd3dc}.actions a:hover{background:#edf4fa;text-decoration:none}.actions i{font-size:15px;line-height:1}
        .notice{margin:0 0 12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
        .errors{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11;border-radius:4px;padding:10px 12px;margin:0 0 12px}
        @media(max-width:760px){.grid2,.grid4{grid-template-columns:1fr}}
              #{status_footer_css()}
      </style>
    </head>
    <body>
      <header>
        <a class="back-btn" href="/"><i class="bi bi-arrow-left"></i><span>Back</span></a>
        <h1 class="settings-title"><i class="bi bi-gear-fill"></i><span>Communication Setting</span></h1>#{header_time()}
      </header>
      <main>
        #{notice_banner(notice)}
        #{settings_errors(setting_or_changeset)}
        <form method="post" action="/settings">
          <section>
            <h2><i class="bi bi-router" style="color:#195b86;margin-right:6px"></i>UDP Setup</h2>
            <div style="padding:14px">
              <div class="grid2">
                #{setting_input(setting_or_changeset, :local_udp_port, "Local Receive Port", "number")}
                #{setting_input(setting_or_changeset, :destination_ip_address, "Destination IP Address")}
                #{setting_input(setting_or_changeset, :port, "Destination Port", "number")}
              </div>
            </div>
          </section>
          <section>
            <h2><i class="bi bi-gear" style="color:#195b86;margin-right:6px"></i>Communication Setup</h2>
            <div style="padding:14px">
              <div class="grid4">
                <div class="field">
                  <label>Code</label>
                  <div class="radio-row">
                    #{radio(setting_or_changeset, :code, "ITA2", "ITA 2")}
                    #{radio(setting_or_changeset, :code, "IA5", "IA 5")}
                  </div>
                </div>
                <div class="field">
                  <label>Digit seq.</label>
                  <div class="radio-row">
                    #{radio(setting_or_changeset, :digit_seq, 3, "3")}
                    #{radio(setting_or_changeset, :digit_seq, 4, "4")}
                  </div>
                </div>
                #{setting_input(setting_or_changeset, :cid, "CID")}
                #{setting_input(setting_or_changeset, :tseq, "Tseq")}
              </div>
              <div class="grid4">
                <div class="field">
                  <label>Svc msg generation</label>
                  <div class="radio-row">
                    #{radio(setting_or_changeset, :svc_msg_generation, true, "ON")}
                    #{radio(setting_or_changeset, :svc_msg_generation, false, "OFF")}
                  </div>
                </div>
                #{setting_input(setting_or_changeset, :prev_st, "Prev.st", "text", "svc-prev-st")}
                <div class="field">
                  <label>Channel check</label>
                  <div class="radio-row">
                    #{radio(setting_or_changeset, :channel_check, true, "ON")}
                    #{radio(setting_or_changeset, :channel_check, false, "OFF")}
                  </div>
                </div>
                <div class="field">
                  <label>Automatic Repeat</label>
                  <div class="radio-row">
                    #{radio(setting_or_changeset, :automatic_repeat, true, "Enabled")}
                    #{radio(setting_or_changeset, :automatic_repeat, false, "Disabled")}
                  </div>
                </div>
              </div>
              <div class="grid4">
                <div class="field">
                  <label>Sound Alert</label>
                  <div class="radio-row">
                    #{radio(setting_or_changeset, :sound_enabled, true, "ON")}
                    #{radio(setting_or_changeset, :sound_enabled, false, "OFF")}
                  </div>
                </div>
                #{setting_input(setting_or_changeset, :alarm_repeat_count, "Alarm Times (0 = Continuous)", "number")}
              </div>
              <div class="grid2">
                #{setting_input(setting_or_changeset, :originator, "Originator")}
              </div>
            </div>
          </section>
          <div class="actions">
            <button type="submit"><i class="bi bi-save"></i><span>Save</span></button>
            <a href="/"><i class="bi bi-x-circle"></i><span>Cancel</span></a>
          </div>
        </form>
      </main>
      #{status_panel([], nil)}
      #{status_footer_script()}
      #{header_clock_script()}
      #{auto_uppercase_script()}
      <script>
        (function(){
          function syncSvcPrev(){
            var on = document.querySelector('input[name="svc_msg_generation"][value="true"]');
            var prev = document.getElementById('svc-prev-st');
            if (!on || !prev) return;
            var enabled = on.checked;
            prev.readOnly = !enabled;
            prev.required = enabled;
            prev.minLength = enabled ? 8 : 0;
            prev.maxLength = 8;
            prev.classList.toggle('is-disabled', !enabled);
            prev.title = enabled ? 'Prev.st wajib 8 huruf' : 'Prev.st aktif jika Svc msg generation ON';
          }

          document.addEventListener('change', function(event){
            if (event.target && event.target.name === 'svc_msg_generation') syncSvcPrev();
          });
          syncSvcPrev();
        })();
      </script>
    </body>
    </html>
    """
  end

  defp compose_dropdown do
    items_html = compose_menu_groups() |> Enum.map_join("", fn
      :sep          -> ~s(<hr class="compose-sep">)
      {type, label} -> ~s(<a href="/compose?form=#{html(type)}"><svg style="width:13px;height:13px;margin-right:6px;opacity:.7;flex-shrink:0" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M12 20h9"/><path d="M16.5 3.5a2.1 2.1 0 0 1 3 3L7 19l-4 1 1-4Z"/></svg>#{html(label)}</a>)
    end)
    """
    <details class="top-menu">
      <summary>#{write_icon()}<span>Message</span></summary>
      <div class="top-submenu compose-submenu">
        #{items_html}
      </div>
    </details>
    """
  end

  defp maintenance_dropdown do
    """
    <details class="top-menu">
      <summary>#{maintenance_icon()}<span>Maintenance</span></summary>
      <div class="top-submenu">
        <a href="/settings"><i class="bi bi-gear" style="margin-right:5px"></i>  Communication Setup</a>
        <a href="/aircraft-types"><i class="bi bi-airplane" style="margin-right:5px"></i> Type of Aircraft</a>
        <a href="/aircraft-reg"><i class="bi bi-card-list" style="margin-right:5px"></i>Aircraft Registration</a>
        <a href="/location-indicators"><i class="bi bi-geo-alt" style="margin-right:5px"></i>ICAO Location Indicator</a>
        <a href="/routes"><i class="bi bi-sign-turn-right" style="margin-right:5px"></i>Route</a>
      </div>
    </details>
    """
  end

  defp views_dropdown do
    """
    <details class="top-menu">
      <summary>#{views_icon()}<span>Views</span></summary>
      <div class="top-submenu">

        <a href="/icao-abbreviations"><i class="bi bi-book" style="margin-right:5px"></i>ICAO Abbreviations and Codes</a>
        <a href="/warning-messages"><i class="bi bi-exclamation-triangle" style="margin-right:5px"></i>Warning Messages</a>
      </div>
    </details>
    """
  end

  defp status_dropdown do
    """
    <details class="top-menu">
      <summary>#{status_icon()}<span>Status</span></summary>
      <div class="top-submenu">
        <a href="/queue"><i class="bi bi-envelope-exclamation" style="margin-right:5px"></i>Queue / Not Delivered</a>

      </div>
    </details>
    """
  end

  defp test_message_link do
    """
    <a href="/test-message" class="top-direct-link">#{test_icon()}<span>Test &amp; Svc Traf</span></a>
    """
  end

  defp test_icon do
    """
    <svg class="menu-icon" viewBox="0 0 24 24" aria-hidden="true">
      <path d="M10 2v7.53a2 2 0 0 1-.21.9L4.72 20.55A1 1 0 0 0 5.63 22h12.74a1 1 0 0 0 .9-1.45L14.21 10.43A2 2 0 0 1 14 9.53V2"></path>
      <path d="M8.5 2h7"></path>
      <path d="M7 16h10"></path>
    </svg>
    """
  end

  defp write_icon do
    """
    <svg class="menu-icon" viewBox="0 0 24 24" aria-hidden="true">
      <path d="M12 20h9"></path>
      <path d="M16.5 3.5a2.1 2.1 0 0 1 3 3L7 19l-4 1 1-4Z"></path>
    </svg>
    """
  end

  defp maintenance_icon do
    """
    <svg class="menu-icon" viewBox="0 0 24 24" aria-hidden="true">
      <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.1-3.1a6 6 0 0 1-7.6 7.6l-6.4 6.4a2.1 2.1 0 0 1-3-3l6.4-6.4a6 6 0 0 1 7.6-7.6Z"></path>
    </svg>
    """
  end

  defp views_icon do
    """
    <svg class="menu-icon" viewBox="0 0 24 24" aria-hidden="true">
      <path d="M2 12s3.5-7 10-7 10 7 10 7-3.5 7-10 7S2 12 2 12Z"></path>
      <circle cx="12" cy="12" r="3"></circle>
    </svg>
    """
  end

  defp status_icon do
    """
    <svg class="menu-icon" viewBox="0 0 24 24" aria-hidden="true">
      <path d="M3 12h4l3 8 4-16 3 8h4"></path>
    </svg>
    """
  end

  defp refresh_icon do
    """
    <svg class="menu-icon" viewBox="0 0 24 24" aria-hidden="true">
      <path d="M21 12a9 9 0 0 1-9 9 8.6 8.6 0 0 1-6-2.4"></path>
      <path d="M3 12a9 9 0 0 1 9-9 8.6 8.6 0 0 1 6 2.4"></path>
      <path d="M18 2v4h-4"></path>
      <path d="M6 22v-4h4"></path>
    </svg>
    """
  end

  defp kebab_icon do
    """
    <svg viewBox="0 0 24 24" aria-hidden="true">
      <path d="M12 5h.01"></path>
      <path d="M12 12h.01"></path>
      <path d="M12 19h.01"></path>
    </svg>
    """
  end

  defp eye_icon do
    """
    <svg viewBox="0 0 24 24" aria-hidden="true">
      <path d="M2 12s3.5-7 10-7 10 7 10 7-3.5 7-10 7S2 12 2 12Z"></path>
      <circle cx="12" cy="12" r="3"></circle>
    </svg>
    """
  end

  defp pdf_icon do
    """
    <svg viewBox="0 0 24 24" aria-hidden="true">
      <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8Z"></path>
      <path d="M14 2v6h6"></path>
      <text class="pdf-text" x="6" y="17">PDF</text>
    </svg>
    """
  end

  defp trash_icon do
    """
    <svg viewBox="0 0 24 24" aria-hidden="true">
      <path d="M3 6h18"></path>
      <path d="M8 6V4h8v2"></path>
      <path d="M19 6l-1 14H6L5 6"></path>
      <path d="M10 11v6"></path>
      <path d="M14 11v6"></path>
    </svg>
    """
  end

  defp settings_errors(%Ecto.Changeset{} = changeset) do
    errors =
      Ecto.Changeset.traverse_errors(changeset, fn {message, opts} ->
        Enum.reduce(opts, message, fn {key, value}, acc ->
          String.replace(acc, "%{#{key}}", to_string(value))
        end)
      end)

    if map_size(errors) == 0 do
      ""
    else
      items =
        errors
        |> Enum.flat_map(fn {field, messages} -> Enum.map(messages, &"#{field}: #{&1}") end)
        |> Enum.map_join("", fn message -> "<li>#{html(message)}</li>" end)

      "<div class=\"errors\"><ul>#{items}</ul></div>"
    end
  end

  defp settings_errors(_setting), do: ""

  defp setting_input(setting, field, label, type \\ "text", id \\ nil) do
    id_attr = if id, do: ~s( id="#{html(id)}"), else: ""

    """
    <div class="field">
      <label>#{html(label)}</label>
      <input type="#{html(type)}" name="#{html(field)}" value="#{html(setting_value(setting, field))}"#{id_attr}>
    </div>
    """
  end

  defp radio(setting, field, value, label) do
    checked = if setting_value(setting, field) == value, do: " checked", else: ""
    string_value = if is_boolean(value), do: to_string(value), else: value

    """
    <label><input type="radio" name="#{html(field)}" value="#{html(string_value)}"#{checked}> #{html(label)}</label>
    """
  end

  defp setting_value(%Ecto.Changeset{} = changeset, field), do: Ecto.Changeset.get_field(changeset, field)
  defp setting_value(setting, field), do: Map.get(setting, field)

  defp status_footer_css do
    """
    .status-footer{position:fixed;left:12px;right:12px;bottom:10px;z-index:850;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 10px 28px rgba(20,50,74,.18);overflow:hidden}.status-panel{padding:7px 9px}.status-summary{display:flex;align-items:center;gap:7px;min-width:0;white-space:nowrap;overflow:auto}.status-card{display:flex;align-items:center;gap:6px;border:1px solid #e1e6ed;background:#fbfcfd;border-radius:5px;padding:5px 8px;min-width:max-content}.status-label{font-size:12px;color:#6d7b88;font-weight:700;text-transform:uppercase}.status-value{font-size:12px;font-weight:700;color:#17202a}.status-card.ok .status-value{color:#1c6b4f}.status-card.warn .status-value{color:#b42318}.status-card.info .status-value{color:#195b86}.status-pill{display:inline-flex;align-items:center;gap:5px;border-radius:999px;padding:2px 7px;background:#f0f3f6;font-size:11px;font-weight:700}.status-dot{width:7px;height:7px;border-radius:50%;background:#b42318;display:inline-block}.status-pill.up .status-dot{background:#1c6b4f}.status-pill.down .status-dot{background:#b42318}.status-actions{margin-left:auto;display:flex;align-items:center}.status-actions a{height:34px;box-sizing:border-box;display:inline-flex;align-items:center;justify-content:center;background:#14324a;color:white;border:0;border-radius:4px;padding:0 12px;font-size:13px;font-weight:700;line-height:1;white-space:nowrap;min-width:76px}.status-actions a:hover{text-decoration:none;background:#195b86}
    """
  end

  defp header_time do
    """
    <div class="header-spacer"></div>
    <div id="header-time" class="header-time" title="Aviation UTC Time">
      <span class="header-time-label">UTC</span>
      <span id="header-time-value">#{html(format_time(DateTime.utc_now()))}</span>
    </div>
    """
  end

  defp header_time_css do
    ".header-spacer{flex:1}.header-time{display:inline-flex;align-items:center;gap:6px;font-size:14px;font-weight:700;color:#d8e8f3;white-space:nowrap}.header-clock-icon{display:inline-flex;align-items:center;color:#9bd0ff}.header-clock-icon svg{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.header-time-label{color:#9bd0ff;font-weight:900;letter-spacing:.03em}"
  end

  defp maint_page_css do
    """
    body{margin:0;padding-bottom:62px;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
    a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
    header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
    header a{color:white}.back-btn{display:inline-flex;align-items:center;gap:6px;font-weight:700;text-decoration:none}.back-btn:hover{color:#d8e8f3;text-decoration:none}.back-btn i{font-size:18px;line-height:1}
    .ac-title{display:inline-flex;align-items:center;gap:8px}.ac-title i{color:#9bd0ff;font-size:18px;line-height:1}
    #{header_time_css()} main{max-width:960px;margin:0 auto;padding:16px}
    h1{font-size:18px;margin:0}
    section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:14px;overflow:visible}
    h2{font-size:15px;font-weight:600;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee;background:#f7f9fb;border-radius:6px 6px 0 0}
    .notice{margin:0 0 12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
    .ac-search{display:flex;align-items:flex-end;gap:8px;flex-wrap:wrap;padding:12px}
    .ac-field{display:flex;flex-direction:column;gap:4px}
    .ac-label{display:block;font-size:12px;color:#435466;font-weight:600;margin-bottom:2px}
    .ac-input-sm{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;text-transform:uppercase;letter-spacing:.06em;width:80px}
    .ac-input-lg{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;width:300px}
    .ac-input{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:6px 8px;font-size:13px;height:34px;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;text-transform:uppercase}
    .btn{display:inline-flex;align-items:center;justify-content:center;gap:7px;border:0;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer;height:36px;box-sizing:border-box}.btn i{font-size:15px;line-height:1}
    .btn-search{background:#14324a;color:white}.btn-search:hover{background:#195b86}
    .btn-clear{background:white;color:#435466;border:1px solid #cbd3dc}.btn-clear:hover{background:#edf4fa;text-decoration:none}
    table{width:100%;border-collapse:collapse;font-size:13px}
    th,td{border-bottom:1px solid #edf0f3;padding:8px 12px;text-align:left;vertical-align:middle}
    th{background:#f0f3f6;color:#435466;font-weight:600;font-size:13px;white-space:nowrap}
    .ac-row{cursor:pointer}.ac-row:hover td{background:#edf4fa}.ac-row.selected td{background:#dbeeff}
    .ac-act{width:36px;text-align:center;padding:4px}
    .ac-del-row{background:transparent;border:0;color:#b42318;cursor:pointer;font-size:14px;padding:2px 6px;border-radius:3px}.ac-del-row:hover{background:#fff1f0}
    .ac-empty{text-align:center;color:#6d7b88;padding:20px}
    .ac-footer{display:flex;align-items:center;justify-content:space-between;gap:12px;flex-wrap:wrap;padding:10px 12px;border-top:1px solid #e4e8ee;background:#fbfcfd;font-size:13px}
    .ac-count{color:#195b86;font-weight:700}.ac-actions{display:flex;align-items:center;gap:6px}
    .btn-sm{display:inline-flex;align-items:center;justify-content:center;gap:7px;border:0;border-radius:6px;padding:0 14px;font-size:13px;font-weight:700;line-height:1;cursor:pointer;height:36px;box-sizing:border-box}.btn-sm i{font-size:15px;line-height:1}
    .btn-edit{background:#14324a;color:white}.btn-edit:hover{background:#195b86}
    .btn-del{background:#b42318;color:white}.btn-del:hover{background:#8a1f11}
    .btn-delall{background:white;color:#b42318;border:1px solid #b42318}.btn-delall:hover{background:#fff1f0}
    .pg{display:flex;align-items:center;gap:4px;flex-wrap:wrap}
    .pg-btn{display:inline-flex;align-items:center;justify-content:center;width:30px;height:30px;border:1px solid #cbd3dc;border-radius:4px;background:white;color:#17202a;font-size:14px;text-decoration:none}
    .pg-btn:hover{background:#edf4fa;text-decoration:none}.pg-btn.disabled{color:#9aa6b2;background:#f0f3f6;pointer-events:none}
    .pg-info{font-size:13px;color:#435466;white-space:nowrap;padding:0 4px}
    .pg-goto{display:inline-flex;align-items:center;gap:4px;font-size:13px}.pg-goto input{width:44px;text-align:center;border:1px solid #cbd3dc;border-radius:4px;padding:4px 5px;font-size:13px}
    .ac-form-body{padding:12px}
    .ac-form-row{display:flex;align-items:flex-end;gap:10px;flex-wrap:wrap}
    .btn-save{background:#1c6b4f;color:white}.btn-save:hover{background:#17583f}
    .btn-update{background:#195b86;color:white}.btn-update:hover{background:#1a4f72}
    .btn-clr{background:white;color:#435466;border:1px solid #cbd3dc}.btn-clr:hover{background:#edf4fa}
    .inline-form{display:inline;margin:0;padding:0}
    """
  end

  defp header_clock_script do
    """
    <script>
      ( function ( ) {
        function updateHeaderTime() {
          var node = document.getElementById('header-time-value');
          if ( !node) return;
          var now = new Date();
          var pad = function ( v) { v = String(v); return v.length < 2 ? '0' + v : v; };
          node.textContent = now.getUTCFullYear() + '-' + pad(now.getUTCMonth() + 1) + '-' + pad(now.getUTCDate()) + ' ' + pad(now.getUTCHours()) + ':' + pad(now.getUTCMinutes()) + ':' + pad(now.getUTCSeconds());
        }
        updateHeaderTime();
        window.setInterval(updateHeaderTime, 1000);
      })();
    </script>
    """
  end

  defp status_footer_script do
    """
    <script>
      ( function ( ) {
        function byId(id) { return document.getElementById(id); }
        function setText(id, value) {
          var node = byId(id);
          if ( !node || value == null) return;
          node.textContent = value;
        }
        function updateQueueCard(queueCount) {
          var card = byId('status-queue-card');
          if ( !card || !card.classList || queueCount == null) return;
          var count = parseInt(queueCount, 10);
          card.classList.toggle('warn', !isNaN(count) && count > 0);
          card.classList.toggle('ok', isNaN(count) || count <= 0);
        }
        function updateCurrentCidSeq(payload) {
          if ( !payload || payload.cid == null || payload.tseq == null) return;
          var value = String(payload.cid) + String(payload.tseq);
          Array.prototype.forEach.call(document.querySelectorAll('[data-current-cid-seq]'), function ( node) {
            if ( !node) return;
            if ( node.tagName === 'INPUT' || node.tagName === 'TEXTAREA') node.value = value;
            else node.textContent = value;
          });
        }
        function updateLink(payload) {
          if ( !payload) return;
          var up = payload.link_up === true || payload.link_state === 'Established' || payload.link_state === 'established';
          var pill = byId('status-link-pill');
          if ( pill && pill.classList) {
            pill.classList.toggle('up', up);
            pill.classList.toggle('down', !up);
          }
          setText('status-link-value', payload.link_state || ( up ? 'Established' : 'Down'));
        }
        function pollFooterStatus() {
          fetch('/api/status', {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin',
            cache: 'no-store'
          })
            .then(function ( response) {
              if ( !response.ok) throw new Error('status failed');
              return response.json();
            })
            .then(function ( payload) {
              if ( !payload) return;
              setText('status-tseq-value', payload.tseq);
              setText('status-queue-count', payload.queue_count);
              setText('status-cid-value', payload.cid);
              setText('status-digit-seq-value', payload.digit_seq);
              setText('status-local-value', payload.local);
              setText('status-destination-value', payload.destination);
              setText('status-check-value', payload.check || payload.link_reason);
              updateQueueCard(payload.queue_count);
              updateCurrentCidSeq(payload);
              updateLink(payload);
            })
            .catch(function ( ) {});
        }
        pollFooterStatus();
        window.setInterval(pollFooterStatus, 1000);
      })();
    </script>
    """
  end

  defp compose_udp_monitor_script do
    """
    <script>
      ( function ( ) {
        function byId(id) { return document.getElementById(id); }

        function escapeHtml(value) {
          return String(value == null ? '' : value)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
        }

        function visibleMonitorUdp(value) {
          var text = String(value == null ? '' : value);
          var out = '';
          for ( var i = 0; i < text.length; i++) {
            var code = text.charCodeAt(i);
            if ( code === 1 || code === 2 || code === 3 || code === 11) continue;
            if ( code === 13) out += String.fromCharCode(10);
            else if ( code === 10 && i > 0 && text.charCodeAt(i - 1) === 13) continue;
            else out += text.charAt(i);
          }
          return out;
        }

        function formatMonitorTime(value) {
          return String(value || '').replace('T', ' ').replace('Z', '').slice(0, 19);
        }

        function monitorSource(item) {
          var ip = item && item.source_ip ? item.source_ip : '';
          var port = item && item.source_port ? item.source_port : '';
          if ( ip && port) return ip + ':' + port;
          return ip || '';
        }

        function monitorRow(item) {
          var messageIn = '<i class="bi bi-envelope-arrow-down-fill" title="Message in" aria-hidden="true"></i>';
          return '<div class="udp-item">' +
            '<div class="udp-meta">' +
              '<span>' + escapeHtml(formatMonitorTime(item.time)) + '</span>' +
              '<span>' + escapeHtml(item.kind || 'UDP') + '</span>' +
              '<span class="udp-source">' + messageIn + escapeHtml(monitorSource(item)) + '</span>' +
            '</div>' +
            '<pre class="udp-raw">' + escapeHtml(visibleMonitorUdp(item.raw || '')) + '</pre>' +
          '</div>';
        }

        function udpEmptyHtml() {
          return '<div class="udp-empty">No Data.</div>';
        }

        function hasBellSignal(item) {
          var raw = String(item && item.raw != null ? item.raw : '');
          for ( var i = 0; i < raw.length; i++) {
            var code = raw.charCodeAt(i);
            if ( code === 7 || code === 11) return true;
          }
          return raw.indexOf('[BEL]') !== -1 || raw.indexOf('\\u0007') !== -1 || raw.indexOf('\\u000b') !== -1;
        }

        function soundSettings() {
          var node = byId('sound-settings');
          var enabled = !!node && node.getAttribute('data-sound-enabled') !== 'false';
          var repeat = node ? parseInt(node.getAttribute('data-alarm-repeat') || '1', 10) : 1;
          if ( isNaN(repeat) || repeat < 0) repeat = 1;
          return {enabled: enabled, repeat: repeat};
        }

        function ensureAlarmStopButton() {
          var existing = byId('alarm-stop-button');
          if ( existing) return existing;
          var button = document.createElement('button');
          button.id = 'alarm-stop-button';
          button.type = 'button';
          button.textContent = 'Stop Alarm';
          button.style.cssText = 'display:none;position:fixed;right:20px;bottom:82px;z-index:1200;background:#b42318;color:white;border:0;border-radius:6px;padding:9px 14px;font-weight:800;box-shadow:0 10px 26px rgba(0,0,0,.22);cursor:pointer';
          button.onclick = stopBellAlarm;
          document.body.appendChild(button);
          return button;
        }

        function stopBellAlarm() {
          if ( window.__aftnBellAlarmTimer) {
            window.clearInterval(window.__aftnBellAlarmTimer);
            window.__aftnBellAlarmTimer = null;
          }
          var button = byId('alarm-stop-button');
          if ( button) button.style.display = 'none';
        }

        function playBellTone() {
          try {
            var AudioContext = window.AudioContext || window.webkitAudioContext;
            if ( !AudioContext) {
              if ( navigator.vibrate) navigator.vibrate([180, 80, 180]);
              return;
            }
            var context = window.__aftnBellAudioContext || new AudioContext();
            window.__aftnBellAudioContext = context;
            if ( context.state === 'suspended' && context.resume) context.resume();
            var start = context.currentTime;
            [0, 0.28, 0.56].forEach(function ( offset) {
              var osc = context.createOscillator();
              var gain = context.createGain();
              osc.type = 'sine';
              osc.frequency.setValueAtTime(880, start + offset);
              gain.gain.setValueAtTime(0.0001, start + offset);
              gain.gain.exponentialRampToValueAtTime(0.22, start + offset + 0.015);
              gain.gain.exponentialRampToValueAtTime(0.0001, start + offset + 0.18);
              osc.connect(gain);
              gain.connect(context.destination);
              osc.start(start + offset);
              osc.stop(start + offset + 0.2);
            });
            if ( navigator.vibrate) navigator.vibrate([180, 80, 180]);
          } catch ( _error) {}
        }

        function playBellAlarm() {
          var settings = soundSettings();
          if ( !settings.enabled) return;
          stopBellAlarm();
          playBellTone();
          if ( settings.repeat === 1) return;
          var played = 1;
          var stopButton = ensureAlarmStopButton();
          stopButton.style.display = 'inline-flex';
          window.__aftnBellAlarmTimer = window.setInterval(function () {
            if ( settings.repeat > 0 && played >= settings.repeat) {
              stopBellAlarm();
              return;
            }
            played += 1;
            playBellTone();
          }, 1200);
        }

        function itemHasBellSignal(item) {
          return !!(item && item.bell) || hasBellSignal(item) || hasBellSignal({raw: item && item.raw_text});
        }

        function playBellAlarmForRows(rows) {
          for ( var i = 0; i < rows.length; i++) {
            if ( itemHasBellSignal(rows[i])) {
              playBellAlarm();
              return;
            }
          }
        }

        function pollUdpMonitor() {
          var body = byId('udp-monitor-body');
          if ( !body) return;

          fetch('/api/udp-monitor', {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin'
          })
            .then(function ( response) {
              if ( !response.ok) throw new Error('monitor failed');
              return response.json();
            })
            .then(function ( payload) {
              var rows = payload && payload.data ? payload.data : [];
              if ( !rows.length) return;

              playBellAlarmForRows(rows);

              var html = '';
              for ( var i = rows.length - 1; i >= 0; i--) html += monitorRow(rows[i]);

              if ( body.querySelector && body.querySelector('.udp-empty')) body.innerHTML = '';
              body.insertAdjacentHTML('afterbegin', html);

              var items = body.querySelectorAll ? body.querySelectorAll('.udp-item') : [];
              for ( var index = 10; index < items.length; index++) items[index].parentNode.removeChild(items[index]);
            })
            .catch(function ( ) {});
        }

        document.addEventListener('click', function ( event) {
          var target = event.target;
          if ( target && ( target.id === 'clear-udp-monitor' || ( target.closest && target.closest('#clear-udp-monitor')))) {
            event.preventDefault();
            var monitor = byId('udp-monitor-body');
            if ( monitor) monitor.innerHTML = udpEmptyHtml();
            return false;
          }
        });

        window.setInterval(pollUdpMonitor, 2000);
      })();
    </script>
    """
  end

  defp auto_uppercase_script do
    """
    <script>
      ( function ( ) {
        function shouldUppercase(field) {
          if ( !field || !field.name) return false;
          var tag = ( field.tagName || '').toUpperCase();
          if ( tag !== 'INPUT' && tag !== 'TEXTAREA') return false;
          var type = ( field.type || '').toLowerCase();
          return ['hidden','submit','button','reset','checkbox','radio','file','date','time','datetime-local','month','week','number','password'].indexOf(type) === -1;
        }

        function uppercaseField(field) {
          if ( !shouldUppercase(field) || !field.value) return;
          var start = field.selectionStart;
          var end = field.selectionEnd;
          var upper = field.value.toUpperCase();
          if ( field.value === upper) return;
          field.value = upper;
          if ( typeof start === 'number' && typeof end === 'number') field.setSelectionRange(start, end);
        }

        function uppercaseAll(root) {
          Array.prototype.forEach.call((root || document).querySelectorAll('input, textarea'), uppercaseField);
        }

        document.addEventListener('input', function ( event) {
          uppercaseField(event.target);
        }, true);

        document.addEventListener('change', function ( event) {
          uppercaseField(event.target);
        }, true);

        document.addEventListener('submit', function ( event) {
          if ( event.target && event.target.querySelectorAll) uppercaseAll(event.target);
        }, true);

        if ( document.readyState === 'loading') {
          document.addEventListener('DOMContentLoaded', function ( ) { uppercaseAll(document); });
        } else {
          uppercaseAll(document);
        }
      })();
    </script>
    """
  end

  defp compose_page_script do
    """
    <script>
      ( function ( ) {
        function pad(value) {
          value = String(value);
          return value.length < 2 ? '0' + value : value;
        }

        function currentDdHhMm() {
          var now = new Date();
          return pad(now.getDate()) + pad(now.getHours()) + pad(now.getMinutes());
        }

        function formKey(form) {
          var returnForm = form.elements.return_form ? form.elements.return_form.value : '';
          var composeType = form.elements.compose_type ? form.elements.compose_type.value : '';
          return 'aftn_compose_draft_' + ( returnForm || composeType || form.id || 'form');
        }

        function fields(form) {
          return Array.prototype.filter.call(form.elements, function ( field) {
            return field.name && field.type !== 'hidden' && field.type !== 'submit' && field.type !== 'button';
          });
        }

        function saveDraft(form) {
          if ( !window.sessionStorage) return;
          var draft = {};
          fields(form).forEach(function ( field) {
            if ( field.type === 'checkbox' || field.type === 'radio') draft[field.name] = field.checked;
            else draft[field.name] = field.value;
          });
          window.sessionStorage.setItem(formKey(form), JSON.stringify(draft));
        }

        function restoreDraft(form) {
          if ( !window.sessionStorage) return;
          var params = new URLSearchParams(window.location.search || '');
          if ( params.get('clear') === '1') {
            window.sessionStorage.removeItem(formKey(form));
            clearForm(form);
            return;
          }
          var hasPrefill = params.has('priority') || params.has('originator') || params.has('message');
          if ( hasPrefill && form.id === 'aftn-free-form') {
            window.sessionStorage.removeItem(formKey(form));
            return;
          }
          var saved = window.sessionStorage.getItem(formKey(form));
          if ( !saved) return;

          try {
            var draft = JSON.parse(saved);
            fields(form).forEach(function ( field) {
              if ( !Object.prototype.hasOwnProperty.call(draft, field.name)) return;
              if ( field.type === 'checkbox' || field.type === 'radio') field.checked = !!draft[field.name];
              else field.value = draft[field.name];
            });
          } catch ( _error) {
          }
        }

        function clearForm(form) {
          fields(form).forEach(function ( field) {
            if ( field.type === 'hidden') return;
            if ( field.name === 'message_type' && form.elements.compose_type) {
              field.value = form.elements.compose_type.value;
              return;
            }
            if ( field.type === 'checkbox' || field.type === 'radio') field.checked = false;
            else field.value = '';
          });
          updateAlrRequired(form);
          if ( window.sessionStorage) window.sessionStorage.removeItem(formKey(form));
        }

        function uppercaseField(field) {
          if ( !field || !field.value || field.type === 'hidden' || field.type === 'submit' || field.type === 'button') return;
          var start = field.selectionStart;
          var end = field.selectionEnd;
          var upper = field.value.toUpperCase();
          if ( field.value === upper) return;
          field.value = upper;
          if ( typeof start === 'number' && typeof end === 'number') field.setSelectionRange(start, end);
        }

        Array.prototype.forEach.call(document.querySelectorAll('form[action="/messages/compose"]'), function ( form) {
          restoreDraft(form);

          fields(form).forEach(uppercaseField);

          form.addEventListener('submit', function ( event) {
            fields(form).forEach(uppercaseField);
            var action = event.submitter ? event.submitter.value : '';
            if ( action === 'send_clear') {
              if ( window.sessionStorage) window.sessionStorage.removeItem(formKey(form));
            } else {
              saveDraft(form);
            }
          });

          form.addEventListener('input', function ( event) {
            var field = event.target;
            if ( field && ( field.tagName === 'INPUT' || field.tagName === 'TEXTAREA')) uppercaseField(field);
            updateAlrRequired(form);
          });

          form.addEventListener('change', function ( ) {
            updateAlrRequired(form);
          });

          form.addEventListener('click', function ( event) {
            var target = event.target;
            var button = target && target.closest ? target.closest('.js-clear-form, .js-current-time') : null;
            if ( !button) return;

            if ( button.classList.contains('js-clear-form')) {
              event.preventDefault();
              clearForm(form);
              return;
            }

            if ( button.classList.contains('js-current-time')) {
              event.preventDefault();
              var input = document.getElementById(button.getAttribute('data-target'));
              if ( input) input.value = currentDdHhMm();
            }
          });

          updateAlrRequired(form);
        });

        function value(form, name) {
          var field = form.elements[name];
          return field && field.value ? field.value.toUpperCase() : '';
        }

        function setRequired(form, name, required) {
          var field = form.elements[name];
          if ( !field) return;
          field.required = !!required;
          var box = field.closest ? field.closest('.field') : null;
          if ( box) box.classList.toggle('required', !!required);
        }

        function updateAlrRequired(form) {
          if ( !form) return;
          var composeType = form.elements.compose_type ? form.elements.compose_type.value : '';
          if ( composeType === 'RCF') {
            setRequired(form, 'ssr_code', !!form.elements.ssr_mode && form.elements.ssr_mode.checked);
            return;
          }
          if ( form.id !== 'alr-form' && form.id !== 'fpl-form') return;
          setRequired(form, 'ssr_code', !!form.elements.ssr_mode && form.elements.ssr_mode.checked);
          setRequired(form, 'pbn', value(form, 'equipment').indexOf('R') !== -1);
          setRequired(form, 'typ', value(form, 'aircraft_type') === 'ZZZZ');
          setRequired(form, 'dep_info', ['ZZZZ', 'AFIL'].indexOf(value(form, 'departure')) !== -1);
          setRequired(form, 'dest_info', value(form, 'destination') === 'ZZZZ');
          setRequired(form, 'altn_detail', value(form, 'alternate') === 'ZZZZ');
          var dinghyDetail = ['dinghy_number', 'dinghy_capacity', 'dinghy_colour'].some(function ( name) { return value(form, name) !== ''; }) || ( !!form.elements.dinghy_cover && form.elements.dinghy_cover.checked);
          setRequired(form, 'dinghy_number', dinghyDetail);
          setRequired(form, 'dinghy_capacity', dinghyDetail);
        }

        var equipmentOrder = {
          '10a': ['N', 'S', 'A', 'B', 'C', 'D', 'E1', 'E2', 'E3', 'F', 'G', 'H', 'I', 'J1', 'J2', 'J3', 'J4', 'J5', 'J6', 'J7', 'K', 'L', 'M1', 'M2', 'M3', 'O', 'P1', 'P2', 'P3', 'P4', 'P5', 'P6', 'P7', 'P8', 'P9', 'R', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'],
          '10b': ['N', 'A', 'C', 'E', 'H', 'I', 'L', 'P', 'S', 'X', 'B1', 'B2', 'U1', 'U2', 'V1', 'V2', 'D1', 'G1'],
          'pbn': ['A1', 'B1', 'B2', 'B3', 'B4', 'B5', 'B6', 'C1', 'C2', 'C3', 'C4', 'D1', 'D2', 'D3', 'D4', 'L1', 'O1', 'O2', 'O3', 'O4', 'S1', 'S2', 'T1', 'T2'],
          'sts': ['ALTRV', 'ATFMX', 'FFR', 'FLTCK', 'HAZMAT', 'HEAD', 'HOSP', 'HUM', 'MARSA', 'MEDEVAC', 'NONRVSM', 'SAR', 'STATE']
        };

        function valuesFromField(value, order) {
          value = ( value || '').toUpperCase();
          return order.filter(function ( code) { return value.indexOf(code) !== -1; });
        }

        function selectedValues(modal) {
          return Array.prototype.map.call(modal.querySelectorAll('.equipment-choice:checked'), function ( box) { return box.value; });
        }

        function applyEquipmentRules(modal) {
          var type = modal.getAttribute('data-equipment-modal');
          var selected = selectedValues(modal);
          modal.querySelectorAll('.equipment-choice').forEach(function ( box) { box.disabled = false; });

          if ( type === '10a') {
            if ( selected.indexOf('N') !== -1) modal.querySelector('[value="S"]').disabled = true;
            if ( selected.indexOf('S') !== -1) modal.querySelector('[value="N"]').disabled = true;
            return;
          }

          if ( type === '10b') {
            var primary = ['N', 'A', 'C', 'E', 'H', 'I', 'L', 'P', 'S', 'X'];
            var selectedPrimary = primary.filter(function ( code) { return selected.indexOf(code) !== -1; });
            if ( selectedPrimary.length) {
              primary.forEach(function ( code) {
                var box = modal.querySelector('[value="' + code + '"]');
                if ( box && selectedPrimary.indexOf(code) === -1) box.disabled = true;
              });
              if ( selectedPrimary.indexOf('N') !== -1) {
                modal.querySelectorAll('.equipment-choice').forEach(function ( box) {
                  if ( box.value !== 'N') box.disabled = true;
                });
              }
            }

            var outOnly = ['B1', 'U1', 'V1'];
            var inOut = ['B2', 'U2', 'V2'];
            var hasOutOnly = outOnly.some(function ( code) { return selected.indexOf(code) !== -1; });
            var hasInOut = inOut.some(function ( code) { return selected.indexOf(code) !== -1; });
            if ( hasOutOnly) inOut.forEach(function ( code) { modal.querySelector('[value="' + code + '"]').disabled = true; });
            if ( hasInOut) outOnly.forEach(function ( code) { modal.querySelector('[value="' + code + '"]').disabled = true; });
          }
        }

        document.addEventListener('click', function ( event) {
          var openButton = event.target.closest ? event.target.closest('.js-equipment-open') : null;
          if ( openButton) {
            event.preventDefault();
            var target = openButton.getAttribute('data-target');
            var type = openButton.getAttribute('data-equipment');
            var modal = document.querySelector('[data-equipment-modal="' + type + '"]');
            var input = document.querySelector('[name="' + target + '"]');
            if ( !modal || !input) return;
            modal.setAttribute('data-target', target);
            var selected = valuesFromField(input.value, equipmentOrder[type] || []);
            modal.querySelectorAll('.equipment-choice').forEach(function ( box) {
              box.checked = selected.indexOf(box.value) !== -1;
            });
            applyEquipmentRules(modal);
            modal.classList.add('open');
            return;
          }

          var closeButton = event.target.closest ? event.target.closest('.js-equipment-close') : null;
          if ( closeButton) {
            event.preventDefault();
            closeButton.closest('.equipment-modal').classList.remove('open');
            return;
          }

          var clearButton = event.target.closest ? event.target.closest('.js-equipment-clear') : null;
          if ( clearButton) {
            event.preventDefault();
            var clearModal = clearButton.closest('.equipment-modal');
            clearModal.querySelectorAll('.equipment-choice').forEach(function ( box) {
              box.checked = false;
              box.disabled = false;
            });
            var clearInput = document.querySelector('[name="' + clearModal.getAttribute('data-target') + '"]');
            if ( clearInput) {
              clearInput.value = '';
              updateAlrRequired(clearInput.form);
            }
            return;
          }

          var addButton = event.target.closest ? event.target.closest('.js-equipment-add') : null;
          if ( addButton) {
            event.preventDefault();
            var addModal = addButton.closest('.equipment-modal');
            var addType = addModal.getAttribute('data-equipment-modal');
            var inputTarget = document.querySelector('[name="' + addModal.getAttribute('data-target') + '"]');
            var picked = selectedValues(addModal);
            var separator = addType === 'sts' ? ' ' : '';
            var value = ( equipmentOrder[addType] || []).filter(function ( code) { return picked.indexOf(code) !== -1; }).join(separator);
            if ( addType === 'pbn' && value.length > 16) {
              if ( inputTarget) inputTarget.value = value;
              window.alert('Please insert maximum 8 entries, i.e. a total of not more than 16 characters !!');
              return;
            }
            if ( inputTarget) {
              inputTarget.value = value;
              updateAlrRequired(inputTarget.form);
            }
            addModal.classList.remove('open');
            return;
          }

          var row = event.target.closest ? event.target.closest('.equipment-row') : null;
          if ( row && !event.target.classList.contains('equipment-choice')) {
            event.preventDefault();
            var choice = row.querySelector('.equipment-choice');
            if ( choice && !choice.disabled) {
              choice.checked = !choice.checked;
              applyEquipmentRules(row.closest('.equipment-modal'));
            }
          }
        });

        document.addEventListener('change', function ( event) {
          if ( event.target.classList && event.target.classList.contains('equipment-choice')) {
            applyEquipmentRules(event.target.closest('.equipment-modal'));
          }
        });
      })();
    </script>
    """
  end

  defp compose_menu_items do
    [
      {"AMO",       "AMO — AMO Message"},
      {"FREE",      "FREE — Free Text"},
      {"AFTN_FREE", "AFTN — AFTN Free Text"},
      {"ALR",       "ALR — Alerting Message"},
      {"RCF",       "RCF — Radio Communication Failure"},
      {"FPL",       "FPL — Flight Plan"},
      {"CHG",       "CHG — Modification Message"},
      {"DLA",       "DLA — Delay Message"},
      {"CNL",       "CNL — Cancellation Message"},
      {"DEP",       "DEP — Departure Message"},
      {"ARR",       "ARR — Arrival Message"},
      {"CDN",       "CDN — Co-ordination Message"},
      {"CPL",       "CPL — Current Flight Plan"},
      {"EST",       "EST — Estimate Message"},
      {"ACP",       "ACP — Acceptance Message"},
      {"LAM",       "LAM — Logical Acknowledgement"},
      {"RQP",       "RQP — Request Flight Plan"},
      {"RQS",       "RQS — Request Supplementary Flight Plan"},
      {"SPL",       "SPL — Supplementary Flight Plan"}
    ]
  end

  defp compose_menu_groups do
    [
      {"AMO",       "AMO ( AMO Message )"},
      :sep,
      {"FREE",      "FREE ( Free Text )"},
      :sep,
      {"AFTN_FREE", "AFTN ( AFTN Free Text )"},
      :sep,
      {"ALR",       "ALR ( Alerting Message )"},
      {"RCF",       "RCF ( Radio Communication Failure )"},
      :sep,
      {"FPL",       "FPL ( Flight Plan )"},
      {"DLA",       "DLA ( Delay Message )"},
      {"CHG",       "CHG ( Modification Message )"},
      {"CNL",       "CNL ( Cancellation Message )"},
      {"DEP",       "DEP ( Departure Message )"},
      {"ARR",       "ARR ( Arrival Message )"},
      :sep,
      {"CDN",       "CDN ( Co-ordination Message )"},
      {"CPL",       "CPL ( Current Flight Plan )"},
      {"EST",       "EST ( Estimate Message )"},
      {"ACP",       "ACP ( Acceptance Message )"},
      {"LAM",       "LAM ( Logical Acknowledgement )"},
      :sep,
      {"RQP",       "RQP ( Request Flight Plan )"},
      {"RQS",       "RQS ( Request Supplementary Flight Plan )"},
      {"SPL",       "SPL ( Supplementary Flight Plan)"}
    ]
  end

  defp compose_type_links(selected) do
    compose_menu_items()
    |> Enum.map_join("", fn {type, label} ->
      short = label |> String.split(" — ") |> List.first()
      class = if type == selected, do: " class=\"active\"", else: ""
      ~s(<a#{class} href="/compose?form=#{html(type)}">#{html(short)}</a>)
    end)
  end

  defp compose_form(type, params \\ %{})
  defp compose_form("AMO", _params), do: amo_form()
  defp compose_form("FREE", _params), do: free_message_form()
  defp compose_form("AFTN_FREE", params), do: aftn_free_form(params)
  defp compose_form("ALR", params), do: alr_form(params)
  defp compose_form("RCF", params), do: rcf_form(params)
  defp compose_form("FPL", params), do: fpl_form(params)
  defp compose_form("CHG", _params), do: change_form()
  defp compose_form("DLA", _params), do: basic_flight_form("DLA", "Delay Message")
  defp compose_form("CNL", _params), do: basic_flight_form("CNL", "Cancel Flight Plan")
  defp compose_form("DEP", _params), do: basic_flight_form("DEP", "Departure Message")
  defp compose_form("ARR", _params), do: arrival_form()
  defp compose_form("CDN", _params), do: coordination_form("CDN", "Coordination Message")
  defp compose_form("CPL", _params), do: fpl_like_form("CPL", "Current Flight Plan")
  defp compose_form("EST", _params), do: estimate_form()
  defp compose_form("ACP", _params), do: arrival_form("ACP", "Acceptance Message")
  defp compose_form("LAM", _params), do: lam_form()
  defp compose_form("RQP", _params), do: basic_flight_form("RQP", "Request Flight Plan")
  defp compose_form("RQS", _params), do: basic_flight_form("RQS", "Request Supplementary Flight Plan")
  defp compose_form("SPL", _params), do: fpl_like_form("SPL", "Supplementary Flight Plan")
  defp compose_form(_type, params), do: aftn_free_form(params)

  defp free_text_form(type, title) do
    post_type = if type in ~w(AMO ALR), do: "FREE", else: type

    """
    <section id="#{html(type)}">
      <h2>#{html(title)}</h2>
      <form method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="#{html(post_type)}">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="#{html(type)}">
        <p class="actions"><button type="submit" name="compose_action" value="send_clear">Send+Clear</button> <button type="submit" name="compose_action" value="send">Send #{html(type)}</button> <button type="button" class="js-clear-form">Clear</button></p>
        #{target_fields()}
        #{textarea("message", "Text", "")}
      </form>
    </section>
    """
  end

  defp alr_form(params \\ %{}) do
    priority = compose_prefill(params, "priority", "FF")
    originator = compose_prefill(params, "originator", default_originator())
    lists = alr_reference_lists()

    """
    <section id="ALR" class="aftn-window">
      <div class="aftn-title"><i class="bi bi-exclamation-triangle"></i><span>ALR ( Alerting) Message</span></div>
      <form id="alr-form" class="aftn-form" method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="ALR">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="ALR">
        <div class="aftn-toolbar">
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send_clear"><i class="bi bi-send-plus-fill"></i><span>Send+Clear</span></button>
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-fill"></i><span>Send</span></button>
          <button class="aftn-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save"></i><span>Save</span></button>
          <button class="aftn-tool discard js-clear-form" type="button"><i class="bi bi-eraser"></i><span>Clear</span></button>
          <a class="aftn-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <input type="hidden" name="transmission_id" value="#{html(current_cid_seq())}" data-current-cid-seq>
        <div class="aftn-body">
          <div class="aftn-required-note">Blue field indicates required field.</div>
          #{alr_header(priority, originator, params)}
        </div>
        <div class="alr-scroll">
          #{alr_page_a(params, lists)}
          #{alr_page_b(params, lists)}
          #{alr_page_c(params, lists)}
          #{alr_page_d(params)}
        </div>
        #{alr_datalists(lists)}
        #{alr_equipment_modals()}
      </form>
    </section>
    """
  end

  defp fpl_form(params \\ %{}) do
    priority = compose_prefill(params, "priority", "FF")
    originator = compose_prefill(params, "originator", default_originator())
    lists = alr_reference_lists()

    """
    <section id="FPL" class="aftn-window">
      <div class="aftn-title"><i class="bi bi-airplane"></i><span>FPL ( Filed Flight Plan) Message</span></div>
      <form id="fpl-form" class="aftn-form" method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="FPL">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="FPL">
        <div class="aftn-toolbar">
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send_clear"><i class="bi bi-send-plus-fill"></i><span>Send+Clear</span></button>
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-fill"></i><span>Send</span></button>
          <button class="aftn-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save"></i><span>Save</span></button>
          <button class="aftn-tool discard js-clear-form" type="button"><i class="bi bi-eraser"></i><span>Clear</span></button>
          <a class="aftn-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <input type="hidden" name="transmission_id" value="#{html(current_cid_seq())}" data-current-cid-seq>
        <div class="aftn-body">
          <div class="aftn-required-note">Blue field indicates required field.</div>
          #{aftn_header("fpl", priority, originator, params)}
        </div>
        <div class="alr-scroll">
          #{fpl_page_a(lists)}
          #{fpl_page_b(lists)}
          #{fpl_page_c()}
          #{fpl_page_d()}
        </div>
        #{alr_datalists(lists)}
        #{alr_equipment_modals()}
      </form>
    </section>
    """
  end

  defp alr_header(priority, originator, params), do: aftn_header("alr", priority, originator, params)

  defp aftn_header(prefix, priority, originator, params) do
    """
    <div class="aftn-card">
      <div class="aftn-card-title">AFTN Header</div>
      <div class="aftn-address-row">
        <div class="field required">
          <label>Priority</label>
          <select name="priority" required>
            #{option("FF", "FF", priority)}
            #{option("GG", "GG", priority)}
            #{option("DD", "DD", priority)}
            #{option("SS", "SS", priority)}
            #{option("KK", "KK", priority)}
          </select>
        </div>
        <div class="field required">
          <label>Address</label>
          <div class="aftn-address-grid">#{address_inputs(params)}</div>
        </div>
      </div>
      <div class="aftn-meta">
        <div class="field">
          <label>Filing Time</label>
          <div class="time-control">
            <input id="#{html(prefix)}-filing-time" name="filing_time" value="#{current_filing_time()}">
            <button class="time-button js-current-time" type="button" data-target="#{html(prefix)}-filing-time" title="Use current DDHHMM" aria-label="Use current DDHHMM"><i class="bi bi-clock-history"></i></button>
          </div>
        </div>
        <div class="field required"><label>Originator</label><input name="originator" value="#{html(originator)}" maxlength="8" required></div>
        <div class="field"><label>Originator's Reference</label><input name="originator_reference" value=""></div>
        <label class="aftn-bell"><input type="checkbox" name="bell" value="1"><i class="bi bi-bell-fill" aria-hidden="true"></i><span>Bell</span></label>
      </div>
    </div>
    """
  end

  defp fpl_page_a(lists) do
    """
    <div class="alr-page">
      <div class="alr-page-title">A - ICAO Flight Plan Format</div>
      <div class="alr-grid">
        <div class="alr-row alr-row-head">
          #{fpl_message_number()}
        </div>
        <div class="alr-row alr-row-5">
          #{alr_input("aircraft_id", "7. Aircraft ID", "", required: true, prefix: "-")}
          #{alr_check_field("ssr_mode", "SSR Mode", "A", prefix: "/")}
          #{alr_input("ssr_code", "Code", "")}
          #{alr_select("flight_rules", "8. Flight Rules", "I", ["I", "V", "Y", "Z"], required: true, prefix: "-")}
          #{alr_select("flight_type", "Type of Flight", "S", ["S", "N", "G", "M", "X"], required: true)}
        </div>
        <div class="alr-row alr-row-5b">
          #{alr_input("aircraft_number", "9. Number", "", prefix: "-")}
          #{alr_input("aircraft_type", "Type of Aircraft", "", required: true, list: "alr-aircraft-types")}
          #{alr_select("wake", "Wake Turb. Category", "", wake_options(lists), required: true, prefix: "/")}
          #{alr_equipment_input("equipment", "10a. Equipment and Capabilities", "", "10a", required: true, list: "alr-equipment", prefix: "-")}
          #{alr_equipment_input("equipment_surveillance", "10b. Surveillance Equipment", "", "10b", required: true, list: "alr-surveillance", prefix: "/")}
        </div>
        <div class="alr-row alr-row-2">
          #{alr_input("departure", "13. DEP AD", "", required: true, list: "alr-departures", prefix: "-")}
          #{alr_input("departure_time", "Time", "", required: true)}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_input("speed", "15. Cruising Speed", "", required: true, prefix: "-")}
          #{alr_input("level", "Cruising Level", "", required: true)}
          #{alr_textarea_picker("route", "Route", "", "required", Map.get(lists, :routes, []), required: true)}
        </div>
      </div>
    </div>
    """
  end

  defp fpl_page_b(lists) do
    """
    <div class="alr-page">
      <div class="alr-page-title">B - Destination & Other Information</div>
      <div class="alr-grid">
        <div class="alr-row alr-row-4">
          #{alr_input("destination", "16. DEST AD", "", required: true, list: "alr-destinations", prefix: "-")}
          #{alr_input("eet", "Total EET", "", required: true)}
          #{alr_input("alternate", "DEST ALTN AD", "", list: "alr-destinations")}
          #{alr_input("second_alternate", "2ND DEST ALTN AD", "")}
        </div>
        <div class="alr-row alr-row-18">
          #{alr_equipment_input("sts", "STS/", "", "sts", list: "alr-sts", prefix: "-")}
          #{alr_equipment_input("pbn", "PBN/", "", "pbn", list: "alr-pbn", prefix: "PBN/")}
          #{alr_textarea("nav", "NAV/", "", "", prefix: "NAV/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("com", "COM/", "", "", prefix: "COM/")}
          #{alr_input("dat", "DAT/", "", prefix: "DAT/")}
          #{alr_textarea("sur", "SUR/", "", "", prefix: "SUR/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("dep_info", "DEP/", "", "", prefix: "DEP/")}
          #{alr_textarea("dest_info", "DEST/", "", "", prefix: "DEST/")}
          #{alr_input("dof", "DOF", Calendar.strftime(Date.utc_today(), "%y%m%d"), required: true, prefix: "DOF/")}
        </div>
      </div>
    </div>
    """
  end

  defp fpl_page_c do
    """
    <div class="alr-page">
      <div class="alr-page-title">C - Other Information Detail</div>
      <div class="alr-grid">
        <div class="alr-row alr-row-3">
          #{alr_input("reg", "REG/", "", list: "alr-registrations", prefix: "REG/")}
          #{alr_textarea("eet_detail", "EET/", "", "", prefix: "EET/")}
          #{alr_input("sel", "SEL/", "", prefix: "SEL/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("typ", "TYP/", "", "", prefix: "TYP/")}
          #{alr_input("code", "CODE/", "", prefix: "CODE/")}
          #{alr_textarea("dle", "DLE/", "", "", prefix: "DLE/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("opr", "OPR/", "", "", list: "alr-operators", prefix: "OPR/")}
          #{alr_textarea("orgn", "ORGN/", "", "", prefix: "ORGN/")}
          #{alr_input("per", "PER/", "", prefix: "PER/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("altn_detail", "ALTN/", "", "", prefix: "ALTN/")}
          #{alr_textarea("ralt", "RALT/", "", "", prefix: "RALT/")}
          #{alr_textarea("talt", "TALT/", "", "", prefix: "TALT/")}
        </div>
        <div class="alr-row alr-row-2">
          #{alr_textarea("rif", "RIF/", "", "", prefix: "RIF/")}
          #{alr_textarea("rmk", "RMK/", "", "", prefix: "RMK/")}
        </div>
      </div>
    </div>
    """
  end

  defp fpl_page_d do
    """
    <div class="alr-page">
      <div class="alr-page-title">D - Supplementary Information</div>
      <div class="alr-grid">
        <div class="alr-row alr-row-sup">
          #{alr_input("endurance", "19. Endurance HR/MIN", "", prefix: "-E/")}
          #{alr_input("pob", "Person on Board", "", prefix: "P/")}
          <div class="field"#{alr_title_attr(alr_tooltip("emergency_radio"))}><label#{alr_title_attr(alr_tooltip("emergency_radio"))}>Emergency Radio</label><div class="alr-checks">#{alr_check("radio_uhf", "UHF")}#{alr_check("radio_vhf", "VHF")}#{alr_check("radio_elt", "ELT")}</div></div>
        </div>
        <div class="alr-row alr-row-2">
          <div class="field"#{alr_title_attr(alr_tooltip("survival_equipment"))}><label#{alr_title_attr(alr_tooltip("survival_equipment"))}>Survival Equipment</label><div class="alr-checks">#{alr_check("survival_polar", "Polar")}#{alr_check("survival_desert", "Desert")}#{alr_check("survival_maritime", "Maritime")}#{alr_check("survival_jungle", "Jungle")}</div></div>
          <div class="field"#{alr_title_attr(alr_tooltip("jackets"))}><label#{alr_title_attr(alr_tooltip("jackets"))}>Jackets</label><div class="alr-checks">#{alr_check("jackets_light", "Light")}#{alr_check("jackets_fluores", "Fluores")}#{alr_check("jackets_uhf", "UHF")}#{alr_check("jackets_vhf", "VHF")}</div></div>
        </div>
        <div class="alr-row alr-row-4">
          #{alr_input("dinghy_number", "Dinghy Number", "", prefix: "D/")}
          #{alr_input("dinghy_capacity", "Dinghy Capacity", "")}
          <div class="field"#{alr_title_attr(alr_tooltip("dinghy_cover"))}><label#{alr_title_attr(alr_tooltip("dinghy_cover"))}>Dinghy Cover</label><div class="alr-checks">#{alr_check("dinghy_cover", "Cover")}</div></div>
          #{alr_input("dinghy_colour", "Dinghy Colour", "")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_input("aircraft_colour", "Aircraft Colour and Markings", "", prefix: "A/")}
          #{alr_input("remarks", "Remarks", "", prefix: "N/")}
          #{alr_input("pilot", "Pilot in Command", "", prefix: "C/")}
        </div>
        <div class="aftn-footer alr-full">
          <div class="aftn-filled"#{alr_title_attr(alr_tooltip("filed_by"))}>
            <label for="fpl-filed-by"#{alr_title_attr(alr_tooltip("filed_by"))}>Filled By</label>
            <input id="fpl-filed-by" name="filed_by" value="SYSTEM" placeholder="Operator"#{alr_title_attr(alr_tooltip("filed_by"))}>
          </div>
        </div>
      </div>
    </div>
    """
  end

  defp alr_page_a(_params, lists) do
    """
    <div class="alr-page">
      <div class="alr-page-title">A - ICAO Flight Plan Format</div>
      <div class="alr-grid">
        <div class="alr-row alr-row-head">
          #{alr_message_number()}
          #{alr_input("reference_data", "Reference Data", "")}
          #{alr_select("phase", "5. Phase of Emergency", "", ["INCERFA", "ALERFA", "DETRESFA"], required: true, prefix: "-")}
          #{alr_input("alert_originator", "Originator of Message", "", required: true, prefix: "/")}
          #{alr_input("nature", "Nature of Emergency", "", required: true, prefix: "/")}
        </div>
        <div class="alr-row alr-row-5">
          #{alr_input("aircraft_id", "7. Aircraft ID", "", required: true, prefix: "-")}
          #{alr_check_field("ssr_mode", "SSR Mode", "A", prefix: "/")}
          #{alr_input("ssr_code", "Code", "")}
          #{alr_select("flight_rules", "8. Flight Rules", "I", ["I", "V", "Y", "Z"], required: true, prefix: "-")}
          #{alr_select("flight_type", "Type of Flight", "S", ["S", "N", "G", "M", "X"], required: true)}
        </div>
        <div class="alr-row alr-row-5b">
          #{alr_input("aircraft_number", "9. Number", "", prefix: "-")}
          #{alr_input("aircraft_type", "Type of Aircraft", "", required: true, list: "alr-aircraft-types")}
          #{alr_select("wake", "Wake Turb. Category", "", wake_options(lists), required: true, prefix: "/")}
          #{alr_equipment_input("equipment", "10a. Equipment and Capabilities", "", "10a", required: true, list: "alr-equipment", prefix: "-")}
          #{alr_equipment_input("equipment_surveillance", "10b. Surveillance Equipment", "", "10b", required: true, list: "alr-surveillance", prefix: "/")}
        </div>
        <div class="alr-row alr-row-2">
          #{alr_input("departure", "13. DEP AD", "", required: true, list: "alr-departures", prefix: "-")}
          #{alr_input("departure_time", "Time", "", required: true)}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_input("speed", "15. Cruising Speed", "", required: true, prefix: "-")}
          #{alr_input("level", "Cruising Level", "", required: true)}
          #{alr_textarea_picker("route", "Route", "", "required", Map.get(lists, :routes, []), required: true)}
        </div>
      </div>
    </div>
    """
  end

  defp alr_page_b(_params, lists) do
    """
    <div class="alr-page">
      <div class="alr-page-title">B - Destination & Other Information</div>
      <div class="alr-grid">
        <div class="alr-row alr-row-4">
          #{alr_input("destination", "16. DEST AD", "", required: true, list: "alr-destinations", prefix: "-")}
          #{alr_input("eet", "Total EET", "", required: true)}
          #{alr_input("alternate", "DEST ALTN AD", "", required: true, list: "alr-destinations")}
          #{alr_input("second_alternate", "2ND DEST ALTN AD", "")}
        </div>
        <div class="alr-row alr-row-18">
          #{alr_equipment_input("sts", "STS/", "", "sts", list: "alr-sts", prefix: "-")}
          #{alr_equipment_input("pbn", "PBN/", "", "pbn", list: "alr-pbn", prefix: "PBN/")}
          #{alr_textarea("nav", "NAV/", "", "", prefix: "NAV/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("com", "COM/", "", "", prefix: "COM/")}
          #{alr_input("dat", "DAT/", "", prefix: "DAT/")}
          #{alr_textarea("sur", "SUR/", "", "", prefix: "SUR/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("dep_info", "DEP/", "", "", prefix: "DEP/")}
          #{alr_textarea("dest_info", "DEST/", "", "", prefix: "DEST/")}
          #{alr_input("dof", "DOF", Calendar.strftime(Date.utc_today(), "%y%m%d"), required: true, prefix: "DOF/")}
        </div>
      </div>
    </div>
    """
  end

  defp alr_page_c(_params, _lists) do
    """
    <div class="alr-page">
      <div class="alr-page-title">C - Other Information Detail</div>
      <div class="alr-grid">
        <div class="alr-row alr-row-3">
          #{alr_input("reg", "REG/", "", list: "alr-registrations", prefix: "REG/")}
          #{alr_textarea("eet_detail", "EET/", "", "", prefix: "EET/")}
          #{alr_input("sel", "SEL/", "", prefix: "SEL/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("typ", "TYP/", "", "", prefix: "TYP/")}
          #{alr_input("code", "CODE/", "", prefix: "CODE/")}
          #{alr_textarea("dle", "DLE/", "", "", prefix: "DLE/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("opr", "OPR/", "", "", list: "alr-operators", prefix: "OPR/")}
          #{alr_textarea("orgn", "ORGN/", "", "", prefix: "ORGN/")}
          #{alr_input("per", "PER/", "", prefix: "PER/")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_textarea("altn_detail", "ALTN/", "", "", prefix: "ALTN/")}
          #{alr_textarea("ralt", "RALT/", "", "", prefix: "RALT/")}
          #{alr_textarea("talt", "TALT/", "", "", prefix: "TALT/")}
        </div>
        <div class="alr-row alr-row-2">
          #{alr_textarea("rif", "RIF/", "", "", prefix: "RIF/")}
          #{alr_textarea("rmk", "RMK/", "", "", prefix: "RMK/")}
        </div>
      </div>
    </div>
    """
  end

  defp alr_page_d(_params) do
    """
    <div class="alr-page">
      <div class="alr-page-title">D - Supplementary & Search Rescue</div>
      <div class="alr-grid">
        <div class="alr-row alr-row-sup">
          #{alr_input("endurance", "19. Endurance HR/MIN", "", prefix: "-E/")}
          #{alr_input("pob", "Person on Board", "", prefix: "P/")}
          <div class="field"#{alr_title_attr(alr_tooltip("emergency_radio"))}><label#{alr_title_attr(alr_tooltip("emergency_radio"))}>Emergency Radio</label><div class="alr-checks">#{alr_check("radio_uhf", "UHF")}#{alr_check("radio_vhf", "VHF")}#{alr_check("radio_elt", "ELT")}</div></div>
        </div>
        <div class="alr-row alr-row-2">
          <div class="field"#{alr_title_attr(alr_tooltip("survival_equipment"))}><label#{alr_title_attr(alr_tooltip("survival_equipment"))}>Survival Equipment</label><div class="alr-checks">#{alr_check("survival_polar", "Polar")}#{alr_check("survival_desert", "Desert")}#{alr_check("survival_maritime", "Maritime")}#{alr_check("survival_jungle", "Jungle")}</div></div>
          <div class="field"#{alr_title_attr(alr_tooltip("jackets"))}><label#{alr_title_attr(alr_tooltip("jackets"))}>Jackets</label><div class="alr-checks">#{alr_check("jackets_light", "Light")}#{alr_check("jackets_fluores", "Fluores")}#{alr_check("jackets_uhf", "UHF")}#{alr_check("jackets_vhf", "VHF")}</div></div>
        </div>
        <div class="alr-row alr-row-4">
          #{alr_input("dinghy_number", "Dinghy Number", "", prefix: "D/")}
          #{alr_input("dinghy_capacity", "Dinghy Capacity", "")}
          <div class="field"#{alr_title_attr(alr_tooltip("dinghy_cover"))}><label#{alr_title_attr(alr_tooltip("dinghy_cover"))}>Dinghy Cover</label><div class="alr-checks">#{alr_check("dinghy_cover", "Cover")}</div></div>
          #{alr_input("dinghy_colour", "Dinghy Colour", "")}
        </div>
        <div class="alr-row alr-row-3">
          #{alr_input("aircraft_colour", "Aircraft Colour and Markings", "", prefix: "A/")}
          #{alr_input("remarks", "Remarks", "", prefix: "N/")}
          #{alr_input("pilot", "Pilot in Command", "", prefix: "C/")}
        </div>
        <div class="alr-row alr-row-1">
          #{alr_textarea("sar_info", "20. Alerting Search and Rescue Information", "", "required", required: true, prefix: "-", suffix: ")")}
        </div>
        <div class="aftn-footer alr-full">
          <div class="aftn-filled"#{alr_title_attr(alr_tooltip("filed_by"))}>
            <label for="alr-filed-by"#{alr_title_attr(alr_tooltip("filed_by"))}>Filled By</label>
            <input id="alr-filed-by" name="filed_by" value="SYSTEM" placeholder="Operator"#{alr_title_attr(alr_tooltip("filed_by"))}>
          </div>
        </div>
      </div>
    </div>
    """
  end

  defp alr_input(name, label, value, opts \\ []) do
    required = if Keyword.get(opts, :required, false), do: " required", else: ""
    readonly = if Keyword.get(opts, :readonly, false), do: " readonly", else: ""
    class = Keyword.get(opts, :class, "")
    list = if list_id = Keyword.get(opts, :list), do: ~s( list="#{html(list_id)}"), else: ""
    maxlength =
      cond do
        max = Keyword.get(opts, :maxlength) -> ~s( maxlength="#{max}")
        to_string(name) in ["originator", "alert_originator"] -> ~s( maxlength="8")
        true -> ""
      end
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field #{html(class)}#{required}"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<input name="#{html(name)}" value="#{html(value)}"#{readonly}#{required}#{list}#{maxlength}#{title}>#{suffix}</div>
    </div>
    """
  end

  defp alr_equipment_input(name, label, value, picker, opts \\ []) do
    required = if Keyword.get(opts, :required, false), do: " required", else: ""
    list = if list_id = Keyword.get(opts, :list), do: ~s( list="#{html(list_id)}"), else: ""
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field#{required}"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control equipment-control">#{prefix}<input name="#{html(name)}" value="#{html(value)}" readonly#{required}#{list}#{title}><button class="equipment-open js-equipment-open" type="button" data-equipment="#{html(picker)}" data-target="#{html(name)}" title="Choose #{html(label)}"><i class="bi bi-list-check"></i></button>#{suffix}</div>
    </div>
    """
  end

  defp alr_message_number do
    message_type_tip = alr_title_attr(alr_tooltip("message_type"))
    number_tip = alr_title_attr(alr_tooltip("alr_number"))

    """
    <div class="field"#{message_type_tip}>
      <label#{message_type_tip}>3. Message Type / Number</label>
      <div class="alr-control compact">
        #{alr_mark("(")}
        <input name="message_type" value="ALR" readonly#{message_type_tip}>
        <input name="alr_number" value=""#{number_tip}>
      </div>
    </div>
    """
  end

  defp fpl_message_number do
    message_type_tip = alr_title_attr(alr_tooltip("message_type"))
    number_tip = alr_title_attr(alr_tooltip("alr_number"))

    """
    <div class="field"#{message_type_tip}>
      <label#{message_type_tip}>3. Message Type / Number</label>
      <div class="alr-control compact">
        #{alr_mark("(")}
        <input name="message_type" value="FPL" readonly#{message_type_tip}>
        <input name="message_number" value=""#{number_tip}>
      </div>
    </div>
    """
  end

  defp alr_textarea(name, label, value, class \\ "", opts \\ []) do
    required = if Keyword.get(opts, :required, false), do: " required", else: ""
    list = if list_id = Keyword.get(opts, :list), do: ~s( list="#{html(list_id)}"), else: ""
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field #{html(class)}"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<textarea class="alr-text" name="#{html(name)}" spellcheck="false"#{required}#{list}#{title}>#{html(value)}</textarea>#{suffix}</div>
    </div>
    """
  end

  defp alr_textarea_picker(name, label, value, class, values, opts \\ []) do
    picker =
      case uniq_present(values) do
        [] ->
          ""

        present ->
          """
          <select class="alr-picker" onchange="this.parentElement.querySelector('textarea').value=this.value">
            #{option("", "Pilih dari database", "")}
            #{Enum.map_join(present, "", &option(&1, &1, ""))}
          </select>
          """
      end

    required = if Keyword.get(opts, :required, false), do: " required", else: ""
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field #{html(class)}"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<textarea class="alr-text" name="#{html(name)}" spellcheck="false"#{required}#{title}>#{html(value)}</textarea>#{suffix}</div>
      #{picker}
    </div>
    """
  end

  defp alr_check(name, label, opts \\ []) do
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))
    ~s(<label#{title}><input type="checkbox" name="#{html(name)}" value="1"#{title}>#{html(label)}</label>)
  end

  defp alr_check_field(name, label, value, opts \\ []) do
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<label class="alr-inline-check"#{title}><input type="checkbox" name="#{html(name)}" value="#{html(value)}"#{title}>#{html(value)}</label>#{suffix}</div>
    </div>
    """
  end

  defp alr_select(name, label, selected, values, opts \\ []) do
    required = if Keyword.get(opts, :required, false), do: " required", else: ""
    class = Keyword.get(opts, :class, "")
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field #{html(class)}#{required}"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<select name="#{html(name)}"#{required}#{title}>
          #{option("", "", selected)}
          #{Enum.map_join(values, "", &option(&1, &1, selected))}
        </select>#{suffix}</div>
    </div>
    """
  end

  defp alr_mark(nil), do: ""
  defp alr_mark(""), do: ""
  defp alr_mark(value), do: ~s(<span class="alr-mark">#{html(value)}</span>)

  defp alr_title_attr(nil), do: ""
  defp alr_title_attr(""), do: ""
  defp alr_title_attr(value), do: ~s( title="#{html(value)}")

  defp alr_tooltip("message_type") do
    """
    Message Type

    3 LETTERS as follows:

    ALR - Alerting
    RCF - Radio Communication Failure
    FPL - Filed Flight Plan
    DLA - Delay
    CHG - Modification
    CNL - Cancellation
    DEP - Departure
    ARR - Arrival
    CDN - Co-ordination
    CPL - Current Flight Plan
    EST - Estimate
    ACP - Acceptance
    LAM - Logical Acknowledgement
    RQP - Request Flight Plan
    RQS - Request Supplementary Flight Plan
    SPL - Supplementary Flight Plan
    """
  end

  defp alr_tooltip("alr_number") do
    """
    Message Number

    1 to 4 LETTER(S) identifying the sending
    ATS unit, followed by

    OBLIQUE STROKE ( /) followed by

    1 to 4 LETTER(S) identifying the receiving
    ATS unit, followed by

    3 DECIMAL NUMERICS giving the serial number
    of this message in the sequence of messages
    transmitted by this unit to the indicated
    receiving ATS unit.
    """
  end

  defp alr_tooltip("reference_data") do
    """
    Reference Data

    1 to 4 LETTER(S) followed by OBLIQUE STROKE ( /)
    followed by 1 to 4 LETTER(S) followed by

    3 DECIMAL NUMERICS, giving the 'message number'
    contained in element ( b) of the operational
    message which began the sequence of messages of
    which this message is a part.
    """
  end

  defp alr_tooltip("phase") do
    """
    Phase of Emergency
    ---------------------------
    INCERFA if an uncertainly phase, or
    ALERFA if an alert phase, or
    DETRESFA if a distress phase

    has been declared in respect of the aircraft concerned.
    """
  end

  defp alr_tooltip("alert_originator"), do: "Originator of Message\n------------------------------\n8 LETTERS, being the 4-letter ICAO location indicator plus the 3-letter designator of the ATS unit originating the message followed by the letter X or, if applicable, the one-letter designator identifying the division of the ATS unit originating the message."
  defp alr_tooltip("nature"), do: "Nature of Emergency\n-----------------------------\nSHORT PLAIN LANGUAGE TEXT, as necessary to explain the nature of the emergency, with natural spaces between the words."
  defp alr_tooltip("aircraft_id"), do: "Aircraft Identification\n\nINSERT one of the following aircraft identification, not exceeding 7 alphanumeric characters and without hypens or symbols:\n\na) the ICAO designator for the aircraft operating agency followed by the flight identification ( e.g. KLM511, NGA213, JTR25) when in radiotelephony the call sign to be used by the aircraft will consist of the ICAO telephony designator for the operating agency followed by the flight identification ( e.g. KLM511, NIGERIA 213, JESTER 25);\n\nb) in the radiotelephony the call sign to be used by the aircraft will consist of this identification alone ( e.g. CGAJS), or preceded by the ICAO telephony designator for the aircraft operating agency ( e.g. BLIZZARD CGAJS);"
  defp alr_tooltip("ssr_mode"), do: "SSR Mode\n\n1 LETTER giving the SSR Mode related to ( c)."
  defp alr_tooltip("ssr_code"), do: "SSR Code\n\n4 NUMERICS giving the SSR Code assigned to the aircraft by ATS and transmitted in the Mode given in ( b)."
  defp alr_tooltip("radio_failure"), do: "Radio Failure Information\n\nThis field consist of the following specified sequence of elements preceded by a single hypen and separated by spaces. Any information not available is to be shown as 'NIL' or 'NOT KNOWN' and not simply omitted.\n\n(a) Time of Last Two-way Contact : 4 NUMERICS giving the time of the last two-way contact with the aircraft.\n(b) Frequency of Last Contact : NUMERICS as necessary giving the transmitting/receiving frequency of the last two-way contact with the aircraft.\n(c) Last Reported Position : The last reported position expressed in one of the data conventions of 1.6 of this Appendix.\n(d) Time at Last Reported Position : 4 NUMERICS giving the time at the last reported position.\n(e) Remaining COM Capability : LETTERS as necessary identifying the remaining COM capability of the aircraft, if known, using the convention of Filed Type 10, or in plain language.\n(f) Any Necessary Remarks : Plain language text as necessary."

  defp alr_tooltip("flight_rules") do
    """
    Flight Rules

    INSERT one of the following letters to denote the category of flight rules with which the pilot intends to comply:

    - I if it is intended that the entire flight will be operated under the IFR
    - V if it is intended that the entire flight will be operated under the VFR
    - Y if the flight initially will be operated under the IFR, followed by one or more subsequent changes of flight rules
    - Z if the flight initially will be operated under the VFR, followed by one or more subsequent changes of flight rules

    Specify in Item 15 the point or points at which a change of flight rules is planned.
    """
  end

  defp alr_tooltip("flight_type") do
    """
    Type of Flight

    INSERT one of the following letters to denote the type of flight when so required by the appropriate ATS authority:

    - S if scheduled air service
    - N if non-scheduled air transport operation
    - G if general aviation
    - M if military
    - X if other than any of the defined categories above.

    Specify status of a flight following the indicator STS in Item 18, or when necessary to denote other reasons for specific handling by ATS, indicate the reason following the indicator RMK/ in Item 18.
    """
  end

  defp alr_tooltip("aircraft_number"), do: "Number of Aircraft ( if more than one)\n\n1 to 2 NUMERICS giving the number of aircraft in the flight."
  defp alr_tooltip("aircraft_type"), do: "Type of Aircraft\n\n2 to 4 CHARACTERS, being the appropriate designator chosen from ICAO Doc 8643, Aircraft Type Designators, or\n\nZZZZ if no designator has been assigned or if there is more than one type of aircraft in the flight."
  defp alr_tooltip("wake"), do: "Wake Turbulence Category\n\n1 LETTER to indicate maximum certificated take-off mass of the aircraft:\n\nH - Heavy\nM - Medium\nL - Light\nJ - Jumbo"

  defp alr_tooltip("equipment") do
    """
    Radio Communication, Navigation and Approach Aid Equipment and Capabilities

    1 LETTER as follows:
    N: no COM/NAV/approach aid equipment for the route to be
        flown is carried, or the equipment is unserviceable, OR

    S: Standard COM/NAV/approach aid equipment for the route
        to be flown is carried and serviceable, AND/OR INSERT

    ONE OR MORE OF THE FOLLOWING LETTERS to indicate the serviceable COM/NAV/approach aid equipment and capabilities:
    A : GBAS landing system
    B : LPV ( APV with SBAS)
    C : LORAN C
    D : DME
    E1: FMC WPR ACARS
    E2: D-FIS ACARS
    E3: PDC ACARS
    F : ADF
    G : GNSS
    H : HF RTF
    I : Inertial Navigation
    J1: CPDLC ATN VDL Mode 2
    J2: CPDLC FANS 1/A HFDL
    J3: CPDLC FANS 1/A VDL Mode A
    J4: CPDLC FANS 1/A VDL Mode 2
    J5: CPDLC FANS 1/A SATCOM ( INMARSAT)
    J6: CPDLC FANS 1/A SATCOM ( MTSAT)
    J7: CPDLC FANS 1/A SATCOM ( Iridium)
    K : MLS
    L : ILS
    M1: ATC RTF SATCOM ( INMARSAT)
    M2: ATC RTF ( MTSAT)
    M3: ATC RTF ( Iridium)
    O : VOR
    P1-P9: Reserved for RCP
    R : PBN approved
    T : TACAN
    U : UHF RTF
    V : VHF RTF
    W : RVSM approved
    X : MNPS approved
    Y : VHF with 8.33 kHz channel spacing capability
    Z : Other equipment carried or other capabilities
    """
  end

  defp alr_tooltip("equipment_surveillance") do
    """
    Surveillance equipment and capabilities

    N   if no surveillance equipment for the route to be flown is
         carried, or the equipment is unserviceable
    A   Transponder - Mode A ( 4 digits - 4 096 codes)
    C   Transponder - Mode A ( 4 digits - 4 096 codes) and Mode C
    E   Transponder - Mode S, including aircraft identification,
         pressure-altitude and extend squitter ( ADS-B) capability
    H   Transponder - Mode S, including aircraft identification,
         pressure-altitude and enhanced survilliance capability
    I   Transponder - Mode S, including aircraft identification,
         but no pressure-altitude capability
    L   Transponder - Mode S, including aircraft identification,
         pressure-altitude, extend squitter ( ADS-B) and enhanced
         survilliance capability
    P   Transponder - Mode S, including pressure-altitude, but no
         aircraft identification capability
    S   Transponder - Mode S, including both pressure altitude and
         aircraft identification capability
    X   Transponder - Mode S with neither aircraft identification
         nor pressure-altitude capability

    ADS B
    B1  ADS-B with dedicated 1090 MHz ADS-B 'out' capability
    B2  ADS-B with dedicated 1090 MHz ADS-B 'out' and 'in' capability
    U1  ADS-B 'out' capability using UAT
    U2  ADS-B 'out' and 'in' capability using UAT
    V1  ADS-B 'out' capability using VDL Mode 4
    V2  ADS-B 'out' and 'in' capability using VDL Mode 4

    ADS C
    D1  ADS-C with FANS 1/A capabilities
    G1  ADS-C with ATN capabilities

    Alphanumeric characters not indicated above are reserved.
    """
  end

  defp alr_tooltip("departure"), do: "Departure Aerodrome\n\nINSERT the ICAO four-letter location indicator of the departure aerodrome as specified in Doc 7910, Location Indicators,\n\nOR if no location indicator has been assigned,\n\nINSERT ZZZZ and SPECIFY, in Item 18, the name and location of the aerodrome preceded by DEP/\n\nOR the first point of the route or the marker radio beacon preceded by DEP/..., if the aircraft has not taken off from the aerodrome,\n\nOR if the flight plan is received from an aircraft in flight,\n\nINSERT AFIL and SPECIFY, in Item 18, the ICAO four-letter location indicator of the location ATS unit from which supplementary flight plan data can be obtained, preceded by DEP/"
  defp alr_tooltip("departure_time"), do: "Time\n\n4 NUMERICS giving\n\nthe estimated off-block time at the aerodrome in ( a) in FPL and DLA messages transmitted before departure and in RQP message, if known, or\n\nthe actual time of departure from the aerodrome in ( a) in ALR, DEP and SPL messages, or\n\nthe actual or estimated time of departure from the first point shown in the Route Field ( see Field Type 15) in FPL messages derived from flight plans filed in the air, as shown by the letters AFIL in ( a)."
  defp alr_tooltip("speed"), do: "Cruising speed ( maximum 5 characters)\n\nINSERT the True Air Speed for the first or the whole cruising portion of the flight, in terms of:\n- Kilometres per hour, expressed as K followed by 4 figures\n  ( e.g. K0830), or\n- Knots, expressed as N followed by 4 figures ( e.g. N0485), or\n- True Mach number, when so prescribed by the appropriate\n  ATS authority, to the nearest hundredth of unit Mach,\n  expressed as M followed by 3 figures ( e.g M082)."
  defp alr_tooltip("level"), do: "Cruising level ( maximum 5 characters)\n\nINSERT the planned cruising level for the first or the whole portion of the route to be flown, in terms if:\n- Flight level, expressed as F followed by 3 figures ( e.g.\n  F085; F330), or\n- *Standard Metric Level in tens of metres, expressed as\n  S followed by 4 figures ( e.g. S1130), or\n- Altitude in hundreds of feet, expressed as A followed by\n  3 figures ( e.g. A045; A100), or\n- Altitude in tens of metres, expressed as M followed by 4\n  figures ( e.g. M0840), or\n- for uncontrolled VFR flights, the letters VFR.\n\n*When so prescribed by the appropriate ATS authorities."
  defp alr_tooltip("route") do
    """
    Route ( including changes of speed, level and/or flight rules)

    Flights along designated ATS routes
    1. ATS route ( 2 to 7 characters)
      The coded designator assigned to the route or route segment
      including, where appropriate, the coded designator
      assigned to the standard departure or arrival route
      ( e.g. BCN1, B1, R14, UB10, KODAP2A)

    2. Significant point ( 2 to 11 characters)
       The coded designator ( 2 to 5 chars) assigned to the point
       ( e.g. LN, MAY, HADDY), or if no coded designator has been
       assigned, one of the following ways:
       - Degress only ( 7 chars), e.g. 46N078W
       - Degress and minutes ( 11 chars), e.g. 4620N07805W
       - Bearing distance from significant point, e.g. DUB180040

    3. Change of speed or level ( maximum 21 characters)
       The point at which a change of speed ( 5% TAS or 0.01 Mach
       or more) or a change of level is planned to commence,
       expressed exactly as in ( 2) above.
       e.g. LN/N0284A045; MAY/N0305F330; HADDY/
       N0420F330; 4602N07805W/N0500F350; 46N078W/
       M082F330; DUB180040/N0350M0840

    4. Change of flight rules ( maximum 3 characters)
       The point at which the change of flight rules is planned,
       expressed exactly as in ( 2) or ( 3) above as appropriate,
       followed by a space and one of the following:
       - VFR if from IFR to VFR    - IFR if from VFR to IFR
       e.g. LN VFR; LN/N0284A050 IFR

    5. Cruise climb ( maximum 28 characters)
       e.g. C/48N050W/M082F290F350; C/48N050W/
       M082F290PLUS; C/52N050W/M220F580F620;
    """
  end
  defp alr_tooltip("destination"), do: "Destination aerodrome\n\nINSERT the ICAO four-letter location indicator of the destination aerodrome as specfied in Doc 7910, Location Indicators,\n\nOR, if no location indicator has been assigned,\n\nINSERT ZZZZ and SPECIFY in Item 18 the name and location of the aerodrome, preceded by DEST/"
  defp alr_tooltip("eet"), do: "Total Estimated Elapsed Time\n\n4 NUMERICS, giving the total estimated elapsed time."
  defp alr_tooltip("alternate"), do: "Destination alternate aerodrome(s)\n\nINSERT the ICAO four-letter location indicator(s) of not more than two destination alternate aerodromes, as specfied in Doc 7910, Location Indicators, separated by a space,\n\nOR, if no location indicator has been assigned to the destination alternate aerodrome(s),\n\nINSERT ZZZZ and SPECIFY in Item 18 the name and location of the destination alternate aerodrome(s), preceded by ALTN/"
  defp alr_tooltip("second_alternate"), do: alr_tooltip("alternate")

  defp alr_tooltip("sts"), do: "Reason for special handling by ATS, e.g. a search and rescue mission, as follows:\n\nALTRV:\tfor a flight operated in accordance with an altitude reservation;\nATFMX:\tfor a flight approved for exemption from ATFM measures by the appropriate ATS authority;\nFFR:\tfire-fighting;\nFLTCK:\tflight check for calibration of navaids;\nHAZMAT:for a flight carrying hazardous material;\nHEAD:\ta flight with Head of State status;\nHOSP:\tfor medical flight declared by medical authorities;\nHUM:\tfor a flight operating on a humanitarian mission;\nMARSA:for a flight for which a military entity assumes responsibility for separation of military aircraft;\nMEDEVAC: for a life critical medical emergency evacuation;\nNONRVSM: for non-RVSM capable flight intending to operate in RVSM airspace;\nSAR:\tfor a flight engaged in a search and rescue mission;\nSTATE:\tfor a flight engaged in military, customs or police services."
  defp alr_tooltip("pbn"), do: "Indication of RNAV and/or RNP capabilities. Include as many of the descriptors below, as apply to the flight, up to maximum 8 entries, i.e. a total of not more than 16 characters.\n\nRNAV SPECIFICATION\nA1: RNAV 10 ( RNP 10)\tC1: RNAV 2 all permitted sensors\nB1: RNAV 5 all permitted sensors\tC2: RNAV 2 GNSS\nC3: RNAV 2 DME/DME\nB2: RNAV 5 GNSS\tC4: RNAV 2 DME/DME/IRU\nB3: RNAV 5 DME/DME\tD1: RNAV 1 all permitted sensors\nB4: RNAV 5 VOR/DME\tD2: RNAV 1 GNSS\nB5: RNAV 5 INS or IRS\tD3: RNAV 1 DME/DME\nB6: RNAV 5 LORANC\tD4: RNAV 1 DME/DME/IRU\n\nRNP SPECIFICATION\nL1: RNP 4\tS1: RNP APCH\nO1: Basic RNP 1 all permitted sensors\tS2: RNP APCH with BAR-VNAV\nO2: Basic RNP 1 GNSS\tT1: RNP AR APCH with RF ( special auth. req.)\nO3: Basic RNP 1 DME/DME\tT2: RNP AR APCH without RF ( special auth. req.)\nO4: Basic RNP 1 DME/DME/IRU\n\nCombinations of alphanumeric characters not indicated above are reserved.\n\n- If B1, B2, C1, C2, D1, D2, O1 or O2 are filed, then a 'G' must be included in Field 10a\n- If B1, B3, C1, C3, D1, D3, O1 or O3 are filed, then a 'D' must be included in Field 10a\n- If B1 or B4 is filed, then an 'O' or 'S' and a 'D' must be included in Field 10a ( i.e., 'OD' or 'SD' must appear in 10a)\n- If B1, B5 or C1 are filed, then an 'I' must be included in Field 10a; and\n- If C1, C4, D1, D4, O1 or O4 are filed, then a 'D' and an 'I' must be included in Field 10a ( i.e., 'DI' must appear in 10a)"
  defp alr_tooltip("nav"), do: "Significant data related to navigation equipment, other than specified in PBN/, as required by the appropriate ATS authority. Indicate GNSS augmentation under this indicator, with a space between two or more methods of augmentation, e.g. NAV/GBAS SBAS."
  defp alr_tooltip("com"), do: "Indicate communications applications or capabilities not specified in Item 10a."
  defp alr_tooltip("dat"), do: "Indicate data applications or capabilities not specified in Item 10a."
  defp alr_tooltip("sur"), do: "Include surveillance applications or capabilities not specified in Item 10b."
  defp alr_tooltip("dep_info"), do: "Name and location of departure aerodrome, if ZZZZ is inserted in Item 13, or the ATS unit from which supplementary flight plan data can be obtained, if AFIL is inserted in Item 13. For aerodromes not listed in the relevant AIP, indicate location as follows:\n\nWith 4 figures describing latitude in degrees and tens and units of minutes followed by 'N' ( North) or 'S' ( South), followed by 5 figures describing longitude in degrees and tens and units of minutes, followed by 'E' ( East) or 'W' ( West). Make up the correct number of figures, where necessary, by insertion of zeros, e.g. 4620N07805W ( 11 characters).\n\nOR, Bearing and distance from the nearest significant point, as follows:\n\nThe identification of the significant point followed by the bearing from the point in the form of 3 figures giving degrees magnetic, followed by the distance from the point in the form of 3 figures expressing nautical miles. In areas of high latitude where it is determined by the appropriate authority that reference to degrees magnetic is impractical, degrees true may be used. Make up the correct number of figures, where necessary, by insertion of zeros, e.g. a point of 180 degrees magnetic at a distance of 40 nautical miles from VOR 'DUB' should be expressed as DUB180040.\n\nOR, The first point of the route ( name or LAT/LONG) or the marker radio beacon, if the aircraft has not taken off from an aerodrome."
  defp alr_tooltip("dest_info"), do: "Name and location of destination aerodrome, if ZZZZ is inserted in Item 16. For aerodromes not listed in the relevant AIP, indicate location in LAT/LONG or bearing and distance from the nearest significant point, as described under DEP/."
  defp alr_tooltip("dof"), do: "The date of flight departure in six figure format ( YYMMDD, where YY equals the year, MM equals the month and DD equals the day)."
  defp alr_tooltip("reg"), do: "The nationality or common mark and registration mark of the aircraft, if different from the aircraft identification in field AIRCRAFT ID."
  defp alr_tooltip("eet_detail"), do: "Significant point of FIR boundary designators and accumulated estimated elapsed times from take-off to such points or FIR boundaries, when so prescribed on the basis of regional air navigation agreements, or by the appropriate ATS authority.\n\ne.g., EET/CAP0745 XYZ080 or EET/EINN0204"
  defp alr_tooltip("sel"), do: "SELCAL Code, for aircraft so equipped. A single string of four letters."
  defp alr_tooltip("typ"), do: "Type(s) of aircraft, preceded if necessary without a space by number(s) of aircraft and separated by one space, if ZZZZ is inserted in Item 9.\n\ne.g., TYP/2F15 5F5 3B2"
  defp alr_tooltip("code"), do: "Aircraft address ( expressed in the form of an alphanumerical code of six hexadecimal characters) when required by the appropriate ATS authority. Example: 'F00001' is the lowest aircraft address contained in the specific block administered by ICAO."
  defp alr_tooltip("dle"), do: "Enroute delay or holding, insert the significant point(s) on the route where a delay is planned to occur, followed by the length of delay using four figure time in hours and minutes ( hhmm).\n\ne.g., DLE/MDG0030"
  defp alr_tooltip("opr"), do: "ICAO designator or name of the aircraft operating agency, if different from the aircraft identification in item 7."
  defp alr_tooltip("orgn"), do: "The originator's 8 letter AFTN address or other appropriate contact details, in cases where the originator of the flight plan may not be readily identified, as required by the appropriate ATS authority."
  defp alr_tooltip("per"), do: "Aircraft performance data, indicated by a single letter, as shown below:\n* Cat. A : < 169 km/h ( 91 kt) indicated airspeed ( IAS)\n* Cat. B : 169 km/h ( 91 kt) or more but < 224 km/h ( 121 kt)\n* Cat. C : 224 km/h ( 121 kt) or more but < 261 km/h ( 141 kt)\n* Cat. D : 261 km/h ( 141 kt) or more but < 307 km/h ( 166 kt)\n* Cat. E : 307 km/h ( 166 kt) or more but < 391 km/h ( 211 kt)\n* Cat. H : Specific procedures for Helicopters"
  defp alr_tooltip("altn_detail"), do: "Name of destination alternate aerodrome(s), if ZZZZ is inserted in Item 16. For aerodromes not listed in the relevant AIP, indicate location in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/."
  defp alr_tooltip("ralt"), do: "ICAO four letter indicator(s) for en-route alternate(s), as specified in Doc 7910, Location Indicators, or name(s) of en-route alternate aerodrome(s), if no indicator is allocated. For aerodromes not listed in the relevant AIP, indicate located in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/."
  defp alr_tooltip("talt"), do: "ICAO four letter indicator(s) for take-off alternate, as specified in Doc 7910, Location Indicators, or name of take-off alternate aerodrome, if no indicator is allocated. For aerodromes not listed in the relevant AIP, indicate located in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/."
  defp alr_tooltip("rif"), do: "The route details to the revised destination aerodrome, following by the ICAO four-letter location indicator of the aerodrome. The revised route is subject to reclearance in flight.\n\ne.g., RIF/DTA HEC KLAX or RIF/ESP G94 CLA YPPH"
  defp alr_tooltip("rmk"), do: "Other reasons for special handling by ATS. Any other plain language remarks when required by the appropriate ATS authority or deemed necessary."

  defp alr_tooltip("endurance"), do: "4 NUMERICS giving the fuel endurance in hours and minutes."
  defp alr_tooltip("pob"), do: "1, 2 or 3 NUMERICS giving the total number of persons on board, when so prescribed by the appropriate ATS authority."
  defp alr_tooltip("emergency_radio"), do: "Emergency Radio\n\nIndicate available emergency radio equipment: UHF 243.0 MHz, VHF 121.5 MHz, and/or ELT."
  defp alr_tooltip("radio_uhf"), do: "Emergency Radio UHF\n\nIf frequency 243.0 MHz ( UHF) is available."
  defp alr_tooltip("radio_vhf"), do: "Emergency Radio VHF\n\nIf frequency 121.5 MHz ( VHF) is available."
  defp alr_tooltip("radio_elt"), do: "If emergency location beacon-aircraft ( ELBA) is available."
  defp alr_tooltip("survival_equipment"), do: "Survival Equipment\n\nIndicate survival equipment carried: Polar, Desert, Maritime, and/or Jungle."
  defp alr_tooltip("survival_polar"), do: "If polar survival equipment is carried."
  defp alr_tooltip("survival_desert"), do: "If desert survival equipment is carried."
  defp alr_tooltip("survival_maritime"), do: "If maritime survival equipment is carried."
  defp alr_tooltip("survival_jungle"), do: "If jungle survival equipment is carried."
  defp alr_tooltip("jackets"), do: "Jackets\n\nIndicate life jacket equipment: lights, fluorescein, UHF, and/or VHF."
  defp alr_tooltip("jackets_light"), do: "If the life jackets are equipped with lights."
  defp alr_tooltip("jackets_fluores"), do: "If they are equipped with fluorscein."
  defp alr_tooltip("jackets_uhf"), do: "If any life jacket radio is equipped with UHF on frequency 243.0 MHz."
  defp alr_tooltip("jackets_vhf"), do: "If any life jacket radio is equipped with VHF on frequency 121.5 MHz."
  defp alr_tooltip("dinghy_number"), do: "2 NUMERICS giving the number of dinghies carried."
  defp alr_tooltip("dinghy_capacity"), do: "3 NUMERICS giving the total capacity, in persons carried, all of dinghies."
  defp alr_tooltip("dinghy_cover"), do: "Dinghy Cover\n\nIndicate if dinghies are covered."
  defp alr_tooltip("dinghy_colour"), do: "Dinghy Colour\n\nThe colour of the dinghies."
  defp alr_tooltip("aircraft_colour"), do: "The colour of the aircraft.\nSignificant markings ( this may include the aircraft registration)."
  defp alr_tooltip("remarks"), do: "Plain language indicating any other survival equipment carried and any other useful remarks."
  defp alr_tooltip("pilot"), do: "The name of the pilot-in-command."
  defp alr_tooltip("sar_info"), do: "Alerting Search and Rescue Information\n\nThis field consists of the following specified sequence of elements separated by spaces. Any information not available should be shown as 'NIL' or 'NOT KNOWN' and not simply omitted."
  defp alr_tooltip("filed_by"), do: "Filled by"
  defp alr_tooltip(_name), do: ""

  defp alr_datalists(lists) do
    [
      datalist("alr-aircraft-types", Map.get(lists, :aircraft_types, [])),
      datalist("alr-equipment", Map.get(lists, :equipment, [])),
      datalist("alr-surveillance", Map.get(lists, :surveillance, [])),
      datalist("alr-departures", Map.get(lists, :departures, [])),
      datalist("alr-destinations", Map.get(lists, :destinations, [])),
      datalist("alr-routes", Map.get(lists, :routes, [])),
      datalist("alr-registrations", Map.get(lists, :registrations, [])),
      datalist("alr-operators", Map.get(lists, :operators, [])),
      datalist("alr-sts", sts_options()),
      datalist("alr-pbn", Map.get(lists, :pbn, []) ++ pbn_options())
    ]
    |> Enum.join("")
  end

  defp alr_equipment_modals do
    """
    #{alr_equipment_modal("10a", "Equipment and Capabilities ( Item 10a)", alr_10a_groups(), [
      "Any alphanumeric characters not indicated above are reserved."
    ])}
    #{alr_equipment_modal("10b", "Surveillance Equipment and Capabilities ( Item 10b)", alr_10b_groups(), [
      "Enhanced surveillance capability is the ability of the aircraft to down-link aircraft derived data via a Mode S transponder.",
      "Alphanumeric characters not indicated above are reserved.",
      "Example: ADE3RV/HB2U2V2G1",
      "Additional surveillance application should be listed in Item 18 following the indicator SUR/."
    ])}
    #{alr_equipment_modal("sts", "STS/ Indicator Value", alr_sts_groups(), [])}
    #{alr_equipment_modal("pbn", "PBN/ Indicator Value", alr_pbn_groups(), [
      "Please insert maximum 8 entries, i.e. a total of not more than 16 characters."
    ])}
    """
  end

  defp alr_equipment_modal(kind, title, groups, notes) do
    """
    <div class="equipment-modal" data-equipment-modal="#{html(kind)}" role="dialog" aria-modal="true">
      <div class="equipment-card">
        <div class="equipment-head">
          <strong>#{html(title)}</strong>
          <button type="button" class="equipment-x js-equipment-close" aria-label="Close"><i class="bi bi-x-lg"></i></button>
        </div>
        <div class="equipment-body">
          #{Enum.map_join(groups, "", &alr_equipment_group/1)}
          <div class="equipment-notes">#{Enum.map_join(notes, "", fn note -> "<p>#{html(note)}</p>" end)}</div>
        </div>
        <div class="equipment-actions">
          <button type="button" class="aftn-tool primary js-equipment-add"><i class="bi bi-plus-lg"></i><span>Add</span></button>
          <button type="button" class="aftn-tool discard js-equipment-clear"><i class="bi bi-eraser"></i><span>Clear</span></button>
          <button type="button" class="aftn-tool close js-equipment-close"><i class="bi bi-x-lg"></i><span>Close</span></button>
        </div>
      </div>
    </div>
    """
  end

  defp alr_equipment_group({heading, rows}) do
    """
    <div class="equipment-group">
      <div class="equipment-group-title">#{html(heading)}</div>
      <div class="equipment-list">#{Enum.map_join(rows, "", &alr_equipment_row/1)}</div>
    </div>
    """
  end

  defp alr_equipment_row({code, description}) do
    """
    <label class="equipment-row" title="#{html("#{code}: #{description}")}">
      <input class="equipment-choice" type="checkbox" value="#{html(code)}">
      <span class="equipment-code">#{html(code)}</span>
      <span class="equipment-desc">#{html(description)}</span>
    </label>
    """
  end

  defp alr_10a_groups do
    [
      {"INSERT one letter as follows", [
        {"N", "No COM/NAV/approach aid equipment for the route to be flown is carried, or the equipment is unserviceable"},
        {"S", "Standard COM/NAV/approach aid equipment for the route to be flown is carried and serviceable"}
      ]},
      {"AND/OR INSERT one or more serviceable COM/NAV/approach aid equipment and capabilities", [
        {"A", "GBAS landing system"},
        {"B", "LPV ( APV with SBAS )"},
        {"C", "LORAN C"},
        {"D", "DME"},
        {"E1", "FMC WPR ACARS"},
        {"E2", "D-FIS ACARS"},
        {"E3", "PDC ACARS"},
        {"F", "ADF"},
        {"G", "GNSS"},
        {"H", "HF RTF"},
        {"I", "Inertial Navigation"},
        {"J1", "CPDLC ATN VDL Mode 2"},
        {"J2", "CPDLC FANS 1/A HFDL"},
        {"J3", "CPDLC FANS 1/A VDL Mode A"},
        {"J4", "CPDLC FANS 1/A VDL Mode 2"},
        {"J5", "CPDLC FANS 1/A SATCOM ( INMARSAT )"},
        {"J6", "CPDLC FANS 1/A SATCOM ( MTSAT )"},
        {"J7", "CPDLC FANS 1/A SATCOM ( Iridium )"},
        {"K", "MLS"},
        {"L", "ILS"},
        {"M1", "ATC RTF SATCOM ( INMARSAT )"},
        {"M2", "ATC RTF ( MTSAT )"},
        {"M3", "ATC RTF ( Iridium )"},
        {"O", "VOR"},
        {"P1", "Reserved for RCP"},
        {"P2", "Reserved for RCP"},
        {"P3", "Reserved for RCP"},
        {"P4", "Reserved for RCP"},
        {"P5", "Reserved for RCP"},
        {"P6", "Reserved for RCP"},
        {"P7", "Reserved for RCP"},
        {"P8", "Reserved for RCP"},
        {"P9", "Reserved for RCP"},
        {"R", "PBN approved"},
        {"T", "TACAN"},
        {"U", "UHF RTF"},
        {"V", "VHF RTF"},
        {"W", "RVSM approved"},
        {"X", "MNPS approved"},
        {"Y", "VHF with 8.33 kHz channel spacing capability"},
        {"Z", "Other equipment carried or other capabilities"}
      ]}
    ]
  end

  defp alr_10b_groups do
    [
      {"INSERT", [
        {"N", "No surveillance equipment for the route to be flown is carried, or the equipment is unserviceable"}
      ]},
      {"SSR Modes A and C", [
        {"A", "Transponder - Mode A ( 4 digits - 4096 codes )"},
        {"C", "Transponder - Mode A ( 4 digits - 4096 codes) and Mode C"}
      ]},
      {"SSR Mode S", [
        {"E", "Transponder - Mode S, including aircraft identification, pressure-altitude and extended squitter ( ADS-B) capability"},
        {"H", "Transponder - Mode S, including aircraft identification, pressure-altitude and enhanced surveillance capability"},
        {"I", "Transponder - Mode S, including aircraft identification, but no pressure-altitude capability"},
        {"L", "Transponder - Mode S, including aircraft identification, pressure-altitude, extended squitter ( ADS-B) and enhanced surveillance capability"},
        {"P", "Transponder - Mode S, including pressure-altitude, but no aircraft identification capability"},
        {"S", "Transponder - Mode S, including both pressure-altitude and aircraft identification capability"},
        {"X", "Transponder - Mode S, with neither aircraft identification nor pressure-altitude capability"}
      ]},
      {"ADS-B", [
        {"B1", "ADS-B with dedicated 1090 MHz ADS-B 'out' capability"},
        {"B2", "ADS-B with dedicated 1090 MHz ADS-B 'out' and 'in' capability"},
        {"U1", "ADS-B 'out' capability using UAT"},
        {"U2", "ADS-B 'out' and 'in' capability using UAT"},
        {"V1", "ADS-B 'out' capability using VDL Mode 4"},
        {"V2", "ADS-B 'out' and 'in' capability using VDL Mode 4"}
      ]},
      {"ADS-C", [
        {"D1", "ADS-C with FANS 1/A capabilities"},
        {"G1", "ADS-C with ATN capabilities"}
      ]}
    ]
  end

  defp alr_sts_groups do
    [
      {"INSERT one or more special handling indicators", [
        {"ALTRV", "For a flight operated in accordance with an altitude reservation"},
        {"ATFMX", "For a flight approved for exemption from ATFM measures by the appropriate ATS authority"},
        {"FFR", "Fire-fighting"},
        {"FLTCK", "Flight check for calibration of navaids"},
        {"HAZMAT", "For a flight carrying hazardous material"},
        {"HEAD", "A flight with Head of State status"},
        {"HOSP", "For medical flight declared by medical authorities"},
        {"HUM", "For a flight operating on a humanitarian mission"},
        {"MARSA", "For a flight for which a military entity assumes responsibility for separation of military aircraft"},
        {"MEDEVAC", "For a life critical medical emergency evacuation"},
        {"NONRVSM", "For non-RVSM capable flight intending to operate in RVSM airspace"},
        {"SAR", "For a flight engaged in a search and rescue mission"},
        {"STATE", "For a flight engaged in military, customs or police services."}
      ]}
    ]
  end

  defp alr_pbn_groups do
    [
      {"RNAV SPECIFICATIONS", [
        {"A1", "RNAV 10 ( RNP 10 )"},
        {"B1", "RNAV 5 all permitted sensors"},
        {"B2", "RNAV 5 GNSS"},
        {"B3", "RNAV 5 DME/DME"},
        {"B4", "RNAV 5 VOR/DME"},
        {"B5", "RNAV 5 INS or IRS"},
        {"B6", "RNAV 5 LORANC"},
        {"C1", "RNAV 2 all permitted sensors"},
        {"C2", "RNAV 2 GNSS"},
        {"C3", "RNAV 2 DME/DME"},
        {"C4", "RNAV 2 DME/DME/IRU"},
        {"D1", "RNAV 1 all permitted sensors"},
        {"D2", "RNAV 1 GNSS"},
        {"D3", "RNAV 1 DME/DME"},
        {"D4", "RNAV 1 DME/DME/IRU"}
      ]},
      {"RNP SPECIFICATIONS", [
        {"L1", "RNP 4"},
        {"O1", "Basic RNP 1 all permitted sensors"},
        {"O2", "Basic RNP 1 GNSS"},
        {"O3", "Basic RNP 1 DME/DME"},
        {"O4", "Basic RNP 1 DME/DME/IRU"},
        {"S1", "RNP APCH"},
        {"S2", "RNP APCH with BARO-VNAV"},
        {"T1", "RNP AR APCH with RF ( special authorization required )"},
        {"T2", "RNP AR APCH without RF ( special authorization required)"}
      ]}
    ]
  end

  defp free_message_form do
    """
    <section id="FREE" class="amo-window">
      <div class="amo-title"><i class="bi bi-card-text"></i><span>FREE Text Message</span></div>
      <form id="free-message-form" class="amo-form" method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="FREE">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="FREE">
        <div class="amo-toolbar">
          <button class="amo-tool primary" type="submit" name="compose_action" value="send_clear"><i class="bi bi-send-plus-fill"></i><span>Send+Clear</span></button>
          <button class="amo-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-fill"></i><span>Send</span></button>
          <button class="amo-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save"></i><span>Save</span></button>
          <button class="amo-tool discard js-clear-form" type="button"><i class="bi bi-eraser"></i><span>Clear</span></button>
          <a class="amo-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <input type="hidden" name="cid_seq" value="#{html(current_cid_seq())}" data-current-cid-seq>
        <div class="amo-body">
          <div class="grid2">
            <div class="field">
              <label>Filing Time</label>
              <div class="time-control">
                <input id="free-filing-time" name="filing_time" value="#{current_filing_time()}">
                <button class="time-button js-current-time" type="button" data-target="free-filing-time" title="Use current DDHHMM" aria-label="Use current DDHHMM"><i class="bi bi-clock-history"></i></button>
              </div>
            </div>
          </div>
          <div class="amo-editor-head">
            <label for="free-message">FREE Text</label>
            <span>Plain free text message</span>
          </div>
          <textarea id="free-message" class="aftn-textarea" name="message" spellcheck="false" placeholder="Type FREE text here..."></textarea>
          <div class="amo-footer">
            <div class="amo-filled">
              <label for="free-filed-by">Filled By</label>
              <input id="free-filed-by" name="filed_by" value="SYSTEM" placeholder="Operator">
            </div>
          </div>
        </div>
      </form>
    </section>
    """
  end

  defp amo_form do
    """
    <section id="AMO" class="amo-window">
      <div class="amo-title"><i class="bi bi-pencil-square"></i><span>AMO Message</span></div>
      <form id="amo-form" class="amo-form" method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="FREE">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="AMO">
        <div class="amo-toolbar">
          <button class="amo-tool primary" type="submit" name="compose_action" value="send_clear"><i class="bi bi-send-plus-fill"></i><span>Send+Clear</span></button>
          <button class="amo-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-fill"></i><span>Send</span></button>
          <button class="amo-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save"></i><span>Save</span></button>
          <button class="amo-tool discard js-clear-form" type="button"><i class="bi bi-eraser"></i><span>Clear</span></button>
          <a class="amo-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <div class="amo-body">
          <div class="amo-editor-head">
            <label for="amo-message">AMO Text</label>
            <span>Free text message</span>
          </div>
          <textarea id="amo-message" class="aftn-textarea" name="message" spellcheck="false" placeholder="Type AMO message here..."></textarea>
          <div class="amo-footer">
            <div class="amo-filled">
              <label for="amo-filed-by">Filled By</label>
              <input id="amo-filed-by" name="filed_by" value="SYSTEM" placeholder="Operator">
            </div>
          </div>
        </div>
      </form>
    </section>
    """
  end

  defp status_panel(_events, settings) do
    settings = settings || Tp.Settings.get_or_default()
    link = Tp.LinkMonitor.status()
    link_up? = link.state == :up
    link_card = if link_up?, do: "ok", else: "warn"
    link_state = if link_up?, do: "Established", else: "Down"
    check_text = if link_up?, do: "established", else: ( link.reason || "down")
    link_pill = if link_up?, do: "up", else: "down"
    queue_count = safe_queue_count()
    queue_card = if is_integer(queue_count) and queue_count > 0, do: "warn", else: "ok"

    """
    <footer class="status-footer">
      <div class="status-panel">
        <div class="status-summary">
          <div class="status-card #{link_card}">
            <div class="status-label">Link</div>
            <div class="status-value"><span id="status-link-pill" class="status-pill #{link_pill}"><span class="status-dot"></span><span id="status-link-value">#{html(link_state)}</span></span></div>
          </div>
           <div class="status-card #{link_card}">
            <div class="status-label">Check</div>
            <div id="status-check-value" class="status-value">#{html(check_text)}</div>
          </div>
          <div class="status-card info">
            <div class="status-label">UDP</div>
            <div class="status-value">UDP</div>
          </div>
          <div class="status-card info">
            <div class="status-label">Tseq</div>
            <div id="status-tseq-value" class="status-value">#{html(settings.tseq)}</div>
          </div>
          <div id="status-queue-card" class="status-card #{queue_card}">
            <div class="status-label">Queue</div>
            <div id="status-queue-count" class="status-value">#{html(queue_count)}</div>
          </div>
          <div class="status-card">
            <div class="status-label">CID</div>
            <div id="status-cid-value" class="status-value">#{html(settings.cid)}</div>
          </div>
          <div class="status-card">
            <div class="status-label">Digit Seq</div>
            <div id="status-digit-seq-value" class="status-value">#{html(settings.digit_seq)}</div>
          </div>
          <div class="status-card">
            <div class="status-label">Free</div>
            <div class="status-value">43G</div>
          </div>
          <div class="status-card info wide">
            <div class="status-label">Local</div>
            <div id="status-local-value" class="status-value">0.0.0.0:#{html(local_udp_port())}</div>
          </div>
          <div class="status-card info wide">
            <div class="status-label">Destination</div>
            <div id="status-destination-value" class="status-value">#{html(settings.destination_ip_address)}:#{html(settings.port)}</div>
          </div>

          <div class="status-actions"><a href="/settings"> <i class="bi bi-gear" style="margin-right:5px"></i> Setting</a></div>
        </div>
      </div>
    </footer>
    """
  end

  defp safe_queue_count do
    Tp.Aftn.queue_count()
  rescue
    _error -> "-"
  end

  defp udp_receive_monitor(messages, _events) do
    rows =
      messages
      |> Enum.sort_by(fn item -> time_sort_key(item.time) end, :desc)
      |> Enum.take(10)

    body =
      if rows == [] do
        ~s(<div class="udp-empty">No Data.</div>)
      else
        Enum.map_join(rows, "", &udp_monitor_row/1)
      end

    """
    <section class="udp-monitor">
      <div class="section-head">
        <h2>Information Display</h2>
        <button id="clear-udp-monitor" class="icon-button" type="button" title="Clear information display" aria-label="Clear information display">#{refresh_icon()}</button>
      </div>
      <div id="udp-monitor-body" class="udp-monitor-body">#{body}</div>
    </section>
    """
  end

  defp udp_monitor_row(item) do
    """
    <div class="udp-item">
      <div class="udp-meta">
        <span>#{html(format_time(item.time))}</span>
        <span>#{html(item.kind)}</span>
        <span class="udp-source"><i class="bi bi-envelope-arrow-down-fill" title="Message in" aria-hidden="true"></i>#{html(udp_source(item))}</span>
      </div>
      <pre class="udp-raw">#{html(visible_monitor_udp(item.raw))}</pre>
    </div>
    """
  end

  defp udp_source(%{source_ip: nil}), do: ""
  defp udp_source(%{source_ip: ip, source_port: nil}), do: ip
  defp udp_source(%{source_ip: ip, source_port: port}), do: "#{ip}:#{port}"
  defp udp_source(%{parsed_fields: fields}) when is_map(fields) do
    ip = Map.get(fields, "source_ip") || Map.get(fields, :source_ip)
    port = Map.get(fields, "source_port") || Map.get(fields, :source_port)
    udp_source(%{source_ip: ip, source_port: port})
  end
  defp udp_source(_item), do: ""

  defp quick_aftn_free_form do
    """
    <section>
      <h2>AFTN Free Text Test</h2>
      <form method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="AFTN_FREE">
        #{target_fields()}
        <input type="hidden" name="transmission_id" value="#{html(current_cid_seq())}" data-current-cid-seq>
        <div class="grid2">
          #{input("filing_time", "Filing Time", current_filing_time())}
          #{input("priority", "Priority", "FF")}
          #{input("originator", "Originator", "WIIIYFYX")}
        </div>
        #{textarea("addresses", "Destination", "WAJJYFYA WIIIYOYA WAAAJXJX WIOOYOYX")}
        #{textarea("message", "Free Text", "SVC MIS CH 0858 LR RCA0049")}
        <p class="hint">Format: SOH + TX ID/time, priority + destination, filing/originator, STX + isi berita, VT + ETX.</p>
        <p><button type="submit">Queue Send</button></p>
      </form>
    </section>
    """
  end

  defp raw_udp_form do
    """
    <section style="margin-top:16px">
      <h2>Raw UDP</h2>
      <form method="post" action="/messages/send">
        #{target_fields()}
        <textarea name="message" spellcheck="false"></textarea>
        <p><button type="submit">Queue Send</button></p>
      </form>
    </section>
    """
  end

  defp quick_fpl_form do
    """
    <section style="margin-top:16px">
      <h2>Compose FPL</h2>
      <form method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="FPL">
        #{target_fields()}
        <div class="grid2">
          #{input("aircraft_id", "Aircraft", "GIA120")}
          #{input("aircraft_type", "Type/WTC", "A320")}
          #{input("equipment", "Equipment", "SACD/AB1")}
          #{input("departure", "Departure", "WIII")}
          #{input("departure_time", "Time", "0330")}
          #{input("destination", "Destination", "WAAA")}
          #{input("speed_level", "Speed/Level", "N0250F230")}
          #{input("eet", "EET", "0200")}
        </div>
        <div class="field"><label>Route</label><input name="route" value="CKG W11 TKG W19 BKL"></div>
        <div class="field"><label>Item 18</label><input name="item18" value="DOF/#{today_dof()}"></div>
        <p><button type="submit">Queue Send</button></p>
      </form>
    </section>
    """
  end

  defp quick_dla_form do
    """
    <section style="margin-top:16px">
      <h2>Compose DLA</h2>
      <form method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="DLA">
        #{target_fields()}
        <div class="grid2">
          #{input("aircraft_id", "Aircraft", "GIA1205")}
          #{input("departure", "Departure", "WIII")}
          #{input("departure_time", "Time", "1000")}
          #{input("destination", "Destination", "WAAA")}
        </div>
        <div class="field"><label>DOF</label><input name="dof" value="#{today_dof()}"></div>
        <p><button type="submit">Queue Send</button></p>
      </form>
    </section>
    """
  end

  defp message_rows([]) do
    """
    <tr><td class="empty-row" colspan="9">Belum ada pesan yang cocok dengan filter.</td></tr>
    """
  end

  defp message_rows(messages) do
    Enum.map_join(messages, "", &message_row/1)
  end

  defp queue_message_rows([]) do
    """
    <tr><td class="empty-row" colspan="9">Tidak ada queue / not delivered message.</td></tr>
    """
  end

  defp queue_message_rows(messages) do
    Enum.map_join(messages, "", &queue_message_row/1)
  end

  defp queue_message_row(message) do
    {preview, _has_more?} = message.raw_text |> queue_message_text() |> message_preview()
    id = html(message.id)

    """
    <tr data-message-id="#{id}" data-compose-priority="#{html(message.priority)}" data-compose-originator="#{html(message.originator)}" data-compose-addresses="#{html(compose_addresses(message))}" data-compose-message="#{html(compose_message_text(message.raw_text))}">
      <td>#{html(queue_message_type(message))}</td>
      <td>#{html(message.filing_time)}</td>
      <td>#{html(format_queue_send_at(message))}</td>
      <td>#{html(message.originator)}</td>
      <td>#{html(message.aircraft_id)}</td>
      <td><code class="message-preview">#{html(preview)}</code></td>
      <td class="#{html(queue_status_class(message.status))}">#{html(queue_status(message))}</td>
      <td>#{html(format_time(message.next_attempt_at || message.inserted_at))}</td>
      <td class="table-actions">#{action_menu(id, delete_path: "/queue/#{id}/delete", return_to: "/queue")}</td>
    </tr>
    """
  end

  defp queue_delete_all_form(q) do
    """
    <form class="inline-form" method="post" action="/queue/delete-all" onsubmit="return confirm('Hapus semua queue / not delivered sesuai pencarian saat ini?')">
      <input type="hidden" name="q" value="#{html(q)}">
      <button class="btn-small btn-outline" type="submit">#{trash_icon()}<span>Delete All</span></button>
    </form>
    """
  end

  defp queue_pagination_controls(q, pagination) do
    page = Map.get(pagination, :page, 1)
    total_pages = Map.get(pagination, :total_pages, 1)

    if total_pages <= 1 do
      ~s(<span class="queue-page-info">Page #{html(page)} of #{html(total_pages)}</span>)
    else
      first_link = if page > 1, do: queue_page_link(q, 1, "&laquo;"), else: ~s(<span class="disabled">&laquo;</span>)
      prev_link = if page > 1, do: queue_page_link(q, page - 1, "&lsaquo;"), else: ~s(<span class="disabled">&lsaquo;</span>)
      next_link = if page < total_pages, do: queue_page_link(q, page + 1, "&rsaquo;"), else: ~s(<span class="disabled">&rsaquo;</span>)
      last_link = if page < total_pages, do: queue_page_link(q, total_pages, "&raquo;"), else: ~s(<span class="disabled">&raquo;</span>)

      """
      <div class="queue-pagination">
        #{first_link}
        #{prev_link}
        #{queue_page_number_links(q, page, total_pages)}
        #{next_link}
        #{last_link}
        <span class="queue-page-info">Page #{html(page)} of #{html(total_pages)}</span>
      </div>
      """
    end
  end

  defp queue_page_link(q, page, label) do
    params = if q in [nil, ""], do: %{page: page}, else: %{q: q, page: page}
    ~s(<a href="/queue?#{html(URI.encode_query(params))}">#{label}</a>)
  end

  defp queue_page_number_links(q, page, total_pages) do
    1..total_pages
    |> Enum.filter(fn number -> number == 1 or number == total_pages or abs(number - page) <= 2 end)
    |> Enum.reduce({[], nil}, fn number, {items, previous} ->
      gap = if previous && number - previous > 1, do: [~s(<span class="disabled">...</span>)], else: []

      item =
        if number == page do
          ~s(<span class="active">#{html(number)}</span>)
        else
          queue_page_link(q, number, number)
        end

      {items ++ gap ++ [item], number}
    end)
    |> elem(0)
    |> Enum.join("")
  end

  defp queue_message_type(%{message_type: type}) when type in [nil, "", "FREE"], do: "FREE TEXT"
  defp queue_message_type(%{message_type: type}), do: type

  defp queue_status(%{status: "retrying"}), do: "waiting"
  defp queue_status(%{status: "queued"}), do: "waiting"
  defp queue_status(%{status: status}), do: status || "waiting"

  defp queue_status_class("retrying"), do: "status-retrying"
  defp queue_status_class(_status), do: "status-waiting"

  defp format_queue_send_at(%{next_attempt_at: nil, filing_time: filing_time}), do: filing_time
  defp format_queue_send_at(%{next_attempt_at: next_attempt_at}), do: format_ddhhmm(next_attempt_at)

  defp queue_message_text(nil), do: ""

  defp queue_message_text(raw_text) do
    text = to_string(raw_text)

    case String.split(text, <<2>>, parts: 2) do
      [_header, rest] ->
        rest
        |> String.split(<<3>>, parts: 2)
        |> List.first()
        |> String.replace(<<11>>, "")
        |> String.trim()

      _ ->
        text
    end
  end

  defp compose_message_text(nil), do: ""

  defp compose_message_text(raw_text) do
    raw_text
    |> queue_message_text()
    |> visible_monitor_udp()
    |> String.trim()
  end

  defp compose_addresses(%{destinations: destinations}) when is_list(destinations) do
    destinations
    |> Enum.reject(&is_nil/1)
    |> Enum.map_join(" ", &to_string/1)
  end

  defp compose_addresses(_message), do: ""

  defp message_detail_templates(messages) do
    messages
    |> Enum.map_join("", &message_detail_template/1)
    |> then(fn body -> ~s(<div id="message-detail-templates" style="display:none">#{body}</div>) end)
  end

  defp message_detail_template(message) do
    id = html(message.id)

    """
    <div id="message-detail-#{id}" class="js-message-detail-template">
      <dl class="modal-grid">
        #{detail_item("Waktu", format_time(message.inserted_at))}
        #{detail_item("IO", message.direction)}
        #{detail_item("CID", message.cid)}
        #{detail_item("Priority", message.priority)}
        #{detail_item("Type", message.message_type)}
        #{detail_item("Originator", message.originator)}
        #{detail_item("Aircraft", message.aircraft_id)}
        #{detail_item("Departure", message.departure)}
        #{detail_item("Destination", message.destination)}
        #{detail_item("Route", message.route)}
        #{detail_item("Sequence", message.sequence_no)}
        #{detail_item("Status", message.status)}
        #{detail_item("Note", Map.get(message, :note))}
        #{detail_item("Filed By", message.filed_by)}
      </dl>
      <pre class="modal-raw">#{html(visible_aftn(message.raw_text))}</pre>
    </div>
    """
  end

  defp message_row(message) do
    {preview, has_more?} = message_preview(message.raw_text)
    id = html(message.id)

    read_more =
      if has_more? do
        ~s|<button class="read-more js-show-message" type="button" data-message-id="#{id}" onclick="return showMessagePopupFromButton(this)">More</button>|
      else
        ""
      end

    """
    <tr data-message-id="#{id}" data-compose-priority="#{html(message.priority)}" data-compose-originator="#{html(message.originator)}" data-compose-addresses="#{html(compose_addresses(message))}" data-compose-message="#{html(compose_message_text(message.raw_text))}">
      <td class="muted">#{html(format_time(message.inserted_at))}</td>
      <td>#{html(message.direction)}</td>
      <td>#{html(message.cid)}</td>
      <td>#{html(message.sequence_no)}</td>
      <td class="message-cell"><code class="message-preview">#{html(preview)}</code>#{read_more}</td>
      <td>#{html(message.status)}</td>
      <td>#{html(Map.get(message, :note))}</td>
      <td>#{html(message.filed_by)}</td>
      <td class="table-actions">#{action_menu(id)}</td>
    </tr>
    """
  end

  defp action_menu(id, opts \\ []) do
    delete_path = Keyword.get(opts, :delete_path, "/messages/#{id}/delete")
    return_to = Keyword.get(opts, :return_to, "/")

    """
    <div class="action-menu">
      <button class="btn-small icon-action js-action-toggle" type="button" title="Action" aria-label="Action menu" onclick="return toggleActionMenuFromButton(this,event)">#{kebab_icon()}</button>
      <div class="action-popup">
        <button class="action-item js-show-message" type="button" data-message-id="#{id}" onclick="return showMessagePopupFromButton(this)">#{eye_icon()}<span>View</span></button>
        <a class="action-item" href="/messages/#{id}/pdf" target="_blank">#{pdf_icon()}<span>PDF</span></a>
        <form class="inline-form" method="post" action="#{delete_path}" onsubmit="return confirm('Hapus message ini?')">
          <input class="js-return-to" type="hidden" name="return_to" value="#{html(return_to)}">
          <button class="action-item danger" type="submit">#{trash_icon()}<span>Delete</span></button>
        </form>
      </div>
    </div>
    """
  end

  defp filter_form(filters) do
    direction = Keyword.get(filters, :direction, "")
    type = Keyword.get(filters, :type, "")
    cid = Keyword.get(filters, :cid, "")
    seq_from = Keyword.get(filters, :seq_from, "")
    seq_to = Keyword.get(filters, :seq_to, "")
    text = Keyword.get(filters, :text, "")
    date_from = Keyword.get(filters, :date_from, "")
    date_to = Keyword.get(filters, :date_to, "")
    filed_by = Keyword.get(filters, :filed_by, "")
    page_size = Keyword.get(filters, :page_size, "15")

    """
    <form class="filters" method="get" action="/">
      <input type="hidden" name="page" value="1">
      <input type="hidden" name="page_size" value="#{html(page_size)}">
      <div class="field filter-cid"><label class="search-label">CID</label><input name="cid" value="#{html(cid)}" placeholder="CID" maxlength="3" autocomplete="off" oninput="this.value=this.value.toUpperCase().replace(/[^A-Z0-9]/g,'').slice(0,3)"></div>
      <div class="field filter-seq"><label class="search-label">Seq From</label><input name="seq_from" value="#{html(seq_from)}" placeholder="From" inputmode="numeric" maxlength="4" autocomplete="off" oninput="this.value=this.value.replace(/\D/g,'').slice(0,4)"></div>
      <div class="field filter-seq"><label class="search-label">Seq To</label><input name="seq_to" value="#{html(seq_to)}" placeholder="To" inputmode="numeric" maxlength="4" autocomplete="off" oninput="this.value=this.value.replace(/\D/g,'').slice(0,4)"></div>
      <div class="field filter-text"><label class="search-label">Text</label><input name="text" value="#{html(text)}" placeholder="Search message text"></div>
       <div class="field filter-filed"><label class="search-label">Filled By</label><input name="filed_by" value="#{html(filed_by)}" placeholder="Operator"></div>
      <div class="field filter-select"><label class="search-label">IO</label><select name="direction">
        #{option("", "All IO", direction)}
        #{option("inbound", "Inbound", direction)}
        #{option("outbound", "Outbound", direction)}
      </select></div>
      <div class="field filter-select"><label class="search-label">Type</label><select name="type">
        #{option("", "All Type", type)}
        #{option("FREE", "FREE", type)}
        #{option("FPL", "FPL", type)}
        #{option("DLA", "DLA", type)}
        #{option("CHG", "CHG", type)}
        #{option("CNL", "CNL", type)}
        #{option("DEP", "DEP", type)}
        #{option("ARR", "ARR", type)}
      </select></div>
       <div class="field filter-date"><label class="search-label">Date From</label><input type="date" name="date_from" value="#{html(date_from)}"></div>
      <div class="field filter-date"><label class="search-label">Date To</label><input type="date" name="date_to" value="#{html(date_to)}"></div>

      <button type="submit"><i class="bi bi-funnel-fill"></i><span>Filter</span></button>
      <a class="clear-filter" href="/?page_size=#{html(page_size)}"><i class="bi bi-eraser"></i><span>Clear</span></a>
    </form>
    """
  end

  defp pagination_controls(filters, pagination) do
    page = Map.get(pagination, :page, 1)
    page_size = Map.get(pagination, :page_size, 15)
    has_next = Map.get(pagination, :has_next, false)
    prev_url = Map.get(pagination, :prev_url)
    next_url = Map.get(pagination, :next_url)
    total_count = Map.get(pagination, :total_count)
    total_pages = Map.get(pagination, :total_pages)
    page_urls = Map.get(pagination, :page_urls, [])
    effective_next_url = if has_next, do: next_url, else: nil
    label = pagination_label(page, page_size, total_count, total_pages)

    """
    <div class="table-footer">
      <div class="muted">#{label}</div>
      <div class="pagination">
        #{pagination_link(prev_url, "Prev")}
        #{page_number_links(page_urls, page)}
        #{pagination_link(effective_next_url, "Next")}
      </div>
      <div class="footer-actions">
        #{delete_all_form(filters)}
      </div>
      <form class="page-size" method="get" action="/">
        #{hidden_filter_inputs(filters)}
        <input type="hidden" name="page" value="1">
        <label>Rows</label>
        <select name="page_size" onchange="this.form.submit()">
          #{option("15", "15", to_string(page_size))}
          #{option("25", "25", to_string(page_size))}
          #{option("50", "50", to_string(page_size))}
          #{option("100", "100", to_string(page_size))}
          #{option("500", "500", to_string(page_size))}
          #{option("1000", "1000", to_string(page_size))}
        </select>
      </form>
    </div>
    """
  end

  defp delete_all_form(filters) do
    """
    <form class="inline-form" method="post" action="/messages/delete-all" onsubmit="return confirm('Hapus semua message sesuai filter tabel saat ini?')">
      #{hidden_filter_inputs(filters)}
      <input type="hidden" name="page_size" value="#{html(Keyword.get(filters, :page_size, "15"))}">
      <button class="btn-small btn-outline" type="submit">#{trash_icon()}<span>Delete All</span></button>
    </form>
    """
  end

  defp pagination_link(nil, label), do: ~s(<span class="disabled">#{html(label)}</span>)
  defp pagination_link("", label), do: pagination_link(nil, label)
  defp pagination_link(url, label), do: ~s(<a href="#{html(url)}">#{html(label)}</a>)

  defp pagination_label(page, page_size, nil, _total_pages), do: "Server-side cursor pagination - Page #{html(page)} - Rows #{html(page_size)}"
  defp pagination_label(page, page_size, total_count, total_pages), do: "Page #{html(page)} / #{html(total_pages)} - Rows #{html(page_size)} - Total #{html(total_count)}"

  defp page_number_links([], page), do: ~s(<span class="active">#{html(page)}</span>)

  defp page_number_links(page_urls, page) do
    total_pages = length(page_urls)

    page_urls
    |> Enum.filter(fn {number, _url} -> number == 1 or number == total_pages or abs(number - page) <= 2 end)
    |> Enum.reduce({[], nil}, fn {number, url}, {items, previous} ->
      gap = if previous && number - previous > 1, do: [~s(<span class="disabled">...</span>)], else: []
      item = if number == page, do: ~s(<span class="active">#{html(number)}</span>), else: pagination_link(url, number)
      {items ++ gap ++ [item], number}
    end)
    |> elem(0)
    |> Enum.join("")
  end

  defp hidden_filter_inputs(filters) do
    filters
    |> Enum.reject(fn {key, value} -> key in [:page, :page_size, :after_id, :after_ts, :history] or is_nil(value) or value == "" end)
    |> Enum.map_join("", fn {key, value} ->
      ~s(<input type="hidden" name="#{html(key)}" value="#{html(value)}">)
    end)
  end

  defp alr_reference_lists do
    aircraft_rows =
      db_rows("""
      SELECT DISTINCT type9b, type9c
      FROM aircraft_wtc
      WHERE type9b IS NOT NULL AND type9b <> ''
      ORDER BY type9b
      LIMIT 500
      """)
      |> fallback_rows("""
      SELECT DISTINCT aircraft_type, wake_turbulence_category
      FROM aircraft_wtc
      WHERE aircraft_type IS NOT NULL AND aircraft_type <> ''
      ORDER BY aircraft_type
      LIMIT 500
      """)

    route_rows =
      db_rows("""
      SELECT type13a, type16a, type15c
      FROM route
      WHERE type15c IS NOT NULL AND type15c <> ''
      ORDER BY id_number DESC
      LIMIT 300
      """)
      |> fallback_rows("""
      SELECT JSON_UNQUOTE(JSON_EXTRACT(raw_data, '$.type13a')),
             JSON_UNQUOTE(JSON_EXTRACT(raw_data, '$.type16a')),
             description
      FROM route
      WHERE description IS NOT NULL AND description <> ''
      ORDER BY id DESC
      LIMIT 300
      """)

    reg_rows =
      db_rows("""
      SELECT type18, type10a, type10b, type18Opr, type18Pbn
      FROM aircraft_reg
      ORDER BY id DESC
      LIMIT 300
      """)
      |> fallback_rows("""
      SELECT registration, JSON_UNQUOTE(JSON_EXTRACT(raw_data, '$.type10a')),
             JSON_UNQUOTE(JSON_EXTRACT(raw_data, '$.type10b')), operator,
             JSON_UNQUOTE(JSON_EXTRACT(raw_data, '$.type18Pbn'))
      FROM aircraft_reg
      ORDER BY id DESC
      LIMIT 300
      """)

    %{
      aircraft_types: aircraft_rows |> Enum.map(&row_at(&1, 0)) |> uniq_present(),
      wakes: aircraft_rows |> Enum.map(&row_at(&1, 1)) |> uniq_present(),
      departures: route_rows |> Enum.map(&row_at(&1, 0)) |> uniq_present(),
      destinations: route_rows |> Enum.map(&row_at(&1, 1)) |> uniq_present(),
      routes: route_rows |> Enum.map(&row_at(&1, 2)) |> uniq_present(),
      registrations: reg_rows |> Enum.map(&row_at(&1, 0)) |> uniq_present(),
      equipment: reg_rows |> Enum.map(&row_at(&1, 1)) |> uniq_present(),
      surveillance: reg_rows |> Enum.map(&row_at(&1, 2)) |> uniq_present(),
      operators: reg_rows |> Enum.map(&row_at(&1, 3)) |> uniq_present(),
      pbn: reg_rows |> Enum.map(&row_at(&1, 4)) |> uniq_present()
    }
  end

  defp fallback_rows([], sql), do: db_rows(sql)
  defp fallback_rows(rows, _sql), do: rows

  defp db_rows(sql) do
    case Tp.Repo.query(sql, []) do
      {:ok, %{rows: rows}} -> rows
      _ -> []
    end
  rescue
    _error -> []
  end

  defp row_at(row, index) when is_list(row), do: Enum.at(row, index)
  defp row_at(_row, _index), do: nil

  defp uniq_present(values) do
    values
    |> Enum.map(&to_string/1)
    |> Enum.map(&String.trim/1)
    |> Enum.reject(&(&1 in ["", "NULL"]))
    |> Enum.uniq()
  end

  defp wake_options(lists) do
    ( Map.get(lists, :wakes, []) ++ ["H", "M", "L", "J", "Y"])
    |> uniq_present()
  end

  defp sts_options do
    ~w(ALTRV ATFMX FFR FLTCK HAZMAT HEAD HOSP HUM MARSA MEDEVAC NONRVSM SAR STATE)
  end

  defp pbn_options do
    ~w(A1 B1 B2 B3 B4 B5 B6 C1 C2 C3 C4 D1 D2 D3 D4 L1 O1 O2 O3 O4 S1 S2 T1 T2)
  end

  defp datalist(_id, []), do: ""

  defp datalist(id, values) do
    options = values |> Enum.map_join("", fn value -> ~s(<option value="#{html(value)}"></option>) end)
    ~s(<datalist id="#{html(id)}">#{options}</datalist>)
  end

  defp option(value, label, selected) do
    attr = if value == selected, do: " selected", else: ""
    ~s(<option value="#{html(value)}"#{attr}>#{html(label)}</option>)
  end

  defp notice_banner(nil), do: ""

  defp notice_banner({kind, message}) do
    """
    <div class="notice #{html(kind)}">#{html(message)}</div>
    """
  end

  defp input(name, label, value) do
    maxlength =
      case to_string(name) do
        "originator" -> ~s( maxlength="8")
        _ -> ""
      end

    """
    <div class="field">
      <label>#{html(label)}</label>
      <input name="#{html(name)}" value="#{html(value)}"#{maxlength}>
    </div>
    """
  end

  defp textarea(name, label, value) do
    """
    <div class="field">
      <label>#{html(label)}</label>
      <textarea name="#{html(name)}" spellcheck="false">#{html(value)}</textarea>
    </div>
    """
  end

  defp ats_form(type, title, body_html) do
    """
    <section id="#{html(type)}">
      <h2>#{html(type)} - #{html(title)}</h2>
      <form method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="#{html(type)}">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="#{html(type)}">
        <p class="actions"><button type="submit" name="compose_action" value="send_clear">Send+Clear</button> <button type="submit" name="compose_action" value="send">Send #{html(type)}</button> <button type="button" class="js-clear-form">Clear</button></p>
        #{target_fields()}
        #{aftn_header_fields()}
        #{body_html}
      </form>
    </section>
    """
  end

  defp aftn_header_fields do
    """
    <div class="subhead">AFTN Header</div>
    <input type="hidden" name="transmission_id" value="#{html(current_cid_seq())}" data-current-cid-seq>
    <div class="grid4">
      #{input("filing_time", "Filing Time", current_filing_time())}
      #{input("priority", "Priority", "FF")}
      #{input("originator", "Originator", "WIIIYFYX")}
    </div>
    #{textarea("addresses", "Destination Address", "WAJJYFYA WIIIYOYA WAAAJXJX WIOOYOYX")}
    """
  end

  defp aftn_free_form(params \\ %{}) do
    priority = compose_prefill(params, "priority", "FF")
    originator = compose_prefill(params, "originator", "WAJJYFYC")
    message = compose_prefill(params, "message", "")

    """
    <section id="AFTN_FREE" class="aftn-window">
      <div class="aftn-title"><i class="bi bi-chat-left-text"></i><span>AFTN Free Text </span></div>
      <form id="aftn-free-form" class="aftn-form" method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="AFTN_FREE">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="AFTN_FREE">
        <input id="aftn-send-at" type="hidden" name="transmission_id" value="#{html(current_cid_seq())}" data-current-cid-seq>
        <div class="aftn-toolbar">
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send_clear"><i class="bi bi-send-plus-fill"></i><span>Send+Clear</span></button>
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-fill"></i><span>Send</span></button>
          <button class="aftn-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save"></i><span>Save</span></button>
          <button class="aftn-tool discard js-clear-form" type="button"><i class="bi bi-eraser"></i><span>Clear</span></button>
          <a class="aftn-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <div class="aftn-body">
          <div class="aftn-required-note">Blue field indicates required field.</div>
          <div class="aftn-card">
            <div class="aftn-card-title">AFTN Header</div>
            <div class="aftn-address-row">
              <div class="field required">
                <label>Priority</label>
                <select name="priority">
                  #{option("", "", priority)}
                  #{option("FF", "FF", priority)}
                  #{option("GG", "GG", priority)}
                  #{option("DD", "DD", priority)}
                  #{option("SS", "SS", priority)}
                  #{option("KK", "KK", priority)}
                </select>
              </div>
              <div class="field required">
                <label>Address</label>
                <div class="aftn-address-grid">#{address_inputs(params)}</div>
              </div>
            </div>
            <div class="aftn-meta">
              <div class="field">
                <label>Filing Time</label>
                <div class="time-control">
                  <input id="aftn-filing-time" name="filing_time" value="#{current_filing_time()}">
                  <button class="time-button js-current-time" type="button" data-target="aftn-filing-time" title="Use current DDHHMM" aria-label="Use current DDHHMM"><i class="bi bi-clock-history"></i></button>
                </div>
              </div>
              <div class="field required"><label>Originator</label><input name="originator" value="#{html(originator)}" maxlength="8"></div>
              <div class="field"><label>Originator's Reference</label><input name="originator_reference" value=""></div>
              <label class="aftn-bell"><input type="checkbox" name="bell" value="1"><i class="bi bi-bell-fill" aria-hidden="true"></i><span>Bell</span></label>
            </div>
          </div>
          <div class="aftn-editor-head">
            <label for="aftn-free-message">Free Text</label>
            <span>AFTN body text</span>
          </div>
          <textarea id="aftn-free-message" class="aftn-textarea" name="message" spellcheck="false">#{html(message)}</textarea>
          <div class="aftn-footer">
            <div class="aftn-filled">
              <label for="aftn-free-filed-by">Filled By</label>
              <input id="aftn-free-filed-by" name="filed_by" value="SYSTEM" placeholder="Operator">
            </div>
          </div>
        </div>
      </form>
    </section>
    """
  end

  defp compose_prefill(params, key, default) do
    if Map.has_key?(params, key) do
      params
      |> Map.get(key, "")
      |> to_string()
      |> String.upcase()
    else
      default
    end
  end

  defp address_inputs(params \\ %{}) do
    1..21
    |> Enum.map_join("", fn index ->
      value = compose_prefill(params, "address_#{index}", "")
      required = if index == 1, do: " required", else: ""
      ~s(<input name="address_#{index}" value="#{html(value)}" maxlength="8"#{required}>)
    end)
  end

  defp fpl_like_form(type, title) do
    ats_form(
      type,
      title,
      """
      <div class="subhead">ATS Body</div>
      <div class="grid4">
        #{input("aircraft_id", "7a Aircraft ID", "GIA120")}
        #{input("flight_rules", "8a Flight Rules", "I")}
        #{input("flight_type", "8b Type", "S")}
        #{input("aircraft_type", "9b Aircraft Type", "A320")}
        #{input("wake", "9c WTC", "M")}
        #{input("equipment", "10 Equipment", "SACD/AB1")}
        #{input("departure", "13a DEP AD", "WIII")}
        #{input("departure_time", "13b Time", "0330")}
        #{input("speed_level", "15 Speed/Level", "N0250F230")}
        #{input("destination", "16a DEST AD", "WAAA")}
        #{input("eet", "16b EET", "0200")}
        #{input("item18", "18 Other Info", "DOF/#{today_dof()}")}
      </div>
      #{textarea("route", "15c Route", "CKG W11 TKG W19 BKL")}
      """
    )
  end

  defp basic_flight_form(type, title) do
    ats_form(
      type,
      title,
      """
      <div class="subhead">ATS Body</div>
      <div class="grid4">
        #{input("aircraft_id", "7a Aircraft ID", "GIA120")}
        #{input("departure", "13a DEP AD", "WIII")}
        #{input("departure_time", "13b Time", "0330")}
        #{input("destination", "16a DEST AD", "WAAA")}
        #{input("dof", "18 DOF", today_dof())}
      </div>
      """
    )
  end

  defp change_form do
    ats_form(
      "CHG",
      "Change Message",
      """
      <div class="subhead">ATS Body</div>
      <div class="grid4">
        #{input("aircraft_id", "7a Aircraft ID", "GIA120")}
        #{input("departure", "13a DEP AD", "WIII")}
        #{input("departure_time", "13b Time", "0330")}
        #{input("destination", "16a DEST AD", "WAAA")}
        #{input("dof", "18 DOF", today_dof())}
      </div>
      #{textarea("item22", "22 Amendment", "13/WIII0400")}
      """
    )
  end

  defp arrival_form(type \\ "ARR", title \\ "Arrival Message") do
    ats_form(
      type,
      title,
      """
      <div class="subhead">ATS Body</div>
      <div class="grid4">
        #{input("aircraft_id", "7a Aircraft ID", "GIA120")}
        #{input("departure", "13a DEP AD", "WIII")}
        #{input("destination", "17a ARR/DEST AD", "WAAA")}
        #{input("arrival_time", "17b Time", "0530")}
      </div>
      """
    )
  end

  defp coordination_form(type, title) do
    ats_form(
      type,
      title,
      """
      <div class="subhead">ATS Body</div>
      <div class="grid4">
        #{input("aircraft_id", "7a Aircraft ID", "GIA120")}
        #{input("departure", "13a DEP AD", "WIII")}
        #{input("departure_time", "13b Time", "0330")}
        #{input("destination", "16a DEST AD", "WAAA")}
      </div>
      #{textarea("item22", "22 Coordination Data", "15/N0250F250 CKG W11 TKG")}
      """
    )
  end

  defp estimate_form do
    ats_form(
      "EST",
      "Estimate Message",
      """
      <div class="subhead">ATS Body</div>
      <div class="grid4">
        #{input("aircraft_id", "7a Aircraft ID", "GIA120")}
        #{input("departure", "13a DEP AD", "WIII")}
        #{input("departure_time", "13b Time", "0330")}
        #{input("fix", "14a Fix", "CKG")}
        #{input("fix_time", "14b Time", "0410")}
        #{input("estimate_level", "14c Level", "F230")}
        #{input("destination", "16a DEST AD", "WAAA")}
      </div>
      """
    )
  end

  defp lam_form do
    ats_form(
      "LAM",
      "Logical Acknowledgement",
      """
      <div class="subhead">ATS Body</div>
      #{input("reference", "Reference", "GIA120")}
      """
    )
  end

  defp rcf_form(params \\ %{}) do
    priority = compose_prefill(params, "priority", "FF")
    originator = compose_prefill(params, "originator", default_originator())

    """
    <section id="RCF" class="aftn-window">
      <div class="aftn-title"><i class="bi bi-broadcast-pin"></i><span>RCF ( Radio Communication Failure) Message</span></div>
      <form id="rcf-form" class="aftn-form" method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="RCF">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="RCF">
        <div class="aftn-toolbar">
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send_clear"><i class="bi bi-send-plus-fill"></i><span>Send+Clear</span></button>
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-fill"></i><span>Send</span></button>
          <button class="aftn-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save"></i><span>Save</span></button>
          <button class="aftn-tool discard js-clear-form" type="button"><i class="bi bi-eraser"></i><span>Clear</span></button>
          <a class="aftn-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <input type="hidden" name="transmission_id" value="#{html(current_cid_seq())}" data-current-cid-seq>
        <div class="aftn-body">
          <div class="aftn-required-note">Blue field indicates required field.</div>
          #{alr_header(priority, originator, params)}
          <div class="rcf-body">
            <div class="alr-grid">
              <div class="alr-row rcf-row-main">
                #{rcf_message_number()}
                #{rcf_input("reference_data", "Reference Data", "", maxlength: 12, tooltip: alr_tooltip("reference_data"))}
                #{rcf_input("aircraft_id", "7. Aircraft ID", "", required: true, maxlength: 7, prefix: "-", tooltip: alr_tooltip("aircraft_id"))}
                #{rcf_check_field("ssr_mode", "SSR Mode", "A", prefix: "/", tooltip: alr_tooltip("ssr_mode"))}
                #{rcf_input("ssr_code", "Code", "", maxlength: 4, tooltip: alr_tooltip("ssr_code"))}
              </div>
              <div class="alr-row alr-row-1">
                #{rcf_textarea("radio_failure", "21. Radio Failure Information", "", required: true, prefix: "-", suffix: ")", tooltip: alr_tooltip("radio_failure"))}
              </div>
              <div class="field rcf-filled"#{alr_title_attr(alr_tooltip("filed_by"))}>
                <label#{alr_title_attr(alr_tooltip("filed_by"))}>Filled By</label>
                <input name="filed_by" value="SYSTEM"#{alr_title_attr(alr_tooltip("filed_by"))}>
              </div>
            </div>
          </div>
        </div>
      </form>
    </section>
    """
  end

  defp rcf_message_number do
    message_type_tip = alr_title_attr(alr_tooltip("message_type"))
    number_tip = alr_title_attr(alr_tooltip("alr_number"))

    """
    <div class="field required rcf-message-type"#{message_type_tip}>
      <label#{message_type_tip}>3. Message Type / Number</label>
      <div class="alr-control compact">
        #{alr_mark("(")}
        <input name="message_type" value="RCF" readonly required maxlength="3"#{message_type_tip}>
        <input name="message_number" value="" maxlength="12"#{number_tip}>
      </div>
    </div>
    """
  end

  defp rcf_input(name, label, value, opts \\ []) do
    required = if Keyword.get(opts, :required, false), do: " required", else: ""
    maxlength = if max = Keyword.get(opts, :maxlength), do: ~s( maxlength="#{max}"), else: ""
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field#{required}"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<input name="#{html(name)}" value="#{html(value)}"#{maxlength}#{required}#{title}>#{suffix}</div>
    </div>
    """
  end

  defp rcf_textarea(name, label, value, opts \\ []) do
    required = if Keyword.get(opts, :required, false), do: " required", else: ""
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field rcf-radio#{required}"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<textarea class="alr-text" name="#{html(name)}" spellcheck="false"#{required}#{title}>#{html(value)}</textarea>#{suffix}</div>
    </div>
    """
  end

  defp rcf_check_field(name, label, value, opts \\ []) do
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<label class="alr-inline-check"#{title}><input type="checkbox" name="#{html(name)}" value="#{html(value)}"#{title}>#{html(value)}</label>#{suffix}</div>
    </div>
    """
  end

  defp target_fields do
    ""
  end

  defp today_dof do
    Date.utc_today()
    |> Calendar.strftime("%y%m%d")
  end

  defp current_filing_time do
    Calendar.strftime(DateTime.utc_now(), "%d%H%M")
  end

  defp current_cid_seq do
    setting = Tp.Settings.get_or_default()
    "#{setting.cid}#{setting.tseq}"
  rescue
    _error -> "RCJ0001"
  end

  defp default_originator do
    Tp.Settings.get_or_default().originator || "WAJJYFYC"
  rescue
    _error -> "WAJJYFYC"
  end

  defp default_udp_host do
    Tp.Settings.udp_target().host
  end

  defp default_udp_port do
    Tp.Settings.udp_target().port
  end

  defp local_udp_port do
    Tp.Settings.udp_receive_port()
  end

  defp message_modal_script do
    """
    <div id="message-modal" class="modal-backdrop">
      <div class="modal-card" role="dialog" aria-modal="true" aria-labelledby="message-modal-title">
        <div class="modal-head">
          <h3 id="message-modal-title">Message Detail</h3>
          <button class="modal-close" type="button" id="message-modal-close" aria-label="Close">×</button>
        </div>
        <div id="message-modal-body" class="modal-body">
          <div class="muted">Loading...</div>
        </div>
      </div>
    </div>
    <script>
      ( function ( ) {
        function byId(id) { return document.getElementById(id); }
        var latestMessageId = 0;

        function escapeHtml(value) {
          return String(value == null ? '' : value)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
        }

        function visibleAftn(value) {
          var text = String(value == null ? '' : value);
          var out = '';
          for ( var i = 0; i < text.length; i++) {
            var code = text.charCodeAt(i);
            if ( code === 1) out += '[SOH]';
            else if ( code === 2) out += '[STX]';
            else if ( code === 3) out += '[ETX]';
            else if ( code === 11) out += '[VT]';
            else if ( code === 13) out += String.fromCharCode(10);
            else if ( code === 10 && i > 0 && text.charCodeAt(i - 1) === 13) continue;
            else out += text.charAt(i);
          }
          return out;
        }

        function visibleMonitorUdp(value) {
          var text = String(value == null ? '' : value);
          var out = '';
          for ( var i = 0; i < text.length; i++) {
            var code = text.charCodeAt(i);
            if ( code === 1 || code === 2 || code === 3 || code === 11) continue;
            if ( code === 13) out += String.fromCharCode(10);
            else if ( code === 10 && i > 0 && text.charCodeAt(i - 1) === 13) continue;
            else out += text.charAt(i);
          }
          return out;
        }

        function messagePreview(value, limit) {
          var text = visibleAftn(value || '').trim();
          limit = limit || 45;
          var lines = text.split('\\n');
          if ( lines.length > 2) return {text: lines.slice(0, 2).join('\\n') + '...', hasMore: true};
          if ( text.length > limit) return {text: text.slice(0, limit) + '...', hasMore: true};
          return {text: text, hasMore: false};
        }

        function composeMessageText(value) {
          var text = String(value == null ? '' : value);
          var stx = text.indexOf(String.fromCharCode(2));
          if ( stx >= 0) {
            text = text.slice(stx + 1);
            var etx = text.indexOf(String.fromCharCode(3));
            if ( etx >= 0) text = text.slice(0, etx);
          }
          return visibleMonitorUdp(text).trim();
        }

        function composeAddresses(value) {
          if ( Array.isArray(value)) return value.filter(Boolean).join(' ');
          return String(value == null ? '' : value).trim();
        }

        function detailItem(label, value) {
          return '<dt>' + escapeHtml(label) + '</dt><dd>' + escapeHtml(value || '-') + '</dd>';
        }

        function formatMonitorTime(value) {
          return String(value || '').replace('T', ' ').replace('Z', '').slice(0, 19);
        }

        function udpEmptyHtml() {
          return '<div class="udp-empty">No Data.</div>';
        }

        function hasBellSignal(item) {
          var raw = String(item && item.raw != null ? item.raw : '');
          for ( var i = 0; i < raw.length; i++) {
            var code = raw.charCodeAt(i);
            if ( code === 7 || code === 11) return true;
          }
          return raw.indexOf('[BEL]') !== -1 || raw.indexOf('\\u0007') !== -1 || raw.indexOf('\\u000b') !== -1;
        }

        function soundSettings() {
          var node = byId('sound-settings');
          var enabled = !!node && node.getAttribute('data-sound-enabled') !== 'false';
          var repeat = node ? parseInt(node.getAttribute('data-alarm-repeat') || '1', 10) : 1;
          if ( isNaN(repeat) || repeat < 0) repeat = 1;
          return {enabled: enabled, repeat: repeat};
        }

        function ensureAlarmStopButton() {
          var existing = byId('alarm-stop-button');
          if ( existing) return existing;
          var button = document.createElement('button');
          button.id = 'alarm-stop-button';
          button.type = 'button';
          button.textContent = 'Stop Alarm';
          button.style.cssText = 'display:none;position:fixed;right:20px;bottom:82px;z-index:1200;background:#b42318;color:white;border:0;border-radius:6px;padding:9px 14px;font-weight:800;box-shadow:0 10px 26px rgba(0,0,0,.22);cursor:pointer';
          button.onclick = stopBellAlarm;
          document.body.appendChild(button);
          return button;
        }

        function stopBellAlarm() {
          if ( window.__aftnBellAlarmTimer) {
            window.clearInterval(window.__aftnBellAlarmTimer);
            window.__aftnBellAlarmTimer = null;
          }
          var button = byId('alarm-stop-button');
          if ( button) button.style.display = 'none';
        }

        function playBellTone() {
          try {
            var AudioContext = window.AudioContext || window.webkitAudioContext;
            if ( !AudioContext) {
              if ( navigator.vibrate) navigator.vibrate([180, 80, 180]);
              return;
            }
            var context = window.__aftnBellAudioContext || new AudioContext();
            window.__aftnBellAudioContext = context;
            if ( context.state === 'suspended' && context.resume) context.resume();
            var start = context.currentTime;
            [0, 0.28, 0.56].forEach(function ( offset) {
              var osc = context.createOscillator();
              var gain = context.createGain();
              osc.type = 'sine';
              osc.frequency.setValueAtTime(880, start + offset);
              gain.gain.setValueAtTime(0.0001, start + offset);
              gain.gain.exponentialRampToValueAtTime(0.22, start + offset + 0.015);
              gain.gain.exponentialRampToValueAtTime(0.0001, start + offset + 0.18);
              osc.connect(gain);
              gain.connect(context.destination);
              osc.start(start + offset);
              osc.stop(start + offset + 0.2);
            });
            if ( navigator.vibrate) navigator.vibrate([180, 80, 180]);
          } catch ( _error) {}
        }

        function playBellAlarm() {
          var settings = soundSettings();
          if ( !settings.enabled) return;
          stopBellAlarm();
          playBellTone();
          if ( settings.repeat === 1) return;
          var played = 1;
          var stopButton = ensureAlarmStopButton();
          stopButton.style.display = 'inline-flex';
          window.__aftnBellAlarmTimer = window.setInterval(function () {
            if ( settings.repeat > 0 && played >= settings.repeat) {
              stopBellAlarm();
              return;
            }
            played += 1;
            playBellTone();
          }, 1200);
        }

        function itemHasBellSignal(item) {
          return !!(item && item.bell) || hasBellSignal(item) || hasBellSignal({raw: item && item.raw_text});
        }

        function playBellAlarmForRows(rows) {
          for ( var i = 0; i < rows.length; i++) {
            if ( itemHasBellSignal(rows[i])) {
              playBellAlarm();
              return;
            }
          }
        }

        function updateHeaderTime() {
          var node = byId('header-time-value');
          if ( !node) return;
          var now = new Date();
          var pad = function ( value) { value = String(value); return value.length < 2 ? '0' + value : value; };
          node.textContent = now.getUTCFullYear() + '-' + pad(now.getUTCMonth() + 1) + '-' + pad(now.getUTCDate()) + ' ' + pad(now.getUTCHours()) + ':' + pad(now.getUTCMinutes()) + ':' + pad(now.getUTCSeconds());
        }

        function monitorSource(item) {
          var ip = item && item.source_ip ? item.source_ip : '';
          var port = item && item.source_port ? item.source_port : '';
          if ( ip && port) return ip + ':' + port;
          return ip || '';
        }

        function monitorRow(item) {
          var messageIn = '<i class="bi bi-envelope-arrow-down-fill" title="Message in" aria-hidden="true"></i>';
          return '<div class="udp-item">' +
            '<div class="udp-meta">' +
              '<span>' + escapeHtml(formatMonitorTime(item.time)) + '</span>' +
              '<span>' + escapeHtml(item.kind || 'UDP') + '</span>' +
              '<span class="udp-source">' + messageIn + escapeHtml(monitorSource(item)) + '</span>' +
            '</div>' +
            '<pre class="udp-raw">' + escapeHtml(visibleMonitorUdp(item.raw || '')) + '</pre>' +
          '</div>';
        }

        function eyeIcon() {
          return '<svg viewBox="0 0 24 24" aria-hidden="true"><path d="M2 12s3.5-7 10-7 10 7 10 7-3.5 7-10 7S2 12 2 12Z"></path><circle cx="12" cy="12" r="3"></circle></svg>';
        }

        function pdfIcon() {
          return '<svg viewBox="0 0 24 24" aria-hidden="true"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8Z"></path><path d="M14 2v6h6"></path><text class="pdf-text" x="6" y="17">PDF</text></svg>';
        }

        function trashIcon() {
          return '<svg viewBox="0 0 24 24" aria-hidden="true"><path d="M3 6h18"></path><path d="M8 6V4h8v2"></path><path d="M19 6l-1 14H6L5 6"></path><path d="M10 11v6"></path><path d="M14 11v6"></path></svg>';
        }

        function kebabIcon() {
          return '<svg viewBox="0 0 24 24" aria-hidden="true"><path d="M12 5h.01"></path><path d="M12 12h.01"></path><path d="M12 19h.01"></path></svg>';
        }

        function messageRow(message) {
          var preview = messagePreview(message.raw_text || '');
          var id = escapeHtml(message.id);
          var readMore = preview.hasMore ? '<button class="read-more js-show-message" type="button" data-message-id="' + id + '" onclick="return showMessagePopupFromButton(this)">More</button>' : '';
          var returnTo = escapeHtml(window.location.pathname + window.location.search);

          return '<tr data-message-id="' + id + '" data-compose-priority="' + escapeHtml(message.priority || '') + '" data-compose-originator="' + escapeHtml(message.originator || '') + '" data-compose-addresses="' + escapeHtml(composeAddresses(message.destinations)) + '" data-compose-message="' + escapeHtml(composeMessageText(message.raw_text || '')) + '">' +
            '<td class="muted">' + escapeHtml(formatMonitorTime(message.inserted_at)) + '</td>' +
            '<td>' + escapeHtml(message.direction || '') + '</td>' +
            '<td>' + escapeHtml(message.cid || '') + '</td>' +
            '<td>' + escapeHtml(message.sequence_no || '') + '</td>' +
            '<td class="message-cell"><code class="message-preview">' + escapeHtml(preview.text) + '</code>' + readMore + '</td>' +
            '<td>' + escapeHtml(message.status || '') + '</td>' +
            '<td>' + escapeHtml(message.note || '') + '</td>' +
            '<td>' + escapeHtml(message.filed_by || '') + '</td>' +
            '<td class="table-actions">' +
              '<div class="action-menu">' +
                '<button class="btn-small icon-action js-action-toggle" type="button" title="Action" aria-label="Action menu" onclick="return toggleActionMenuFromButton(this,event)">' + kebabIcon() + '</button>' +
                '<div class="action-popup">' +
                  '<button class="action-item js-show-message" type="button" data-message-id="' + id + '" onclick="return showMessagePopupFromButton(this)">' + eyeIcon() + '<span>View</span></button>' +
                  '<a class="action-item" href="/messages/' + id + '/pdf" target="_blank">' + pdfIcon() + '<span>PDF</span></a>' +
                  '<form class="inline-form" method="post" action="/messages/' + id + '/delete" onsubmit="return confirm(&quot;Hapus message ini?&quot;)">' +
                    '<input class="js-return-to" type="hidden" name="return_to" value="' + returnTo + '">' +
                    '<button class="action-item danger" type="submit">' + trashIcon() + '<span>Delete</span></button>' +
                  '</form>' +
                '</div>' +
              '</div>' +
            '</td>' +
          '</tr>';
        }

        function closeActionMenus(except) {
          var menus = document.querySelectorAll ? document.querySelectorAll('.action-menu.open') : [];
          for ( var i = 0; i < menus.length; i++) {
            if ( !except || menus[i] !== except) menus[i].classList.remove('open');
          }
        }

        function syncReturnToFields() {
          var value = window.location.pathname + window.location.search;
          var fields = document.querySelectorAll ? document.querySelectorAll('.js-return-to') : [];
          for ( var i = 0; i < fields.length; i++) fields[i].value = value;
        }

        function interactiveTarget(target) {
          return target && target.closest && target.closest('button, a, input, select, textarea, form, .action-menu, .read-more');
        }

        function selectMessageRow(row) {
          if ( !row || !row.classList) return;
          var selected = document.querySelectorAll ? document.querySelectorAll('tr.row-selected') : [];
          for ( var i = 0; i < selected.length; i++) {
            if ( selected[i] !== row) selected[i].classList.remove('row-selected');
          }
          row.classList.add('row-selected');
        }

        function openAftnFreeFromRow(row) {
          if ( !row) return;
          var params = new URLSearchParams();
          params.set('form', 'AFTN_FREE');
          params.set('priority', row.getAttribute('data-compose-priority') || '');
          params.set('originator', row.getAttribute('data-compose-originator') || '');
          params.set('message', row.getAttribute('data-compose-message') || '');
          var addresses = String(row.getAttribute('data-compose-addresses') || '').trim().split(/\s+/);
          for ( var i = 0; i < addresses.length && i < 21; i++) {
            if ( addresses[i]) params.set('address_' + ( i + 1), addresses[i]);
          }
          window.location.href = '/compose?' + params.toString();
        }

        function initLatestMessageId() {
          var body = byId('message-table-body');
          if ( !body || !body.querySelectorAll) return;

          var rows = body.querySelectorAll('button[data-message-id]');
          for ( var i = 0; i < rows.length; i++) {
            var id = parseInt(rows[i].getAttribute('data-message-id'), 10);
            if ( !isNaN(id) && id > latestMessageId) latestMessageId = id;
          }
        }

        function messagesApiUrl() {
          var params = new URLSearchParams(window.location.search || '');
          params.set('page', '1');
          params.set('page_size', ( byId('message-table-body') && byId('message-table-body').getAttribute('data-page-size')) || '15');
          params.delete('after_id');
          params.delete('after_ts');
          params.delete('history');
          return '/api/messages/recent?' + params.toString();
        }

        function pollMessages() {
          var body = byId('message-table-body');
          if ( !body) return;

          var qp = new URLSearchParams(window.location.search || '');
          var onFirstPage = parseInt(qp.get('page') || '1', 10) <= 1;
          var hasCursor   = qp.get('after_id') || qp.get('after_ts') || qp.get('history');
          if ( !onFirstPage || hasCursor) return;

          fetch(messagesApiUrl(), {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin'
          })
            .then(function ( response) {
              if ( !response.ok) throw new Error('messages failed');
              return response.json();
            })
            .then(function ( payload) {
              var rows = payload && payload.data ? payload.data : [];
              var fresh = [];

              for ( var i = 0; i < rows.length; i++) {
                var id = parseInt(rows[i].id, 10);
                if ( !isNaN(id) && !body.querySelector('[data-message-id="' + id + '"]')) fresh.push(rows[i]);
              }
              if ( !fresh.length) return;
              playBellAlarmForRows(fresh);
              var empty = body.querySelector('.empty-row');
              if ( empty) empty.parentNode.removeChild(empty);

              for ( var index = fresh.length - 1; index >= 0; index--) {
                body.insertAdjacentHTML('afterbegin', messageRow(fresh[index]));
                var freshId = parseInt(fresh[index].id, 10);
                if ( !isNaN(freshId) && freshId > latestMessageId) latestMessageId = freshId;
              }

              var limit = parseInt(body.getAttribute('data-page-size') || '15', 10);
              var tableRows = body.querySelectorAll ? body.querySelectorAll('tr') : [];
              for ( var rowIndex = limit; rowIndex < tableRows.length; rowIndex++) tableRows[rowIndex].parentNode.removeChild(tableRows[rowIndex]);
            })
            .catch(function ( ) {});
        }

        function pollUdpMonitor() {
          var body = byId('udp-monitor-body');
          if ( !body) return;

          fetch('/api/udp-monitor', {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin'
          })
            .then(function ( response) {
              if ( !response.ok) throw new Error('monitor failed');
              return response.json();
            })
            .then(function ( payload) {
              var rows = payload && payload.data ? payload.data : [];
              if ( !rows.length) return;

              playBellAlarmForRows(rows);

              var html = '';
              for ( var i = rows.length - 1; i >= 0; i--) html += monitorRow(rows[i]);

              if ( body.querySelector && body.querySelector('.udp-empty')) body.innerHTML = '';
              body.insertAdjacentHTML('afterbegin', html);

              var items = body.querySelectorAll ? body.querySelectorAll('.udp-item') : [];
              for ( var index = 10; index < items.length; index++) items[index].parentNode.removeChild(items[index]);
            })
            .catch(function ( ) {});
        }

        function pollStatus() {
          // Footer status is handled by status_footer_script() on every page.
        }

        function openModal() {
          var modal = byId('message-modal');
          if ( !modal) return;
          if ( modal.classList) modal.classList.add('open');
          else if ( String(modal.className).indexOf('open') < 0) modal.className += ' open';
        }

        function closeModal() {
          var modal = byId('message-modal');
          if ( !modal) return;
          if ( modal.classList) modal.classList.remove('open');
          else modal.className = String(modal.className).split('open').join('');
        }

        function renderFromTemplate(id) {
          var body = byId('message-modal-body');
          var title = byId('message-modal-title');
          var template = byId('message-detail-' + id);
          if ( !body || !title || !template) return false;
          title.textContent = 'Message #' + id;
          body.innerHTML = template.innerHTML;
          return true;
        }

        function renderFromApi(id) {
          var body = byId('message-modal-body');
          var title = byId('message-modal-title');
          if ( !body || !title) return;

          title.textContent = 'Message #' + id;
          body.innerHTML = '<div class="muted">Loading message #' + escapeHtml(id) + '...</div>';

          fetch('/api/messages/' + encodeURIComponent(id), {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin'
          })
            .then(function ( response) {
              if ( !response.ok) throw new Error('Message tidak ditemukan / gagal dimuat. HTTP ' + response.status);
              return response.json();
            })
            .then(function ( message) {
              title.textContent = 'Message #' + message.id;
              body.innerHTML =
                '<dl class="modal-grid">' +
                detailItem('Waktu', message.inserted_at) +
                detailItem('IO', message.direction) +
                detailItem('CID', message.cid) +
                detailItem('Priority', message.priority) +
                detailItem('Type', message.message_type) +
                detailItem('Originator', message.originator) +
                detailItem('Aircraft', message.aircraft_id) +
                detailItem('Departure', message.departure) +
                detailItem('Destination', message.destination) +
                detailItem('Route', message.route) +
                detailItem('Sequence', message.sequence_no) +
                detailItem('Status', message.status) +
                detailItem('Note', message.note) +
                detailItem('Filed By', message.filed_by) +
                '</dl>' +
                '<pre class="modal-raw">' + escapeHtml(visibleAftn(message.raw_text)) + '</pre>';
            })
            .catch(function ( error) {
              body.innerHTML = '<div class="modal-error">' + escapeHtml(error.message) + '</div>';
            });
        }

        window.showMessagePopup = function ( id) {
          if ( !id) return false;
          openModal();
          if ( !renderFromTemplate(id)) renderFromApi(id);
          return false;
        };

        window.showMessagePopupFromButton = function ( button) {
          if ( !button) return false;
          return window.showMessagePopup(button.getAttribute('data-message-id'));
        };

        window.toggleActionMenuFromButton = function ( button, event) {
          if ( event && event.preventDefault) event.preventDefault();
          if ( event && event.stopPropagation) event.stopPropagation();
          if ( !button) return false;

          var menu = button.closest ? button.closest('.action-menu') : null;
          if ( !menu) return false;

          var willOpen = !(menu.classList && menu.classList.contains('open'));
          closeActionMenus(menu);
          if ( willOpen && menu.classList) menu.classList.add('open');
          return false;
        };

        window.handleMessageRowClick = function ( event, row) {
          var target = event && ( event.target || event.srcElement);
          if ( !row || interactiveTarget(target)) return false;
          selectMessageRow(row);
          return false;
        };

        window.handleMessageRowDblClick = function ( event, row) {
          var target = event && ( event.target || event.srcElement);
          if ( !row || interactiveTarget(target)) return false;
          if ( event && event.preventDefault) event.preventDefault();
          if ( event && event.stopPropagation) event.stopPropagation();
          selectMessageRow(row);
          openAftnFreeFromRow(row);
          return false;
        };

        document.addEventListener('click', function ( event) {
          var target = event.target;
          var toggle = target && target.closest ? target.closest('.js-action-toggle') : null;

          if ( toggle) {
            return window.toggleActionMenuFromButton(toggle, event);
          }

          var button = target;

          while ( button && button !== document && !(button.className && String(button.className).indexOf('js-show-message') >= 0)) {
            button = button.parentNode;
          }

          if ( button && button !== document && button.getAttribute) {
            event.preventDefault();
            closeActionMenus();
            window.showMessagePopupFromButton(button);
            return false;
          }

          var row = target && target.closest ? target.closest('tr[data-message-id]') : null;
          if ( row && !interactiveTarget(target)) selectMessageRow(row);

          if ( target && ( target.id === 'clear-udp-monitor' || ( target.closest && target.closest('#clear-udp-monitor')))) {
            event.preventDefault();
            var monitor = byId('udp-monitor-body');
            if ( monitor) monitor.innerHTML = udpEmptyHtml();
            return false;
          }

          if ( target && target.id === 'message-modal') closeModal();
          if ( target && target.id === 'message-modal-close') closeModal();
          if ( !(target && target.closest && target.closest('.action-menu'))) closeActionMenus();
        });

        document.addEventListener('keydown', function ( event) {
          if ( event.key === 'Escape' || event.keyCode === 27) {
            closeModal();
            closeActionMenus();
          }
        });

        document.addEventListener('dblclick', function ( event) {
          var target = event.target;
          var row = target && target.closest ? target.closest('tr[data-message-id]') : null;
          if ( !row || interactiveTarget(target)) return;
          return window.handleMessageRowDblClick(event, row);
        });

        initLatestMessageId();
        syncReturnToFields();
        updateHeaderTime();
        window.setInterval(updateHeaderTime, 1000);
        window.setInterval(pollMessages, 2000);
        window.setInterval(pollUdpMonitor, 2000);
        window.setInterval(pollStatus, 5000);
      })();
    </script>
    """
  end

  defp udp_target(message) do
    host = message.udp_target_host || default_udp_host()
    port = message.udp_target_port || default_udp_port()
    "#{host}:#{port}"
  end

  defp message_preview(raw_text, limit \\ 45) do
    text = raw_text |> visible_aftn() |> String.trim()
    lines = String.split(text, "\n")

    cond do
      length(lines) > 2 ->
        {lines |> Enum.take(2) |> Enum.join("\n") |> Kernel.<>("..."), true}

      String.length(text) > limit ->
        {String.slice(text, 0, limit) <> "...", true}

      true ->
        {text, false}
    end
  end

  defp visible_aftn(nil), do: ""

  defp visible_aftn(value) do
    value
    |> to_string()
    |> String.replace(<<1>>, "[SOH]")
    |> String.replace(<<2>>, "[STX]")
    |> String.replace(<<3>>, "[ETX]")
    |> String.replace(<<11>>, "[VT]")
    |> String.replace("\r\n", "\n")
    |> String.replace("\r", "\n")
  end

  defp visible_monitor_udp(nil), do: ""

  defp visible_monitor_udp(value) do
    value
    |> to_string()
    |> String.replace(<<1>>, "")
    |> String.replace(<<2>>, "")
    |> String.replace(<<3>>, "")
    |> String.replace(<<11>>, "")
    |> String.replace("\r\n", "\n")
    |> String.replace("\r", "\n")
  end

  defp format_time(nil), do: ""

  defp format_time(%NaiveDateTime{} = time) do
    time |> NaiveDateTime.truncate(:second) |> NaiveDateTime.to_string()
  end

  defp format_time(%DateTime{} = time) do
    time |> DateTime.truncate(:second) |> DateTime.to_naive() |> NaiveDateTime.to_string()
  end

  defp format_time(time), do: time

  defp format_ddhhmm(nil), do: ""

  defp format_ddhhmm(%DateTime{} = datetime) do
    time = DateTime.to_time(datetime)
    date = DateTime.to_date(datetime)
    "#{pad2(date.day)}#{pad2(time.hour)}#{pad2(time.minute)}"
  end

  defp format_ddhhmm(%NaiveDateTime{} = time) do
    "#{pad2(time.day)}#{pad2(time.hour)}#{pad2(time.minute)}"
  end

  defp format_ddhhmm(time), do: to_string(time)

  defp pad2(value), do: value |> Integer.to_string() |> String.pad_leading(2, "0")

  defp time_sort_key(nil), do: ""
  defp time_sort_key(%DateTime{} = time), do: DateTime.to_iso8601(time)
  defp time_sort_key(%NaiveDateTime{} = time), do: NaiveDateTime.to_iso8601(time)
  defp time_sort_key(time), do: to_string(time)

  defp detail_item(label, value) do
    "<dt>#{html(label)}</dt><dd>#{html(value)}</dd>"
  end

  defp pdf_row(label, value) do
    "<tr><th>#{html(label)}</th><td>#{html(value)}</td></tr>"
  end

  defp join_pair(nil, nil), do: nil
  defp join_pair(left, nil), do: left
  defp join_pair(nil, right), do: right
  defp join_pair(left, right), do: "#{left} #{right}"

  defp legacy_ref(%{legacy_table: nil}), do: nil
  defp legacy_ref(message), do: "#{message.legacy_table}.#{message.legacy_id}"

  defp route_summary(message) do
    [message.departure, message.destination]
    |> Enum.reject(&is_nil/1)
    |> Enum.join(" > ")
    |> then(fn pair ->
      case {pair, message.route} do
        {"", nil} -> nil
        {"", route} -> route
        {pair, nil} -> pair
        {pair, route} -> "#{pair} / #{route}"
      end
    end)
  end

  defp html(nil), do: ""

  defp html(value) do
    value
    |> to_string()
    |> String.replace("&", "&amp;")
    |> String.replace("<", "&lt;")
    |> String.replace(">", "&gt;")
    |> String.replace("\"", "&quot;")
    |> String.replace("'", "&#39;")
  end
end
