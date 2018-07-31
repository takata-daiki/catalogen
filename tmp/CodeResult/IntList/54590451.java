/*
 * This file is part of seadams Utils.
 *
 * Copyright (c) 2008-2011 Sam Adams <seadams@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.seadams.util.collections;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class IntList extends AbstractList<Integer> implements RandomAccess {

    private int[] data;
    private int size;
    private int modCount = 0;

    public IntList() {
        this(10);
    }

    public IntList(int initCapacity) {
        data = new int[initCapacity];
    }

    public IntList(int[] a) {
        size = a.length;
        data = new int[1+(int)(size*1.1)];
        System.arraycopy(a, 0, data, 0, size);
    }

    public IntList(Collection<Integer> c) {
        int capacity = c.size();
        data = new int[1+(int)(capacity*1.1)];
        for (Integer i : c) {
            data[size++] = i.intValue();
        }
    }

    public boolean addInt(int i) {
        ensureCapacity(size + 1);
        data[size++] = i;
        modCount++;
        return true;
    }

    public void addInt(int index, int i) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
        ensureCapacity(size+1);
        System.arraycopy(data, index, data, index+1, size-index);
        data[index] = i;
        modCount++;
        size++;
    }

    @Override
    public boolean add(Integer o) {
        return addInt(o);
    }

    @Override
    public void add(int index, Integer i) {
        addInt(index, i);
    }

    public void addAll(int[] a) {
        ensureCapacity(size + a.length);
        System.arraycopy(a, 0, data, size, a.length);
        size += a.length;
        modCount++;
    }

    public void addAll(IntList list) {
        int n = list.size;
        ensureCapacity(size + n);
        System.arraycopy(list.data, 0, data, size, n);
        size += n;
        modCount++;
    }

    @Override
    public Integer set(int index, Integer i) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
        int ret = data[index];
        data[index] = i;
        modCount++;
        return ret;
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(minCapacity, 2*data.length);
            int[] temp = new int[newCapacity];
            System.arraycopy(data, 0, temp, 0, size);
            data = temp;
        }
    }

    public int getInt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:"+index+", Size:"+size);
        }
        return data[index];
    }

    @Override
    public Integer get(int index) {
        return getInt(index);
    }

    public Integer remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:"+index+", Size:"+size);
        }
        int n = data[index];
        System.arraycopy(data, index+1, data, index, size-index-1);
        size--;
        return n;
    }

    public boolean removeInt(int value) {
        IntIterator it = iterator();
        while (it.hasNext()) {
            if (value == it.nextInt()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        modCount++;
    }

    public boolean containsInt(int o) {
        for (int i = 0; i < size; i++) {
            if (data[i] == o) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int[] toIntArray() {
        int[] a = new int[size];
        System.arraycopy(data, 0, a, 0, size);
        return a;
    }

    @Override
    public IntIterator iterator() {
        return new Itr();
    }

    class Itr implements IntIterator {

        private int cursor;
        private int lastRet = -1;
        private int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size;
        }

        public int nextInt() {
            checkForComodification();
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            lastRet = cursor;
            return data[cursor++];
        }

        public Integer next() {
            return nextInt();
        }

        public void remove() {
            if (lastRet == -1) {
                throw new IllegalStateException();
            }
            checkForComodification();
            IntList.this.remove(lastRet);
            lastRet = -1;
            cursor--;
            expectedModCount = modCount;
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

    }

    @Override
    public IntList clone() {
        IntList copy;
        try {
            copy = (IntList)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        copy.data = new int[data.length];
        System.arraycopy(data, 0, copy.data, 0, size);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IntList) {
            IntList l = (IntList) o;
            if (size == l.size) {
                for (int i = 0; i < size; i++) {
                    if (data[i] != l.data[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (int i : data) {
        	hashCode = 31 * hashCode + i;	// Integer(i).hashcode() = i;
        }
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('[');
        for (IntIterator i = iterator(); i.hasNext();) {
            s.append(i.nextInt());
            if (i.hasNext()) {
                s.append(", ");
            }
        }
        s.append(']');
        return s.toString();
    }

    public void setInts(int[] ints) {
        size = ints.length;
        ensureCapacity(size);
        System.arraycopy(ints, 0, data, 0, size);
        modCount++;
    }

    public void sort() {
        Arrays.sort(data, 0, size);
        modCount++;
    }

}
