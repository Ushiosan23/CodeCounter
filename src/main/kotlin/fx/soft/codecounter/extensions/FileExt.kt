package fx.soft.codecounter.extensions

import java.io.File
import java.io.Reader

/**
 * Get file stream reader
 *
 * @see java.io.InputStream.streamReader
 */
val File.streamReader: Reader
	get() = this.inputStream().streamReader
