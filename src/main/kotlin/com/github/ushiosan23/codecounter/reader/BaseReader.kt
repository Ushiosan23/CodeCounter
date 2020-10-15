package com.github.ushiosan23.codecounter.reader

import com.github.ushiosan23.codecounter.system.logger.Log
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URI
import java.net.URL

/**
 * Abstract base reader
 *
 * @param T Target generic type value
 */
abstract class BaseReader<T> : IReader<T> {

	/* ---------------------------------------------------------
	 *
	 * Properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Target file url
	 */
	@Suppress("MemberVisibilityCanBePrivate")
	protected val fileUrl: URL

	/**
	 * Result data object
	 */
	protected var readerData: T? = null

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * URL constructor
	 *
	 * @param url Target file url
	 */
	constructor(url: URL) {
		fileUrl = url
	}

	/**
	 * File constructor
	 *
	 * @param file Target file object
	 */
	constructor(file: File) : this(file.toURI().toURL())

	/**
	 * URI constructor
	 *
	 * @param uri Target file uri
	 */
	constructor(uri: URI) : this(uri.toURL())

	/* ---------------------------------------------------------
	 *
	 * Implemented methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Read data by bytes
	 *
	 * @param block Block callback
	 * @param byteSize How long is the byteArray
	 */
	override fun readByBytes(byteSize: Int, block: (data: ByteArray) -> Unit) {
		try {
			val stream = fileUrl.openStream()
			val bffData = ByteArray(byteSize)

			while (stream.read(bffData) != -1) {
				block.invoke(bffData)
			}

			stream.close()
		} catch (err: Exception) {
			Log.logE(err)
		}
	}

	/**
	 * Read data by bytes
	 *
	 * @param block Block callback
	 */
	override fun readByBytes(block: (data: ByteArray) -> Unit) =
		readByBytes(1024, block)

	/**
	 * Read data line by line
	 *
	 * @param block Block callback
	 */
	override fun readByLine(block: (data: String) -> Unit) {
		try {
			val stream = fileUrl.openStream()
			val reader = BufferedReader(InputStreamReader(stream))

			reader.forEachLine {
				block.invoke(it)
			}

			reader.close()
			stream.close()
		} catch (err: Exception) {
			Log.logE(err)
		}
	}

}
