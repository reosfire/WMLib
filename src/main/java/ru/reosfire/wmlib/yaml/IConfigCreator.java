package ru.reosfire.wmlib.yaml;

import org.bukkit.configuration.ConfigurationSection;

public interface IConfigCreator<T extends YamlConfig>
{
    T create(ConfigurationSection configurationSection);
}