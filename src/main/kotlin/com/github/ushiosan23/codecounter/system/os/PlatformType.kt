package com.github.ushiosan23.codecounter.system.os

/**
 * Enumerator to detect device
 *
 * @param regex Platform regex
 */
enum class PlatformType(private val regex: Regex? = null) {

	/**
	 * Windows platform type
	 */
	WINDOWS(Regex("(win|windows)", RegexOption.IGNORE_CASE)),

	/**
	 * Mac platform type
	 */
	DARWIN(Regex("(darwin|macos|ios|osx)", RegexOption.IGNORE_CASE)),

	/**
	 * Linux platform type
	 */
	LINUX(Regex("(nix|unix|linux)", RegexOption.IGNORE_CASE)),

	/**
	 * Solaris platform type
	 */
	SOLARIS(Regex("(sol|solaris)", RegexOption.IGNORE_CASE)),

	/**
	 * Unknown platform
	 */
	UNKNOWN;

	/**
	 * Companion object
	 */
	companion object {

		/**
		 * Get platform string value
		 */
		@JvmStatic
		val currentPlatformString: String = System.getProperty("os.name").toLowerCase()

		/**
		 * Get platform version
		 */
		@JvmStatic
		val platformVersion: String = System.getProperty("os.version")

		/**
		 * Get platform arch
		 */
		@JvmStatic
		val platformArch: String = System.getProperty("os.arch")

		/**
		 * Get current enum platform value
		 */
		@JvmStatic
		val currentPlatform: PlatformType =
			getFromString(currentPlatformString)

		/**
		 * Get platform from string
		 *
		 * @param platform Target string to search
		 *
		 * @return [PlatformType] platform type
		 */
		@JvmStatic
		fun getFromString(platform: String): PlatformType = values().firstOrNull {
			it.regex?.containsMatchIn(platform) ?: false
		} ?: UNKNOWN

	}

}
