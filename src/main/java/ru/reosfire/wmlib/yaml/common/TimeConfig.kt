package ru.reosfire.wmlib.yaml.common

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig

class TimeConfig(configurationSection: ConfigurationSection) : YamlConfig(configurationSection) {
    val Seconds: Long

    init {
        var seconds = configurationSection.getInt("Seconds", 0).toLong()
        seconds += configurationSection.getLong("Minutes", 0) * 60
        seconds += configurationSection.getLong("Hours", 0) * 60 * 60
        seconds += configurationSection.getLong("Days", 0) * 60 * 60 * 24
        Seconds = seconds
    }
}