package ru.reosfire.wmlib.yaml.common.wrappers

import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.material.MaterialData
import ru.reosfire.wmlib.yaml.YamlConfig

class MaterialDataConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection),
    WrapperConfig<MaterialData?> {
    val material: Material = org.bukkit.Material.getMaterial(getString("Material"))
    val data: Byte = getByte("Data", 0.toByte())

    override fun unwrap(): MaterialData {
        return try {
            if (data > 0) MaterialData(material, data) else MaterialData(material)
        } catch (e: Exception) {
            MaterialData(material)
        }
    }
}