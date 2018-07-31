
/* First created by JCasGen Thu Dec 17 13:23:37 GMT 2009 */
package org.u_compare.shared.document.text;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

/** 
 * Updated by JCasGen Wed Jan 13 13:50:59 GMT 2010
 * XML source: /home/keybo/workspace/uima-oscar-play/desc/OSCARNameRecognizer.xml
 * @generated */
public class Paragraph extends TextBody {
	/**
	 * @generated
	 * @ordered
	 */
	public final static int typeIndexID = JCasRegistry.register(Paragraph.class);
	/**
	 * @generated
	 * @ordered
	 */
	public final static int type = typeIndexID;

	/** @generated */
	public int getTypeIndexID() {return typeIndexID;}
 
	/**
	 * Never called. Disable default constructor
	 * 
	 * @generated
	 */
	protected Paragraph() {}
    
	/**
	 * Internal - constructor used by generator
	 * 
	 * @generated
	 */
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

	/**
	 * <!-- begin-user-doc --> Write your own initialization here <!--
	 * end-user-doc -->
	 * 
	 * @generated modifiable
	 */
	private void readObject() {
	}

}
