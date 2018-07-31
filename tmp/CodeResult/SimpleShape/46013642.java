package pk.demos.osgi.paint.internal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.ImageIcon;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pk.demos.osgi.shape.SimpleShape;

import pk.demos.osgi.paint.PaintFrame;

/**
 * Ta klasa służy jako proxy kształtu w celu oddzielenia tworzenia kształtu
 * od paczki dostarczającej implementację kształtu. Klasa służy również jako
 * kształt zastępczy, kiedy poprzednio użyty kształt nie jest już więcej dostępny.
 * Te dwa przypadki są od siebie niezależne, ale zostały połączone w jedną klasę
 * w celu zredukowania liczyby klas używanych w aplikacji.
 * Funkcjonalność związana z proxy pokazuje wzorzec leniwej inicjalizacji
 * obiektów kształtów w celu zwiększenia wydajności; ten pośredni poziom
 * może zostać usunięty, jeżeli natychmiastowe tworzenie obiektów nie jest kosztowne..
 **/
public class DefaultShape implements SimpleShape {
	private SimpleShape shape;
	private ImageIcon icon;
	private BundleContext context;
	private ServiceReference ref;

	/**
	 * Tworzy kształt zastępczy z domyślną ikoną. Używane, gdy poprzednio
	 * wybrany kształt nie jest już dłużej dostępny.
	 **/
	public DefaultShape() {
		// nic nie rób
	}

	/**
	 * Konstruuje proxy kształtu, który leniwie pobiera odpowiedni serwis.
	 * 
	 * @param context Kontekst paczki używany do pobrania serwisu kształtu.
	 * @param ref Referencja do serwisu.
	 **/
	public DefaultShape(BundleContext context, ServiceReference ref) {
		this.context = context;
		this.ref = ref;
	}

	/**
	 * Unicestwienie obiektu serwisu proxy; wywoływane, gdy
	 * docelowy serwis kształtu zostanie dezaktywowany.
	 **/
	public void dispose() {
		if (shape != null) {
			context.ungetService(ref);
			context = null;
			ref = null;
			shape = null;
		}
	}

	/**
	 * Implementuje metodę <tt>SimpleShape.draw()</tt>. Kiedy obiekt działa jako proxy, 
	 * ta metoda pobiera adekwatny serwis kształtu i używa go do narysowania siebie. 
	 * Kiedy działa jako kształt zastępczy, rysuje domyślną ikonę.
	 * 
	 * @param g2 Obiekt graficzny służący do rysowania.
	 * @param p Pozycja do narysowania kształtu.
	 **/
	public void draw(Graphics2D g2, Point p) {
		// Jeżeli jest to proxy, pobiera instancję
		// serwisu kształtu i używa jej do rysowania.
		if (context != null) {
			try {
				if (shape == null) {
					// Pobranie serwisu kształtu.
					shape = (SimpleShape)context.getService(ref);
				}
				// Narysowanie kształtu.
				shape.draw(g2, p);
				// Wyjdź, gdy jest ok.
				return;
			} catch (Exception ex) {
				// To nie powinno się zdarzyć, ale jeżeli jednak się przytrafi,
				// to narysujemy domyślną ikonę.
			}
		}

		// Jeżeli kształt proxy nie mógł być narysowany z jakiegoś powodu
		// lub jest to kształt zastępczy, to narysuj domyślną ikonę.
		if (icon == null) {
			try {
				icon = new ImageIcon(PaintFrame.class.getResource("underc.png"));
			} catch (Exception ex) {
				ex.printStackTrace();
				g2.setColor(Color.red);
				g2.fillRect(0, 0, 60, 60);
				return;
			}
		}
		g2.drawImage(icon.getImage(), 0, 0, null);
	}

}
