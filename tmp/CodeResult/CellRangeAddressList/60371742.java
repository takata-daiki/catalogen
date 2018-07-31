/************************************************************************
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * Copyright 2008, 2010 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2009, 2010 IBM. All rights reserved.
 * 
 * Use is subject to license terms.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. You can also
 * obtain a copy of the License at http://odftoolkit.org/docs/license.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ************************************************************************/
package org.odftoolkit.simple;

import java.awt.Rectangle;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.attribute.text.TextAnchorTypeAttribute;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeDocumentStylesElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeMasterStylesElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.odftoolkit.odfdom.dom.element.style.StyleFooterElement;
import org.odftoolkit.odfdom.dom.element.style.StyleHeaderElement;
import org.odftoolkit.odfdom.dom.element.style.StyleMasterPageElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.dom.element.text.TextSectionElement;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStylePageLayout;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.pkg.MediaType;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.odfdom.type.CellRangeAddressList;
import org.odftoolkit.simple.chart.AbstractChartContainer;
import org.odftoolkit.simple.chart.Chart;
import org.odftoolkit.simple.chart.ChartContainer;
import org.odftoolkit.simple.chart.DataSet;
import org.odftoolkit.simple.common.field.AbstractVariableContainer;
import org.odftoolkit.simple.common.field.VariableContainer;
import org.odftoolkit.simple.common.field.VariableField;
import org.odftoolkit.simple.common.field.VariableField.VariableType;
import org.odftoolkit.simple.text.AbstractParagraphContainer;
import org.odftoolkit.simple.text.Footer;
import org.odftoolkit.simple.text.Header;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.ParagraphContainer;
import org.odftoolkit.simple.text.Section;
import org.odftoolkit.simple.text.list.AbstractListContainer;
import org.odftoolkit.simple.text.list.List;
import org.odftoolkit.simple.text.list.ListContainer;
import org.odftoolkit.simple.text.list.ListDecorator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class represents an empty ODF text document.
 * 
 */
public class TextDocument extends Document implements ListContainer, ParagraphContainer, VariableContainer, ChartContainer {

	private static final String EMPTY_TEXT_DOCUMENT_PATH = "/OdfTextDocument.odt";
	static final Resource EMPTY_TEXT_DOCUMENT_RESOURCE = new Resource(EMPTY_TEXT_DOCUMENT_PATH);

	private ListContainerImpl listContainerImpl;
	private ParagraphContainerImpl paragraphContainerImpl;
	private VariableContainerImpl variableContainerImpl;
	private ChartContainerImpl chartContainerImpl;
	
	private Header firstPageHeader;
	private Header standardHeader;

	private Footer firstPageFooter;
	private Footer standardFooter;

	/**
	 * This enum contains all possible media types of SpreadsheetDocument
	 * documents.
	 */
	public enum OdfMediaType implements MediaType {

		TEXT(Document.OdfMediaType.TEXT), TEXT_TEMPLATE(Document.OdfMediaType.TEXT_TEMPLATE), TEXT_MASTER(
				Document.OdfMediaType.TEXT_MASTER), TEXT_WEB(Document.OdfMediaType.TEXT_WEB);
		private final Document.OdfMediaType mMediaType;

		OdfMediaType(Document.OdfMediaType mediaType) {
			this.mMediaType = mediaType;
		}

		/**
		 * @return the mediatype of this document
		 */
		public String getMediaTypeString() {
			return mMediaType.getMediaTypeString();
		}

		/**
		 * @return the ODF filesuffix of this document
		 */
		public String getSuffix() {
			return mMediaType.getSuffix();
		}

		/**
		 * 
		 * @param mediaType
		 *            string defining an ODF document
		 * @return the according OdfMediatype encapuslating the given string and
		 *         the suffix
		 */
		public static Document.OdfMediaType getOdfMediaType(String mediaType) {
			return Document.OdfMediaType.getOdfMediaType(mediaType);
		}
	}

