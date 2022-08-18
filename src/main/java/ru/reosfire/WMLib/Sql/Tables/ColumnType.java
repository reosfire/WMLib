package ru.reosfire.WMLib.Sql.Tables;

import ru.reosfire.WMLib.Sql.ISqlPart;

public enum ColumnType implements ISqlPart
{
    Bit, TinyInt, SmallInt, Int, Integer, BigInt, Decimal, Numeric, Float, Real, Boolean,
    Date, Time, DateTime, TimeStamp, Year,
    Char, VarChar, Text,
    NChar, NVarChar, NText,
    Binary, VarBinary, Image,
    Clob, Blob, XML, JSON;

    private int Max = -1;

    public ColumnType setMax(int max)
    {
        Max = max;
        return this;
    }

    @Override
    public String toSqlString()
    {
        StringBuilder result = new StringBuilder(this.name().toUpperCase());
        if (Max > 0) result.append("(").append(Max).append(")");
        return result.toString();
    }
}