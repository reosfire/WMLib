package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig

abstract class GuiConfig(section: ConfigurationSection?) : YamlConfig(section) {
    val title: String? = getColoredString("Title")
    val size: Int = getInt("Size")
    val components = getNestedConfigs("Components") {
        ComponentConfig(it)
    }

    fun getButton(path: String): ButtonConfig {
        return ButtonConfig(getSection(path))
    }
}