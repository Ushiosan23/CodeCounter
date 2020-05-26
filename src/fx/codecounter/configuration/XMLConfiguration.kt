package fx.codecounter.configuration

import fx.codecounter.program.Program
import org.w3c.dom.Node
import java.io.File
import java.io.InputStream
import java.net.URL
import kotlin.reflect.full.isSubclassOf

/**
 * Create configuration with xml file.
 *
 * @param location file stream object.
 */
class XMLConfiguration(location: InputStream) : XMLBaseConfiguration(location) {
	
	/**
	 * Configuration data.
	 */
	var configurationData: MutableMap<String, Any?> = mutableMapOf()
		private set
	
	/**
	 * Secondary constructor.
	 *
	 * @param location file object path.
	 */
	constructor(location: File) : this(location.inputStream())
	
	/**
	 * Third constructor.
	 *
	 * @param location url path.
	 */
	constructor(location: URL) : this(location.openStream())
	
	/**
	 * Initialize object
	 */
	init {
		createConfiguration()
	}
	
	/**
	 * Get value operator.
	 * You can use this operator like array accessor `object["configName"]`
	 *
	 * @param configName target configuration.
	 *
	 * @return [Any] configuration object or null if not exists.
	 */
	operator fun get(configName: String) = if (configurationData.containsKey(configName)) {
		configurationData[configName]
	} else {
		null
	}
	
	/**
	 * Get value with typed data.
	 *
	 * @param configName target configuration.
	 *
	 * @return [T] configuration object or null if not exists.
	 */
	inline fun <reified T> getTyped(configName: String): T? {
		// Check if data exists.
		if (!configurationData.containsKey(configName))
			return null
		// Save local data
		val localData = configurationData[configName]
		
		// Check if data is different of null
		if (localData != null) {
			// Check type
			if (localData::class.isSubclassOf(T::class)) {
				return localData as T
			}
		}
		
		return null
	}
	
	/**
	 * Create configurations
	 */
	private fun createConfiguration() {
		val configurations = root.getElementsByTagName("item")
		
		for (i in 0..configurations.length) {
			if (configurations.item(i) != null)
				parseConfig(configurations.item(i))
		}
	}
	
	/**
	 * Parse configuration node
	 *
	 * @param node target to parse.
	 */
	private fun parseConfig(node: Node) {
		val name = getNodeAttributeOrDefault(node, "name", "")
		val clazz = getNodeAttributeOrDefault(node, "type", String::class.java.name)
		var value: String? = getNodeAttributeOrDefault(node, "value", "null")
		
		if (value == "null")
			value = null
		
		when (clazz) {
			String::class.java.name -> configurationData[name] = value
			Int::class.java.name -> configurationData[name] = value?.toInt()
			Float::class.java.name -> configurationData[name] = value?.toFloat()
			Double::class.java.name -> configurationData[name] = value?.toDouble()
			Byte::class.java.name -> configurationData[name] = value?.toByte()
			Boolean::class.java.name -> configurationData[name] = value?.toBoolean()
			Version::class.java.name -> configurationData[name] = Version(Program::class.java.getResource(value))
			XMLIgnoreConfiguration::class.java.name -> configurationData[name] = XMLIgnoreConfiguration(Program::class.java.getResource(value))
			XMLLanguageConfiguration::class.java.name -> configurationData[name] = XMLLanguageConfiguration(Program::class.java.getResource(value))
		}
	}
	
	/**
	 * Configuration to string.
	 */
	override fun toString(): String {
		return configurationData.toString()
	}
	
}