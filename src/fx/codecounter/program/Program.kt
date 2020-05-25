package fx.codecounter.program

import fx.codecounter.configuration.XMLConfiguration
import fx.codecounter.ui.PrimaryFrame

/**
 * Primary object program.
 */
object Program {
	
	/**
	 * Configuration file location
	 */
	@JvmStatic
	private val configurationFile = Program::class.java.getResource("configuration.xml")
	
	/**
	 * application name
	 */
	@JvmStatic
	private lateinit var appName: String
	
	/**
	 * app configuration object
	 */
	@JvmStatic
	lateinit var configuration: XMLConfiguration
	
	/**
	 * app primary frame
	 */
	@JvmStatic
	lateinit var primaryFrame: PrimaryFrame
	
	/**
	 * Main entry point
	 *
	 * @param args program arguments
	 */
	@JvmStatic
	fun main(args: Array<String>) {
		configuration = XMLConfiguration(configurationFile)
		appName = configuration.getTyped<String>("app_name")!!
		primaryFrame = PrimaryFrame(appName)
		
		primaryFrame.isVisible = true
	}
	
}