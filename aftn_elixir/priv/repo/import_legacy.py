#!/usr/bin/env python3
import argparse
import csv
import json
import re
from pathlib import Path


AIR_COLUMNS = [
    "direction",
    "priority",
    "destinations",
    "filing_time",
    "originator",
    "message_type",
    "aircraft_id",
    "departure",
    "departure_time",
    "destination",
    "route",
    "raw_text",
    "parsed_fields",
    "status",
    "status_reply",
    "sequence_no",
    "cid",
    "bell",
    "transmit_attempts",
    "last_error",
    "next_attempt_at",
    "udp_target_host",
    "udp_target_port",
    "filed_by",
    "legacy_table",
    "legacy_id",
    "received_at",
    "sent_at",
    "inserted_at",
    "updated_at",
]

TARGET_TABLES = {
    "abbreviations",
    "aircraft_reg",
    "aircraft_wtc",
    "location_indicator",
    "route",
    "incoming_msg",
    "queuelp",
    "warning",
}


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--dump", default="../../database/tp.sql")
    parser.add_argument("--out-dir", default="/tmp/tp_legacy_import")
    args = parser.parse_args()

    dump = Path(args.dump).resolve()
    out_dir = Path(args.out_dir).resolve()
    out_dir.mkdir(parents=True, exist_ok=True)

    schema = parse_schema(dump)
    writers, files = open_writers(out_dir)
    counts = {name: 0 for name in files}

    try:
        with dump.open("r", encoding="utf-8", errors="replace") as handle:
            for line in handle:
                parsed = parse_insert(line)
                if not parsed:
                    continue

                table, values = parsed
                if table.startswith("air_message"):
                    row = map_air_message(table, schema[table], values)
                    write_row(writers["air_messages"], row)
                    counts["air_messages"] += 1
                elif table in TARGET_TABLES:
                    mapper = TABLE_MAPPERS[table]
                    row = mapper(schema[table], values)
                    write_row(writers[table], row)
                    counts[table] += 1
    finally:
        for file_handle in files.values():
            file_handle.close()

    sql_path = write_sql(out_dir)
    print(f"Generated import files in {out_dir}")
    print(f"Generated SQL: {sql_path}")
    for table, count in sorted(counts.items()):
        print(f"{table}: {count}")


def parse_schema(path):
    schema = {}
    current_table = None

    with path.open("r", encoding="utf-8", errors="replace") as handle:
        for raw in handle:
            line = raw.strip()
            match = re.match(r"CREATE TABLE `([^`]+)`", line)
            if match:
                current_table = match.group(1)
                schema[current_table] = []
                continue

            if current_table and line.startswith("`"):
                column = line.split("`", 2)[1]
                schema[current_table].append(column)
                continue

            if current_table and line.startswith(")"):
                current_table = None

    return schema


def write_row(writer, row):
    writer.writerow(["\\N" if value is None else value for value in row])


def open_writers(out_dir):
    specs = {
        "air_messages": AIR_COLUMNS,
        "abbreviations": ["abbr", "mean"],
        "aircraft_reg": ["registration", "aircraft_type", "operator", "raw_data"],
        "aircraft_wtc": ["aircraft_type", "wake_turbulence_category", "raw_data"],
        "location_indicator": ["indicator", "name", "raw_data"],
        "route": ["name", "description", "raw_data"],
        "incoming_msg": ["raw_text", "source", "processed", "inserted_at", "updated_at"],
        "queuelp": ["data", "status", "printed_at", "inserted_at", "updated_at"],
        "warning": ["message", "severity", "acknowledged_at", "inserted_at", "updated_at"],
    }

    writers = {}
    files = {}
    for table, columns in specs.items():
        path = out_dir / f"{table}.csv"
        file_handle = path.open("w", newline="", encoding="utf-8")
        files[table] = file_handle
        writers[table] = csv.writer(file_handle)
    return writers, files


def parse_insert(line):
    match = re.match(r"INSERT INTO `([^`]+)` VALUES \((.*)\);\s*$", line)
    if not match:
        return None
    return match.group(1), parse_values(match.group(2))


def parse_values(raw):
    values = []
    index = 0
    length = len(raw)

    while index < length:
        while index < length and raw[index].isspace():
            index += 1

        if raw[index] == "'":
            value, index = parse_string(raw, index + 1)
            values.append(value)
        else:
            start = index
            while index < length and raw[index] != ",":
                index += 1
            token = raw[start:index].strip()
            values.append(None if token.lower() == "null" else token)

        if index < length and raw[index] == ",":
            index += 1

    return values


def parse_string(raw, index):
    chars = []
    length = len(raw)

    while index < length:
        char = raw[index]
        if char == "\\" and index + 1 < length:
            nxt = raw[index + 1]
            chars.append({
                "0": "\0",
                "n": "\n",
                "r": "\r",
                "t": "\t",
                "b": "\b",
                "Z": "\x1a",
                "\\": "\\",
                "'": "'",
                '"': '"',
            }.get(nxt, nxt))
            index += 2
            continue

        if char == "'":
            return "".join(chars), index + 1

        chars.append(char)
        index += 1

    raise ValueError("Unterminated SQL string")


