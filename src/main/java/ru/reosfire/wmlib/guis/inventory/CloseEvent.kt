package ru.reosfire.wmlib.guis.inventory

import org.bukkit.event.inventory.InventoryCloseEvent

class CloseEvent(val Event: InventoryCloseEvent) {
    var cancelled = false
}