package ru.reosfire.wmlib.yaml.common

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig

class ScoreBoardConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val name: String = getString("Name")!!
    val lines: List<String?> = getStringList("Lines")!!
    val updateInterval: Int = getInt("UpdateInterval")
}