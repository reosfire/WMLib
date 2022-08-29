package ru.reosfire.wmlib.animation.blocks

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.material.MaterialData
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import ru.reosfire.wmlib.extensions.NMSSet
import ru.reosfire.wmlib.extensions.setFor

class AnimationOperation(private val block: Block, private val materialData: MaterialData) {
    fun execute() {
        block.NMSSet(materialData)
    }

    fun execute(player: Player) {
        block.setFor(player, materialData)
    }

    companion object {
        fun execute(plugin: Plugin, vararg operations: AnimationOperation) {
            object : BukkitRunnable() {
                override fun run() {
                    for (operation in operations) {
                        operation.execute()
                    }
                }
            }.runTask(plugin)
        }
    }
}