package fx.soft.codecounter.reader.ini

import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.HashSet

/**
 * Class used to parse .ini files
 */
class JIni {
	
	/**
	 * Save all property sections
	 */
	private val mSections: MutableMap<String, Section> = mutableMapOf()
	
	/**
	 * Load file content
	 *
	 * @param stream File reader
	 */
	fun load(stream: InputStream) {
		val reader = InputStreamReader(stream)
		
		// Set default section
		var sectionKey = DEFAULT_SECTION_KEY
		mSections[sectionKey] = Section()
		// Iterate all file lines
		reader.forEachLine { line ->
			// Ignore comments
			if (isCommentLine(line))
				return@forEachLine
			
			// Check if line is section
			if (isSectionLine(line)) {
				sectionKey = getSectionKey(line)
				
				if (mSections.containsKey(sectionKey))
					return@forEachLine
				
				val section = Section()
				section.mDefaultSection = section
				
				mSections[sectionKey] = section
			}
			
			// Create property
			val pair = line.split("=".toRegex(), 2)
			if (pair.size == 2)
				mSections[sectionKey]!!.put(pair[0].trim(), pair[1].trim())
		}
	}
	
	/**
	 * Get all section keys
	 */
	fun getSectionKeys(): Set<String> =
		Collections.unmodifiableSet(mSections.keys)
	
	/**
	 * Check if ini file has section
	 *
	 * @param sectionKey Target section to check
	 */
	@Suppress("MemberVisibilityCanBePrivate")
	fun hasSection(sectionKey: String): Boolean =
		mSections.containsKey(transformSectionKey(sectionKey))
	
	/**
	 * Get section object
	 *
	 * @param sectionKey Target section to get
	 * @throws [NoSuchElementException] Error when section not found
	 */
	@Suppress("MemberVisibilityCanBePrivate")
	fun getSection(sectionKey: String): Section = if (hasSection(sectionKey))
		mSections[sectionKey]!!
	else
		throw NoSuchElementException("Section '$sectionKey' not found")
	
	/**
	 * Get section object or `null` if not exists
	 *
	 * @param sectionKey Target section to get
	 */
	@Suppress("MemberVisibilityCanBePrivate")
	fun getSectionOrNull(sectionKey: String): Section? = try {
		getSection(sectionKey)
	} catch (err: Exception) {
		err.printStackTrace()
		null
	}
	
	/**
	 * Operator to get section property
	 *
	 * @param sectionKey Target section to get
	 */
	operator fun get(sectionKey: String): Section? =
		getSectionOrNull(sectionKey)
	
	/**
	 * Get format object
	 */
	override fun toString(): String {
		return "${this::class.java.canonicalName}: $mSections"
	}
	
	/**
	 * Properties section class
	 */
	class Section {
		
		/**
		 * Section properties
		 */
		@Suppress("JoinDeclarationAndAssignment")
		private val mProperties: MutableMap<String, String>
		
		/**
		 * Section default section
		 */
		lateinit var mDefaultSection: Section
		
		/**
		 * Initialize object
		 */
		init {
			mProperties = mutableMapOf()
		}
		
		/**
		 * Get property keys sections
		 *
		 * @param includeDefault Include default section object
		 */
		@Suppress("MemberVisibilityCanBePrivate")
		fun getKeys(includeDefault: Boolean = false): Set<String> = if (!includeDefault) {
			Collections.unmodifiableSet(mProperties.keys)
		} else {
			val allKeys = HashSet<String>(mProperties.keys)
			allKeys.addAll(mDefaultSection.getKeys())
			allKeys
		}
		
		/**
		 * Check if property exists
		 *
		 * @param key Target property to search
		 * @param includeDefault Include keys
		 * @return [Boolean] Result of search
		 */
		@Suppress("MemberVisibilityCanBePrivate")
		fun containsKeys(key: String, includeDefault: Boolean = false): Boolean {
			val finalKey = transformKey(key)
			
			return if (!includeDefault) {
				mProperties.containsKey(finalKey)
			} else {
				mProperties.containsKey(key) || mDefaultSection.containsKeys(key)
			}
		}
		
		/**
		 * Get property string
		 *
		 * @param key Target property to get
		 * @param default String default if value not exists
		 * @return [String] Property value or `null` if not exists
		 */
		@Suppress("MemberVisibilityCanBePrivate")
		fun getString(key: String, default: String = ""): String {
			val tKey = transformKey(key)
			
			if (!containsKeys(key)) {
				if (!mDefaultSection.containsKeys(key))
					return default
				
				return mDefaultSection.getString(key)
			}
			
			return mProperties[tKey] ?: default
		}
		
