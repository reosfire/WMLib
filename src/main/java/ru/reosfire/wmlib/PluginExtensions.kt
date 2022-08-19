package ru.reosfire.wmlib

import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

class PluginExtensions
{
    fun Plugin.registerListener(listener: Listener)
    {
        this.server.pluginManager.registerEvents(listener, this)
    }
}