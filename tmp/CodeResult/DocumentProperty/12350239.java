package org.grooveclipse.word.comon.ole;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Shell;
import org.grooveclipse.ole.common.IAutomationVisitor;
import org.grooveclipse.ole.common.IVariantVisitor;
import org.grooveclipse.ole.common.OleHelper;
import org.grooveclipse.word.common.enums.word.WdBuiltInProperty;
import org.grooveclipse.word.common.enums.word.WdGoToDirection;
import org.grooveclipse.word.common.enums.word.WdGoToItem;
import org.grooveclipse.word.common.enums.word.WdReferenceKind;
import org.grooveclipse.word.common.enums.word.WdReferenceType;
import org.grooveclipse.word.common.enums.word.WdStyleType;
import org.grooveclipse.word.common.enums.word.WdUnits;
import org.grooveclipse.word.common.enums.word.WdViewType;
import org.grooveclipse.word.comon.IWordSettings;
import org.grooveclipse.word.comon.util.ReferenceHolder;
import org.grooveclipse.word.comon.util.ReferenceType;

import com.google.common.collect.Maps;

/**
 * The Class WordRemoteHandler is responsible for interacting with MS Word via
 * the SWT Ole API. It's implementation is based on ported VBA scripts and on
 * the Word reference provided by MSN on http://msdn.microsoft.com/en-us/library/ff841702.aspx
 * 
 * TODO : the enums provided should be extracted to separate classes.
 * Word Enumerated Constants - http://msdn.microsoft.com/en-us/library/Aa211923%28office.11%29.aspx
 * this could be the source for generating the corresponding enum classes.
 */
public class WordRemoteHandler {

	/** The table idx. */
	private int tableIdx = 1;

	/**
	 * Sets the table idx.
	 * 
	 * @param tableIdx
	 *            the new table idx
	 */
	public void setTableIdx(int tableIdx) {
		this.tableIdx = tableIdx;
	}

	/**
	 * Gets the table idx.
	 * 
	 * @return the table idx
	 */
	public int getTableIdx() {
		return tableIdx;
	}

//	/**
//	 * The Enum View.
//	 */
//	public enum View {
//
//		/** The Master. */
//		Master(5),
//		/** The Normal. */
//		Normal(1),
//		/** The Outline. */
//		Outline(2),
//		/** The Print preview. */
//		PrintPreview(4),
//		/** The Print. */
//		Print(3),
//		/** The Reading. */
//		Reading(7),
//		/** The Web. */
//		Web(6);
//
//		/** The key. */
//		private int key = -1;
//
//		/**
//		 * Instantiates a new view.
//		 * 
//		 * @param key
//		 *            the key
//		 */
//		private View(int key) {
//			this.key = key;
//		}
//
//		/**
//		 * Gets the key.
//		 * 
//		 * @return the key
//		 */
//		public int getKey() {
//			return key;
//		}
//
//		/**
//		 * To view.
//		 * 
//		 * @param key
//		 *            the key
//		 * @return the view
//		 */
//		public static View toView(int key) {
//			View result = View.Master;
//			for (View view : values()) {
//				if (view.getKey() == key) {
//					result = view;
//					break;
//				}
//			}
//			return result;
//		}
//	}

	/**
	 * The Enum CustomDocumentProperty.
	 */
	public enum CustomDocumentProperty {

		/** The Content type. */
		ContentType(4),
		/** The Document number. */
		DocumentNumber(2),
		/** The Document status. */
		DocumentStatus(5),
		/** The User date. */
		UserDate(6),
		/** The Version. */
		Version(1);

		/** The key. */
		private int key;

		/**
		 * Instantiates a new custom document property.
		 * 
		 * @param key
		 *            the key
		 */
		private CustomDocumentProperty(int key) {
			this.key = key;
		}

		/**
		 * Gets the key.
		 * 
		 * @return the key
		 */
		public int getKey() {
			return key;
		}
	}

	/**
	 * The Enum DocumentPart.
	 */
	public enum DocumentPart {

		/** The Footer. */
		Footer("Footers"),
		/** The Header. */
		Header("Headers");

		/** The property name. */
		private String propertyName = null;

		/**
		 * Instantiates a new document part.
		 * 
		 * @param propertyName
		 *            the property name
		 */
		private DocumentPart(String propertyName) {
			this.propertyName = propertyName;
		}

