/* NotImplementedException.java
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

package ca.usask.cs.giraffe.exception;

/**
 * Exception for unsupported features
 * 
 * @author Joey Eremondi
 */
public class NotImplementedException extends ExecutionErrorException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new {@code NotImplementedException} with the specified
	 * detail message.
	 * 
	 * @param s
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link Throwable#getMessage()} method.
	 */
	public NotImplementedException(String s) {
		super(s);
	}
}
