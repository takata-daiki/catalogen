/**
 * Path description
 *
 * @author	Martin Swany
 * @author	Jason Zurawski
 * @version	$Id: Path.java 189 2007-01-31 20:46:21Z boote $  
 * @see		org.ggf.ns.nmwg.base.v2_0.Element
 * @see		org.ggf.ns.nmwg.topology.l4.v3_0.EndPoint
 * @see		org.w3c.dom.Document
 * @see		org.w3c.dom.Element  
 */
package org.ggf.ns.nmwg.topology.l4.v3_0;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.NamespaceSupport;

import org.ggf.ns.nmwg.base.v2_0.Element;
import org.ggf.ns.nmwg.topology.base.v3_0.Link;

public class Path extends org.ggf.ns.nmwg.topology.base.v3_0.Path {
	
	/**
	 * 'map' of endPoint elements
	 */	
	protected Map endPointMap;
	
	/**
 	 * Constructor.
	 */ 
	public Path() {
		endPointMap = new HashMap();
		linkMap = null;
		pathIdRef = "";			
			
		parent = null;
		id = "";		
		completed = false;			
				
		localName = "path";		
		uri = "http://ggf.org/ns/nmwg/topology/l4/3.0/";
		qName = "nmwgtopol4:path";	
		prefix = "nmwgtopol4";	
		fileName = "org/ggf/ns/nmwg/topology/l4/v3_0/Path.java";	
	}
		
	/**
 	 * Returns a pathIdRef.
	 */
	public String getPathIdRef() {
		return pathIdRef;
	}
	
	/**
 	 * Sets a pathIdRef.
	 */	
	public void setPathIdRef(String newPathIdRef) {
		pathIdRef = newPathIdRef;
	}	

/** 
 * Unused in this object
 */

	public void clearLink() {
		System.err.println("Error: " + getFileName() + " 'clearLink()' cannot be used in this context.");
	}

	public void setLink(Link newLink) {	 
		System.err.println("Error: " + getFileName() + " 'setLink(Link)' cannot be used in this context.");		
	}
	
	public Map getLinkMap() {
		System.err.println("Error: " + getFileName() + " 'getLinkMap()' cannot be used in this context.");	
		return null;
	}

	public Link[] getLinkArray() {
		System.err.println("Error: " + getFileName() + " 'getLinkArray()' cannot be used in this context.");
		return null;
	}

	public Iterator getLinkIterator() {
		System.err.println("Error: " + getFileName() + " 'getLinkIterator()' cannot be used in this context.");
		return null;
	}

	public Link getLink(String id) {
		System.err.println("Error: " + getFileName() + " 'getLink(String)' cannot be used in this context.");			
		return null;
	}

/** 
 * EndPoint Section
 */

	/**
 	 * clear all EndPoint elements
	 */	
	public void clearEndPoint() {
		endPointMap = new HashMap();
	}

	/**
 	 * Adds a endPoint to the map
	 */	
	public void setEndPoint(EndPoint newEndPoint) {	 
		if(newEndPoint != null) {	   
			if(!(newEndPoint.getId().equals(""))) {    
              			endPointMap.put(newEndPoint.getId(),newEndPoint);	    	
			}
			else {  		
				System.err.println("Error: " + getFileName() + " 'setEndPoint(EndPoint)' cannot have nil value for id.");
			}	 
		}
		else {
			System.err.println("Error: " + getFileName() + " 'setEndPoint(EndPoint)' cannot have nil value for EndPoint.");		
		}		  		
	}
	
	/**
 	 * Return the endPoint map
	 */	
	public Map getEndPointMap() {
		return this.endPointMap;
	}

	/**
 	 * Convert and return the endPoint block in array form
	 */
	public EndPoint[] getEndPointArray() {
		return (EndPoint [])endPointMap.values().toArray(new EndPoint[endPointMap.size()]);
	}

	/**
 	 * Get an iterartor to the endPoint map
	 */
	public Iterator getEndPointIterator() {
		return endPointMap.values().iterator();
	}

	/**
 	 * Get a single endPoint block by id from the endPoint block
	 */
	public EndPoint getEndPoint(String id) {
		if(!(id.equals(""))) {
			EndPoint l = (EndPoint) endPointMap.get(id);
			if(l == null) {
				System.err.println("Error: " + getFileName() + " 'getEndPoint(String)' has returned null for id: " + id);		
			}
			return l;
		}
		else {
			System.err.println("Error: " + getFileName() + " 'getEndPoint(String)' id cannont be nil.");				
			return null;
		}
	}

	/**
 	 * Add only children related to the endPoint.
	 */ 		
	public boolean addChild(Element newChild) {
		if(newChild.getLocalName().equals("endPoint")) {		
			setEndPoint((EndPoint)newChild);
		}				
		else {					 	
		 	System.err.println("Error: " + getFileName() + " addChild(Element) unrecognized child: " + newChild);
		   	return false;		
		}				
		return true;
	}	

	/**
	 * Chases children references down to pring out all related xml tags.
	 */
	public ContentHandler getChildren(ContentHandler handler, NamespaceSupport nss) throws Exception {
                EndPoint tl;
                Collection lc = getEndPointMap().values();
		
                for(Iterator i = lc.iterator(); i.hasNext(); ){
                    tl = (EndPoint)i.next();
                    handler = tl.toXML(handler, nss);
                }
		return handler;
	}	

	/** 
	 * Given a DOM element (a parent), construct
	 * the children elements.
	 */	
	public void getDOM(org.w3c.dom.Element parent) {		
		EndPoint tl;		
		Collection lc = getEndPointMap().values();
	
        	org.w3c.dom.Document doc = parent.getOwnerDocument();
        	org.w3c.dom.Element path = doc.createElementNS(getUri(), getQName()); 
		parent.appendChild(path);

		if(!(getId().equals(""))) {
			path.setAttribute("id", getId());	
		}
		if(!(getPathIdRef().equals(""))) {
			path.setAttribute("pathIdRef", getPathIdRef());	
		}

		for(Iterator i = lc.iterator(); i.hasNext(); ){
                	tl = (EndPoint)i.next();
			tl.getDOM(path);		
		}		
				
	}

	/**
 	 * Called when the object is placed in
	 * an output statement.
	 */ 							
	public String toString() {
		EndPoint tl;		
		Collection lc = getEndPointMap().values();
		String ls = "";
		
		for(Iterator i = lc.iterator(); i.hasNext(); ){
                	tl = (EndPoint)i.next();
			ls = ls + tl.toString();		
		}	
		return getFileName() + ": ---> " + getId() + getPathIdRef() + " endPoint:" + ls;
	}	

}
