package ru.reosfire.WMLib.PlayersPersonality;

import ru.reosfire.WMLib.PlayersPersonality.Moneys.IPlayerMoneys;
import ru.reosfire.WMLib.PlayersPersonality.Moneys.PlayerMoneys;
import ru.reosfire.WMLib.PlayersPersonality.Stats.IPlayerStats;
import ru.reosfire.WMLib.PlayersPersonality.Stats.PlayerStats;
import ru.reosfire.WMLib.Sql.SqlConnection;
import ru.reosfire.WMLib.Utils.UUIDConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerInfo implements IPlayerInfo
{
    private final SqlConnection connection;
    private final UUID playerUuid;
    private String nameCache = null;

    public IPlayerMoneys getMoneys()
    {
        return new PlayerMoneys(connection, playerUuid);
    }

    public IPlayerStats getStats()
    {
        return new PlayerStats(connection, playerUuid);
    }

    public String getName() throws SQLException
    {
        if (nameCache != null) return nameCache;
        PreparedStatement preparedStatement = connection.getConnection().prepareStatement("SELECT Name FROM " +
                "PlayersAccounts WHERE UUID=?");
        preparedStatement.setBytes(1, UUIDConverter.ToBytes(playerUuid));
        ResultSet resultSet = preparedStatement.executeQuery();
        String name = null;
        if (resultSet.next())
        {
            name = resultSet.getString("Name");
        }
        nameCache = name;
        return name;
    }


    public PlayerInfo(SqlConnection connection, UUID player)
    {
        this.connection = connection;
        this.playerUuid = player;
    }
}