package pk.demos.osgi.shape.triangle.internal;

import java.util.Hashtable;

import javax.swing.ImageIcon;

import pk.demos.osgi.shape.SimpleShape;
import pk.demos.osgi.shape.triangle.Triangle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Klasa implementuje prosty aktywator dla serwisu <tt>SimpleShape</tt> o kształcie trójkąta. 
 * Aktywator tworzy instancję serwisu trójkąta i rejestruje ją w rejestrze serwisów
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
	    dict.put(SimpleShape.NAME_PROPERTY, "Triangle");
	    dict.put(SimpleShape.ICON_PROPERTY, new ImageIcon(Triangle.class.getResource("triangle.png")));
	    context.registerService(SimpleShape.class.getName(), new Triangle(), dict);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}

