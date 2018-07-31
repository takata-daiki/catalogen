/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package io.process.statebox.pnml.ptnet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Offset</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link io.process.statebox.pnml.ptnet.Offset#getContainerAnnotationGraphics <em>Container Annotation Graphics</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public interface Offset extends Coordinate
{
	/**
	 * Returns the value of the '<em><b>Container Annotation Graphics</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link io.process.statebox.pnml.ptnet.AnnotationGraphics#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container Annotation Graphics</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container Annotation Graphics</em>' container reference.
	 * @see #setContainerAnnotationGraphics(AnnotationGraphics)
	 * @see io.process.statebox.pnml.ptnet.AnnotationGraphics#getOffset
	 * @generated
	 */
	AnnotationGraphics getContainerAnnotationGraphics();

	/**
	 * Sets the value of the '{@link io.process.statebox.pnml.ptnet.Offset#getContainerAnnotationGraphics <em>Container Annotation Graphics</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container Annotation Graphics</em>' container reference.
	 * @see #getContainerAnnotationGraphics()
	 * @generated
	 */
	void setContainerAnnotationGraphics(AnnotationGraphics value);

} // Offset
