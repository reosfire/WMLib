package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig

class SwitchConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val index: Int = getInt("Index")
    val items: List<ItemConfig> = getNestedConfigs("Items") { ItemConfig(it) }
    val cooldown: Int = getInt("CoolDown", 0)
}