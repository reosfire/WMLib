package ru.reosfire.wmlib.commands.yaml.common.wrappers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.reosfire.wmlib.commands.yaml.YamlConfig;
import ru.reosfire.wmlib.commands.yaml.common.Enchantment;
import ru.reosfire.wmlib.text.IColorizer;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.text.Text;

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
    public ItemStack Unwrap()
    {
        return Unwrap(Text::SetColors);
    }

    public ItemStack Unwrap(OfflinePlayer player, Replacement... replacements)
    {
        return Unwrap(s -> Text.Colorize(player, s, replacements));
    }

    public ItemStack Unwrap(OfflinePlayer player, OfflinePlayer player1, Replacement... replacements)
    {
        return Unwrap(s -> Text.Colorize(player, player1, s, replacements));
    }

    public ItemStack Unwrap(IColorizer colorizer)
    {
        ItemStack itemStack = new ItemStack(MaterialData.Material, Amount, MaterialData.Data);

        SetTo(itemStack, colorizer);

        return itemStack;
    }

    public void SetTo(ItemStack itemStack)
    {
        SetTo(itemStack, Text::SetColors);
    }

    public void SetTo(ItemStack itemStack, OfflinePlayer player, Replacement... replacements)
    {
        SetTo(itemStack, s -> Text.Colorize(player, s, replacements));
    }

    public void SetTo(ItemStack itemStack, OfflinePlayer player, OfflinePlayer player1, Replacement... replacements)
    {
        SetTo(itemStack, s -> Text.Colorize(player, player1, s, replacements));
    }

    public void SetTo(ItemStack itemStack, IColorizer colorizer)
    {
        String headData = getString("HeadData");
        if(headData != null) setHeadData(itemStack, headData);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(colorizer.Colorize(Name));
        itemMeta.setLore(IColorizer.Colorize(colorizer, Lore));

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