package ru.reosfire.wmlib.commands.yaml.common.gui;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.commands.yaml.YamlConfig;

public class ComponentConfig extends YamlConfig
{
    public final String Type;
    public ComponentConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        String type = getString("Type", null);
        Type = type == null ? null : type.toLowerCase();
    }
}