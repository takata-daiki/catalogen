/*
 * This example was based on a sample code posted to the iText mailing
 * list by Matthias Uhler; the example was rewritten by Bruno Lowagie,
 * author of the book 'iText in Action' by Manning Publications (ISBN: 1932394796).
 * You can use this example as inspiration for your own applications.
 * The following license applies:
 * http://www.1t3xt.com/about/copyright/index.php?page=MIT
 */

package questions.markedcontent;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfLayer;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfStructureElement;
import com.lowagie.text.pdf.PdfStructureTreeRoot;
import com.lowagie.text.pdf.PdfWriter;

public class ObjectData {
	
	public static final String RESULT = "results/questions/markedcontent/objectdata.pdf";
	
	public static void main(String[] args) {
		Document document = new Document(PageSize.A5.rotate());
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
			writer.setTagged();

			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PdfStructureTreeRoot tree = writer.getStructureTreeRoot();
			PdfStructureElement se = new PdfStructureElement(tree, new PdfName("Figure"));			
			PdfStructureElement element = new PdfStructureElement(se, new PdfName("Element"));
			PdfDictionary userproperties = new PdfDictionary();
			userproperties.put(PdfName.O, PdfName.USERPROPERTIES);
			userproperties.put(PdfName.S, new PdfName("Figure"));
			PdfArray properties = new PdfArray();
			PdfDictionary property1 = new PdfDictionary();
			property1.put(PdfName.N, new PdfString("Name1"));
			property1.put(PdfName.V, new PdfString("Value1"));			
			properties.add(property1);
			PdfDictionary property2 = new PdfDictionary();
			property2.put(PdfName.N, new PdfString("Name2"));
			property2.put(PdfName.V, new PdfString("Value2"));			
			properties.add(property2);
			PdfDictionary property3 = new PdfDictionary();
			property3.put(PdfName.N, new PdfString("Name3"));
			property3.put(PdfName.V, new PdfString("Value3"));			
			properties.add(property3);
			userproperties.put(PdfName.P, properties);
			element.put(PdfName.A, userproperties);

			PdfLayer lay1 = new PdfLayer("My object", writer);

			cb.beginMarkedContentSequence(element);
			cb.beginLayer(lay1);
			cb.setColorFill(Color.BLUE);
			cb.rectangle(50, 50, 200, 200);
			cb.fill();
			cb.endLayer();
			cb.endMarkedContentSequence();


		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		document.close();

	}

}
