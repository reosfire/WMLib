package ru.reosfire.wmlib.yaml.common.wrappers

interface WrapperConfig<T> {
    fun unwrap(): T
}