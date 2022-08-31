package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig

class RawItemConfig(configurationSection: ConfigurationSection?) : ComponentConfig(configurationSection) {
    val indexes = getIntegerList("Indexes")
    val item = ItemConfig(getSection("Item"))
}