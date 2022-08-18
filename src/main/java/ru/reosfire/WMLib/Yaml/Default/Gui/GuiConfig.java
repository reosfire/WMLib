package ru.reosfire.WMLib.Yaml.Default.Gui;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.WMLib.Yaml.YamlConfig;

import java.util.List;

public abstract class GuiConfig extends YamlConfig
{
    public final String Title;
    public final int Size;
    public final List<ComponentConfig> Components;

    public GuiConfig(ConfigurationSection section)
    {
        super(section);
        Title = getColoredString("Title");
        Size = getInt("Size");
        Components = getNestedConfigs(ComponentConfig::new,"Components");
    }
    public ButtonConfig getButton(String path)
    {
        return new ButtonConfig(getSection(path));
    }
}