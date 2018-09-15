# ValueRecordsAggregate

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
220. public int serialize(int offset, byte [] data, ValueRecordsAggregate cells)
240.         if (null != cells && cells.rowHasCells(row)) {
241.           final int rowCellSize = cells.serializeCellRow(row, pos, data);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
312. ValueRecordsAggregate rec = new ValueRecordsAggregate();
315.   rec.insertCell(val);
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
378. ValueRecordsAggregate vrAgg = (ValueRecordsAggregate)rec;
379. for (Iterator cellIter = vrAgg.getIterator();cellIter.hasNext();) {
{% endhighlight %}

***

