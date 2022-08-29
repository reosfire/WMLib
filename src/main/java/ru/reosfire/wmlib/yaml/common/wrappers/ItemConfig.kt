package ru.reosfire.wmlib.yaml.common.wrappers

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import ru.reosfire.wmlib.extensions.colorize
import ru.reosfire.wmlib.extensions.setColors
import ru.reosfire.wmlib.text.IColorizer
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.Enchantment
import java.util.*

class ItemConfig(section: ConfigurationSection?) : YamlConfig(section), WrapperConfig<ItemStack?> {
    var MaterialData: MaterialDataConfig
    val Amount: Int
    val Name: String?
    val Lore: List<String?>?
    val Enchantments: List<Enchantment>
    val Flags: List<ItemFlag>?

    init {
        MaterialData = MaterialDataConfig(section)
        Amount = getInt("Amount", 1)
        Name = getString("Name", "")
        Lore = getStringList("Lore", ArrayList())
        Enchantments = getNestedConfigs("Enchantments"){
            Enchantment(it)
        }
        Flags = ArrayList()
        val flags = getStringList("Flags")
        if (flags != null) {
            for (flag in flags) {
                Flags.add(ItemFlag.valueOf(flag!!.uppercase(Locale.getDefault())))
            }
        }
    }

    override fun unwrap(): ItemStack {
        return unwrap{ it?.setColors() }
    }

    fun unwrap(player: OfflinePlayer, vararg replacements: Replacement): ItemStack {
        return unwrap{ it?.colorize(player, *replacements) }
    }

    fun unwrap(player: OfflinePlayer, player1: OfflinePlayer, vararg replacements: Replacement): ItemStack {
        return unwrap{ it?.colorize(player, player1, *replacements) }
    }

    fun unwrap(colorizer: IColorizer): ItemStack {
        val itemStack = ItemStack(MaterialData.Material, Amount, MaterialData.Data.toShort())
        setTo(itemStack, colorizer)
        return itemStack
    }

    fun setTo(item: ItemStack) {
        setTo(item){ it?.setColors() }
    }

    fun setTo(item: ItemStack, player: OfflinePlayer, vararg replacements: Replacement) {
        setTo(item) { it?.colorize(player, *replacements) }
    }

    fun setTo(item: ItemStack,
        player: OfflinePlayer,
        player1: OfflinePlayer,
        vararg replacements: Replacement
    ) {
        setTo(item) { it?.colorize(player, player1, *replacements) }
    }

    fun setTo(item: ItemStack, colorizer: IColorizer) {
        val headData = getString("HeadData")
        headData?.let { setHeadData(item, it) }
        val itemMeta = item.itemMeta
        itemMeta.displayName = colorizer.colorize(Name)
        if (Lore != null) itemMeta.lore = colorizer.colorize(Lore).toList()
        for (enchantment in Enchantments) {
            enchantment.setTo(itemMeta, true)
        }
        if (Flags != null) {
            for (flag in Flags) {
                itemMeta.addItemFlags(flag)
            }
        }
        item.itemMeta = itemMeta
    }

    private fun setHeadData(head: ItemStack, data: String) {
        try {
            val headMeta = head.itemMeta as SkullMeta
            val randomProfile = GameProfile(UUID.randomUUID(), null)
            randomProfile.properties.put("textures", Property("textures", data))
            val profileField = headMeta.javaClass.getDeclaredField("profile")
            profileField.isAccessible = true
            profileField[headMeta] = randomProfile
            head.itemMeta = headMeta
        } catch (e: NoSuchFieldException) {
            throw RuntimeException("Error while setting head data", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Error while setting head data", e)
        }
    }
}