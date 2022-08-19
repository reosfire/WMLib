package ru.reosfire.wmlib.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;
import ru.reosfire.wmlib.text.Text;
import ru.reosfire.wmlib.yaml.common.ScoreBoardConfig;

import java.util.HashMap;

public class ScoreBoard
{
    private final ScoreBoardConfig config;
    private final BukkitTask updateTask;
    private final HashMap<Player, Scoreboard> scoreboards;
    private final Events scoreboardEvents;

    public ScoreBoard(Plugin plugin, ScoreBoardConfig config)
    {
        this.config = config;
        scoreboards = new HashMap<>();
        scoreboardEvents = new Events();

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                update();
                plugin.getServer().getPluginManager().registerEvents(scoreboardEvents,
                        plugin);
            }
        }.runTask(plugin);
        updateTask = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                update();
            }
        }.runTaskTimer(plugin, 0, config.UpdateInterval);

    }

    public void update()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            update(player);
        }
    }

    private void update(Player player)
    {
        if (!scoreboards.containsKey(player)) scoreboards.put(player, Bukkit.getScoreboardManager().getNewScoreboard());
        Scoreboard scoreboard = scoreboards.get(player);
        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        if (objective == null)
        {
            objective = scoreboard.registerNewObjective("a", "b");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        String name = Text.colorize(player, config.Name);
        if (!PlaceholderAPI.containsPlaceholders(name))
        { objective.setDisplayName(name); }


        int score = 100;
        for (String line : config.Lines)
        {
            score--;
            String teamName = Integer.toString(score);
            Team team = scoreboard.getTeam(teamName);
            String colorizedLine = Text.colorize(player, line);
            String entry = ChatColor.values()[99 - score] + "" + ChatColor.RESET;
            if (PlaceholderAPI.containsPlaceholders(colorizedLine))
            {
                if (team != null) team.unregister();
                scoreboard.resetScores(entry);
                continue;
            }

            String[] prefixSuffix = getPrefixSuffix(colorizedLine);
            if (team == null)
            {
                Team newTeam = scoreboard.registerNewTeam(teamName);
                newTeam.setPrefix(prefixSuffix[0]);
                newTeam.addEntry(entry);
                newTeam.setSuffix(prefixSuffix[1]);
                Score scoreLine = objective.getScore(entry);
                scoreLine.setScore(score);
            }
            else
            {
                team.setPrefix(prefixSuffix[0]);
                team.setSuffix(prefixSuffix[1]);
            }
        }
        if (!player.getScoreboard().equals(scoreboard))
        {
            player.setScoreboard(scoreboard);
        }
    }

    private String[] getPrefixSuffix(String line)
    {
        String[] result = new String[2];
        if (line.length() <= 16)
        {
            result[0] = line;
            result[1] = "";
            return result;
        }
        String prefix = line.substring(0, 16);

        String suffix = line.substring(16);
        if (prefix.endsWith("§"))
        {
            suffix = "§" + suffix;
            while (prefix.endsWith("§"))
            {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
        else
        {
            String lastColors = ChatColor.getLastColors(prefix);
            if (!lastColors.equals("§r") && !lastColors.equals("§f"))
            { suffix = lastColors + suffix; }
        }
        suffix = suffix.substring(0, Math.min(16, suffix.length()));

        result[0] = prefix;
        result[1] = suffix;
        return result;
    }

    public void stop(JavaPlugin plugin)
    {
        updateTask.cancel();
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (Player player : scoreboards.keySet())
                {
                    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                }
                HandlerList.unregisterAll(scoreboardEvents);
                scoreboards.clear();
            }
        }.runTask(plugin);
    }

    private class Events implements Listener
    {
        @EventHandler
        public void SendOnJoin(PlayerJoinEvent event)
        {
            update(event.getPlayer());
        }
    }
}