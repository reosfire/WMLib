package ru.reosfire.WMLib.Yaml;

import org.bukkit.configuration.ConfigurationSection;

public interface IConfigCreator<T extends YamlConfig>
{
    T Create(ConfigurationSection configurationSection);
}