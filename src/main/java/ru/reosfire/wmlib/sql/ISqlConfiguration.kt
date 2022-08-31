package ru.reosfire.wmlib.sql

import kotlin.Throws

interface ISqlConfiguration {
    val user: String?
    val password: String?
    val connectionString: String?

    @Throws(SqlRequirementsNotSatisfiedException::class)
    fun checkRequirements()
}