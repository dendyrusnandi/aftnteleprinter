defmodule Tp.Registration do
  import Ecto.Query
  alias Tp.{Repo, AircraftReg}

  @page_size 15

  def list(opts \\ []) do
    q18  = opts |> Keyword.get(:q18, "")  |> to_string() |> String.upcase() |> String.trim()
    q9b  = opts |> Keyword.get(:q9b, "")  |> to_string() |> String.upcase() |> String.trim()
    page = opts |> Keyword.get(:page, 1)  |> parse_int(1) |> max(1)

    base        = base_query(q18, q9b)
    total       = Repo.aggregate(base, :count, :id)
    total_pages = if total > 0, do: ceil(total / @page_size), else: 1
    page        = min(page, total_pages)

    items =
      base
      |> order_by([r], desc: r.id)
      |> limit(@page_size)
      |> offset(^((page - 1) * @page_size))
      |> Repo.all()

    {:ok, items, %{page: page, total_pages: total_pages, total: total}}
  end

  def get(id), do: Repo.get(AircraftReg, id)

  def create(attrs) do
    %AircraftReg{} |> AircraftReg.changeset(normalize(attrs)) |> Repo.insert()
  end

  def update(%AircraftReg{} = r, attrs) do
    r |> AircraftReg.changeset(normalize(attrs)) |> Repo.update()
  end

  def delete(%AircraftReg{} = r), do: Repo.delete(r)
  def delete(id) when is_integer(id) do
    case Repo.get(AircraftReg, id) do
      nil -> {:error, :not_found}
      r   -> Repo.delete(r)
    end
  end

  def delete_all(opts \\ []) do
    q18 = opts |> Keyword.get(:q18, "") |> to_string() |> String.upcase() |> String.trim()
    q9b = opts |> Keyword.get(:q9b, "") |> to_string() |> String.upcase() |> String.trim()
    {count, _} = base_query(q18, q9b) |> Repo.delete_all()
    {:ok, count}
  end

  defp base_query(q18, q9b) do
    AircraftReg
    |> then(fn q -> if q18 != "", do: where(q, [r], like(r.type18, ^"%#{q18}%")), else: q end)
    |> then(fn q -> if q9b != "", do: where(q, [r], like(r.type9b, ^"%#{q9b}%")), else: q end)
  end

  defp normalize(attrs) do
    up = &(to_string(&1) |> String.upcase() |> String.trim())
    attrs
    |> Enum.map(fn {k, v} -> {to_string(k), v} end)
    |> Map.new()
    |> Map.update("type18",    "", up)
    |> Map.update("type9b",    "", up)
    |> Map.update("type10a",   "", up)
    |> Map.update("type10b",   "", up)
    |> Map.update("type18Opr", "", &(to_string(&1) |> String.trim()))
    |> Map.update("type18Pbn", "", up)
    |> Map.update("type18Sel", "", up)
  end

  defp parse_int(v, d) when is_integer(v), do: v
  defp parse_int(v, d) do
    case Integer.parse(to_string(v)) do
      {n, _} -> n
      :error -> d
    end
  end
end
