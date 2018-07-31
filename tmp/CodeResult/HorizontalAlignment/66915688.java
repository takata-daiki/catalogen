package net.sf.anathema.hero.sheet.pdf.encoder.graphics;

import com.itextpdf.text.Element;

public enum HorizontalAlignment {

  Left(Element.ALIGN_LEFT), Right(Element.ALIGN_RIGHT), Center(Element.ALIGN_CENTER), Justified(Element.ALIGN_JUSTIFIED);
  private int pdfAlignment;

  private HorizontalAlignment(int pdfAlignment) {
    this.pdfAlignment = pdfAlignment;
  }

  public int getPdfAlignment() {
    return pdfAlignment;
  }
}
