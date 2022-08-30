package ru.reosfire.wmlib.sql.selection

class Condition : IWhereMember {
    val variable: String
    val comparer: Comparer
    val value: String

    constructor(variable: String, comparer: Comparer, value: String) {
        this.variable = variable
        this.comparer = comparer
        this.value = "'$value'"
    }

    constructor(variable: String, comparer: Comparer, value: Long) {
        this.variable = variable
        this.comparer = comparer
        this.value = java.lang.Long.toString(value)
    }

    constructor(variable: String, comparer: Comparer, value: Boolean) {
        this.variable = variable
        this.comparer = comparer
        this.value = if (value) "1" else "0"
    }

    override fun toSqlString(): String {
        return variable + comparer.toSqlString() + value
    }
}