package ru.reosfire.wmlib.extensions

import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import ru.reosfire.wmlib.yaml.IConfigCreator
import ru.reosfire.wmlib.yaml.YamlConfig

fun Plugin.registerListener(listener: Listener) {
    this.server.pluginManager.registerEvents(listener, this)
}

fun JavaPlugin.registerCommand(name: String, executor: CommandExecutor) {
    val pluginCommand = getCommand(name)
    pluginCommand.executor = executor
    if (executor is TabCompleter) pluginCommand.tabCompleter = executor
}

fun<T: YamlConfig?> Plugin.loadConfig(creator: IConfigCreator<T>): T {
    try {
        return creator.create(YamlConfig.loadOrCreate("config.yml", this))
    } catch (e: Exception){
        throw RuntimeException("Error while loading config", e)
    }
}