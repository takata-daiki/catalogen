
package com.touwolf.bridje.dm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *  Implementacion de {@link AbstractNumericField} cuya dimension es menor que el {@link IntegerField}
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ShortField extends AbstractNumericField
{
    @XmlAttribute(name = "default")
    private Short defaultValue; 
    
    @Override
    public String getJavaType()
    {
        if (getRequired())
        {
            return "short";
        }
        return "Short";
    }

    @Override
    public Short getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(Short defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getObjectJavaType()
    {
        return "Short";
    } 
}
