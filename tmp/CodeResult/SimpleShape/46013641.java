package pk.demos.osgi.paint.internal;

import javax.swing.Icon;
import javax.swing.SwingUtilities;

import pk.demos.osgi.paint.PaintFrame;
import pk.demos.osgi.shape.SimpleShape;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Rozszerzenie <tt>ServiceTracker</tt> w celu śledzenia serwisów typu <tt>SimpleShape</tt>.
 * Klasa jest odpowiedzialna za nasłuchiwanie rejestracji i wyrejestrowań
 * serwisów typu <tt>SimpleShape</tt> i informowanie aplikacji o dostępnych kształtach.
 * Klasa zapewnia przetwarzanie wszelkich powiadomień w wątku obsługi zdarzeń Swing-a
 * w celu uniknięcia potrzeby synchronizacji i problemów z odświeżaniem.
 **/
public class ShapeTracker extends ServiceTracker {
	
	private static enum ActionType {
		// Flaga informująca o dodanym kształcie.
		ADDED,
		// Flaga informująca o zmodyfikowanym kształcie
		MODIFIED,
		// Flaga informująca o usuniętym kształcie.
		REMOVED
	}
	
	// Kontekst paczki wykorzystywany do śledzenia.
	private BundleContext context;
	// Obiekt aplikacji dostający powiadomienia.
	private PaintFrame frame;

	/**
	 * Tworzy obiekt klasy śledzącej używającej zadany kontekst paczki
	 * w celu śledzenia serwisów i informujacy o zmianach zadany obiekt aplikacji.
	 * 
	 * @param context Kontekst paczki wykorzystywany do śledzenia.
	 * @param frame Obiekt aplikacji dostający powiadomienia o zmianach serwisów kształtów.
	 **/
	public ShapeTracker(BundleContext context, PaintFrame frame) {
		super(context, SimpleShape.class.getName(), null);
		this.context = context;
		this.frame = frame;
	}

	/**
	 * Nadpisuje funkcjonalność <tt>ServiceTracker</tt>
	 * w celu poinformowania obiektu aplikacji o dodaniu serwisu.
	 * 
	 * @param ref Referencja na dodany serwis.
	 * @return Obiekt dodanego serwisu.
	 **/
	@Override
	public DefaultShape addingService(ServiceReference ref) {
		DefaultShape shape = new DefaultShape(context, ref);
		processShapeOnEventThread(ActionType.ADDED, ref, shape);
		return shape;
	}

	/**
	 * Nadpisuje funkcjonalność <tt>ServiceTracker</tt>
	 * w celu poinformowania obiektu aplikacji o modyfikacji serwisu.
	 * 
	 * @param ref Referencja na zmodyfikowany serwis.
	 * @return Obiekt zmodyfikowanego serwisu.
	 **/
	@Override
	public void modifiedService(ServiceReference ref, Object svc) {
		processShapeOnEventThread(ActionType.MODIFIED, ref, (SimpleShape)svc);
	}

	/**
	 * Nadpisuje funkcjonalność <tt>ServiceTracker</tt>
	 * w celu poinformowania obiektu aplikacji o usunięciu serwisu.
	 * 
	 * @param ref Referencja na usunięty serwis.
	 * @return Obiekt usuniętego serwisu.
	 **/
	@Override
	public void removedService(ServiceReference ref, Object svc) {
		processShapeOnEventThread(ActionType.REMOVED, ref, (SimpleShape)svc);
		((DefaultShape)svc).dispose();
	}

	/**
	 * Przetwarza powiadomienie z <tt>ServiceTracker</tt>, 
	 * zapewniając propagację informacji w wątku obsługi zdarzeń Swing-a.
	 * 
	 * @param action Typ powiadomienia.
	 * @param ref Referencja na serwis.
	 * @param shape Obiekt serwisu.
	 **/
	private void processShapeOnEventThread(ActionType action, ServiceReference ref, SimpleShape shape) {
		// Jeżeli framework nie jest uruchamiany lub uruchomiony, to nie należy przetwarzać żądania
		if ((context.getBundle(0).getState() & (Bundle.STARTING | Bundle.ACTIVE)) == 0) {
			return;
		}

		try {
			if (SwingUtilities.isEventDispatchThread()) {
				processShape(action, ref, shape);
			} else {
				SwingUtilities.invokeAndWait(new ShapeRunnable(action, ref, shape));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Przetwarza powiadomienie o serwisie i przekazuje je obiektowi aplikacji
	 * w odpowiedni sposób.
	 * 
	 * @param action Typ powiadomienia.
	 * @param ref Referencja na serwis.
	 * @param shape Obiekt serwisu.
	 **/
	private void processShape(ActionType action, ServiceReference ref, SimpleShape shape) {
		String name = (String) ref.getProperty(SimpleShape.NAME_PROPERTY);

		switch (action) {
			case MODIFIED:
				frame.removeShape(name);
				// Po usunięciu następuje dodanie zmodyfikowanego obiektu serwisu.
			case ADDED:
				Icon icon = (Icon) ref.getProperty(SimpleShape.ICON_PROPERTY);
				frame.addShape(name, icon, shape);
				break;
	
			case REMOVED:
				frame.removeShape(name);
				break;
		}
	}

	/**
	 * Prosta klasa służąca do przetwarzania powiadomienia w osobnym wątku.
	 **/
	private class ShapeRunnable implements Runnable {
		private ActionType action;
		private ServiceReference ref;
		private SimpleShape shape;

		public ShapeRunnable(ActionType action, ServiceReference ref, SimpleShape shape) {
			this.action = action;
			this.ref = ref;
			this.shape = shape;
		}

		@Override
		public void run() {
			processShape(action, ref, shape);
		}
	}

}
