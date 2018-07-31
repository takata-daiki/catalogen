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

import java.util.*;
import java.util.functions.*;
import java.util.streams.TerminalSink;
import java.util.streams.Stream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * LambdaTestHelpers -- assertion methods and useful objects for lambda test cases
 */
public class LambdaTestHelpers {
    public static final String LONG_STRING = "When in the Course of human events it becomes necessary for one people to dissolve the political bands which have connected them with another and to assume among the powers of the earth, the separate and equal station to which the Laws of Nature and of Nature's God entitle them, a decent respect to the opinions of mankind requires that they should declare the causes which impel them to the separation.";

    @SuppressWarnings("rawtypes")
    public static final Block bEmpty = x -> {  };
    @SuppressWarnings("rawtypes")
    public static final BiBlock bBiEmpty = (x,y) -> { };
    @SuppressWarnings("rawtypes")
    public static final Block bHashCode = x -> { Objects.hashCode(x); };
    @SuppressWarnings("rawtypes")
    public static final BiBlock bBiHashCode = (x,y) -> { Objects.hash(x, y); };
    public static final Mapper<Integer, Integer> mZero = x -> 0;
    public static final Mapper<Integer, Integer> mId = x -> x;
    public static final Mapper<Integer, Integer> mDoubler = x -> x * 2;
    public static final FlatMapper<Integer, Integer> mfId = /*@@@ Sink::accept*/ (s,e) -> { s.apply(e); };
    public static final FlatMapper<Integer, Integer> mfNull = (s, e) -> { };
    public static final FlatMapper<Integer, Integer> mfLt = (s, e) -> { for (int i=0; i<e; i++) s.apply(i); };
    public static final IntMapper<Integer> imDoubler = x -> x * 2;
    public static final LongMapper<Long> lmDoubler = x -> x * 2;
    public static final DoubleMapper<Double> dmDoubler = x -> x * 2;
    public static final Predicate<Integer> pFalse = x -> false;
    public static final Predicate<Integer> pTrue = x -> true;
    public static final Predicate<Integer> pEven = x -> 0 == x % 2;
    public static final Predicate<Integer> pOdd = x -> 1 == x % 2;
    public static final BinaryOperator<Integer> rPlus = (x, y) -> x+y;
    public static final BinaryOperator<Integer> rMax = (x, y) -> Math.max(x, y);
    public static final BinaryOperator<Integer> rMin = (x, y) -> Math.min(x,y);
    public static final IntBinaryOperator irPlus = (x, y) -> x+y;
    public static final LongBinaryOperator lrPlus = (x, y) -> x+y;
    public static final DoubleBinaryOperator drPlus = (x, y) -> x+y;
    public static final Comparator<Integer> cInteger = (a, b) -> Integer.compare(a, b);
    public static final BiPredicate<?, ?> bipFalse = (x, y) -> false;
    public static final BiPredicate<?, ?> bipTrue = (x, y) -> true;
    public static final BiPredicate<Integer, Integer> bipBothEven = (x, y) -> 0 == (x % 2 + y % 2);
    public static final BiPredicate<Integer, Integer> bipBothOdd = (x, y) -> 2 == (x % 2 + y % 2);
    public static final BiPredicate<?, ?> bipSameString = (x, y) -> String.valueOf(x).equals(String.valueOf(y));

    public static final FlatMapper<String, Character> flattenChars = new FlatMapper<String, Character>() {
        @Override
        public void flatMapInto(Block<? super Character> sink, String element) {
            for (int i=0; i<element.length(); i++) {
                sink.apply(element.charAt(i));
            }
        }
    };

