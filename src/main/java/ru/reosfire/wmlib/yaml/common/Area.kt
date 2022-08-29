package ru.reosfire.wmlib.yaml.common

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.util.Vector
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.VectorConfig

class Area(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val FirstPoint: VectorConfig = VectorConfig(getSection("FirstPoint"))
    val SecondPoint: VectorConfig = VectorConfig(getSection("SecondPoint"))

    fun isInner(vector: Vector): Boolean {
        val minimum = Vector.getMinimum(FirstPoint.unwrap(), SecondPoint.unwrap())
        val maximum = Vector.getMaximum(FirstPoint.unwrap(), SecondPoint.unwrap())
        return vector.isInAABB(minimum, maximum)
    }
}