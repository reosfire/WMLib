package ru.reosfire.wmlib.sql;


import ru.reosfire.wmlib.sql.insertion.ColumnValue;
import ru.reosfire.wmlib.sql.selection.ISelectionAttribute;
import ru.reosfire.wmlib.sql.tables.TableColumn;

import java.sql.*;

public class SqlConnection
{
    private final ISqlConfiguration configuration;
    private Connection connection = null;

    public SqlConnection(ISqlConfiguration config) throws SQLException
    {
        configuration = config;
        Open();
    }

    private void Open() throws SQLException
    {
        synchronized (this)
        {
            if (connection != null && !connection.isClosed() && isConnectionAlive()) return;

            try
            {
                configuration.checkRequirements();
            }
            catch (SqlRequirementsNotSatisfiedException e)
            {
                throw new SQLException(e);
            }
            connection = DriverManager.getConnection(configuration.getConnectionString(), configuration.getUser(),
                    configuration.getPassword());
        }
    }

    private boolean isConnectionAlive()
    {
        try
        {
            connection.createStatement().executeQuery("SELECT 1;");
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    public Connection getConnection() throws SQLException
    {
        Open();
        return connection;
    }

    public void createTable(String name, TableColumn... columns) throws SQLException
    {
        StringBuilder request = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append('`').append(name).append('`').append(" (");
        for (int i = 0; i < columns.length; i++)
        {
            request.append(columns[i].toSqlString());
            if (i + 1 < columns.length) request.append(", ");
        }
        request.append(");");
        getConnection().createStatement().executeUpdate(request.toString());
    }

    public void addTableColumn(String table, TableColumn column) throws SQLException
    {
        StringBuilder request = new StringBuilder("ALTER TABLE ").append('`').append(table).append('`').append(" ADD COLUMN ");
        request.append(column.toSqlString());
        getConnection().createStatement().executeUpdate(request.append(";").toString());
    }

    public ResultSet select(String table, String column, ISelectionAttribute... attributes) throws SQLException
    {
        StringBuilder request = new StringBuilder("SELECT ").append(column).append(" FROM ").append(table);
        for (ISelectionAttribute attribute : attributes)
        {
            request.append(" ");
            request.append(attribute.toSqlString());
        }
        return getConnection().createStatement().executeQuery(request.append(";").toString());
    }
    public ResultSet select(String table, String[] columns, ISelectionAttribute... attributes) throws SQLException
    {
        StringBuilder request = new StringBuilder("SELECT ");
        for (int i = 0; i < columns.length; i++)
        {
            request.append(columns[i]);
            if (i + 1 < columns.length) request.append(",");
        }
        request.append(" FROM ").append(table);
        for (ISelectionAttribute attribute : attributes)
        {
            request.append(" ");
            request.append(attribute.toSqlString());
        }
        return getConnection().createStatement().executeQuery(request.append(";").toString());
    }

    public void insert(String table, ColumnValue... values) throws SQLException
    {
        StringBuilder request = insertStringBuilder(table, values);
        request.append(";");
        PreparedStatement preparedStatement = getConnection().prepareStatement(request.toString());

        for (int i = 0; i < values.length; i++)
        {
            preparedStatement.setObject(i + 1, values[i].getValue());
        }

        preparedStatement.executeUpdate();
    }
    public void insertOrUpdate(String table, String keyColumn, ColumnValue... values) throws SQLException
    {
        StringBuilder request = insertStringBuilder(table, values);
        request.append(" ON CONFLICT(").append(keyColumn).append(") DO UPDATE SET ");
        for (int i = 0; i < values.length; i++)
        {
            request.append(values[i].getColumn()).append("=?");
            if (i + 1 < values.length) request.append(", ");
        }
        request.append(";");
        PreparedStatement preparedStatement = getConnection().prepareStatement(request.toString());

        for (int i = 0; i < values.length * 2; i++)
        {
            preparedStatement.setObject(i + 1, values[i % values.length].getValue());
        }

        preparedStatement.executeUpdate();
    }
    private StringBuilder insertStringBuilder(String table, ColumnValue[] values)
    {
        StringBuilder request = new StringBuilder("INSERT INTO ").append(table).append(" (");
        for (int i = 0; i < values.length; i++)
        {
            request.append(values[i].getColumn());
            if (i + 1 < values.length) request.append(", ");
        }
        request.append(") VALUES (");
        for (int i = 0; i < values.length; i++)
        {
            request.append("?");
            if (i + 1 < values.length) request.append(", ");
        }
        return request.append(")");
    }

    public void update()
    {

    }

    public void delete()
    {

    }
}