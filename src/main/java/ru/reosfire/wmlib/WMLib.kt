package ru.reosfire.wmlib

import org.bukkit.plugin.java.JavaPlugin

class WMLib : JavaPlugin() {
    private var instance: WMLib? = null

    fun getInstance(): WMLib? {
        return instance
    }

    override fun onEnable() {
        instance = this
    }
}