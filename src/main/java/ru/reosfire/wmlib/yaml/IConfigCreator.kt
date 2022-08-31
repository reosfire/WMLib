package ru.reosfire.wmlib.yaml

import org.bukkit.configuration.ConfigurationSection

fun interface IConfigCreator<T : YamlConfig?> {
    fun create(configurationSection: ConfigurationSection?): T
}