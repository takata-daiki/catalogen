/*
 * Entry.java
 *
 * Created on July 16, 2007, 12:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.atomojo.app.client;

import java.net.URI;
import org.infoset.xml.Document;
import org.infoset.xml.Element;
import org.infoset.xml.InfosetFactory;
import org.infoset.xml.XMLException;
import org.restlet.data.MediaType;

/**
 *
 * @author alex
 */
public class Entry extends CategorizedXMLEntity
{
   public class Media {
      Element content;
      Media(Element content) {
         this.content = content;
      }
      
      public String getName() {
         return content.getAttributeValue("src");
      }
      
      public URI getLocation() {
         return content.getBaseURI().resolve(getName());
      }
      
      public MediaType getMediaType() {
         String type = content.getAttributeValue("type");
         return type!=null ? MediaType.valueOf(type) : null;
      }
      
   }
   /** Creates a new instance of Entry */
   public Entry(Document doc)
   {
      super(doc);
   }
   
   /** Creates a new instance of Entry */
   public Entry()
   {
      super(null);
      try {
         doc = InfosetFactory.getDefaultInfoset().createItemConstructor().createDocument();
         doc.createDocumentElement(XML.ENTRY_NAME);
      } catch (XMLException ex) {
         // THis should only happen if the infoset is misconfigured
         throw new RuntimeException("Cannot create entry document.",ex);
      }
      index();
   }
   
   public Media getMediaContent() {
      Element contentE = doc.getDocumentElement().getFirstElementNamed(XML.CONTENT_NAME);
      return contentE!=null && contentE.getAttributeValue("src")!=null ? new Media(contentE) : null;
   }
   
   public Text getContent() {
      return getContent(false);
   }
   
   public Text getContent(boolean create)
   {
      Element contentE = doc.getDocumentElement().getFirstElementNamed(XML.CONTENT_NAME);
      if (contentE==null && create) {
         contentE = doc.getDocumentElement().addElement(XML.CONTENT_NAME);
      }
      return contentE!=null ? new Text(contentE) : null;
   }
   
}
