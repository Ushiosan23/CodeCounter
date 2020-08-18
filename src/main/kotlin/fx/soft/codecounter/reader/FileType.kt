package fx.soft.codecounter.reader

data class FileType(
	val fileName: String,
	val extensions: List<String>,
	val singleComment: Regex,
	val startMultipleComment: Regex,
	val endMultipleComment: Regex
)
