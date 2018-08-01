//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.10.09 at 02:37:29 AM PHT 
//


package reader_writer.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ErrorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Valid"/>
 *     &lt;enumeration value="XML_Error"/>
 *     &lt;enumeration value="Net_Error"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ErrorType")
@XmlEnum
public enum ErrorType {

    @XmlEnumValue("Valid")
    VALID("Valid"),
    @XmlEnumValue("XML_Error")
    XML_ERROR("XML_Error"),
    @XmlEnumValue("Net_Error")
    NET_ERROR("Net_Error");
    private final String value;

    ErrorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErrorType fromValue(String v) {
        for (ErrorType c: ErrorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}