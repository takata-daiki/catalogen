/*
 * @(#)Table.java   11/08/25
 * 
 * Copyright (c) 2011 MichaelReilly.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. *
 *
 *
 */



package org.michaelreilly.dbcopy.filesystem;

/**
 *
 * @author mreilly
 */
public class Table
{

    /** Field description */
    protected String control;

    /** Field description */
    protected String fileName;

    /** Field description */
    protected String format;

    /** Field description */
    protected String name;

    /** Field description */
    protected boolean truncateFirst;

    /**
     * Get the value of truncateFirst
     *
     * @return the value of truncateFirst
     */
    public boolean getTruncateFirst ()
    {
        return truncateFirst;
    }

    /**
     * Set the value of truncateFirst
     *
     * @param truncateFirst new value of truncateFirst
     */
    public void setTruncateFirst (boolean truncateFirst)
    {
        this.truncateFirst = truncateFirst;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName ()
    {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * Get the value of fileName
     *
     * @return the value of fileName
     */
    public String getFileName ()
    {
        return fileName;
    }

    /**
     * Set the value of fileName
     *
     * @param fileName new value of fileName
     */
    public void setFileName (String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Get the value of control
     *
     * @return the value of control
     */
    public String getControl ()
    {
        return control;
    }

    /**
     * Set the value of control
     *
     * @param control new value of control
     */
    public void setControl (String control)
    {
        this.control = control;
    }

    /**
     * Get the value of format
     *
     * @return the value of format
     */
    public String getFormat ()
    {
        return format;
    }

    /**
     * Set the value of format
     *
     * @param format new value of format
     */
    public void setFormat (String format)
    {
        this.format = format;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
