package ru.reosfire.wmlib.guis.inventory;

import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseEvent
{
    public final InventoryCloseEvent Event;
    public boolean cancelled = false;

    public CloseEvent(InventoryCloseEvent event)
    {
        Event = event;
    }
}