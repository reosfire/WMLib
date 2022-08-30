package ru.reosfire.wmlib.yaml.common;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.ChunkSection;
import net.minecraft.server.v1_12_R1.IBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import ru.reosfire.wmlib.extensions.StringExtensionsKt;
import ru.reosfire.wmlib.yaml.YamlConfig;

import java.lang.reflect.InvocationTargetException;

public class Block extends YamlConfig
{
    public final Material Material;
    public final byte Data;

    public MaterialData getMaterialData()
    {
        return new MaterialData(Material, Data);
    }

    public Block(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Material = org.bukkit.Material.matchMaterial(configurationSection.getString("Material"));
        Data = (byte) configurationSection.getInt("Data");
    }

    public boolean equals(org.bukkit.block.Block block)
    {
        return (Material == block.getType()) && (Data == block.getData());
    }

    public void set(Location location)
    {
        set(location.getBlock());
    }

    public void set(org.bukkit.block.Block block)
    {
        block.setType(Material);
        block.setData(Data);
    }

    public void set(org.bukkit.block.Block block, boolean applyPhysics)
    {
        block.setType(Material, applyPhysics);
        block.setData(Data, applyPhysics);
    }
}