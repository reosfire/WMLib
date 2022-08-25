package ru.reosfire.wmlib.random

import java.util.*
import kotlin.collections.ArrayList

class LinearDropList<T>: DropList<T> {
    override val size get() = _elements.size
    override val elements get() = _elements
    override val chancesSum get() = _chancesSum

    private val _elements = ArrayList<DropList.Element<T>>()
    private var _chancesSum = 0.0

    override fun add(element: DropList.Element<T>) {
        _elements.add(element)
        _chancesSum += element.chance
    }

    override fun remove(element: DropList.Element<T>) {
        _elements.remove(element)
        _chancesSum -= element.chance
    }

    override fun removeAt(index: Int) {
        _chancesSum -= _elements.removeAt(index).chance
    }

    override fun getRandom(random: Random): T {
        val generated = random.nextDouble() * chancesSum
        var sum = _elements.first().chance
        var result = 1

        while (sum < generated) {
            sum += _elements[result].chance
            result++
        }

        return _elements[result - 1].item
    }
}