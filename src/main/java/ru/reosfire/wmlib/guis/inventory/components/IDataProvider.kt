package ru.reosfire.wmlib.guis.inventory.components

interface IDataProvider<T> {
    fun getData(start: Int, end: Int): List<T>
    val size: Int
}