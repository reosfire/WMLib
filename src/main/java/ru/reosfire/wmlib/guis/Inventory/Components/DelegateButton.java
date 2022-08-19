package ru.reosfire.wmlib.guis.Inventory.Components;

import org.bukkit.event.inventory.InventoryClickEvent;
import ru.reosfire.wmlib.guis.Inventory.Gui;
import ru.reosfire.wmlib.guis.Inventory.IClickEventHandler;
import ru.reosfire.wmlib.commands.yaml.common.gui.ButtonConfig;

public class DelegateButton extends Button
{
    private final IClickEventHandler Delegate;
    public DelegateButton(ButtonConfig config, Gui gui, IClickEventHandler delegate)
    {
        super(config, gui);
        Delegate = delegate;
    }

    @Override
    protected void OnClick(InventoryClickEvent event)
    {
        Delegate.Handle(event);
    }
}