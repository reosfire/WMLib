package ru.reosfire.wmlib.yaml.common.wrappers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.reosfire.wmlib.text.IColorizer;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.text.Text;
import ru.reosfire.wmlib.yaml.common.Enchantment;
import ru.reosfire.wmlib.yaml.YamlConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemConfig extends YamlConfig implements WrapperConfig<ItemStack>
{
    public MaterialDataConfig MaterialData;
    public final int Amount;
    public final String Name;
    public final List<String> Lore;
    public final List<Enchantment> Enchantments;
    public final List<ItemFlag> Flags;

    public ItemConfig(ConfigurationSection section)
    {
        super(section);
        MaterialData = new MaterialDataConfig(getSection());
        Amount = getInt("Amount", 1);
        Name = getString("Name", "");
        Lore = getStringList("Lore", new ArrayList<>());
        Enchantments = getNestedConfigs(Enchantment::new, "Enchantments");

        Flags = new ArrayList<>();
        for (String flag : getStringList("Flags"))
        {
            Flags.add(ItemFlag.valueOf(flag.toUpperCase()));
        }
    }

    @Override
    public ItemStack unwrap()
    {
        return unwrap(Text::setColors);
    }

    public ItemStack unwrap(OfflinePlayer player, Replacement... replacements)
    {
        return unwrap(s -> Text.colorize(player, s, replacements));
    }

    public ItemStack unwrap(OfflinePlayer player, OfflinePlayer player1, Replacement... replacements)
    {
        return unwrap(s -> Text.colorize(player, player1, s, replacements));
    }

    public ItemStack unwrap(IColorizer colorizer)
    {
        ItemStack itemStack = new ItemStack(MaterialData.Material, Amount, MaterialData.Data);

        setTo(itemStack, colorizer);

        return itemStack;
    }

    public void setTo(ItemStack itemStack)
    {
        setTo(itemStack, Text::setColors);
    }

    public void setTo(ItemStack itemStack, OfflinePlayer player, Replacement... replacements)
    {
        setTo(itemStack, s -> Text.colorize(player, s, replacements));
    }

    public void setTo(ItemStack itemStack, OfflinePlayer player, OfflinePlayer player1, Replacement... replacements)
    {
        setTo(itemStack, s -> Text.colorize(player, player1, s, replacements));
    }

    public void setTo(ItemStack itemStack, IColorizer colorizer)
    {
        String headData = getString("HeadData");
        if(headData != null) setHeadData(itemStack, headData);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(colorizer.colorize(Name));
        itemMeta.setLore(IColorizer.colorize(colorizer, Lore));

        for (Enchantment enchantment : Enchantments)
        {
            enchantment.setTo(itemMeta, true);
        }
        for (ItemFlag flag : Flags)
        {
            itemMeta.addItemFlags(flag);
        }

        itemStack.setItemMeta(itemMeta);
    }

    private void setHeadData(ItemStack head, String data)
    {
        if (data == null) return;
        try
        {
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();

            GameProfile randomProfile = new GameProfile(UUID.randomUUID(), null);
            randomProfile.getProperties().put("textures", new Property("textures", data));

            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, randomProfile);
            head.setItemMeta(headMeta);
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException("Error while setting head data", e);
        }
    }
}