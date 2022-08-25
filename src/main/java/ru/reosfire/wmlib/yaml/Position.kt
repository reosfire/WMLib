package ru.reosfire.wmlib.yaml

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class Position(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection) {
    val X: Double = getDouble("X")
    val Y: Double = getDouble("Y")
    val Z: Double = getDouble("Z")
    private var Yaw = 0f
    private var Pitch = 0f
    private var yawPitchSet = false

    init {
        val yaw = getString("Yaw")
        val pitch = getString("Pitch")

        if (!(yaw == null || pitch == null)) {
            Yaw = yaw.toFloat()
            Pitch = pitch.toFloat()
            yawPitchSet = true
        } else {
            Yaw = 0f
            Pitch = 0f
        }
    }

    fun toVector(): Vector {
        return Vector(X, Y, Z)
    }

    fun toLocation(world: World): Location {
        return if (yawPitchSet) Location(world, X, Y, Z, Yaw, Pitch) else Location(world, X, Y, Z)
    }

    fun teleport(player: Player) {
        val allowFlight = player.allowFlight
        if (!allowFlight) {
            player.allowFlight = true
            player.teleport(toLocation(player.world))
            player.allowFlight = false
        }
        else player.teleport(toLocation(player.world))
    }
}