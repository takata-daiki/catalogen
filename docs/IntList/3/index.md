# IntList @Cluster 3

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

