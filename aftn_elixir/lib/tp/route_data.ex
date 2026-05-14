defmodule Tp.RouteData do
  import Ecto.Query
  alias Tp.{Repo, RouteEntry}

  @page_size 20

  def list(opts \\ []) do
    dep  = opts |> Keyword.get(:dep, "")  |> to_string() |> String.upcase() |> String.trim()
    dest = opts |> Keyword.get(:dest, "") |> to_string() |> String.upcase() |> String.trim()
    page = opts |> Keyword.get(:page, 1)  |> parse_int(1) |> max(1)

    base        = base_query(dep, dest)
    total       = Repo.aggregate(base, :count, :id_number)
    total_pages = if total > 0, do: ceil(total / @page_size), else: 1
    page        = min(page, total_pages)

    items =
      base
      |> order_by([r], desc: r.id_number)
      |> limit(@page_size)
      |> offset(^((page - 1) * @page_size))
      |> Repo.all()

    {:ok, items, %{page: page, total_pages: total_pages, total: total}}
  end

  def get(id), do: Repo.get(RouteEntry, id)

  def create(attrs) do
    %RouteEntry{} |> RouteEntry.changeset(normalize(attrs)) |> Repo.insert()
  end

  def update(%RouteEntry{} = r, attrs) do
    r |> RouteEntry.changeset(normalize(attrs)) |> Repo.update()
  end

  def delete(%RouteEntry{} = r), do: Repo.delete(r)
  def delete(id) when is_integer(id) do
    case Repo.get(RouteEntry, id) do
      nil -> {:error, :not_found}
      r   -> Repo.delete(r)
    end
  end

  def delete_all(opts \\ []) do
    dep  = opts |> Keyword.get(:dep, "")  |> to_string() |> String.upcase() |> String.trim()
    dest = opts |> Keyword.get(:dest, "") |> to_string() |> String.upcase() |> String.trim()
    {count, _} = base_query(dep, dest) |> Repo.delete_all()
    {:ok, count}
  end

  defp base_query(dep, dest) do
    RouteEntry
    |> then(fn q -> if dep  != "", do: where(q, [r], like(r.type13a, ^"%#{dep}%")),  else: q end)
    |> then(fn q -> if dest != "", do: where(q, [r], like(r.type16a, ^"%#{dest}%")), else: q end)
  end

  defp normalize(attrs) do
    up = &(to_string(&1) |> String.upcase() |> String.trim())
    base =
      attrs
      |> Enum.map(fn {k, v} -> {to_string(k), v} end)
      |> Map.new()
      |> Map.update("type13a", "", up)
      |> Map.update("type16a", "", up)
      |> Map.update("type15c", "", &(to_string(&1) |> String.trim()))

    Enum.reduce(1..21, base, fn i, acc ->
      Map.update(acc, "destination#{i}", "", up)
    end)
  end

  defp parse_int(v, d) when is_integer(v), do: v
  defp parse_int(v, d) do
    case Integer.parse(to_string(v)) do
      {n, _} -> n
      :error -> d
    end
  end
end
