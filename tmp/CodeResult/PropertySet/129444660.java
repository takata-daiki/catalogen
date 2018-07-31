package org.jwall.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


/**
 * 
 * 
 * @author Christian Bockermann &lt;chris@jwall.org&gt;
 *
 */
@XStreamAlias("Properties")
public class PropertySet {

	/** The category to which this set of properties belongs */
	@XStreamAsAttribute
	private String category;
	
	/** The ordered list of property-names */
	@XStreamImplicit
	private LinkedList<Property<String>> props = new LinkedList<Property<String>>();
	

	/**
	 * 
	 * Creates a new set of properties for the given category.
	 * 
	 * @param cat The category to which this set of properties belongs.
	 * 
	 */
	public PropertySet( String cat ){
		props = new LinkedList<Property<String>>();
		category = cat;
	}
	
	
	public List<String> getPropertyNames(){
		LinkedList<String> l = new LinkedList<String>();
		for( Property<String> p : props )
			l.add( p.getName() );
		
		return l;
	}
	
	public String getProperty( String key ){

		for( Property<String> p : props )
			if( p.getName().equals( key ) )
				return p.getValue();
		
		return null;
	}
	
	public void setProperty( String key, String val ){

		for( Property<String> p : props ){
			if( p.getName().equals( key ) ){
				p.setValue( val );
				return;
			}
		}
		
		props.add( new Property<String>( key, val ) );
	}
	
	public void addProperty( Property<String> p ){
	    if(props.contains( p ) )
	        props.remove( p );
	        
	    props.add( p );
	}
	
	public void setCategory( String cat ){
		category = cat;
	}
	
	public String getCategory(){
		return category;
	}
	
	
	public static XStream getXStream(){
		XStream xstream = new XStream();
		xstream.processAnnotations( Property.class );
		xstream.processAnnotations( PropertySet.class );
		return xstream;
	}
	
	public void save( OutputStream out ){
		PropertySet.getXStream().toXML( this , out );
	}
	
	public PropertySet load( InputStream in ){
		return (PropertySet) PropertySet.getXStream().fromXML( in );
	}
}