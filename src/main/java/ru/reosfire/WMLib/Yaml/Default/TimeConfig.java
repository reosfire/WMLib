package ru.reosfire.WMLib.Yaml.Default;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.WMLib.Yaml.YamlConfig;

public class TimeConfig extends YamlConfig
{
    public final long Seconds;
    public TimeConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        long seconds = configurationSection.getInt("Seconds", 0);
        seconds += configurationSection.getLong("Minutes", 0) * 60;
        seconds += configurationSection.getLong("Hours", 0) * 60 * 60;
        seconds += configurationSection.getLong("Days", 0) * 60 * 60 * 24;

        Seconds = seconds;
    }
}