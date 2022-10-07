package ru.reosfire.wmlib.guis.inventory.components

import org.bukkit.inventory.Inventory
import ru.reosfire.wmlib.guis.inventory.Gui
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.common.gui.RawItemConfig

class RawItem(private val Config: RawItemConfig, gui: Gui) : GuiComponent(gui) {
    override fun renderTo(inventory: Inventory, vararg replacements: Replacement) {
        for (index in Config.indexes!!) {
            inventory.setItem(index, Config.item.unwrap(gui.Player, *replacements))
        }
    }

    override fun register() {}
}