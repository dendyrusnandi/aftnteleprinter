defmodule Tp.Aftn.DestinationList do
  use Ecto.Type

  def type, do: :string

  def cast(value) when is_list(value), do: {:ok, normalize(value)}

  def cast(value) when is_binary(value) do
    case Jason.decode(value) do
      {:ok, decoded} when is_list(decoded) -> {:ok, normalize(decoded)}
      _ -> {:ok, value |> String.split(",", trim: true) |> normalize()}
    end
  end

  def cast(nil), do: {:ok, []}
  def cast(_value), do: :error

  def load(value) when is_binary(value), do: cast(value)
  def load(value) when is_list(value), do: {:ok, normalize(value)}
  def load(nil), do: {:ok, []}
  def load(_value), do: :error

  def dump(value) when is_list(value), do: {:ok, Jason.encode!(normalize(value))}
  def dump(nil), do: {:ok, "[]"}
  def dump(_value), do: :error

  defp normalize(values) do
    values
    |> Enum.map(&to_string/1)
    |> Enum.map(&String.trim/1)
    |> Enum.reject(&(&1 == ""))
  end
end
