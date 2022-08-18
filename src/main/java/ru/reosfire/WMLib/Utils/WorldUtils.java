package ru.reosfire.WMLib.Utils;

import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

public class WorldUtils
{
    public static World LoadWorld(String referencePath, String resultPath) throws IOException
    {
        File exampleFile = new File(referencePath);

        int i = 0;
        File worldFile;
        do
        {
            worldFile = new File(resultPath + i);
            i++;
        }
        while (worldFile.exists());
        if(worldFile.exists())
        worldFile.mkdirs();

        FileUtils.copyDirectory(exampleFile, worldFile);

        WorldCreator worldCreator = new WorldCreator(worldFile.getPath());
        return worldCreator.createWorld();
    }
    public static void DeleteWorld(World world) throws IOException
    {
        File worldFolder = world.getWorldFolder();
        FileUtils.deleteDirectory(worldFolder);
    }
}