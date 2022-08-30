package ru.reosfire.wmlib.extensions.java

import ru.reosfire.wmlib.utils.UUIDConverter
import java.sql.ResultSet
import java.util.*

fun ResultSet.getUUID(columnIndex: Int): UUID {
    return UUID.fromString(getString(columnIndex))
}

fun ResultSet.getUUID(columnLabel: String): UUID {
    return UUID.fromString(getString(columnLabel))
}

fun ResultSet.getUUIDFromBytes(columnIndex: Int): UUID {
    return UUIDConverter.fromBytes(getBytes(columnIndex))
}

fun ResultSet.getUUIDFromBytes(columnLabel: String): UUID {
    return UUIDConverter.fromBytes(getBytes(columnLabel))
}