		/**
		 * Gets the property name.
		 * 
		 * @return the property name
		 */
		public String getPropertyName() {
			return propertyName;
		}
	}

//	/**
//	 * The Enum DocumentProperty.
//	 */
//	public enum DocumentProperty {
//
//		/** The Application_ name. */
//		Application_Name(9),
//		/** The Author. */
//		Author(3),
//		/** The Category. */
//		Category(18),
//		/** The Comments. */
//		Comments(5),
//		/** The Company. */
//		Company(21),
//		/** The Creation_ date. */
//		Creation_Date(11),
//		/** The Format. */
//		Format(19),
//		/** The Hyperlinkbasis. */
//		Hyperlinkbasis(29),
//		/** The Keywords. */
//		Keywords(4),
//		/** The Last_ author. */
//		Last_Author(7),
//		/** The Last_ print_ date. */
//		Last_Print_Date(10),
//		/** The Last_ save_ time. */
//		Last_Save_Time(12),
//		/** The Manager. */
//		Manager(20),
//		/** The Number_ of_ bytes. */
//		Number_Of_Bytes(22),
//		/** The Number_ of_ characters. */
//		Number_Of_Characters(16),
//		/** The Number_ of_ hidden_ slides. */
//		Number_Of_Hidden_Slides(27),
//		/** The Number_ of_ lines. */
//		Number_Of_Lines(23),
//		/** The Number_ of_ multimedia_ clips. */
//		Number_Of_Multimedia_Clips(28),
//		/** The Number_ of_ notes. */
//		Number_Of_Notes(26),
//		/** The Number_ of_ pages. */
//		Number_Of_Pages(14),
//		/** The Number_ of_ paragraphs. */
//		Number_Of_Paragraphs(24),
//		/** The Number_ of_ slides. */
//		Number_Of_Slides(25),
//		/** The Number_ of_ words. */
//		Number_Of_Words(15),
//		/** The Revision_ number. */
//		Revision_Number(8),
//		/** The Security. */
//		Security(17),
//		/** The Subject. */
//		Subject(2),
//		/** The Template. */
//		Template(6),
//		/** The Thirty chars. */
//		ThirtyChars(30),
//		/** The Title. */
//		Title(1),
//		/** The Total_ editing_time. */
//		Total_Editing_time(13);
//
//		/** The key. */
//		private int key;
//
//		/**
//		 * Instantiates a new document property.
//		 * 
//		 * @param key
//		 *            the key
//		 */
//		private DocumentProperty(int key) {
//			this.key = key;
//		}
//
//		/**
//		 * Gets the key.
//		 * 
//		 * @return the key
//		 */
//		public int getKey() {
//			return key;
//		}
//	}
//
//	/**
//	 * The Enum WdGoToDirection.
//	 */
//	public enum WdGoToDirection {
//
//		/** The Absolute. */
//		Absolute(1),
//		/** The First. */
//		First(1),
//		/** The Last. */
//		Last(-1),
//		/** The Next. */
//		Next(2),
//		/** The Previous. */
//		Previous(3),
//		/** The Relative. */
//		Relative(2);
//
//		/** The index. */
//		private int index;
//
//		/**
//		 * Instantiates a new wd go to direction.
//		 * 
//		 * @param index
//		 *            the index
//		 */
//		private WdGoToDirection(int index) {
//			this.index = index;
//		}
//
//		/**
//		 * Gets the index.
//		 * 
//		 * @return the index
//		 */
//		public int getIndex() {
//			return index;
//		}
//
//	}
//
//	/**
//	 * The Enum WdGoToItem.
//	 */
//	public enum WdGoToItem {
//
//		/** The Bookmark. */
//		Bookmark(-1),
//		/** The Comment. */
//		Comment(6),
//		/** The End note. */
//		EndNote(5),
//		/** The Equation. */
//		Equation(10),
//		/** The Field. */
//		Field(7),
//		/** The Foot note. */
//		FootNote(4),
//		/** The Grammatical error. */
//		GrammaticalError(14),
//		/** The Graphic. */
//		Graphic(8),
//		/** The Heading. */
//		Heading(11),
//		/** The Line. */
//		Line(3),
//		/** The Object. */
//		Object(9),
//		/** The Page. */
//		Page(1),
//		/** The Percent. */
//		Percent(12),
//		/** The Proof reading error. */
//		ProofReadingError(15),
//		/** The Section. */
//		Section(0),
//		/** The Spelling error. */
//		SpellingError(13),
//		/** The Table. */
//		Table(2);
//
//		/** The index. */
//		private int index;
//
//		/**
//		 * Instantiates a new wd go to item.
//		 * 
//		 * @param index
//		 *            the index
//		 */
//		private WdGoToItem(int index) {
//			this.index = index;
//		}
//
//		/**
//		 * Gets the index.
//		 * 
//		 * @return the index
//		 */
//		public int getIndex() {
//			return index;
//		}
//
//	}
//
//	/**
//	 * The Enum WdUnit.
//	 */
//	public enum WdUnit {
//
//		/** The Cell. */
//		Cell(12),
//		/** The Character. */
//		Character(1),
//		/** The Character formatting. */
//		CharacterFormatting(13),
//		/** The Column. */
//		Column(9),
//		/** The Item. */
//		Item(16),
//		/** The Line. */
//		Line(5),
//		/** The Paragraph. */
//		Paragraph(4),
//		/** The Paragraph formatting. */
//		ParagraphFormatting(14),
//		/** The Row. */
//		Row(10),
//		/** The Screen. */
//		Screen(7),
//		/** The Section. */
//		Section(8),
//		/** The Sentence. */
//		Sentence(3),
//		/** The Story. */
//		Story(6),
//		/** The Table. */
//		Table(15),
//		/** The Window. */
//		Window(11),
//		/** The Word. */
//		Word(2);
//
//		/** The index. */
//		private int index;
//
//		/**
//		 * Instantiates a new wd unit.
//		 * 
//		 * @param index
//		 *            the index
//		 */
//		private WdUnit(int index) {
//			this.index = index;
//		}
//
//		/**
//		 * Gets the index.
//		 * 
//		 * @return the index
//		 */
//		public int getIndex() {
//			return index;
//		}
//
//	}
//
//	/**
//	 * The Enum WdReferenceKind.
//	 */
//	public enum WdReferenceKind {
//
//		/** The Content text. */
//		ContentText(-1),
//		/** The Endnote number. */
//		EndnoteNumber(6),
//		/** The Entire caption. */
//		EntireCaption(2),
//		/** The Footnote number. */
//		FootnoteNumber(5),
//		/** The Footnote number formatted. */
//		FootnoteNumberFormatted(16),
//		/** The Number full context. */
//		NumberFullContext(-4),
//		/** The Number no context. */
//		NumberNoContext(-3),
//		/** The Number relative context. */
//		NumberRelativeContext(-2),
//		/** The Only caption text. */
//		OnlyCaptionText(4),
//		/** The Only label and number. */
//		OnlyLabelAndNumber(3),
//		/** The Page number. */
//		PageNumber(7),
//		/** The Position. */
//		Position(15);
//
//		/** The index. */
//		private final int index;
//
//		/**
//		 * Instantiates a new wd reference kind.
//		 * 
//		 * @param index
//		 *            the index
//		 */
//		WdReferenceKind(int index) {
//			this.index = index;
//		}
//
//		/**
//		 * Gets the index.
//		 * 
//		 * @return the index
//		 */
//		public int getIndex() {
//			return index;
//		}
//
//		/**
//		 * To kind.
//		 * 
//		 * @param styleIdx
//		 *            the style idx
//		 * @return the wd reference kind
//		 */
//		public static WdReferenceKind toKind(int styleIdx) {
//			WdReferenceKind referenceKind = null;
//			for (WdReferenceKind referenceKindIt : values()) {
//				if (referenceKindIt.getIndex() == styleIdx) {
//					referenceKind = referenceKindIt;
//					break;
//				}
//			}
//			return referenceKind;
//		}
//
//	}
//
//	/**
//	 * The Enum WdReferenceType.
//	 */
//	public enum WdReferenceType {
//
//		/** The Numbered item. */
//		NumberedItem(0),
//		/** The Heading. */
//		Heading(1),
//		/** The Bookmark. */
//		Bookmark(2),
//		/** The Footnote. */
//		Footnote(3),
//		/** The Endnote. */
//		Endnote(4);
//
//		/** The index. */
//		private final int index;
//
//		/**
//		 * Instantiates a new wd reference type.
//		 * 
//		 * @param index
//		 *            the index
//		 */
//		WdReferenceType(int index) {
//			this.index = index;
//		}
//
//		/**
//		 * Gets the index.
//		 * 
//		 * @return the index
//		 */
//		public int getIndex() {
//			return index;
//		}
//
//		/**
//		 * To type.
//		 * 
//		 * @param styleIdx
//		 *            the style idx
//		 * @return the wd reference type
//		 */
//		public static WdReferenceType toType(int styleIdx) {
//			WdReferenceType referenceType = null;
//			for (WdReferenceType styleTypeIt : values()) {
//				if (styleTypeIt.getIndex() == styleIdx) {
//					referenceType = styleTypeIt;
//					break;
//				}
//			}
//			return referenceType;
//		}
//
//	}
//
//	/**
//	 * The Enum WdStyleType.
//	 */
//	public enum WdStyleType {
//
//		/** The Paragraph. */
//		Paragraph(1),
//		/** The Character. */
//		Character(2),
//		/** The Table. */
//		Table(3),
//		/** The List. */
//		List(4),
//		/** The Paragraph only. */
//		ParagraphOnly(5),
//		/** The Linked. */
//		Linked(6), ;
//
//		/** The index. */
//		private final int index;
//
//		/**
//		 * Instantiates a new wd style type.
//		 * 
//		 * @param index
//		 *            the index
//		 */
//		WdStyleType(int index) {
//			this.index = index;
//		}
//
//		/**
//		 * Gets the index.
//		 * 
//		 * @return the index
//		 */
//		public int getIndex() {
//			return index;
//		}
//
//		/**
//		 * To style.
//		 * 
//		 * @param styleIdx
//		 *            the style idx
//		 * @return the wd style type
//		 */
//		public static WdStyleType toStyle(int styleIdx) {
//			WdStyleType styleType = null;
//			for (WdStyleType styleTypeIt : values()) {
//				if (styleTypeIt.getIndex() == styleIdx) {
//					styleType = styleTypeIt;
//					break;
//				}
//			}
//			return styleType;
//		}
//	}

	/** The Constant PROG_ID. */
	private static final String PROG_ID = "Word.Application";

	/** The Constant WD_FIND_CONTINUE. */
	private static final int WD_FIND_CONTINUE = 1;

	/** The Constant WD_REPLACE_ALL. */
	private static final int WD_REPLACE_ALL = 2;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		WordRemoteHandler wordSR = null;

