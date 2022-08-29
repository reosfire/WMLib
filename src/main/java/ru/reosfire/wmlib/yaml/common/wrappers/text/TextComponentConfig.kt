package ru.reosfire.wmlib.yaml.common.wrappers.text

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import ru.reosfire.wmlib.extensions.colorize
import ru.reosfire.wmlib.extensions.setColors
import ru.reosfire.wmlib.extensions.setReplacements
import ru.reosfire.wmlib.text.IColorizer
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.YamlConfig

class TextComponentConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    var TextContent: String? = null
    var Content: List<TextComponentConfig>? = null
    val ClickConfig: ClickConfig?
    val HoverConfig: HoverConfig?
    val Color: ChatColor?
    val Bold = getBoolean("Bold", false)
    val Italic = getBoolean("Italic", false)
    val Strikethrough = getBoolean("Strikethrough", false)
    val Underlined = getBoolean("Underlined", false)

    init {
        if (isList("Content")) {
            Content =
                getList({
                    TextComponentConfig(configurationSection)
                }, "Content")
            TextContent = null
        } else {
            TextContent = getColoredString("Content", null)
            Content = null
        }
        val clickSection = getSection("Click", null)
        ClickConfig = if (clickSection == null) null else ClickConfig(clickSection)
        val hoverSection = getSection("Hover", null)
        HoverConfig = if (hoverSection == null) null else HoverConfig(hoverSection)
        val color = getString("Color")
        Color = if (color == null) null else ChatColor.valueOf(color.uppercase())
    }

    fun send(receiver: CommandSender, vararg replacements: Replacement) {
        if (receiver is Player) {
            val player = receiver
            player.spigot().sendMessage(unwrap(player, *replacements))
        } else receiver.sendMessage(toString(*replacements))
    }

    fun unwrap(player: OfflinePlayer, vararg replacements: Replacement): TextComponent {
        return unwrap { it?.colorize(player, *replacements) }
    }

    fun unwrap(colorizer: IColorizer): TextComponent {
        val result: TextComponent
        if (TextContent != null) result =
            TextComponent(*TextComponent.fromLegacyText(colorizer.colorize(TextContent!!))) else {
            val subComponents = arrayOfNulls<TextComponent>(Content!!.size)
            for (i in subComponents.indices) subComponents[i] = Content!![i].unwrap(colorizer)
            result = TextComponent(*subComponents)
        }
        if (ClickConfig != null) result.clickEvent = ClickConfig.unwrap(colorizer)
        if (HoverConfig != null) result.hoverEvent = HoverConfig.unwrap(colorizer)
        if (Color != null) result.color = Color
        result.setBold(Bold)
        result.setItalic(Italic)
        result.setUnderlined(Underlined)
        result.setStrikethrough(Strikethrough)
        return result
    }

    fun toString(vararg replacements: Replacement): String {
        if (TextContent != null) (TextContent as String).setReplacements(*replacements).setColors();
        val resultBuilder = StringBuilder()
        for (subComponent in Content!!) {
            resultBuilder.append(subComponent.toString(*replacements))
        }
        return resultBuilder.toString().setColors();
    }

    override fun toString(): String {
        if (TextContent != null) return TextContent as String
        val resultBuilder = StringBuilder()
        for (subComponent in Content!!) {
            resultBuilder.append(subComponent.toString())
        }
        return resultBuilder.toString()
    }
}