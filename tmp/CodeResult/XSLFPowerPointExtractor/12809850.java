
package net.ontopia.topicmaps.classify;

import java.io.*;
import java.util.*;

import net.ontopia.xml.*;
import net.ontopia.utils.*;

import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;

/**
 * INTERNAL: A format module for the OOXML PresentationML format.
 */
public class OOXMLPowerpointFormatModule implements FormatModuleIF {
  protected String[] extensions = new String[] {".pptx"};
  // these are really magic bytes for all zip files...
  protected byte[] magicBytes = new byte[] {
    (byte) 0x50, (byte) 0x4B, (byte) 0x03, (byte) 0x04 };
  
  public boolean matchesContent(ClassifiableContentIF cc) {
    return false;
  }

  public boolean matchesIdentifier(ClassifiableContentIF cc) {
    boolean matches = FormatModule.matchesExtension(cc.getIdentifier(), extensions);
    if (!matches) return false;
    // name matches, then check office magic bytes
    return FormatModule.startsWith(cc.getContent(), magicBytes);
  }

  public void readContent(ClassifiableContentIF cc, TextHandlerIF handler) {
    try {
      OPCPackage opc = OPCPackage.open(new ByteArrayInputStream(cc.getContent()));
      XSLFPowerPointExtractor extractor = new XSLFPowerPointExtractor(opc);
      String s = extractor.getText();
      char[] c = s.toCharArray();
      handler.startRegion("document");
      handler.text(c, 0, c.length);
      handler.endRegion();
    } catch (Exception e) {
      throw new OntopiaRuntimeException(e);
    }    
  }
}
