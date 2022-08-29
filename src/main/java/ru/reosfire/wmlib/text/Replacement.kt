package ru.reosfire.wmlib.text

import java.util.*

class Replacement(private val from: String, private val to: String) {
    fun set(Input: String): String {
        return Input.replace(from, to)
    }

    companion object {
        operator fun set(message: String, replacement: Replacement): String {
            return replacement.set(message)
        }

        fun set(message: String, vararg replacements: Replacement): String {
            var result = message
            for (replacement in replacements) {
                result = replacement.set(message)
            }
            return result
        }

        operator fun set(messages: Iterable<String>, replacement: Replacement): Iterable<String> {
            val result = LinkedList<String>()
            for (message in messages) {
                result.add(replacement.set(message))
            }
            return result
        }

        fun set(messages: Iterable<String>, vararg replacements: Replacement): Iterable<String> {
            var temp = messages
            for (replacement in replacements) {
                temp = set(messages, replacement)
            }
            return temp
        }
    }
}