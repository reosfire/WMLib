package ru.reosfire.wmlib.guis.inventory

import org.bukkit.event.inventory.InventoryClickEvent

fun interface IClickEventHandler {
    fun handle(event: InventoryClickEvent)
}