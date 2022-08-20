package ru.reosfire.wmlib.extensions

import ru.reosfire.wmlib.sql.tables.TableColumn
import java.sql.Connection

fun Connection.createTable(name: String, vararg columns: TableColumn) {
    val request = StringBuilder("CREATE TABLE IF NOT EXISTS ").append('`').append(name).append("` (")
    request.append(columns.joinToString(", ") { it.toSqlString() })
    request.append(");")
    this.createStatement().executeUpdate(request.toString())
}