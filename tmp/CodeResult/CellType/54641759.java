

/* First created by JCasGen Tue Sep 07 15:24:52 BST 2010 */
package org.u_compare.shared.semantic.bio;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.u_compare.shared.semantic.NamedEntity;


/** 
 * Updated by JCasGen Tue Sep 07 15:27:15 BST 2010
 * XML source: /home/lezan/galWorkspace/oscar-patternrecogniser/src/main/resources/uk/ac/nactem/cheta/uimaDescriptor/OscarPER1.xml
 * @generated */
public class CellType extends NamedEntity {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(CellType.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected CellType() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public CellType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public CellType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public CellType(JCas jcas, int begin, int end) {
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

    