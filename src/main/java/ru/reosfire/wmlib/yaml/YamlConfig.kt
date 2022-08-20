package ru.reosfire.wmlib.yaml

import org.apache.commons.lang.NullArgumentException
import org.bukkit.ChatColor
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.MemoryConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import ru.reosfire.wmlib.text.Text
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

abstract class YamlConfig(configurationSection: ConfigurationSection?) {
    val section: ConfigurationSection

    init {
        section = configurationSection ?: throw NullArgumentException("configurationSection")
    }

    fun <T : YamlConfig?> getNestedConfigs(creator: IConfigCreator<T>, path: String?): List<T> {
        val result = ArrayList<T>()
        val configsParent = getSection(path, null) ?: return result
        return getNestedConfigs(creator, configsParent)
    }

    fun <T : YamlConfig?> getNestedConfigs(creator: IConfigCreator<T>): List<T> {
        return getNestedConfigs(creator, section)
    }

    private fun <T : YamlConfig?> getNestedConfigs(creator: IConfigCreator<T>, section: ConfigurationSection): List<T> {
        val result = ArrayList<T>()
        for (key in section.getKeys(false)) {
            try {
                result.add(creator.create(section.getConfigurationSection(key)))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun <T : YamlConfig?> getList(creator: IConfigCreator<T>, path: String?): List<T>? {
        val list = section.getList(path) ?: return null
        val tempConfig = MemoryConfiguration()
        for (i in list.indices) {
            tempConfig.createSection(i.toString(), list[i] as Map<*, *>?)
        }
        return getNestedConfigs(creator, tempConfig)
    }

    fun <T : YamlConfig?> getMap(creator: IConfigCreator<T>, path: String): Map<String, T> {
        return getMap(creator, getSection(path))
    }

    fun <T : YamlConfig?> getMap(creator: IConfigCreator<T>): Map<String, T> {
        return getMap(creator, section)
    }

    fun <T : YamlConfig?> getMap(creator: IConfigCreator<T>, section: ConfigurationSection): Map<String, T> {
        val result: MutableMap<String, T> = HashMap()
        for (key in section.getKeys(false)) {
            try {
                result[key] = creator.create(section.getConfigurationSection(key))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun getString(path: String?): String? {
        return section.getString(path)
    }

    fun getString(path: String?, def: String?): String {
        return section.getString(path, def)
    }

    fun getColoredString(path: String?): String {
        return ChatColor.translateAlternateColorCodes('&', getString(path))
    }

    fun getColoredString(path: String?, def: String?): String {
        return ChatColor.translateAlternateColorCodes('&', getString(path, def))
    }

    fun getInt(path: String?): Int {
        return section.getInt(path)
    }

    fun getInt(path: String?, def: Int): Int {
        return section.getInt(path, def)
    }

    fun getLong(path: String?): Long {
        return section.getLong(path)
    }

    fun getLong(path: String?, def: Long): Long {
        return section.getLong(path, def)
    }

    fun getBoolean(path: String?): Boolean {
        return section.getBoolean(path)
    }

    fun getBoolean(path: String?, def: Boolean): Boolean {
        return section.getBoolean(path, def)
    }

    fun getDouble(path: String?): Double {
        return section.getDouble(path)
    }

    fun getDouble(path: String?, def: Double): Double {
        return section.getDouble(path, def)
    }

    fun getFloat(path: String?, def: Float): Float {
        val string = getString(path) ?: return def
        return string.toFloat()
    }

    fun getSection(path: String): ConfigurationSection {
        return section.getConfigurationSection(path)
            ?: throw IllegalArgumentException("Unknown path: " + section.currentPath + "." + path)
    }

    fun getSection(
        path: String?,
        def: ConfigurationSection?
    ): ConfigurationSection? {
        return section.getConfigurationSection(path) ?: return def
    }

    fun getStringList(path: String?): List<String>? {
        val stringList = section.getStringList(path)
        if (stringList == null || stringList.isEmpty()) {
            val string = getString(path)
            if (string != null) return listOf(string)
        }
        return stringList
    }

    fun getStringList(path: String?, def: List<String>): List<String> {
        val stringList = getStringList(path)
        return if (stringList == null || stringList.isEmpty()) def else stringList
    }

    fun getColoredStringList(path: String?): List<String> {
        return Text.setColors(getStringList(path))
    }

    fun getColoredStringList(path: String?, def: List<String>): List<String> {
        return Text.setColors(getStringList(path, def))
    }

    fun getIntegerList(path: String?): List<Int>? {
        val stringList = getStringList(path) ?: return null
        val result: MutableList<Int> = ArrayList()
        for (s in stringList) {
            result.add(s.toInt())
        }
        return result
    }

    fun getIntegerList(path: String?, def: List<Int>): List<Int> {
        val stringList = getStringList(path) ?: return def
        val result: MutableList<Int> = ArrayList()
        for (s in stringList) {
            result.add(s.toInt())
        }
        return if (result.isEmpty()) def else result
    }

    fun getByte(path: String?): Byte {
        return getInt(path).toByte()
    }

    fun getByte(path: String?, def: Byte): Byte {
        return try {
            getInt(path, def.toInt()).toByte()
        } catch (e: Exception) {
            def
        }
    }

    fun isSection(path: String?): Boolean {
        return section.isConfigurationSection(path)
    }

    fun isList(path: String?): Boolean {
        return section.isList(path)
    }

    companion object {
        fun <T : YamlConfig?> create(configurationSection: ConfigurationSection?, creator: IConfigCreator<T>): T {
            return creator.create(configurationSection)
        }

        @Throws(IOException::class, InvalidConfigurationException::class)
        fun loadOrCreate(
            resultFileName: String, defaultConfigurationResource: String?,
            plugin: Plugin
        ): YamlConfiguration {
            val config = YamlConfiguration()
            config.load(loadOrCreateFile(resultFileName, defaultConfigurationResource, plugin))
            return config
        }

        @Throws(IOException::class, InvalidConfigurationException::class)
        fun loadOrCreate(file: File?): YamlConfiguration {
            val config = YamlConfiguration()
            config.load(file)
            return config
        }

        @JvmStatic
        @Throws(IOException::class, InvalidConfigurationException::class)
        fun loadOrCreate(fileName: String, plugin: Plugin): YamlConfiguration {
            return loadOrCreate(fileName, fileName, plugin)
        }

        @Throws(IOException::class)
        fun loadOrCreateFile(
            resultFileName: String, defaultConfigurationResource: String?,
            plugin: Plugin
        ): File {
            val configFile = File(plugin.dataFolder, resultFileName)
            if (!configFile.exists()) {
                configFile.parentFile.mkdirs()
                val resource = plugin.getResource(defaultConfigurationResource)
                val buffer = ByteArray(resource.available())
                resource.read(buffer)
                val fileOutputStream = FileOutputStream(configFile)
                fileOutputStream.write(buffer)
                fileOutputStream.close()
            }
            return configFile
        }

        @Throws(IOException::class)
        fun loadOrCreateFile(fileName: String, plugin: Plugin): File {
            return loadOrCreateFile(fileName, fileName, plugin)
        }
    }
}