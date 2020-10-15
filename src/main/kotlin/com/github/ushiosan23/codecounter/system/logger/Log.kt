package com.github.ushiosan23.codecounter.system.logger

import java.util.logging.Level
import java.util.logging.Logger

/**
 * Logger absolute instance
 */
object Log {

	/**
	 * Get default logger
	 */
	@JvmStatic
	val defaultLogger: Logger =
		Logger.getGlobal()

	/**
	 * Create custom log message
	 *
	 * @param level Log level
	 * @param msg Log message
	 * @param params Log message params
	 */
	@JvmStatic
	fun log(level: Level, msg: String, vararg params: String) = defaultLogger.log(level, msg, params)

	/**
	 * Create custom log message
	 *
	 * @param level Log level
	 * @param msg Log message
	 */
	@JvmStatic
	fun log(level: Level, msg: Any?) = defaultLogger.log(level, anyString(msg))

	/**
	 * Log information
	 *
	 * @param msg Log message
	 */
	@JvmStatic
	fun logI(msg: Any?) = log(Level.INFO, msg)

	/**
	 * Log warning
	 *
	 * @param msg Log message
	 */
	@JvmStatic
	fun logW(msg: Any?) = log(Level.WARNING, msg)

	/**
	 * Log error
	 *
	 * @param msg Log message
	 */
	@JvmStatic
	fun logE(msg: Any?) = log(Level.SEVERE, msg)

	/**
	 * Create log string value
	 *
	 * @param any Any value
	 *
	 * @return [String] object representation
	 */
	private fun anyString(any: Any?): String = if (any != null)
		"(@${Integer.toHexString(any.hashCode())}) ${any.javaClass.simpleName}: $any"
	else
		"null"


}
