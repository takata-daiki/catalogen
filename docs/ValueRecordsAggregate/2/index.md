# ValueRecordsAggregate @Cluster 2 (cells, data, int)

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
> called by the class that is responsible for writing this sucker . subclasses should implement this so that their data is passed back in a byte array . @ param offset to in bytes @ param data the data stream to serialize to . @ throws ioexception if an error occurs while writing . 
{% highlight java %}
220. public int serialize(int offset, byte [] data, ValueRecordsAggregate cells)
240.         if (null != cells && cells.rowHasCells(row)) {
241.           final int rowCellSize = cells.serializeCellRow(row, pos, data);
{% endhighlight %}

***

