package ru.reosfire.wmlib.scoreboard

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard
import ru.reosfire.wmlib.extensions.colorize
import ru.reosfire.wmlib.yaml.common.ScoreBoardConfig

class ScoreBoard(plugin: Plugin, private val config: ScoreBoardConfig) {
    private val updateTask: BukkitTask
    private val scoreboards: HashMap<Player, Scoreboard>
    private val scoreboardEvents: Events

    init {
        scoreboards = HashMap()
        scoreboardEvents = Events()
        object : BukkitRunnable() {
            override fun run() {
                update()
                plugin.server.pluginManager.registerEvents(
                    scoreboardEvents,
                    plugin
                )
            }
        }.runTask(plugin)
        updateTask = object : BukkitRunnable() {
            override fun run() {
                update()
            }
        }.runTaskTimer(plugin, 0, config.updateInterval.toLong())
    }

    fun update() {
        for (player in Bukkit.getOnlinePlayers()) {
            update(player)
        }
    }

    private fun update(player: Player) {
        if (!scoreboards.containsKey(player)) scoreboards[player] = Bukkit.getScoreboardManager().newScoreboard
        val scoreboard = scoreboards[player]
        var objective = scoreboard!!.getObjective(DisplaySlot.SIDEBAR)
        if (objective == null) {
            objective = scoreboard.registerNewObjective("a", "b")
            objective.displaySlot = DisplaySlot.SIDEBAR
        }
        val name = config.name.colorize(player)
        if (!PlaceholderAPI.containsPlaceholders(name)) {
            objective!!.displayName = name
        }
        var score = 100
        for (line in config.lines) {
            if (line == null) continue
            score--
            val teamName = Integer.toString(score)
            val team = scoreboard.getTeam(teamName)
            val colorizedLine = line.colorize(player)
            val entry = ChatColor.values()[99 - score].toString() + "" + ChatColor.RESET
            if (PlaceholderAPI.containsPlaceholders(colorizedLine)) {
                team?.unregister()
                scoreboard.resetScores(entry)
                continue
            }
            val prefixSuffix = getPrefixSuffix(colorizedLine)
            if (team == null) {
                val newTeam = scoreboard.registerNewTeam(teamName)
                newTeam.prefix = prefixSuffix[0]
                newTeam.addEntry(entry)
                newTeam.suffix = prefixSuffix[1]
                val scoreLine = objective!!.getScore(entry)
                scoreLine.score = score
            } else {
                team.prefix = prefixSuffix[0]
                team.suffix = prefixSuffix[1]
            }
        }
        if (player.scoreboard != scoreboard) {
            player.scoreboard = scoreboard
        }
    }

    private fun getPrefixSuffix(line: String): Array<String?> {
        val result = arrayOfNulls<String>(2)
        if (line.length <= 16) {
            result[0] = line
            result[1] = ""
            return result
        }
        var prefix = line.substring(0, 16)
        var suffix = line.substring(16)
        if (prefix.endsWith("§")) {
            suffix = "§$suffix"
            while (prefix.endsWith("§")) {
                prefix = prefix.substring(0, prefix.length - 1)
            }
        } else {
            val lastColors = ChatColor.getLastColors(prefix)
            if (lastColors != "§r" && lastColors != "§f") {
                suffix = lastColors + suffix
            }
        }
        suffix = suffix.substring(0, Math.min(16, suffix.length))
        result[0] = prefix
        result[1] = suffix
        return result
    }

    fun stop(plugin: JavaPlugin?) {
        updateTask.cancel()
        object : BukkitRunnable() {
            override fun run() {
                for (player in scoreboards.keys) {
                    player.scoreboard = Bukkit.getScoreboardManager().newScoreboard
                }
                HandlerList.unregisterAll(scoreboardEvents)
                scoreboards.clear()
            }
        }.runTask(plugin)
    }

    private inner class Events : Listener {
        @EventHandler
        fun SendOnJoin(event: PlayerJoinEvent) {
            update(event.player)
        }
    }
}