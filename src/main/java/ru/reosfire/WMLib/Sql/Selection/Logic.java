package ru.reosfire.WMLib.Sql.Selection;

import java.util.Locale;

public enum Logic implements IWhereMember
{
    And,
    Or;

    @Override
    public String toSqlString()
    {
        return name().toUpperCase(Locale.ROOT);
    }
}