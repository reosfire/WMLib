package ru.reosfire.wmlib.sql.selection

class Where : ISelectionAttribute {
    private val members: Array<out IWhereMember>

    constructor(vararg members: IWhereMember) {
        this.members = members
    }

    constructor(variable: String, comparer: Comparer, value: String) {
        members = arrayOf(Condition(variable, comparer, value))
    }

    constructor(variable: String, comparer: Comparer, value: Long) {
        members = arrayOf(Condition(variable, comparer, value))
    }

    constructor(variable: String, comparer: Comparer, value: Boolean) {
        members = arrayOf(Condition(variable, comparer, value))
    }

    override fun toSqlString(): String {
        return "WHERE ${members.joinToString(" ") { it.toSqlString() }}"
    }
}