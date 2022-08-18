package ru.reosfire.WMLib.Yaml.Default.Gui;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.WMLib.Yaml.Default.Wrappers.ItemConfig;
import ru.reosfire.WMLib.Yaml.YamlConfig;

import java.util.List;

public class RawItemConfig extends ComponentConfig
{
    public final List<Integer> Indexes;
    public final ItemConfig Item;
    public RawItemConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Indexes = getIntegerList("Indexes");
        Item = new ItemConfig(getSection("Item"));
    }
}