package com.github.ushiosan23.codecounter.reader

import java.io.File
import java.net.URI
import java.net.URL

/**
 * File reader class
 */
class FileReader : BaseReader<String> {

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Url constructor
	 *
	 * @param url File url
	 */
	constructor(url: URL) : super(url)

	/**
	 * File constructor
	 *
	 * @param file File object
	 */
	constructor(file: File) : super(file)

	/**
	 * URI constructor
	 *
	 * @param uri URI object
	 */
	constructor(uri: URI) : super(uri)

	/* ---------------------------------------------------------
	 *
	 * Implementation methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Get file data
	 *
	 * @return [String] file data
	 */
	override fun getData(): String {
		if (readerData == null) updateData()
		return readerData!!
	}


	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Update file data
	 */
	private fun updateData() {
		readerData = ""
		readByLine { readerData += it }
	}

}
