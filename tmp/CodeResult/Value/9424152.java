/*
 * Value.java
 *
 * Created on April 2, 2006, 2:48 PM
 *
 * ====================================================================
 * Copyright (C) 2005-2006 The OpenRPG Project (www.openrpg.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License on www.gnu.org for more details.
 * ====================================================================
 */

package openrpg2.common.dice;

/**
 * Base interface defining the api for all values used by the dice package
 * @author markt
 */
public interface Value {
    /**
     * Determines whether or not this value is an integer
     * @return true if an integer, false otherwise
     */
    public boolean isInt();
    /**
     * Gets the number of elements in this value. 
     * @return the number of elements in this value ( 0 means that the value is atomic )
     */
    public int getSize();
    /**
     * Gets the base for this value (ie, all groups are evaluated to atomic values).
     * @return a base value that represents this value after it is fully evaluated
     */
    public Value getBaseValue();
}
