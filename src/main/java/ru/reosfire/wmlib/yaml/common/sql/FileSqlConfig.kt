package ru.reosfire.wmlib.yaml.common.sql

import org.bukkit.configuration.ConfigurationSection
import ru.reosfire.wmlib.yaml.YamlConfig
import ru.reosfire.wmlib.sql.ISqlConfiguration
import kotlin.Throws
import ru.reosfire.wmlib.sql.SqlRequirementsNotSatisfiedException
import java.lang.ClassNotFoundException

class FileSqlConfig(configurationSection: ConfigurationSection?) : YamlConfig(configurationSection), ISqlConfiguration {
    val filePath = getString("FilePath")
    override val user = null
    override val password = null
    override val connectionString get() = "jdbc:sqlite:$filePath"

    @Throws(SqlRequirementsNotSatisfiedException::class)
    override fun checkRequirements() {
        try {
            Class.forName("org.sqlite.JDBC")
        } catch (e: ClassNotFoundException) {
            throw SqlRequirementsNotSatisfiedException(e)
        }
    }
}