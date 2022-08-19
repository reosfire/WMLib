package ru.reosfire.wmlib.commands.yaml.common.gui;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.commands.yaml.YamlConfig;
import ru.reosfire.wmlib.commands.yaml.common.wrappers.ItemConfig;

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