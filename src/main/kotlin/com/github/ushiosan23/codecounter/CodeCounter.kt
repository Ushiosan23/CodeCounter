package com.github.ushiosan23.codecounter

import com.github.ushiosan23.codecounter.system.cli.Params

/**
 * Application entry object
 */
object CodeCounter {

	/**
	 * Program arguments
	 */
	@JvmStatic
	private lateinit var programParams: Params

	/**
	 * Entry main point
	 *
	 * @param args Program arguments
	 */
	@JvmStatic
	fun main(args: Array<String>) {
		// Set program arguments
		programParams = Params(args)
	}

}
