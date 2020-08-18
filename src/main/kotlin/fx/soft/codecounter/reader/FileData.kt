package fx.soft.codecounter.reader

import java.io.File

/**
 * Data file result
 */
data class FileData(
	val location: File,
	val fileType: FileType
)
