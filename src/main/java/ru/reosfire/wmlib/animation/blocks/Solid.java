package ru.reosfire.wmlib.animation.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Solid
{
    protected Location pivot;
    protected Hashtable<Vector, MaterialData> relativeBlocks;

    public Location getLocation()
    {
        return pivot;
    }

    public ArrayList<AnimationOperation> shift(Vector shift)
    {
        Vector BlockShift = new Vector(shift.getBlockX(), shift.getBlockY(), shift.getZ());
        Location newPivot = pivot.clone().add(BlockShift);
        World world = pivot.getWorld();
        Hashtable<Block, MaterialData> operationsHashTable = new Hashtable<>();
        for (Vector vector : relativeBlocks.keySet())
        {
            Block blockAt = world.getBlockAt(pivot.clone().add(vector));
            operationsHashTable.put(blockAt, new MaterialData(Material.AIR, (byte) 0));
        }
        for (Vector vector : relativeBlocks.keySet())
        {
            MaterialData blockShould = relativeBlocks.get(vector);
            Block blockAt = world.getBlockAt(newPivot.clone().add(vector));
            if (!equals(blockShould, blockAt) || operationsHashTable.get(blockAt) != null)
            {
                operationsHashTable.put(blockAt, blockShould);
            }
        }
        ArrayList<AnimationOperation> operations = new ArrayList<>();
        operationsHashTable.forEach(((block, blockShould) -> operations.add(new AnimationOperation(block,
                blockShould))));
        pivot = newPivot;
        return operations;
    }

    private boolean equals(MaterialData materialData, Block block)
    {
        return materialData.getItemType() == block.getType() && materialData.getData() == block.getData();
    }

    public ArrayList<AnimationOperation> draw()
    {
        ArrayList<AnimationOperation> operations = new ArrayList<>();
        World world = pivot.getWorld();
        for (Vector vector : relativeBlocks.keySet())
        {
            Block blockAt = world.getBlockAt(pivot.clone().add(vector));
            operations.add(new AnimationOperation(blockAt, relativeBlocks.get(vector)));
        }
        return operations;
    }

    public ArrayList<Block> getBlocks()
    {
        ArrayList<Block> blocks = new ArrayList<>();
        World world = pivot.getWorld();
        for (Vector vector : relativeBlocks.keySet())
        {
            blocks.add(world.getBlockAt(pivot.clone().add(vector)));
        }
        return blocks;
    }

    public ArrayList<AnimationOperation> clear()
    {
        ArrayList<AnimationOperation> operations = new ArrayList<>();
        World world = pivot.getWorld();
        for (Vector vector : relativeBlocks.keySet())
        {
            Block blockAt = world.getBlockAt(pivot.clone().add(vector));
            operations.add(new AnimationOperation(blockAt, new MaterialData(Material.AIR, (byte) 0)));
        }
        return operations;
    }
}