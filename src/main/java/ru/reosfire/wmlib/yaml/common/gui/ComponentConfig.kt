package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig
import java.util.Locale

open class ComponentConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val type = getString("Type", null)!!.lowercase(Locale.getDefault())
}