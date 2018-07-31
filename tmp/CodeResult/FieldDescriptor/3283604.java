/*
 * MySQL Index Analysis Tool
 * Copyright (C) 2006  Daniel Schneller
 * dannyschneller - at - bigfoot dot com
 * http://mysql-index-analyzer.blogspot.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

package de.shipdown.util.mysql.index;

import java.text.MessageFormat;

import de.shipdown.annotations.NonNull;

/**
 * Describes a single database field and it's position as it occurs in an index.
 * 
 * @author ds
 */
public class FieldDescriptor {
    /** Database field name as used in CREATE TABLE */
    String name;

    /** Position of this field in a composite index. Defaults to 1. */
    int sequenceInIndex;
    
    /**
     *  Average length of this field.
     *  Defined as long to prevent unwanted implicit conversions
     *  when taking part in calculations with ints.
     */
    long avgLength;

    /**
     * Creates a new FieldDescriptor. Use this constructor especially for parts
     * of composite indices.
     * 
     * @param aName
     *            Database field name as used in CREATE TABLE
     * @param aSequenceInIndex
     *            Position of this field in a composite index.
     */
    public FieldDescriptor(@NonNull
    String aName, int aSequenceInIndex) {
        name = aName;
        sequenceInIndex = aSequenceInIndex;
    }

    public FieldDescriptor(@NonNull
                           String aName, int aSequenceInIndex, int anAvgLength) {
                               name = aName;
                               sequenceInIndex = aSequenceInIndex;
                               avgLength = anAvgLength;
                           }

    
    /**
     * Creates a new FieldDescriptor. Use this constructor for single field
     * indices. It will automatically set the sequence in the index to 1.
     * 
     * @param aName
     *            Database field name as used in CREATE TABLE
     */
    public FieldDescriptor(@NonNull
    String aName) {
        name = aName;
        sequenceInIndex = 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FieldDescriptor)) {
            return false;
        }
        FieldDescriptor tDescriptor = (FieldDescriptor) obj;
        boolean tEquals = true;
        tEquals &= name.equals(tDescriptor.name);
        tEquals &= sequenceInIndex == tDescriptor.sequenceInIndex;
        return tEquals;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int tHash = 17;
        int tMultiplier = 59;
        tHash = tHash * tMultiplier;
        tHash = tHash + name.hashCode();
        tHash = tHash * sequenceInIndex;
        return tHash;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the name, enclosed in single quotes to allow
     * unusual characters and reserved words in field names
     */
    public String getEscapedName() {
    	return MessageFormat.format("`{0}`", getName());
    }
    
    /**
     * @return the sequenceInIndex
     */
    public int getSequenceInIndex() {
        return sequenceInIndex;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return sequenceInIndex + "-" + name;
    }

    protected long getAvgLength() {
        return avgLength;
    }

    protected void setAvgLength(long avgLength) {
        this.avgLength = avgLength;
    }
}
