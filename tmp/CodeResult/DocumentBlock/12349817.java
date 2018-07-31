package org.grooveclipse.documentation.importer.wikitext;

import java.util.Stack;

import org.eclipse.mylyn.wikitext.core.parser.Attributes;
import org.eclipse.mylyn.wikitext.core.parser.DocumentBuilder;
import org.grooveclipse.documentation.Cell;
import org.grooveclipse.documentation.DocList;
import org.grooveclipse.documentation.DocTable;
import org.grooveclipse.documentation.Document;
import org.grooveclipse.documentation.DocumentItem;
import org.grooveclipse.documentation.DocumentItemContainer;
import org.grooveclipse.documentation.DocumentationElement;
import org.grooveclipse.documentation.DocumentationFactory;
import org.grooveclipse.documentation.Link;
import org.grooveclipse.documentation.ListItem;
import org.grooveclipse.documentation.ListType;
import org.grooveclipse.documentation.Paragraph;
import org.grooveclipse.documentation.Row;
import org.grooveclipse.documentation.Section;
import org.grooveclipse.documentation.Text;
import org.grooveclipse.documentation.ValueList;

public class WikitextDocumentBuilder extends DocumentBuilder {

	private static final String ABSTRACT_TITLE = "Abstract";
	private DocumentationFactory docFactory = DocumentationFactory.eINSTANCE;

	private Document document;
	private Stack<DocumentationElement> docElementStack;
	private int sectionLevel = 0;

	public WikitextDocumentBuilder() {
		docElementStack = new Stack<DocumentationElement>();
	}

	@Override
	public void acronym(String text, String definition) {

	}

	@Override
	public void beginBlock(BlockType type, Attributes attributes) {

		DocumentationElement documentationElement = docElementStack.peek();

		DocumentationElement documentBlock = null;

		switch (type) {
		case PARAGRAPH :
			documentBlock = docFactory.createParagraph();
			break;
		case NUMERIC_LIST :
			ValueList valueList = docFactory.createValueList();
			valueList.setType(ListType.NUMERIC);
			documentBlock = valueList;
			break;
		case BULLETED_LIST :
			valueList = docFactory.createValueList();
			valueList.setType(ListType.BULLET);
			documentBlock = valueList;
			break;
		case LIST_ITEM :
			ListItem listItem = docFactory.createListItem();
			if (documentationElement instanceof DocList) {
				DocList docList = (DocList) documentationElement;
				docList.getListItems().add(listItem);
			}
			documentBlock = listItem;
			break;
		case TABLE :
			DocTable docTable = docFactory.createDocTable();
			documentBlock = docTable;
			break;
		case TABLE_ROW :
			Row row = docFactory.createRow();
			if (documentationElement instanceof DocTable) {
				docTable = (DocTable) documentationElement;
				docTable.getRows().add(row);
			}
			documentBlock = row;
			break;
		case TABLE_CELL_HEADER :
			Cell cell = docFactory.createCell();
			cell.setHeader(true);
			if (documentationElement instanceof Row) {
				row = (Row) documentationElement;
				row.getCells().add(cell);
			}
			documentBlock = cell;
			break;
		case TABLE_CELL_NORMAL :
			cell = docFactory.createCell();
			if (documentationElement instanceof Row) {
				row = (Row) documentationElement;
				row.getCells().add(cell);
			}
			documentBlock = cell;
			break;
		}

		if (documentBlock instanceof DocumentItem && documentationElement instanceof Section) {
			DocumentItem docItem = (DocumentItem) documentBlock;
			Section section = (Section) documentationElement;
			section.getItems().add(docItem);
		}

		docElementStack.push(documentBlock);
	}

	@Override
	public void beginDocument() {
		document = docFactory.createDocument();
		docElementStack.push(document);
		sectionLevel++;
	}

	private void closeSections(int toLevel) {

		DocumentationElement documentationElement = docElementStack.peek();

		if (toLevel < 1) {
			toLevel = 1;
		}

		int levelDiff = sectionLevel - toLevel;
		if (levelDiff>0) {

			for(int i = 0; i < levelDiff; i++) {
				if (documentationElement instanceof Section) {
					Section section = (Section) documentationElement;
					documentationElement = docElementStack.pop();
				}
			}

			sectionLevel = toLevel;
		}

//		while (sectionLevel > toLevel) {
//
//			if (documentationElement instanceof Section) {
//
//				Section section = (Section) documentationElement;
//				int currentSectionLevel = determineSectionLevel(section);
//
//				documentationElement = docElementStack.pop();
//				sectionLevel = currentSectionLevel-1;
//
//			} else {
//
//				sectionLevel = toLevel;
//
//			}
//		}
	}

//	private int determineSectionLevel(Section section) {
//
//		int currentSectionLevel = 1;
//
//		Section sectionContainer = section.getContainer();
//		if (sectionContainer!=null) {
//			int level = determineSectionLevel(sectionContainer);
//			currentSectionLevel = level + 1;
//		}
//
//		return currentSectionLevel;
//	}

	@Override
	public void beginHeading(int level, Attributes attributes) {

		level = level + 1;
		closeSections(Math.max(level-1, 1));

		while (sectionLevel < level) {
			sectionLevel++;
		}

		DocumentationElement documentationElement = docElementStack.peek();

		Section section = docFactory.createSection();

		String title = attributes.getTitle();
		String id = attributes.getId();
		title = title!=null ? title : id;
		title = title!=null ? title.trim() : title;


		section.setTitle(title);

		if (documentationElement instanceof Section) {
			Section parentSection = (Section) documentationElement;
			parentSection.getSections().add(section);
			parentSection.getItems().add(section);
		}

		if (ABSTRACT_TITLE.equalsIgnoreCase(title) && documentationElement instanceof Document) {
			Document document = (Document) documentationElement;
			document.setAbstract(section);
		}

		docElementStack.push(section);

	}

	@Override
	public void beginSpan(SpanType type, Attributes attributes) {

		switch(type) {
		case SPAN :
			break;
		}

	}

	@Override
	public void characters(String text) {

		DocumentationElement documentationElement = docElementStack.peek();

		if (documentationElement instanceof Section) {
			Section section = (Section) documentationElement;
			section.setTitle(text);
		} else if (documentationElement instanceof DocumentItemContainer) {
			DocumentItemContainer documentItemContainer = (DocumentItemContainer) documentationElement;
			Text docText = docFactory.createText();
			docText.setTextContent(text);
			documentItemContainer.getItems().add(docText);
		}
	}

	@Override
	public void charactersUnescaped(String literal) {

	}

	@Override
	public void endBlock() {
		docElementStack.pop();
	}

	@Override
	public void endDocument() {
		docElementStack.pop();
	}

	@Override
	public void endHeading() {
	}

	@Override
	public void endSpan() {


	}

	@Override
	public void entityReference(String entity) {


	}

	@Override
	public void image(Attributes attributes, String url) {


	}

	@Override
	public void imageLink(Attributes linkAttributes,
			Attributes imageAttributes, String href, String imageUrl) {


	}

	@Override
	public void lineBreak() {


	}

	@Override
	public void link(Attributes attributes, String hrefOrHashName, String text) {

		DocumentationElement documentationElement = docElementStack.peek();

		if (documentationElement instanceof DocumentItemContainer) {

			DocumentItemContainer documentItemContainer = (DocumentItemContainer) documentationElement;

			Link link = docFactory.createLink();
			link.setTextContent(text);
			link.setUrl(hrefOrHashName);

			documentItemContainer.getItems().add(link);
		}

	}

	public Document getDocument() {
		return document;
	}

}