package ru.reosfire.wmlib.yaml.common

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.util.Vector
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.yaml.common.wrappers.VectorConfig
import kotlin.math.max
import kotlin.math.min

class CubicAreaConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val firstPoint: VectorConfig = VectorConfig(getSection("FirstPoint"))
    val secondPoint: VectorConfig = VectorConfig(getSection("SecondPoint"))

    fun isInner(vector: Vector): Boolean {
        val minimum = getMinVector(firstPoint, secondPoint)
        val maximum = getMaxVector(firstPoint, secondPoint)
        return vector.isInAABB(minimum, maximum)
    }

    private fun getMinVector(a: VectorConfig, b: VectorConfig): Vector {
        return Vector(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z))
    }

    private fun getMaxVector(a: VectorConfig, b: VectorConfig): Vector {
        return Vector(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z))
    }
}