package ru.reosfire.WMLib.Caching;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LifetimeCache<TKey, TValue>
{
    private final Function<TKey, TValue> dataSupplier;
    private final BiConsumer<TKey, TValue> dataUpdater;
    private final long lifetime;
    private final Map<TKey, Data<TValue>> cachedData = new LinkedHashMap<>();

    public LifetimeCache(Function<TKey, TValue> dataSupplier, BiConsumer<TKey, TValue> dataUpdater, long lifetime)
    {
        this.dataSupplier = dataSupplier;
        this.dataUpdater = dataUpdater;
        this.lifetime = lifetime;
    }

    public void add(TKey key, TValue value)
    {
        dataUpdater.accept(key, value);
        cachedData.put(key, new Data<>(value, Instant.now().toEpochMilli()));
    }

    public TValue get(TKey key)
    {
        long currentTime = Instant.now().toEpochMilli();
        if (cachedData.containsKey(key))
        {
            Data<TValue> cached = cachedData.get(key);
            if (currentTime - cached.CreationTimeMilli > lifetime) return getFromSupplier(key, currentTime);
            else return cached.Value;
        }
        else return getFromSupplier(key, currentTime);
    }

    public boolean containsKey(TKey key)
    {
        return cachedData.containsKey(key);
    }

    private TValue getFromSupplier(TKey key, long currentTime)
    {
        TValue value = dataSupplier.apply(key);
        cachedData.put(key, new Data<>(value, currentTime));
        return value;
    }

    public static class Data<T>
    {
        public final T Value;
        public final long CreationTimeMilli;

        public Data(T value, long creationTimeMilli)
        {
            Value = value;
            CreationTimeMilli = creationTimeMilli;
        }

        public Data(T value)
        {
            this (value, Instant.now().toEpochMilli());
        }
    }
}