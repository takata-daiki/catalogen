package pk.demos.osgi.paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import pk.demos.osgi.paint.internal.DefaultShape;
import pk.demos.osgi.shape.SimpleShape;

/**
 * Główna klasa aplikacji reprezentująca okienko z paskiem narzędziowym 
 * kształtów i płótnem do rysowania. Klasa ta nie odwołuje się bezpośrednio
 * do frameworka OSGi; zamiast tego wstrzykiwane są w niej
 * dostepne instancje <tt>SimpleShape</tt> w celu eliminacji
 * zależności od API OSGi.
 **/
public class PaintFrame extends JFrame implements MouseListener,MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private static final int BOX = 54;
	private JToolBar toolbar;
	private String selected;
	private JPanel panel;
	private ShapeComponent selectedComponent;
	private Map<String, ShapeInfo> shapes = new HashMap<String, ShapeInfo>();
	private ActionListener reusableActionListener = new ShapeActionListener();
	private SimpleShape defaultShape = new DefaultShape();

	/**
	 * Domyślny konstruktor tworzący okienko.
	 **/
	public PaintFrame() {
		super("Rysowanko");

		toolbar = new JToolBar("Pasek narzędziowy");
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		panel.setMinimumSize(new Dimension(400, 400));
		panel.addMouseListener(this);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);
		setSize(400, 400);
	}

	/**
	 * Ustawienie aktualnie wybranego kształtu do rysowania na płótnie.
	 * 
	 * @param name Nazwa kształtu użytego do rysowania.
	 **/
	public void selectShape(String name) {
		selected = name;
	}

	/**
	 * Pobiera dostępną instancję <tt>SimpleShape</tt> 
	 * o zadanej nazwie.
	 * 
	 * @param name Nazwa kształtu typu <tt>SimpleShape</tt> do pobranie.
	 * @return Adekwatna instancja <tt>SimpleShape</tt> jeżeli dostępna lub 
	 *         instancja domyślna typu<tt>DefaultShape</tt>.
	 **/
	public SimpleShape getShape(String name) {
		ShapeInfo info = shapes.get(name);
		if (info == null) {
			return defaultShape;
		} else {
			return info.shape;
		}
	}

	/**
	 * Wstrzykuje dostępny kształt typu <tt>SimpleShape</tt> do okienka.
	 * 
	 * @param name Nazwa wstrzykniętego kształtu typu <tt>SimpleShape</tt>.
	 * @param icon Ikona przedstawiająca wstryknięty kształt typu <tt>SimpleShape</tt>.
	 * @param shape Wstrzyknięta instancja kształtu typu <tt>SimpleShape</tt>.
	 **/
	public void addShape(String name, Icon icon, SimpleShape shape) {
		shapes.put(name, new ShapeInfo(name, icon, shape));
		JButton button = new JButton(icon);
		button.setActionCommand(name);
		button.setToolTipText(name);
		button.addActionListener(reusableActionListener);

		if (selected == null) {
			button.doClick();
		}

		toolbar.add(button);
		toolbar.validate();
		repaint();
	}

	/**
	 * Usuwa niedostępny już kształt typu <tt>SimpleShape</tt> z okienka.
	 * 
	 * @param name Nazwa kształtu typu <tt>SimpleShape</tt> do usunięcia.
	 **/
	public void removeShape(String name) {
		shapes.remove(name);

		if ((selected != null) && selected.equals(name)) {
			selected = null;
		}

		for (int i = 0; i < toolbar.getComponentCount(); i++) {
			JButton sb = (JButton) toolbar.getComponent(i);
			if (sb.getActionCommand().equals(name)) {
				toolbar.remove(i);
				toolbar.invalidate();
				validate();
				repaint();
				break;
			}
		}

		if ((selected == null) && (toolbar.getComponentCount() > 0)) {
			((JButton) toolbar.getComponent(0)).doClick();
		}
	}

	/**
	 * Implementuje metodę interfejsu <tt>MouseListener</tt>
	 * do rysowania wybranego kształtu na płótnie.
	 * 
	 * @param evt Powiązane zdarzenie myszy.
	 **/
	@Override
	public void mouseClicked(MouseEvent evt) {
		if (selected == null) {
			return;
		}

		if (panel.contains(evt.getX(), evt.getY())) {
			ShapeComponent sc = new ShapeComponent(this, selected);
			sc.setBounds(evt.getX() - BOX / 2, evt.getY() - BOX / 2, BOX, BOX);
			panel.add(sc, 0);
			panel.validate();
			panel.repaint(sc.getBounds());
		}
	}

	@Override
	public void mouseEntered(MouseEvent evt) {}

	@Override
	public void mouseExited(MouseEvent evt) {}

	/**
	 * Implementuje metodę interfejsu <tt>MouseListener</tt>
	 * do zainicjalizowania przenoszenia kształtu.
	 * 
	 * @param evt Powiązane zdarzenie myszy.
	 **/
	@Override
	public void mousePressed(MouseEvent evt) {
		Component c = panel.getComponentAt(evt.getPoint());
		if (c instanceof ShapeComponent) {
			selectedComponent = (ShapeComponent) c;
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			panel.addMouseMotionListener(this);
			selectedComponent.repaint();
		}
	}

	/**
	 * Implementuję metodę interfejsu <tt>MouseListener</tt>
	 * do zakończenia przenoszenia kształtu.
	 * 
	 * @param evt Powiązane zdarzenie myszy.
	 **/
	@Override
	public void mouseReleased(MouseEvent evt) {
		if (selectedComponent != null) {
			panel.removeMouseMotionListener(this);
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			selectedComponent.setBounds(evt.getX() - BOX / 2, evt.getY() - BOX / 2, BOX, BOX);
			selectedComponent.repaint();
			selectedComponent = null;
		}
	}

	/**
	 * Implementuje metodę interfejsu <tt>MouseMotionListener</tt>
	 * do przeniesienia przeciąganego kształtu.
	 * 
	 * @param evt Powiązane zdarzenie myszy.
	 **/
	@Override
	public void mouseDragged(MouseEvent evt) {
		selectedComponent.setBounds(evt.getX() - BOX / 2, evt.getY() - BOX / 2, BOX, BOX);
	}

	@Override
	public void mouseMoved(MouseEvent evt) {}

	/**
	 * Prosty słuchacz dla przycisków paska narzędziowego, który ustawia
	 * aktualnie wybrany kształt w momencie naciśnięcia przycisku.
	 **/
	private class ShapeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			selectShape(evt.getActionCommand());
		}
	}

	/**
	 * Klasa przechowująca różne informacje o dostępnym kształcie.
	 **/
	private static class ShapeInfo {
		public String name;
		public Icon icon;
		public SimpleShape shape;

		public ShapeInfo(String name, Icon icon, SimpleShape shape) {
			this.name = name;
			this.icon = icon;
			this.shape = shape;
		}
	}

}
