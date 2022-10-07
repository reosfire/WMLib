package ru.reosfire.wmlib.yaml.common

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.material.MaterialData
import ru.reosfire.wmlib.yaml.YamlConfig

class Block(configurationSection: ConfigurationSection) : YamlConfig(configurationSection) {
    val Material: Material
    val Data: Byte
    val materialData: MaterialData
        get() = MaterialData(Material, Data)

    init {
        Material = org.bukkit.Material.matchMaterial(configurationSection.getString("Material"))
        Data = configurationSection.getInt("Data").toByte()
    }

    fun equals(block: Block): Boolean {
        return Material == block.type && Data == block.data
    }

    fun set(location: Location) {
        set(location.block)
    }

    fun set(block: Block) {
        block.type = Material
        block.data = Data
    }

    operator fun set(block: Block, applyPhysics: Boolean) {
        block.setType(Material, applyPhysics)
        block.setData(Data, applyPhysics)
    }
}