	/**
	 * Creates an empty text document.
	 * 
	 * @return ODF text document based on a default template
	 * @throws java.lang.Exception
	 *             - if the document could not be created
	 */
	public static TextDocument newTextDocument() throws Exception {
		return (TextDocument) Document.loadTemplate(EMPTY_TEXT_DOCUMENT_RESOURCE, Document.OdfMediaType.TEXT);
	}

	/**
	 * Creates an empty text document.
	 * 
	 * @return ODF text document based on a default template
	 * @throws java.lang.Exception
	 *             - if the document could not be created
	 */
	public static TextDocument newTextDocument(TextDocument.OdfMediaType mimeType) throws Exception {
		return (TextDocument) Document.loadTemplate(EMPTY_TEXT_DOCUMENT_RESOURCE, Document.OdfMediaType.TEXT);
	}

	/**
	 * Creates an empty text template.
	 * 
	 * @return ODF text template based on a default
	 * @throws java.lang.Exception
	 *             - if the template could not be created
	 */
	public static TextDocument newTextTemplateDocument() throws Exception {
		return (TextDocument) Document.loadTemplate(EMPTY_TEXT_DOCUMENT_RESOURCE, Document.OdfMediaType.TEXT_TEMPLATE);
	}

	/**
	 * Creates an empty text master document.
	 * 
	 * @return ODF text master based on a default
	 * @throws java.lang.Exception
	 *             - if the document could not be created
	 */
	public static TextDocument newTextMasterDocument() throws Exception {
		TextDocument doc = (TextDocument) Document.loadTemplate(EMPTY_TEXT_DOCUMENT_RESOURCE,
				Document.OdfMediaType.TEXT_MASTER);
		doc.changeMode(OdfMediaType.TEXT_MASTER);
		return doc;
	}

	/**
	 * Creates an empty text web.
	 * 
	 * @return ODF text web based on a default
	 * @throws java.lang.Exception
	 *             - if the document could not be created
	 */
	public static TextDocument newTextWebDocument() throws Exception {
		TextDocument doc = (TextDocument) Document.loadTemplate(EMPTY_TEXT_DOCUMENT_RESOURCE,
				Document.OdfMediaType.TEXT_WEB);
		doc.changeMode(OdfMediaType.TEXT_WEB);
		return doc;
	}

	/**
	 * Creates an TextDocument from the OpenDocument provided by a resource
	 * Stream.
	 * 
	 * <p>
	 * Since an InputStream does not provide the arbitrary (non sequentiell)
	 * read access needed by TextDocument, the InputStream is cached. This
	 * usually takes more time compared to the other createInternalDocument
	 * methods. An advantage of caching is that there are no problems
	 * overwriting an input file.
	 * </p>
	 * 
	 * <p>
	 * If the resource stream is not a ODF text document, ClassCastException
	 * might be thrown.
	 * </p>
	 * 
	 * @param inputStream
	 *            - the InputStream of the ODF text document.
	 * @return the text document created from the given InputStream
	 * @throws java.lang.Exception
	 *             - if the document could not be created.
	 */
	public static TextDocument loadDocument(InputStream inputStream) throws Exception {
		return (TextDocument) Document.loadDocument(inputStream);
	}

	/**
	 * Loads an TextDocument from the provided path.
	 * 
	 * <p>
	 * TextDocument relies on the file being available for read access over the
	 * whole lifecycle of TextDocument.
	 * </p>
	 * 
	 * <p>
	 * If the resource stream is not a ODF text document, ClassCastException
	 * might be thrown.
	 * </p>
	 * 
	 * @param documentPath
	 *            - the path from where the document can be loaded
	 * @return the text document from the given path or NULL if the media type
	 *         is not supported by SIMPLE.
	 * @throws java.lang.Exception
	 *             - if the document could not be created.
	 */
	public static TextDocument loadDocument(String documentPath) throws Exception {
		return (TextDocument) Document.loadDocument(documentPath);
	}

