package ru.reosfire.wmlib.sql.tables;

public enum ColumnFlag implements IColumnAttribute
{
    Not_null, Unique, Primary_key, Auto__Increment;

    @Override
    public String toSqlString()
    {
        return this.name().toUpperCase().replace('_', ' ').replace("  ", "_");
    }
}