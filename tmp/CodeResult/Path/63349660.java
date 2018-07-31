/**
 * Path description
 *
 * @author	Martin Swany
 * @author	Jason Zurawski
 * @version	$Id: Path.java 189 2007-01-31 20:46:21Z boote $  
 * @see		org.ggf.ns.nmwg.base.v2_0.Element
 * @see		org.ggf.ns.nmwg.topology.base.v3_0.Link 
 * @see		org.w3c.dom.Document
 * @see		org.w3c.dom.Element  
 */
package org.ggf.ns.nmwg.topology.base.v3_0;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.NamespaceSupport;

import org.ggf.ns.nmwg.base.v2_0.Element;

public class Path extends Element {
	
	/**
	 * 'map' of link elements
	 */	
	protected Map linkMap;
	
	/**
	 * attribute for pathIdRef
	 */
	protected String pathIdRef;

	/**
 	 * Constructor.
	 */ 
	public Path() {
		linkMap = new HashMap();
				
		pathIdRef = "";			
			
		parent = null;
		id = "";		
		completed = false;			
				
		localName = "path";		
		uri = "http://ggf.org/ns/nmwg/topology/base/3.0/";
		qName = "nmwgtopo3:path";	
		prefix = "nmwgtopo3";	
		fileName = "org/ggf/ns/nmwg/topology/base/v3_0/Path.java";	
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
 * Link Section
 */

	/**
 	 * clear all Link elements
	 */	
	public void clearLink() {
		linkMap = new HashMap();
	}

	/**
 	 * Adds a link to the map
	 */	
	public void setLink(Link newLink) {	 
		if(newLink != null) {	   
			if(!(newLink.getId().equals(""))) {    
              			linkMap.put(newLink.getId(),newLink);	    	
			}
			else {  		
				System.err.println("Error: " + getFileName() + " 'setLink(Link)' cannot have nil value for id.");
			}	 
		}
		else {
			System.err.println("Error: " + getFileName() + " 'setLink(Link)' cannot have nil value for Link.");		
		}		  		
	}
	
	/**
 	 * Return the link map
	 */	
	public Map getLinkMap() {
		return this.linkMap;
	}

	/**
 	 * Convert and return the link block in array form
	 */
	public Link[] getLinkArray() {
		return (Link [])linkMap.values().toArray(new Link[linkMap.size()]);
	}

	/**
 	 * Get an iterartor to the link map
	 */
	public Iterator getLinkIterator() {
		return linkMap.values().iterator();
	}

	/**
 	 * Get a single link block by id from the link block
	 */
	public Link getLink(String id) {
		if(!(id.equals(""))) {
			Link l = (Link) linkMap.get(id);
			if(l == null) {
				System.err.println("Error: " + getFileName() + " 'getLink(String)' has returned null for id: " + id);		
			}
			return l;
		}
		else {
			System.err.println("Error: " + getFileName() + " 'getLink(String)' id cannont be nil.");				
			return null;
		}
	}

	/**
 	 * Add only children related to the link.
	 */ 		
	public boolean addChild(Element newChild) {
		if(newChild.getLocalName().equals("link")) {		
			setLink((Link)newChild);
		}				
		else {					 	
		 	System.err.println("Error: " + getFileName() + " addChild(Element) unrecognized child: " + newChild);
		   	return false;		
		}				
		return true;
	}	

	/**
 	 * There shouldnt be an attribute, but there could be a namespace?
	 */ 
	public boolean addAttr(String attr, String value, NamespaceSupport nss) {
		if(attr.indexOf("xmlns") >= 0) {	
			if(attr.split(":").length == 1) {
				if(nss.getURI("") == null) {
					nss.declarePrefix("", value);
				}
				if((prefix.equals("")) && !(value.equals(uri))) {
					uri = value;
				}
			}
			else {
				if(nss.getURI(attr.split(":")[1]) == null) {
					nss.declarePrefix(attr.split(":")[1], value);
				}			
				if((prefix.equals(attr.split(":")[1])) && !(value.equals(uri))) {
					uri = value;
				}
			}						
		}	
		else if(attr.equals("id")) {	
			setId(value);
		}	
		else if(attr.equals("pathIdRef")) {	
			setPathIdRef(value);
		}			
		else {
		 	System.err.println("Error: " + getFileName() + " addAttr(String, String, NamespaceSupport) unrecognized attribute pair: " + attr + " - " + value);
		   	return false;
		}
		return true;
	}

	/**
 	 * Converts the contents of this object into an xml tag.
	 */	
	public ContentHandler toXML(ContentHandler handler, NamespaceSupport nss) throws Exception {
		try {
  			handler.startElement(getUri(), getLocalName(), getQName(), getAttributes(nss));
			handler = getChildren(handler, nss);
  			handler.endElement(getUri(), getLocalName(), getQName());
			
			if(getCompleted()) {
				nss.popContext();
				unSetCompleted();
			}				
		}
		catch(Exception e) {
		 	System.err.println("Error: " + getFileName() + " toXML(ContentHandler, NamespaceSupport) XML formulation error.");
		}	
		return handler;
	}

	/**
	 * Converts items into attributes for an xml tag.
	 */	
	public AttributesImpl getAttributes(NamespaceSupport nss) {
		AttributesImpl atts = new AttributesImpl();
		
		if(nss.getURI(getPrefix()) == null) {		
			nss.pushContext();		
			if(getPrefix().equals("")) {
				atts.addAttribute(getUri(), "xmlns", "xmlns", "CDATA", getUri());			
			}
			else {
				atts.addAttribute(getUri(), "xmlns:" + getPrefix(), "xmlns:" + getPrefix(), "CDATA", getUri());			
			}
			nss.declarePrefix(getPrefix(), getUri());			
			setCompleted();
		}
		if(!(getId().equals(""))) {
			atts.addAttribute(getUri(), "id", "id", "CDATA", getId());
		} 
		if(!(getPathIdRef().equals(""))) {
			atts.addAttribute(getUri(), "pathIdRef", "pathIdRef", "CDATA", getPathIdRef());
		} 

		return atts;
	}

	/**
	 * Chases children references down to pring out all related xml tags.
	 */
	public ContentHandler getChildren(ContentHandler handler, NamespaceSupport nss) throws Exception {
                Link tl;
                Collection lc = getLinkMap().values();
		
                for(Iterator i = lc.iterator(); i.hasNext(); ){
                    tl = (Link)i.next();
                    handler = tl.toXML(handler, nss);
                }
		return handler;
	}	

	/** 
	 * Given a DOM element (a parent), construct
	 * the children elements.
	 */	
	public void getDOM(org.w3c.dom.Element parent) {		
		Link tl;		
		Collection lc = getLinkMap().values();
	
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
                	tl = (Link)i.next();
			tl.getDOM(path);		
		}		
				
	}

	/**
 	 * Called when the object is placed in
	 * an output statement.
	 */ 							
	public String toString() {
		Link tl;		
		Collection lc = getLinkMap().values();
		String ls = "";
		
		for(Iterator i = lc.iterator(); i.hasNext(); ){
                	tl = (Link)i.next();
			ls = ls + tl.toString();		
		}	
		return getFileName() + ": ---> " + getId() + getPathIdRef() + " link:" + ls;
	}	

}
