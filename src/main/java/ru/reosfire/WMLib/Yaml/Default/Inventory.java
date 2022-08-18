package ru.reosfire.WMLib.Yaml.Default;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ru.reosfire.WMLib.Yaml.Default.Wrappers.ItemConfig;
import ru.reosfire.WMLib.Yaml.YamlConfig;

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

    /**
     * @param configurationSection Configuration element whose children are int numbers and could be parsed as Item
     *                             or children contain "InventoryIndex: int" field.
     */
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
            ItemStack itemStack = new ItemConfig(itemSection).Unwrap();
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

    public void Set(Player player)
    {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        for (int itemKey : Items.keySet())
        {
            inventory.setItem(itemKey, Items.get(itemKey));
        }
    }

    public void Set(Iterable<Player> players)
    {
        for (Player player : players)
        {
            Set(player);
        }
    }
}