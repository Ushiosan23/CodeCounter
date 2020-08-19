package fx.soft.codecounter

import java.io.InputStream
import java.net.URL

object R {
	
	// Resource file - languages.ini
	val Languages: Resource = Resource("languages.ini")
	
	/**
	 * Resource data POJO
	 *
	 * @param location File resource location
	 */
	data class Resource(val location: String) {
		
		/**
		 * Get system class loader
		 */
		private val classLoader = ClassLoader.getSystemClassLoader()
		
		/**
		 * Get resource location
		 */
		val url: URL?
			get() = classLoader.getResource(location)
		
		/**
		 * Get resource stream
		 */
		val stream: InputStream?
			get() = try {
				classLoader.getResourceAsStream(location)
			} catch (err: Exception) {
				err.printStackTrace()
				null
			}
		
	}
	
}
