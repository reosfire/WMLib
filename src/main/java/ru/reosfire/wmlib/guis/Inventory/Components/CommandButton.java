package ru.reosfire.wmlib.guis.Inventory.Components;

import org.bukkit.event.inventory.InventoryClickEvent;
import ru.reosfire.wmlib.guis.Inventory.Gui;
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
    protected void OnClick(InventoryClickEvent event)
    {
        for (String command : _config.Command)
        {
            gui.Player.performCommand(command);
        }
    }
}