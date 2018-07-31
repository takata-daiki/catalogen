

/* First created by JCasGen Tue Sep 07 15:24:52 BST 2010 */
package org.u_compare.shared.document.text;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Sep 07 15:27:17 BST 2010
 * XML source: /home/lezan/galWorkspace/oscar-patternrecogniser/src/main/resources/uk/ac/nactem/cheta/uimaDescriptor/OscarPER1.xml
 * @generated */
public class Paragraph extends TextBody {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Paragraph.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Paragraph() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Paragraph(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Paragraph(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Paragraph(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
}

    