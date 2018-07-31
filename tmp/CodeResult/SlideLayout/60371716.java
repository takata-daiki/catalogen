/************************************************************************
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * Copyright 2011 IBM. All rights reserved.
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
package org.odftoolkit.simple.draw;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;
import org.odftoolkit.simple.PresentationDocument;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.presentation.Slide;
import org.odftoolkit.simple.presentation.Slide.SlideLayout;
import org.odftoolkit.simple.style.StyleTypeDefinitions.SupportedLinearMeasure;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.list.List;
import org.odftoolkit.simple.utils.ResourceUtilities;

public class TextBoxTest {

	@Test
	public void testAddTextbox() {
		String content = "This is a text box";
		try {
			TextDocument textDoc = TextDocument.newTextDocument();
			Paragraph p = textDoc.addParagraph("abc");
			Textbox box = p.addTextbox(new FrameRectangle(1, 1, 2, 3, SupportedLinearMeasure.IN));
			box.setName("box1");
			box.setTextContent(content);
			textDoc.save(ResourceUtilities.newTestOutputFile("textsample.odt"));

			textDoc = TextDocument.loadDocument(ResourceUtilities.getTestResourceAsStream("textsample.odt"));
			Paragraph p1 = textDoc.getParagraphByIndex(0, true);
			Assert.assertNotNull(p1);
			Textbox aBox = p1.getTextboxByName("box1");
			Assert.assertNotNull(aBox);
			String textContent = aBox.getTextContent();
			Assert.assertEquals(content, textContent);

			PresentationDocument presentDoc = PresentationDocument.newPresentationDocument();
			Slide slide1 = presentDoc.newSlide(1, "slide1", SlideLayout.TITLE_PLUS_2_TEXT_BLOCK);
			Textbox titleTextbox = slide1.getTextboxByUsage(PresentationDocument.PresentationClass.TITLE).get(0);
			Assert.assertNotNull(titleTextbox);
			titleTextbox.setTextContent("My Title1");
			java.util.List<Textbox> boxList = slide1.getTextboxByUsage(PresentationDocument.PresentationClass.OUTLINE);
			Assert.assertNotNull(boxList);
			Assert.assertEquals(2, boxList.size());
			Textbox leftTextbox = boxList.get(0);
			List list1 = leftTextbox.addList();
			list1.addItem("Test outline1");
			list1.addItem("Test outline2");

			presentDoc.save(ResourceUtilities.newTestOutputFile("abc.odp"));
		} catch (Exception e) {
			Logger.getLogger(TextBoxTest.class.getName()).log(Level.SEVERE, null, e);
			Assert.fail();
		}
	}

	@Test
	public void testGetRemoveTextbox() {
		int count = 0;
		try {
			TextDocument doc = TextDocument.loadDocument(ResourceUtilities.getTestResourceAsStream("SampleBox.odt"));
			count = countAllTextbox(doc);
			Assert.assertEquals(8, count);

			int c = 0;
			Paragraph p = doc.getParagraphByIndex(1, true);
			Iterator<Textbox> boxIter = p.getTextboxIterator();
			while (boxIter.hasNext()) {
				Textbox box = boxIter.next();
				if (p.removeTextbox(box))
					;
				c++;
			}

			count = countAllTextbox(doc);
			Assert.assertEquals(8 - c, count);
			doc.save(ResourceUtilities.newTestOutputFile("SampleBoxOutput.odt"));
		} catch (Exception e) {
			Logger.getLogger(TextBoxTest.class.getName()).log(Level.SEVERE, null, e);
			Assert.fail();
		}

	}
	
	private int countAllTextbox(TextDocument doc) {
		int count = 0;
		Iterator<Paragraph> pIter = doc.getParagraphIterator();
		while (pIter.hasNext()) {
			Paragraph p = pIter.next();
			Iterator<Textbox> boxIter = p.getTextboxIterator();
			while (boxIter.hasNext()) {
				Textbox box = boxIter.next();
				System.out.println(box.getTextContent());
				count++;
			}
		}
		return count;
	}
}
