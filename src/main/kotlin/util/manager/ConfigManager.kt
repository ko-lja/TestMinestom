package lu.kolja.util.manager

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path

class ConfigManager(pluginName: String) {
    private var pluginFolder: Path
    private var configFile: Path
    private val mapper = ObjectMapper(YAMLFactory()).registerModule(KotlinModule.Builder().build())
    private var config: Config? = null
    init {
        val currentDir = System.getProperty("user.dir")
        pluginFolder = Path(currentDir, "world", pluginName)
        configFile = pluginFolder.resolve("config.yml")
        createDefaultConfig()
        loadConfig()
    }

    data class Config(
        var scoreboard: Scoreboard = Scoreboard()
    )
    data class Scoreboard(
        val title: String = "My Scoreboard",
        val text: List<String> = listOf("Line 1 hell yeah", "Line 2 sigma")
    )

    private fun createDefaultConfig() {
        try {
            // Create plugin directory if it doesn't exist
            if (!Files.exists(pluginFolder)) {
                Files.createDirectories(pluginFolder)
            }

            // Create config file if it doesn't exist
            if (!Files.exists(configFile)) {
                val defaultConfig = Config()
                saveConfig(defaultConfig)
            }
        } catch (e: Exception) {
            println("Failed to create config directory or file: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun loadConfig() {
        try {
            config = mapper.readValue(configFile.toFile())
        } catch (e: Exception) {
            println("Failed to load config: ${e.message}")
            config = Config()
            saveConfig(config!!)
        }
    }

    private fun saveConfig(config: Config) {
        try {
            mapper.writeValue(configFile.toFile(), config)
        } catch (e: Exception) {
            println("Failed to save config: ${e.message}")
            e.printStackTrace()
        }
    }

    fun getConfig(): Config = config ?: Config()

    fun updateConfig(updates: Config.() -> Unit) {
        val currentConfig = getConfig()
        currentConfig.updates()
        saveConfig(currentConfig)
        config = currentConfig
    }

    fun reloadConfig() {
        loadConfig()
    }
}