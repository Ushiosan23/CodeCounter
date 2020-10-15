package com.github.ushiosan23.codecounter.system.cli

/**
 * Parameters class
 */
class Params(private val args: Array<String>) {

	/* ---------------------------------------------------------
	 *
	 * Internal params
	 *
	 * --------------------------------------------------------- */

	/**
	 * Parameter map
	 */
	private val paramsMap: MutableMap<String, String> = mutableMapOf()

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Initialize object
	 */
	init {
		updateArgs()
	}

	/* ---------------------------------------------------------
	 *
	 * Public methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Get value from map
	 *
	 * @param param Target param to search
	 *
	 * @return [String] result or `null` if not exists
	 */
	operator fun get(param: String): String? = paramsMap[param]

	/**
	 * Get value from map or default value
	 *
	 * @param param Target param to search
	 * @param default Default value
	 *
	 * @return [String] result or [default] if not exists
	 */
	fun getOrDefault(param: String, default: String = ""): String =
		get(param) ?: default

	/**
	 * Check if param exists
	 *
	 * @param param Target param to search
	 */
	fun paramExists(param: String): Boolean =
		paramsMap.containsKey(param)

	/* ---------------------------------------------------------
	 *
	 * Implementation methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Returns a string representation of the object
	 *
	 * @return [String] result
	 */
	override fun toString(): String {
		return paramsMap.toString()
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Update all arguments
	 */
	private fun updateArgs() {
		// Clear arguments map
		paramsMap.clear()
		// Iterate params
		args.forEach { item ->
			parseArgs(item)?.let { paramsMap[it.first] = it.second }
		}
	}

	/**
	 * Parse arguments
	 *
	 * @param arg Argument to parse
	 *
	 * @return [Pair] Pair result object
	 */
	private fun parseArgs(arg: String): Pair<String, String>? {
		val split = arg.split("=")

		return when {
			split.size > 1 -> Pair(split[0], split[1])
			split.size <= 1 -> Pair(split[0], "")
			else -> null
		}
	}

}
