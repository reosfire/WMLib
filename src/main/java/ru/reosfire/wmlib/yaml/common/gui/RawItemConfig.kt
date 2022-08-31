package ru.reosfire.wmlib.yaml.common.gui;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig;

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