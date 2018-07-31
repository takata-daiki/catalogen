/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.tests.java.util;

import java.util.Comparator;
import java.util.Comparators;
import org.testng.annotations.Test;

import java.util.functions.DoubleMapper;
import java.util.functions.IntMapper;
import java.util.functions.LongMapper;
import java.util.functions.Mapper;

import static org.testng.Assert.assertEquals;

/**
 * Unit tests for helper methods in Comparators
 */
@Test(groups = "unit")
public class ComparatorsTest {
    private static class Thing {
        public final int intField;
        public final long longField;
        public final double doubleField;
        public final String stringField;

        private Thing(int intField, long longField, double doubleField, String stringField) {
            this.intField = intField;
            this.longField = longField;
            this.doubleField = doubleField;
            this.stringField = stringField;
        }

        public int getIntField() {
            return intField;
        }

        public long getLongField() {
            return longField;
        }

        public double getDoubleField() {
            return doubleField;
        }

        public String getStringField() {
            return stringField;
        }
    }

    private final int[] intValues = { -2, -2, -1, -1, 0, 0, 1, 1, 2, 2 };
    private final long[] longValues = { -2, -2, -1, -1, 0, 0, 1, 1, 2, 2 };
    private final double[] doubleValues = { -2, -2, -1, -1, 0, 0, 1, 1, 2, 2 };
    private final String[] stringValues = { "a", "a", "b", "b", "c", "c", "d", "d", "e", "e" };
    private final int[] comparisons = { 0, -1, 0, -1, 0, -1, 0, -1, 0 };

    private<T> void assertComparisons(T[] things, Comparator<T> comp, int[] comparisons) {
        for (int i=0; i<comparisons.length; i++) {
            assertEquals(comparisons.length + 1, things.length);
            assertEquals(comparisons[i], comp.compare(things[i], things[i+1]));
            assertEquals(-comparisons[i], comp.compare(things[i+1], things[i]));
        }
    }

    public void testIntComparator() {
        Thing[] things = new Thing[intValues.length];
        for (int i=0; i<intValues.length; i++)
            things[i] = new Thing(intValues[i], 0L, 0.0, null);
        Comparator<Thing> comp = Comparators.comparing(new IntMapper<Thing>() {
            @Override
            public int map(Thing thing) {
                return thing.getIntField();
            }
        });

        assertComparisons(things, comp, comparisons);
    }

    public void testLongComparator() {
        Thing[] things = new Thing[longValues.length];
        for (int i=0; i<longValues.length; i++)
            things[i] = new Thing(0, longValues[i], 0.0, null);
        Comparator<Thing> comp = Comparators.comparing(new LongMapper<Thing>() {
            @Override
            public long map(Thing thing) {
                return thing.getLongField();
            }
        });

        assertComparisons(things, comp, comparisons);
    }
    
    public void testDoubleComparator() {
        Thing[] things = new Thing[doubleValues.length];
        for (int i=0; i<doubleValues.length; i++)
            things[i] = new Thing(0, 0L, doubleValues[i], null);
        Comparator<Thing> comp = Comparators.comparing(new DoubleMapper<Thing>() {
            @Override
            public double map(Thing thing) {
                return thing.getDoubleField();
            }
        });

        assertComparisons(things, comp, comparisons);
    }

    public void testMappingComparator() {
        Thing[] things = new Thing[doubleValues.length];
        for (int i=0; i<doubleValues.length; i++)
            things[i] = new Thing(0, 0L, 0.0, stringValues[i]);
        Comparator<Thing> comp = Comparators.comparing(new Mapper<Thing, String>() {
            @Override
            public String map(Thing thing) {
                return thing.getStringField();
            }
        });

        assertComparisons(things, comp, comparisons);
    }

    public void testNaturalOrderComparator() {
        Comparator<String> comp = Comparators.naturalOrder();

        assertComparisons(stringValues, comp, comparisons);
    }
}
