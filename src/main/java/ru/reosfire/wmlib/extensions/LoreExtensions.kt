package ru.reosfire.wmlib.extensions

import org.bukkit.ChatColor

fun List<String>.beautifyLore(): List<String> {
    val result = ArrayList<String>()
    for (line in this) {
        val split = line.split("(?<=\\G.{64})").toTypedArray()
        var lastColors = ""
        for (s in split) {
            val newLine = lastColors + s
            lastColors = ChatColor.getLastColors(newLine)
            result.add(newLine)
        }
    }
    return result
}