package pk.demos.osgi.shape;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Interfejs definuje serwis <tt>SimpleShape</tt> service. 
 * Serwis ten jest używany do rysowania kształtów.
 * Posiada dwie właściwości serwisu:
 * <ul>
 * <li>simple.shape.name - nazwa kształtu typu <tt>String</tt>.</li>
 * <li>simple.shape.icon - ikona kształtu typu <tt>Icon</tt>.</li>
 * </ul>
 **/
public interface SimpleShape {

  /**
   * Właściwość serwisu dla nazwy kształtu.
   **/
  public static final String NAME_PROPERTY = "simple.shape.name";

  /**
   * Właściwość serwisu dla ikony kształtu.
   **/
  public static final String ICON_PROPERTY = "simple.shape.icon";

  /**
   * Narysuj kształt na danej pozycji.
   * 
   * @param g2 Obiekt graficzny służący do rysowania.
   * @param p Pozycja do narysowania kształtu.
   **/
  public void draw(Graphics2D g2, Point p);
}

