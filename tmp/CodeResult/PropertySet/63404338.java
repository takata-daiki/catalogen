/**
 */
package org.cmdbuild.archimate.model.cmdb;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.cmdbuild.archimate.model.cmdb.PropertySet#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.cmdbuild.archimate.model.cmdb.PropertySet#getLocalName <em>Local Name</em>}</li>
 *   <li>{@link org.cmdbuild.archimate.model.cmdb.PropertySet#getAny <em>Any</em>}</li>
 *   <li>{@link org.cmdbuild.archimate.model.cmdb.PropertySet#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.cmdbuild.archimate.model.cmdb.CmdbPackage#getPropertySet()
 * @model
 * @generated
 */
public interface PropertySet extends EObject {
	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see org.cmdbuild.archimate.model.cmdb.CmdbPackage#getPropertySet_Namespace()
	 * @model
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link org.cmdbuild.archimate.model.cmdb.PropertySet#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

	/**
	 * Returns the value of the '<em><b>Local Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Name</em>' attribute.
	 * @see #setLocalName(String)
	 * @see org.cmdbuild.archimate.model.cmdb.CmdbPackage#getPropertySet_LocalName()
	 * @model
	 * @generated
	 */
	String getLocalName();

	/**
	 * Sets the value of the '{@link org.cmdbuild.archimate.model.cmdb.PropertySet#getLocalName <em>Local Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Name</em>' attribute.
	 * @see #getLocalName()
	 * @generated
	 */
	void setLocalName(String value);

	/**
	 * Returns the value of the '<em><b>Any</b></em>' containment reference list.
	 * The list contents are of type {@link org.cmdbuild.archimate.model.cmdb.XmlNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any</em>' containment reference list.
	 * @see org.cmdbuild.archimate.model.cmdb.CmdbPackage#getPropertySet_Any()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNode> getAny();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.cmdbuild.archimate.model.cmdb.Record#getPropertySet <em>Property Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(Record)
	 * @see org.cmdbuild.archimate.model.cmdb.CmdbPackage#getPropertySet_Parent()
	 * @see org.cmdbuild.archimate.model.cmdb.Record#getPropertySet
	 * @model opposite="propertySet" transient="false"
	 * @generated
	 */
	Record getParent();

	/**
	 * Sets the value of the '{@link org.cmdbuild.archimate.model.cmdb.PropertySet#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Record value);

} // PropertySet
