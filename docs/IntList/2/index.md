# IntList @Cluster 2

***

### [IntList2d.java](https://searchcode.com/codesearch/view/15642640/)
{% highlight java %}
99. IntList cols = (IntList) rows.get( row );
100. return ( col < cols.size() );
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
{% highlight java %}
571. public boolean retainAll(final IntList c)
577.         if (!c.contains(_array[ j ]))
{% endhighlight %}

***

### [IntList2d.java](https://searchcode.com/codesearch/view/15642640/)
{% highlight java %}
78. IntList cols = (IntList) rows.get( row );
79. cols.set( col, value );
{% endhighlight %}

***

### [IntList2d.java](https://searchcode.com/codesearch/view/15642640/)
{% highlight java %}
90. IntList cols = (IntList) rows.get( row );
91. while (cols.size() <= col)
92.     cols.add(0);
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
{% highlight java %}
545. public boolean removeAll(final IntList c)
549.     for (int j = 0; j < c._limit; j++)
551.         if (removeValue(c._array[ j ]))
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
{% highlight java %}
342. IntList other = ( IntList ) o;
344. if (other._limit == _limit)
351.         rval = _array[ j ] == other._array[ j ];
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
{% highlight java %}
303. public boolean containsAll(final IntList c)
309.         for (int j = 0; rval && (j < c._limit); j++)
311.             if (!contains(c._array[ j ]))
{% endhighlight %}

***

### [IntList2d.java](https://searchcode.com/codesearch/view/15642640/)
{% highlight java %}
66. IntList cols = (IntList) rows.get(row);
67. if (col >= cols.size())
70.     return cols.get( col );
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
{% highlight java %}
101. public IntList(final IntList list)
103.     this(list._array.length);
104.     System.arraycopy(list._array, 0, _array, 0, _array.length);
105.     _limit = list._limit;
{% endhighlight %}

***

### [BlockAllocationTableReader.java](https://searchcode.com/codesearch/view/97398057/)
{% highlight java %}
58. private final IntList _entries;
225.             currentBlock = _entries.get(currentBlock);
259.         return _entries.get(index) != -1;
279.         return _entries.get(index);
305.                 raw_blocks.zap(_entries.size());
307.             _entries.add(entry);
{% endhighlight %}

***

### [BlockAllocationTableReader.java](https://searchcode.com/codesearch/view/15642268/)
{% highlight java %}
71. private IntList _entries;
218.         currentBlock = _entries.get(currentBlock);
240.         rval = _entries.get(index) != -1;
265.         return _entries.get(index);
300.                 raw_blocks.zap(_entries.size());
302.             _entries.add(entry);
{% endhighlight %}

***

### [BlockAllocationTableWriter.java](https://searchcode.com/codesearch/view/15642252/)
{% highlight java %}
74. private IntList    _entries;
105.                                                   + _entries.size(), size);
141.     int startBlock = _entries.size();
150.             _entries.add(index++);
152.         _entries.add(POIFSConstants.END_OF_CHAIN);
174.     _blocks = BATBlock.createBATBlocks(_entries.toArray(), size);
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
{% highlight java %}
199. public boolean addAll(final IntList c)
201.     if (c._limit != 0)
203.         if ((_limit + c._limit) > _array.length)
205.             growArray(_limit + c._limit);
207.         System.arraycopy(c._array, 0, _array, _limit, c._limit);
208.         _limit += c._limit;
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
{% highlight java %}
235. public boolean addAll(final int index, final IntList c)
241.     if (c._limit != 0)
243.         if ((_limit + c._limit) > _array.length)
245.             growArray(_limit + c._limit);
249.         System.arraycopy(_array, index, _array, index + c._limit,
253.         System.arraycopy(c._array, 0, _array, index, c._limit);
254.         _limit += c._limit;
{% endhighlight %}

***

