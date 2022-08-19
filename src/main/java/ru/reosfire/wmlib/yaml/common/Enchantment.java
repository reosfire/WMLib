package ru.reosfire.wmlib.yaml.common;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import ru.reosfire.wmlib.yaml.YamlConfig;

public class Enchantment extends YamlConfig
{
    public final org.bukkit.enchantments.Enchantment Type;
    public final int Level;
    public Enchantment(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Type = org.bukkit.enchantments.Enchantment.getByName(getString("Type"));
        Level = getInt("Level", 1);
    }
    public void setTo(ItemMeta meta, boolean force)
    {
        meta.addEnchant(Type, Level, force);
    }
}