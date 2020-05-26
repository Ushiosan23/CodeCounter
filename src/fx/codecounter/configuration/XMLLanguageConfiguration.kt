package fx.codecounter.configuration

import fx.codecounter.data.Language
import fx.codecounter.data.format.Comment
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import java.io.InputStream
import java.net.URL

/**
 * XML Language configuration class.
 * Primary constructor
 *
 * @param stream file stream object
 */
class XMLLanguageConfiguration(stream: InputStream) : XMLBaseConfiguration(stream) {
	
	/**
	 * Language map
	 */
	val languages: MutableMap<String, Language> = mutableMapOf()
	
	/**
	 * Languages by extensions
	 */
	val extensionLang: MutableMap<String, String> = mutableMapOf()
	
	/**
	 * Second constructor
	 *
	 * @param location url location object
	 */
	constructor(location: URL) : this(location.openStream())
	
	/**
	 * Third constructor
	 *
	 * @param location file location object
	 */
	constructor(location: File) : this(location.inputStream())
	
	/**
	 * Initialize object
	 */
	init {
		val items = root.getElementsByTagName("item")
		
		for (i in 0..items.length) {
			val node = items.item(i)
			if (node != null)
				generateLanguages(node)
		}
		
		
		println(languages)
	}
	
	/**
	 * Generate language objects
	 */
	private fun generateLanguages(node: Node) {
		val nodeEl = node as Element
		val name = getNodeAttributeOrDefault(node, "name", "other")
		var readable = getNodeAttributeOrDefault(node, "readable", "false").toBoolean()
		val baseExtensions = getNodeAttributeOrDefault(node, "extensions", "null")
		val singleComment = getNodeAttributeOrDefault(node, "comment", "\\/\\/.*")
		
		if (name == "other")
			readable = false
		if (baseExtensions == "null")
			return
		
		val extensions = baseExtensions.split(",")
		
		val cStartL = nodeEl.getElementsByTagName("comment-start")
		val commentStart = if (cStartL.length > 0) cStartL.item(0).nodeValue else null
		
		val cEndL = nodeEl.getElementsByTagName("comment-end")
		val commentEnd = if (cEndL.length > 0) cEndL.item(0).nodeValue else null
		
		val comments = Comment(singleComment, commentStart?.ifEmpty { null }, commentEnd?.ifEmpty { null })
		
		languages[name] = Language(name, readable, extensions, comments)
		updateExtensions(name, extensions)
	}
	
	/**
	 * Update extensions and languages
	 *
	 * @param name target language
	 * @param extensions language extensions
	 */
	private fun updateExtensions(name: String, extensions: List<String>) = extensions.forEach {
		extensionLang[it.trim()] = name
	}
	
}