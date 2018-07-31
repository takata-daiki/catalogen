/**
 * Polygon.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.google.api.adwords.v201109.cm;


/**
 * Represents a Polygon Criterion.
 *             
 *             A polygon is described by a list of at least three points,
 * where each point is a
 *             (<var>latitude</var>, <var>longitude</var>) ordered pair.
 * No point can be more than 400km
 *             from the center of the polygon. The points are specified
 * in microdegrees, the precison
 *             for the value is 1 second of angle which is equal to 277
 * microdegrees.<p>
 *             
 *             <p>Please note that Polygons are deprecated. This means
 * that Polygon targets cannot be added
 *             through the API, though existing targets can be retrieved
 * and deleted.
 *             <p>
 */
public class Polygon  extends com.google.api.adwords.v201109.cm.Criterion  implements java.io.Serializable {
    /* The latitude/longitude points that define the polygon. At least
     * three points are required, and no point can be more than 400km
     *                     away from the center of the polygon.
     *                     <span class="constraint Selectable">This field
     * can be selected using the value "Vertices".</span>
     *                     <span class="constraint ReadOnly">This field is
     * read only and should not be set.  If this field is sent to the API,
     * it will be ignored.</span> */
    private com.google.api.adwords.v201109.cm.GeoPoint[] vertices;

    public Polygon() {
    }

    public Polygon(
           java.lang.Long id,
           com.google.api.adwords.v201109.cm.CriterionType type,
           java.lang.String criterionType,
           com.google.api.adwords.v201109.cm.GeoPoint[] vertices) {
        super(
            id,
            type,
            criterionType);
        this.vertices = vertices;
    }


    /**
     * Gets the vertices value for this Polygon.
     * 
     * @return vertices   * The latitude/longitude points that define the polygon. At least
     * three points are required, and no point can be more than 400km
     *                     away from the center of the polygon.
     *                     <span class="constraint Selectable">This field
     * can be selected using the value "Vertices".</span>
     *                     <span class="constraint ReadOnly">This field is
     * read only and should not be set.  If this field is sent to the API,
     * it will be ignored.</span>
     */
    public com.google.api.adwords.v201109.cm.GeoPoint[] getVertices() {
        return vertices;
    }


    /**
     * Sets the vertices value for this Polygon.
     * 
     * @param vertices   * The latitude/longitude points that define the polygon. At least
     * three points are required, and no point can be more than 400km
     *                     away from the center of the polygon.
     *                     <span class="constraint Selectable">This field
     * can be selected using the value "Vertices".</span>
     *                     <span class="constraint ReadOnly">This field is
     * read only and should not be set.  If this field is sent to the API,
     * it will be ignored.</span>
     */
    public void setVertices(com.google.api.adwords.v201109.cm.GeoPoint[] vertices) {
        this.vertices = vertices;
    }

    public com.google.api.adwords.v201109.cm.GeoPoint getVertices(int i) {
        return this.vertices[i];
    }

    public void setVertices(int i, com.google.api.adwords.v201109.cm.GeoPoint _value) {
        this.vertices[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Polygon)) return false;
        Polygon other = (Polygon) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.vertices==null && other.getVertices()==null) || 
             (this.vertices!=null &&
              java.util.Arrays.equals(this.vertices, other.getVertices())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getVertices() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVertices());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVertices(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Polygon.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://adwords.google.com/api/adwords/cm/v201109", "Polygon"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vertices");
        elemField.setXmlName(new javax.xml.namespace.QName("https://adwords.google.com/api/adwords/cm/v201109", "vertices"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://adwords.google.com/api/adwords/cm/v201109", "GeoPoint"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
