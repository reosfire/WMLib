package ru.reosfire.wmlib.extensions

import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework
import java.util.concurrent.ThreadLocalRandom

fun Firework.setRandomEffects()
{
    val random = ThreadLocalRandom.current()

    val meta = fireworkMeta

    val builder = FireworkEffect.builder()
    builder.flicker(random.nextBoolean())
    builder.trail(random.nextBoolean())
    val types = FireworkEffect.Type.values()
    builder.with(types[random.nextInt(types.size)])
    builder.withColor(getRandomColor())
    builder.withFade(getRandomColor())

    meta.addEffect(builder.build())
    meta.power = random.nextInt(2) + 1
    fireworkMeta = meta
}

private fun getRandomColor(): Color
{
    return Color.fromBGR(ThreadLocalRandom.current().nextInt(1 shl 24))
}