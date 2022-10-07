package ru.reosfire.wmlib.yaml.common.wrappers

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import ru.reosfire.wmlib.yaml.YamlConfig

class VectorConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection),
    WrapperConfig<Vector?> {
    val x: Double = getDouble("X")
    val y: Double = getDouble("Y")
    val z: Double = getDouble("Z")

    override fun unwrap(): Vector {
        return Vector(x, y, z)
    }

    fun unwrap(world: World?): Location {
        return unwrap().toLocation(world)
    }

    fun teleport(player: Player) {
        player.teleport(unwrap(player.world))
    }
}