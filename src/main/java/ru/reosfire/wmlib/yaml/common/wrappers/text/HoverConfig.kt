package ru.reosfire.wmlib.yaml.common.wrappers.text

import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.extensions.colorize
import ru.reosfire.wmlib.extensions.setReplacements
import ru.reosfire.wmlib.text.IColorizer
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.WrapperConfig

class HoverConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection),
    WrapperConfig<HoverEvent?> {
    val Action = HoverEvent.Action.valueOf(getString("Action")!!)
    val Value = getColoredString("Value")!!

    fun unwrap(player: OfflinePlayer, vararg replacements: Replacement): HoverEvent {
        return unwrap{ it?.colorize(player, *replacements) }
    }

    fun unwrap(vararg replacements: Replacement): HoverEvent {
        return unwrap{ it?.setReplacements(*replacements) }
    }

    override fun unwrap(): HoverEvent {
        return HoverEvent(Action, arrayOf<BaseComponent>(TextComponent(Value)))
    }

    fun unwrap(colorizer: IColorizer): HoverEvent {
        return HoverEvent(Action, arrayOf(TextComponent(colorizer.colorize(Value))))
    }
}