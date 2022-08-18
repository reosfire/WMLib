package ru.reosfire.WMLib.PlayersPersonality.Moneys;

public class Currency
{
    private final String name;
    private final long value;

    public String getName()
    {
        return name;
    }

    public long getValue()
    {
        return value;
    }

    public Currency(String name, long value)
    {
        this.name = name;
        this.value = value;
    }
}