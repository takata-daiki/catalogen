/**
 * gmimano 2009
 */
package org.javarosa.graphing.devTest;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.javarosa.graphing.view.AnalysisView;
import org.javarosa.graphing.view.VisualizationFactory;

/**
 * @author gmimano
 *
 */
public class VizMidlet extends MIDlet { 
	Display display;
	AnalysisView form;
	VisualizationFactory myfactory;
 
	/**
	 * 
	 */
	public VizMidlet() {
		display = Display.getDisplay(this);
		
	}  

	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {
		myfactory = new VisualizationFactory();
////		form = new AnalysisView("Testing",myfactory.getVizItem(ChartTypes.TYPE_HOR, "Sample Form", UsefulTestingStuff.getBarItems()),myfactory.getVizItem(ChartTypes.TYPE_TBL, "Sample Table", UsefulTestingStuff.getBarItems()));
//		display.setCurrent(form);

	}

}
