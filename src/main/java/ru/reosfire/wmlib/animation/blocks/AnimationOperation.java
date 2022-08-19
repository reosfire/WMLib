package ru.reosfire.wmlib.animation.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import ru.reosfire.wmlib.Startup;

public class AnimationOperation
{
    private final Block block;
    private final MaterialData materialData;

    public AnimationOperation(Block block, MaterialData materialData)
    {
        this.block = block;
        this.materialData = materialData;
    }

    public AnimationOperation(Block block, ru.reosfire.wmlib.yaml.common.Block blockData)
    {
        this(block, blockData.getMaterialData());
    }

    public void Execute()
    {
        ru.reosfire.wmlib.yaml.common.Block.NMSSet(block, materialData);
    }
    public void Execute(Player player)
    {
        ru.reosfire.wmlib.yaml.common.Block.SetFor(player, block, materialData);
    }

    public static void Execute(AnimationOperation... operations)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (AnimationOperation operation : operations)
                {
                    operation.Execute();
                }
            }
        }.runTask(Startup.getInstance());
    }
}