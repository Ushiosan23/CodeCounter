package fx.codecounter.data.format

/**
 * Comment data identify
 *
 * @param shortComment a line of comment
 * @param longCommentStart start long comment (multiline comment)
 * @param longCommentEnd end long comment (multiline comment)
 */
data class Comment(
	
	/**
	 * Short comment regex
	 */
	val shortComment: String,
	
	/**
	 * Long start comment regex
	 */
	val longCommentStart: String?,
	
	/**
	 * Long end comment regex
	 */
	val longCommentEnd: String?
)