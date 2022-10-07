package ru.reosfire.wmlib.yaml.common;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ru.reosfire.wmlib.yaml.YamlConfig;
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Inventory extends YamlConfig
{
    Hashtable<Integer, ItemStack> Items;

    public List<ItemStack> getItems()
    {
        return (List<ItemStack>) Items.values();
    }

    public Inventory(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Items = new Hashtable<>();
        Set<String> keys = configurationSection.getKeys(false);
        if (keys == null) return;
        for (String key : keys)
        {
            int inventoryIndex;
            ConfigurationSection itemSection = getSection(key);
            ItemStack itemStack = new ItemConfig(itemSection).unwrap();
            try
            {
                inventoryIndex = Integer.parseInt(key);
            }
            catch (Exception e)
            {
                inventoryIndex = itemSection.getInt("InventoryIndex", 0);
            }
            Items.put(inventoryIndex, itemStack);
        }
    }

    public void set(Player player)
    {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        for (int itemKey : Items.keySet())
        {
            inventory.setItem(itemKey, Items.get(itemKey));
        }
    }

    public void set(Iterable<Player> players)
    {
        for (Player player : players)
        {
            set(player);
        }
    }
}