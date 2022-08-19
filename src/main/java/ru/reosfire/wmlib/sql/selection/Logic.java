package ru.reosfire.wmlib.sql.selection;

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