package fx.codecounter.data

/**
 * Language result scan
 *
 * @param language language object data
 * @param _code total lines of code (only code)
 * @param _empty total empty lines (jump lines)
 * @param _comments total lines with comments (short and long comments)
 */
data class LanguageResult(
	
	/**
	 * Language data
	 */
	val language: Language,
	
	/**
	 * Total lines of code
	 */
	private val _code: Long,
	
	/**
	 * Total empty lines
	 */
	private val _empty: Long,
	
	/**
	 * Total comments
	 */
	private val _comments: Long
) {
	
	/**
	 * Public code field
	 */
	var code: Long = _code
		set(value) {
			field = value
			updateTotal()
		}
	
	/**
	 * Public empty field
	 */
	var empty: Long = _empty
		set(value) {
			field = value
			updateTotal()
		}
	
	/**
	 * Public comments field
	 */
	var comments: Long = _comments
		set(value) {
			field = value
			updateTotal()
		}
	
	/**
	 * Total lines include: [code], [empty] and [comments]
	 */
	var total: Long = 0
		private set
	
	/**
	 * Update total result
	 */
	private fun updateTotal() {
		total = code + empty + comments
	}
	
}