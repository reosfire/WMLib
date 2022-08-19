package ru.reosfire.wmlib.personality.stats;

import ru.reosfire.wmlib.sql.SqlConnection;
import ru.reosfire.wmlib.sql.tables.ColumnFlag;
import ru.reosfire.wmlib.sql.tables.ColumnType;
import ru.reosfire.wmlib.sql.tables.TableColumn;
import ru.reosfire.wmlib.utils.UUIDConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

public class PlayerStats implements IPlayerStats
{
    private final HashSet<String> RegisteredStats = new HashSet<>();
    private final SqlConnection connection;
    private final UUID playerUuid;

    public PlayerStats(SqlConnection connection, UUID playerUuid)
    {
        this.connection = connection;
        this.playerUuid = playerUuid;
    }

    private void RegisterStat(String stat) throws SQLException
    {
        if (RegisteredStats.contains(stat)) return;
        connection.createTable(stat,
                new TableColumn("UUID", ColumnType.VarBinary, ColumnFlag.Unique, ColumnFlag.Not_null),
                new TableColumn("Value", ColumnType.BigInt),
                new TableColumn("Time", ColumnType.BigInt));
        RegisteredStats.add(stat);
    }

    public PlayerStat Get(String stat) throws SQLException
    {
        return Get(stat, 0);
    }

    public PlayerStat Get(String stat, long def) throws SQLException
    {
        RegisterStat(stat);
        PreparedStatement preparedStatement =
                connection.getConnection().prepareStatement("SELECT Value FROM " + stat + " WHERE UUID=?;");
        preparedStatement.setBytes(1, UUIDConverter.ToBytes(playerUuid));
        ResultSet resultSet = preparedStatement.executeQuery();
        long value;
        if (resultSet.next()) { value = resultSet.getLong("Value"); }
        else { value = def; }
        return new PlayerStat(stat, value, playerUuid);
    }

    public void Set(String stat, long value) throws SQLException
    {
        RegisterStat(stat);
        long nowMillis = Instant.now().toEpochMilli();
        PreparedStatement preparedStatement = connection.getConnection().prepareStatement(
                "INSERT INTO " + stat + " (UUID, Value, Time) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE Value=?, " +
                        "Time=?;");
        preparedStatement.setBytes(1, UUIDConverter.ToBytes(playerUuid));
        preparedStatement.setLong(2, value);
        preparedStatement.setLong(3, nowMillis);
        preparedStatement.setLong(4, value);
        preparedStatement.setLong(5, nowMillis);
        preparedStatement.executeUpdate();
    }

    public void Add(String stat, long value) throws SQLException
    {
        PlayerStat get = Get(stat);
        Set(stat, get.value + value);
    }
}