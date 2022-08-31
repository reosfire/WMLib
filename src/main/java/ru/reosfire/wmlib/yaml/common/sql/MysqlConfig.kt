package ru.reosfire.wmlib.yaml.common.sql

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.sql.ISqlConfiguration
import kotlin.Throws
import ru.reosfire.wmlib.sql.SqlRequirementsNotSatisfiedException
import java.lang.ClassNotFoundException

class MysqlConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection), ISqlConfiguration {
    val ip = getString("Ip")!!
    override val user = getString("User")!!
    override val password = getString("Password")!!
    private val database = getString("Database")!!
    val useSsl = getBoolean("UseSsl", false)
    val useUnicode = getBoolean("UseUnicode", true)
    val autoReconnect = getBoolean("AutoReconnect", true)
    val failOverReadOnly = getBoolean("FailOverReadOnly", false)
    val port = getInt("Port", 3306)
    val maxReconnects = getInt("MaxReconnects", 2)

    override val connectionString get() = ("jdbc:mysql://" + ip + ":" + port + "/" + database
            + "?useSSL=" + (if (useSsl) "true" else "false")
            + "&useUnicode=" + (if (useUnicode) "true" else "false")
            + "&autoReconnect=" + (if (autoReconnect) "true" else "false")
            + "&failOverReadOnly=" + (if (failOverReadOnly) "true" else "false")
            + "&maxReconnects=" + maxReconnects)

    @Throws(SqlRequirementsNotSatisfiedException::class)
    override fun checkRequirements() {
        try {
            Class.forName("com.mysql.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            throw SqlRequirementsNotSatisfiedException(e)
        }
    }
}