package ru.reosfire.wmlib.yaml.common.wrappers

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import ru.reosfire.wmlib.yaml.YamlConfig

class LocationConfig : YamlConfig, WrapperConfig<Location?> {
    val World: World
    val Vector: VectorConfig
    var Yaw = 0f
    var Pitch = 0f
    private var yawPitchSet = false

    constructor(configurationSection: ConfigurationSection?, world: World) : super(configurationSection) {
        World = world
        Vector = VectorConfig(section)
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

    constructor(configurationSection: ConfigurationSection?) : super(configurationSection) {
        World = Bukkit.getWorld(getString("World"))
        Vector = VectorConfig(section)
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

    override fun unwrap(): Location {
        return if (yawPitchSet) Location(
            World,
            Vector.unwrap().x,
            Vector.unwrap().y,
            Vector.unwrap().z, Yaw, Pitch
        ) else Location(
            World,
            Vector.unwrap().x,
            Vector.unwrap().y,
            Vector.unwrap().z
        )
    }

    fun teleport(player: Player) {
        player.teleport(unwrap())
    }
}