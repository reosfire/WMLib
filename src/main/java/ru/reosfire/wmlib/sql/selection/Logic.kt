package ru.reosfire.wmlib.sql.selection

enum class Logic : IWhereMember {
    And, Or;

    override fun toSqlString(): String {
        return name.uppercase()
    }
}