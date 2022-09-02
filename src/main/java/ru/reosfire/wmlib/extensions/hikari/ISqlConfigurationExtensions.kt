package ru.reosfire.wmlib.extensions.hikari

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import ru.reosfire.wmlib.sql.ISqlConfiguration

fun ISqlConfiguration.getHikariDataSource(): HikariDataSource{
    val config = HikariConfig()
    config.jdbcUrl = connectionString
    config.username = user
    config.password = password

    return HikariDataSource(config)
}

fun ISqlConfiguration.getHikariDataSource(configurer :(HikariConfig) -> Unit): HikariDataSource{
    val config = HikariConfig()
    config.jdbcUrl = connectionString
    config.username = user
    config.password = password

    configurer(config)

    return HikariDataSource(config)
}