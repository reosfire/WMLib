package ru.reosfire.wmlib.yaml.common

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig
import java.util.*

class Inventory(configurationSection: ConfigurationSection) : YamlConfig(configurationSection) {
    var Items: Hashtable<Int, ItemStack?> = Hashtable()
    fun getItems(): List<ItemStack?> {
        return Items.values as List<ItemStack?>
    }

    init {
        val keys = configurationSection.getKeys(false)
        if (keys != null) {
            for (key in keys) {
                var inventoryIndex: Int
                val itemSection = getSection(key)
                val itemStack = ItemConfig(itemSection).unwrap()
                inventoryIndex = try {
                    key.toInt()
                } catch (e: Exception) {
                    itemSection.getInt("InventoryIndex", 0)
                }
                Items[inventoryIndex] = itemStack
            }
        }
    }

    fun set(player: Player) {
        val inventory = player.inventory
        inventory.clear()
        for (itemKey in Items.keys) {
            inventory.setItem(itemKey, Items[itemKey])
        }
    }

    fun set(players: Iterable<Player>) {
        for (player in players) {
            set(player)
        }
    }
}