/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.apache.avro;  
@SuppressWarnings("all")
public class Interop extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = org.apache.avro.Schema.parse("{\"type\":\"record\",\"name\":\"Interop\",\"namespace\":\"org.apache.avro\",\"fields\":[{\"name\":\"intField\",\"type\":\"int\"},{\"name\":\"longField\",\"type\":\"long\"},{\"name\":\"stringField\",\"type\":\"string\"},{\"name\":\"boolField\",\"type\":\"boolean\"},{\"name\":\"floatField\",\"type\":\"float\"},{\"name\":\"doubleField\",\"type\":\"double\"},{\"name\":\"bytesField\",\"type\":\"bytes\"},{\"name\":\"nullField\",\"type\":\"null\"},{\"name\":\"arrayField\",\"type\":{\"type\":\"array\",\"items\":\"double\"}},{\"name\":\"mapField\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"Foo\",\"fields\":[{\"name\":\"label\",\"type\":\"string\"}]}}},{\"name\":\"unionField\",\"type\":[\"boolean\",\"double\",{\"type\":\"array\",\"items\":\"bytes\"}]},{\"name\":\"enumField\",\"type\":{\"type\":\"enum\",\"name\":\"Kind\",\"symbols\":[\"A\",\"B\",\"C\"]}},{\"name\":\"fixedField\",\"type\":{\"type\":\"fixed\",\"name\":\"MD5\",\"size\":16}},{\"name\":\"recordField\",\"type\":{\"type\":\"record\",\"name\":\"Node\",\"fields\":[{\"name\":\"label\",\"type\":\"string\"},{\"name\":\"children\",\"type\":{\"type\":\"array\",\"items\":\"Node\"}}]}}]}");
  public int intField;
  public long longField;
  public java.lang.CharSequence stringField;
  public boolean boolField;
  public float floatField;
  public double doubleField;
  public java.nio.ByteBuffer bytesField;
  public java.lang.Void nullField;
  public java.util.List<java.lang.Double> arrayField;
  public java.util.Map<java.lang.CharSequence,org.apache.avro.Foo> mapField;
  public java.lang.Object unionField;
  public org.apache.avro.Kind enumField;
  public org.apache.avro.MD5 fixedField;
  public org.apache.avro.Node recordField;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return intField;
    case 1: return longField;
    case 2: return stringField;
    case 3: return boolField;
    case 4: return floatField;
    case 5: return doubleField;
    case 6: return bytesField;
    case 7: return nullField;
    case 8: return arrayField;
    case 9: return mapField;
    case 10: return unionField;
    case 11: return enumField;
    case 12: return fixedField;
    case 13: return recordField;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: intField = (java.lang.Integer)value$; break;
    case 1: longField = (java.lang.Long)value$; break;
    case 2: stringField = (java.lang.CharSequence)value$; break;
    case 3: boolField = (java.lang.Boolean)value$; break;
    case 4: floatField = (java.lang.Float)value$; break;
    case 5: doubleField = (java.lang.Double)value$; break;
    case 6: bytesField = (java.nio.ByteBuffer)value$; break;
    case 7: nullField = (java.lang.Void)value$; break;
    case 8: arrayField = (java.util.List<java.lang.Double>)value$; break;
    case 9: mapField = (java.util.Map<java.lang.CharSequence,org.apache.avro.Foo>)value$; break;
    case 10: unionField = (java.lang.Object)value$; break;
    case 11: enumField = (org.apache.avro.Kind)value$; break;
    case 12: fixedField = (org.apache.avro.MD5)value$; break;
    case 13: recordField = (org.apache.avro.Node)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
}