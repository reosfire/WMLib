package ru.reosfire.WMLib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class Startup extends JavaPlugin
{
    private static Startup Instance;
    public static final Random Random = new Random();

    public static Startup getInstance()
    {
        return Instance;
    }

    @Override
    public void onEnable()
    {
        Instance = this;
    }

    @Override
    public void onDisable()
    {

    }
}