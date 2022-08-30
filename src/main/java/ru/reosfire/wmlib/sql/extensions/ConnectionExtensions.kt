package ru.reosfire.wmlib.sql.extensions

import ru.reosfire.wmlib.sql.insertion.ColumnValue
import ru.reosfire.wmlib.sql.tables.TableColumn
import java.sql.Connection

fun Connection.createTable(name: String, vararg columns: TableColumn) {
    val request = StringBuilder("CREATE TABLE IF NOT EXISTS `").append(name).append("` (")
    request.append(columns.joinToString(", ") { it.toSqlString() })
    request.append(");")
    createStatement().use {
        it.executeUpdate(request.toString())
    }
}

fun Connection.insert(table: String, vararg values: ColumnValue) {
    val request = insertStringBuilder(table, values).append(";")

    prepareStatement(request.toString()).use {
        values.forEachIndexed { i, value ->
            it.setObject(i + 1, value)
        }
        it.executeUpdate()
    }
}

private fun insertStringBuilder(table: String, values: Array<out ColumnValue>): StringBuilder {
    val request = StringBuilder("INSERT INTO `").append(table).append("` (")
    request.append(values.joinToString(", ") { it.column })
    request.append(") VALUES (")
    request.append(values.joinToString(", ") { "?" })
    return request.append(")")
}