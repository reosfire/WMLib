package ru.reosfire.wmlib.sql;

public interface ISqlConfiguration
{
    String getUser();
    String getPassword();
    String getConnectionString();
    void checkRequirements() throws SqlRequirementsNotSatisfiedException;
}