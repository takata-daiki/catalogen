/**
 * Shape abstract class represents Shape type
 */

import java.io.Serializable;

/**
 * @author Admin
 *
 */
public abstract class Shape implements Serializable {
	/**
	 * shape bit pattern represents the shape
	 */
	private Integer[][] shapeBitPattern;

	/**
	 * @return the shapeBitPattern
	 *
	 */
	public Integer[][] getShapeBitPattern() {
		// begin-user-code
		return shapeBitPattern;
		// end-user-code
	}

	/**
	 * @param theShapeBitPattern
	 *
	 */
	public void setShapeBitPattern(Integer[][] theShapeBitPattern) {
		// begin-user-code
		shapeBitPattern = theShapeBitPattern;
		// end-user-code
	}

	/**
	 * @generated "UML to Java V5.0
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Integer shapeID;

	/**
	 * @return the shapeID
	 * @generated "UML to Java V5.0
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Integer getShapeID() {
		// begin-user-code
		return shapeID;
		// end-user-code
	}

	/**
	 * @param theShapeID
	 *            the shapeID to set
	 * @generated "UML to Java V5.0
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setShapeID(Integer theShapeID) {
		// begin-user-code
		shapeID = theShapeID;
		// end-user-code
	}

	/**
	 * @generated "UML to Java V5.0
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Integer rotationFactor;

	/**
	 * @return the rotationFactor
	 * @generated "UML to Java V5.0
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Integer getRotationFactor() {
		// begin-user-code
		return rotationFactor;
		// end-user-code
	}

	/**
	 * @param theRotationFactor
	 *            the rotationFactor to set
	 * @generated "UML to Java V5.0
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setRotationFactor(Integer theRotationFactor) {
		// begin-user-code
		rotationFactor = theRotationFactor;
		// end-user-code
	}

}