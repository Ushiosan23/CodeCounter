package fx.codecounter.configuration

import fx.codecounter.program.Program
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import java.io.InputStream
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.reflect.full.isSubclassOf

/**
 * Create configuration with xml file.
 *
 * @param location file stream object.
 */
class XMLConfiguration(location: InputStream) {

    /**
     * File data content.
     */
    private var fileStream: InputStream = location

    /**
     * Configuration data.
     */
    var configurationData: MutableMap<String, Any?> = mutableMapOf()
        private set

    /**
     * XML Document structure
     */
    private lateinit var document: Document

    /**
     * Document root node
     */
    private lateinit var documentRoot: Element

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
        readDocument()
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
     * Read xml document
     */
    private fun readDocument() {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()

        document = builder.parse(fileStream)
        documentRoot = document.documentElement

        // create configuration
        createConfiguration()
    }

    /**
     * Create configurations
     */
    private fun createConfiguration() {
        val configurations = documentRoot.getElementsByTagName("item")

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
        val name = getNodeAttributeDefault(node, "name", "")
        val clazz = getNodeAttributeDefault(node, "type", String::class.java.name)
        var value: String? = getNodeAttributeDefault(node, "value", "null")

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
        }
    }

    /**
     * Get attribute value or default value if not exists.
     */
    private fun getNodeAttributeDefault(node: Node, attribute: String, defaultValue: String): String {
        if (node.attributes.getNamedItem(attribute) == null)
            return defaultValue

        return node.attributes.getNamedItem(attribute).nodeValue
    }

    /**
     * Configuration to string.
     */
    override fun toString(): String {
        return configurationData.toString()
    }

}