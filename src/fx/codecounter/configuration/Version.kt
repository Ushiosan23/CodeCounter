package fx.codecounter.configuration

import java.io.File
import java.io.InputStream
import java.net.URL

class Version(stream: InputStream) {

    /**
     * Regex version format
     */
    private val regexVersion: Regex = Regex("^(\\d+\\.)+(\\d+\\.)+(\\d+)\$")

    /**
     * Save file stream.
     */
    private val fileStream: InputStream = stream

    /**
     * Major version.
     */
    var major: Long = 0
        private set

    /**
     * Minor version.
     */
    var minor: Long = 0
        private set

    /**
     * Build version.
     */
    var build: Long = 0
        private set

    /**
     * Version string.
     */
    var versionString: String
        private set

    /**
     * Second constructor.
     *
     * @param location file object location.
     */
    constructor(location: File) : this(location.inputStream())

    /**
     * Third constructor.
     *
     * @param location file url location.
     */
    constructor(location: URL) : this(location.openStream())

    /**
     * Initialize object.
     */
    init {

        val reader = fileStream.reader()
        val lines = reader.readLines()

        // Check if version is not valid
        if (lines.isEmpty())
            throw Exception("Version file is empty")

        val versionLine = lines.first()

        // Check version format
        if (!regexVersion.matches(versionLine))
            throw Exception("Version is not valid")

        // split
        val split = versionLine.trim().split(".")


        major = split[0].toLong()
        minor = split[1].toLong()
        build = split[2].toLong()

        versionString = versionLine.trim()
    }

    /**
     * Get version string.
     */
    override fun toString(): String {
        return versionString
    }

}