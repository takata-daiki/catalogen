/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.jbi.jsf.bean;

/**
 * Represents a name, value pair in a Table row.
 */
public class Row
{
    /**
     * no-arg ctor
     */
    public Row()
    {
    }

    /**
     * gets row disablement
     * @return boolean true if row is to be disabled
     */
    public boolean getDisabled()
    {
        return mDisabled;
    }

    /**
     * gets row name
     * @return String name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * gets row rendering
     * @return boolean true if row is to be rendering
     */
    public boolean getRendered()
    {
        return mRendered;
    }

    /**
     * gets row selection
     * @return boolean true if row is to selected
     */
    public boolean getSelected()
    {
        return mSelected;
    }

    /**
     * gets row value
     * @return String value
     */
    public String getValue()
    {
        return mValue;
    }

    /**
     * sets row disablement
     * @param isDisabled boolean true if row is to be disabled
     */
    public void setDisabled(boolean isDisabled)
    {
        mDisabled = isDisabled;
    }

    /**
     * sets row name
     * @param aName String with row name
     */
    public void setName(String aName)
    {
        mName = aName;
    }

    /**
     * sets row rendering
     * @param isRendered boolean true if row is to be rendered
     */
    public void setRendered(boolean isRendered)
    {
        mRendered = isRendered;
    }

    /**
     * sets row selection state
     * @param isSelected boolean true if row is to be selected
     */
    public void setSelected(boolean isSelected)
    {
        mSelected = isSelected;
    }

    /**
     * sets row value
     * @param aValue String with row value
     */
    public void setValue(String aValue)
    {
        mValue = aValue;
    }

    /**
     * row disabled attribute
     */
    private boolean mDisabled;

    /**
     * row name attribute
     */
    private String mName;

    /**
     * row rendered attribute
     */
    private boolean mRendered;

    /**
     * row selected attribute
     */
    private boolean mSelected;

    /**
     * row value attribute
     */
    private String mValue;

}
