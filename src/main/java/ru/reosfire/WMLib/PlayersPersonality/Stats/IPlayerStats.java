package ru.reosfire.WMLib.PlayersPersonality.Stats;

import java.sql.SQLException;

public interface IPlayerStats
{
    PlayerStat Get(String stat) throws SQLException;

    void Set(String stat, long value) throws SQLException;

    void Add(String stat, long value) throws SQLException;
}