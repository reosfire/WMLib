package ru.reosfire.wmlib.guis.inventory.components

import org.bukkit.event.inventory.InventoryClickEvent
import ru.reosfire.wmlib.yaml.common.gui.CommandButtonConfig
import ru.reosfire.wmlib.guis.inventory.Gui

class CommandButton(private val _config: CommandButtonConfig, gui: Gui) : Button(_config, gui) {
    override fun onClick(event: InventoryClickEvent) {
        for (command in _config.commands) {
            gui.Player.performCommand(command)
        }
    }
}