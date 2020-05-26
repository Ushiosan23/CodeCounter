package fx.codecounter.ui.menu

import fx.codecounter.fn.closeApplication
import fx.codecounter.ui.AboutDialog
import java.awt.event.ActionEvent
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

/**
 * Main MenuBar window
 */
class MainMenu : JMenuBar() {
	
	/**
	 * File menu section
	 */
	private val fileM = JMenu("File")
	
	/**
	 * File menu items
	 */
	private val fileItems = listOf(
		JMenuItem(BaseAction("New scan", this::newScan)),
		null,
		JMenuItem(BaseAction("Exit", this::exitProgram))
	)
	
	/**
	 * Help menu section
	 */
	private val helpM = JMenu("Help")
	
	/**
	 * Help menu items
	 */
	private val helpItems = listOf(
		JMenuItem(BaseAction("About", this::showAbout))
	)
	
	/**
	 * Initialize main menu object
	 */
	init {
		configureMenu(fileM, fileItems)
		configureMenu(helpM, helpItems)
	}
	
	/**
	 * Configure menu element
	 *
	 * @param menu
	 */
	private fun configureMenu(menu: JMenu, items: List<JMenuItem?>?) {
		add(menu)
		// Check items are null
		if (items == null)
			return
		// Create insert items
		items.forEach {
			if (it == null)
				menu.addSeparator()
			else
				menu.add(it)
		}
	}
	
	/**
	 * Create new scan project
	 *
	 * @param e action event
	 */
	private fun newScan(e: ActionEvent) {}
	
	/**
	 * Exit program action
	 *
	 * @param e action event
	 */
	private fun exitProgram(e: ActionEvent) = closeApplication()
	
	/**
	 * Create about dialog
	 *
	 * @param e action event
	 */
	private fun showAbout(e: ActionEvent) {
		AboutDialog()
	}
	
}