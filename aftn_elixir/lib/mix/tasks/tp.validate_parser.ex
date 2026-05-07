defmodule Mix.Tasks.Tp.ValidateParser do
  use Mix.Task

  import Ecto.Query

  alias Tp.Aftn.Message
  alias Tp.Aftn.Parser
  alias Tp.Repo

  @shortdoc "Validate AFTN parser against migrated legacy fields"

  @impl true
  def run(args) do
    Mix.Task.run("app.start")

    limit = parse_limit(args)

    query =
      from m in Message,
        where: not is_nil(m.legacy_table),
        where: m.message_type in ["FPL", "DLA"],
        where: not is_nil(m.raw_text) and m.raw_text != "",
        order_by: [asc: m.id],
        limit: ^limit

    records = Repo.all(query)
    result = Enum.reduce(records, initial_result(), &validate_record/2)

    Mix.shell().info("Validated #{result.total} structured legacy messages")
    Mix.shell().info("OK: #{result.ok}")
    Mix.shell().info("Mismatched: #{length(result.mismatches)}")

    result.mismatches
    |> Enum.take(20)
    |> Enum.each(&print_mismatch/1)

    if result.mismatches == [] do
      :ok
    else
      Mix.raise("Parser validation found #{length(result.mismatches)} mismatches")
    end
  end

  defp parse_limit(args) do
    case args do
      [value | _] -> String.to_integer(value)
      _ -> 10_000
    end
  end

  defp initial_result, do: %{total: 0, ok: 0, mismatches: []}

  defp validate_record(record, result) do
    parsed = Parser.parse_message(record.raw_text)

    mismatches =
      [:message_type, :aircraft_id, :departure, :departure_time, :destination, :route]
      |> Enum.flat_map(&field_mismatch(record, parsed, &1))

    result = %{result | total: result.total + 1}

    if mismatches == [] do
      %{result | ok: result.ok + 1}
    else
      mismatch = %{id: record.id, legacy: "#{record.legacy_table}.#{record.legacy_id}", mismatches: mismatches}
      %{result | mismatches: [mismatch | result.mismatches]}
    end
  end

  defp field_mismatch(record, parsed, field) do
    expected = normalize(Map.get(record, field))
    actual = normalize(Map.get(parsed, field))

    if expected == actual do
      []
    else
      [%{field: field, expected: expected, actual: actual}]
    end
  end

  defp normalize(nil), do: nil
  defp normalize(""), do: nil
  defp normalize(value) when is_binary(value), do: String.trim(value)
  defp normalize(value), do: value

  defp print_mismatch(mismatch) do
    Mix.shell().info("")
    Mix.shell().info("Message #{mismatch.id} (#{mismatch.legacy})")

    Enum.each(mismatch.mismatches, fn item ->
      Mix.shell().info("  #{item.field}: expected=#{inspect(item.expected)} actual=#{inspect(item.actual)}")
    end)
  end
end

