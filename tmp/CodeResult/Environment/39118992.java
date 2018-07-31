/* Environment.java
 * Copyright (C) 2012 Alex "HolyCause" Mair (holy.cause@gmail.com)
 * Copyright (C) 2012 Ivan Vendrov (ivendrov@gmail.com)
 * Copyright (C) 2012 Joey Eremondi (jse313@mail.usask.ca)
 * Copyright (C) 2012 Joanne Traves (jet971@mail.usask.ca)
 * Copyright (C) 2012 Logan Cool (coollogan88@gmail.com)
 * 
 * This file is a part of Giraffe.
 * 
 * Giraffe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Giraffe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Giraffe.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.usask.cs.giraffe.compiler;

import java.util.*;

import ca.usask.cs.giraffe.exception.*;

/**
 * Interface for storing name-value mappings which can deal with different loop,
 * function, and global scope levels
 * 
 * @author Joey Eremondi
 */
public interface Environment {
	
	/**
	 * Get the value of the given variable name
	 * 
	 * @param id
	 *            the name to lookup
	 * @return The value stored in that variable
	 * @throws NameNotFoundException
	 */
	public Value getValue(String id) throws NameNotFoundException;
	
	/**
	 * Declare a new variable name in this environment
	 * 
	 * @param id
	 *            the name of the variable to add
	 * @param t
	 *            the type of the declared variable
	 */
	public void extendEnv(String id, Type t) throws DuplicateNameException;
	
	/**
	 * Assign a new value to a variable
	 * 
	 * @param id
	 *            the variable to assign to
	 * @param v
	 *            the value to associate with the name
	 * @throws ExecutionErrorException
	 */
	public void assignValue(String id, Value v) throws NameNotFoundException,
			InvalidTypeException, ExecutionErrorException;
	
	/**
	 * Return the environment once level down in the lexical scope heiearchy
	 * 
	 * @return The parent of this environment
	 */
	public Environment getParent();
	
	/**
	 * Creates a list of all names at this level
	 * 
	 * @return the map of all names-types in this environment level, but NOT
	 *         those in
	 *         its parents or ancestors
	 */
	public Map<String,Type> oneLevelNames();
	
	/**
	 * Return the type associated with the given id in this environment or its
	 * parent
	 * 
	 * @param id
	 *            the name to lookup
	 * @return The type of that variable
	 * @throws NameNotFoundException
	 */
	public Type getType(String id) throws NameNotFoundException;
	
	/**
	 * Return names of all variables in the current function scope
	 * but not the global scope
	 */
	public AbstractList<Map.Entry<String,Type>> nonGlobalVars();
	
}
