package ru.reosfire.wmlib.yaml.common.gui

import org.bukkit.configuration.ConfigurationSection

class CommandButtonConfig(configurationSection: ConfigurationSection?) : ButtonConfig(configurationSection) {
    val commands = getStringList("Commands")!!
}