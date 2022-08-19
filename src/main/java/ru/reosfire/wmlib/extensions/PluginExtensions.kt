package ru.reosfire.wmlib.extensions

import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

fun Plugin.registerListener(listener: Listener)
{
    this.server.pluginManager.registerEvents(listener, this)
}