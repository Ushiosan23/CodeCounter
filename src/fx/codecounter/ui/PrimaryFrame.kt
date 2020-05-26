package fx.codecounter.ui

import fx.codecounter.data.LanguageResult
import fx.codecounter.program.Program
import fx.codecounter.ui.menu.BaseAction
import fx.codecounter.ui.menu.MainMenu
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.*
import javax.swing.table.DefaultTableModel

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
	private val contentTable: JTable = JTable()
	
	/**
	 * Bottom panel used to locale buttons
	 */
	private val bottomPanel = JPanel(FlowLayout(FlowLayout.RIGHT, 10, 10))
	
	/**
	 * Save button
	 */
	private val buttonSave = JButton(BaseAction("Save"))
	
	/**
	 * Clear button
	 */
	private val buttonClear = JButton(BaseAction("Clear"))
	
	/**
	 * initialize object.
	 */
	init {
		jMenuBar = MainMenu()
		defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
		
		cWidth = Program.configuration.getTyped<Int>("width") ?: cWidth
		cHeight = Program.configuration.getTyped<Int>("height") ?: cHeight
		cCenter = Program.configuration.getTyped<Boolean>("center") ?: cCenter
		
		// set configuration
		initializeConfiguration()
		initializeComponents()
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
		val scroll = JScrollPane(contentTable)
		contentTable.model = getTableModel(LanguageResult.getColumnsModel(), arrayOf())
		
		// Add buttons to panel
		bottomPanel.add(buttonSave)
		bottomPanel.add(buttonClear)
		
		// Add panels to contentPane
		contentPane.add(scroll, BorderLayout.CENTER)
		contentPane.add(bottomPanel, BorderLayout.SOUTH)
	}
	
	/**
	 * Get data table data model
	 *
	 * @param columns table column names
	 * @param data table data
	 */
	private fun getTableModel(columns: Array<String>, data: Array<Array<Any?>>) = object : DefaultTableModel(data, columns) {
		
		/**
		 * Cannot edit table content
		 */
		override fun isCellEditable(row: Int, column: Int): Boolean {
			return false
		}
		
	}
}