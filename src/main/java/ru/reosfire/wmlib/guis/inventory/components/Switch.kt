package ru.reosfire.wmlib.guis.inventory.components

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import ru.reosfire.wmlib.guis.inventory.Gui
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.common.gui.SwitchConfig
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig
import java.time.Instant

class Switch<T> : GuiComponent {
    protected var elementIndex = 0
    protected var replacements: Array<out Replacement> = arrayOf()
    private val config: SwitchConfig
    private val elements: List<T>
    private var lastClick: Long = 0
    fun setElement(index: Int) {
        if (index < 0 || index >= elements.size) throw IndexOutOfBoundsException()
        val previous = current
        elementIndex = index
        onSwitch(previous, current)
    }

    fun setElement(element: T) {
        setElement(elements.indexOf(element))
    }

    constructor(config: SwitchConfig, gui: Gui?, elements: List<T>) : super(gui!!) {
        this.elements = elements
        this.config = config
    }

    constructor(config: SwitchConfig, gui: Gui?, elements: List<T>, initialElement: Int) : super(
        gui!!
    ) {
        this.elements = elements
        this.config = config
        if (initialElement < 0 || initialElement >= this.elements.size) throw IndexOutOfBoundsException()
        elementIndex = initialElement
    }

    val current: T
        get() = elements[elementIndex]

    protected fun renderElement(element: T, item: ItemConfig): ItemStack {
        return item.unwrap(gui.Player, *replacements)
    }

    protected fun onSwitch(previousElement: T, currentElement: T) {
        reRender(*replacements)
    }

    protected fun onClick(event: InventoryClickEvent?) {
        val nowTime = Instant.now().toEpochMilli()
        if (nowTime - lastClick < config.cooldown) return
        val previous = current
        val newIndex = elementIndex + 1
        elementIndex = newIndex % elements.size
        onSwitch(previous, current)
        lastClick = nowTime
    }

    private fun onItemClicked(event: InventoryClickEvent) {
        if (event.slot != config.index) return
        onClick(event)
    }

    override fun register() {
        addClickHandler { event: InventoryClickEvent -> onItemClicked(event) }
    }

    override fun renderTo(inventory: Inventory, vararg replacements: Replacement) {
        inventory.setItem(
            config.index, renderElement(
                elements[elementIndex],
                config.items[elementIndex % config.items.size]
            )
        )
        this.replacements = replacements
    }
}