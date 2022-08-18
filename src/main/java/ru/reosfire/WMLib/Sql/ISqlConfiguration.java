package ru.reosfire.WMLib.Sql;

public interface ISqlConfiguration
{
    String getUser();
    String getPassword();
    String getConnectionString();
    void CheckRequirements() throws SqlRequirementsNotSatisfiedException;
}