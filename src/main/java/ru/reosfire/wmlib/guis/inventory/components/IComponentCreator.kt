package ru.reosfire.wmlib.guis.inventory.components

import ru.reosfire.wmlib.guis.inventory.Gui
import ru.reosfire.wmlib.yaml.common.gui.ComponentConfig

fun interface IComponentCreator {
    fun create(config: ComponentConfig, gui: Gui): GuiComponent
}