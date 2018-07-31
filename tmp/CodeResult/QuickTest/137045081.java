/*
 * Copyright (C) 2011  Xin Zhang and Franck van Breugel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You can find a copy of the GNU General Public License at
 * <http://www.gnu.org/licenses/>.
 */

package probabilistic;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * This app creates an array and sorts it.
 * 
 * @author Xin Zhang
 * @author Franck van Breugel
 */
public class QuickTest {
	public static void main(String[] args) {
		Integer[] elements = { 7, 1, 2, 6, 4, 3, 5 };
		List<Integer> list = new Vector<Integer>(Arrays.asList(elements));
		Quick.sort(list);
	}
}
