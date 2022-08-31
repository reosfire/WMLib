package ru.reosfire.wmlib.guis.inventory.components

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import ru.reosfire.wmlib.yaml.common.gui.ButtonConfig
import ru.reosfire.wmlib.guis.inventory.Gui
import ru.reosfire.wmlib.text.Replacement

abstract class Button(private val config: ButtonConfig, gui: Gui) : GuiComponent(gui) {
    protected abstract fun onClick(event: InventoryClickEvent)
    private fun onItemClicked(event: InventoryClickEvent) {
        if (event.slot != config.index) return
        onClick(event)
    }

    override fun register() {
        addClickHandler { event: InventoryClickEvent -> onItemClicked(event) }
    }

    override fun unregister() {
        super.unregister()
        gui.inventory.setItem(config.index, null)
    }

    override fun renderTo(inventory: Inventory, vararg replacements: Replacement) {
        inventory.setItem(config.index, config.item.unwrap(gui.Player, *replacements))
    }
}