package fx.soft.codecounter.reader

import fx.soft.codecounter.extensions.streamReader
import java.io.File

/**
 * Base file reader
 *
 * @param location File location string
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseReader(protected val location: String) : IReader {
	
	/**
	 * File object
	 */
	protected val fileObject: File = File(location)
	
	/**
	 * Get file status
	 */
	override val exists: Boolean
		get() = fileObject.exists()
	
	/**
	 * Get file extension
	 */
	override val extension: String
		get() = getFileExtension(fileObject)
	
	/**
	 * file read file by line
	 *
	 * @throws java.io.IOException Error to read file
	 */
	override fun readByLine(action: (line: String) -> Unit) =
		fileObject.streamReader.forEachLine {
			action.invoke(it)
		}
	
	/**
	 * Reader companion object
	 */
	companion object {
		
		/**
		 * Get file extension
		 *
		 * @see File.extension
		 */
		protected fun getFileExtension(file: File): String =
			file.extension
		
	}
	
}
