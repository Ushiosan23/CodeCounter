package fx.codecounter.ui

import fx.codecounter.program.Program
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.EmptyBorder

class AboutDialog : JDialog(Program.primaryFrame, "About", true) {
	
	/**
	 * Bold font
	 */
	private val boldFont = Font("Arial", Font.BOLD, 14)
	
	/**
	 * Normal font
	 */
	private val normalFont = Font("Arial", Font.PLAIN, 14)
	
	/**
	 * Help info container
	 */
	private val container = JPanel(GridLayout(0, 2, 5, 10))
	
	/**
	 * Initialize object
	 */
	init {
		contentPane = JPanel(BorderLayout())
		
		defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
		isResizable = false
		isAlwaysOnTop = true
		size = Dimension(400, 220)
		setLocationRelativeTo(null)
		
		val centerPanel = JPanel()
		centerPanel.add(container)
		contentPane.add(centerPanel, BorderLayout.CENTER)
		
		initializeComponents()
		
		isVisible = true
	}
	
	/**
	 * Initialize components
	 */
	private fun initializeComponents() {
		val appName = Program.configuration["app_name"].toString()
		val titleAbout = JLabel(appName)
		
		titleAbout.font = Font("Arial", Font.BOLD, 24)
		titleAbout.horizontalAlignment = SwingConstants.CENTER
		titleAbout.border = EmptyBorder(12, 0, 12, 0)
		contentPane.add(titleAbout, BorderLayout.NORTH)
		
		val lbTitleName = JLabel("App Name: ")
		val lbValueName = JLabel(appName)
		
		container.add(boldLabel(lbTitleName))
		container.add(normalLabel(lbValueName))
		
		val lbTitleVersion = JLabel("Version: ")
		val lbValueVersion = JLabel(Program.configuration["app_version"].toString())
		
		container.add(boldLabel(lbTitleVersion))
		container.add(normalLabel(lbValueVersion))
		
		val lbTitleKtVersion = JLabel("Kotlin version: ")
		val lbValueKtVersion = JLabel(KotlinVersion.CURRENT.toString())
		
		container.add(boldLabel(lbTitleKtVersion))
		container.add(normalLabel(lbValueKtVersion))
		
		val lbTitleJvmVersion = JLabel("Java version: ")
		val lbValueJvmVersion = JLabel(System.getProperty("java.version"))
		
		container.add(boldLabel(lbTitleJvmVersion))
		container.add(normalLabel(lbValueJvmVersion))
	}
	
	/**
	 * Set normal font to label
	 *
	 * @param label target label
	 */
	private fun normalLabel(label: JLabel): JLabel {
		label.font = normalFont
		return label
	}
	
	/**
	 * Set bold font to label
	 *
	 * @param label target label
	 */
	private fun boldLabel(label: JLabel): JLabel {
		label.font = boldFont
		return label
	}
	
}