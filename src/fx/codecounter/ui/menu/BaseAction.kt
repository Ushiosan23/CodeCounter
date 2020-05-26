package fx.codecounter.ui.menu

import java.awt.event.ActionEvent
import javax.swing.AbstractAction

/**
 * Menu item action
 *
 * @param actionName action name
 * @param actionHandler method to execute
 */
class BaseAction(
	actionName: String,
	private val actionHandler: ((e: ActionEvent) -> Unit)? = null) : AbstractAction(actionName) {
	
	/**
	 * Called when action is invoked
	 *
	 * @param e action event
	 */
	override fun actionPerformed(e: ActionEvent) {
		actionHandler?.invoke(e)
	}
	
}