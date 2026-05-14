defmodule Tp.Abbreviations do
  import Ecto.Query
  alias Tp.{Repo, IcaoAbbr}

  @page_size 15

  def list(opts \\ []) do
    q    = opts |> Keyword.get(:q, "")    |> to_string() |> String.trim()
    mean = opts |> Keyword.get(:mean, "") |> to_string() |> String.trim()
    page = opts |> Keyword.get(:page, 1)  |> parse_int(1) |> max(1)

    base        = base_query(q, mean)
    total       = Repo.aggregate(base, :count, :id)
    total_pages = if total > 0, do: ceil(total / @page_size), else: 1
    page        = min(page, total_pages)

    items =
      base
      |> order_by([a], asc: a.abbr)
      |> limit(@page_size)
      |> offset(^((page - 1) * @page_size))
      |> Repo.all()

    {:ok, items, %{page: page, total_pages: total_pages, total: total}}
  end

  defp base_query(q, mean) do
    IcaoAbbr
    |> then(fn qry -> if q != "", do: where(qry, [a], like(a.abbr, ^"#{q}%")), else: qry end)
    |> then(fn qry -> if mean != "", do: where(qry, [a], like(a.mean, ^"%#{mean}%")), else: qry end)
  end

  defp parse_int(v, _d) when is_integer(v), do: v
  defp parse_int(v, d) do
    case Integer.parse(to_string(v)) do
      {n, _} -> n
      :error -> d
    end
  end
end
