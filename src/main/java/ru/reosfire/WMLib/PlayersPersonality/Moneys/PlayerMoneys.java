package ru.reosfire.WMLib.PlayersPersonality.Moneys;

import ru.reosfire.WMLib.Sql.SqlConnection;

import java.util.UUID;

public class PlayerMoneys implements IPlayerMoneys
{
    private final SqlConnection connection;
    private final UUID playerUuid;

    public PlayerMoneys(SqlConnection connection, UUID playerUuid)
    {
        this.connection = connection;
        this.playerUuid = playerUuid;
    }
}