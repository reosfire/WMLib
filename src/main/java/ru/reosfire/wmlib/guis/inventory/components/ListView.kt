package ru.reosfire.wmlib.guis.inventory.components

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import ru.reosfire.wmlib.guis.inventory.Gui
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.common.gui.ListViewConfig
import java.util.*
import kotlin.math.*

abstract class ListView<T> private constructor(private val Config: ListViewConfig, gui: Gui) : GuiComponent(gui) {
    private val indexes: MutableList<Int> = ArrayList()
    private val indexesData = HashMap<Int, T?>()
    private var dataProvider: IDataProvider<T>? = null
    protected var index = 0
    private val backwardButton: Button = BackwardButton()
    private val forwardButton: Button = ForwardButton()

    init {
        val start = min(Config.startIndex, Config.endIndex)
        val end = max(Config.startIndex, Config.endIndex)
        val startX = start % 9
        val endX = end % 9
        for (i in start..end) {
            val x = i % 9
            if (x in startX..endX) indexes.add(i)
        }
    }

    constructor(config: ListViewConfig, gui: Gui, dataProvider: IDataProvider<T>?) : this(config, gui) {
        this.dataProvider = dataProvider
    }

    private fun onMenuItemClicked(event: InventoryClickEvent) {
        if (Arrays.binarySearch(indexes.toTypedArray(), event.slot) < 0) return
        onElementClick(indexesData[event.slot], event)
    }

    override fun register() {
        gui.addComponent(backwardButton)
        gui.addComponent(forwardButton)
        addClickHandler { event: InventoryClickEvent -> onMenuItemClicked(event) }
    }

    override fun unregister() {
        super.unregister()
        gui.removeComponent(forwardButton)
        gui.removeComponent(backwardButton)
    }

    protected fun onElementClick(element: T?, event: InventoryClickEvent?) {}

    val page: Int
        get() {
            var maxPage = dataProvider!!.size / indexes.size + if (dataProvider!!.size % indexes.size == 0) 0 else 1
            maxPage = max(1, maxPage)
            return abs(index % maxPage)
        }

    override fun renderTo(inventory: Inventory, vararg replacements: Replacement) {
        val dataPage = getDataPage(page)
        for (i in indexes.indices) {
            val data = if (i < dataPage!!.size) dataPage[i] else null
            val item = data?.let { renderElement(it) }
            inventory.setItem(indexes[i], item)
            indexesData[indexes[i]] = data
        }
    }

    private fun getDataPage(page: Int): List<T>? {
        val pageSize = indexes.size
        val startIndex = min(pageSize * page, dataProvider!!.size)
        val endIndex = min(pageSize * (page + 1), dataProvider!!.size)
        return dataProvider!!.getData(startIndex, endIndex)
    }

    protected abstract fun renderElement(element: T): ItemStack

    inner class BackwardButton : Button(Config.backButton, gui) {
        override fun onClick(event: InventoryClickEvent) {
            index--
            this@ListView.reRender()
        }
    }

    inner class ForwardButton : Button(Config.forwardButton, gui) {
        override fun onClick(event: InventoryClickEvent) {
            index++
            this@ListView.reRender()
        }
    }
}