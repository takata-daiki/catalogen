# IntList

***

### [Cluster 1](./1)
{% highlight java %}
101. public IntList(final IntList list)
103.     this(list._array.length);
104.     System.arraycopy(list._array, 0, _array, 0, _array.length);
105.     _limit = list._limit;
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
66. IntList cols = (IntList) rows.get(row);
67. if (col >= cols.size())
70.     return cols.get( col );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
74. private IntList    _entries;
105.                                                   + _entries.size(), size);
141.     int startBlock = _entries.size();
150.             _entries.add(index++);
152.         _entries.add(POIFSConstants.END_OF_CHAIN);
174.     _blocks = BATBlock.createBATBlocks(_entries.toArray(), size);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
67. public IntList            field_5_dbcells;         // array of offsets to DBCELL records
103.         field_5_dbcells.add(in.readInt());
123.     field_5_dbcells.add(cell);
128.     field_5_dbcells.set(cell, value);
147.     return field_5_dbcells.size();
152.     return field_5_dbcells.get(cellnum);
213.   rec.field_5_dbcells.addAll(field_5_dbcells);
{% endhighlight %}

***