		/**
		 * Get string list
		 *
		 * @param key Target property key
		 * @param separator Value string separator
		 *
		 * @return [List] with result value
		 */
		fun getStringList(key: String, separator: String = VALUE_SEPARATOR): List<String> {
			val result = getString(key)
			
			if (result.isEmpty())
				return emptyList()
			
			return result.split(separator).toList()
		}
		
		/**
		 * Get integer property
		 *
		 * @param key Target property key
		 * @return [Int] result value or default value if is not value or `null` if default value is not defined
		 */
		fun getInt(key: String, default: Int? = null): Int? =
			getString(key).toIntOrNull() ?: default
		
		/**
		 * Get float property
		 *
		 * @param key Target property key
		 * @return [Float] result value or default value if is not value or `null` if default value is not defined
		 */
		fun getFloat(key: String, default: Float?): Float? =
			getString(key).toFloatOrNull() ?: default
		
		/**
		 * Get double property
		 *
		 * @param key Target property key
		 * @return [Double] result value or default value if is not value or `null` if default value is not defined
		 */
		fun getDouble(key: String, default: Double?) =
			getString(key).toDoubleOrNull() ?: default
		
		/**
		 * Operator get. This method is called like array called Section[key]
		 *
		 * @param key Target property name
		 * @return [T] Result property or `null`
		 */
		inline operator fun <reified T> get(key: String, default: T? = null): T? = when (T::class) {
			String::class -> getString(key, default as String? ?: "") as T
			Int::class -> getInt(key, default as Int?) as T
			Float::class -> getFloat(key, default as Float?) as T
			Double::class -> getDouble(key, default as Double?) as T
			else -> default
		}
		
		/**
		 * Put a key in current section
		 *
		 * @param key Property key name
		 * @param value Property key value
		 * @return [String] inserted value
		 */
		fun put(key: String, value: String): String? =
			mProperties.put(transformKey(key), value)
		
		/**
		 * Get section string format
		 */
		override fun toString(): String {
			return mProperties.toString()
		}
		
		/**
		 * Companion Section object
		 */
		companion object {
			
			/**
			 * Case sensitive property checker
			 */
			@JvmStatic
			private val KEY_CASE_SENSITIVE: Boolean = false
			
			/**
			 * Transform key to target format
			 */
			private fun transformKey(key: String): String = if (KEY_CASE_SENSITIVE)
				key
			else
				key.toLowerCase()
			
		}
		
	}
	
	/**
	 * Companion JIni object
	 */
	companion object {
		
		/**
		 * Constant with string comment
		 */
		@JvmStatic
		private val COMMENT_CHARS: String = ";#"
		
		/**
		 * Constant with char separator
		 */
		@JvmStatic
		private val VALUE_SEPARATOR: String = ","
		
		/**
		 * Save ini file property default key
		 */
		@JvmStatic
		private val DEFAULT_SECTION_KEY: String = transformSectionKey("default")
		
		/**
		 * Property to manage properties string case
		 */
		@JvmStatic
		private val SECTION_KEY_CASE_SENSITIVE: Boolean = false
		
		/**
		 * Transform section key to lower case
		 *
		 * @param sectionKey Target section to transform
		 * @return [String] transform section key
		 */
		@Suppress("SameParameterValue")
		@JvmStatic
		private fun transformSectionKey(sectionKey: String): String = if (SECTION_KEY_CASE_SENSITIVE)
			sectionKey
		else
			sectionKey.toLowerCase()
		
		/**
		 * Check if line is comment
		 *
		 * @param line Target line to check
		 */
		@JvmStatic
		private fun isCommentLine(line: String): Boolean {
			val fLine = line.trim()
			
			if (fLine.isEmpty())
				return true
			
			val fChar = fLine.substring(0, 1)
			return COMMENT_CHARS.contains(fChar)
		}
		
		/**
		 * Check if line is section
		 *
		 * @param line Target line to check
		 */
		@JvmStatic
		private fun isSectionLine(line: String): Boolean {
			val fLine = line.trim()
			return fLine.startsWith("[") && fLine.endsWith("]")
		}
		
		/**
		 * Get section key name from line
		 *
		 * @param line Target line to get section key
		 * @throws IllegalArgumentException Error when line is not valid section
		 */
		@JvmStatic
		private fun getSectionKey(line: String): String {
			if (!isSectionLine(line))
				throw IllegalArgumentException("Line '$line' is not a section")
			
			val fLine = line.trim()
			val sectionKey = fLine.substring(1 until fLine.length - 1).trim()
			
			if (sectionKey.isEmpty())
				throw IllegalArgumentException("Section key cannot be empty")
			
			return transformSectionKey(sectionKey)
		}
		
	}
	
}
