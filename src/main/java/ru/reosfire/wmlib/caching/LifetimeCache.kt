package ru.reosfire.wmlib.caching

import java.time.Instant
import java.util.function.BiConsumer
import java.util.function.Function

class LifetimeCache<TKey, TValue>(
    private val dataSupplier: Function<TKey, TValue>,
    private val dataUpdater: BiConsumer<TKey, TValue>,
    private val lifetime: Long
) {
    private val cachedData: MutableMap<TKey, Data<TValue>> = LinkedHashMap()
    fun add(key: TKey, value: TValue) {
        dataUpdater.accept(key, value)
        cachedData[key] = Data(value, Instant.now().toEpochMilli())
    }

    operator fun get(key: TKey): TValue {
        val currentTime = Instant.now().toEpochMilli()
        return if (cachedData.containsKey(key)) {
            val cached = cachedData[key]!!
            if (currentTime - cached.CreationTimeMilli > lifetime) getFromSupplier(key, currentTime) else cached.Value
        } else getFromSupplier(key, currentTime)
    }

    fun containsKey(key: TKey): Boolean {
        return cachedData.containsKey(key)
    }

    private fun getFromSupplier(key: TKey, currentTime: Long): TValue {
        val value = dataSupplier.apply(key)
        cachedData[key] = Data(value, currentTime)
        return value
    }

    class Data<T> constructor(val Value: T, val CreationTimeMilli: Long = Instant.now().toEpochMilli())
}