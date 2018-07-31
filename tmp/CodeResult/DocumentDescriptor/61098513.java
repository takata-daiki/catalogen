/****************************************************************************
 * ubion.ORS - The Open Report Suite                                        *
 *                                                                          *
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * Subproject: NOA (Nice Office Access)                                     *
 *                                                                          *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2005 by IOn AG                                            *
 *                                                                          *
 * This library is free software; you can redistribute it and/or            *
 * modify it under the terms of the GNU Lesser General Public               *
 * License version 2.1, as published by the Free Software Foundation.       *
 *                                                                          *
 * This library is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of           *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU        *
 * Lesser General Public License for more details.                          *
 *                                                                          *
 * You should have received a copy of the GNU Lesser General Public         *
 * License along with this library; if not, write to the Free Software      *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,                    *
 * MA  02111-1307  USA                                                      *
 *                                                                          *
 * Contact us:                                                              *
 *  http://www.ion.ag                                                       *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.bion.officelayer.document;

import ag.ion.bion.officelayer.IDisposeable;

import ag.ion.bion.officelayer.desktop.IFrame;

import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;
import ag.ion.noa.NOAException;

import java.io.InputStream;

/**
 * Service for documents.
 * 
 * @author Andreas Brรถcker
 * @version $Revision: 10398 $
 */
public interface IDocumentService extends IDisposeable {
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new document.
   * 
   * <br><br>
   * Use one of the types:
   * <br><br>
   * <code>IDocument.WRITER</code><br>
   * <code>IDocument.CALC</code><br>
   * <code>IDocument.IMPRESS</code><br>
   * <code>IDocument.DRAW</code><br>
   * <code>IDocument.MATH</code><br>
   * <code>IDocument.WEB</code><br>
   * <code>IDocument.BASE</code><br>
   * <code>IDocument.GLOBAL</code>
   * 
   * @param documentType document type to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return new constructed document
   * 
   * @throws NOAException if the new document can not be constructed
   * 
   * @author Andreas Brรถcker
   */
  public IDocument constructNewDocument(String documentType, IDocumentDescriptor documentDescriptor) throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Constructs new document in the submitted frame.
   * 
   * <br><br>
   * Use one of the types:
   * <br><br>
   * <code>IDocument.WRITER</code><br>
   * <code>IDocument.CALC</code><br>
   * <code>IDocument.IMPRESS</code><br>
   * <code>IDocument.DRAW</code><br>
   * <code>IDocument.MATH</code><br>
   * <code>IDocument.WEB</code><br>
   * <code>IDocument.BASE</code><br>
   * <code>IDocument.GLOBAL</code>
   * 
   * @param frame frame to be used
   * @param documentType document type to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return new constructed document
   * 
   * @throws NOAException if the new document can not be constructed
   * 
   * @author Andreas Brรถcker
   */
  public IDocument constructNewDocument(IFrame frame, String documentType, IDocumentDescriptor documentDescriptor) throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Constructs new hidden document of the submitted type.
   * <br><br>
   * Use one of the types:
   * <br><br>
   * <code>IDocument.WRITER</code><br>
   * <code>IDocument.CALC</code><br>
   * <code>IDocument.IMPRESS</code><br>
   * <code>IDocument.DRAW</code><br>
   * <code>IDocument.MATH</code><br>
   * <code>IDocument.WEB</code><br>
   * <code>IDocument.BASE</code><br>
   * <code>IDocument.GLOBAL</code>
   * 
   * @param documentType document type to be constructed
   * 
   * @return new constructed document of the submitted type
   * 
   * @throws NOAException if the document can not be contructed
   * 
   * @author Andreas Brรถcker
   * @date 16.03.2006
   */
  public IDocument constructNewHiddenDocument(String documentType) throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted URL.
   * 
   * @param url URL of the document 
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded or the URL does
   * not locate an OpenOffice.org document
   * 
   * @author Andreas Brรถcker
   */
  public IDocument loadDocument(String url) throws DocumentException;  
  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted URL.
   * 
   * @param url URL of the document 
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   *  
   * @throws NOAException if the document can not be loaded or the URL does
   * not locate an OpenOffice.org document
   * 
   * @author Andreas Brรถcker
   * @date 02.07.2006
   */
  public IDocument loadDocument(String url, IDocumentDescriptor documentDescriptor) throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted stream.
   * 
   * @param inputStream input stream to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded
   * 
   * @author Andreas Brรถcker
   */
  public IDocument loadDocument(InputStream inputStream, IDocumentDescriptor documentDescriptor) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted stream.
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * @param inputStream input stream to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded
   * 
   * @author Andreas Brรถcker
   */
  public IDocument loadDocument(IOfficeProgressMonitor officeProgressMonitor, 
      InputStream inputStream, IDocumentDescriptor documentDescriptor) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted stream. 
   * 
   * <b>The document has no location and can not be stored with
   * the store() method of the <code>IPersistenceService</code>. Furthermore
   * OpenOffice.org can not recognize if the document is already open - 
   * therefore the document will be never opened in <code>ReadOnly</code> mode.</b>
   * 
   * @param frame frame to be used for the document
   * @param inputStream input stream to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded
   * 
   * @author Andreas Brรถcker
   */
  public IDocument loadDocument(IFrame frame, InputStream inputStream, 
      IDocumentDescriptor documentDescriptor) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted stream.
   * 
   * <b>The document has no location and can not be stored with
   * the store() method of the <code>IPersistenceService</code>. Furthermore
   * OpenOffice.org can not recognize if the document is already open - 
   * therefore the document will be never opened in <code>ReadOnly</code> mode.</b>
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * @param frame frame to be used for the document
   * @param inputStream input stream to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded
   * 
   * @author Andreas Brรถcker
   */
  public IDocument loadDocument(IOfficeProgressMonitor officeProgressMonitor, IFrame frame, 
      InputStream inputStream, IDocumentDescriptor documentDescriptor) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Loads document into the submitted frame.
   *  
   * @param frame frame to be used for the document
   * @param url URL of the document (must start with file:///)
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded, the URL does
   * not locate an OpenOffice.org document or the submitted frame is not valid
   * 
   * @author Andreas Brรถcker
   */
  public IDocument loadDocument(IFrame frame, String url) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Loads document into the submitted frame.
   * 
   * @param frame frame to be used for the document
   * @param url URL of the document (must start with file:///)
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded, the URL does
   * not locate an OpenOffice.org document or the submitted frame is not valid
   * 
   * @author Andreas Brรถcker
   */
  public IDocument loadDocument(IFrame frame, String url, IDocumentDescriptor documentDescriptor) throws DocumentException;  
  //----------------------------------------------------------------------------
  /**
   * Returns current documents of an application.
   * 
   * @return documents of an application
   * 
   * @throws DocumentException if the documents cannot be provided
   * 
   * @author Markus Krรผger
   */
  public IDocument[] getCurrentDocuments() throws DocumentException;
  //----------------------------------------------------------------------------

}