package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig

abstract class GuiConfig(section: ConfigurationSection?) : YamlConfig(section) {
    val Title: String?
    val Size: Int
    val Components: List<ComponentConfig>

    init {
        Title = getColoredString("Title")
        Size = getInt("Size")
        Components = getNestedConfigs("Components") {
            ComponentConfig(it)
        }
    }

    fun getButton(path: String?): ButtonConfig {
        return ButtonConfig(getSection(path!!))
    }
}