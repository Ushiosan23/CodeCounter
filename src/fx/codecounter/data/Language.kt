package fx.codecounter.data

import fx.codecounter.data.format.Comment

/**
 * Language data.
 */
data class Language(
	
	/**
	 * Language name
	 */
	val name: String,
	
	/**
	 * If file is readable
	 */
	val readable: Boolean,
	
	/**
	 * Language file extensions
	 */
	val extensions: List<String>,
	
	/**
	 * Language identify comments
	 */
	val comments: Comment? = null
)