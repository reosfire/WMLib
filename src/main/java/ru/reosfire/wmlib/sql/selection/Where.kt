package ru.reosfire.wmlib.sql.selection;

public class Where implements ISelectionAttribute
{
    private final IWhereMember[] members;

    public Where(IWhereMember... members)
    {
        this.members = members;
    }

    public Where(String var, Comparer comparer, String value)
    {
        members = new IWhereMember[]{new Condition(var, comparer, value)};
    }

    public Where(String var, Comparer comparer, long value)
    {
        members = new IWhereMember[]{new Condition(var, comparer, value)};
    }

    public Where(String var, Comparer comparer, boolean value)
    {
        members = new IWhereMember[]{new Condition(var, comparer, value)};
    }

    @Override
    public String toSqlString()
    {
        StringBuilder result = new StringBuilder("WHERE");

        for (IWhereMember member : members)
        {
            result.append(" ").append(member.toSqlString());
        }

        return result.toString();
    }
}