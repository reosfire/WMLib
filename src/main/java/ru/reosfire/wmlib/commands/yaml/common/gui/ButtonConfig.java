package ru.reosfire.wmlib.commands.yaml.common.gui;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import ru.reosfire.wmlib.commands.yaml.YamlConfig;
import ru.reosfire.wmlib.commands.yaml.common.wrappers.ItemConfig;
import ru.reosfire.wmlib.text.Replacement;

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