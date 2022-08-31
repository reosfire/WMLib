package ru.reosfire.wmlib.yaml.common

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Sound
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import ru.reosfire.wmlib.extensions.colorize
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.YamlConfig
import java.util.stream.Stream

class AnnouncementConfig(configurationSection: ConfigurationSection) : YamlConfig(configurationSection) {
    val message: List<String?>?
    val Sound: Sound?
    val Title: String?
    var Subtitle: String? = null
    var TitleTime = -1
    val ActionBarMessage: String?

    init {
        var message = getStringList("Message")
        if (message != null && message.isEmpty()) message = null
        this.message = message
        val soundName = configurationSection.getString("Sound")
        Sound = if (soundName == null) null else org.bukkit.Sound.valueOf(soundName)
        Title = configurationSection.getString("Title", null)
        if (Title != null) {
            Subtitle = configurationSection.getString("Subtitle", "")
            TitleTime = configurationSection.getInt("TitleTime", 20)
        }
        ActionBarMessage = configurationSection.getString("ActionBarMessage")
    }

    fun sendMessage(player: Player, vararg replacements: Replacement) {
        if (message == null) return
        val messages: List<String?> = message.colorize(player, *replacements)
        for (message in messages) {
            player.sendMessage(message)
        }
    }

    fun playSound(player: Player?) {
        if (player == null) return
        player.playSound(player.location, Sound, 1f, 1f)
    }

    fun showTitle(player: Player, vararg replacements: Replacement) {
        if (Title == null) return
        val title: String = Title.colorize(player, *replacements)
        val subtitle = Subtitle?.colorize(player, *replacements)
        player.sendTitle(title, subtitle, TitleTime / 4, TitleTime / 2, TitleTime / 4)
    }

    fun sendActionBar(player: Player, vararg replacements: Replacement) {
        if (ActionBarMessage == null) return
        val message: String = ActionBarMessage.colorize(player, *replacements)
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(message))
    }

    fun send(player: Player, vararg replacements: Replacement) {
        sendMessage(player, *replacements)
        playSound(player)
        showTitle(player, *replacements)
        sendActionBar(player)
    }

    fun <T : Player> send(players: Iterable<T>, vararg replacements: Replacement) {
        for (player in players) {
            send(player, *replacements)
        }
    }

    fun <T : Player> send(players: Iterable<T>, except: Player, vararg replacements: Replacement) {
        for (player in players) {
            if (player.uniqueId === except.uniqueId) continue
            send(player, *replacements)
        }
    }

    fun send(players: Stream<Player>, vararg replacements: Replacement) {
        players.forEach { player: Player -> send(player, *replacements) }
    }
}