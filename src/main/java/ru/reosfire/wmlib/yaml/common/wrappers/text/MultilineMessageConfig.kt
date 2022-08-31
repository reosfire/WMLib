package ru.reosfire.wmlib.yaml.common.wrappers.text

import org.bukkit.command.CommandSender
import ru.reosfire.wmlib.text.Replacement

class MultilineMessageConfig(val Messages: List<TextComponentConfig>) {
    fun send(sender: CommandSender, vararg replacements: Replacement) {
        for (message in Messages) {
            message.send(sender, *replacements)
        }
    }
}