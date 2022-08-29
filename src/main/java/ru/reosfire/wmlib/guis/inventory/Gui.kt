package ru.reosfire.wmlib.guis.inventory

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import ru.reosfire.wmlib.extensions.colorize
import ru.reosfire.wmlib.guis.inventory.components.GuiComponent
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.common.gui.GuiConfig
import java.util.concurrent.CopyOnWriteArrayList

abstract class Gui(private val GuiConfig: GuiConfig, val Player: Player, plugin: JavaPlugin) : InventoryHolder {
    @JvmField
    val Events: EventsHandler
    private var inventory: Inventory? = null
    private val plugin: JavaPlugin
    private val components: MutableList<GuiComponent> = ArrayList()
    override fun getInventory(): Inventory {
        return inventory!!
    }

    protected abstract fun redraw(inventory: Inventory?)
    protected fun show(title: String, size: Int, vararg replacements: Replacement) {
        var title = title
        title = title.colorize(Player, *replacements)
        if (inventory == null || inventory!!.size != size || Player.openInventory == null || Player.openInventory.title != title) {
            inventory = Bukkit.createInventory(this, size, title)
            redraw(inventory)
            redrawComponents(*replacements)
            open()
        } else {
            redraw(inventory)
            redrawComponents(*replacements)
        }
    }

    protected fun open() {
        object : BukkitRunnable() {
            override fun run() {
                if (!Player.isOnline) return
                if (inventory === Player.openInventory.topInventory) return
                Player.openInventory(inventory)
            }
        }.runTask(plugin)
    }

    fun redrawComponents(vararg replacements: Replacement?) {
        for (component in components) {
            redrawComponent(component, *replacements)
        }
    }

    fun redrawComponent(component: GuiComponent, vararg replacements: Replacement?) {
        component.renderTo(inventory, *replacements)
    }

    fun addComponent(component: GuiComponent) {
        components.add(component)
        component.register()
    }

    fun removeComponent(component: GuiComponent) {
        components.remove(component)
        component.unregister()
    }

    protected fun show(vararg replacements: Replacement) {
        show(GuiConfig.Title!!, GuiConfig.Size, *replacements)
    }

    fun close() {
        Player.closeInventory()
    }

    init {
        Events = EventsHandler(plugin)
        for (component in GuiConfig.Components) {
            addComponent(GuiComponent.Create(component, this))
        }
        this.plugin = plugin
    }

    inner class EventsHandler(plugin: JavaPlugin) : Listener {
        private val clickHandlers: MutableList<IClickEventHandler> = CopyOnWriteArrayList()
        private val closeHandlers: MutableList<ICloseHandler> = CopyOnWriteArrayList()
        fun addClickHandler(handler: IClickEventHandler) {
            clickHandlers.add(handler)
        }

        fun removeClickHandler(handler: IClickEventHandler) {
            clickHandlers.remove(handler)
        }

        fun addCloseHandler(closeHandler: ICloseHandler) {
            closeHandlers.add(closeHandler)
        }

        fun removeCloseHandler(handler: ICloseHandler) {
            closeHandlers.remove(handler)
        }

        init {
            plugin.server.pluginManager.registerEvents(this, plugin)
        }

        private fun cancelHandling() {
            HandlerList.unregisterAll(this)
        }

        @EventHandler(priority = EventPriority.LOWEST)
        fun onClick(event: InventoryClickEvent) {
            if (inventory == null) return
            val clickedInventory = event.clickedInventory ?: return
            val holder = clickedInventory.holder
            if (holder !== this@Gui) return
            event.isCancelled = true
            val currentItem = event.currentItem ?: return
            for (clickHandler in clickHandlers) {
                clickHandler.Handle(event)
            }
        }

        @EventHandler(priority = EventPriority.LOWEST)
        fun onClickBottom(event: InventoryClickEvent) {
            if (inventory == null) return
            val clickedInventory = event.clickedInventory ?: return
            if (clickedInventory !== Player.openInventory.bottomInventory) return
            if (bannedActions.contains(event.action)) event.isCancelled = true
        }

        @EventHandler(priority = EventPriority.LOWEST)
        fun inventoryDragCanceller(event: InventoryDragEvent) {
            if (inventory == null) return
            if (event.inventory.holder !== this@Gui) return
            val rawSlots = event.rawSlots
            if (rawSlots.stream().noneMatch { e: Int -> e < inventory!!.size }) return
            event.isCancelled = true
        }

        @EventHandler(priority = EventPriority.LOWEST)
        fun inventoryPickupCanceller(event: InventoryPickupItemEvent) {
            if (inventory == null) return
            if (event.inventory.holder !== this@Gui) return
            event.isCancelled = true
        }

        @EventHandler(priority = EventPriority.LOWEST)
        fun inventoryMoveItemCanceller(event: InventoryMoveItemEvent) {
            if (inventory == null) return
            if (event.destination.holder !== this@Gui && event.source.holder !== this@Gui) return
            event.isCancelled = true
        }

        @EventHandler
        fun onClose(event: InventoryCloseEvent) {
            if (inventory == null) return
            if (inventory != event.inventory) return
            if (event.player !is Player || event.player != Player) return
            var cancelled = false
            for (closeHandler in closeHandlers) {
                val closeEvent = CloseEvent(event)
                closeHandler.Handle(closeEvent)
                if (closeEvent.cancelled) cancelled = true
            }
            if (cancelled) open() else cancelHandling()
        }
    }

    companion object {
        private val bannedActions: MutableSet<InventoryAction> = HashSet()

        init {
            bannedActions.add(InventoryAction.MOVE_TO_OTHER_INVENTORY)
            bannedActions.add(InventoryAction.COLLECT_TO_CURSOR)
        }
    }
}