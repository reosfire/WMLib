package ru.reosfire.WMLib.Guis.Inventory;

import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseEvent
{
    public final InventoryCloseEvent Event;
    public boolean Cancelled = false;

    public CloseEvent(InventoryCloseEvent event)
    {
        Event = event;
    }
}