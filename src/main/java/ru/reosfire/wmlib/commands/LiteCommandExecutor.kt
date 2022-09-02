package ru.reosfire.wmlib.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

abstract class LiteCommandExecutor: CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender == null || args == null) return true
        onCommand(sender, args)
        return true
    }

    protected abstract fun onCommand(sender: CommandSender, args: Array<out String>)
}