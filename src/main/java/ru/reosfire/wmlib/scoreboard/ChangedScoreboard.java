package ru.reosfire.wmlib.scoreboard;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import ru.reosfire.wmlib.yaml.common.ScoreBoardConfig;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ChangedScoreboard
{
    private final HashMap<ScoreBoardConfig, Integer> scoreboardTimes = new HashMap<>();
    private final Queue<ScoreBoardConfig> scoreboards = new LinkedList<>();
    private ScoreBoard currentScoreBoard;
    private nextMover currentNextMover;
    private boolean started = false;

    public void AddScoreBoard(ScoreBoardConfig config, int time)
    {
        scoreboardTimes.put(config, time);
        scoreboards.add(config);
    }

    public void start(JavaPlugin plugin)
    {
        if (started) return;
        started = true;
        moveNext(plugin);
    }

    private void moveNext(JavaPlugin plugin)
    {
        ScoreBoardConfig poll = scoreboards.poll();
        if (poll == null)
        {
            stop(plugin);
            return;
        }
        scoreboards.add(poll);
        Integer time = scoreboardTimes.get(poll);
        currentScoreBoard = new ScoreBoard(plugin, poll);
        currentNextMover = new nextMover(plugin, time);
    }

    public void stop(JavaPlugin plugin)
    {
        if (!started) return;
        started = false;
        currentScoreBoard.stop(plugin);
        currentNextMover.cancel();
    }

    private class nextMover extends BukkitRunnable
    {
        private final BukkitTask currentTask;
        private final JavaPlugin plugin;

        public nextMover(JavaPlugin plugin, int delay)
        {
            currentTask = runTaskLater(plugin, delay);
            currentNextMover = this;
            this.plugin = plugin;
        }

        @Override
        public void run()
        {
            currentScoreBoard.stop(plugin);
            moveNext(plugin);
        }

        @Override
        public synchronized void cancel() throws IllegalStateException
        {
            super.cancel();
            currentTask.cancel();
        }
    }
}