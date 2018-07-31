//Copyright (C) 2010  Novabit Informationssysteme GmbH
//
//This file is part of Nuclos.
//
//Nuclos is free software: you can redistribute it and/or modify
//it under the terms of the GNU Affero General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//Nuclos is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Affero General Public License for more details.
//
//You should have received a copy of the GNU Affero General Public License
//along with Nuclos.  If not, see <http://www.gnu.org/licenses/>.
package org.nuclos.server.report.export;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.nuclos.common.NuclosFile;
import org.nuclos.common.UID;
import org.nuclos.common.collect.collectable.CollectableFieldFormat;
import org.nuclos.common.report.NuclosReportException;
import org.nuclos.common.report.ReportFieldDefinition;
import org.nuclos.common.report.ReportFieldDefinitionFactory;
import org.nuclos.common.report.valueobject.DefaultReportOutputVO;
import org.nuclos.common.report.valueobject.ReportOutputVO;
import org.nuclos.common.report.valueobject.ResultColumnVO;
import org.nuclos.common.report.valueobject.ResultVO;
import org.nuclos.common2.LangUtils;
import org.nuclos.common2.StringUtils;
import org.nuclos.common2.exception.CommonBusinessException;
import org.nuclos.server.common.NuclosUserDetailsContextHolder;
import org.nuclos.server.masterdata.ejb3.MasterDataFacadeLocal;
import org.nuclos.server.report.Export;
import org.nuclos.server.report.ejb3.DatasourceFacadeLocal;
import org.nuclos.server.report.ejb3.ReportFacadeLocal;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.w3c.dom.Node;

@Configurable
public class WordExport extends Export {
	
	private static final Logger LOG = LoggerFactory.getLogger(WordExport.class);

	private final ReportOutputVO.Format format;

	private DatasourceFacadeLocal datasourceFacade;

	private MasterDataFacadeLocal masterdataFacade;
	
	private ReportFacadeLocal reportFacade;
	
	@Autowired
	private NuclosUserDetailsContextHolder userCtx;
	
	
	public WordExport(ReportOutputVO.Format format) {
		super();
		this.format = format;
	}

	@Autowired
	public void setDatasourceFacade(DatasourceFacadeLocal datasourceFacade) {
		this.datasourceFacade = datasourceFacade;
	}
	
	@Autowired
	public void setMasterdataFacade(MasterDataFacadeLocal masterdatafacade) {
		this.masterdataFacade = masterdatafacade;
	}
	
	@Autowired
	public void setReportFacade(ReportFacadeLocal reportFacade) {
		this.reportFacade = reportFacade;
	}

