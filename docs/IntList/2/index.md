# IntList @Cluster 2 (_array, intlist, rval)

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
> remove a number of merged regions of cells ( hence letting them free ) @ param indices a set of the regions to unmerge 
{% highlight java %}
342. IntList other = ( IntList ) o;
344. if (other._limit == _limit)
351.         rval = _array[ j ] == other._array[ j ];
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
> removes a set of properties from this { @ link a 1 } or { @ code null } if a formula 
{% highlight java %}
303. public boolean containsAll(final IntList c)
309.         for (int j = 0; rval && (j < c._limit); j++)
311.             if (!contains(c._array[ j ]))
{% endhighlight %}

***

### [IntList2d.java](https://searchcode.com/codesearch/view/15642640/)
> get the visibility state for a given column @ return the index of the column 
{% highlight java %}
66. IntList cols = (IntList) rows.get(row);
67. if (col >= cols.size())
70.     return cols.get( col );
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
> test that we get the same value as excel and , for 
{% highlight java %}
101. public IntList(final IntList list)
103.     this(list._array.length);
104.     System.arraycopy(list._array, 0, _array, 0, _array.length);
105.     _limit = list._limit;
{% endhighlight %}

***

### [BlockAllocationTableReader.java](https://searchcode.com/codesearch/view/15642268/)
> test that we get the same value as excel and , for 
{% highlight java %}
71. private IntList _entries;
218.         currentBlock = _entries.get(currentBlock);
240.         rval = _entries.get(index) != -1;
265.         return _entries.get(index);
300.                 raw_blocks.zap(_entries.size());
302.             _entries.add(entry);
{% endhighlight %}

***

### [IntList.java](https://searchcode.com/codesearch/view/15642643/)
> sets the record the text of the header or footer . @ param / @ param 6 
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
> verify that the list of properties in this 
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

