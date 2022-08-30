package ru.reosfire.wmlib.sql.selection

import ru.reosfire.wmlib.sql.ISqlPart

enum class Comparer : ISqlPart {
    Equal, NotEqual, GreaterThan, LessThan, GreaterThanOrEquals, LessThanOrEquals, Between, Like, In;

    override fun toSqlString(): String {
        return when (this) {
            Equal -> "="
            NotEqual -> "<>"
            GreaterThan -> ">"
            LessThan -> "<"
            GreaterThanOrEquals -> ">="
            LessThanOrEquals -> "<="
            Between -> "BETWEEN"
            Like -> "LIKE"
            In -> "IN"
        }
    }
}