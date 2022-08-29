package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig

class SwitchConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val Index: Int = getInt("Index")
    val Items: List<ItemConfig> = getNestedConfigs("Items") { ItemConfig(it) }
    val CoolDown: Int = getInt("CoolDown", 0)
}