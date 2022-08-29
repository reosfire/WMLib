package ru.reosfire.wmlib.yaml.common.wrappers.text

import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.extensions.colorize
import ru.reosfire.wmlib.extensions.setReplacements
import ru.reosfire.wmlib.text.IColorizer
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.WrapperConfig

class ClickConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection),
    WrapperConfig<ClickEvent?> {
    val Action = ClickEvent.Action.valueOf(getString("Action")!!)
    val Value = getColoredString("Value")

    fun unwrap(player: OfflinePlayer, vararg replacements: Replacement): ClickEvent {
        return unwrap{ it?.colorize(player, *replacements) }
    }

    fun unwrap(vararg replacements: Replacement): ClickEvent {
        return unwrap{ it?.setReplacements(*replacements) }
    }

    override fun unwrap(): ClickEvent {
        return ClickEvent(Action, Value)
    }

    fun unwrap(colorizer: IColorizer): ClickEvent {
        return ClickEvent(Action, colorizer.colorize(Value))
    }
}