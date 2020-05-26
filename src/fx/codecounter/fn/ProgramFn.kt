package fx.codecounter.fn

import fx.codecounter.program.Program
import javax.swing.JOptionPane

/**
 * Close application action
 */
fun closeApplication() {
	if (Program.currentProject != null) {
		val ask = JOptionPane.showConfirmDialog(
			Program.primaryFrame,
			"Do you want to close program? \nAll changes might be lost",
			"Confirmation message",
			JOptionPane.YES_NO_OPTION
		)
		
		if (ask == JOptionPane.YES_OPTION)
			Program.primaryFrame.dispose()
	} else {
		Program.primaryFrame.dispose()
	}
}