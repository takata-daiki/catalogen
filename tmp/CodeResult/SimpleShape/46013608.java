package pk.demos.osgi.shape.square.internal;

import java.util.Hashtable;

import javax.swing.ImageIcon;

import pk.demos.osgi.shape.SimpleShape;
import pk.demos.osgi.shape.square.Square;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Klasa implementuje prosty aktywator dla serwisu <tt>SimpleShape</tt> o kształcie kwadratu. 
 * Aktywator tworzy instancję serwisu kwadratu i rejestruje ją w rejestrze serwisów
 * z odpowiednimi właściwościami (nazwa i ikona) serwisu.
 **/

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Hashtable<String,Object> dict = new Hashtable<String,Object>();
	    dict.put(SimpleShape.NAME_PROPERTY, "Square");
	    dict.put(SimpleShape.ICON_PROPERTY, new ImageIcon(Square.class.getResource("square.png")));
	    context.registerService(SimpleShape.class.getName(), new Square(), dict);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}

