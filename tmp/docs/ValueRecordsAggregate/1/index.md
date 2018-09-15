# ValueRecordsAggregate @Cluster 1

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
220. public int serialize(int offset, byte [] data, ValueRecordsAggregate cells)
240.         if (null != cells && cells.rowHasCells(row)) {
241.           final int rowCellSize = cells.serializeCellRow(row, pos, data);
{% endhighlight %}

***

