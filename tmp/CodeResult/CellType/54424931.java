
/* First created by JCasGen Thu Dec 17 13:23:36 GMT 2009 */
package org.u_compare.shared.semantic.bio;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.u_compare.shared.semantic.NamedEntity;

/**
 * Updated by JCasGen Thu Dec 17 13:23:36 GMT 2009 XML source:
 * /Users/ojd20/Documents/gal-workspace/uima-play/desc/OSCARTypeSystem.xml
 * 
 * @generated
 */
public class CellType extends NamedEntity {
	/**
	 * @generated
	 * @ordered
	 */
	public final static int typeIndexID = JCasRegistry.register(CellType.class);
	/**
	 * @generated
	 * @ordered
	 */
	public final static int type = typeIndexID;

	/** @generated */
	public int getTypeIndexID() {
		return typeIndexID;
	}

	/**
	 * Never called. Disable default constructor
	 * 
	 * @generated
	 */
	protected CellType() {
	}

	/**
	 * Internal - constructor used by generator
	 * 
	 * @generated
	 */
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

	/**
	 * <!-- begin-user-doc --> Write your own initialization here <!--
	 * end-user-doc -->
	 * 
	 * @generated modifiable
	 */
	private void readObject() {
	}

}