	/**
	 * Creates an TextDocument from the OpenDocument provided by a File.
	 * 
	 * <p>
	 * TextDocument relies on the file being available for read access over the
	 * whole lifecycle of TextDocument.
	 * </p>
	 * 
	 * <p>
	 * If the resource stream is not a ODF text document, ClassCastException
	 * might be thrown.
	 * </p>
	 * 
	 * @param file
	 *            - a file representing the ODF text document.
	 * @return the text document created from the given File
	 * @throws java.lang.Exception
	 *             - if the document could not be created.
	 */
	public static TextDocument loadDocument(File file) throws Exception {
		return (TextDocument) Document.loadDocument(file);
	}

	/**
	 * To avoid data duplication a new document is only created, if not already
	 * opened. A document is cached by this constructor using the internalpath
	 * as key.
	 */
	protected TextDocument(OdfPackage pkg, String internalPath, TextDocument.OdfMediaType odfMediaType) {
		super(pkg, internalPath, odfMediaType.mMediaType);
	}

	/**
	 * Get the content root of a text document. Start here to get or create new
	 * elements of a text document like paragraphs, headings, tables or lists.
	 * 
	 * @return content root, representing the office:text tag
	 * @throws Exception
	 *             if the file DOM could not be created.
	 */
	@Override
	public OfficeTextElement getContentRoot() throws Exception {
		return super.getContentRoot(OfficeTextElement.class);
	}

	/**
	 * Creates a new paragraph and append text
	 * 
	 * @param text
	 * @return the new paragraph
	 * @throws Exception
	 *             if the file DOM could not be created.
	 * @deprecated As of Simple version 0.5, replaced by
	 *             <code>addParagraph(String text)</code>
	 * @see #addParagraph(String)
	 */
	public OdfTextParagraph newParagraph(String text) throws Exception {
		OdfTextParagraph para = newParagraph();
		para.addContent(text);
		return para;
	}

	/**
	 * Creates a new paragraph
	 * 
	 * @return The new paragraph
	 * @throws Exception
	 *             if the file DOM could not be created.
	 * @deprecated As of Simple version 0.5, replaced by
	 *             <code>Paragraph.newParagraph(ParagraphContainer)</code>
	 * @see Paragraph#newParagraph(ParagraphContainer)
	 * @see #addParagraph(String)
	 */
	public OdfTextParagraph newParagraph() throws Exception {
		OfficeTextElement odfText = getContentRoot();
		return (OdfTextParagraph) odfText.newTextPElement();
	}

	/**
	 * Append text to the end of a text document. If there is no paragraph at
	 * the end of a document, a new one will be created.
	 * 
	 * @param text
	 *            initial text for the paragraph.
	 * @return The paragraph at the end of the text document, where the text has
	 *         been added to.
	 * @throws Exception
	 *             if the file DOM could not be created.
	 * @deprecated As of Simple version 0.5, replaced by
	 *             <code>Paragraph.appendTextContent(String content)</code>
	 * @see Paragraph#appendTextContent(String)
	 * @see Paragraph#appendTextContentNotCollapsed(String)
	 * @see #getParagraphByIndex(int, boolean)
	 * @see #getParagraphByReverseIndex(int, boolean)
	 */
	public OdfTextParagraph addText(String text) throws Exception {
		OfficeTextElement odfText = getContentRoot();
		Node n = odfText.getLastChild();
		OdfTextParagraph para;
		if (OdfTextParagraph.class.isInstance(n)) {
			para = (OdfTextParagraph) n;
		} else {
			para = newParagraph();
		}
		para.addContent(text);
		return para;
	}

	/**
	 * Changes the document to the given mediatype. This method can only be used
	 * to convert a document to a related mediatype, e.g. template.
	 * 
	 * @param mediaType
	 *            the related ODF mimetype
	 */
	public void changeMode(OdfMediaType mediaType) {
		setOdfMediaType(mediaType.mMediaType);
	}

