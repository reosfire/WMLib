package ru.reosfire.wmlib.random

import java.util.*
import kotlin.collections.ArrayList

class LinearDropList<T>: DropList<T> {
    val size get() = _elements.size
    val elements: List<DropList.Element<T>> get() = _elements
    var chancesSum: Double = 0.0
        private set

    private val _elements = ArrayList<DropList.Element<T>>()

    override fun add(element: DropList.Element<T>) {
        _elements.add(element)
        chancesSum += element.chance
    }

    override fun remove(element: DropList.Element<T>) {
        _elements.remove(element)
        chancesSum -= element.chance
    }

    override fun removeAt(index: Int) {
        chancesSum -= _elements.removeAt(index).chance
    }

    override fun getRandom(random: Random): T {
        val generated = random.nextDouble(chancesSum)
        var sum = _elements.first().chance
        var result = 1

        while (sum < generated) {
            sum += _elements[result].chance
            result++
        }

        return _elements[result - 1].item
    }
}