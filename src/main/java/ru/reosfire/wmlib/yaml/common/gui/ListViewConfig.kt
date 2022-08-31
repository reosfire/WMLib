package ru.reosfire.wmlib.yaml.common.gui;

import org.bukkit.configuration.ConfigurationSection;

public class ListViewConfig extends ComponentConfig
{
    public final int StartIndex;
    public final int EndIndex;
    public final ButtonConfig BackButton;
    public final ButtonConfig ForwardButton;
    public ListViewConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        StartIndex = getInt("StartIndex");
        EndIndex = getInt("EndIndex");
        BackButton = new ButtonConfig(getSection("BackwardButton"));
        ForwardButton = new ButtonConfig(getSection("ForwardButton"));
    }
}