package ru.reosfire.wmlib.scoreboard;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import ru.reosfire.wmlib.WMLib;
import ru.reosfire.wmlib.yaml.common.ScoreBoardConfig;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ChangedScoreboard
{
    private final HashMap<ScoreBoardConfig, Integer> scoreboardTimes = new HashMap<>();
    private final Queue<ScoreBoardConfig> scoreboards = new LinkedList<>();
    private ScoreBoard currentScoreBoard;
    private NextMover currentNextMover;
    private boolean started = false;

    public void AddScoreBoard(ScoreBoardConfig config, int time)
    {
        scoreboardTimes.put(config, time);
        scoreboards.add(config);
    }

    public void Start(JavaPlugin plugin)
    {
        if (started) return;
        started = true;
        MoveNext(plugin);
    }

    private void MoveNext(JavaPlugin plugin)
    {
        ScoreBoardConfig poll = scoreboards.poll();
        if (poll == null)
        {
            Stop(plugin);
            return;
        }
        scoreboards.add(poll);
        Integer time = scoreboardTimes.get(poll);
        currentScoreBoard = new ScoreBoard(plugin, poll);
        currentNextMover = new NextMover(plugin, time);
    }

    public void Stop(JavaPlugin plugin)
    {
        if (!started) return;
        started = false;
        currentScoreBoard.stop(plugin);
        currentNextMover.cancel();
    }

    private class NextMover extends BukkitRunnable
    {
        private final BukkitTask currentTask;
        private final JavaPlugin plugin;

        public NextMover(JavaPlugin plugin, int delay)
        {
            currentTask = runTaskLater(plugin, delay);
            currentNextMover = this;
            this.plugin = plugin;
        }

        @Override
        public void run()
        {
            currentScoreBoard.stop(plugin);
            MoveNext(plugin);
        }

        @Override
        public synchronized void cancel() throws IllegalStateException
        {
            super.cancel();
            currentTask.cancel();
        }
    }
}