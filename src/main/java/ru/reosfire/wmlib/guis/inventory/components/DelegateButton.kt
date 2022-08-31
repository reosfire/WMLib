package ru.reosfire.wmlib.guis.inventory.components

import org.bukkit.event.inventory.InventoryClickEvent
import ru.reosfire.wmlib.yaml.common.gui.ButtonConfig
import ru.reosfire.wmlib.guis.inventory.Gui
import ru.reosfire.wmlib.guis.inventory.IClickEventHandler

class DelegateButton(config: ButtonConfig, gui: Gui, private val delegate: IClickEventHandler) : Button(config, gui) {
    override fun onClick(event: InventoryClickEvent) {
        delegate.handle(event)
    }
}