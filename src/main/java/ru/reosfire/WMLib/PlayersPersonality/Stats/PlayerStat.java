package ru.reosfire.WMLib.PlayersPersonality.Stats;

import java.util.UUID;

public class PlayerStat
{
    public final String name;
    public final long value;
    public final UUID owner;

    public PlayerStat(String name, long value, UUID playerUUID)
    {
        this.name = name;
        this.value = value;
        this.owner = playerUUID;
    }
}