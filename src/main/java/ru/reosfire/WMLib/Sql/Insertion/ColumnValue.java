package ru.reosfire.WMLib.Sql.Insertion;

public class ColumnValue
{
    public final String Column;
    public final Object Value;

    public ColumnValue(String column, Object value)
    {
        Column = column;
        Value = value;
    }
}