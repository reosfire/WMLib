package ru.reosfire.wmlib.guis.inventory;

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