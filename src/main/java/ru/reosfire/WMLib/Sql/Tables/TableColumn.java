package ru.reosfire.WMLib.Sql.Tables;

import ru.reosfire.WMLib.Sql.ISqlPart;

public class TableColumn implements ISqlPart
{
    private final String name;
    private final ColumnType type;
    private final ColumnFlag[] flags;

    public TableColumn(String name, ColumnType type, ColumnFlag... flags)
    {
        this.name = name;
        this.type = type;
        this.flags = flags;
    }

    @Override
    public String toSqlString()
    {
        StringBuilder result = new StringBuilder("`").append(name).append("` ").append(type.toSqlString());
        for (ColumnFlag flag : flags)
        {
            result.append(" ").append(flag.toSqlString());
        }
        return result.toString();
    }
}