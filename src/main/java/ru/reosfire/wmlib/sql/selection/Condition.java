package ru.reosfire.wmlib.sql.selection;

public class Condition implements IWhereMember
{
    public final String Variable;
    public final Comparer Comparer;
    public final String Value;

    public Condition(String variable, Comparer comparer, String value)
    {
        Variable = variable;
        Comparer = comparer;
        Value = "'" + value + "'";
    }

    public Condition(String variable, Comparer comparer, long value)
    {
        Variable = variable;
        Comparer = comparer;
        Value = Long.toString(value);
    }

    public Condition(String variable, Comparer comparer, boolean value)
    {
        Variable = variable;
        Comparer = comparer;
        Value = value ? "1" : "0";
    }

    @Override
    public String toSqlString()
    {
        return Variable + Comparer.toSqlString() + Value;
    }
}