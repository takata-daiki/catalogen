package extraction;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import uk.ac.cam.ch.wwmm.chemicaltagger.ChemistrySentenceParser;
import uk.ac.cam.ch.wwmm.oscar3.flow.OscarFlow;
import uk.ac.cam.ch.wwmm.ptclib.scixml.TextToSciXML;

public class Paragraph {

	private Element sourceElement;
	private Document parsedDoc;
	private String tagged;
	private Document sourceDocument;
	
	Paragraph(Element p) {
		sourceDocument = p.getDocument();
		sourceElement = (Element) p.copy();
//		checkSourceElement();
		runChemicalTagger();
	}

	private void runChemicalTagger() {
		 tagged = Utils.tagString(getText());
		
		InputStream in;
		try {
			in = new ByteArrayInputStream(tagged.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		ChemistrySentenceParser chemistrySentenceParser = new ChemistrySentenceParser(in);
		
		parsedDoc = chemistrySentenceParser.parseTagsToDocument();
	}

	private void checkSourceElement() {
		if (sourceElement.getAttribute("id") == null) {
			throw new RuntimeException("paragraph must have an id attribute");
		}
		if (sourceElement.getAttribute("num") == null) {
			throw new RuntimeException("paragraph must have a num attribute");
		}
	}

	public static List<Paragraph> createParagraphsFromStep(Step step) {
//		Elements paras = step.getSourceElement().getChildElements("p");
		List <Element> paras = Step.getNonChemistryParagraphs(step.getSourceElement());
		List <Paragraph> list = new ArrayList<Paragraph>(paras.size());
		for (int i = 0; i < paras.size(); i++) {
			list.add(new Paragraph(paras.get(i)));
		}
		return list;
	}

	public Element getSourceElement() {
		return sourceElement;
	}

	
	/**
	 * Not yet properly implemented
	 * 
	 * @return sourceElement.getValue()
	 */
	public String getText() {
		//TODO write a proper method
		String text = sourceElement.getValue();
		return text.replaceAll("\\s+", " ");
	}

	public Document getParsedDoc() {
		return parsedDoc;
	}

	String getTaggedText() {
		return tagged;
	}

	public Document getSourceDocument() {
		return sourceDocument;
	}

}
