package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.Inventory
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig

open class ButtonConfig(section: ConfigurationSection?) : YamlConfig(section) {
    val item: ItemConfig = ItemConfig(getSection("Item"))
    val index: Int = getInt("Index")

    fun set(to: Inventory, player: OfflinePlayer, vararg replacements: Replacement) {
        to.setItem(index, item.unwrap(player, *replacements))
    }
}