package ru.reosfire.wmlib.yaml.common

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.meta.ItemMeta
import ru.reosfire.wmlib.yaml.YamlConfig

class Enchantment(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val Type: Enchantment
    val Level: Int

    init {
        Type = Enchantment.getByName(getString("Type"))
        Level = getInt("Level", 1)
    }

    fun setTo(meta: ItemMeta, force: Boolean) {
        meta.addEnchant(Type, Level, force)
    }
}