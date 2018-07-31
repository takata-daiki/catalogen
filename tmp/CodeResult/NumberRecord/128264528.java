//
// This file was ru.rosreestr by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.19 at 06:26:27 PM NOVT 
//


package ru.rosreestr.artefacts.x.outgoing.kvzu._6_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.rosreestr.artefacts.x.commons.complex_types.entity_spatial._2_0.TEntitySpatialBordersZUOut;


/**
 * Описание контура многоконтурного участка
 * 
 * <p>Java class for tContour complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tContour">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Area" type="{urn://x-artefacts-rosreestr-ru/outgoing/kvzu/6.0.9}tAreaWithoutInaccuracyOut"/>
 *         &lt;element name="EntitySpatial" type="{urn://x-artefacts-rosreestr-ru/commons/complex-types/entity-spatial/2.0.1}tEntitySpatialBordersZUOut" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="NumberRecord" use="required" type="{urn://x-artefacts-rosreestr-ru/commons/simple-types/1.0}p10" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tContour", propOrder = {
    "area",
    "entitySpatial"
})
public class TContour {

    @XmlElement(name = "Area", required = true)
    protected TAreaWithoutInaccuracyOut area;
    @XmlElement(name = "EntitySpatial")
    protected TEntitySpatialBordersZUOut entitySpatial;
    @XmlAttribute(name = "NumberRecord", required = true)
    protected BigInteger numberRecord;

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
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberRecord() {
        return numberRecord;
    }

    /**
     * Sets the value of the numberRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberRecord(BigInteger value) {
        this.numberRecord = value;
    }

}
