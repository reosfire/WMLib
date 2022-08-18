package ru.reosfire.WMLib.Yaml.Default.Gui;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.WMLib.Yaml.Default.Gui.ComponentConfig;
import ru.reosfire.WMLib.Yaml.Default.Wrappers.ItemConfig;
import ru.reosfire.WMLib.Yaml.YamlConfig;

public class ItemSelectorConfig extends YamlConfig
{
    public final int Index;
    public final ItemConfig DefaultItem;
    public ItemSelectorConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Index = getInt("Index");
        DefaultItem = new ItemConfig(getSection("DefaultItem"));
    }
}