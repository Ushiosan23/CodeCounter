package fx.codecounter.ui

import fx.codecounter.program.Program
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JDialog
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.WindowConstants

/**
 * Primary window frame.
 *
 * @param title window title
 */
class PrimaryFrame(title: String) : JDialog(null, title, ModalityType.APPLICATION_MODAL) {
	
	/**
	 * Window width (in configuration file)
	 */
	private var cWidth: Int = 400
	
	/**
	 * Window height (in configuration file)
	 */
	private var cHeight: Int = 400
	
	/**
	 * Window property to set window location (in configuration file)
	 */
	private var cCenter: Boolean = true
	
	/**
	 * Content table
	 */
	private lateinit var contentTable: JTable
	
	/**
	 * initialize object.
	 */
	init {
		defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
		
		cWidth = Program.configuration.getTyped<Int>("width") ?: cWidth
		cHeight = Program.configuration.getTyped<Int>("height") ?: cHeight
		cCenter = Program.configuration.getTyped<Boolean>("center") ?: cCenter
		
		// set configuration
		initializeConfiguration()
	}
	
	/**
	 * Initialize window configuration.
	 */
	private fun initializeConfiguration() {
		size = Dimension(cWidth, cHeight)
		if (cCenter)
			setLocationRelativeTo(null)
		
		// Set layout
		contentPane = JPanel(BorderLayout())
	}
	
	/**
	 * Initialize all components
	 */
	private fun initializeComponents() {
	
	}
	
}