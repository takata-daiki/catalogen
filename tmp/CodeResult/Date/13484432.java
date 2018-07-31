/**
 * Date Created: Apr 12, 2009
 */
package forteresce.portprofile;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import forteresce.portprofile.gui.PortProfileFrame;

public class PortProfileGUI {

	public static void main(String[] args) {
		
		//Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PortProfileFrame ppFrame;

				try {
					// Set system look and feel
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());

					ppFrame = new PortProfileFrame();
					
					ppFrame.pack();
					ppFrame.setVisible(true);
					ppFrame.init();
				} catch (UnsupportedLookAndFeelException e) {
					// handle exception
					System.out.println(e);
				} catch (ClassNotFoundException e) {
					// handle exception
					System.out.println(e);
				} catch (InstantiationException e) {
					// handle exception
					System.out.println(e);
				} catch (IllegalAccessException e) {
					// handle exception
					System.out.println(e);
				}
			}
		});

	}

}
