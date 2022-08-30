package ru.reosfire.wmlib.yaml.common

import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.IConfigCreator
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.group.Group
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import java.util.*

class LuckPermsSwitch<T : YamlConfig?>(configurationSection: ConfigurationSection, creator: IConfigCreator<T>) :
    YamlConfig(configurationSection) {
    private val innerConfigurations = Hashtable<Group?, T>()

    init {
        val groupManager = LuckPermsProvider.get().groupManager
        for (key in configurationSection.getKeys(false)) {
            val groups = key.split(",").toTypedArray()
            val config = creator.create(getSection(key))
            for (groupName in groups) {
                val group = groupManager.getGroup(groupName) ?: continue
                innerConfigurations[group] = config
            }
        }
    }

    fun getFor(group: Group?): T? {
        return innerConfigurations[group]
    }

    fun getFor(player: Player): T? {
        val primaryGroup = LuckPermsProvider.get().userManager.getUser(player.uniqueId)!!.primaryGroup
        var result = getFor(LuckPermsProvider.get().groupManager.getGroup(primaryGroup))
        if (result == null) result = getFor(LuckPermsProvider.get().groupManager.getGroup("default"))
        return result
    }

    companion object {
        fun string(section: ConfigurationSection, group: Group?, def: String?): String {
            return section.getString(group!!.name, def)
        }

        fun string(section: ConfigurationSection, player: Player, def: String?): String {
            val primaryGroup = LuckPermsProvider.get().userManager.getUser(player.uniqueId)!!
                .primaryGroup
            return string(section, LuckPermsProvider.get().groupManager.getGroup(primaryGroup), def)
        }
    }
}