package ru.reosfire.wmlib.random

import java.util.*
import kotlin.collections.ArrayList

class BinaryDropList<T>: DropList<T> {
    override val size get() = _elements.size
    override val elements: List<DropList.Element<T>> get() = _elements
    override val chancesSum get() = _chancesPrefixSums.last()
    val chancesPrefixSums: List<Double> get() = _chancesPrefixSums

    private val _elements = ArrayList<DropList.Element<T>>()
    private val _chancesPrefixSums = arrayListOf(0.0)

    override fun add(element: DropList.Element<T>) {
        _elements.add(element)
        _chancesPrefixSums.add(_chancesPrefixSums.last() + element.chance)
    }

    override fun remove(element: DropList.Element<T>) {
        _elements.remove(element)
        refreshPrefixSums()
    }

    override fun removeAt(index: Int) {
        _elements.removeAt(index)
        refreshPrefixSums()
    }

    private fun refreshPrefixSums() {
        _chancesPrefixSums.clear()
        _chancesPrefixSums.add(0.0)
        for (element in _elements) {
            _chancesPrefixSums.add(_chancesPrefixSums.last() + element.chance)
        }
    }

    override fun getRandom(random: Random): T {
        val generated = random.nextDouble(_chancesPrefixSums.last())
        var start = -1
        var end = size
        while (end - start > 1) {
            val middle = (start + end) / 2
            if (_chancesPrefixSums[middle] <= generated) start = middle
            else end = middle
        }
        return _elements[start].item
    }
}