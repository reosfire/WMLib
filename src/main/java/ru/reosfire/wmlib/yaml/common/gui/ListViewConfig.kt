package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection

class ListViewConfig(configurationSection: ConfigurationSection?) : ComponentConfig(configurationSection) {
    val startIndex = getInt("StartIndex")
    val endIndex = getInt("EndIndex")
    val backButton = ButtonConfig(getSection("BackwardButton"))
    val forwardButton = ButtonConfig(getSection("ForwardButton"))

}