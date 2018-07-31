//
// This file was ru.rosreestr by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.19 at 06:26:27 PM NOVT 
//


package ru.rosreestr.artefacts.x.outgoing.kvzu._6_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.rosreestr.artefacts.x.commons.complex_types.entity_spatial._2_0.TEntitySpatialBordersZUOut;


/**
 * Описание части земельного участка
 * 
 * <p>Java class for tSubParcel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tSubParcel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Area" type="{urn://x-artefacts-rosreestr-ru/outgoing/kvzu/6.0.9}tAreaWithoutInaccuracyOut"/>
 *         &lt;element name="Encumbrance" type="{urn://x-artefacts-rosreestr-ru/outgoing/kvzu/6.0.9}tEncumbranceZU" minOccurs="0"/>
 *         &lt;element name="EntitySpatial" type="{urn://x-artefacts-rosreestr-ru/commons/complex-types/entity-spatial/2.0.1}tEntitySpatialBordersZUOut" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="NumberRecord" use="required" type="{urn://x-artefacts-rosreestr-ru/commons/simple-types/1.0}s40" />
 *       &lt;attribute name="Full" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="State" type="{urn://x-artefacts-rosreestr-ru/commons/directories/states/1.0.1}dStates" />
 *       &lt;attribute name="DateExpiry" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSubParcel", propOrder = {
    "area",
    "encumbrance",
    "entitySpatial"
})
public class TSubParcel {

    @XmlElement(name = "Area", required = true)
    protected TAreaWithoutInaccuracyOut area;
    @XmlElement(name = "Encumbrance")
    protected TEncumbranceZU encumbrance;
    @XmlElement(name = "EntitySpatial")
    protected TEntitySpatialBordersZUOut entitySpatial;
    @XmlAttribute(name = "NumberRecord", required = true)
    protected String numberRecord;
    @XmlAttribute(name = "Full")
    protected Boolean full;
    @XmlAttribute(name = "State")
    protected String state;
    @XmlAttribute(name = "DateExpiry")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateExpiry;

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link TAreaWithoutInaccuracyOut }
     *     
     */
    public TAreaWithoutInaccuracyOut getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAreaWithoutInaccuracyOut }
     *     
     */
    public void setArea(TAreaWithoutInaccuracyOut value) {
        this.area = value;
    }

    /**
     * Gets the value of the encumbrance property.
     * 
     * @return
     *     possible object is
     *     {@link TEncumbranceZU }
     *     
     */
    public TEncumbranceZU getEncumbrance() {
        return encumbrance;
    }

    /**
     * Sets the value of the encumbrance property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEncumbranceZU }
     *     
     */
    public void setEncumbrance(TEncumbranceZU value) {
        this.encumbrance = value;
    }

    /**
     * Gets the value of the entitySpatial property.
     * 
     * @return
     *     possible object is
     *     {@link TEntitySpatialBordersZUOut }
     *     
     */
    public TEntitySpatialBordersZUOut getEntitySpatial() {
        return entitySpatial;
    }

    /**
     * Sets the value of the entitySpatial property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEntitySpatialBordersZUOut }
     *     
     */
    public void setEntitySpatial(TEntitySpatialBordersZUOut value) {
        this.entitySpatial = value;
    }

    /**
     * Gets the value of the numberRecord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberRecord() {
        return numberRecord;
    }

    /**
     * Sets the value of the numberRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberRecord(String value) {
        this.numberRecord = value;
    }

    /**
     * Gets the value of the full property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFull() {
        return full;
    }

    /**
     * Sets the value of the full property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFull(Boolean value) {
        this.full = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the dateExpiry property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateExpiry() {
        return dateExpiry;
    }

    /**
     * Sets the value of the dateExpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateExpiry(XMLGregorianCalendar value) {
        this.dateExpiry = value;
    }

}
