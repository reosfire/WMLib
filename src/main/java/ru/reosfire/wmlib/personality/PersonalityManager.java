package ru.reosfire.wmlib.personality;

import org.bukkit.OfflinePlayer;
import ru.reosfire.wmlib.personality.stats.PlayerStat;
import ru.reosfire.wmlib.sql.ISqlConfiguration;
import ru.reosfire.wmlib.sql.SqlConnection;
import ru.reosfire.wmlib.sql.tables.ColumnFlag;
import ru.reosfire.wmlib.sql.tables.ColumnType;
import ru.reosfire.wmlib.sql.tables.TableColumn;
import ru.reosfire.wmlib.utils.UUIDConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonalityManager
{
    private final SqlConnection connection;

    public PersonalityManager(ISqlConfiguration connectionConfiguration) throws SQLException
    {
        connection = new SqlConnection(connectionConfiguration);
        connection.createTable("PlayersAccounts",
                new TableColumn("UUID", ColumnType.VarBinary.setMax(16), ColumnFlag.Unique, ColumnFlag.Not_null),
                new TableColumn("Name", ColumnType.Text));
    }

    public void RegisterPlayer(OfflinePlayer player) throws SQLException
    {
        PreparedStatement preparedStatement = connection.getConnection().prepareStatement(
                "INSERT INTO PlayersAccounts (UUID, Name) VALUES (?, ?) ON DUPLICATE KEY UPDATE UUID=UUID;");
        preparedStatement.setBytes(1, UUIDConverter.ToBytes(player.getUniqueId()));
        preparedStatement.setString(2, player.getName());
        preparedStatement.executeUpdate();
    }

    public IPlayerInfo GetPlayerInfo(UUID uuid)
    {
        return new PlayerInfo(connection, uuid);
    }

    public IPlayerInfo GetPlayerInfo(OfflinePlayer player)
    {
        return GetPlayerInfo(player.getUniqueId());
    }

    public List<PlayerStat> GetTop(String stat, int count) throws SQLException
    {
        PreparedStatement preparedStatement = connection.getConnection().prepareStatement("SELECT * FROM " + stat +
                " ORDER BY Value DESC, Time ASC LIMIT ?;");
        preparedStatement.setInt(1, count);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<PlayerStat> players = new ArrayList<>();
        while (resultSet.next())
        {
            long value = resultSet.getLong("Value");
            UUID uuid = UUIDConverter.FromBytes(resultSet.getBytes("UUID"));
            PlayerStat playerStat = new PlayerStat(stat, value, uuid);
            players.add(playerStat);
        }
        return players;
    }

    public List<PlayerStat> GetTop(String stat, int count, int from) throws SQLException
    {
        PreparedStatement preparedStatement = connection.getConnection().prepareStatement("SELECT * FROM " + stat +
                " ORDER BY Value DESC, Time ASC LIMIT ? OFFSET ?;");
        preparedStatement.setInt(1, count);
        preparedStatement.setInt(2, from);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<PlayerStat> players = new ArrayList<>();
        while (resultSet.next())
        {
            long value = resultSet.getLong("Value");
            UUID uuid = UUIDConverter.FromBytes(resultSet.getBytes("UUID"));
            PlayerStat playerStat = new PlayerStat(stat, value, uuid);
            players.add(playerStat);
        }
        return players;
    }
}