	@Override
	public NuclosFile test(DefaultReportOutputVO output, UID mandatorUID) throws NuclosReportException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		try {
			final Object doc = newDocument(output);
			if (doc instanceof HWPFDocument)
				((HWPFDocument)doc).write(baos);
			if (doc instanceof XWPFDocument)
				((XWPFDocument)doc).write(baos);
			return new NuclosFile(output.getDescription() != null ? output.getDescription() : "Preview" + format.getExtension(), baos.toByteArray());
		}
		catch (IOException e) {
			throw new NuclosReportException(e);
		}
		finally {
			try {
				baos.close();
			}
			catch (IOException e) { }
		}
	}

	@Override
	public NuclosFile export(DefaultReportOutputVO output, Map<String, Object> params, Locale locale, int maxrows, UID language, UID mandatorUID) throws NuclosReportException {
		try {
//			String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//			ReportVO r = MasterDataWrapper.getReportVO(masterdataFacade.get(E.REPORT, output.getReportUID()), username, userCtx.getMandatorUID());
			ResultVO resultvo = datasourceFacade.executeQuery(output.getDatasourceUID(), params, maxrows, language, mandatorUID);
			if (resultvo.getRows().isEmpty()) {
				throw new NuclosReportException("report.exception.nodata");
			}
			List<ReportFieldDefinition> fields = ReportFieldDefinitionFactory.getFieldDefinitions(resultvo);
			return export(newDocument(output), resultvo, fields, 
					getPath(!StringUtils.isNullOrEmpty(output.getFilename()) ? output.getFilename() : output.getDescription(), resultvo, fields));
		}
		catch (IOException e1) {
			throw new NuclosReportException(e1);
		}
		catch (CommonBusinessException e1) {
			throw new NuclosReportException(e1);
		}
	}

	@Override
	public NuclosFile export(ReportOutputVO output, ResultVO result, List<ReportFieldDefinition> fields) throws NuclosReportException {
		try {
			return export(newDocument(), result, fields, getPath(!StringUtils.isNullOrEmpty(output.getFilename()) ? output.getFilename() : output.getDescription(), result, fields));
		}
		catch (IOException e1) {
			throw new NuclosReportException(e1);
		}
	}

	private NuclosFile export(Object docObj, ResultVO resultvo, List<ReportFieldDefinition> fields, String name) throws NuclosReportException {
		if (docObj instanceof HWPFDocument) {
			final HWPFDocument doc = ((HWPFDocument)docObj);
			try {
				// export data
				for (int i = 0; i < resultvo.getRows().size(); i++) {
				 for (int j = 0; j < resultvo.getColumns().size(); j++) {
						final Object value = resultvo.getRows().get(i)[j];
						final ResultColumnVO column = resultvo.getColumns().get(j);
						final Bookmarks bookmarks = doc.getBookmarks();
						if (bookmarks != null) {
							for (int k = 0; k < bookmarks.getBookmarksCount(); k++) {
								final Bookmark b = bookmarks.getBookmark(k);
								if (b.getName().equals(column.getColumnLabel())) {
									if (value != null) {
										final ReportFieldDefinition def = fields.get(j);
										final Range range = new Range(b.getStart(), b.getEnd(), doc);
										range.replaceText(range.text(), CollectableFieldFormat.getInstance(def.getJavaClass()).format(def.getOutputformat(), value));
									}
								}
							}
						}
				 	}
				}
			} catch (Exception e) {
				LOG.warn("error exporting report: {}", e.getMessage());
			}
		}
		if (docObj instanceof XWPFDocument) {
			final XWPFDocument doc = ((XWPFDocument)docObj);
			try {
				// export data
				for (int i = 0; i < resultvo.getRows().size(); i++) {
				 for (int j = 0; j < resultvo.getColumns().size(); j++) {
						final Object value = resultvo.getRows().get(i)[j];
						final ResultColumnVO column = resultvo.getColumns().get(j);
						
						final List<XWPFParagraph> lstParagraphs = new ArrayList<XWPFParagraph>();

						final List<XWPFTable> tableList = doc.getTables();
				        final Iterator<XWPFTable> tableIter = tableList.iterator();
				        while(tableIter.hasNext()) {
					        final XWPFTable table = tableIter.next();
				        	final List<XWPFTableRow> rowList = table.getRows();
		  					final Iterator<XWPFTableRow> rowIter = rowList.iterator();
				            while(rowIter.hasNext()) {
				                final XWPFTableRow row = rowIter.next();
				                final List<XWPFTableCell> cellList = row.getTableCells();
				                final Iterator<XWPFTableCell> cellIter = cellList.iterator();
				                while(cellIter.hasNext()) {
				                	final XWPFTableCell cell = cellIter.next();
				                    lstParagraphs.addAll(cell.getParagraphs());
				                }
				            }
				        }
				        
				        lstParagraphs.addAll(doc.getParagraphs());

						Iterator<XWPFParagraph>  itParagraph = lstParagraphs.iterator();
						while (itParagraph.hasNext()){
						      final XWPFParagraph paragraph  = itParagraph.next();

						      final CTP ctp = paragraph.getCTP();

						      final List<CTBookmark> bList = ctp.getBookmarkStartList();
						      final Iterator<CTBookmark> bIter = bList.iterator();
						      
						      CTBookmark b = null; 
					          while(bIter.hasNext()) {
					                b = bIter.next();
					                if(b.getName().equals(column.getColumnLabel())) {
										if (value != null) {
											final ReportFieldDefinition def = fields.get(j);
											
											final XWPFRun run = new XWPFRun(ctp.addNewR(), paragraph);
											String formattedValue = CollectableFieldFormat.getInstance(def.getJavaClass()).format(def.getOutputformat(), value); 
											if (formattedValue != null && formattedValue.contains("\n")) {
								                String[] lines = formattedValue.split("\n");
								                run.setText(lines[0], 0);
								                for(int iLine=1;iLine<lines.length;iLine++){
								                    run.addBreak();
								                    run.setText(lines[iLine]);
								                }
								            } else {
								                run.setText(formattedValue, 0);
								            }
											replaceBookmark(b, run, paragraph);
										}
					                }
					            } 
					     } 
				 	}
				}
			}
			catch (Exception e) {
				LOG.warn("error exporting report: {}", e.getMessage());
			} 
		}		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		try {
			if (docObj instanceof HWPFDocument)
				((HWPFDocument)docObj).write(baos);
			if (docObj instanceof XWPFDocument)
				((XWPFDocument)docObj).write(baos);
			return new NuclosFile(name + format.getExtension(), baos.toByteArray());
		}
		catch (IOException e) {
			throw new NuclosReportException(e);
		}
		finally {
			try {
				baos.close();
			}
			catch (IOException e) { }
		}
	}
	
	private void replaceBookmark(CTBookmark bookmark, XWPFRun run,
            XWPFParagraph para) {
        Node nextNode = null;
        Node styleNode = null;
        Node lastRunNode = null;
        Stack<Node> nodeStack = null;
        int bookmarkStartID = 0;
        int bookmarkEndID = -1;

        nodeStack = new Stack<Node>();
        bookmarkStartID = bookmark.getId().intValue();
        nextNode = bookmark.getDomNode();

        // Loop through the nodes looking for a matching bookmarkEnd tag
        while (bookmarkStartID != bookmarkEndID) {

            nextNode = nextNode.getNextSibling();

            // If an end tag is found, does it match the start tag? If so, end
            // the while loop.
            if (nextNode.getNodeName().contains("bookmarkEnd")) {
                try {
                    bookmarkEndID = Integer.parseInt(
                            nextNode.getAttributes().getNamedItem("w:id").getNodeValue());
                } catch (NumberFormatException nfe) {
                    bookmarkEndID = bookmarkStartID;
                }
            } else {
                // If this is not a bookmark end tag, store the reference to the
                // node on the stack for later deletion. This is easier that
                // trying to delete the nodes as they are found.
                nodeStack.push(nextNode);
            }
        }

        // If the stack of nodes found between the bookmark tags is not empty
        // then they have to be removed.
        if (!nodeStack.isEmpty()) {

            // Check the node at the top of the stack. If it is a run, get it's
            // style - if any - and apply to the run that will be replacing it.
            lastRunNode = nodeStack.pop();
            if ((lastRunNode.getNodeName().equals("w:r"))) {
                styleNode = this.getStyleNode(lastRunNode);
                if (styleNode != null) {
                    run.getCTR().getDomNode().insertBefore(
                            styleNode.cloneNode(true), run.getCTR().getDomNode().getFirstChild());
                }
            }

            // Delete any and all node that were found in between the start and
            // end tags. This is slightly safer that trying to delete the nodes
            // as they are found wile stepping through them in the loop above.
            para.getCTP().getDomNode().removeChild(lastRunNode);
            // Now, delete the remaing Nodes on the stack
            while (!nodeStack.isEmpty()) {
                para.getCTP().getDomNode().removeChild(nodeStack.pop());
            }
        }

        // Place the text into position, between the bookmark tags.
        para.getCTP().getDomNode().insertBefore(
                run.getCTR().getDomNode(), nextNode);
    } 
	
    private Node getStyleNode(Node parentNode) {
        Node childNode = null;
        Node styleNode = null;
        if (parentNode != null) {

            // If the node represents a run and it has child nodes then
            // it can be processed further. Note, whilst testing the code, it
            // was observed that although it is possible to get a list of a nodes
            // children, even when a node did have children, trying to obtain this
            // list would often return a null value. This is the reason why the
            // technique of stepping from one node to the next is used here.
            if (parentNode.getNodeName().equalsIgnoreCase("w:r")
                    && parentNode.hasChildNodes()) {

                // Get the first node and catch it's reference for return if
                // the first child node is a style node (w:rPr).
                childNode = parentNode.getFirstChild();
                if (childNode.getNodeName().equals("w:rPr")) {
                    styleNode = childNode;
                } else {
                    // If the first node was not a style node and there are other
                    // child nodes remaining to be checked, then step through
                    // the remaining child nodes until either a style node is
                    // found or until all child nodes have been processed.
                    while ((childNode = childNode.getNextSibling()) != null) {
                        if (childNode.getNodeName().equals("w:rPr")) {
                            styleNode = childNode;
                            // Note setting to null here if a style node is
                            // found in order order to terminate any further
                            // checking
                            childNode = null;
                        }
                    }
                }
            }
        }
        return (styleNode);
    } 
    
	private Object newDocument() throws IOException {
		if (format == ReportOutputVO.Format.DOC) {
			return new HWPFDocument(LangUtils.getClassLoaderThatWorksForWebStart().getResourceAsStream(
					"org/nuclos/server/report/export/blank.doc"));
		}
		else if (format == ReportOutputVO.Format.DOCX) {
			return new XWPFDocument();
		}
		else {
			throw new IllegalStateException();
		}
	}

	private Object newDocument(ReportOutputVO output) throws IOException {
		if (output.getSourceFile() != null) {
			if (format == ReportOutputVO.Format.DOC) {
				return new HWPFDocument(new ByteArrayInputStream(output.getSourceFileContent().getData()));
			}
			else if (format == ReportOutputVO.Format.DOCX) {
				return new XWPFDocument(new ByteArrayInputStream(output.getSourceFileContent().getData()));
			}
			else {
				throw new IllegalStateException();
			}
		}
		else {
			return newDocument();
		}
	}
}
