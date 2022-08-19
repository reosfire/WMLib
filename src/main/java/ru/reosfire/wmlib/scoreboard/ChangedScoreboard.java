package ru.reosfire.wmlib.scoreboard;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import ru.reosfire.wmlib.Startup;
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

    public void Start()
    {
        if (started) return;
        started = true;
        MoveNext();
    }

    private void MoveNext()
    {
        ScoreBoardConfig poll = scoreboards.poll();
        if (poll == null)
        {
            Stop();
            return;
        }
        scoreboards.add(poll);
        Integer time = scoreboardTimes.get(poll);
        currentScoreBoard = new ScoreBoard(poll);
        currentNextMover = new NextMover(time);
    }

    public void Stop()
    {
        if (!started) return;
        started = false;
        currentScoreBoard.stop();
        currentNextMover.cancel();
    }

    private class NextMover extends BukkitRunnable
    {
        private final BukkitTask currentTask;

        public NextMover(int delay)
        {
            currentTask = runTaskLater(Startup.getInstance(), delay);
            currentNextMover = this;
        }

        @Override
        public void run()
        {
            currentScoreBoard.stop();
            MoveNext();
        }

        @Override
        public synchronized void cancel() throws IllegalStateException
        {
            super.cancel();
            currentTask.cancel();
        }
    }
}