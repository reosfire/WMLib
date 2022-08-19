package ru.reosfire.wmlib.personality.moneys;

import ru.reosfire.wmlib.sql.SqlConnection;

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