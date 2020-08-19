package fx.soft.codecounter.extensions

import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset

/**
 * Get input stream reader
 */
val InputStream.streamReader: Reader
	get() = InputStreamReader(this, Charset.defaultCharset())
