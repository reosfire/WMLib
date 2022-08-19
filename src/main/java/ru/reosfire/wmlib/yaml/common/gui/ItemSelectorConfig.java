package ru.reosfire.wmlib.yaml.common.gui;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig;
import ru.reosfire.wmlib.yaml.YamlConfig;

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