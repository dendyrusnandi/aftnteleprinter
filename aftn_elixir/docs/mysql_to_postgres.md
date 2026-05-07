# Migrasi MySQL `tp` ke PostgreSQL `tp`

## Perubahan Skema

Java lama membuat tabel per bulan:

- `air_message2018_12`
- `air_message2019_01`
- dan seterusnya

Di Elixir/PostgreSQL, semua data masuk ke `air_messages`. Kolom `legacy_table` dan `legacy_id` menjaga jejak asal data lama.

## Mapping Utama

| MySQL lama | PostgreSQL baru |
| --- | --- |
| `air_messageYYYY_MM.id_ats` | `air_messages.legacy_id` |
| nama tabel `air_messageYYYY_MM` | `air_messages.legacy_table` |
| `io` `0/1` | `direction` `inbound/outbound` |
| `priority` | `priority` |
| `destination1..destination21` | `destinations text[]` |
| `originator` | `originator` |
| `type3a` | `message_type` |
| `type7a` | `aircraft_id` |
| `type13a` | `departure` |
| `type13b` | `departure_time` |
| `type16a` | `destination` |
| `type15c` | `route` |
| `msgall` atau `freetext` | `raw_text` |
| `status` | `status` |
| `status_rpl` | `status_reply` |
| `seq` atau `sequence_no` | `sequence_no` |
| `cid` | `cid` |
| `bell` | `bell` |
| `filedby` | `filed_by` |
| `tgl` | `received_at` atau `sent_at` |

## Query Export Contoh

```sql
SELECT
  'air_message2021_08' AS legacy_table,
  id_ats AS legacy_id,
  CASE io WHEN '1' THEN 'outbound' ELSE 'inbound' END AS direction,
  priority,
  CONCAT_WS('|',
    NULLIF(destination1,''), NULLIF(destination2,''), NULLIF(destination3,''),
    NULLIF(destination4,''), NULLIF(destination5,''), NULLIF(destination6,''),
    NULLIF(destination7,''), NULLIF(destination8,''), NULLIF(destination9,''),
    NULLIF(destination10,''), NULLIF(destination11,''), NULLIF(destination12,''),
    NULLIF(destination13,''), NULLIF(destination14,''), NULLIF(destination15,''),
    NULLIF(destination16,''), NULLIF(destination17,''), NULLIF(destination18,''),
    NULLIF(destination19,''), NULLIF(destination20,''), NULLIF(destination21,'')
  ) AS destinations_pipe,
  filing_time,
  originator,
  COALESCE(NULLIF(type3a,''), 'FREE') AS message_type,
  type7a AS aircraft_id,
  type13a AS departure,
  type13b AS departure_time,
  type16a AS destination,
  type15c AS route,
  COALESCE(NULLIF(msgall,''), freetext, '') AS raw_text,
  status,
  status_rpl AS status_reply,
  COALESCE(NULLIF(seq,''), sequence_no) AS sequence_no,
  cid,
  bell = '1' AS bell,
  filedby AS filed_by,
  tgl
FROM air_message2021_08;
```

Untuk import CSV ke PostgreSQL, ubah `destinations_pipe` menjadi array:

```sql
UPDATE staging_air_messages
SET destinations = string_to_array(destinations_pipe, '|');
```

## Operasi 24/7

Gunakan release dan systemd:

```ini
[Unit]
Description=AFTN Teleprinter Elixir
After=network.target postgresql.service

[Service]
User=aftn
WorkingDirectory=/opt/aftn_elixir
Environment=DATABASE_URL=ecto://aftn:secret@127.0.0.1/tp
Environment=AFTN_UDP_PORT=101
Environment=HTTP_PORT=4001
ExecStart=/opt/aftn_elixir/bin/tp start
Restart=always
RestartSec=5
LimitNOFILE=65535

[Install]
WantedBy=multi-user.target
```
