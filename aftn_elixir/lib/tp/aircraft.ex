defmodule Tp.Aircraft do
  import Ecto.Query
  alias Tp.{Repo, AircraftWtc}

  @page_size 20

  def list(opts \\ []) do
    q    = opts |> Keyword.get(:q, "")    |> to_string() |> String.upcase() |> String.trim()
    wake = opts |> Keyword.get(:wake, "") |> to_string() |> String.upcase() |> String.trim()
    page = opts |> Keyword.get(:page, 1)  |> parse_int(1) |> max(1)

    base  = base_query(q, wake)
    total = Repo.aggregate(base, :count, :id)

    total_pages = if total > 0, do: ceil(total / @page_size), else: 1
    page        = min(page, total_pages)

    items =
      base
      |> order_by([a], asc: a.type9b)
      |> limit(@page_size)
      |> offset(^((page - 1) * @page_size))
      |> Repo.all()

    {:ok, items, %{page: page, total_pages: total_pages, total: total, page_size: @page_size}}
  end

  def get(id), do: Repo.get(AircraftWtc, id)

  def create(attrs) do
    attrs = normalize(attrs)
    %AircraftWtc{} |> AircraftWtc.changeset(attrs) |> Repo.insert()
  end

  def update(%AircraftWtc{} = record, attrs) do
    attrs = normalize(attrs)
    record |> AircraftWtc.changeset(attrs) |> Repo.update()
  end

  def delete(%AircraftWtc{} = record), do: Repo.delete(record)

  def delete(id) when is_integer(id) do
    case Repo.get(AircraftWtc, id) do
      nil    -> {:error, :not_found}
      record -> Repo.delete(record)
    end
  end

  def delete_all(opts \\ []) do
    q    = opts |> Keyword.get(:q, "")    |> to_string() |> String.upcase() |> String.trim()
    wake = opts |> Keyword.get(:wake, "") |> to_string() |> String.upcase() |> String.trim()

    {count, _} = base_query(q, wake) |> Repo.delete_all()
    {:ok, count}
  end

  defp base_query(q, wake) do
    AircraftWtc
    |> then(fn query ->
      if q != "", do: where(query, [a], like(a.type9b, ^"%#{q}%")), else: query
    end)
    |> then(fn query ->
      if wake != "", do: where(query, [a], a.type9c == ^wake), else: query
    end)
  end

  defp normalize(attrs) do
    attrs
    |> Enum.map(fn {k, v} -> {to_string(k), v} end)
    |> Map.new()
    |> Map.update("type9b", "", &(to_string(&1) |> String.upcase() |> String.trim()))
    |> Map.update("type9c", "", &(to_string(&1) |> String.upcase() |> String.trim()))
  end

  defp parse_int(v, d) when is_integer(v), do: v
  defp parse_int(v, d) do
    case Integer.parse(to_string(v)) do
      {n, _} -> n
      :error -> d
    end
  end
end
