package ru.reosfire.wmlib.sql.insertion;

public class ColumnValue
{
    public final String column;
    public final Object value;

    public ColumnValue(String column, Object value)
    {
        this.column = column;
        this.value = value;
    }
}