/**
 * -----------------------------------------------------------------------------------------------
 *  File: PropertyTable.java
 *
 *  Copyright (c) 2007 by Keymind Computing as.
 *  All rights reserved.
 *
 *  This file is subject to the terms and conditions of the Apache Licence 2.0.
 *  See the file LICENCE in the main directory of the Keywatch distribution for more details.
 *
 *  Revision History:
 *   $URL: http://keywatch.googlecode.com/svn/trunk/src/server/gwtgui/src/keymind/keywatch/gui/client/eventtable/PropertyTable.java $
 *   $Date: 2009-09-17 11:14:51 +0200 (Thu, 17 Sep 2009) $, $Rev: $
 * -----------------------------------------------------------------------------------------------
 */
package keymind.keywatch.gui.client.eventtable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import keymind.keywatch.domainmodel.eventDomain.Property;
import keymind.keywatch.gui.client.desktop.EditableValueWidget;
import keymind.keywatch.gui.client.desktop.GUICtrl;
import keymind.keywatch.gui.client.desktop.IValueListener;

/**
 * A table of properties that can be edited
 */
public class PropertyTable extends FlexTable implements ClickHandler, IValueListener
{
    private Property[] properties;
    private Hyperlink addLink;
    private EditableValueWidget[] propValues;
    private IValueListener valueListener;


    /**
     * C'tor
     */
    public PropertyTable()
    {
        setText(0, 0, "Properties");
        addLink = new Hyperlink("Add property", "add");
        addLink.addClickHandler(this);
        setWidget(0, 1, addLink);
    }


    /**                                                                 
     * Set properties
     *
     * @param props
     */
    public void setProperties(Property[] props)
    {
        this.properties = props;
        refresh();
    }


    /**
     * Update the properties in the tab
     *
     * @param props
     */
    public void updateProperties(Property[] props)
    {
        if (props == null || props.length == 0)
        {
            return;
        }

        Property p;
        for (int i = 0; i < props.length; i++)
        {
            p = findProperty(props[i].getName());
            if (p != null)
            {
                // Existing property, change value (if not edited)
                if (props[i].getValue() != null && !props[i].getValue().equals(p.getValue()))
                {
                    p.setValue(props[i].getValue());

                    // Update the editable value as well
                    int index = findPropertyIndex(p);
                    if (index >= 0)
                    {
                        propValues[index].setValue(p.getValue());
                    }
                }
            }
            else
            {
                // This is a new property, add to the bottom
                this.newProperty(props[i]);
            }
        }
    }


    /**
     * Get properties
     *
     * @return properties
     */
    public Property[] getProperties()
    {
        return properties;
    }


    /**
     * Updates the table based on the current properties
     * NB Forgets about edited values
     */
    public void refresh()
    {
        // Clean up, remove old rows
        for (int i = this.getRowCount() - 1; i > 0; i--)
        {
            this.removeRow(i);
        }

        // Update internal data and table
        if (properties == null)
        {
            this.properties = new Property[0];
            this.propValues = new EditableValueWidget[0];
        }
        else
        {
            propValues = new EditableValueWidget[properties.length];
            for (int i = 0; i < properties.length; i++)
            {
                setText(i + 1, 0, properties[i].getName());
                this.getCellFormatter().setHeight(i + 1, 0, "25px");

                propValues[i] = new EditableValueWidget(properties[i].getValue());
                propValues[i].addValueListener(this);
                setWidget(i + 1, 1, propValues[i]);
                this.getCellFormatter().setHeight(i + 1, 1, "25px");
            }
        }
    }


    /**
     * Update the property values based on the edited
     * values in the property list
     */
    public void updateEditedProperties()
    {
        for (int i = 0; i < properties.length; i++)
        {
            properties[i].setValue(propValues[i].getValue());
        }
    }


    public void clearEditMarks()
    {
        for (int i = 0; i < propValues.length; i++)
        {
            propValues[i].setEdited(false);
        }
    }


    /**
     * Add new property
     *
     * @param newProp Property to add
     * @return False if the property was invalid
     */
    public boolean newProperty(Property newProp)
    {
        String name = newProp.getName();

        // Precondition
        if (findProperty(name) != null)
        {
            return false;
        }

        if (name == null || name.equals(""))
        {
            return false;
        }

        // Copy into larger table
        Property[] oldProps = properties;
        EditableValueWidget[] oldPropValues = propValues;
        properties = new Property[oldProps.length + 1];
        propValues = new EditableValueWidget[oldProps.length + 1];

        for (int i = 0; i < oldProps.length; i++)
        {
            properties[i] = oldProps[i];
            propValues[i] = oldPropValues[i];
        }

        EditableValueWidget newPropValue = new EditableValueWidget(null);
        newPropValue.addValueListener(this);
        newPropValue.setValue(newProp.getValue());
        newPropValue.setTitle("value");

        properties[oldProps.length] = newProp;
        propValues[oldProps.length] = newPropValue;

        EditableValueWidget newPropName = new EditableValueWidget(null);
        newPropName.addValueListener(this);
        newPropName.setValue(properties[oldProps.length].getName());
        newPropName.setTitle("name");

        // Update prop table
        setWidget(oldProps.length + 1, 0, newPropName);
        setWidget(oldProps.length + 1, 1, propValues[oldProps.length]);
        GUICtrl.log("Ny prop:" + newProp.getName() + "=" + newPropValue.getValue());

        return true;
    }


    /**
     * Add property
     *
     * @param name  Property name
     * @param value Property value
     * @return True if the property name and value was valid
     */
    public boolean newProperty(String name, String value)
    {
        Property newProp = new Property();
        newProp.setName(name);
        newProp.setValue(value);

        return newProperty(newProp);
    }


    /**
     * Adds a new property to the bottom of the propertylist,
     * updates GUI
     *
     * @param name
     */
    public boolean newProperty(String name)
    {
        return newProperty(name, null);
    }


    /**
     * Add value listener
     */
    public void addValueListener(IValueListener listener)
    {
        valueListener = listener;
    }


    /**
     * Helper: finds a property with given name
     *
     * @param name
     * @return property if any
     */
    public Property findProperty(String name)
    {
        for (int i = 0; i < properties.length; i++)
        {
            if (properties[i] != null && properties[i].getName() != null && properties[i].getName().equals(name))
            {
                return properties[i];
            }
        }
        return null;
    }


    /**
     * Helper: finds property index to a given property
     *
     * @param p
     * @return index, -1 if not found
     */
    public int findPropertyIndex(Property p)
    {
        for (int i = 0; i < properties.length; i++)
        {
            if (properties[i] == p)
            {
                return i;
            }
        }
        return -1;
    }


    /**
     * Click handler
     *
     * @param evt ClickEvent
     */
    public void onClick(ClickEvent evt)
    {
        if (evt.getSource() == addLink)
        {
            int index = 1;
            String name = "NewProperty_" + index;
            while (!newProperty(name))
            {
                index++;
                name = "NewProperty_" + index;
            }
        }
    }


    /**
     * Called on change in one of the property-values forward the call to the owner of the proptable
     *
     * @param newValue
     * @param oldValue
     * @param source
     */
    public void onValue(String newValue, String oldValue, Object source)
    {
        if (((EditableValueWidget)source).getTitle() != null &&
                ((EditableValueWidget)source).getTitle().equals("name"))
        {
            // Set in the name and make it a "real property"
            Property p = findProperty(oldValue);
            if (p != null)
            {
                p.setName(newValue);
            }
        }
        else if (valueListener != null)
        {
            valueListener.onValue(newValue, oldValue, this);
        }
    }
}
