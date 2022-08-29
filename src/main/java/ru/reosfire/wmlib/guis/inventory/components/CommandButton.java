package ru.reosfire.wmlib.guis.inventory.components;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.reosfire.wmlib.guis.inventory.Gui;
import ru.reosfire.wmlib.yaml.common.gui.CommandButtonConfig;

public class CommandButton extends Button
{
    private final CommandButtonConfig _config;
    public CommandButton(CommandButtonConfig config, Gui gui)
    {
        super(config, gui);
        _config = config;
    }

    @Override
    protected void onClick(InventoryClickEvent event)
    {
        for (String command : _config.Command)
        {
            gui.getPlayer().performCommand(command);
        }
    }
}