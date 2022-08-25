package ru.reosfire.wmlib.random

import io.netty.util.internal.ThreadLocalRandom
import java.util.Random

interface DropList<T> {
    fun add(element: Element<T>)
    fun add(item: T, chance: Double) {
        add(Element(item, chance))
    }

    fun remove(element: Element<T>)
    fun removeAt(index: Int)

    fun getRandom(random: Random): T
    fun getRandom(): T {
        return getRandom(ThreadLocalRandom.current())
    }

    class Element<T>(val item: T, val chance: Double)
}