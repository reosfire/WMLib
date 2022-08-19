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

    public void NMSSet(Location location)
    {
        NMSSet(location, getMaterialData());
    }

    public void NMSSet(org.bukkit.block.Block block)
    {
        NMSSet(block, getMaterialData());
    }

    public static void NMSSet(Location location, MaterialData materialData)
    {
        org.bukkit.Material material = materialData.getItemType();
        byte data = materialData.getData();
        net.minecraft.server.v1_12_R1.Chunk chunk = ((CraftChunk) location.getChunk()).getHandle();
        BlockPosition blockPosition = new BlockPosition(location.getX(), location.getY(), location.getZ());
        IBlockData blockData = net.minecraft.server.v1_12_R1.Block.getByCombinedId(material.getId() + (data << 12));

        ChunkSection chunkSection = chunk.getSections()[blockPosition.getY() >> 4];
        chunkSection.getBlocks().setBlock(blockPosition.getX() & 15, blockPosition.getY() & 15,
                blockPosition.getZ() & 15, blockData);
        ((CraftWorld) location.getWorld()).getHandle().notify(blockPosition, blockData, blockData, 0);
    }

    public static void NMSSet(org.bukkit.block.Block block, MaterialData materialData)
    {
        NMSSet(block.getLocation(), materialData);
    }

    public static void setFor(Player player, org.bukkit.block.Block block, MaterialData materialData)
    {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
        packet.getModifier().writeDefaults();
        packet.getBlockPositionModifier().write(0,
                new com.comphenix.protocol.wrappers.BlockPosition(block.getX(), block.getY(), block.getZ()));
        packet.getBlockData().write(0, WrappedBlockData.createData(materialData.getItemType(), materialData.getData()));
        try
        {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static void crackFor(Player player, org.bukkit.block.Block block, int level, int entityId)
    {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
        packet.getBlockPositionModifier().write(0, new com.comphenix.protocol.wrappers.BlockPosition(block.getX(),
                block.getY(), block.getZ()));
        packet.getIntegers()
                .write(0, entityId)
                .write(1, level);
        try
        {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static <T extends Player> void crackFor(Iterable<T> players, org.bukkit.block.Block block, int level, int entityId)
    {
        for (T player : players)
        {
            crackFor(player, block, level, entityId);
        }
    }
}