	/**
	 * Copy a section and append it at the end of the text document, whether the
	 * section is in this document or in a different document.
	 * <p>
	 * The IDs and names in this section would be changed to ensure unique.
	 * <p>
	 * If the section contains a linked resource, <code>isResourceCopied</code>
	 * would specify whether the linked resource would be copied or not, when
	 * the copy and append happens within a same document.
	 * 
	 * @param section
	 *            - the section object
	 * @param isResourceCopied
	 *            - whether the linked resource is copied or not.
	 */
	public Section appendSection(Section section, boolean isResourceCopied) {
		boolean isForeignNode = false;
		try {
			if (section.getOdfElement().getOwnerDocument() != getContentDom())
				isForeignNode = true;
			TextSectionElement oldSectionEle = section.getOdfElement();
			TextSectionElement newSectionEle = (TextSectionElement) oldSectionEle.cloneNode(true);

			if (isResourceCopied || isForeignNode)
				copyLinkedRefInBatch(newSectionEle, section.getOwnerDocument());
			if (isForeignNode)
				copyForeignStyleRef(newSectionEle, section.getOwnerDocument());
			if (isForeignNode) // not in a same document
				newSectionEle = (TextSectionElement) cloneForeignElement(newSectionEle, getContentDom(), true);

			updateNames(newSectionEle);
			updateXMLIds(newSectionEle);
			OfficeTextElement contentRoot = getContentRoot();
			contentRoot.appendChild(newSectionEle);
			return Section.getInstance(newSectionEle);
		} catch (Exception e) {
			Logger.getLogger(TextDocument.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}

	/**
	 * Get the Standard Page header of this text document.
	 * 
	 * @return the Standard Page header of this text document.
	 * @since 0.4.5
	 */
	public Header getHeader() {
		return getHeader(false);
	}

	/**
	 * Get the header of this text document.
	 * 
	 * @param isFirstPage
	 *            if <code>isFirstPage</code> is true, return the First Page
	 *            header, otherwise return Standard Page header.
	 * 
	 * @return the header of this text document.
	 * @since 0.5
	 */
	public Header getHeader(boolean isFirstPage) {
		Header tmpHeader = isFirstPage ? firstPageHeader : standardHeader;
		if (tmpHeader == null) {
			try {
				StyleMasterPageElement masterPageElement = getMasterPage(isFirstPage);
				StyleHeaderElement headerElement = OdfElement.findFirstChildNode(StyleHeaderElement.class,
						masterPageElement);
				if (headerElement == null) {
					headerElement = masterPageElement.newStyleHeaderElement();
				}
				tmpHeader = new Header(headerElement);
			} catch (Exception e) {
				Logger.getLogger(TextDocument.class.getName()).log(Level.SEVERE, null, e);
			}
			if (isFirstPage) {
				firstPageHeader = tmpHeader;
			} else {
				standardHeader = tmpHeader;
			}
		}
		return tmpHeader;
	}

	/**
	 * Get the Standard Page footer of this text document.
	 * 
	 * @return the Standard Page footer of this text document.
	 * @since 0.4.5
	 */
	public Footer getFooter() {
		return getFooter(false);
	}

	/**
	 * Get the footer of this text document.
	 * 
	 * @param isFirstPage
	 *            if <code>isFirstPage</code> is true, return the First Page
	 *            footer, otherwise return Standard Page footer.
	 * 
	 * @return the footer of this text document.
	 * @since 0.5
	 */
	public Footer getFooter(boolean isFirstPage) {
		Footer tmpFooter = isFirstPage ? firstPageFooter : standardFooter;
		if (tmpFooter == null) {
			try {
				StyleMasterPageElement masterPageElement = getMasterPage(isFirstPage);
				StyleFooterElement footerElement = OdfElement.findFirstChildNode(StyleFooterElement.class,
						masterPageElement);
				if (footerElement == null) {
					footerElement = masterPageElement.newStyleFooterElement();
				}
				tmpFooter = new Footer(footerElement);
			} catch (Exception e) {
				Logger.getLogger(TextDocument.class.getName()).log(Level.SEVERE, null, e);
			}
			if (isFirstPage) {
				firstPageFooter = tmpFooter;
			} else {
				standardFooter = tmpFooter;
			}
		}
		return tmpFooter;
	}

	public OdfElement getTableContainerElement() {
		return getTableContainerImpl().getTableContainerElement();
	}

	public OdfElement getListContainerElement() {
		return getListContainerImpl().getListContainerElement();
	}

	public List addList() {
		return getListContainerImpl().addList();
	}

	public List addList(ListDecorator decorator) {
		return getListContainerImpl().addList(decorator);
	}

	public void clearList() {
		getListContainerImpl().clearList();
	}

	public Iterator<List> getListIterator() {
		return getListContainerImpl().getListIterator();
	}

	public boolean removeList(List list) {
		return getListContainerImpl().removeList(list);
	}

	/**
	 * Creates a new paragraph and append text
	 * 
	 * @param text
	 * @return the new paragraph
	 * @throws Exception
	 *             if the file DOM could not be created.
	 */
	public Paragraph addParagraph(String text) {
		Paragraph para = getParagraphContainerImpl().addParagraph(text);
		return para;
	}

	/**
	 * Remove paragraph from this document
	 * 
	 * @param para
	 *            - the instance of paragraph
	 * @return true if the paragraph is removed successfully, false if errors
	 *         happen.
	 */
	public boolean removeParagraph(Paragraph para) {
		return getParagraphContainerImpl().removeParagraph(para);
	}

	public OdfElement getParagraphContainerElement() {
		return getParagraphContainerImpl().getParagraphContainerElement();
	}

	public Paragraph getParagraphByIndex(int index, boolean isEmptyParagraphSkipped) {
		return getParagraphContainerImpl().getParagraphByIndex(index, isEmptyParagraphSkipped);
	}

	public Paragraph getParagraphByReverseIndex(int reverseIndex, boolean isEmptyParagraphSkipped) {
		return getParagraphContainerImpl().getParagraphByReverseIndex(reverseIndex, isEmptyParagraphSkipped);
	}

	public Iterator<Paragraph> getParagraphIterator() {
		return getParagraphContainerImpl().getParagraphIterator();
	}

	public VariableField declareVariable(String name, VariableType type) {
		return getVariableContainerImpl().declareVariable(name, type);
	}

	public VariableField getVariableFieldByName(String name) {
		return getVariableContainerImpl().getVariableFieldByName(name);
	}

	public OdfElement getVariableContainerElement() {
		return getVariableContainerImpl().getVariableContainerElement();
	}
	
	public Chart createChart(String title, DataSet dataset, Rectangle rect) {
		return getChartContainerImpl().createChart(title, dataset, rect);
	}

	public Chart createChart(String title, SpreadsheetDocument document, CellRangeAddressList cellRangeAddr, boolean firstRowAsLabel,
			boolean firstColumnAsLabel, boolean rowAsDataSeries, Rectangle rect) {
		return getChartContainerImpl().createChart(title, document, cellRangeAddr, firstRowAsLabel, firstColumnAsLabel,
				rowAsDataSeries, rect);
	}

	public Chart createChart(String title, String[] labels, String[] legends, double[][] data, Rectangle rect) {
		return getChartContainerImpl().createChart(title, labels, legends, data, rect);
	}

	public void deleteChartById(String chartId) {
		getChartContainerImpl().deleteChartById(chartId);
	}

	public void deleteChartByTitle(String title) {
		getChartContainerImpl().deleteChartByTitle(title);
	}

	public Chart getChartById(String chartId) {
		return getChartContainerImpl().getChartById(chartId);
	}

	public java.util.List<Chart> getChartByTitle(String title) {
		return getChartContainerImpl().getChartByTitle(title);
	}

	public int getChartCount() {
		return getChartContainerImpl().getChartCount();
	}
	
	private ListContainerImpl getListContainerImpl() {
		if (listContainerImpl == null) {
			listContainerImpl = new ListContainerImpl();
		}
		return listContainerImpl;
	}

	private class ListContainerImpl extends AbstractListContainer {

		public OdfElement getListContainerElement() {
			OdfElement containerElement = null;
			try {
				containerElement = getContentRoot();
			} catch (Exception e) {
				Logger.getLogger(TextDocument.class.getName()).log(Level.SEVERE, null, e);
			}
			return containerElement;
		}
	}

	private StyleMasterPageElement getMasterPage(boolean pFirstPage) throws Exception {
		String pageStyleName = pFirstPage ? "First_20_Page" : "Standard";
		OfficeDocumentStylesElement rootElement = getStylesDom().getRootElement();
		OfficeMasterStylesElement masterStyles = OdfElement.findFirstChildNode(OfficeMasterStylesElement.class,
				rootElement);
		if (masterStyles == null) {
			masterStyles = rootElement.newOfficeMasterStylesElement();
		}
		StyleMasterPageElement masterPageEle = null;
		NodeList lastMasterPages = masterStyles.getElementsByTagNameNS(OdfDocumentNamespace.STYLE.getUri(),
				"master-page");
		if (lastMasterPages != null && lastMasterPages.getLength() > 0) {
			for (int i = 0; i < lastMasterPages.getLength(); i++) {
				StyleMasterPageElement masterPage = (StyleMasterPageElement) lastMasterPages.item(i);
				String styleName = masterPage.getStyleNameAttribute();
				if (pageStyleName.equals(styleName)) {
					masterPageEle = masterPage;
					break;
				}
			}
		}
		if (masterPageEle == null) {
			OdfStylePageLayout layout = OdfElement.findFirstChildNode(OdfStylePageLayout.class, getStylesDom()
					.getAutomaticStyles());
			masterPageEle = masterStyles.newStyleMasterPageElement(pageStyleName, layout.getStyleNameAttribute());
		}
		return masterPageEle;
	}

	private class ParagraphContainerImpl extends AbstractParagraphContainer {
		public OdfElement getParagraphContainerElement() {
			OdfElement containerElement = null;
			try {
				containerElement = getContentRoot();
			} catch (Exception e) {
				Logger.getLogger(TextDocument.class.getName()).log(Level.SEVERE, null, e);
			}
			return containerElement;
		}
	}

	private ParagraphContainerImpl getParagraphContainerImpl() {
		if (paragraphContainerImpl == null)
			paragraphContainerImpl = new ParagraphContainerImpl();
		return paragraphContainerImpl;
	}

	private class VariableContainerImpl extends AbstractVariableContainer {

		public OdfElement getVariableContainerElement() {
			try {
				return getContentRoot();
			} catch (Exception e) {
				Logger.getLogger(TextDocument.class.getName()).log(Level.SEVERE, null, e);
				return null;
			}
		}
	}

	private VariableContainer getVariableContainerImpl() {
		if (variableContainerImpl == null) {
			variableContainerImpl = new VariableContainerImpl();
		}
		return variableContainerImpl;
	}
	
	private ChartContainerImpl getChartContainerImpl() {
		if (chartContainerImpl == null) {
			chartContainerImpl = new ChartContainerImpl(this);
		}
		return chartContainerImpl;
	}
	
	private class ChartContainerImpl extends AbstractChartContainer {
		TextDocument sdoc;

		protected ChartContainerImpl(Document doc) {
			super(doc);
			sdoc = (TextDocument) doc;
		}

		protected DrawFrameElement getChartFrame() throws Exception {
			OdfContentDom contentDom2 = sdoc.getContentDom();
			DrawFrameElement drawFrame = contentDom2.newOdfElement(DrawFrameElement.class);
			TextPElement lastPara = sdoc.getContentRoot().newTextPElement();
			lastPara.appendChild(drawFrame);
			drawFrame.setTextAnchorTypeAttribute(TextAnchorTypeAttribute.Value.PARAGRAPH.toString());
			return drawFrame;
		}
	}
}
