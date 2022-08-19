package ru.reosfire.wmlib.guis.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface IClickEventHandler
{
    void Handle(InventoryClickEvent event);
}