def map_air_message(table, columns, values):
    data = dict(zip(columns, values))
    direction = "outbound" if data.get("io") == "1" else "inbound"
    destinations = [data.get(f"destination{i}") for i in range(1, 22)]
    destinations = [dest for dest in destinations if dest]
    timestamp = clean_timestamp(data.get("tgl") or data.get("time_control"))
    raw_text = data.get("msgall") or data.get("freetext") or data.get("type15c") or ""
    message_type = data.get("type3a") or "FREE"
    now = timestamp or "1970-01-01 00:00:00"

    parsed_fields = {
        key: value
        for key, value in data.items()
        if value not in (None, "") and (key.startswith("type") or key in {"priority", "originator", "seq", "cid"})
    }

    return [
        direction,
        data.get("priority"),
        pg_array(destinations),
        data.get("filing_time"),
        data.get("originator"),
        message_type,
        data.get("type7a"),
        data.get("type13a"),
        data.get("type13b"),
        data.get("type16a"),
        data.get("type15c"),
        raw_text,
        json.dumps(parsed_fields, ensure_ascii=False),
        data.get("status"),
        data.get("status_rpl"),
        data.get("seq") or data.get("sequence_no"),
        data.get("cid"),
        "t" if data.get("bell") == "1" else "f",
        0,
        None,
        None,
        None,
        None,
        data.get("filedby") or data.get("filled_by") or "SYSTEM",
        table,
        data.get("id_ats"),
        timestamp if direction == "inbound" else None,
        timestamp if direction == "outbound" else None,
        now,
        now,
    ]


def map_abbreviations(columns, values):
    data = dict(zip(columns, values))
    return [data.get("abbr"), data.get("mean")]


def map_aircraft_reg(columns, values):
    data = dict(zip(columns, values))
    return [
        data.get("type18"),
        data.get("type9b"),
        data.get("type18Opr"),
        json.dumps(data, ensure_ascii=False),
    ]


def map_aircraft_wtc(columns, values):
    data = dict(zip(columns, values))
    return [
        data.get("type9b"),
        data.get("type9c"),
        json.dumps(data, ensure_ascii=False),
    ]


def map_location_indicator(columns, values):
    data = dict(zip(columns, values))
    return [
        data.get("indicator"),
        data.get("location"),
        json.dumps(data, ensure_ascii=False),
    ]


def map_route(columns, values):
    data = dict(zip(columns, values))
    name = f"{data.get('type13a') or ''}-{data.get('type16a') or ''}".strip("-")
    return [
        name,
        data.get("type15c"),
        json.dumps(data, ensure_ascii=False),
    ]


def map_incoming_msg(columns, values):
    data = dict(zip(columns, values))
    timestamp = clean_timestamp(data.get("tgl")) or "1970-01-01 00:00:00"
    raw_text = data.get("message1") or data.get("message") or ""
    source = data.get("originator") or data.get("table_name")
    return [raw_text, source, "f", timestamp, timestamp]


def map_queuelp(columns, values):
    data = dict(zip(columns, values))
    timestamp = clean_timestamp(data.get("tgl")) or "1970-01-01 00:00:00"
    return [data.get("data") or "", "legacy", timestamp, timestamp, timestamp]


def map_warning(columns, values):
    data = dict(zip(columns, values))
    timestamp = clean_timestamp(data.get("tgl")) or "1970-01-01 00:00:00"
    return [data.get("message") or "", data.get("reason"), None, timestamp, timestamp]


TABLE_MAPPERS = {
    "abbreviations": map_abbreviations,
    "aircraft_reg": map_aircraft_reg,
    "aircraft_wtc": map_aircraft_wtc,
    "location_indicator": map_location_indicator,
    "route": map_route,
    "incoming_msg": map_incoming_msg,
    "queuelp": map_queuelp,
    "warning": map_warning,
}


def clean_timestamp(value):
    if not value or value.startswith("0000-00-00"):
        return None
    return value


def pg_array(values):
    if not values:
        return "{}"
    escaped = [str(value).replace("\\", "\\\\").replace('"', '\\"') for value in values]
    return "{" + ",".join(f'"{value}"' for value in escaped) + "}"


def copy_command(table, columns, source=None):
    cols = ", ".join(columns)
    source = source or f"{table}.csv"
    return f"\\copy {table} ({cols}) FROM '{source}' WITH (FORMAT csv, NULL '\\N');"


def write_sql(out_dir):
    sql = out_dir / "import.sql"
    with sql.open("w", encoding="utf-8") as handle:
        handle.write("BEGIN;\n")
        handle.write("TRUNCATE abbreviations, aircraft_reg, aircraft_wtc, location_indicator, route, incoming_msg, queuelp, warning RESTART IDENTITY;\n")
        handle.write(copy_command("abbreviations", ["abbr", "mean"]) + "\n")
        handle.write(copy_command("aircraft_reg", ["registration", "aircraft_type", "operator", "raw_data"]) + "\n")
        handle.write(copy_command("aircraft_wtc", ["aircraft_type", "wake_turbulence_category", "raw_data"]) + "\n")
        handle.write(copy_command("location_indicator", ["indicator", "name", "raw_data"]) + "\n")
        handle.write(copy_command("route", ["name", "description", "raw_data"]) + "\n")
        handle.write(copy_command("incoming_msg", ["raw_text", "source", "processed", "inserted_at", "updated_at"]) + "\n")
        handle.write(copy_command("queuelp", ["data", "status", "printed_at", "inserted_at", "updated_at"]) + "\n")
        handle.write(copy_command("warning", ["message", "severity", "acknowledged_at", "inserted_at", "updated_at"]) + "\n")
        handle.write("CREATE TEMP TABLE legacy_air_messages (LIKE air_messages INCLUDING DEFAULTS);\n")
        handle.write(copy_command("legacy_air_messages", AIR_COLUMNS, "air_messages.csv") + "\n")
        handle.write(f"INSERT INTO air_messages ({', '.join(AIR_COLUMNS)}) SELECT {', '.join(AIR_COLUMNS)} FROM legacy_air_messages ON CONFLICT (legacy_table, legacy_id) WHERE legacy_table IS NOT NULL AND legacy_id IS NOT NULL DO NOTHING;\n")
        handle.write("COMMIT;\n")
    return sql


if __name__ == "__main__":
    main()
