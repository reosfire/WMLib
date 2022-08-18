package ru.reosfire.WMLib.Yaml.Default.Gui;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import ru.reosfire.WMLib.Text.Replacement;
import ru.reosfire.WMLib.Yaml.Default.Wrappers.ItemConfig;
import ru.reosfire.WMLib.Yaml.YamlConfig;

public class ButtonConfig extends YamlConfig
{
    public final ItemConfig Item;
    public final int Index;

    public ButtonConfig(ConfigurationSection section)
    {
        super(section);
        Item = new ItemConfig(getSection("Item"));
        Index = getInt("Index");
    }
    public void set(Inventory to, OfflinePlayer player, Replacement... replacements)
    {
        to.setItem(Index, Item.Unwrap(player, replacements));
    }
}