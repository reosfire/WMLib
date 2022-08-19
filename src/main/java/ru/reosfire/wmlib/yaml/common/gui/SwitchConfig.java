package ru.reosfire.wmlib.yaml.common.gui;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig;
import ru.reosfire.wmlib.yaml.YamlConfig;

import java.util.List;

public class SwitchConfig extends YamlConfig
{
    public final int Index;
    public final List<ItemConfig> Items;
    public final Integer CoolDown;
    public SwitchConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Index = getInt("Index");
        Items = getNestedConfigs(ItemConfig::new, "Items");
        CoolDown = getInt("CoolDown", 0);
    }
}