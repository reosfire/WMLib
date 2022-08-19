package ru.reosfire.wmlib.animation.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Hashtable;

public class Solid
{
    protected Location Pivot;
    protected Hashtable<Vector, MaterialData> RelativeBlocks;

    public Location getLocation()
    {
        return Pivot;
    }

    public ArrayList<AnimationOperation> Shift(Vector shift)
    {
        Vector BlockShift = new Vector(shift.getBlockX(), shift.getBlockY(), shift.getZ());
        Location newPivot = Pivot.clone().add(BlockShift);
        World world = Pivot.getWorld();
        Hashtable<Block, MaterialData> operationsHashTable = new Hashtable<>();
        for (Vector vector : RelativeBlocks.keySet())
        {
            Block blockAt = world.getBlockAt(Pivot.clone().add(vector));
            operationsHashTable.put(blockAt, new MaterialData(Material.AIR, (byte) 0));
        }
        for (Vector vector : RelativeBlocks.keySet())
        {
            MaterialData blockShould = RelativeBlocks.get(vector);
            Block blockAt = world.getBlockAt(newPivot.clone().add(vector));
            if (!equals(blockShould, blockAt) || operationsHashTable.get(blockAt) != null)
            {
                operationsHashTable.put(blockAt, blockShould);
            }
        }
        ArrayList<AnimationOperation> operations = new ArrayList<>();
        operationsHashTable.forEach(((block, blockShould) -> operations.add(new AnimationOperation(block,
                blockShould))));
        Pivot = newPivot;
        return operations;
    }

    private boolean equals(MaterialData materialData, Block block)
    {
        return materialData.getItemType() == block.getType() && materialData.getData() == block.getData();
    }

    public ArrayList<AnimationOperation> Draw()
    {
        ArrayList<AnimationOperation> operations = new ArrayList<>();
        World world = Pivot.getWorld();
        for (Vector vector : RelativeBlocks.keySet())
        {
            Block blockAt = world.getBlockAt(Pivot.clone().add(vector));
            operations.add(new AnimationOperation(blockAt, RelativeBlocks.get(vector)));
        }
        return operations;
    }

    public ArrayList<Block> GetBlocks()
    {
        ArrayList<Block> blocks = new ArrayList<>();
        World world = Pivot.getWorld();
        for (Vector vector : RelativeBlocks.keySet())
        {
            blocks.add(world.getBlockAt(Pivot.clone().add(vector)));
        }
        return blocks;
    }

    public ArrayList<AnimationOperation> Clear()
    {
        ArrayList<AnimationOperation> operations = new ArrayList<>();
        World world = Pivot.getWorld();
        for (Vector vector : RelativeBlocks.keySet())
        {
            Block blockAt = world.getBlockAt(Pivot.clone().add(vector));
            operations.add(new AnimationOperation(blockAt, new MaterialData(Material.AIR, (byte) 0)));
        }
        return operations;
    }
}