package fx.codecounter.scanner

import fx.codecounter.data.LanguageResult
import java.io.File
import java.io.IOException

/**
 * File scanner class
 *
 * @param path file location
 */
class FileScanner(path: File) {
	
	/**
	 * Location scanner
	 */
	val location: File = path
	
	/**
	 * File name
	 */
	val filename: String
	
	/**
	 * Language result scan
	 */
	lateinit var fileResult: LanguageResult
		private set
	
	
	init {
		if (!location.exists())
			throw IOException("File \"$location\" not found.")
		if (location.isDirectory)
			throw RuntimeException("File \"$location\" is not valid file. Directory given.")
		
		// Set file name
		filename = location.name
		// Initialize file result
		
		// Scan file
		
	}
	
	private fun scan() {
	
	}
	
}