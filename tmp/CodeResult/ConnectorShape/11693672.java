package org.epistem.diagram.model;

import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.epistem.graffle.OGGraphic;

/**
 * Base graphic
 *
 * @author nickmain
 */
public abstract class Graphic {

    public final Map<String,Object> userData = new HashMap<String, Object>();
    public final Metadata metadata;
    public final boolean  isSolid;
    public final Page     page;
    public final Collection<Connector> incoming = new HashSet<Connector>();
    public final Collection<Connector> outgoing = new HashSet<Connector>();
    public GraphicContainer parent;
    public final double x, y;
    
    protected OGGraphic ogg;
    
    abstract void init();
    
    public abstract void accept( DiagramVisitor visitor );
    
    Graphic( OGGraphic ogg, GraphicContainer parent, Page page ) {

        this.ogg = ogg;
        this.page = page;
        this.parent = parent;
        metadata = new Metadata( ogg.notes(), ogg.userProperties() );
        isSolid = ogg.strokePattern() == 0;
        
        page.graphics.put( ogg.id(), this );
        
        Rectangle2D bounds = ogg.bounds();
        x = bounds.getCenterX();
        y = bounds.getCenterY();
    }
    
    static Graphic make( OGGraphic ogg, GraphicContainer parent, Page page ) {
        switch( ogg.graphicClass() ) {
            case Group:        return new Group( ogg, parent, page );
            case TableGroup:   return new Table( ogg, parent, page );
            case LineGraphic:  return new Line ( ogg, parent, page );
            case ShapedGraphic:     
                if( ogg.tailId() != 0 || ogg.headId() != 0 ) return new ConnectorShape( ogg, parent, page );
                return new Shape( ogg, parent, page );
                
            default: throw new RuntimeException( "UNREACHABLE CODE" );
        }
    }
    
    @Override
    abstract public String toString();
}
