package com.thoughtworks.studio.tools.cardkit.gui;

import org.jhotdraw.draw.*;
import org.jhotdraw.util.ReversedList;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;
import org.apache.log4j.Logger;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.awt.*;
import java.util.*;
import java.io.IOException;

import com.thoughtworks.studio.tools.cardkit.gui.figures.CardFigure;
import com.thoughtworks.studio.tools.cardkit.*;
import com.thoughtworks.studio.tools.cardkit.card.Card;

public class CardwallDrawing extends AbstractDrawing {

    private static Logger logger = Logger.getLogger(CardwallDrawing.class);

    private boolean needsSorting = false;
    private Rectangle2D.Double canvasBounds;
    private CardRepository repository;

    public CardwallDrawing(CardWallIdentifier cardwallidentifer) {
        if (repository == null) {
            this.repository = (CardRepository) BeanFactory.getBean("cardRepository");
        }
        repository.setCardWallIdentifier(cardwallidentifer);
        CardwallInputOutputFormat ioFormat =
                new CardwallInputOutputFormat(new CardwallDOMFactory());
        LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
        inputFormats.add(ioFormat);
        setInputFormats(inputFormats);
        LinkedList<OutputFormat> outputFormats = new LinkedList<OutputFormat>();
        outputFormats.add(ioFormat);
        outputFormats.add(new ImageOutputFormat());
        setOutputFormats(outputFormats);


    }


    public CardRepository getRepository() {
        return repository;
    }

