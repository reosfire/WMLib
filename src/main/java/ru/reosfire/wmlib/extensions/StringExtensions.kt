package ru.reosfire.wmlib.extensions

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import ru.reosfire.wmlib.text.Replacement

fun String.setColors(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}
fun Iterable<String?>.setColors(): List<String?> {
    return map { it?.setColors() }
}

fun String.setPlaceholders(player: OfflinePlayer): String {
    return PlaceholderAPI.setPlaceholders(player, this)
}
fun String.setPlaceholders(player: OfflinePlayer, player2: OfflinePlayer): String {
    return PlaceholderAPI.setRelationalPlaceholders(player.player, player2.player, this)
}

fun String.setReplacements(vararg replacements: Replacement): String {
    return Replacement.set(this, *replacements)
}
fun Iterable<String?>.setReplacements(vararg replacements: Replacement): List<String?> {
    return map { it?.setReplacements(*replacements) }
}

fun String.colorize(player: OfflinePlayer, vararg replacements: Replacement): String {
    return this.setPlaceholders(player).setReplacements(*replacements).setColors();
}
fun Iterable<String?>.colorize(player: OfflinePlayer, vararg replacements: Replacement): List<String?> {
    return map { it?.colorize(player, *replacements) }
}
fun String.colorize(
    player: OfflinePlayer,
    player1: OfflinePlayer,
    vararg replacements: Replacement
): String {
    return setPlaceholders(player, player1).setReplacements(*replacements).setColors();
}
fun Iterable<String?>.colorize(
    player: OfflinePlayer,
    player1: OfflinePlayer,
    vararg replacements: Replacement
): List<String?> {
    return map { it?.colorize(player, player1, *replacements) }
}