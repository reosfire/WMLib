package ru.reosfire.WMLib.Yaml.Default.Gui;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.WMLib.Yaml.YamlConfig;

import java.util.Locale;

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