defmodule TpWeb.Views do
  def dashboard(messages, events, filters \\ [], notice \\ nil, settings \\ nil, received_messages \\ [], pagination \\ %{}) do
    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>AFTN Teleprinter</title>
      <style>
        body{margin:0;padding:56px 0 62px;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{position:fixed;top:0;left:0;right:0;z-index:900;height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px;box-shadow:0 6px 18px rgba(20,50,74,.18)}
        header a{color:white}.top-nav{display:flex;align-items:center;gap:16px}.header-spacer{flex:1}.header-time{font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-size:13px;font-weight:700;color:#d8e8f3;white-space:nowrap}.top-menu{position:relative}.top-menu summary{cursor:pointer;color:white;font-weight:600;list-style:none;display:flex;align-items:center;gap:6px}.top-menu summary::-webkit-details-marker{display:none}.menu-icon{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.top-submenu{position:absolute;top:28px;left:0;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 12px 26px rgba(23,32,42,.16);z-index:20;min-width:260px;padding:6px}.top-submenu a{display:block;color:#17202a;padding:8px 10px;border-radius:4px;white-space:nowrap}.top-submenu a:hover{background:#edf4fa;text-decoration:none}
        main{display:grid;grid-template-columns:1fr 360px;gap:16px;padding:16px}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;overflow:hidden}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        table{width:100%;border-collapse:collapse;font-size:13px} th,td{border-bottom:1px solid #edf0f3;padding:8px;text-align:left;vertical-align:top}
        th{background:#f0f3f6;color:#435466;font-weight:600;white-space:nowrap} code,textarea{font-family:ui-monospace,SFMono-Regular,Consolas,monospace}
        .table-wrap{overflow:auto}tbody tr[data-message-id]{cursor:pointer}tbody tr.row-selected td{background:#eaf4ff}.table-actions{white-space:nowrap;text-align:center;vertical-align:middle}.btn-small{display:inline-flex;align-items:center;justify-content:center;gap:6px;background:#14324a;color:white;border:0;border-radius:4px;padding:5px 9px;font-size:12px;font-weight:700;cursor:pointer}.btn-small:hover{background:#195b86;text-decoration:none}.btn-small.pdf{background:#195b86}.btn-small.danger{background:#b42318}.btn-small.danger:hover{background:#8a1f11}.icon-action{width:30px;height:30px;padding:0}.icon-action svg,.btn-small svg{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.icon-action .pdf-text{font-size:7px;font-weight:800;fill:currentColor;stroke:none}.inline-form{display:inline;margin:0;padding:0}.empty-row{text-align:center;color:#6d7b88;padding:18px}.table-footer{display:flex;align-items:center;justify-content:space-between;gap:12px;padding:10px 12px;border-top:1px solid #e4e8ee;background:#fbfcfd;font-size:13px;flex-wrap:wrap}.footer-actions{display:flex;align-items:center;gap:8px}.pagination{display:flex;align-items:center;gap:6px;flex-wrap:wrap}.pagination a,.pagination span{border:1px solid #cbd3dc;background:white;border-radius:4px;padding:6px 9px;color:#17202a}.pagination a:hover{background:#edf4fa;text-decoration:none}.pagination .active{background:#14324a;color:white;border-color:#14324a;font-weight:700}.pagination .disabled{color:#9aa6b2;background:#f0f3f6}.page-size{display:flex;align-items:center;gap:6px;padding:0}.page-size select{padding:5px 7px}.modal-backdrop{display:none;position:fixed;inset:0;background:rgba(10,18,28,.55);z-index:1000;align-items:center;justify-content:center;padding:16px}.modal-backdrop.open{display:flex}.modal-card{background:white;border-radius:8px;box-shadow:0 22px 60px rgba(0,0,0,.28);width:min(920px,96vw);max-height:90vh;overflow:hidden;border:1px solid #d8dee6}.modal-head{display:flex;align-items:center;justify-content:space-between;gap:12px;background:#14324a;color:white;padding:11px 14px}.modal-head h3{margin:0;font-size:15px}.modal-close{background:transparent;color:white;border:0;font-size:22px;line-height:1;cursor:pointer;padding:0 4px}.modal-body{padding:14px;overflow:auto;max-height:calc(90vh - 48px)}.modal-grid{display:grid;grid-template-columns:160px 1fr;gap:0;border:1px solid #edf0f3;border-bottom:0;margin-bottom:12px}.modal-grid dt,.modal-grid dd{border-bottom:1px solid #edf0f3;margin:0;padding:7px 9px}.modal-grid dt{background:#f0f3f6;color:#435466;font-weight:700}.modal-raw{margin:0;border:1px solid #d8dee6;border-radius:6px;background:#fbfcfd;padding:12px;white-space:pre-wrap;max-height:430px;overflow:auto}.modal-error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11;border-radius:4px;padding:10px 12px}
        .action-menu{position:relative;display:inline-flex}.action-menu .js-action-toggle{background:transparent;color:#435466;border:0;border-radius:0;box-shadow:none}.action-menu .js-action-toggle:hover{background:transparent;color:#14324a}.btn-outline{background:white;color:#435466;border:1px solid #cbd3dc}.btn-outline:hover{background:#edf4fa;color:#14324a}.action-popup{display:none;position:absolute;right:0;top:28px;z-index:60;min-width:132px;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 12px 26px rgba(23,32,42,.18);padding:5px}.action-menu.open .action-popup{display:block}.action-item{display:flex;align-items:center;gap:8px;width:100%;box-sizing:border-box;background:white;color:#17202a;border:0;border-radius:4px;padding:7px 9px;font-size:12px;font-weight:700;text-align:left;cursor:pointer}.action-item:hover{background:#edf4fa;text-decoration:none}.action-item.danger{color:#b42318}.action-item svg{width:15px;height:15px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.action-item .pdf-text{font-size:7px;font-weight:800;fill:currentColor;stroke:none}
        textarea,input,select{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:8px;background:white}
        textarea{width:100%;min-height:130px}
        form{padding:12px} button{background:#1c6b4f;color:white;border:0;border-radius:4px;padding:8px 12px;font-weight:600}
        label{display:block;font-size:12px;color:#435466;font-weight:600;margin-bottom:4px}.grid2{display:grid;grid-template-columns:1fr 1fr;gap:8px}.grid4{display:grid;grid-template-columns:repeat(4,1fr);gap:8px}.field{margin-bottom:8px}.field input,.field select{width:100%}
        .filters{display:grid;grid-template-columns:1fr 140px 120px 145px 145px auto;gap:8px;padding:12px;border-bottom:1px solid #e4e8ee}
        .notice{margin:12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
        .hint{font-size:12px;color:#6d7b88;margin:4px 0 10px}
        .message-cell{max-width:560px}.message-preview{display:inline;white-space:pre-wrap;word-break:break-word}.read-more{display:inline;border:0;background:transparent;color:#195b86;padding:0 0 0 6px;font-size:12px;font-weight:700;cursor:pointer}.read-more:hover{text-decoration:underline}.muted{color:#6d7b88}.events{padding:12px}.event{border-bottom:1px solid #edf0f3;padding:8px 0}
        .status-footer{position:fixed;left:12px;right:12px;bottom:10px;z-index:850;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 10px 28px rgba(20,50,74,.18);overflow:hidden}.status-panel{padding:8px 10px}.status-summary{display:flex;align-items:center;gap:8px;min-width:0;white-space:nowrap;overflow:auto}.status-card{display:flex;align-items:center;gap:6px;border:1px solid #e1e6ed;background:#fbfcfd;border-radius:5px;padding:5px 8px;min-width:max-content}.status-label{font-size:10px;color:#6d7b88;font-weight:700;text-transform:uppercase}.status-value{font-size:12px;font-weight:700;color:#17202a}.status-card.ok .status-value{color:#1c6b4f}.status-card.warn .status-value{color:#b42318}.status-card.info .status-value{color:#195b86}.status-pill{display:inline-flex;align-items:center;gap:5px;border-radius:999px;padding:3px 7px;background:#f0f3f6;font-size:11px;font-weight:700}.status-dot{width:7px;height:7px;border-radius:50%;background:#b42318;display:inline-block}.status-pill.up .status-dot{background:#1c6b4f}.status-pill.down .status-dot{background:#b42318}.status-actions{margin-left:auto}.status-actions a{display:block;background:#14324a;color:white;border-radius:4px;padding:6px 10px;font-size:12px;font-weight:700}.status-actions a:hover{text-decoration:none;background:#195b86}
        .udp-monitor{margin-top:0}.section-head{display:flex;align-items:center;justify-content:space-between;gap:10px;border-bottom:1px solid #e4e8ee}.section-head h2{border:0}.icon-button{display:inline-flex;align-items:center;justify-content:center;width:30px;height:30px;margin-right:10px;border:1px solid #cbd3dc;border-radius:4px;background:#fbfcfd;color:#14324a;padding:0;cursor:pointer}.icon-button:hover{background:#edf4fa}.udp-monitor-body{padding:9px}.udp-item{border:1px solid #e1e6ed;background:#fbfcfd;border-radius:6px;margin-bottom:7px;overflow:hidden}.udp-meta{display:flex;justify-content:space-between;gap:8px;padding:6px 8px;background:#f0f3f6;color:#435466;font-size:12px;font-weight:700}.udp-raw{margin:0;padding:7px 8px;max-height:110px;overflow:auto;white-space:pre-wrap;font-size:12px;line-height:1.15}.udp-empty{padding:12px;border:1px dashed #cbd3dc;border-radius:6px;color:#6d7b88;font-size:13px}
        @media(max-width:900px){main{grid-template-columns:1fr}.filters,.grid2,.grid4{grid-template-columns:1fr}}
      </style>
    </head>
    <body>
      <header><h1>AFTN Teleprinter</h1><nav class="top-nav">#{compose_dropdown()}#{maintenance_dropdown()}#{views_dropdown()}#{status_dropdown()}</nav>#{header_time()}</header>
      <main>
        <section>
          <h2>Pesan Terakhir</h2>
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
      #{message_modal_script()}
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
      <style>
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}#{header_time_css()} main{max-width:1100px;margin:16px auto;padding:0 16px}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:16px;overflow:visible}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        dl{display:grid;grid-template-columns:180px 1fr;margin:0} dt,dd{border-bottom:1px solid #edf0f3;padding:8px 12px;margin:0}
        dt{background:#f0f3f6;color:#435466;font-weight:600} pre{margin:0;padding:14px;white-space:pre-wrap;font-family:ui-monospace,SFMono-Regular,Consolas,monospace}
      </style>
    </head>
    <body>
      <header><a href="/">Back</a><h1>Message #{html(message.id)}</h1>#{header_time()}</header>
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
      #{header_clock_script()}
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
      <script>window.addEventListener('load', function () { window.setTimeout(function () { window.print(); }, 250); });</script>
    </body>
    </html>
    """
  end

  def compose_page(notice \\ nil, selected \\ "AFTN_FREE", params \\ %{}) do
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
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}#{header_time_css()} main{max-width:1280px;margin:0 auto;padding:16px}
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
        .compose-head{border-bottom:1px solid #e4e8ee;padding:10px 12px;background:#fbfcfd}.compose-head h2{border:0;padding:0 0 2px}.form-note{font-size:11px;color:#195b86;font-style:italic}.required label{color:#005ce6}.form-band{background:#fbfcfd;border:1px solid #e4e8ee;border-radius:6px;padding:10px;margin-bottom:10px}.send-row{display:grid;grid-template-columns:120px 1fr;gap:8px}.address-line{display:grid;grid-template-columns:110px 1fr;gap:10px;align-items:start}.address-grid{display:grid;grid-template-columns:repeat(7,1fr);gap:6px}.address-grid input{text-transform:uppercase;text-align:left;padding:6px}.header-meta{display:grid;grid-template-columns:120px 150px 1fr;gap:8px}.free-text-large textarea{min-height:230px}.filled-row{border-top:1px solid #e4e8ee;margin-top:10px;padding-top:10px;max-width:220px}.compose-submit{display:flex;gap:8px;align-items:center;margin-top:10px}.compose-submit button[type=button]{background:#f0f3f6;color:#17202a;border:1px solid #cbd3dc}.amo-window{background:white;border:1px solid #d8dee6;border-radius:8px;box-shadow:0 12px 28px rgba(20,50,74,.08);overflow:visible}.amo-title{display:flex;align-items:center;gap:9px;background:#14324a;color:white;font-size:15px;font-weight:800;padding:12px 14px}.amo-title i{font-size:18px;color:#9bd0ff}.amo-form{padding:0}.amo-toolbar{position:sticky;top:8px;z-index:600;display:flex;justify-content:flex-end;gap:8px;padding:10px 12px;background:#f7f9fb;border-bottom:1px solid #e4e8ee;box-shadow:0 8px 18px rgba(20,50,74,.10);flex-wrap:wrap}.amo-tool{display:inline-flex;align-items:center;gap:7px;background:white;color:#17202a;border:1px solid #cbd3dc;border-radius:6px;padding:8px 10px;min-width:0;font-size:13px;font-weight:700;cursor:pointer}.amo-tool i{font-size:16px}.amo-tool:hover{background:#edf4fa;text-decoration:none}.amo-tool.primary{background:#1c6b4f;color:white;border-color:#1c6b4f}.amo-tool.primary:hover{background:#17583f}.amo-tool.save{color:#14324a}.amo-tool.discard{color:#8a1f11}.amo-tool.close{color:#435466}.amo-body{padding:12px}.amo-editor-head{display:flex;align-items:center;justify-content:space-between;gap:8px;margin-bottom:6px}.amo-editor-head label{margin:0;color:#435466;font-size:12px;font-weight:800;text-transform:uppercase}.amo-editor-head span{color:#6d7b88;font-size:12px}.aftn-textarea{min-height:320px;background:#fbfcfd;border-color:#cbd3dc;border-radius:6px;resize:vertical;font-size:14px;line-height:1.5}.amo-footer{display:flex;align-items:end;justify-content:flex-start;gap:12px;border-top:1px solid #e4e8ee;margin-top:12px;padding-top:12px;flex-wrap:wrap}.amo-filled{width:260px}.amo-filled label{color:#435466;font-size:12px;font-weight:800;text-transform:uppercase}.amo-filled input{height:36px}
        .aftn-window{background:white;border:1px solid #d8dee6;border-radius:8px;box-shadow:0 12px 28px rgba(20,50,74,.08);overflow:visible}.aftn-title{display:flex;align-items:center;gap:9px;background:#14324a;color:white;font-size:15px;font-weight:800;padding:12px 14px}.aftn-title i{font-size:18px;color:#9bd0ff}.aftn-form{padding:0}.aftn-toolbar{position:sticky;top:8px;z-index:600;display:flex;justify-content:flex-end;gap:8px;padding:10px 12px;background:#f7f9fb;border-bottom:1px solid #e4e8ee;box-shadow:0 8px 18px rgba(20,50,74,.10);flex-wrap:wrap}.aftn-tool{display:inline-flex;align-items:center;gap:7px;background:white;color:#17202a;border:1px solid #cbd3dc;border-radius:6px;padding:8px 10px;font-size:13px;font-weight:700;cursor:pointer}.aftn-tool i{font-size:16px}.aftn-tool:hover{background:#edf4fa;text-decoration:none}.aftn-tool.primary{background:#1c6b4f;color:white;border-color:#1c6b4f}.aftn-tool.primary:hover{background:#17583f}.aftn-tool.save{color:#14324a}.aftn-tool.discard{color:#8a1f11}.aftn-tool.close{color:#435466}.aftn-body{padding:12px}.aftn-required-note{font-size:12px;color:#195b86;font-style:italic;margin-bottom:10px}.aftn-topline{display:grid;grid-template-columns:180px;gap:12px;align-items:end;margin-bottom:10px}.aftn-card{border:1px solid #e4e8ee;border-radius:7px;background:#fbfcfd;padding:10px;margin-bottom:12px}.aftn-card-title{font-size:12px;font-weight:800;text-transform:uppercase;color:#435466;margin-bottom:8px}.aftn-address-row{display:grid;grid-template-columns:90px 1fr;gap:12px;align-items:start}.aftn-address-grid{display:grid;grid-template-columns:repeat(7,1fr);gap:6px}.aftn-address-grid input{text-transform:uppercase;text-align:left;padding:6px}.aftn-meta{display:grid;grid-template-columns:150px 160px 1fr 90px;gap:8px;align-items:end;margin-top:10px}.aftn-bell{display:flex;align-items:center;gap:8px;height:36px}.aftn-bell input{width:auto}.time-control{display:flex;gap:6px}.time-control input{flex:1}.time-button{display:inline-flex;align-items:center;justify-content:center;width:38px;background:white;color:#14324a;border:1px solid #cbd3dc;border-radius:4px;padding:0;cursor:pointer}.time-button:hover{background:#edf4fa}.aftn-editor-head{display:flex;align-items:center;justify-content:space-between;gap:8px;margin-bottom:6px}.aftn-editor-head label{margin:0;color:#435466;font-size:12px;font-weight:800;text-transform:uppercase}.aftn-editor-head span{color:#6d7b88;font-size:12px}.aftn-textarea{min-height:320px;background:#fbfcfd;border-color:#cbd3dc;border-radius:6px;resize:vertical;font-size:14px;line-height:1.5}.aftn-footer{display:flex;align-items:end;justify-content:flex-start;gap:12px;border-top:1px solid #e4e8ee;margin-top:12px;padding-top:12px;flex-wrap:wrap}.aftn-filled{width:260px}.aftn-filled label{color:#435466;font-size:12px;font-weight:800;text-transform:uppercase}.aftn-filled input{height:36px}
        .alr-scroll{max-height:520px;overflow:auto;border-top:1px solid #e4e8ee}.alr-page{padding:12px;border-bottom:1px solid #e4e8ee}.alr-page-title{font-size:13px;font-weight:900;color:#14324a;margin:0 0 10px;text-transform:uppercase}.alr-grid{display:flex;flex-direction:column;gap:10px}.alr-row{display:grid;gap:10px;align-items:end}.alr-row-head{grid-template-columns:1.25fr 1.1fr 1.2fr 1fr 1.8fr}.alr-row-5{grid-template-columns:1.15fr .9fr .85fr 1fr 1fr}.alr-row-5b{grid-template-columns:.8fr 1.1fr .9fr 1.7fr 1.5fr}.alr-row-2{grid-template-columns:1fr 1fr}.alr-row-3{grid-template-columns:repeat(3,1fr)}.alr-row-4{grid-template-columns:repeat(4,1fr)}.alr-row-18{grid-template-columns:1.4fr 1fr 1.4fr}.alr-row-sup{grid-template-columns:1fr 1fr 2fr}.alr-wide{grid-column:span 2}.alr-full{grid-column:1/-1}.alr-checks{display:flex;flex-wrap:wrap;align-items:center;gap:10px;min-height:36px}.alr-checks label,.alr-inline-check{display:inline-flex;align-items:center;gap:5px;margin:0;color:#17202a;font-size:13px;font-weight:700}.alr-checks input,.alr-inline-check input{width:auto}.alr-text{min-height:54px;resize:vertical}.alr-picker{margin-top:6px;padding:6px;font-size:12px}.alr-control{display:flex;align-items:stretch;width:100%}.alr-control input,.alr-control select,.alr-control textarea{flex:1;min-width:0}.alr-control.compact input{width:50%;flex:1 1 50%}.equipment-control .equipment-open{width:40px;flex:0 0 40px;background:white;color:#14324a;border:1px solid #cbd3dc;border-left:0;border-radius:0 4px 4px 0;padding:0;display:inline-flex;align-items:center;justify-content:center;cursor:pointer}.equipment-control .equipment-open:hover{background:#edf4fa}.equipment-control input{border-top-right-radius:0;border-bottom-right-radius:0}.alr-mark{display:inline-flex;align-items:center;justify-content:center;min-width:28px;padding:0 8px;border:1px solid #cbd3dc;background:#f0f3f6;color:#14324a;font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-weight:800}.alr-mark:first-child{border-radius:4px 0 0 4px;border-right:0}.alr-mark:last-child{border-radius:0 4px 4px 0;border-left:0}.alr-control .alr-mark:first-child+input,.alr-control .alr-mark:first-child+select,.alr-control .alr-mark:first-child+textarea{border-top-left-radius:0;border-bottom-left-radius:0}.alr-control input:not(:last-child),.alr-control select:not(:last-child),.alr-control textarea:not(:last-child){border-top-right-radius:0;border-bottom-right-radius:0}
        .equipment-modal{display:none;position:fixed;inset:0;z-index:1200;background:rgba(10,18,28,.52);align-items:center;justify-content:center;padding:16px}.equipment-modal.open{display:flex}.equipment-card{width:min(760px,96vw);max-height:90vh;background:white;border:1px solid #d8dee6;border-radius:8px;box-shadow:0 24px 70px rgba(0,0,0,.28);display:flex;flex-direction:column;overflow:hidden}.equipment-head{display:flex;align-items:center;justify-content:space-between;gap:12px;background:#14324a;color:white;padding:11px 14px}.equipment-head strong{font-size:14px}.equipment-x{background:transparent;color:white;border:0;border-radius:0;padding:4px;display:inline-flex;align-items:center;justify-content:center;cursor:pointer}.equipment-body{padding:12px;overflow:auto}.equipment-group{border:1px solid #e4e8ee;border-radius:6px;margin-bottom:10px;overflow:hidden}.equipment-group-title{background:#f0f3f6;color:#14324a;font-size:12px;font-weight:900;text-transform:uppercase;padding:7px 9px}.equipment-list{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:0}.equipment-row{display:grid;grid-template-columns:22px 48px 1fr;gap:7px;align-items:center;margin:0;padding:7px 9px;border-top:1px solid #edf0f3;cursor:pointer;color:#17202a;font-size:12px;font-weight:600}.equipment-row:nth-child(odd){border-right:1px solid #edf0f3}.equipment-row:hover{background:#edf4fa}.equipment-row input{width:auto}.equipment-code{font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-weight:900;color:#14324a}.equipment-desc{font-weight:600;color:#435466}.equipment-notes{background:#fffbe6;border:1px solid #ffe58f;border-radius:6px;padding:8px 10px;color:#5f4400;font-size:12px}.equipment-notes p{margin:0 0 5px}.equipment-notes p:last-child{margin-bottom:0}.equipment-actions{display:flex;justify-content:flex-end;gap:8px;padding:10px 12px;background:#f7f9fb;border-top:1px solid #e4e8ee;flex-wrap:wrap}
        @media(max-width:900px){.grid2,.grid4,.send-row,.header-meta{grid-template-columns:1fr}}
        @media(max-width:900px){.alr-row,.alr-row-head,.alr-row-5,.alr-row-5b,.alr-row-2,.alr-row-3,.alr-row-4,.alr-row-18,.alr-row-sup{grid-template-columns:1fr}.alr-wide{grid-column:auto}}
        @media(max-width:900px){.compose-head,.address-line{display:block}.address-grid,.aftn-address-grid{grid-template-columns:repeat(2,1fr)}.aftn-topline,.aftn-address-row,.aftn-meta{grid-template-columns:1fr}}
      </style>
    </head>
    <body>
      <header><a href="/">Back</a><h1>Message</h1>#{header_time()}</header>
      <main>
        #{notice_banner(notice)}
        <nav class="toolbar">#{compose_type_links(selected)}</nav>
        #{compose_form(selected, params)}
      </main>
      #{header_clock_script()}
      #{compose_page_script()}
    </body>
    </html>
    """
  end

  def queue_page(messages, q \\ "", notice \\ nil) do
    """
    <!doctype html>
    <html lang="id">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Queue / Not Delivered Messages</title>
      <style>
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}#{header_time_css()} main{max-width:1280px;margin:0 auto;padding:16px}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:16px;overflow:visible}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        table{width:100%;border-collapse:collapse;font-size:13px} th,td{border-bottom:1px solid #edf0f3;padding:7px 8px;text-align:left;vertical-align:top}
        th{background:#f0f3f6;color:#435466;font-weight:700;white-space:nowrap}.table-wrap{overflow:auto}.muted{color:#6d7b88}.empty-row{text-align:center;color:#6d7b88;padding:18px}.message-preview{white-space:pre-wrap;word-break:break-word}
        form.queue-search{display:flex;align-items:end;gap:8px;padding:12px;border-bottom:1px solid #e4e8ee;background:#fbfcfd;flex-wrap:wrap}
        label{display:block;font-size:12px;color:#435466;font-weight:700;margin-bottom:4px} input{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:8px;background:white}
        button,.btn{display:inline-flex;align-items:center;justify-content:center;background:#14324a;color:white;border:0;border-radius:4px;padding:8px 12px;font-weight:700;cursor:pointer}.queue-search .btn{height:36px;box-sizing:border-box;padding:0 12px;font-size:13px;line-height:1}.btn.secondary{background:white;color:#17202a;border:1px solid #cbd3dc}.btn:hover{text-decoration:none;background:#195b86}.btn.secondary:hover{background:#edf4fa}
        .queue-footer{display:flex;align-items:center;justify-content:space-between;gap:8px;padding:10px 12px;border-top:1px solid #e4e8ee;background:#fbfcfd;font-size:13px;font-weight:700;color:#195b86}tbody tr[data-message-id]{cursor:pointer}tbody tr.row-selected td{background:#eaf4ff}
        .table-actions{white-space:nowrap;text-align:center;vertical-align:middle}.btn-small{display:inline-flex;align-items:center;justify-content:center;gap:6px;background:#14324a;color:white;border:0;border-radius:4px;padding:5px 9px;font-size:12px;font-weight:700;cursor:pointer}.btn-small:hover{background:#195b86;text-decoration:none}.icon-action{width:30px;height:30px;padding:0}.icon-action svg,.btn-small svg{width:16px;height:16px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.btn-small .pdf-text{font-size:7px;font-weight:800;fill:currentColor;stroke:none}.btn-outline{background:white;color:#435466;border:1px solid #cbd3dc}.btn-outline:hover{background:#edf4fa;color:#14324a}.inline-form{display:inline;margin:0;padding:0}
        .action-menu{position:relative;display:inline-flex}.action-menu .js-action-toggle{background:transparent;color:#435466;border:0;border-radius:0;box-shadow:none}.action-menu .js-action-toggle:hover{background:transparent;color:#14324a}.action-popup{display:none;position:absolute;right:0;top:28px;z-index:60;min-width:132px;background:white;border:1px solid #d8dee6;border-radius:6px;box-shadow:0 12px 26px rgba(23,32,42,.18);padding:5px}.action-menu.open .action-popup{display:block}.action-item{display:flex;align-items:center;gap:8px;width:100%;box-sizing:border-box;background:white;color:#17202a;border:0;border-radius:4px;padding:7px 9px;font-size:12px;font-weight:700;text-align:left;cursor:pointer}.action-item:hover{background:#edf4fa;text-decoration:none}.action-item.danger{color:#b42318}.action-item svg{width:15px;height:15px;stroke:currentColor;stroke-width:2;fill:none;stroke-linecap:round;stroke-linejoin:round}.action-item .pdf-text{font-size:7px;font-weight:800;fill:currentColor;stroke:none}
        .modal-backdrop{display:none;position:fixed;inset:0;background:rgba(10,18,28,.55);z-index:1000;align-items:center;justify-content:center;padding:16px}.modal-backdrop.open{display:flex}.modal-card{background:white;border-radius:8px;box-shadow:0 22px 60px rgba(0,0,0,.28);width:min(920px,96vw);max-height:90vh;overflow:hidden;border:1px solid #d8dee6}.modal-head{display:flex;align-items:center;justify-content:space-between;gap:12px;background:#14324a;color:white;padding:11px 14px}.modal-head h3{margin:0;font-size:15px}.modal-close{background:transparent;color:white;border:0;font-size:22px;line-height:1;cursor:pointer;padding:0 4px}.modal-body{padding:14px;overflow:auto;max-height:calc(90vh - 48px)}.modal-grid{display:grid;grid-template-columns:160px 1fr;gap:0;border:1px solid #edf0f3;border-bottom:0;margin-bottom:12px}.modal-grid dt,.modal-grid dd{border-bottom:1px solid #edf0f3;margin:0;padding:7px 9px}.modal-grid dt{background:#f0f3f6;color:#435466;font-weight:700}.modal-raw{margin:0;border:1px solid #d8dee6;border-radius:6px;background:#fbfcfd;padding:12px;white-space:pre-wrap;max-height:430px;overflow:auto}.modal-error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11;border-radius:4px;padding:10px 12px}
        .status-waiting{color:#b26a00;font-weight:700}.status-retrying{color:#8a1f11;font-weight:700}
      </style>
    </head>
    <body>
      <header><a href="/">Back</a><h1>Queue / Not Delivered Messages</h1>#{header_time()}</header>
      <main>
        <section>
          <h2>Search Queue/Not Delivered Messages</h2>
          #{notice_banner(notice)}
          <form class="queue-search" method="get" action="/queue">
            <div>
              <label>AIRCRAFT ID</label>
              <input name="q" value="#{html(q)}" autofocus>
            </div>
            <button class="btn" type="submit">Search</button>
            <a class="btn secondary" href="/queue">Clear</a>
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
            <span>#{length(messages)} Element(s) in this table</span>
            #{queue_delete_all_form(q)}
          </div>
        </section>
      </main>
      #{message_modal_script()}
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
      <title>AFTN Teleprinter Setup</title>
      <style>
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        #{header_time_css()} main{max-width:760px;margin:16px auto;background:white;border:1px solid #d8dee6;border-radius:6px;padding:24px}
        header h1{font-size:18px;margin:0} main h1{font-size:22px;margin:0 0 12px} p{line-height:1.55} code,pre{font-family:ui-monospace,SFMono-Regular,Consolas,monospace}
        pre{background:#eef2f6;border:1px solid #d8dee6;border-radius:4px;padding:12px;overflow:auto}
      </style>
    </head>
    <body>
      <header><h1>AFTN Teleprinter Setup</h1>#{header_time()}</header>
      <main>
        <h1>Database belum dimigrasi</h1>
        <p>Aplikasi sudah jalan, tapi tabel MySQL seperti <code>air_messages</code> belum ada di database <code>tp</code>.</p>
        <pre>cd aftn_elixir
    mix ecto.migrate</pre>
        <p>Setelah migration selesai, refresh halaman dashboard.</p>
      </main>
      #{header_clock_script()}
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
      <style>
        body{margin:0;font-family:system-ui,-apple-system,Segoe UI,sans-serif;background:#f6f7f9;color:#17202a}
        a{color:#195b86;text-decoration:none} a:hover{text-decoration:underline}
        header{height:56px;background:#14324a;color:white;display:flex;align-items:center;gap:16px;padding:0 18px}
        header a{color:white}#{header_time_css()} main{max-width:920px;margin:0 auto;padding:16px}
        section{background:white;border:1px solid #d8dee6;border-radius:6px;margin-bottom:16px;overflow:visible}
        h1{font-size:18px;margin:0} h2{font-size:15px;margin:0;padding:12px 14px;border-bottom:1px solid #e4e8ee}
        form{padding:14px} label{display:block;font-size:12px;color:#435466;font-weight:700;margin-bottom:4px}
        input{box-sizing:border-box;border:1px solid #cbd3dc;border-radius:4px;padding:8px;background:white}
        .grid2{display:grid;grid-template-columns:1fr 1fr;gap:12px}.grid4{display:grid;grid-template-columns:repeat(4,1fr);gap:12px}.field{margin-bottom:10px}.field input[type=text],.field input[type=number]{width:100%}
        .radio-row{display:flex;align-items:center;gap:12px;min-height:36px}.radio-row label{display:flex;align-items:center;gap:5px;margin:0;font-weight:600;color:#17202a}
        button{background:#1c6b4f;color:white;border:0;border-radius:4px;padding:9px 14px;font-weight:700}.actions{display:flex;gap:8px;align-items:center;margin-top:8px}.actions a{padding:9px 12px;border:1px solid #cbd3dc;border-radius:4px;background:white}
        .notice{margin:0 0 12px;padding:10px 12px;border-radius:4px;font-size:13px}.notice.error{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11}.notice.info{background:#edf7ed;border:1px solid #b7dfb9;color:#1d5f27}
        .errors{background:#fff1f0;border:1px solid #ffccc7;color:#8a1f11;border-radius:4px;padding:10px 12px;margin:0 0 12px}
        @media(max-width:760px){.grid2,.grid4{grid-template-columns:1fr}}
      </style>
    </head>
    <body>
      <header><a href="/">Back</a><h1>Communication Setting</h1>#{header_time()}</header>
      <main>
        #{notice_banner(notice)}
        #{settings_errors(setting_or_changeset)}
        <form method="post" action="/settings">
          <section>
            <h2>UDP Setup</h2>
            <div style="padding:14px">
              <div class="grid2">
                #{setting_input(setting_or_changeset, :local_udp_port, "Local Receive Port", "number")}
                #{setting_input(setting_or_changeset, :destination_ip_address, "Destination IP Address")}
                #{setting_input(setting_or_changeset, :port, "Destination Port", "number")}
              </div>
            </div>
          </section>
          <section>
            <h2>Communication Setup</h2>
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
                #{setting_input(setting_or_changeset, :prev_st, "Prev.st")}
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
              <div class="grid2">
                #{setting_input(setting_or_changeset, :originator, "Originator")}
              </div>
            </div>
          </section>
          <div class="actions">
            <button type="submit">Save Setting</button>
            <a href="/">Close</a>
          </div>
        </form>
      </main>
      #{header_clock_script()}
    </body>
    </html>
    """
  end

  defp compose_dropdown do
    """
    <details class="top-menu">
      <summary>#{write_icon()}<span>Message</span></summary>
      <div class="top-submenu">
        #{Enum.map_join(compose_menu_items(), "", fn {type, label} -> ~s(<a href="/compose?form=#{html(type)}">#{html(label)}</a>) end)}
      </div>
    </details>
    """
  end

  defp maintenance_dropdown do
    """
    <details class="top-menu">
      <summary>#{maintenance_icon()}<span>Maintenance</span></summary>
      <div class="top-submenu">
        <a href="/settings">Communication Setup</a>
        <a href="#">Type of Aircraft</a>
        <a href="#">Aircraft Registration</a>
        <a href="#">ICAO Location Indicator</a>
        <a href="#">Route</a>
      </div>
    </details>
    """
  end

  defp views_dropdown do
    """
    <details class="top-menu">
      <summary>#{views_icon()}<span>Views</span></summary>
      <div class="top-submenu">
        <a href="/">ATS Messages</a>
        <a href="/?direction=inbound">Incoming Messages</a>
        <a href="#">ICAO Abbreviations and Codes</a>
        <a href="#">Warning Messages</a>
      </div>
    </details>
    """
  end

  defp status_dropdown do
    """
    <details class="top-menu">
      <summary>#{status_icon()}<span>Status</span></summary>
      <div class="top-submenu">
        <a href="/queue">Queue / Not Delivered</a>

      </div>
    </details>
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

  defp setting_input(setting, field, label, type \\ "text") do
    """
    <div class="field">
      <label>#{html(label)}</label>
      <input type="#{html(type)}" name="#{html(field)}" value="#{html(setting_value(setting, field))}">
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

  defp header_time do
    """
    <div class="header-spacer"></div><div id="header-time" class="header-time">#{html(format_time(DateTime.utc_now()))}</div>
    """
  end

  defp header_time_css do
    ".header-spacer{flex:1}.header-time{font-family:ui-monospace,SFMono-Regular,Consolas,monospace;font-size:13px;font-weight:700;color:#d8e8f3;white-space:nowrap}"
  end

  defp header_clock_script do
    """
    <script>
      (function () {
        function updateHeaderTime() {
          var node = document.getElementById('header-time');
          if (!node) return;
          var now = new Date();
          var pad = function (value) { value = String(value); return value.length < 2 ? '0' + value : value; };
          node.textContent = now.getFullYear() + '-' + pad(now.getMonth() + 1) + '-' + pad(now.getDate()) + ' ' + pad(now.getHours()) + ':' + pad(now.getMinutes()) + ':' + pad(now.getSeconds());
        }
        updateHeaderTime();
        window.setInterval(updateHeaderTime, 1000);
      })();
    </script>
    """
  end

  defp compose_page_script do
    """
    <script>
      (function () {
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
          return 'aftn_compose_draft_' + (returnForm || composeType || form.id || 'form');
        }

        function fields(form) {
          return Array.prototype.filter.call(form.elements, function (field) {
            return field.name && field.type !== 'hidden' && field.type !== 'submit' && field.type !== 'button';
          });
        }

        function saveDraft(form) {
          if (!window.sessionStorage) return;
          var draft = {};
          fields(form).forEach(function (field) {
            if (field.type === 'checkbox' || field.type === 'radio') draft[field.name] = field.checked;
            else draft[field.name] = field.value;
          });
          window.sessionStorage.setItem(formKey(form), JSON.stringify(draft));
        }

        function restoreDraft(form) {
          if (!window.sessionStorage) return;
          var params = new URLSearchParams(window.location.search || '');
          var hasPrefill = params.has('priority') || params.has('originator') || params.has('message');
          if (hasPrefill && form.id === 'aftn-free-form') {
            window.sessionStorage.removeItem(formKey(form));
            return;
          }
          var saved = window.sessionStorage.getItem(formKey(form));
          if (!saved) return;

          try {
            var draft = JSON.parse(saved);
            fields(form).forEach(function (field) {
              if (!Object.prototype.hasOwnProperty.call(draft, field.name)) return;
              if (field.type === 'checkbox' || field.type === 'radio') field.checked = !!draft[field.name];
              else field.value = draft[field.name];
            });
          } catch (_error) {
          }
        }

        function clearForm(form) {
          fields(form).forEach(function (field) {
            if (field.type === 'checkbox' || field.type === 'radio') field.checked = false;
            else field.value = '';
          });
          updateAlrRequired(form);
          if (window.sessionStorage) window.sessionStorage.removeItem(formKey(form));
        }

        function uppercaseField(field) {
          if (!field || !field.value || field.type === 'hidden' || field.type === 'submit' || field.type === 'button') return;
          var start = field.selectionStart;
          var end = field.selectionEnd;
          var upper = field.value.toUpperCase();
          if (field.value === upper) return;
          field.value = upper;
          if (typeof start === 'number' && typeof end === 'number') field.setSelectionRange(start, end);
        }

        Array.prototype.forEach.call(document.querySelectorAll('form[action="/messages/compose"]'), function (form) {
          restoreDraft(form);

          fields(form).forEach(uppercaseField);

          form.addEventListener('submit', function () {
            fields(form).forEach(uppercaseField);
            saveDraft(form);
          });

          form.addEventListener('input', function (event) {
            var field = event.target;
            if (field && (field.tagName === 'INPUT' || field.tagName === 'TEXTAREA')) uppercaseField(field);
            updateAlrRequired(form);
          });

          form.addEventListener('change', function () {
            updateAlrRequired(form);
          });

          form.addEventListener('click', function (event) {
            var target = event.target;
            var button = target && target.closest ? target.closest('.js-clear-form, .js-current-time') : null;
            if (!button) return;

            if (button.classList.contains('js-clear-form')) {
              event.preventDefault();
              clearForm(form);
              return;
            }

            if (button.classList.contains('js-current-time')) {
              event.preventDefault();
              var input = document.getElementById(button.getAttribute('data-target'));
              if (input) input.value = currentDdHhMm();
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
          if (!field) return;
          field.required = !!required;
          var box = field.closest ? field.closest('.field') : null;
          if (box) box.classList.toggle('required', !!required);
        }

        function updateAlrRequired(form) {
          if (!form || form.id !== 'alr-form') return;
          setRequired(form, 'ssr_code', !!form.elements.ssr_mode && form.elements.ssr_mode.checked);
          setRequired(form, 'pbn', value(form, 'equipment').indexOf('R') !== -1);
          setRequired(form, 'typ', value(form, 'aircraft_type') === 'ZZZZ');
          setRequired(form, 'dep_info', ['ZZZZ', 'AFIL'].indexOf(value(form, 'departure')) !== -1);
          setRequired(form, 'dest_info', value(form, 'destination') === 'ZZZZ');
          setRequired(form, 'altn_detail', value(form, 'alternate') === 'ZZZZ');
          var dinghyDetail = ['dinghy_number', 'dinghy_capacity', 'dinghy_colour'].some(function (name) { return value(form, name) !== ''; }) || (!!form.elements.dinghy_cover && form.elements.dinghy_cover.checked);
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
          value = (value || '').toUpperCase();
          return order.filter(function (code) { return value.indexOf(code) !== -1; });
        }

        function selectedValues(modal) {
          return Array.prototype.map.call(modal.querySelectorAll('.equipment-choice:checked'), function (box) { return box.value; });
        }

        function applyEquipmentRules(modal) {
          var type = modal.getAttribute('data-equipment-modal');
          var selected = selectedValues(modal);
          modal.querySelectorAll('.equipment-choice').forEach(function (box) { box.disabled = false; });

          if (type === '10a') {
            if (selected.indexOf('N') !== -1) modal.querySelector('[value="S"]').disabled = true;
            if (selected.indexOf('S') !== -1) modal.querySelector('[value="N"]').disabled = true;
            return;
          }

          if (type === '10b') {
            var primary = ['N', 'A', 'C', 'E', 'H', 'I', 'L', 'P', 'S', 'X'];
            var selectedPrimary = primary.filter(function (code) { return selected.indexOf(code) !== -1; });
            if (selectedPrimary.length) {
              primary.forEach(function (code) {
                var box = modal.querySelector('[value="' + code + '"]');
                if (box && selectedPrimary.indexOf(code) === -1) box.disabled = true;
              });
              if (selectedPrimary.indexOf('N') !== -1) {
                modal.querySelectorAll('.equipment-choice').forEach(function (box) {
                  if (box.value !== 'N') box.disabled = true;
                });
              }
            }

            var outOnly = ['B1', 'U1', 'V1'];
            var inOut = ['B2', 'U2', 'V2'];
            var hasOutOnly = outOnly.some(function (code) { return selected.indexOf(code) !== -1; });
            var hasInOut = inOut.some(function (code) { return selected.indexOf(code) !== -1; });
            if (hasOutOnly) inOut.forEach(function (code) { modal.querySelector('[value="' + code + '"]').disabled = true; });
            if (hasInOut) outOnly.forEach(function (code) { modal.querySelector('[value="' + code + '"]').disabled = true; });
          }
        }

        document.addEventListener('click', function (event) {
          var openButton = event.target.closest ? event.target.closest('.js-equipment-open') : null;
          if (openButton) {
            event.preventDefault();
            var target = openButton.getAttribute('data-target');
            var type = openButton.getAttribute('data-equipment');
            var modal = document.querySelector('[data-equipment-modal="' + type + '"]');
            var input = document.querySelector('[name="' + target + '"]');
            if (!modal || !input) return;
            modal.setAttribute('data-target', target);
            var selected = valuesFromField(input.value, equipmentOrder[type] || []);
            modal.querySelectorAll('.equipment-choice').forEach(function (box) {
              box.checked = selected.indexOf(box.value) !== -1;
            });
            applyEquipmentRules(modal);
            modal.classList.add('open');
            return;
          }

          var closeButton = event.target.closest ? event.target.closest('.js-equipment-close') : null;
          if (closeButton) {
            event.preventDefault();
            closeButton.closest('.equipment-modal').classList.remove('open');
            return;
          }

          var clearButton = event.target.closest ? event.target.closest('.js-equipment-clear') : null;
          if (clearButton) {
            event.preventDefault();
            var clearModal = clearButton.closest('.equipment-modal');
            clearModal.querySelectorAll('.equipment-choice').forEach(function (box) {
              box.checked = false;
              box.disabled = false;
            });
            var clearInput = document.querySelector('[name="' + clearModal.getAttribute('data-target') + '"]');
            if (clearInput) {
              clearInput.value = '';
              updateAlrRequired(clearInput.form);
            }
            return;
          }

          var addButton = event.target.closest ? event.target.closest('.js-equipment-add') : null;
          if (addButton) {
            event.preventDefault();
            var addModal = addButton.closest('.equipment-modal');
            var addType = addModal.getAttribute('data-equipment-modal');
            var inputTarget = document.querySelector('[name="' + addModal.getAttribute('data-target') + '"]');
            var picked = selectedValues(addModal);
            var separator = addType === 'sts' ? ' ' : '';
            var value = (equipmentOrder[addType] || []).filter(function (code) { return picked.indexOf(code) !== -1; }).join(separator);
            if (addType === 'pbn' && value.length > 16) {
              if (inputTarget) inputTarget.value = value;
              window.alert('Please insert maximum 8 entries, i.e. a total of not more than 16 characters !!');
              return;
            }
            if (inputTarget) {
              inputTarget.value = value;
              updateAlrRequired(inputTarget.form);
            }
            addModal.classList.remove('open');
            return;
          }

          var row = event.target.closest ? event.target.closest('.equipment-row') : null;
          if (row && !event.target.classList.contains('equipment-choice')) {
            event.preventDefault();
            var choice = row.querySelector('.equipment-choice');
            if (choice && !choice.disabled) {
              choice.checked = !choice.checked;
              applyEquipmentRules(row.closest('.equipment-modal'));
            }
          }
        });

        document.addEventListener('change', function (event) {
          if (event.target.classList && event.target.classList.contains('equipment-choice')) {
            applyEquipmentRules(event.target.closest('.equipment-modal'));
          }
        });
      })();
    </script>
    """
  end

  defp compose_menu_items do
    [
      {"AMO", "AMO"},
      {"FREE", "FREE Text"},
      {"AFTN_FREE", "AFTN Free Text"},
      {"ALR", "ALR"},
      {"RCF", "RCF"},
      {"FPL", "FPL"},
      {"CHG", "CHG"},
      {"DLA", "DLA"},
      {"CNL", "CNL"},
      {"DEP", "DEP"},
      {"ARR", "ARR"},
      {"CDN", "CDN"},
      {"CPL", "CPL"},
      {"EST", "EST"},
      {"ACP", "ACP"},
      {"LAM", "LAM"},
      {"RQP", "RQP"},
      {"RQS", "RQS"},
      {"SPL", "SPL"}
    ]
  end

  defp compose_type_links(selected) do
    compose_menu_items()
    |> Enum.map_join("", fn {type, label} ->
      class = if type == selected, do: " class=\"active\"", else: ""
      ~s(<a#{class} href="/compose?form=#{html(type)}">#{html(label)}</a>)
    end)
  end

  defp compose_form(type, params \\ %{})
  defp compose_form("AMO", _params), do: amo_form()
  defp compose_form("FREE", _params), do: free_message_form()
  defp compose_form("AFTN_FREE", params), do: aftn_free_form(params)
  defp compose_form("ALR", params), do: alr_form(params)
  defp compose_form("RCF", _params), do: rcf_form()
  defp compose_form("FPL", _params), do: fpl_like_form("FPL", "Filed Flight Plan")
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
        <p class="actions"><button type="submit" name="compose_action" value="send">Send #{html(type)}</button> <button type="button" class="js-clear-form">Clear</button></p>
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
      <div class="aftn-title"><i class="bi bi-exclamation-triangle"></i><span>ALR (Alerting) Message</span></div>
      <form id="alr-form" class="aftn-form" method="post" action="/messages/compose">
        <input type="hidden" name="compose_type" value="ALR">
        <input type="hidden" name="return_to" value="compose">
        <input type="hidden" name="return_form" value="ALR">
        <div class="aftn-toolbar">
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-check"></i><span>Send</span></button>
          <button class="aftn-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save2"></i><span>Save</span></button>
          <button class="aftn-tool discard js-clear-form" type="button"><i class="bi bi-arrow-counterclockwise"></i><span>Clear</span></button>
          <a class="aftn-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <div class="aftn-body">
          <div class="aftn-required-note">Blue field indicates required field.</div>
          <div class="aftn-topline">
            <div class="field required">
              <label>Send At</label>
              <div class="time-control">
                <input id="alr-send-at" name="transmission_id" value="#{current_filing_time()}">
                <button class="time-button js-current-time" type="button" data-target="alr-send-at" title="Use current DDHHMM" aria-label="Use current DDHHMM"><i class="bi bi-clock-history"></i></button>
              </div>
            </div>
          </div>
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

  defp alr_header(priority, originator, params) do
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
            <input id="alr-filing-time" name="filing_time" value="#{current_filing_time()}">
            <button class="time-button js-current-time" type="button" data-target="alr-filing-time" title="Use current DDHHMM" aria-label="Use current DDHHMM"><i class="bi bi-clock-history"></i></button>
          </div>
        </div>
        <div class="field required"><label>Originator</label><input name="originator" value="#{html(originator)}" required></div>
        <div class="field"><label>Originator's Reference</label><input name="originator_reference" value=""></div>
        <label class="aftn-bell"><input type="checkbox" name="bell" value="1"> Bell</label>
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
    prefix = alr_mark(Keyword.get(opts, :prefix))
    suffix = alr_mark(Keyword.get(opts, :suffix))
    title = alr_title_attr(Keyword.get(opts, :tooltip) || alr_tooltip(name))

    """
    <div class="field #{html(class)}#{required}"#{title}>
      <label#{title}>#{html(label)}</label>
      <div class="alr-control">#{prefix}<input name="#{html(name)}" value="#{html(value)}"#{readonly}#{required}#{list}#{title}>#{suffix}</div>
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

    OBLIQUE STROKE (/) followed by

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

    1 to 4 LETTER(S) followed by OBLIQUE STROKE (/)
    followed by 1 to 4 LETTER(S) followed by

    3 DECIMAL NUMERICS, giving the 'message number'
    contained in element (b) of the operational
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
  defp alr_tooltip("aircraft_id"), do: "Aircraft Identification\n\nINSERT one of the following aircraft identification, not exceeding 7 alphanumeric characters and without hypens or symbols:\n\na) the ICAO designator for the aircraft operating agency followed by the flight identification (e.g. KLM511, NGA213, JTR25) when in radiotelephony the call sign to be used by the aircraft will consist of the ICAO telephony designator for the operating agency followed by the flight identification (e.g. KLM511, NIGERIA 213, JESTER 25);\n\nb) in the radiotelephony the call sign to be used by the aircraft will consist of this identification alone (e.g. CGAJS), or preceded by the ICAO telephony designator for the aircraft operating agency (e.g. BLIZZARD CGAJS);"
  defp alr_tooltip("ssr_mode"), do: "SSR Mode\n\n1 LETTER giving the SSR Mode related to (c)."
  defp alr_tooltip("ssr_code"), do: "SSR Code\n\n4 NUMERICS giving the SSR Code assigned to the aircraft by ATS and transmitted in the Mode given in (b)."

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

  defp alr_tooltip("aircraft_number"), do: "Number of Aircraft (if more than one)\n\n1 to 2 NUMERICS giving the number of aircraft in the flight."
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
    B : LPV (APV with SBAS)
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
    J5: CPDLC FANS 1/A SATCOM (INMARSAT)
    J6: CPDLC FANS 1/A SATCOM (MTSAT)
    J7: CPDLC FANS 1/A SATCOM (Iridium)
    K : MLS
    L : ILS
    M1: ATC RTF SATCOM (INMARSAT)
    M2: ATC RTF (MTSAT)
    M3: ATC RTF (Iridium)
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
    A   Transponder - Mode A (4 digits - 4 096 codes)
    C   Transponder - Mode A (4 digits - 4 096 codes) and Mode C
    E   Transponder - Mode S, including aircraft identification,
         pressure-altitude and extend squitter (ADS-B) capability
    H   Transponder - Mode S, including aircraft identification,
         pressure-altitude and enhanced survilliance capability
    I   Transponder - Mode S, including aircraft identification,
         but no pressure-altitude capability
    L   Transponder - Mode S, including aircraft identification,
         pressure-altitude, extend squitter (ADS-B) and enhanced
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
  defp alr_tooltip("departure_time"), do: "Time\n\n4 NUMERICS giving\n\nthe estimated off-block time at the aerodrome in (a) in FPL and DLA messages transmitted before departure and in RQP message, if known, or\n\nthe actual time of departure from the aerodrome in (a) in ALR, DEP and SPL messages, or\n\nthe actual or estimated time of departure from the first point shown in the Route Field (see Field Type 15) in FPL messages derived from flight plans filed in the air, as shown by the letters AFIL in (a)."
  defp alr_tooltip("speed"), do: "Cruising speed (maximum 5 characters)\n\nINSERT the True Air Speed for the first or the whole cruising portion of the flight, in terms of:\n- Kilometres per hour, expressed as K followed by 4 figures\n  (e.g. K0830), or\n- Knots, expressed as N followed by 4 figures (e.g. N0485), or\n- True Mach number, when so prescribed by the appropriate\n  ATS authority, to the nearest hundredth of unit Mach,\n  expressed as M followed by 3 figures (e.g M082)."
  defp alr_tooltip("level"), do: "Cruising level (maximum 5 characters)\n\nINSERT the planned cruising level for the first or the whole portion of the route to be flown, in terms if:\n- Flight level, expressed as F followed by 3 figures (e.g.\n  F085; F330), or\n- *Standard Metric Level in tens of metres, expressed as\n  S followed by 4 figures (e.g. S1130), or\n- Altitude in hundreds of feet, expressed as A followed by\n  3 figures (e.g. A045; A100), or\n- Altitude in tens of metres, expressed as M followed by 4\n  figures (e.g. M0840), or\n- for uncontrolled VFR flights, the letters VFR.\n\n*When so prescribed by the appropriate ATS authorities."
  defp alr_tooltip("route") do
    """
    Route (including changes of speed, level and/or flight rules)

    Flights along designated ATS routes
    1. ATS route (2 to 7 characters)
      The coded designator assigned to the route or route segment
      including, where appropriate, the coded designator
      assigned to the standard departure or arrival route
      (e.g. BCN1, B1, R14, UB10, KODAP2A)

    2. Significant point (2 to 11 characters)
       The coded designator (2 to 5 chars) assigned to the point
       (e.g. LN, MAY, HADDY), or if no coded designator has been
       assigned, one of the following ways:
       - Degress only (7 chars), e.g. 46N078W
       - Degress and minutes (11 chars), e.g. 4620N07805W
       - Bearing distance from significant point, e.g. DUB180040

    3. Change of speed or level (maximum 21 characters)
       The point at which a change of speed (5% TAS or 0.01 Mach
       or more) or a change of level is planned to commence,
       expressed exactly as in (2) above.
       e.g. LN/N0284A045; MAY/N0305F330; HADDY/
       N0420F330; 4602N07805W/N0500F350; 46N078W/
       M082F330; DUB180040/N0350M0840

    4. Change of flight rules (maximum 3 characters)
       The point at which the change of flight rules is planned,
       expressed exactly as in (2) or (3) above as appropriate,
       followed by a space and one of the following:
       - VFR if from IFR to VFR    - IFR if from VFR to IFR
       e.g. LN VFR; LN/N0284A050 IFR

    5. Cruise climb (maximum 28 characters)
       e.g. C/48N050W/M082F290F350; C/48N050W/
       M082F290PLUS; C/52N050W/M220F580F620;
    """
  end
  defp alr_tooltip("destination"), do: "Destination aerodrome\n\nINSERT the ICAO four-letter location indicator of the destination aerodrome as specfied in Doc 7910, Location Indicators,\n\nOR, if no location indicator has been assigned,\n\nINSERT ZZZZ and SPECIFY in Item 18 the name and location of the aerodrome, preceded by DEST/"
  defp alr_tooltip("eet"), do: "Total Estimated Elapsed Time\n\n4 NUMERICS, giving the total estimated elapsed time."
  defp alr_tooltip("alternate"), do: "Destination alternate aerodrome(s)\n\nINSERT the ICAO four-letter location indicator(s) of not more than two destination alternate aerodromes, as specfied in Doc 7910, Location Indicators, separated by a space,\n\nOR, if no location indicator has been assigned to the destination alternate aerodrome(s),\n\nINSERT ZZZZ and SPECIFY in Item 18 the name and location of the destination alternate aerodrome(s), preceded by ALTN/"
  defp alr_tooltip("second_alternate"), do: alr_tooltip("alternate")

  defp alr_tooltip("sts"), do: "Reason for special handling by ATS, e.g. a search and rescue mission, as follows:\n\nALTRV:\tfor a flight operated in accordance with an altitude reservation;\nATFMX:\tfor a flight approved for exemption from ATFM measures by the appropriate ATS authority;\nFFR:\tfire-fighting;\nFLTCK:\tflight check for calibration of navaids;\nHAZMAT:for a flight carrying hazardous material;\nHEAD:\ta flight with Head of State status;\nHOSP:\tfor medical flight declared by medical authorities;\nHUM:\tfor a flight operating on a humanitarian mission;\nMARSA:for a flight for which a military entity assumes responsibility for separation of military aircraft;\nMEDEVAC: for a life critical medical emergency evacuation;\nNONRVSM: for non-RVSM capable flight intending to operate in RVSM airspace;\nSAR:\tfor a flight engaged in a search and rescue mission;\nSTATE:\tfor a flight engaged in military, customs or police services."
  defp alr_tooltip("pbn"), do: "Indication of RNAV and/or RNP capabilities. Include as many of the descriptors below, as apply to the flight, up to maximum 8 entries, i.e. a total of not more than 16 characters.\n\nRNAV SPECIFICATION\nA1: RNAV 10 (RNP 10)\tC1: RNAV 2 all permitted sensors\nB1: RNAV 5 all permitted sensors\tC2: RNAV 2 GNSS\nC3: RNAV 2 DME/DME\nB2: RNAV 5 GNSS\tC4: RNAV 2 DME/DME/IRU\nB3: RNAV 5 DME/DME\tD1: RNAV 1 all permitted sensors\nB4: RNAV 5 VOR/DME\tD2: RNAV 1 GNSS\nB5: RNAV 5 INS or IRS\tD3: RNAV 1 DME/DME\nB6: RNAV 5 LORANC\tD4: RNAV 1 DME/DME/IRU\n\nRNP SPECIFICATION\nL1: RNP 4\tS1: RNP APCH\nO1: Basic RNP 1 all permitted sensors\tS2: RNP APCH with BAR-VNAV\nO2: Basic RNP 1 GNSS\tT1: RNP AR APCH with RF (special auth. req.)\nO3: Basic RNP 1 DME/DME\tT2: RNP AR APCH without RF (special auth. req.)\nO4: Basic RNP 1 DME/DME/IRU\n\nCombinations of alphanumeric characters not indicated above are reserved.\n\n- If B1, B2, C1, C2, D1, D2, O1 or O2 are filed, then a 'G' must be included in Field 10a\n- If B1, B3, C1, C3, D1, D3, O1 or O3 are filed, then a 'D' must be included in Field 10a\n- If B1 or B4 is filed, then an 'O' or 'S' and a 'D' must be included in Field 10a (i.e., 'OD' or 'SD' must appear in 10a)\n- If B1, B5 or C1 are filed, then an 'I' must be included in Field 10a; and\n- If C1, C4, D1, D4, O1 or O4 are filed, then a 'D' and an 'I' must be included in Field 10a (i.e., 'DI' must appear in 10a)"
  defp alr_tooltip("nav"), do: "Significant data related to navigation equipment, other than specified in PBN/, as required by the appropriate ATS authority. Indicate GNSS augmentation under this indicator, with a space between two or more methods of augmentation, e.g. NAV/GBAS SBAS."
  defp alr_tooltip("com"), do: "Indicate communications applications or capabilities not specified in Item 10a."
  defp alr_tooltip("dat"), do: "Indicate data applications or capabilities not specified in Item 10a."
  defp alr_tooltip("sur"), do: "Include surveillance applications or capabilities not specified in Item 10b."
  defp alr_tooltip("dep_info"), do: "Name and location of departure aerodrome, if ZZZZ is inserted in Item 13, or the ATS unit from which supplementary flight plan data can be obtained, if AFIL is inserted in Item 13. For aerodromes not listed in the relevant AIP, indicate location as follows:\n\nWith 4 figures describing latitude in degrees and tens and units of minutes followed by 'N' (North) or 'S' (South), followed by 5 figures describing longitude in degrees and tens and units of minutes, followed by 'E' (East) or 'W' (West). Make up the correct number of figures, where necessary, by insertion of zeros, e.g. 4620N07805W (11 characters).\n\nOR, Bearing and distance from the nearest significant point, as follows:\n\nThe identification of the significant point followed by the bearing from the point in the form of 3 figures giving degrees magnetic, followed by the distance from the point in the form of 3 figures expressing nautical miles. In areas of high latitude where it is determined by the appropriate authority that reference to degrees magnetic is impractical, degrees true may be used. Make up the correct number of figures, where necessary, by insertion of zeros, e.g. a point of 180 degrees magnetic at a distance of 40 nautical miles from VOR 'DUB' should be expressed as DUB180040.\n\nOR, The first point of the route (name or LAT/LONG) or the marker radio beacon, if the aircraft has not taken off from an aerodrome."
  defp alr_tooltip("dest_info"), do: "Name and location of destination aerodrome, if ZZZZ is inserted in Item 16. For aerodromes not listed in the relevant AIP, indicate location in LAT/LONG or bearing and distance from the nearest significant point, as described under DEP/."
  defp alr_tooltip("dof"), do: "The date of flight departure in six figure format (YYMMDD, where YY equals the year, MM equals the month and DD equals the day)."
  defp alr_tooltip("reg"), do: "The nationality or common mark and registration mark of the aircraft, if different from the aircraft identification in field AIRCRAFT ID."
  defp alr_tooltip("eet_detail"), do: "Significant point of FIR boundary designators and accumulated estimated elapsed times from take-off to such points or FIR boundaries, when so prescribed on the basis of regional air navigation agreements, or by the appropriate ATS authority.\n\ne.g., EET/CAP0745 XYZ080 or EET/EINN0204"
  defp alr_tooltip("sel"), do: "SELCAL Code, for aircraft so equipped. A single string of four letters."
  defp alr_tooltip("typ"), do: "Type(s) of aircraft, preceded if necessary without a space by number(s) of aircraft and separated by one space, if ZZZZ is inserted in Item 9.\n\ne.g., TYP/2F15 5F5 3B2"
  defp alr_tooltip("code"), do: "Aircraft address (expressed in the form of an alphanumerical code of six hexadecimal characters) when required by the appropriate ATS authority. Example: 'F00001' is the lowest aircraft address contained in the specific block administered by ICAO."
  defp alr_tooltip("dle"), do: "Enroute delay or holding, insert the significant point(s) on the route where a delay is planned to occur, followed by the length of delay using four figure time in hours and minutes (hhmm).\n\ne.g., DLE/MDG0030"
  defp alr_tooltip("opr"), do: "ICAO designator or name of the aircraft operating agency, if different from the aircraft identification in item 7."
  defp alr_tooltip("orgn"), do: "The originator's 8 letter AFTN address or other appropriate contact details, in cases where the originator of the flight plan may not be readily identified, as required by the appropriate ATS authority."
  defp alr_tooltip("per"), do: "Aircraft performance data, indicated by a single letter, as shown below:\n* Cat. A : < 169 km/h (91 kt) indicated airspeed (IAS)\n* Cat. B : 169 km/h (91 kt) or more but < 224 km/h (121 kt)\n* Cat. C : 224 km/h (121 kt) or more but < 261 km/h (141 kt)\n* Cat. D : 261 km/h (141 kt) or more but < 307 km/h (166 kt)\n* Cat. E : 307 km/h (166 kt) or more but < 391 km/h (211 kt)\n* Cat. H : Specific procedures for Helicopters"
  defp alr_tooltip("altn_detail"), do: "Name of destination alternate aerodrome(s), if ZZZZ is inserted in Item 16. For aerodromes not listed in the relevant AIP, indicate location in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/."
  defp alr_tooltip("ralt"), do: "ICAO four letter indicator(s) for en-route alternate(s), as specified in Doc 7910, Location Indicators, or name(s) of en-route alternate aerodrome(s), if no indicator is allocated. For aerodromes not listed in the relevant AIP, indicate located in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/."
  defp alr_tooltip("talt"), do: "ICAO four letter indicator(s) for take-off alternate, as specified in Doc 7910, Location Indicators, or name of take-off alternate aerodrome, if no indicator is allocated. For aerodromes not listed in the relevant AIP, indicate located in LAT/LONG or bearing and distance from the nearest significant point, as described in DEP/."
  defp alr_tooltip("rif"), do: "The route details to the revised destination aerodrome, following by the ICAO four-letter location indicator of the aerodrome. The revised route is subject to reclearance in flight.\n\ne.g., RIF/DTA HEC KLAX or RIF/ESP G94 CLA YPPH"
  defp alr_tooltip("rmk"), do: "Other reasons for special handling by ATS. Any other plain language remarks when required by the appropriate ATS authority or deemed necessary."

  defp alr_tooltip("endurance"), do: "4 NUMERICS giving the fuel endurance in hours and minutes."
  defp alr_tooltip("pob"), do: "1, 2 or 3 NUMERICS giving the total number of persons on board, when so prescribed by the appropriate ATS authority."
  defp alr_tooltip("emergency_radio"), do: "Emergency Radio\n\nIndicate available emergency radio equipment: UHF 243.0 MHz, VHF 121.5 MHz, and/or ELT."
  defp alr_tooltip("radio_uhf"), do: "Emergency Radio UHF\n\nIf frequency 243.0 MHz (UHF) is available."
  defp alr_tooltip("radio_vhf"), do: "Emergency Radio VHF\n\nIf frequency 121.5 MHz (VHF) is available."
  defp alr_tooltip("radio_elt"), do: "If emergency location beacon-aircraft (ELBA) is available."
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
  defp alr_tooltip("aircraft_colour"), do: "The colour of the aircraft.\nSignificant markings (this may include the aircraft registration)."
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
    #{alr_equipment_modal("10a", "Equipment and Capabilities (Item 10a)", alr_10a_groups(), [
      "Any alphanumeric characters not indicated above are reserved."
    ])}
    #{alr_equipment_modal("10b", "Surveillance Equipment and Capabilities (Item 10b)", alr_10b_groups(), [
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
          <button type="button" class="aftn-tool discard js-equipment-clear"><i class="bi bi-arrow-counterclockwise"></i><span>Clear</span></button>
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
        {"B", "LPV (APV with SBAS)"},
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
        {"J5", "CPDLC FANS 1/A SATCOM (INMARSAT)"},
        {"J6", "CPDLC FANS 1/A SATCOM (MTSAT)"},
        {"J7", "CPDLC FANS 1/A SATCOM (Iridium)"},
        {"K", "MLS"},
        {"L", "ILS"},
        {"M1", "ATC RTF SATCOM (INMARSAT)"},
        {"M2", "ATC RTF (MTSAT)"},
        {"M3", "ATC RTF (Iridium)"},
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
        {"A", "Transponder - Mode A (4 digits - 4096 codes)"},
        {"C", "Transponder - Mode A (4 digits - 4096 codes) and Mode C"}
      ]},
      {"SSR Mode S", [
        {"E", "Transponder - Mode S, including aircraft identification, pressure-altitude and extended squitter (ADS-B) capability"},
        {"H", "Transponder - Mode S, including aircraft identification, pressure-altitude and enhanced surveillance capability"},
        {"I", "Transponder - Mode S, including aircraft identification, but no pressure-altitude capability"},
        {"L", "Transponder - Mode S, including aircraft identification, pressure-altitude, extended squitter (ADS-B) and enhanced surveillance capability"},
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
        {"A1", "RNAV 10 (RNP 10)"},
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
        {"T1", "RNP AR APCH with RF (special authorization required)"},
        {"T2", "RNP AR APCH without RF (special authorization required)"}
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
          <button class="amo-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-check"></i><span>Send</span></button>
          <button class="amo-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save2"></i><span>Save</span></button>
          <button class="amo-tool discard js-clear-form" type="button"><i class="bi bi-arrow-counterclockwise"></i><span>Clear</span></button>
          <a class="amo-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <div class="amo-body">
          <div class="grid2">
            <div class="field">
              <label>CID Seq</label>
              <input name="cid_seq" value="#{html(current_cid_seq())}">
            </div>
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
          <button class="amo-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-check"></i><span>Send</span></button>
          <button class="amo-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save2"></i><span>Save</span></button>
          <button class="amo-tool discard js-clear-form" type="button"><i class="bi bi-arrow-counterclockwise"></i><span>Clear</span></button>
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
    settings = settings || Tp.Settings.default_setting()
    link = Tp.LinkMonitor.status()
    link_up? = link.state == :up
    link_card = if link_up?, do: "ok", else: "warn"
    link_state = if link_up?, do: "Established", else: "Down"
    link_pill = if link_up?, do: "up", else: "down"
    queue_count = safe_queue_count()
    queue_card = if is_integer(queue_count) and queue_count > 0, do: "warn", else: "ok"

    """
    <footer class="status-footer">
      <div class="status-panel">
        <div class="status-summary">
          <div class="status-card #{link_card}">
            <div class="status-label">Link</div>
            <div class="status-value"><span class="status-pill #{link_pill}"><span class="status-dot"></span>#{html(link_state)}</span></div>
          </div>
          <div class="status-card info">
            <div class="status-label">UDP</div>
            <div class="status-value">UDP</div>
          </div>
          <div class="status-card info">
            <div class="status-label">Tseq</div>
            <div class="status-value">#{html(settings.tseq)}</div>
          </div>
          <div class="status-card #{queue_card}">
            <div class="status-label">Queue</div>
            <div id="status-queue-count" class="status-value">#{html(queue_count)}</div>
          </div>
          <div class="status-card">
            <div class="status-label">CID</div>
            <div class="status-value">#{html(settings.cid)}</div>
          </div>
          <div class="status-card">
            <div class="status-label">Digit Seq</div>
            <div class="status-value">#{html(settings.digit_seq)}</div>
          </div>
          <div class="status-card">
            <div class="status-label">Free</div>
            <div class="status-value">43G</div>
          </div>
          <div class="status-card info wide">
            <div class="status-label">Local</div>
            <div class="status-value">0.0.0.0:#{html(local_udp_port())}</div>
          </div>
          <div class="status-card info wide">
            <div class="status-label">Destination</div>
            <div class="status-value">#{html(settings.destination_ip_address)}:#{html(settings.port)}</div>
          </div>
          <div class="status-card #{link_card}">
            <div class="status-label">Check</div>
            <div class="status-value">#{html(link.reason)}</div>
          </div>
          <div class="status-actions"><a href="/settings">Setting</a></div>
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
        <span>#{html(udp_source(item))}</span>
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
        <div class="grid2">
          #{input("transmission_id", "TX ID", "ACR0016")}
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
      <td class="table-actions">#{action_menu(id)}</td>
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

  defp action_menu(id) do
    """
    <div class="action-menu">
      <button class="btn-small icon-action js-action-toggle" type="button" title="Action" aria-label="Action menu">#{kebab_icon()}</button>
      <div class="action-popup">
        <button class="action-item js-show-message" type="button" data-message-id="#{id}" onclick="return showMessagePopupFromButton(this)">#{eye_icon()}<span>View</span></button>
        <a class="action-item" href="/messages/#{id}/pdf" target="_blank">#{pdf_icon()}<span>PDF</span></a>
        <form class="inline-form" method="post" action="/messages/#{id}/delete" onsubmit="return confirm('Hapus message ini?')">
          <input class="js-return-to" type="hidden" name="return_to" value="/">
          <button class="action-item danger" type="submit">#{trash_icon()}<span>Delete</span></button>
        </form>
      </div>
    </div>
    """
  end

  defp filter_form(filters) do
    direction = Keyword.get(filters, :direction, "")
    type = Keyword.get(filters, :type, "")
    q = Keyword.get(filters, :q, "")
    date_from = Keyword.get(filters, :date_from, "")
    date_to = Keyword.get(filters, :date_to, "")
    page_size = Keyword.get(filters, :page_size, "15")

    """
    <form class="filters" method="get" action="/">
      <input type="hidden" name="page" value="1">
      <input type="hidden" name="page_size" value="#{html(page_size)}">
      <input name="q" value="#{html(q)}" placeholder="Search CID, seq, message">
      <select name="direction">
        #{option("", "All IO", direction)}
        #{option("inbound", "Inbound", direction)}
        #{option("outbound", "Outbound", direction)}
      </select>
      <select name="type">
        #{option("", "All Type", type)}
        #{option("FREE", "FREE", type)}
        #{option("FPL", "FPL", type)}
        #{option("DLA", "DLA", type)}
        #{option("CHG", "CHG", type)}
        #{option("CNL", "CNL", type)}
        #{option("DEP", "DEP", type)}
        #{option("ARR", "ARR", type)}
      </select>
      <input type="date" name="date_from" value="#{html(date_from)}">
      <input type="date" name="date_to" value="#{html(date_to)}">
      <button type="submit">Filter</button>
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
    (Map.get(lists, :wakes, []) ++ ["H", "M", "L", "J", "Y"])
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
    """
    <div class="field">
      <label>#{html(label)}</label>
      <input name="#{html(name)}" value="#{html(value)}">
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
        <p class="actions"><button type="submit" name="compose_action" value="send">Send #{html(type)}</button> <button type="button" class="js-clear-form">Clear</button></p>
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
    <div class="grid4">
      #{input("transmission_id", "TX ID", "ACR0016")}
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
        <div class="aftn-toolbar">
          <button class="aftn-tool primary" type="submit" name="compose_action" value="send"><i class="bi bi-send-check"></i><span>Send</span></button>
          <button class="aftn-tool save" type="submit" name="compose_action" value="save"><i class="bi bi-save2"></i><span>Save</span></button>
          <button class="aftn-tool discard js-clear-form" type="button"><i class="bi bi-arrow-counterclockwise"></i><span>Clear</span></button>
          <a class="aftn-tool close" href="/"><i class="bi bi-x-lg"></i><span>Close</span></a>
        </div>
        <div class="aftn-body">
          <div class="aftn-required-note">Blue field indicates required field.</div>
          <div class="aftn-topline">
            <div class="field required">
              <label>Send At</label>
              <div class="time-control">
                <input id="aftn-send-at" name="transmission_id" value="#{current_filing_time()}">
                <button class="time-button js-current-time" type="button" data-target="aftn-send-at" title="Use current DDHHMM" aria-label="Use current DDHHMM"><i class="bi bi-clock-history"></i></button>
              </div>
            </div>
          </div>
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
              <div class="field required"><label>Originator</label><input name="originator" value="#{html(originator)}"></div>
              <div class="field"><label>Originator's Reference</label><input name="originator_reference" value=""></div>
              <label class="aftn-bell"><input type="checkbox" name="bell" value="1"> Bell</label>
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

  defp rcf_form do
    ats_form(
      "RCF",
      "Radio Communication Failure",
      """
      <div class="subhead">ATS Body</div>
      <div class="grid2">
        #{input("aircraft_id", "7a Aircraft ID", "GIA120")}
        #{input("radio_failure", "21 Radio Failure", "WIII0330 WAAA")}
      </div>
      """
    )
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
      (function () {
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
          for (var i = 0; i < text.length; i++) {
            var code = text.charCodeAt(i);
            if (code === 1) out += '[SOH]';
            else if (code === 2) out += '[STX]';
            else if (code === 3) out += '[ETX]';
            else if (code === 11) out += '[VT]';
            else if (code === 13) out += String.fromCharCode(10);
            else if (code === 10 && i > 0 && text.charCodeAt(i - 1) === 13) continue;
            else out += text.charAt(i);
          }
          return out;
        }

        function visibleMonitorUdp(value) {
          var text = String(value == null ? '' : value);
          var out = '';
          for (var i = 0; i < text.length; i++) {
            var code = text.charCodeAt(i);
            if (code === 1 || code === 2 || code === 3 || code === 11) continue;
            if (code === 13) out += String.fromCharCode(10);
            else if (code === 10 && i > 0 && text.charCodeAt(i - 1) === 13) continue;
            else out += text.charAt(i);
          }
          return out;
        }

        function messagePreview(value, limit) {
          var text = visibleAftn(value || '').trim();
          limit = limit || 45;
          var lines = text.split('\\n');
          if (lines.length > 2) return {text: lines.slice(0, 2).join('\\n') + '...', hasMore: true};
          if (text.length > limit) return {text: text.slice(0, limit) + '...', hasMore: true};
          return {text: text, hasMore: false};
        }

        function composeMessageText(value) {
          var text = String(value == null ? '' : value);
          var stx = text.indexOf(String.fromCharCode(2));
          if (stx >= 0) {
            text = text.slice(stx + 1);
            var etx = text.indexOf(String.fromCharCode(3));
            if (etx >= 0) text = text.slice(0, etx);
          }
          return visibleMonitorUdp(text).trim();
        }

        function composeAddresses(value) {
          if (Array.isArray(value)) return value.filter(Boolean).join(' ');
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

        function updateHeaderTime() {
          var node = byId('header-time');
          if (!node) return;
          var now = new Date();
          var pad = function (value) { value = String(value); return value.length < 2 ? '0' + value : value; };
          node.textContent = now.getFullYear() + '-' + pad(now.getMonth() + 1) + '-' + pad(now.getDate()) + ' ' + pad(now.getHours()) + ':' + pad(now.getMinutes()) + ':' + pad(now.getSeconds());
        }

        function monitorSource(item) {
          var ip = item && item.source_ip ? item.source_ip : '';
          var port = item && item.source_port ? item.source_port : '';
          if (ip && port) return ip + ':' + port;
          return ip || '';
        }

        function monitorRow(item) {
          return '<div class="udp-item">' +
            '<div class="udp-meta">' +
              '<span>' + escapeHtml(formatMonitorTime(item.time)) + '</span>' +
              '<span>' + escapeHtml(item.kind || 'UDP') + '</span>' +
              '<span>' + escapeHtml(monitorSource(item)) + '</span>' +
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
                '<button class="btn-small icon-action js-action-toggle" type="button" title="Action" aria-label="Action menu">' + kebabIcon() + '</button>' +
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
          for (var i = 0; i < menus.length; i++) {
            if (!except || menus[i] !== except) menus[i].classList.remove('open');
          }
        }

        function syncReturnToFields() {
          var value = window.location.pathname + window.location.search;
          var fields = document.querySelectorAll ? document.querySelectorAll('.js-return-to') : [];
          for (var i = 0; i < fields.length; i++) fields[i].value = value;
        }

        function interactiveTarget(target) {
          return target && target.closest && target.closest('button, a, input, select, textarea, form, .action-menu, .read-more');
        }

        function selectMessageRow(row) {
          if (!row || !row.classList) return;
          var selected = document.querySelectorAll ? document.querySelectorAll('tr.row-selected') : [];
          for (var i = 0; i < selected.length; i++) {
            if (selected[i] !== row) selected[i].classList.remove('row-selected');
          }
          row.classList.add('row-selected');
        }

        function openAftnFreeFromRow(row) {
          if (!row) return;
          var params = new URLSearchParams();
          params.set('form', 'AFTN_FREE');
          params.set('priority', row.getAttribute('data-compose-priority') || '');
          params.set('originator', row.getAttribute('data-compose-originator') || '');
          params.set('message', row.getAttribute('data-compose-message') || '');
          var addresses = String(row.getAttribute('data-compose-addresses') || '').trim().split(/\s+/);
          for (var i = 0; i < addresses.length && i < 21; i++) {
            if (addresses[i]) params.set('address_' + (i + 1), addresses[i]);
          }
          window.location.href = '/compose?' + params.toString();
        }

        function initLatestMessageId() {
          var body = byId('message-table-body');
          if (!body || !body.querySelectorAll) return;

          var rows = body.querySelectorAll('button[data-message-id]');
          for (var i = 0; i < rows.length; i++) {
            var id = parseInt(rows[i].getAttribute('data-message-id'), 10);
            if (!isNaN(id) && id > latestMessageId) latestMessageId = id;
          }
        }

        function messagesApiUrl() {
          var params = new URLSearchParams(window.location.search || '');
          params.set('page', '1');
          params.set('page_size', (byId('message-table-body') && byId('message-table-body').getAttribute('data-page-size')) || '15');
          params.delete('after_id');
          params.delete('after_ts');
          params.delete('history');
          return '/api/messages/recent?' + params.toString();
        }

        function pollMessages() {
          var body = byId('message-table-body');
          if (!body) return;

          fetch(messagesApiUrl(), {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin'
          })
            .then(function (response) {
              if (!response.ok) throw new Error('messages failed');
              return response.json();
            })
            .then(function (payload) {
              var rows = payload && payload.data ? payload.data : [];
              var fresh = [];

              for (var i = 0; i < rows.length; i++) {
                var id = parseInt(rows[i].id, 10);
                if (!isNaN(id) && id > latestMessageId && !body.querySelector('[data-message-id="' + id + '"]')) fresh.push(rows[i]);
              }

              if (!fresh.length) return;
              var empty = body.querySelector('.empty-row');
              if (empty) empty.parentNode.removeChild(empty);

              for (var index = fresh.length - 1; index >= 0; index--) {
                body.insertAdjacentHTML('afterbegin', messageRow(fresh[index]));
                var freshId = parseInt(fresh[index].id, 10);
                if (!isNaN(freshId) && freshId > latestMessageId) latestMessageId = freshId;
              }

              var limit = parseInt(body.getAttribute('data-page-size') || '15', 10);
              var tableRows = body.querySelectorAll ? body.querySelectorAll('tr') : [];
              for (var rowIndex = limit; rowIndex < tableRows.length; rowIndex++) tableRows[rowIndex].parentNode.removeChild(tableRows[rowIndex]);
            })
            .catch(function () {});
        }

        function pollUdpMonitor() {
          var body = byId('udp-monitor-body');
          if (!body) return;

          fetch('/api/udp-monitor', {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin'
          })
            .then(function (response) {
              if (!response.ok) throw new Error('monitor failed');
              return response.json();
            })
            .then(function (payload) {
              var rows = payload && payload.data ? payload.data : [];
              if (!rows.length) return;

              var html = '';
              for (var i = rows.length - 1; i >= 0; i--) html += monitorRow(rows[i]);

              if (body.querySelector && body.querySelector('.udp-empty')) body.innerHTML = '';
              body.insertAdjacentHTML('afterbegin', html);

              var items = body.querySelectorAll ? body.querySelectorAll('.udp-item') : [];
              for (var index = 10; index < items.length; index++) items[index].parentNode.removeChild(items[index]);
            })
            .catch(function () {});
        }

        function pollStatus() {
          var queue = byId('status-queue-count');
          if (!queue) return;

          fetch('/api/status', {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin'
          })
            .then(function (response) {
              if (!response.ok) throw new Error('status failed');
              return response.json();
            })
            .then(function (payload) {
              if (payload && payload.queue_count != null) queue.textContent = payload.queue_count;
            })
            .catch(function () {});
        }

        function openModal() {
          var modal = byId('message-modal');
          if (!modal) return;
          if (modal.classList) modal.classList.add('open');
          else if (String(modal.className).indexOf('open') < 0) modal.className += ' open';
        }

        function closeModal() {
          var modal = byId('message-modal');
          if (!modal) return;
          if (modal.classList) modal.classList.remove('open');
          else modal.className = String(modal.className).split('open').join('');
        }

        function renderFromTemplate(id) {
          var body = byId('message-modal-body');
          var title = byId('message-modal-title');
          var template = byId('message-detail-' + id);
          if (!body || !title || !template) return false;
          title.textContent = 'Message #' + id;
          body.innerHTML = template.innerHTML;
          return true;
        }

        function renderFromApi(id) {
          var body = byId('message-modal-body');
          var title = byId('message-modal-title');
          if (!body || !title) return;

          title.textContent = 'Message #' + id;
          body.innerHTML = '<div class="muted">Loading message #' + escapeHtml(id) + '...</div>';

          fetch('/api/messages/' + encodeURIComponent(id), {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'same-origin'
          })
            .then(function (response) {
              if (!response.ok) throw new Error('Message tidak ditemukan / gagal dimuat. HTTP ' + response.status);
              return response.json();
            })
            .then(function (message) {
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
                '</dl>' +
                '<pre class="modal-raw">' + escapeHtml(visibleAftn(message.raw_text)) + '</pre>';
            })
            .catch(function (error) {
              body.innerHTML = '<div class="modal-error">' + escapeHtml(error.message) + '</div>';
            });
        }

        window.showMessagePopup = function (id) {
          if (!id) return false;
          openModal();
          if (!renderFromTemplate(id)) renderFromApi(id);
          return false;
        };

        window.showMessagePopupFromButton = function (button) {
          if (!button) return false;
          return window.showMessagePopup(button.getAttribute('data-message-id'));
        };

        document.addEventListener('click', function (event) {
          var target = event.target;
          var toggle = target && target.closest ? target.closest('.js-action-toggle') : null;

          if (toggle) {
            event.preventDefault();
            var menu = toggle.closest ? toggle.closest('.action-menu') : null;
            if (menu) {
              var willOpen = !(menu.classList && menu.classList.contains('open'));
              closeActionMenus(menu);
              if (willOpen && menu.classList) menu.classList.add('open');
            }
            return false;
          }

          var button = target;

          while (button && button !== document && !(button.className && String(button.className).indexOf('js-show-message') >= 0)) {
            button = button.parentNode;
          }

          if (button && button !== document && button.getAttribute) {
            event.preventDefault();
            closeActionMenus();
            window.showMessagePopupFromButton(button);
            return false;
          }

          var row = target && target.closest ? target.closest('tr[data-message-id]') : null;
          if (row && !interactiveTarget(target)) selectMessageRow(row);

          if (target && (target.id === 'clear-udp-monitor' || (target.closest && target.closest('#clear-udp-monitor')))) {
            event.preventDefault();
            var monitor = byId('udp-monitor-body');
            if (monitor) monitor.innerHTML = udpEmptyHtml();
            return false;
          }

          if (target && target.id === 'message-modal') closeModal();
          if (target && target.id === 'message-modal-close') closeModal();
          if (!(target && target.closest && target.closest('.action-menu'))) closeActionMenus();
        });

        document.addEventListener('keydown', function (event) {
          if (event.key === 'Escape' || event.keyCode === 27) {
            closeModal();
            closeActionMenus();
          }
        });

        document.addEventListener('dblclick', function (event) {
          var target = event.target;
          var row = target && target.closest ? target.closest('tr[data-message-id]') : null;
          if (!row || interactiveTarget(target)) return;
          event.preventDefault();
          selectMessageRow(row);
          openAftnFreeFromRow(row);
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
