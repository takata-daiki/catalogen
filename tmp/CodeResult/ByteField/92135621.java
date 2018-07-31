
package com.touwolf.bridje.dm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Implementacion de {@link AbstractNumericField} para almacenar objetos de tipo Byte, que son de menor dimension que los {@link ShortField}
 * Util para capturar bloques de <b>byte</b>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteField extends AbstractNumericField
{

    @XmlAttribute(name = "default")
    private Byte defaultValue;

    @Override
    public String getJavaType()
    {
        if (getRequired())
        {
            return "byte";
        }
        return "Byte";
    }

    @Override
    public Byte getDefaultValue()
    {
        return defaultValue;
    }

    /**
     * Establece el valore por defecto
     * @param defaultValue Valor de tipo {@link Byte} con nuevo valor por defecto
     */
    public void setDefaultValue(Byte defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getObjectJavaType()
    {
        return "Byte";
    }
}
