defmodule Tp.Warnings do
  import Ecto.Query
  alias Tp.{Repo, WarningMsg}

  @page_size 20

  def list(opts \\ []) do
    msg     = opts |> Keyword.get(:msg, "")     |> to_string() |> String.trim()
    reason  = opts |> Keyword.get(:reason, "")  |> to_string() |> String.trim()
    date_fr = opts |> Keyword.get(:date_fr, "") |> to_string() |> String.trim()
    date_to = opts |> Keyword.get(:date_to, "") |> to_string() |> String.trim()
    page    = opts |> Keyword.get(:page, 1)     |> parse_int(1) |> max(1)

    {fr, to_dt} = build_date_range(date_fr, date_to)

    base        = base_query(msg, reason, fr, to_dt)
    total       = Repo.aggregate(base, :count, :idcnt)
    total_pages = if total > 0, do: ceil(total / @page_size), else: 1
    page        = min(page, total_pages)

    items =
      base
      |> order_by([w], desc: w.idcnt)
      |> limit(@page_size)
      |> offset(^((page - 1) * @page_size))
      |> Repo.all()

    {:ok, items, %{page: page, total_pages: total_pages, total: total}}
  end

  def delete(id) when is_integer(id) do
    case Repo.get(WarningMsg, id) do
      nil -> {:error, :not_found}
      w   -> Repo.delete(w)
    end
  end

  def delete_all(opts \\ []) do
    msg     = opts |> Keyword.get(:msg, "")     |> to_string() |> String.trim()
    reason  = opts |> Keyword.get(:reason, "")  |> to_string() |> String.trim()
    date_fr = opts |> Keyword.get(:date_fr, "") |> to_string() |> String.trim()
    date_to = opts |> Keyword.get(:date_to, "") |> to_string() |> String.trim()

    {fr, to_dt} = build_date_range(date_fr, date_to)
    {count, _} = base_query(msg, reason, fr, to_dt) |> Repo.delete_all()
    {:ok, count}
  end

  defp build_date_range(date_fr, date_to) do
    today = Date.utc_today()
    fr    = parse_date(date_fr) || today
    to    = parse_date(date_to) || today
    {NaiveDateTime.new!(fr, ~T[00:00:00]), NaiveDateTime.new!(to, ~T[23:59:59])}
  end

  defp parse_date(str) when is_binary(str) and byte_size(str) >= 10 do
    case Date.from_iso8601(String.slice(str, 0, 10)) do
      {:ok, d} -> d
      _        -> nil
    end
  end
  defp parse_date(_), do: nil

  defp base_query(msg, reason, fr, to_dt) do
    WarningMsg
    |> where([w], not is_nil(w.tgl))
    |> where([w], w.tgl >= ^fr)
    |> where([w], w.tgl <= ^to_dt)
    |> then(fn q -> if msg    != "", do: where(q, [w], like(w.message, ^"%#{msg}%")),    else: q end)
    |> then(fn q -> if reason != "", do: where(q, [w], like(w.reason,  ^"%#{reason}%")), else: q end)
  end

  defp parse_int(v, _d) when is_integer(v), do: v
  defp parse_int(v, d) do
    case Integer.parse(to_string(v)) do
      {n, _} -> n
      :error -> d
    end
  end
end
