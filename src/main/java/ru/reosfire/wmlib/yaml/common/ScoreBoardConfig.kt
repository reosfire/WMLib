package ru.reosfire.wmlib.yaml.common

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig

class ScoreBoardConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val Name: String = getString("Name")!!
    val Lines: List<String?> = getStringList("Lines")!!
    val UpdateInterval: Int = getInt("UpdateInterval")
}