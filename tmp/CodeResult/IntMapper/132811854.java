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

import java.util.functions.*;
import java.util.streams.Stream;

/**
 * Utilities for Iterators.  All of these methods consume elements from the iterators passed to them!
 *
 * @since 1.8
 */
public final class Iterators {

    private Iterators() {
        throw new Error("No instances for you!");
    }

    public static<T> T getFirst(Iterator<? extends T> iterator) {
        if (!iterator.hasNext())
            return null;

        T next = iterator.next();
        if (next == null)
            throw new NullPointerException();
        else
            return next;
    }

    public static<T> T getOnly(Iterator<? extends T> iterator) {
        if (!iterator.hasNext())
            throw new NoSuchElementException();

        T next = iterator.next();
        if (next == null)
            throw new NullPointerException();

        if (iterator.hasNext())
            throw new IllegalStateException();

        return next;
    }

    public static <T> T reduce(final Iterator<? extends T> iterator, T base, final BinaryOperator<T> operator) {
        Objects.requireNonNull(iterator);
        Objects.requireNonNull(operator);
        while(iterator.hasNext()) {
            base = operator.operate(base, iterator.next());
        }

        return base;
    }

    public static <T, U> U mapReduce(Iterator<? extends T> iterator, Mapper<? super T, ? extends U> mapper, U base, BinaryOperator<U> operator) {
        Objects.requireNonNull(iterator);
        Objects.requireNonNull(mapper);
        Objects.requireNonNull(operator);
        while(iterator.hasNext()) {
            base = operator.operate(base, mapper.map(iterator.next()));
        }

        return base;
    }

    public static <T> int mapReduce(Iterator<? extends T> iterator, IntMapper<? super T> mapper, int base, IntBinaryOperator operator) {
        Objects.requireNonNull(iterator);
        Objects.requireNonNull(mapper);
        Objects.requireNonNull(operator);
        while(iterator.hasNext()) {
            base = operator.eval(base, mapper.map(iterator.next()));
        }

        return base;
    }

    public static <T> long mapReduce(Iterator<? extends T> iterator, LongMapper<? super T> mapper, long base, LongBinaryOperator operator) {
        Objects.requireNonNull(iterator);
        Objects.requireNonNull(mapper);
        Objects.requireNonNull(operator);
        while(iterator.hasNext()) {
            base = operator.eval(base, mapper.map(iterator.next()));
        }

        return base;
     }

    public static <T> double mapReduce(Iterator<? extends T> iterator, DoubleMapper<? super T> mapper, double base, DoubleBinaryOperator operator) {
        Objects.requireNonNull(iterator);
        Objects.requireNonNull(mapper);
        Objects.requireNonNull(operator);
        while(iterator.hasNext()) {
            base = operator.eval(base, mapper.map(iterator.next()));
        }

        return base;
    }

    // @@@ toString
    // @@@ concat
    // @@@ zip

    // @@@ Overloaded concat methods for various inputs

    public static <T> Iterator<T> concat(final Iterator<? extends T> i1, final Iterator<? extends T> i2) {
        Objects.requireNonNull(i1);
        Objects.requireNonNull(i2);

        return concat(Arrays.asList(i1, i2).iterator());
    }

    public static <T> Iterator<T> concat(final Iterator<? extends Iterator<? extends T>> iterators) {
        Objects.requireNonNull(iterators);

        if (!iterators.hasNext())
            return Collections.emptyIterator();

        return new Iterator<T>() {
            private Iterator<? extends T> it = Objects.requireNonNull(iterators.next());
            // Need to retain a reference to the last iterator used with next()
            // so that remove() can use that reference for deferral and check for two or more calls,
            // and because hasNext() may update "it" to the next iterator
            private Iterator<? extends T> itForRemove = null;

            @Override
            public boolean hasNext() {
                while (!it.hasNext()) {
                    if (!iterators.hasNext()) {
                        return false;
                    }
                    it = Objects.requireNonNull(iterators.next());
                }

                return true;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                itForRemove = it;
                return it.next();
            }

            @Override
            public void remove() {
                if (itForRemove == null) {
                    throw new IllegalStateException();
                }

                itForRemove.remove();
                itForRemove = null;
            }
        };
    }
}
