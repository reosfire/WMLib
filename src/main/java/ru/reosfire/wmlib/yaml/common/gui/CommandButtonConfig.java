package ru.reosfire.wmlib.yaml.common.gui;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class CommandButtonConfig extends ButtonConfig
{
    public final List<String> Command;

    public CommandButtonConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Command = getStringList("Commands");
    }
}