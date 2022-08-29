package ru.reosfire.wmlib.text

fun interface IColorizer {
    fun colorize(string: String?): String?

    fun colorize(input: Iterable<String?>): Iterable<String?> {
        return input.map { colorize(it) }
    }
}