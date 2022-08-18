package ru.reosfire.WMLib.Guis.Inventory.Components;

import org.bukkit.event.inventory.InventoryClickEvent;
import ru.reosfire.WMLib.Guis.Inventory.Gui;
import ru.reosfire.WMLib.Guis.Inventory.IClickEventHandler;
import ru.reosfire.WMLib.Yaml.Default.Gui.ButtonConfig;

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