    public void setRepository(CardRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new instance.
     */
    public CardwallDrawing() {
        this(null);
    }

    /**
     * Creates a new instance.
     */
    public static CardwallDrawing createCardwallDrawing(CardWallIdentifier cardwallidentifer) {
        return  new CardwallDrawing(cardwallidentifer);
    }

    public void draw(Graphics2D g) {
        synchronized (getLock()) {
            ensureSorted();
            ArrayList<Figure> toDraw = new ArrayList<Figure>(getChildren().size());
            Rectangle clipRect = g.getClipBounds();
            for (Figure f : getChildren()) {
                if (f.getDrawingArea().intersects(clipRect)) {
                    toDraw.add(f);
                }
            }
            draw(g, toDraw);
        }
    }

    public void read(DOMInput in) throws IOException {
        in.openElement("figures");
        for (int i = 0; i < in.getElementCount(); i++) {
            Figure f = (Figure) in.readObject(i);
            if(f instanceof CardFigure){
                CardFigure cf= (CardFigure) f;
                update(cf);
            }
            add(f);
        }
        in.closeElement();
    }

    private void update(CardFigure cf) {
        Card card = getRepository().getCard(cf.getNumber());
        if(card !=null){
             cf.setNumberArea(card.getNumber());
            cf.setNameArea(card.getName());
            cf.setCard(card);
        }
    }

    public void write(DOMOutput out) throws IOException {
        out.openElement("figures");

        for (Figure f : getChildren()) {
            out.writeObject(f);
        }
        out.closeElement();
    }
    public void write() throws IOException {
        CardwallInputOutputFormat outputFormat =(CardwallInputOutputFormat) getOutputFormats().get(0);
        outputFormat.write(this);
    }

    public void draw(Graphics2D g, Collection<Figure> children) {
        Rectangle2D clipBounds = g.getClipBounds();
        if (clipBounds != null) {
            for (Figure f : children) {
                if (f.isVisible() && f.getDrawingArea().intersects(clipBounds)) {
                    f.draw(g);
                }
            }
        } else {
            for (Figure f : children) {
                if (f.isVisible()) {
                    f.draw(g);
                }
            }
        }
    }

    public java.util.List<Figure> sort(Collection<Figure> c) {
        HashSet<Figure> unsorted = new HashSet<Figure>();
        unsorted.addAll(c);
        ArrayList<Figure> sorted = new ArrayList<Figure>(c.size());
        for (Figure f : getChildren()) {
            if (unsorted.contains(f)) {
                sorted.add(f);
                unsorted.remove(f);
            }
        }
        for (Figure f : c) {
            if (unsorted.contains(f)) {
                sorted.add(f);
                unsorted.remove(f);
            }
        }
        return sorted;
    }

    public Figure findFigure(Point2D.Double p) {
        for (Figure f : getFiguresFrontToBack()) {
            if (f.isVisible() && f.contains(p)) {
                return f;
            }
        }
        return null;
    }

    public Figure findFigureExcept(Point2D.Double p, Figure ignore) {
        for (Figure f : getFiguresFrontToBack()) {
            if (f != ignore && f.isVisible() && f.contains(p)) {
                return f;
            }
        }
        return null;
    }

    public Figure findFigureBehind(Point2D.Double p, Figure figure) {
        boolean isBehind = false;
        for (Figure f : getFiguresFrontToBack()) {
            if (isBehind) {
                if (f.isVisible() && f.contains(p)) {
                    return f;
                }
            } else {
                isBehind = figure == f;
            }
        }
        return null;
    }

    public Figure findFigureBehind(Point2D.Double p, Collection<Figure> children) {
        int inFrontOf = children.size();
        for (Figure f : getFiguresFrontToBack()) {
            if (inFrontOf == 0) {
                if (f.isVisible() && f.contains(p)) {
                    return f;
                }
            } else {
                if (children.contains(f)) {
                    inFrontOf--;
                }
            }
        }
        return null;
    }

    public Figure findFigureExcept(Point2D.Double p, Collection<Figure> ignore) {
        for (Figure f : getFiguresFrontToBack()) {
            if (!ignore.contains(f) && f.isVisible() && f.contains(p)) {
                return f;
            }
        }
        return null;
    }

    public java.util.List<Figure> findFigures(Rectangle2D.Double bounds) {
        LinkedList<Figure> intersection = new LinkedList<Figure>();
        for (Figure f : getChildren()) {
            if (f.isVisible() && f.getBounds().intersects(bounds)) {
                intersection.add(f);
            }
        }
        return intersection;
    }

    public java.util.List<Figure> findFiguresWithin(Rectangle2D.Double bounds) {
        LinkedList<Figure> contained = new LinkedList<Figure>();
        for (Figure f : getChildren()) {
            Rectangle2D r = f.getBounds();
            if (AttributeKeys.TRANSFORM.get(f) != null) {
                r = AttributeKeys.TRANSFORM.get(f).createTransformedShape(r).getBounds2D();
            }
            if (f.isVisible() && bounds.contains(r)) {
                contained.add(f);
            }
        }
        return contained;
    }

    public Figure findFigureInside(Point2D.Double p) {
        Figure f = findFigure(p);
        return (f == null) ? null : f.findFigureInside(p);
    }

    /**
     * Returns an iterator to iterate in
     * Z-order front to back over the children.
     */
    public java.util.List<Figure> getFiguresFrontToBack() {
        ensureSorted();
        return new ReversedList<Figure>(getChildren());
    }

    public void bringToFront(Figure figure) {
        if (basicRemove(figure) != -1) {
            basicAdd(figure);
            invalidateSortOrder();
            fireAreaInvalidated(figure.getDrawingArea());
        }
    }

    public void sendToBack(Figure figure) {
        if (basicRemove(figure) != -1) {
            basicAdd(0, figure);
            invalidateSortOrder();
            fireAreaInvalidated(figure.getDrawingArea());
        }
    }

    /**
     * Invalidates the sort order.
     */
    private void invalidateSortOrder() {
        needsSorting = true;
    }

    /**
     * Ensures that the children are sorted in z-order sequence from back to
     * front.
     */
    private void ensureSorted() {
        if (needsSorting) {
            Collections.sort(children, FigureLayerComparator.INSTANCE);
            needsSorting = false;
        }
    }

    protected void setAttributeOnChildren(AttributeKey key, Object newValue) {
        // empty
    }

    public void setCanvasSize(Dimension2DDouble newValue) {
        Dimension2DDouble oldValue = new Dimension2DDouble(
                canvasBounds.width, canvasBounds.height);
        canvasBounds.width = newValue == null ? -1 : newValue.width;
        canvasBounds.height = newValue == null ? -1 : newValue.height;
        firePropertyChange("canvasSize", oldValue, newValue);
    }

    public Dimension2DDouble getCanvasSize() {
        return canvasBounds == null || canvasBounds.isEmpty() ? null : new Dimension2DDouble(
                canvasBounds.width, canvasBounds.height);
    }

    public int indexOf(Figure figure) {
        return children.indexOf(figure);
    }

    public CardwallDrawing clone() {
        CardwallDrawing that = (CardwallDrawing) super.clone();
        that.canvasBounds = (Rectangle2D.Double) this.canvasBounds.clone();
        return that;
    }

    @Override
    protected void drawFill(Graphics2D g) {
        //  throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        //  throw new UnsupportedOperationException("Not supported yet.");
    }

    public Card getCard(String cardnumber) {
        return repository.getCard(cardnumber);
    }

}
