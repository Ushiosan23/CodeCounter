package fx.codecounter.configuration

import org.w3c.dom.Node
import java.io.File
import java.io.InputStream
import java.net.URL

/**
 * Ignore file configuration class
 *
 * @param stream file stream
 */
class XMLIgnoreConfiguration(stream: InputStream) : XMLBaseConfiguration(stream) {
	
	/**
	 * Ignore files by name (include files and directories)
	 */
	var ignoreByName: MutableList<String> = mutableListOf()
	
	/**
	 * Ignore files by regex (include files and directories)
	 */
	var ignoreByRegex: MutableList<Regex> = mutableListOf()
	
	/**
	 * Second constructor
	 *
	 * @param location file object
	 */
	constructor(location: File) : this(location.inputStream())
	
	/**
	 * Third constructor
	 *
	 * @param location url object
	 */
	constructor(location: URL) : this(location.openStream())
	
	/**
	 * Initialize object
	 */
	init {
		createConfigurations()
	}
	
	/**
	 * Check if file is ignore
	 *
	 * @param fileName target filename (only file name, not path location)
	 */
	fun checkIgnoreFile(fileName: String): Boolean {
		// Check if has file in name array
		if (ignoreByName.contains(fileName.trim()))
			return true
		
		// Check if has file in regex array
		ignoreByRegex.forEach {
			if (it.matches(fileName.trim()))
				return true
		}
		
		return false
	}
	
	/**
	 * Create configuration node
	 */
	private fun createConfigurations() {
		val configurations = root.getElementsByTagName("item")
		
		for (i in 0..configurations.length) {
			if (configurations.item(i) != null)
				defineNode(configurations.item(i))
		}
	}
	
	/**
	 * Define node configuration in arrays
	 */
	private fun defineNode(node: Node) {
		val type = getNodeAttributeOrDefault(node, "type", "name").trim()
		val value = getNodeAttributeOrDefault(node, "value", "null")
		
		// Check if value is null or value is empty
		if (value.trim() == "null" || value.trim().isEmpty())
			return
		// Check if type is valid
		if (!IgnoreType.containString(type))
			return
		
		// Add to name
		if (IgnoreType.NAME.type == type)
			ignoreByName.add(value)
		// Add to regex
		if (IgnoreType.REGEX.type == type)
			ignoreByRegex.add(Regex(value))
	}
	
	/**
	 * Override toString method
	 */
	override fun toString(): String {
		val resultList = mutableListOf<Any>(ignoreByName, ignoreByRegex)
		return resultList.toString()
	}
	
	/**
	 * Ignore file types
	 */
	enum class IgnoreType(val type: String) {
		NAME("name"),
		REGEX("regex");
		
		
		/**
		 * Companion object
		 */
		companion object {
			
			/**
			 * Check if enum contains string value
			 */
			fun containString(value: String): Boolean {
				for (i in values()) {
					if (i.type == value.toLowerCase())
						return true
				}
				
				return false
			}
			
		}
	}
	
}