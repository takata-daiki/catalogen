/*
 * Function.java
 *
 * Created on April 2, 2006, 7:15 PM
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
 * Base class for all functions added to the parser.
 * @author markt
 */
public abstract class Function {
    private String name;
    /**
     * Creates a new instance of Function
     * Decendants of this class should have a constructor that begins with a
     * call  to super(functionname).  The constructor is the only function that
     * should set any kind of global state, since the other functions may get
     * called by various instances of the function in the expression
     * @param n name of function
     */
    public Function(String n) {
        name=n;
    }
    /**
     * getter function for the function name
     * @return the name of the function
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the number of elements in the resulting value.
     * This function will be called when the expression is first parsed, before
     * any dice are rolled.  You can verify the number of arguments being
     * passed, as well as the type and size of any arguments here.  You should
     * not expect to be able to get the value of any expression that is not
     * already atomic. Throw an IllegalArgumentException with some sort of
     * descriptive text in the message if the parameters are not acceptable for
     * any reason.  It should return 0 if the return value will be atomic,
     * or else the number of elements that will be in the group returned.
     * You can evaluate expressions that you have already verified to be
     * constant, because they were either decendants of ConstExpr, by using 
     * either getIntValue() or getStrValue(), (depending if isInt() returns true
     * or false, respectively).  
     * @param expr the parameters to this expression
     * @return the number of elements that this function will return
     */
    public abstract int getSize(DiceExpr[] expr);
    /**
     * Determines if the expression is an integer or not
     * This function will also be called when the expression is first parsed,
     * but will be called after getSize().   Do not attempt to evaluate the
     * parameters to the function at this time.  Only their general type can
     * be known at this stage (a string or integer expression, and either an
     * atomic value, or group value).   This function can also throw an
     * IllegalArgumentException if the parameters are not of expected types.
     * This function should return true if the function returns any sort of
     * integer expression (including a group of integer expressions), false
     * otherwise.
     * @param exprs the parameters to this function
     * @return true if an integer, false otherwise
     */
    public abstract boolean isInt(DiceExpr[] exprs);
    /**
     * Executes the function.
     * This function should actually do whatever the function is normally
     * supposed to do when it is called.  At this point, all expressions dice
     * have been rolled, and the vals array contains already evaluated results.
     * Individual elements of the array may be themselves either group values
     * or atomic values.  Call val[x].getBaseValue() on any element to obtain
     * its atomic equivalent (getBaseValue just returns the original value if
     * used on values that are already atomic).  Call the toString() method of
     * the value to convert it to a string, and if the value is an instance of
     * IntValue, then Integer.parseInt(val[x].toString()) will be guaranteed to
     * work correctly.   The return value should be either an IntValue,
     * StrValue, or GroupValue.
     * @param vals The values to pass to this function (already evaluated)
     * @return The result of the function
     */
    public abstract DiceValue exec(DiceValue[]vals);
    /**
     * utility function which can be called by subclasses to compare values
     * @param a Arg1
     * @param b Arg2
     * @return Returns negative if Arg1 < Arg2, positive if Arg1 > Arg2, and 0 if equal
     */
    public static int compareTo(DiceValue a,DiceValue b) {
        String x=a.getBaseValue().toString();
        String y=b.getBaseValue().toString();
        if (a.isInt() && b.isInt())
            return Integer.parseInt(x)-Integer.parseInt(y);
        else
            return x.compareTo(y);
    }
}
