package ru.reosfire.wmlib.guis.inventory.components

import org.bukkit.inventory.Inventory
import ru.reosfire.wmlib.guis.inventory.Gui
import ru.reosfire.wmlib.guis.inventory.IClickEventHandler
import java.util.HashSet
import java.util.HashMap
import java.util.Locale
import ru.reosfire.wmlib.yaml.common.gui.ComponentConfig
import ru.reosfire.wmlib.yaml.common.gui.RawItemConfig
import ru.reosfire.wmlib.text.Replacement
import ru.reosfire.wmlib.yaml.common.gui.CommandButtonConfig

abstract class GuiComponent(protected val gui: Gui) {
    private val clickEventHandlers: MutableSet<IClickEventHandler> = HashSet()
    fun addComponent(component: GuiComponent?) {
        gui.addComponent(component!!)
    }

    fun reRender(vararg replacements: Replacement) {
        gui.redrawComponent(this, *replacements)
    }

    abstract fun register()
    open fun unregister() {
        for (clickEventHandler in clickEventHandlers) {
            gui.Events.removeClickHandler(clickEventHandler)
        }
    }

    protected fun addClickHandler(handler: IClickEventHandler) {
        gui.Events.addClickHandler(handler)
        clickEventHandlers.add(handler)
    }

    abstract fun renderTo(inventory: Inventory, vararg replacements: Replacement)

    companion object {
        private val creators = HashMap<String, IComponentCreator>()
        fun addCreator(type: String, creator: IComponentCreator) {
            creators[type] = creator
        }

        init {
            addCreator("RawItem".lowercase(Locale.getDefault())) { config: ComponentConfig, gui: Gui ->
                RawItem(RawItemConfig(config.section), gui)
            }
            addCreator("CommandButton".lowercase(Locale.getDefault())) { config: ComponentConfig, gui: Gui ->
                CommandButton(CommandButtonConfig(config.section), gui)
            }
        }

        fun create(config: ComponentConfig, gui: Gui): GuiComponent {
            return creators[config.type]!!.create(config, gui)
        }
    }
}