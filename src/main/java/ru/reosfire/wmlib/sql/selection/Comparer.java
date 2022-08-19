package ru.reosfire.wmlib.sql.selection;

import ru.reosfire.wmlib.sql.ISqlPart;

public enum Comparer implements ISqlPart
{
    Equal, NotEqual, GreaterThan, LessThan, GreaterThanOrEquals, LessThanOrEquals, Between, Like, In;

    @Override
    public String toSqlString()
    {
        switch (this)
        {
            case Equal:
                return "=";
            case NotEqual:
                return "<>";
            case GreaterThan:
                return ">";
            case LessThan:
                return "<";
            case GreaterThanOrEquals:
                return ">=";
            case LessThanOrEquals:
                return "<=";
            case Between:
                return "BETWEEN";
            case Like:
                return "LIKE";
            case In:
                return "IN";
            default:
                return this.name();
        }
    }
}