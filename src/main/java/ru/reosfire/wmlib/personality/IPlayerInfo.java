package ru.reosfire.wmlib.personality;

import ru.reosfire.wmlib.personality.moneys.IPlayerMoneys;
import ru.reosfire.wmlib.personality.stats.IPlayerStats;

import java.sql.SQLException;

public interface IPlayerInfo
{
    IPlayerMoneys getMoneys();

    IPlayerStats getStats();

    String getName() throws SQLException;
}