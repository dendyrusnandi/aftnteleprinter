# AFTN Teleprinter Elixir Migration

Prototype migrasi dari aplikasi Java SWT/MySQL `tp` ke aplikasi Elixir web dengan Phoenix/Plug dan MySQL.

Target awal:

- Service OTP 24/7 untuk menerima UDP AFTN/status di port `101`.
- HTTP dashboard sederhana untuk melihat inbox/outbox dan mengirim pesan UDP.
- Database MySQL lama bernama `tp`.
- Skema aplikasi Elixir memakai tabel `air_messages` di database MySQL yang sama, tanpa memindahkan data ke PostgreSQL.

## Menjalankan

Mesin ini belum memiliki `mix`, jadi project ini disiapkan sebagai source Elixir yang siap dijalankan setelah Elixir terpasang.

```bash
cd aftn_elixir
mix deps.get
mix ecto.migrate
mix run --no-halt
```

Konfigurasi runtime penting:

```bash
export MYSQL_HOST=localhost
export MYSQL_DATABASE=tp
export MYSQL_USER=root
export MYSQL_PASSWORD='Elsaelsa1#'
export AFTN_UDP_PORT=101
export AFTN_UDP_TARGET_HOST=127.0.0.1
export AFTN_UDP_TARGET_PORT=101
export HTTP_PORT=4001
```

Dashboard: `http://localhost:4001`

Port receive UDP lokal juga bisa diubah dari menu Settings lewat field `Local Receive Port`. Setelah disimpan, listener UDP akan pindah ke port baru tanpa restart aplikasi.

Catatan port: UDP `101` adalah privileged port. Di production, jalankan release dengan capability:

```bash
sudo setcap 'cap_net_bind_service=+ep' /opt/aftn_elixir/bin/tp
```

Alternatifnya pakai port non-privileged, lalu arahkan trafik UDP 101 ke port tersebut melalui firewall/load balancer.

## Data Lama MySQL

Database lama tetap dipakai langsung dari MySQL `tp`. Dashboard membaca tabel bulanan lama dengan format `air_messageYYYY_MM` seperti `air_message2026_05`; receive UDP inbound juga disimpan ke tabel bulan berjalan. Jika tabel bulan berjalan belum ada, aplikasi membuatnya dengan `CREATE TABLE ... LIKE` dari tabel legacy terbaru.

Contoh query gabungan di MySQL:

```sql
SELECT 'air_message2021_08' AS legacy_table, a.*
FROM air_message2021_08 a;
```

Validasi parser setelah import:

```bash
mix tp.validate_parser
```

## Catatan Arsitektur

- UDP listener memakai `GenServer` dan socket `:gen_udp`, bukan thread manual.
- Semua pesan masuk disimpan mentah di `raw_text`; parser ringan mengisi `message_type`, `originator`, `priority`, `sequence_no`, dan `destinations` bila bisa dibaca.
- Status lama seperti `Tseq`, `Rseq`, `Queu`, `Line`, `Link`, `Conn`, `warning`, `QPrint`, dan `Free` disimpan ke `teleprinter_events`.
- Untuk produksi 24/7 gunakan release + systemd, bukan `mix run`.