		try {

			wordSR = new WordRemoteHandler(null);

			wordSR.openFile("Template.docx");
			wordSR.saveAs("Document.docx");

			wordSR.setDocumentProperty(WdBuiltInProperty.WD_PROPERTY_TITLE, "Title");
			wordSR.setDocumentProperty(WdBuiltInProperty.WD_PROPERTY_AUTHOR,
					"Michael Halwax");
			wordSR.setDocumentProperty(WdBuiltInProperty.WD_PROPERTY_CATEGORY,
					"Kostenstelle");
			wordSR.setCustomDocumentProperty(CustomDocumentProperty.UserDate,
					"17.03.2011");

			wordSR.goTo(WdGoToItem.WD_GO_TO_SECTION, WdGoToDirection.WD_GO_TO_LAST);
			int selectionStart = wordSR.determineSelectionStart();
			wordSR.goTo(WdGoToItem.WD_GO_TO_LINE, WdGoToDirection.WD_GO_TO_LAST);
			int selectionEnd = wordSR.determineSelectionStart();

			wordSR.select(selectionStart, selectionEnd);
			wordSR.deleteSelection();

			wordSR.goTo(WdGoToItem.WD_GO_TO_LINE, WdGoToDirection.WD_GO_TO_ABSOLUTE, 1);
			wordSR.readSelection();
			wordSR.goTo(WdGoToItem.WD_GO_TO_LINE, WdGoToDirection.WD_GO_TO_LAST);
			wordSR.insert("Zusammenfassung\n", "Überschrift 1;1");

			selectionStart = wordSR.determineSelectionStart();
			wordSR.insert(
					"Dieses Dokument beschreibt den MDA Prozess in BN und die dabei generierten Artifakte",
					"Standard");
			selectionEnd = wordSR.determineSelectionStart();

			wordSR.comment(selectionStart, selectionEnd, "Test");
			wordSR.insert("\n\n");

			wordSR.save();
			wordSR.update();
			wordSR.save();

			wordSR.closeFile();

		} catch (Exception e) {
			System.out.println("Caught: " + e.getClass().getName());
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
		} finally {
			if (wordSR != null) {
				try {
					wordSR.dispose();
				} catch (Exception innerE) {
					System.out
							.println("Caught: " + innerE.getClass().getName());
					System.out.println(innerE.getMessage());
					innerE.printStackTrace(System.out);
				}
			}
		}
	}

	/**
	 * Creates the table.
	 * 
	 * @param rows
	 *            the rows
	 * @param cols
	 *            the cols
	 * @param styleName
	 *            the style name
	 * @return the int
	 */
	public int createTable(int rows, int cols, String styleName) {
		return this.createTable(rows, cols, styleName, true);
	}

	/**
	 * Creates the table.
	 * 
	 * @param rows
	 *            the rows
	 * @param cols
	 *            the cols
	 * @param styleName
	 *            the style name
	 * @param tableCaption
	 *            the table caption
	 * @return the int
	 */
	public int createTable(int rows, int cols, String styleName,
			boolean tableCaption) {

		Variant rangeProperty = determineRange();

		OleAutomation tablesAutomation = oleHelper.getChildAutomation(
				activeDocumentAutomation, "Tables");
		Variant tableProperty = oleHelper.invokeFunction(tablesAutomation,
				"Add", rangeProperty, rows, cols);
		
		OleAutomation tableAutomation = tableProperty.getAutomation();
		
		// "Tabellengitternetz"
		Entry<WdStyleType, Variant> style = readStyleMap().get(styleName);
		oleHelper.setOleProperty(tableAutomation, "Style", style.getValue());
		// oleHelper.printTypeInfoAttributes(tableAutomation);

		if (tableCaption) {
			oleHelper.invokeFunction(tableAutomation, "Select");
			OleAutomation selectionAutomation = getSelectionAutomation();
			oleHelper.invokeFunction(selectionAutomation, "InsertCaption", -2,
					"", 1, true);
			selectionAutomation.dispose();
		}

		tableAutomation.dispose();
		tablesAutomation.dispose();

		return ++tableIdx;
	}

	/**
	 * Determine range.
	 * 
	 * @return the variant
	 */
	private Variant determineRange() {
		OleAutomation selectionAutomation = getSelectionAutomation();
		Variant rangeProperty = oleHelper.getOleProperty(selectionAutomation,
				"Range");
		selectionAutomation.dispose();
		return rangeProperty;
	}

	/**
	 * Select cell.
	 * 
	 * @param tableIndex
	 *            the table index
	 * @param row
	 *            the row
	 * @param column
	 *            the column
	 */
	public void selectCell(int tableIndex, int row, int column) {
		OleAutomation tablesAutomation = oleHelper.getChildAutomation(
				activeDocumentAutomation, "Tables");
		Variant tableProperty = oleHelper.invokeFunction(tablesAutomation,
				"Item", tableIndex);
		OleAutomation tableAutomation = tableProperty.getAutomation();

		OleAutomation cellAutomation = oleHelper.invokeFunction(
				tableAutomation, "Cell", row, column).getAutomation();
		OleAutomation rangeAutomation = oleHelper.getChildAutomation(
				cellAutomation, "Range");
		oleHelper.invokeFunction(rangeAutomation, "Select");

		rangeAutomation.dispose();
		cellAutomation.dispose();
		tableAutomation.dispose();
		tablesAutomation.dispose();

	}

	/** The active document automation. */
	private OleAutomation activeDocumentAutomation = null;

	/** The cleaned. */
	private boolean cleaned = false;

	/** The frame. */
	private OleFrame frame = null;

	/** The ole helper. */
	private OleHelper oleHelper = null;

	/** The shell. */
	private Shell shell = null;

	/** The word automation. */
	private OleAutomation wordAutomation = null;

	/** The word site. */
	private OleClientSite wordSite = null;

	/** The styles. */
	private Map<String, Entry<WdStyleType, Variant>> styles;

	/** The picture idx. */
	private int pictureIdx = 0;

	/** The word settings. */
	private IWordSettings wordSettings;

	/**
	 * Instantiates a new word remote handler.
	 * 
	 * @param wordSettings
	 *            the word settings
	 */
	public WordRemoteHandler(IWordSettings wordSettings) {
		this(new Shell(), wordSettings);
	}

	/**
	 * Instantiates a new word remote handler.
	 * 
	 * @param shell
	 *            the shell
	 * @param wordSettings
	 *            the word settings
	 */
	public WordRemoteHandler(Shell shell, IWordSettings wordSettings) {
		this.shell = shell;
		this.frame = new OleFrame(this.shell, SWT.NONE);
		this.wordSite = new OleClientSite(this.frame, SWT.NONE,
				WordRemoteHandler.PROG_ID);
		this.wordAutomation = new OleAutomation(this.wordSite);
		this.oleHelper = new OleHelper();
		this.wordSettings = wordSettings;
	}

	/**
	 * Check file extension.
	 * 
	 * @param fileName
	 *            the file name
	 */
	private void checkFileExtension(String fileName) {
		if (!(fileName.endsWith(".doc")) && !(fileName.endsWith(".dot"))
				&& !(fileName.endsWith(".docx"))) {
			throw new IllegalArgumentException(
					"The filename must end with the extensions \'.doc\' or \'.dot\' or \'.docx\'");
		}
	}

	/**
	 * Close file.
	 * 
	 * @throws SWTException
	 *             the sWT exception
	 */
	public void closeFile() throws SWTException {

		int[] id = null;
		Variant invokeResult = null;

		try {

			id = this.activeDocumentAutomation
					.getIDsOfNames(new String[] { "Close" });

			if (id == null) {
				throw new SWTException("It was not possible to recover an "
						+ "identifier for the Close method in "
						+ "WordSearchReplace.closeFile().");
			}

			invokeResult = this.activeDocumentAutomation.invoke(id[0]);

			if (invokeResult == null) {
				throw new SWTException(
						"An error occurred invoking the Close method in "
								+ "WordSearchReplace.closeFile().");
			}

		} finally {
			if (this.activeDocumentAutomation != null) {
				this.activeDocumentAutomation.dispose();
			}
		}

	}

	/**
	 * Delete selection.
	 */
	public void deleteSelection() {
		OleAutomation selectionAutomation = getSelectionAutomation();
		oleHelper.invokeFunction(selectionAutomation, "Delete");
		selectionAutomation.dispose();
	}

	/**
	 * Determine selection start.
	 * 
	 * @return the int
	 */
	public int determineSelectionStart() {
		OleAutomation selectionAutomation = getSelectionAutomation();
		OleAutomation rangeAutomation = oleHelper.getOleProperty(
				selectionAutomation, "Range").getAutomation();
		Variant startProperty = oleHelper.getOleProperty(rangeAutomation,
				"Start");
		int start = startProperty.getInt();
		rangeAutomation.dispose();
		selectionAutomation.dispose();
		return start;

	}

	/**
	 * Release resources.
	 * 
	 * @throws SWTException
	 *             the sWT exception
	 */
	public void dispose() throws SWTException {

		try {
			this.cleaned = true;

			int[] id = this.wordAutomation
					.getIDsOfNames(new String[] { "Quit" });

			if (id == null) {
				throw new SWTException("Unable to obtain an id for the Quit "
						+ "property in WordSearchReplace.dispose().");
			}

			Variant result = this.wordAutomation.invoke(id[0]);

			if (result == null) {
				throw new SWTException(
						"A problem occurred trying to invoke the "
								+ "Quit method in WordSearchReplace.dispose().");
			}

		} finally {
			// Finally, dispose of the word application automation.
			this.wordAutomation.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	public void finalize() throws Throwable {
		if (!this.cleaned) {
			this.dispose();
		}
	}

	/**
	 * Gets the custom property.
	 * 
	 * @param property
	 *            the property
	 * @return the custom property
	 */
	public String getCustomProperty(CustomDocumentProperty property) {

		OleAutomation customDocumentPropertiesAutomation = oleHelper
				.createOleAutomation(activeDocumentAutomation,
						"CustomDocumentProperties");
		int[] itemId = customDocumentPropertiesAutomation
				.getIDsOfNames(new String[] { "Item" });
		String value = customDocumentPropertiesAutomation.getProperty(
				itemId[0], new Variant[] { new Variant(property.getKey()) })
				.getString();
		customDocumentPropertiesAutomation.dispose();

		return value;
	}

	/**
	 * Gets the document property.
	 * 
	 * @param property
	 *            the property
	 * @return the document property
	 * @throws SWTException
	 *             the sWT exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 */
	public String getDocumentProperty(WdBuiltInProperty property)
			throws SWTException, NullPointerException {

		OleAutomation builtInDocumentProperties = oleHelper
				.createOleAutomation(activeDocumentAutomation,
						"BuiltInDocumentProperties");
		int[] itemId = builtInDocumentProperties
				.getIDsOfNames(new String[] { "Item" });
		String value = builtInDocumentProperties.getProperty(itemId[0],
				new Variant[] { new Variant(property.getValue()) }).getString();
		builtInDocumentProperties.dispose();

		return value;
	}

	/**
	 * Gets the selection automation.
	 * 
	 * @return the selection automation
	 */
	public OleAutomation getSelectionAutomation() {
		OleAutomation selectionAutomation = oleHelper.getOleProperty(
				wordAutomation, "Selection").getAutomation();
		return selectionAutomation;
	}

	/**
	 * Go to.
	 * 
	 * @param item
	 *            the item
	 * @param direction
	 *            the direction
	 */
	public void goTo(WdGoToItem item, WdGoToDirection direction) {

		OleAutomation selectionAutomation = getSelectionAutomation();

		oleHelper.invokeFunction(selectionAutomation, "GoTo", item.getValue(),
				direction.getValue());

		selectionAutomation.dispose();
	}

	/**
	 * Go to.
	 * 
	 * @param item
	 *            the item
	 * @param direction
	 *            the direction
	 * @param index
	 *            the index
	 */
	public void goTo(WdGoToItem item, WdGoToDirection direction, int index) {

		OleAutomation selectionAutomation = getSelectionAutomation();

		oleHelper.invokeFunction(selectionAutomation, "GoTo", item.getValue(),
				direction.getValue(), index);

		selectionAutomation.dispose();
	}

	/**
	 * Insert after.
	 * 
	 * @param text
	 *            the text
	 */
	private void insertAfter(String text) {
		insert("InsertAfter", text, null);
	}

	/**
	 * Insert.
	 * 
	 * @param text
	 *            the text
	 */
	public void insert(String text) {
		insertAfter(text);
	}

	/**
	 * Insert.
	 * 
	 * @param text
	 *            the text
	 * @param style
	 *            the style
	 */
	public void insert(String text, String style) {
		insertAfter(text, style);
	}

	/**
	 * Insert after.
	 * 
	 * @param text
	 *            the text
	 * @param style
	 *            the style
	 */
	private void insertAfter(String text, String style) {
		insert("InsertAfter", text, style);
	}

	/**
	 * Insert.
	 * 
	 * @param insertFunction
	 *            the insert function
	 * @param text
	 *            the text
	 * @param style
	 *            the style
	 */
	private void insert(String insertFunction, String text, String style) {

		int startPosition = determineSelectionStart();
		setStyle(style);

		OleAutomation selectionOleAutomation = getSelectionAutomation();
		oleHelper.invokeFunction(selectionOleAutomation, insertFunction, text);
		selectionOleAutomation.dispose();

		int stopPosition = startPosition + text.length();
		select(startPosition, stopPosition);
		select(stopPosition, stopPosition);
	}

	/**
	 * Sets the superscript.
	 * 
	 * @param selectionAutomation
	 *            the new superscript
	 */
	public void setSuperscript(OleAutomation selectionAutomation) {

		OleAutomation rangeAutomation = determineRange().getAutomation();
		OleAutomation charactersAutomation = oleHelper.getChildAutomation(
				rangeAutomation, "Characters");

		IAutomationVisitor iAutomationVisitor = new IAutomationVisitor() {
			public void visit(OleAutomation characterAutomation) {
				OleAutomation fontAutomation = oleHelper.getChildAutomation(
						characterAutomation, "Font");
				oleHelper.setOleProperty(fontAutomation, "Superscript", true);
				fontAutomation.dispose();
			}
		};
		oleHelper.iterateOverArray(charactersAutomation, iAutomationVisitor);

		charactersAutomation.dispose();
		rangeAutomation.dispose();
		selectionAutomation.dispose();

	}

	/**
	 * Sets the style.
	 * 
	 * @param style
	 *            the new style
	 */
	public void setStyle(String style) {
		OleAutomation selectionAutomation = getSelectionAutomation();
		if (style != null) {
			Map<String, Entry<WdStyleType, Variant>> listStyles = readStyleMap();
			Entry<WdStyleType, Variant> entry = listStyles.get(style);
			Variant value = entry.getValue();
			oleHelper.setOleProperty(selectionAutomation, "Style", value);
		}
		selectionAutomation.dispose();
	}

	/**
	 * Insert before.
	 * 
	 * @param text
	 *            the text
	 */
	private void insertBefore(String text) {
		insert("InsertBefore", text, null);
	}

	/**
	 * The Interface IFormatHelper.
	 */
	public interface IFormatHelper {

		/**
		 * Format.
		 */
		public void format();
	}

	/**
	 * Insert cite source.
	 * 
	 * @param content
	 *            the content
	 * @param label
	 *            the label
	 */
	public void insertCiteSource(String content, String label) {

		IFormatHelper iFormatHelper = new IFormatHelper() {
			public void format() {
				OleAutomation selectionAutomation = getSelectionAutomation();
				setSuperscript(selectionAutomation);
				selectionAutomation.dispose();
			}
		};
		insertBookmark(content, label, iFormatHelper);

	}

	/**
	 * Insert bookmark.
	 * 
	 * @param content
	 *            the content
	 * @param label
	 *            the label
	 */
	public void insertBookmark(String content, String label) {
		insertBookmark(content, label, null);
	}

	/**
	 * Insert bookmark.
	 * 
	 * @param content
	 *            the content
	 * @param label
	 *            the label
	 * @param iFormatHelper
	 *            the i format helper
	 */
	private void insertBookmark(String content, String label,
			IFormatHelper iFormatHelper) {
		int selectionStart = determineSelectionStart();
		insert(content);
		int selectionEnd = determineSelectionStart();

		select(selectionStart, selectionEnd);

		if (iFormatHelper != null) {
			iFormatHelper.format();
		}

		Variant range = determineRange();

		OleAutomation bookmarksAutomation = oleHelper.getOleProperty(
				activeDocumentAutomation, "Bookmarks").getAutomation();
		oleHelper.invokeFunction(bookmarksAutomation, "Add", label, range);
		bookmarksAutomation.dispose();

		select(selectionEnd, selectionEnd);

	}

	/**
	 * Insert new page.
	 */
	private void insertNewPage() {
		OleAutomation selectionAutomation = getSelectionAutomation();
		oleHelper.invokeFunction(selectionAutomation, "InsertNewPage");
		selectionAutomation.dispose();
	}

	/**
	 * Read styles.
	 * 
	 * @return the list
	 */
	public List<String> readStyles() {
		List<String> result = new ArrayList<String>();
		result.addAll(readStyleMap().keySet());
		return result;
	}

	/**
	 * Read styles.
	 * 
	 * @param styleType
	 *            the style type
	 * @return the list
	 */
	public List<String> readStyles(WdStyleType styleType) {
		Map<String, Entry<WdStyleType, Variant>> styleMap = readStyleMap();
		List<String> styleNames = new ArrayList<String>();
		for (Entry<String, Entry<WdStyleType, Variant>> styleEntry : styleMap
				.entrySet()) {
			String styleName = styleEntry.getKey();
			Entry<WdStyleType, Variant> entry = styleEntry.getValue();
			if (styleType.equals(entry.getKey())) {
				styleNames.add(styleName);
			}
		}
		return styleNames;
	}
	
	/**
	 * Read style map.
	 * 
	 * @return the map
	 */
	public Map<String, Entry<WdStyleType, Variant>> readStyleMap() {

		if (styles == null) {
			
			this.styles = new HashMap<String, Entry<WdStyleType, Variant>>();

			OleAutomation stylesAutomation = oleHelper.getOleProperty(
					this.activeDocumentAutomation, "Styles").getAutomation();

			IVariantVisitor iVariantVisitor = new IVariantVisitor() {
				public void visit(final Variant variant) {
					OleAutomation oleAutomation = variant.getAutomation();

					final String nameLocal = oleHelper.getOleProperty(
							oleAutomation, "NameLocal").getString();

					Variant type = oleHelper.getOleProperty(oleAutomation,
							"Type");
					int typeEnum = type.getInt();
					final WdStyleType wdStyleType = WdStyleType.get(typeEnum);

					oleAutomation.dispose();

					Entry<WdStyleType, Variant> styleEntry = new Entry<WdStyleType, Variant>() {
						public WdStyleType getKey() {
							return wdStyleType;
						}

						public Variant getValue() {
							return variant;
						}

						public Variant setValue(Variant arg0) {
							return null;
						}

					};
					styles.put(nameLocal, styleEntry);
				}
			};

			oleHelper.iterateOverArray(stylesAutomation, iVariantVisitor);
			stylesAutomation.dispose();
		}

		return styles;

		/*
		 * Selection.Style = ActiveDocument.Styles("1") Selection.TypeText
		 * Text:="test" Selection.TypeParagraph Selection.TypeText Text:="test"
		 * Selection.TypeParagraph Selection.TypeBackspace
		 * Selection.TypeBackspace Selection.TypeBackspace
		 * Selection.TypeBackspace Selection.TypeBackspace
		 * Selection.TypeBackspace Selection.TypeParagraph Selection.Style =
		 * ActiveDocument.Styles("Textkörper-Zeileneinzug") Selection.TypeText
		 * Text:="test test test test" Selection.TypeParagraph
		 * Selection.TypeText Text:="test test test test"
		 * Selection.TypeParagraph Selection.Style = ActiveDocument.Styles("1")
		 * Selection.TypeText Text:="test" Selection.TypeParagraph
		 * Selection.Style = ActiveDocument.Styles("2.2") Selection.TypeText
		 * Text:="test 1" Selection.TypeParagraph Selection.Style =
		 * ActiveDocument.Styles("2.2") Selection.TypeText Text:="test 2"
		 * Selection.TypeParagraph Selection.Style =
		 * ActiveDocument.Styles("3.3.3") Selection.TypeText Text:="test 2"
		 */
	}

	/**
	 * Open file.
	 * 
	 * @param fileName
	 *            the file name
	 * @throws SWTException
	 *             the sWT exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public void openFile(String fileName) throws SWTException,
			NullPointerException, FileNotFoundException,
			IllegalArgumentException {

		OleAutomation documentsAutomation = null;
		int[] id = null;
		Variant[] arguments = null;
		Variant invokeResult = null;
		try {
			if (fileName == null) {
				throw new NullPointerException("Null value passed to "
						+ "fileName parameters of the openFile() method.");
			}

			checkFileExtension(fileName);

			File fileToPrint = new File(fileName);
			if (!(fileToPrint.exists())) {
				throw new FileNotFoundException("The file " + fileName
						+ "cannot be found.");
			}

			documentsAutomation = oleHelper.getChildAutomation(
					this.wordAutomation, "Documents");

			id = documentsAutomation.getIDsOfNames(new String[] { "Open" });

			if (id == null) {
				throw new SWTException(
						"It was not possible to recover an "
								+ "identifer for the Open method in WordSearchReplace.openFile().");
			}

			arguments = new Variant[1];
			arguments[0] = new Variant(fileName);

			invokeResult = documentsAutomation.invoke(id[0], arguments);

			if (invokeResult == null) {
				throw new SWTException("An error occurred whilst invoking the "
						+ "Open method for the following file: " + fileName
						+ " in WordSearchReplace.openFile().");
			} else {

				this.activeDocumentAutomation = oleHelper.getChildAutomation(
						this.wordAutomation, "ActiveDocument");

			}
		} finally {

			if (documentsAutomation != null) {
				documentsAutomation.dispose();
			}
		}
	}

	/**
	 * Read selection.
	 * 
	 * @return the string
	 */
	private String readSelection() {
		OleAutomation selectionAutomation = getSelectionAutomation();
		Variant textProperty = oleHelper.getOleProperty(selectionAutomation,
				"Text");
		String text = textProperty.getString();
		selectionAutomation.dispose();
		return text;
	}

	/**
	 * Replace.
	 * 
	 * @param searchTerm
	 *            the search term
	 * @param replacementTerm
	 *            the replacement term
	 * @throws SWTException
	 *             the sWT exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 */
	public void replace(String searchTerm, String replacementTerm)
			throws SWTException, NullPointerException {

		OleAutomation selectionFindAutomation = null;
		OleAutomation childAutomation = null;
		Variant[] arguments = null;
		Variant invokeResult = null;
		int[] id = null;
		int[] namedArguments = null;
		boolean success = true;

		if (searchTerm == null) {
			throw new NullPointerException("Null value passed to "
					+ "searchTerm parameter of the replace() method.");
		}

		if (replacementTerm == null) {
			throw new NullPointerException("Null value passed to "
					+ "replacementTerm parameter of the replace() method.");
		}

		childAutomation = oleHelper.getChildAutomation(this.wordAutomation,
				"Selection");

		selectionFindAutomation = oleHelper.getChildAutomation(childAutomation,
				"Find");
		id = selectionFindAutomation
				.getIDsOfNames(new String[] { "ClearFormatting" });
		if (id == null) {
			throw new SWTException(
					"It is not possible to recover an identifier "
							+ "for the ClearFormatting method in WordSearchReplace.replace() "
							+ "when clearing the formatting for the search string.");
		}
		invokeResult = selectionFindAutomation.invoke(id[0]);
		if (invokeResult == null) {
			throw new SWTException("A problem occurred invoking the "
					+ "ClearFormatting method in WordSearchReplace.repace() "
					+ "when clearing formatting for the search string.");
		}

		childAutomation = oleHelper.getChildAutomation(selectionFindAutomation,
				"Replacement");
		id = childAutomation.getIDsOfNames(new String[] { "ClearFormatting" });
		if (id == null) {
			throw new SWTException(
					"It is not possible to recover an identifier "
							+ "for the ClearFormatting method in WordSearchReplace.replace() "
							+ "when clearing the formatting for the replacement string.");
		}
		invokeResult = childAutomation.invoke(id[0]);
		if (invokeResult == null) {
			throw new SWTException("A problem occurred invoking the "
					+ "ClearFormatting method in WordSearchReplace.repace() "
					+ "when clearing formatting for the replacement string.");
		}

		arguments = new Variant[1];
		arguments[0] = new Variant(searchTerm);
		success = oleHelper.setPropertyValue(selectionFindAutomation, "Text",
				arguments);
		if (!success) {
			throw new SWTException(
					"A problem occurred setting the Text "
							+ "property for the search string in WordSearchReplace.replace().");
		}

		childAutomation = oleHelper.getChildAutomation(selectionFindAutomation,
				"Replacement");
		arguments[0] = new Variant(replacementTerm);
		success = oleHelper
				.setPropertyValue(childAutomation, "Text", arguments);
		if (!success) {
			throw new SWTException(
					"A problem occurred setting the Text property"
							+ " for the replacement string in WordSearchReplace.replace().");
		}

		arguments[0] = new Variant(true);
		success = oleHelper.setPropertyValue(selectionFindAutomation,
				"Forward", arguments);
		if (!success) {
			throw new SWTException("A problem occurred setting the Forward "
					+ "property in WordSearchReplace.replace().");
		}

		arguments[0] = new Variant(WordRemoteHandler.WD_FIND_CONTINUE);
		success = oleHelper.setPropertyValue(selectionFindAutomation, "Wrap",
				arguments);
		if (!success) {
			throw new SWTException("A problem occurred setting the Wrap "
					+ "property in WordSearchReplace.replace().");
		}

		arguments[0] = new Variant(false);
		success = oleHelper.setPropertyValue(selectionFindAutomation, "Format",
				arguments);
		if (!success) {
			throw new SWTException("A problem occurred setting the Format "
					+ "property in WordSearchReplace.replace().");
		}

		arguments[0] = new Variant(false);
		success = oleHelper.setPropertyValue(selectionFindAutomation,
				"MatchCase", arguments);
		if (!success) {
			throw new SWTException("A problem occurred setting the MatchCase "
					+ "property in WordSearchReplace.replace().");
		}

		arguments[0] = new Variant(false);
		success = oleHelper.setPropertyValue(selectionFindAutomation,
				"MatchWholeWord", arguments);
		if (!success) {
			throw new SWTException("A problem occurred setting the "
					+ "MatchWholeWord property in WordSearchReplace.replace().");
		}

		arguments[0] = new Variant(false);
		success = oleHelper.setPropertyValue(selectionFindAutomation,
				"MatchWildCards", arguments);
		if (!success) {
			throw new SWTException("A problem occurred setting the "
					+ "MatchWildCards property in WordSearchReplace.replace().");
		}

		arguments[0] = new Variant(false);
		success = oleHelper.setPropertyValue(selectionFindAutomation,
				"MatchSoundsLike", arguments);
		if (!success) {
			throw new SWTException(
					"A problem occurred setting the "
							+ "MatchSoundsLike property in WordSearchReplace.replace().");
		}

		arguments[0] = new Variant(false);
		success = oleHelper.setPropertyValue(selectionFindAutomation,
				"MatchAllWordForms", arguments);
		if (!success) {
			throw new SWTException(
					"A problem occurred setting the "
							+ "MatchAllWordForms property in WordSearchReplace.replace().");
		}

		id = selectionFindAutomation.getIDsOfNames(new String[] { "Execute",
				"Replace" });
		if (id == null) {
			throw new SWTException(
					"It was not possible to recover an identifier "
							+ "for the Execute method in WordSearchReplace.replace().");
		}

		arguments = new Variant[1];
		arguments[0] = new Variant(WordRemoteHandler.WD_REPLACE_ALL);
		namedArguments = new int[1];
		namedArguments[0] = id[1];

		invokeResult = selectionFindAutomation.invoke(id[0], arguments,
				namedArguments);
		if (invokeResult == null) {
			throw new SWTException("A problem occurred trying to invoke the "
					+ "Execute method in WordSearchReplace.replace().");
		}

	}

	/**
	 * Save.
	 * 
	 * @throws SWTException
	 *             the sWT exception
	 */
	public void save() throws SWTException {

		int[] id = null;
		Variant invokeResult = null;

		id = this.activeDocumentAutomation
				.getIDsOfNames(new String[] { "Save" });

		if (id == null) {
			throw new SWTException("Unable to obtain an automation for "
					+ "the Save method in WordSearchReplace.save().");
		}

		invokeResult = this.activeDocumentAutomation.invoke(id[0]);

		if (invokeResult == null) {
			throw new SWTException("A problem occurred invoking the "
					+ "Save method in WordSearchReplace.save().");
		}

	}

	/**
	 * Save as.
	 * 
	 * @param fileName
	 *            the file name
	 * @throws SWTException
	 *             the sWT exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public void saveAs(String fileName) throws SWTException,
			NullPointerException, IllegalArgumentException {

		int[] id = null;
		Variant[] arguments = null;
		Variant invokeResult = null;

		if (fileName == null) {
			throw new NullPointerException("A null value was passed to "
					+ "the fileName parameter of WordSearchReplace.saveAs().");
		}

		if (fileName.length() == 0) {
			throw new NullPointerException(
					"An empty string was passed "
							+ "to the fileName parameter of WordSearchReplace.saveAs().");
		}

		checkFileExtension(fileName);

		id = this.activeDocumentAutomation
				.getIDsOfNames(new String[] { "SaveAs" });

		if (id == null) {
			throw new SWTException("Unable to obtain an automation for "
					+ "the SaveAs method in WordSearchReplace.saveAs().");
		}

		arguments = new Variant[1];
		arguments[0] = new Variant(fileName);

		invokeResult = this.activeDocumentAutomation.invoke(id[0], arguments);

		if (invokeResult == null) {
			throw new SWTException("A problem occurred invoking the "
					+ "SaveAs method in WordSearchReplace.saveAs().");
		}
	}

	/**
	 * Select.
	 * 
	 * @param selectionStart
	 *            the selection start
	 * @param selectionEnd
	 *            the selection end
	 */
	public void select(int selectionStart, int selectionEnd) {

		OleAutomation selectionAutomation = getSelectionAutomation();
		oleHelper.setOleProperty(selectionAutomation, "Start", selectionStart);
		oleHelper.setOleProperty(selectionAutomation, "End", selectionEnd);
		selectionAutomation.dispose();

	}

	/**
	 * Gets the custom document property.
	 * 
	 * @param property
	 *            the property
	 * @return the custom document property
	 * @throws SWTException
	 *             the sWT exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 */
	public String getCustomDocumentProperty(CustomDocumentProperty property)
			throws SWTException, NullPointerException {

		OleAutomation builtInDocumentProperties = oleHelper
				.createOleAutomation(activeDocumentAutomation,
						"CustomDocumentProperties");
		int[] itemId = builtInDocumentProperties
				.getIDsOfNames(new String[] { "Item" });
		Variant result = builtInDocumentProperties.getProperty(itemId[0],
				new Variant[] { new Variant(property.getKey()) });

		builtInDocumentProperties.dispose();

		return result.getString();
	}

	/**
	 * Sets the custom document property.
	 * 
	 * @param property
	 *            the property
	 * @param value
	 *            the value
	 * @throws SWTException
	 *             the sWT exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 */
	public void setCustomDocumentProperty(CustomDocumentProperty property,
			String value) throws SWTException, NullPointerException {

		OleAutomation builtInDocumentProperties = oleHelper
				.createOleAutomation(activeDocumentAutomation,
						"CustomDocumentProperties");
		int[] itemId = builtInDocumentProperties
				.getIDsOfNames(new String[] { "Item" });
		builtInDocumentProperties.setProperty(itemId[0], new Variant[] {
				new Variant(property.getKey()), new Variant(value) });

		builtInDocumentProperties.dispose();
	}

	/**
	 * Sets the document property.
	 * 
	 * @param property
	 *            the property
	 * @param value
	 *            the value
	 * @throws SWTException
	 *             the sWT exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 */
	public void setDocumentProperty(WdBuiltInProperty property, String value)
			throws SWTException, NullPointerException {

		OleAutomation builtInDocumentProperties = oleHelper
				.createOleAutomation(activeDocumentAutomation,
						"BuiltInDocumentProperties");
		int[] itemId = builtInDocumentProperties
				.getIDsOfNames(new String[] { "Item" });
		builtInDocumentProperties.setProperty(itemId[0], new Variant[] {
				new Variant(property.getValue()), new Variant(value) });

		builtInDocumentProperties.dispose();

	}

	/**
	 * Update.
	 */
	public void update() {

		// http://www.vbaexpress.com/kb/getarticle.php?kb_id=459

		updateFields();
		updateFields(DocumentPart.Header);
		updateFields(DocumentPart.Footer);
		updateTableOfContents();

		// refreshView();

	}

	/**
	 * Update fields.
	 */
	private void updateFields() {
		OleAutomation fieldsAutomation = oleHelper.createOleAutomation(
				activeDocumentAutomation, "Fields");
		int[] id = fieldsAutomation.getIDsOfNames(new String[] { "Update" });
		fieldsAutomation.invoke(id[0]);

		OleAutomation storyRangesAutomation = oleHelper.createOleAutomation(
				activeDocumentAutomation, "StoryRanges");

		oleHelper.iterateOverArray(storyRangesAutomation, "Fields", "Update");
		storyRangesAutomation.dispose();
	}

	/**
	 * Update table of contents.
	 */
	private void updateTableOfContents() {
		OleAutomation tableOfContentsAutomation = oleHelper
				.createOleAutomation(activeDocumentAutomation,
						"TablesOfContents");
		IAutomationVisitor updateTableOfContentsVisitor = new IAutomationVisitor() {
			public void visit(OleAutomation oleAutomation) {
				oleHelper.invokeFunction(oleAutomation, "Update", false);
			}
		};

		oleHelper.iterateOverArray(tableOfContentsAutomation,
				updateTableOfContentsVisitor);
		tableOfContentsAutomation.dispose();
	}

	/**
	 * Refresh view.
	 */
	public void refreshView() {
		OleAutomation documentsAutomation = oleHelper.getOleProperty(
				wordAutomation, "Documents").getAutomation();
		int collectionCount = oleHelper
				.determineCollectionCount(documentsAutomation);
		documentsAutomation.dispose();

		if (collectionCount > 0) {
			OleAutomation applicationAutomation = oleHelper.getOleProperty(
					wordAutomation, "Application").getAutomation();
			oleHelper.setOleProperty(applicationAutomation, "ScreenUpdating",
					false);

			OleAutomation activeWindowAutomation = oleHelper.getOleProperty(
					wordAutomation, "ActiveWindow").getAutomation();
			OleAutomation viewAutomation = oleHelper.getOleProperty(
					activeWindowAutomation, "View").getAutomation();

			int viewId = oleHelper.getOleProperty(viewAutomation, "Type")
					.getInt();
			oleHelper.setOleProperty(viewAutomation, "Type",
					WdViewType.WD_MASTER_VIEW);
			oleHelper.setOleProperty(viewAutomation, "Type",
					WdViewType.WD_PRINT_PREVIEW);
			oleHelper.setOleProperty(viewAutomation, "Type", viewId);

			viewAutomation.dispose();
			activeWindowAutomation.dispose();

			oleHelper.setOleProperty(applicationAutomation, "ScreenUpdating",
					true);
			applicationAutomation.dispose();
		}
	}

	/**
	 * Update fields.
	 * 
	 * @param part
	 *            the part
	 */
	private void updateFields(final DocumentPart part) {
		String numberOfPagesStr = getDocumentProperty(WdBuiltInProperty.WD_PROPERTY_PAGES);
		int numberOfPages = Integer.parseInt(numberOfPagesStr);

		if (numberOfPages >= 1) {
			OleAutomation sectionsOleAutomation = oleHelper
					.createOleAutomation(activeDocumentAutomation, "Sections");
			int collectionCount = oleHelper
					.determineCollectionCount(sectionsOleAutomation);

			oleHelper.iterateOverArray(sectionsOleAutomation,
					new IAutomationVisitor() {

						public void visit(OleAutomation oleAutomation) {

							OleAutomation documentPartAutomation = oleHelper
									.getOleProperty(oleAutomation,
											part.getPropertyName())
									.getAutomation();

							oleHelper.iterateOverArray(documentPartAutomation,
									new IAutomationVisitor() {
										public void visit(
												OleAutomation oleAutomation) {
											Variant rangeProperty = oleHelper
													.getOleProperty(
															oleAutomation,
															"Range");
											OleAutomation rangeAutomation = rangeProperty
													.getAutomation();
											Variant fieldsProperty = oleHelper
													.getOleProperty(
															rangeAutomation,
															"Fields");
											OleAutomation fieldsAutomation = fieldsProperty
													.getAutomation();
											if (fieldsAutomation != null) {
												oleHelper.invokeFunction(
														fieldsAutomation,
														"Update");
											}
										}

									});

							oleAutomation.dispose();
						}
					});
			sectionsOleAutomation.dispose();
		}
	}

	/**
	 * Sets the heading style.
	 * 
	 * @param arg0
	 *            the new heading style
	 */
	public void setHeadingStyle(int arg0) {

		// "Überschrift 1;1"
		String headingStyle = wordSettings != null ? wordSettings.getH1Style()
				: null;
		headingStyle = headingStyle != null ? headingStyle : "Überschrift 1;1";
		switch (arg0) {
		case 1:
			headingStyle = wordSettings != null ? wordSettings.getH1Style()
					: null;
			headingStyle = headingStyle != null && !headingStyle.isEmpty() ? headingStyle
					: "Überschrift 1;1";
			break;
		case 2:
			headingStyle = wordSettings != null ? wordSettings.getH2Style()
					: null;
			headingStyle = headingStyle != null && !headingStyle.isEmpty() ? headingStyle
					: "Überschrift 2;2.2";
			break;
		case 3:
			headingStyle = wordSettings != null ? wordSettings.getH3Style()
					: null;
			headingStyle = headingStyle != null && !headingStyle.isEmpty() ? headingStyle
					: "Überschrift 3;3.3.3";
			break;
		case 4:
			headingStyle = wordSettings != null ? wordSettings.getH3Style()
					: null;
			headingStyle = headingStyle != null && !headingStyle.isEmpty() ? headingStyle
					: "Überschrift 4;4.4.4.4";
			break;
		}

		setStyle(headingStyle);
	}
	
	/**
	 * Sets the default style.
	 */
	public void setDefaultStyle() {

		String defaultStyle = wordSettings != null ? wordSettings
				.getDefaultStyle() : null;
		defaultStyle = defaultStyle != null && !defaultStyle.isEmpty() ? defaultStyle
				: "Standard";

		setStyle(defaultStyle);
	}

	/**
	 * Sets the bullet list style.
	 */
	public void setBulletListStyle() {

		String bulletListStyle = wordSettings != null ? wordSettings
				.getBulletListStyle() : null;
		bulletListStyle = bulletListStyle != null && !bulletListStyle.isEmpty() ? bulletListStyle
				: "Aufzählung 01";

		setStyle(bulletListStyle);
	}

	/**
	 * Sets the numeric list style.
	 */
	public void setNumericListStyle() {

		String numericListStyle = wordSettings != null ? wordSettings
				.getNumericListStyle() : null;
		numericListStyle = numericListStyle != null
				&& !numericListStyle.isEmpty() ? numericListStyle
				: "Listennummer";

		setStyle(numericListStyle);
	}

	/**
	 * Insert cross reference.
	 * 
	 * @param key
	 *            the key
	 */
	public void insertCrossReference(ReferenceHolder key) {

		ReferenceType referenceType = key.getReferenceType();
		OleAutomation selectionAutomation = getSelectionAutomation();

		String indexStr = String.format("%s_%s", referenceType.name()
				.toLowerCase(), key.getIndex());

		oleHelper.invokeFunction(selectionAutomation, "InsertCrossReference",
				WdReferenceType.WD_REF_TYPE_BOOKMARK,
				WdReferenceKind.WD_CONTENT_TEXT, indexStr, true);

		selectionAutomation.dispose();
	}

	/**
	 * Insert link.
	 * 
	 * @param ahref
	 *            the ahref
	 * @param name
	 *            the name
	 */
	public void insertLink(String ahref, String name) {

		int selectionStart = determineSelectionStart();
		insert(name);
		int selectionEnd = determineSelectionStart();

		select(selectionStart, selectionEnd);

		Variant range = determineRange();

		OleAutomation hyperlinksAutomation = oleHelper.getChildAutomation(
				activeDocumentAutomation, "Hyperlinks");
		oleHelper.invokeFunction(hyperlinksAutomation, "Add", range, ahref);
		hyperlinksAutomation.dispose();

	}

	/**
	 * Insert picture.
	 * 
	 * @param src
	 *            the src
	 * @param alt
	 *            the alt
	 * @return the int
	 */
	public int insertPicture(String src, String alt) {

		String caption = String.format("Abbildung %s", pictureIdx + 1);

		OleAutomation selectionAutomation = getSelectionAutomation();

		OleAutomation inlineShapesAutomation = oleHelper.getChildAutomation(
				selectionAutomation, "InlineShapes");

		Variant shapeVariant = oleHelper.invokeFunction(inlineShapesAutomation,
				"AddPicture", src, false, true);
		OleAutomation shapeAutomation = shapeVariant.getAutomation();

		if (alt != null) {
			oleHelper.setOleProperty(shapeAutomation, "AlternativeText", alt);
		}

		oleHelper.invokeFunction(shapeAutomation, "Select");

		shapeAutomation.dispose();

		oleHelper.invokeFunction(selectionAutomation, "InsertCaption", -1);

		inlineShapesAutomation.dispose();
		selectionAutomation.dispose();

		pictureIdx++;

		return pictureIdx;
	}

	/**
	 * Gets the reference type entries.
	 * 
	 * @param cites
	 *            the cites
	 * @param linkTypes
	 *            the link types
	 * @return the reference type entries
	 */
	public Map<Integer, ReferenceHolder> getReferenceTypeEntries(
			final Map<String, ReferenceHolder> cites,
			ReferenceType... linkTypes) {
		Map<Integer, ReferenceHolder> citeList = Maps.newHashMap();
		for (Entry<String, ReferenceHolder> cite : cites.entrySet()) {
			ReferenceHolder value = cite.getValue();
			for (ReferenceType linkType : linkTypes) {
				if (linkType.equals(value.getReferenceType())) {
					Integer index = value.getIndex();
					citeList.put(index, value);
				}
			}
		}
		return citeList;
	}

	/**
	 * Gets the max index.
	 * 
	 * @param references
	 *            the references
	 * @param linkType
	 *            the link type
	 * @return the max index
	 */
	public int getMaxIndex(final Map<String, ReferenceHolder> references,
			ReferenceType linkType) {
		int maxIndex = 0;
		Map<Integer, ReferenceHolder> linkTypeEntries = getReferenceTypeEntries(
				references, linkType);
		Set<Integer> keySet = linkTypeEntries.keySet();
		if (!keySet.isEmpty()) {
			maxIndex = Collections.max(keySet);
		}
		return maxIndex;
	}

	/**
	 * Gets the max src index.
	 * 
	 * @param references
	 *            the references
	 * @return the max src index
	 */
	public int getMaxSrcIndex(Map<String, ReferenceHolder> references) {
		int maxReferenceNumber = 0;
		List<Integer> referenceList = new ArrayList<Integer>();
		for (Entry<String, ReferenceHolder> reference : references.entrySet()) {
			ReferenceHolder value = reference.getValue();
			referenceList.addAll(value.getSrcIds());
		}
		if (!referenceList.isEmpty()) {
			maxReferenceNumber = Collections.max(referenceList);
		}
		return maxReferenceNumber;
	}

	/**
	 * Fill references.
	 * 
	 * @param references
	 *            the references
	 * @param headers
	 *            the headers
	 */
	public void fillReferences(Map<String, ReferenceHolder> references,
			Map<String, Integer> headers) {

		Map<Integer, ReferenceHolder> chapterEntries = getReferenceTypeEntries(
				references, ReferenceType.CHAPTER);
		for (Entry<Integer, ReferenceHolder> chapterEntry : chapterEntries
				.entrySet()) {
			ReferenceHolder value = chapterEntry.getValue();
			String content = value.getContent();
			Integer headerIndex = headers.get(content);
			if (headerIndex != null) {
				value.setIndex(headerIndex);
			}
		}

		int start = determineSelectionStart();
		for (ReferenceType linkType : ReferenceType.values()) {

			Map<Integer, ReferenceHolder> referenceList = getReferenceTypeEntries(
					references, linkType);

			Set<Integer> keySet = referenceList.keySet();
			List<Integer> indexList = new ArrayList<Integer>();
			indexList.addAll(keySet);
			Collections.sort(indexList);

			OleAutomation bookmarksAutomation = oleHelper.getChildAutomation(
					activeDocumentAutomation, "Bookmarks");
			for (Integer index : indexList) {

				ReferenceHolder key = referenceList.get(index);

				for (int srcId : key.getSrcIds()) {

					OleAutomation bookmarkAutomation = oleHelper
							.invokeFunction(bookmarksAutomation, "Item",
									String.format("src_%s", srcId))
							.getAutomation();
					OleAutomation rangeAutomation = oleHelper
							.getChildAutomation(bookmarkAutomation, "Range");
					int end = oleHelper.getOleProperty(rangeAutomation, "End")
							.getInt();
					rangeAutomation.dispose();
					bookmarkAutomation.dispose();

					select(end, end);
					insertCrossReference(key);
				}

			}
			bookmarksAutomation.dispose();
			select(start, start);

		}
	}

	/**
	 * Delete.
	 * 
	 * @param wdUnit
	 *            the wd unit
	 * @param length
	 *            the length
	 */
	public void delete(WdUnits wdUnit, int length) {
		OleAutomation selectionAutomation = getSelectionAutomation();
		oleHelper.invokeFunction(selectionAutomation, "Delete",
				wdUnit.getValue(), length);
		selectionAutomation.dispose();
	}

	/**
	 * Sets the italic.
	 * 
	 * @param selectionAutomation
	 *            the new italic
	 */
	public void setItalic(OleAutomation selectionAutomation) {

		OleAutomation rangeAutomation = determineRange().getAutomation();
		OleAutomation charactersAutomation = oleHelper.getChildAutomation(
				rangeAutomation, "Characters");

		IAutomationVisitor iAutomationVisitor = new IAutomationVisitor() {
			public void visit(OleAutomation characterAutomation) {
				OleAutomation fontAutomation = oleHelper.getChildAutomation(
						characterAutomation, "Font");
				oleHelper.setOleProperty(fontAutomation, "Italic", true);
				fontAutomation.dispose();
			}
		};
		oleHelper.iterateOverArray(charactersAutomation, iAutomationVisitor);

		charactersAutomation.dispose();
		rangeAutomation.dispose();
		selectionAutomation.dispose();
	}

	/**
	 * Sets the bold.
	 * 
	 * @param selectionAutomation
	 *            the new bold
	 */
	public void setBold(OleAutomation selectionAutomation) {
		OleAutomation rangeAutomation = determineRange().getAutomation();
		OleAutomation charactersAutomation = oleHelper.getChildAutomation(
				rangeAutomation, "Characters");

		IAutomationVisitor iAutomationVisitor = new IAutomationVisitor() {
			public void visit(OleAutomation characterAutomation) {
				OleAutomation fontAutomation = oleHelper.getChildAutomation(
						characterAutomation, "Font");
				oleHelper.setOleProperty(fontAutomation, "Bold", true);
				fontAutomation.dispose();
			}
		};
		oleHelper.iterateOverArray(charactersAutomation, iAutomationVisitor);

		charactersAutomation.dispose();
		rangeAutomation.dispose();
		selectionAutomation.dispose();

	}

	/**
	 * Comment.
	 * 
	 * @param selectionStart
	 *            the selection start
	 * @param selectionEnd
	 *            the selection end
	 * @param comment
	 *            the comment
	 */
	public void comment(int selectionStart, int selectionEnd, String comment) {

		select(selectionStart, selectionEnd);

		Variant range = determineRange();
		OleAutomation selectionAutomation = getSelectionAutomation();

		OleAutomation comments = oleHelper.getChildAutomation(
				selectionAutomation, "Comments");
		oleHelper.invokeFunction(comments, "Add", range, comment);
		comments.dispose();

		selectionAutomation.dispose();

	}

	/*
	 * insert image Selection.InlineShapes.AddPicture "C:sample.bmp", False,
	 * True
	 * http://msdn.microsoft.com/en-us/library/aa171535%28v=office.11%29.aspx
	 */

	// private void setDocumentProperty(
	// DocumentPropertiesCollection wd_docProperties,
	// DocumentProperty property, String propertyValue) {
	//
	// switch(property) {
	//
	// case Application_Name : wd_docProperties.setAppName(propertyValue);
	// break;
	// case Author : wd_docProperties.setAuthor(propertyValue); break;
	// case Category : wd_docProperties.setCategory(propertyValue); break;
	// case Company : wd_docProperties.setCompany(propertyValue); break;
	// case Creation_Date : wd_docProperties.setCreated(value); break;
	// case Format : wd_docProperties.setFormat(propertyValue); break;
	//
	// }
	// }

}