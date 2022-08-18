package ru.reosfire.WMLib.PlayersPersonality;

import ru.reosfire.WMLib.PlayersPersonality.Moneys.IPlayerMoneys;
import ru.reosfire.WMLib.PlayersPersonality.Stats.IPlayerStats;

import java.sql.SQLException;

public interface IPlayerInfo
{
    IPlayerMoneys getMoneys();

    IPlayerStats getStats();

    String getName() throws SQLException;
}