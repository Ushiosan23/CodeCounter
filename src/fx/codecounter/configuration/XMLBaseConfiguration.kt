package fx.codecounter.configuration

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Base xml configuration.
 *
 * @param stream file stream
 */
abstract class XMLBaseConfiguration(stream: InputStream) {
	
	/**
	 * file data stream
	 */
	protected val fileStream: InputStream = stream
	
	/**
	 * XML Document
	 */
	protected var document: Document
	
	/**
	 * XML root element
	 */
	protected var root: Element
	
	/**
	 * Initialize object
	 */
	init {
		val factory = DocumentBuilderFactory.newInstance()
		val builder = factory.newDocumentBuilder()
		
		document = builder.parse(fileStream)
		root = document.documentElement
	}
	
	/**
	 * Companion object
	 */
	companion object {
		
		/**
		 * Get node attribute or default value
		 *
		 * @param node target search node
		 * @param attribute target attribute
		 * @param default default value to return
		 *
		 * @return [String] with attribute value or [default] value
		 */
		fun getNodeAttributeOrDefault(node: Node, attribute: String, default: String): String {
			if (node.attributes.getNamedItem(attribute) == null)
				return default
			
			return node.attributes.getNamedItem(attribute).nodeValue
		}
		
	}
	
}