    public static List<Integer> empty() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(null);
        return list;
    }

    public static List<Integer> countTo(int n) {
        return range(1, n);
    }

    public static List<Integer> range(int l, int u) {
        ArrayList<Integer> list = new ArrayList<>(u - l + 1);
        for (int i=l; i<=u; i++) {
            list.add(i);
        }
        return list;
    }

    public static List<Integer> repeat(int value, int n) {
        ArrayList<Integer> list = new ArrayList<>(n);
        for (int i=1; i<=n; i++) {
            list.add(value);
        }
        return list;
    }

    public static List<Double> asDoubles(List<Integer> integers) {
        ArrayList<Double> list = new ArrayList<>();
        for (Integer i : integers) {
            list.add((double) i);
        }
        return list;
    }

    public static List<Long> asLongs(List<Integer> integers) {
        ArrayList<Long> list = new ArrayList<>();
        for (Integer i : integers) {
            list.add((long) i);
        }
        return list;
    }

    public static void assertCountSum(Stream<? super Integer> it, int count, int sum) {
        assertCountSum(it.iterator(), count, sum);
    }

    public static void assertCountSum(Iterable<? super Integer> it, int count, int sum) {
        assertCountSum(it.iterator(), count, sum);
    }

    public static void assertCountSum(Iterator<? super Integer> it, int count, int sum) {
        int c = 0;
        int s = 0;
        while (it.hasNext()) {
            int i = (Integer) it.next();
            c++;
            s += i;
        }

        assertEquals(c, count);
        assertEquals(s, sum);
    }

    public static void assertConcat(Iterator<Character> it, String result) {
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
        }

        assertEquals(result, sb.toString());
    }

    public static<T extends Comparable<? super T>> void assertSorted(Iterator<T> i) {
        if (!i.hasNext())
            return;
        T last = i.next();
        while (i.hasNext()) {
            T t = i.next();
            assertTrue(last.compareTo(t) <= 0);
            assertTrue(t.compareTo(last) >= 0);
            last = t;
        }
    }

    public static<T> void assertSorted(Iterator<T> i, Comparator<? super T> comp) {
        if (!i.hasNext())
            return;
        T last = i.next();
        while (i.hasNext()) {
            T t = i.next();
            assertTrue(comp.compare(last, t) <= 0);
            assertTrue(comp.compare(t, last) >= 0);
            last = t;
        }
    }

    public static<T extends Comparable<? super T>> void assertSorted(Iterable<T> iter) {
        assertSorted(iter.iterator());
    }

    public static<T> void assertSorted(Iterable<T> iter, Comparator<? super T> comp) {
        assertSorted(iter.iterator(), comp);
    }

    public static <T> void assertUnique(Iterable<T> iter) {
        assertUnique(iter.iterator());
    }

    public static<T> void assertUnique(Iterator<T> iter) {
        if (!iter.hasNext()) {
            return;
        }

        Set<T> uniq = new HashSet<>();
        while(iter.hasNext()) {
            T each = iter.next();
            assertTrue(!uniq.contains(each));
            uniq.add(each);
        }
    }

    public static<T> void assertContents(Iterable<T> actual, Iterable<T> expected) {
        assertContents(actual.iterator(), expected.iterator());
    }

    public static<T> void assertContents(Iterator<T> actual, Iterator<T> expected) {
        while (expected.hasNext()) {
            assertTrue(actual.hasNext());
            T pT = actual.next();
            T lT = expected.next();

            assertEquals(pT, lT);
        }
        assertTrue(!actual.hasNext());
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static<T> void assertContents(Iterator<T> actual, T... expected) {
        assertContents(actual, Arrays.asList(expected).iterator());
    }

    public static<T extends Comparable<? super T>> void assertContentsUnordered(Iterable<T> actual, Iterable<T> expected) {
        ArrayList<T> one = new ArrayList<>();
        for (T t : actual)
            one.add(t);
        ArrayList<T> two = new ArrayList<>();
        for (T t : expected)
            two.add(t);
        Collections.sort(one);
        Collections.sort(two);
        assertContents(one, two);
    }

    static <T> void assertSplitContents(Iterable<Iterable<T>> splits, Iterable<T> list) {
        Iterator<Iterable<T>> mI = splits.iterator();
        Iterator<T> pI = null;
        Iterator<T> lI = list.iterator();

        while (lI.hasNext()) {
            if (pI == null)
                pI = mI.next().iterator();
            while (!pI.hasNext()) {
                if (!mI.hasNext()) {
                    break;
                }
                else {
                    pI = mI.next().iterator();
                }
            }
            assertTrue(pI.hasNext());
            T pT = pI.next();
            T lT = lI.next();
            assertEquals(pT, lT);
        }

        if (pI != null) {
            assertTrue(!pI.hasNext());
        }

        while(mI.hasNext()) {
            pI = mI.next().iterator();
            assertTrue(!pI.hasNext());
        }
    }

    public static <T,V> V iteratorToStatefulSink(Iterator<? extends T> iterator, TerminalSink<? super T, ? extends V> sink) {
        sink.begin(-1);
        while(iterator.hasNext()) {
            sink.accept(iterator.next());
        }
        sink.end();
        return sink.getAndClearState();
    }
}
