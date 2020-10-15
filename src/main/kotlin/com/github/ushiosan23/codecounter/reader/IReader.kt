package com.github.ushiosan23.codecounter.reader

/**
 * Interface reader
 *
 * @param T Generic data type
 */
interface IReader<T> {

	/**
	 * Get reader data
	 *
	 * @return [T] get data type
	 */
	fun getData(): T

	/**
	 * Read data by bytes
	 *
	 * @param block Block callback
	 * @param byteSize How long is the byteArray
	 */
	fun readByBytes(byteSize: Int, block: (data: ByteArray) -> Unit)

	/**
	 * Read data by bytes
	 *
	 * @param block Block callback
	 */
	fun readByBytes(block: (data: ByteArray) -> Unit)

	/**
	 * Read data line by line
	 *
	 * @param block Block callback
	 */
	fun readByLine(block: (data: String) -> Unit)

}
