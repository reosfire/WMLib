package ru.reosfire.wmlib.guis.inventory.components

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import ru.reosfire.wmlib.yaml.common.gui.ItemSelectorConfig
import ru.reosfire.wmlib.guis.inventory.Gui
import ru.reosfire.wmlib.guis.inventory.IClickEventHandler
import ru.reosfire.wmlib.text.Replacement

abstract class ItemSelector(private val config: ItemSelectorConfig, gui: Gui) : GuiComponent(gui) {
    var currentItem: ItemStack? = null
    private lateinit var replacements: Array<out Replacement>
    private fun onClick(event: InventoryClickEvent) {
        if (event.slot != config.index) return
        val cursor = event.cursor.clone()
        if (isEmptyItem(cursor)) return
        if (cursor == currentItem) return
        onItemChange(currentItem, cursor)
        currentItem = cursor
        reRender(*replacements)
    }

    private fun isEmptyItem(item: ItemStack?): Boolean {
        return item == null || item.type == Material.AIR || item.amount == 0
    }

    protected abstract fun onItemChange(lastItem: ItemStack?, currentItem: ItemStack?)
    override fun register() {
        addClickHandler { event: InventoryClickEvent -> onClick(event) }
    }

    override fun renderTo(inventory: Inventory, vararg replacements: Replacement) {
        this.replacements = replacements
        val item = if (isEmptyItem(currentItem)) config.defaultItem.unwrap(gui.Player, *replacements) else currentItem!!
        inventory.setItem(config.index, item)
    }
}