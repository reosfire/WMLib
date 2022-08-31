package ru.reosfire.wmlib.yaml.common

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig

class TimeConfig(configurationSection: ConfigurationSection) : YamlConfig(configurationSection) {
    val milliseconds: Long

    init {
        var ms = getLong("Milliseconds", 0)
        ms += getLong("Seconds", 0) * 1000
        ms += getLong("Minutes", 0) * 1000 * 60
        ms += getLong("Hours", 0)   * 1000 * 60 * 60
        ms += getLong("Days", 0)    * 1000 * 60 * 60 * 24
        milliseconds = ms
    }
}