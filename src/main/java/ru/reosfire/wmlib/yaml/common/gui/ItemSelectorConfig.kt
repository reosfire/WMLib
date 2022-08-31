package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig

class ItemSelectorConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val index: Int = getInt("Index")
    val defaultItem: ItemConfig = ItemConfig(getSection("DefaultItem"))

}