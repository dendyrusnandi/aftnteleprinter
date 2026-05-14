defmodule Tp.Location do
  import Ecto.Query
  alias Tp.{Repo, LocationIndicator}

  @page_size 15

  def list(opts \\ []) do
    q        = opts |> Keyword.get(:q, "")   |> to_string() |> String.upcase() |> String.trim()
    location = opts |> Keyword.get(:loc, "") |> to_string() |> String.upcase() |> String.trim()
    page     = opts |> Keyword.get(:page, 1) |> parse_int(1) |> max(1)

    base        = base_query(q, location)
    total       = Repo.aggregate(base, :count, :id)
    total_pages = if total > 0, do: ceil(total / @page_size), else: 1
    page        = min(page, total_pages)

    items =
      base
      |> order_by([l], asc: l.indicator)
      |> limit(@page_size)
      |> offset(^((page - 1) * @page_size))
      |> Repo.all()

    {:ok, items, %{page: page, total_pages: total_pages, total: total, page_size: @page_size}}
  end

  def get(id), do: Repo.get(LocationIndicator, id)

  def create(attrs) do
    %LocationIndicator{} |> LocationIndicator.changeset(normalize(attrs)) |> Repo.insert()
  end

  def update(%LocationIndicator{} = record, attrs) do
    record |> LocationIndicator.changeset(normalize(attrs)) |> Repo.update()
  end

  def delete(%LocationIndicator{} = record), do: Repo.delete(record)

  def delete(id) when is_integer(id) do
    case Repo.get(LocationIndicator, id) do
      nil    -> {:error, :not_found}
      record -> Repo.delete(record)
    end
  end

  def delete_all(opts \\ []) do
    q        = opts |> Keyword.get(:q, "")   |> to_string() |> String.upcase() |> String.trim()
    location = opts |> Keyword.get(:loc, "") |> to_string() |> String.upcase() |> String.trim()
    {count, _} = base_query(q, location) |> Repo.delete_all()
    {:ok, count}
  end

  defp base_query(q, location) do
    LocationIndicator
    |> then(fn query ->
      if q != "", do: where(query, [l], like(l.indicator, ^"%#{q}%")), else: query
    end)
    |> then(fn query ->
      if location != "", do: where(query, [l], like(l.location, ^"%#{location}%")), else: query
    end)
  end

  defp normalize(attrs) do
    attrs
    |> Enum.map(fn {k, v} -> {to_string(k), v} end)
    |> Map.new()
    |> Map.update("indicator", "", &(to_string(&1) |> String.upcase() |> String.trim()))
    |> Map.update("location",  "", &(to_string(&1) |> String.upcase() |> String.trim()))
  end

  defp parse_int(v, d) when is_integer(v), do: v
  defp parse_int(v, d) do
    case Integer.parse(to_string(v)) do
      {n, _} -> n
      :error -> d
    end
  end
end
