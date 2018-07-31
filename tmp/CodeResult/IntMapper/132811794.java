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
package java.util;

import java.io.Serializable;
import java.util.functions.DoubleMapper;
import java.util.functions.IntMapper;
import java.util.functions.LongMapper;
import java.util.functions.Mapper;

/**
 * Comparators
 */
public class Comparators {
    private Comparators() {
        throw new AssertionError("no instances");
    }

    private static class NaturalOrderComparator
        implements Comparator<Comparable<Object>>, Serializable {

        private static final long serialVersionUID = 46013117367460976L;

        private static final NaturalOrderComparator INSTANCE = new NaturalOrderComparator();

        @Override
        public int compare(Comparable<Object> c1, Comparable<Object> c2) {
            return c1.compareTo(c2);
        }

        private Object readResolve() { return INSTANCE; }
    }

    private static class IntMapperComparator<T>
        implements Comparator<T>, Serializable {
        private static final long serialVersionUID = -5450968559910565116L;

        private final IntMapper<? super T> mapper;

        private IntMapperComparator(IntMapper<? super T> mapper) {
            Objects.requireNonNull(mapper);
            this.mapper = mapper;
        }

        @Override
        public int compare(T c1, T c2) {
            return Integer.compare(mapper.map(c1), mapper.map(c2));
        }
    }

    private static class DoubleMapperComparator<T>
        implements Comparator<T>, Serializable {
        private static final long serialVersionUID = -5734087649067745165L;

        private final DoubleMapper<? super T> mapper;

        private DoubleMapperComparator(DoubleMapper<? super T> mapper) {
            Objects.requireNonNull(mapper);
            this.mapper = mapper;
        }

        @Override
        public int compare(T c1, T c2) {
            return Double.compare(mapper.map(c1), mapper.map(c2));
        }
    }

    private static class LongMapperComparator<T>
        implements Comparator<T>, Serializable {
        private static final long serialVersionUID = 915311427971695245L;

        private final LongMapper<? super T> mapper;

        private LongMapperComparator(LongMapper<? super T> mapper) {
            Objects.requireNonNull(mapper);
            this.mapper = mapper;
        }

        @Override
        public int compare(T c1, T c2) {
            return Long.compare(mapper.map(c1), mapper.map(c2));
        }
    }

    private static class MappingKeyComparator<K extends Comparable<? super K>, V>
        implements Comparator<Mapping<K,V>>, Serializable {

        // FIXME needs to be calculated.
        private static final long serialVersionUID = -1;

        private static final MappingKeyComparator<? extends Comparable<?>,?> INSTANCE = new MappingKeyComparator<>();

        @Override
        public int compare(Mapping<K,V> c1, Mapping<K,V> c2) {
            return c1.getKey().compareTo(c2.getKey());
        }

        private Object readResolve() { return INSTANCE; }
    }

    private static class MappingValueComparator<K, V extends Comparable<? super V>>
        implements Comparator<Mapping<K,V>>, Serializable {

        // FIXME needs to be calculated.
        private static final long serialVersionUID = -1;

        private static final MappingValueComparator<?,? extends Comparable<?>> INSTANCE = new MappingValueComparator<>();

        @Override
        public int compare(Mapping<K,V> c1, Mapping<K,V> c2) {
            return c1.getValue().compareTo(c2.getValue());
        }

        private Object readResolve() { return INSTANCE; }
    }

    private static class MapperComparator<T, U extends Comparable<? super U>>
        implements Comparator<T>, Serializable {

        private static final long serialVersionUID = 8900536460967781434L;

        private final Mapper<? super T, ? extends U> mapper;

        private MapperComparator(Mapper<? super T, ? extends U> mapper) {
            Objects.requireNonNull(mapper);
            this.mapper = mapper;
        }

        @Override
        public int compare(T c1, T c2) {
            return mapper.map(c1).compareTo(mapper.map(c2));
        }
    }

    private static class ComposedComparator<T>
        implements Comparator<T>, Serializable {

        private static final long serialVersionUID = -3088981872625314196L;

        private final Comparator<? super T> first;
        private final Comparator<? super T> second;

        private ComposedComparator(Comparator<? super T> first, Comparator<? super T> second) {
            this.first = Objects.requireNonNull(first);
            this.second = Objects.requireNonNull(second);
        }

        @Override
        public int compare(T c1, T c2) {
            int res = first.compare(c1, c2);
            return (res != 0) ? res : second.compare(c1, c2);
        }
    }

    // Included for convenience, duplicates Collections functionality
    public static <T> Comparator<T> reverseOrder() {
        return Collections.reverseOrder();
    }

    // Included for convenience, duplicates Collections functionality
    public static <T> Comparator<T> reverseOrder(Comparator<T> cmp) {
        return Collections.reverseOrder(cmp);
    }

    public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
        return (Comparator<T>) NaturalOrderComparator.INSTANCE;
    }

    public static <K extends Comparable<? super K>, V> Comparator<Mapping<K,V>> naturalOrderKeys() {
        return (Comparator<Mapping<K,V>>) MappingKeyComparator.INSTANCE;
    }

    public static <K, V extends Comparable<? super V>> Comparator<Mapping<K,V>> naturalOrderValues() {
        return (Comparator<Mapping<K,V>>) MappingValueComparator.INSTANCE;
    }

    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(Mapper<? super T, ? extends U> mapper) {
        return new MapperComparator<>(mapper);
    }

    public static <T> Comparator<T> comparing(IntMapper<? super T> mapper) {
        return new IntMapperComparator<>(mapper);
    }

    public static <T> Comparator<T> comparing(LongMapper<? super T> mapper) {
        return new LongMapperComparator<>(mapper);
    }

    public static<T> Comparator<T> comparing(DoubleMapper<? super T> mapper) {
        return new DoubleMapperComparator<>(mapper);
    }

    public static<T> Comparator<T> compose(Comparator<? super T> first, Comparator<? super T> second) {
        return new ComposedComparator<>(first, second);
    }
}
