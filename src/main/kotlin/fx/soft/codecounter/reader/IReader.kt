package fx.soft.codecounter.reader

/**
 * Interface to read files
 */
interface IReader {
	
	/**
	 * Property to check file exists
	 */
	val exists: Boolean
	
	/**
	 * Property file extension
	 */
	val extension: String
	
	/**
	 * file read file by line
	 *
	 * @throws java.io.IOException Error to read file
	 */
	fun readByLine(action: (line: String) -> Unit)
	
}
