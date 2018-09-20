# ValueRecordsAggregate

***

## [Cluster 1](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
110. protected ValueRecordsAggregate      cells             =     null;
231.                 retval.cells.construct( k, recs );
633.             records.size(), cells.getPhysicalNumberOfCells(),
635.             records.size() + cells.getPhysicalNumberOfCells()
639.     return records.size() + cells.getPhysicalNumberOfCells()
823.     cellBlockOffset += null == cells ? 0 : cells.getRowCellBlockSize(rows.getStartRowNumberForBlock(block),
1001.     cells.insertCell(col);
1044.     cells.removeCell(col);
1089.     cells.removeCell(newval);
1090.     cells.insertCell(newval);
1245.         valueRecIterator = cells.getIterator();
2197.             if (cells != null && cells.rowHasCells(row.getRowNumber()))
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> called by the class that is responsible for writing this sucker . subclasses should implement this so that their data is passed back in a byte array . @ param offset to in bytes @ param data the data stream to serialize to . @ throws ioexception if an error occurs while writing . 
{% highlight java %}
220. public int serialize(int offset, byte [] data, ValueRecordsAggregate cells)
240.         if (null != cells && cells.rowHasCells(row)) {
241.           final int rowCellSize = cells.serializeCellRow(row, pos, data);
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> creates the < code > slideshow < / code > object that represents the slide ' s that this class belongs to . 
{% highlight java %}
312. ValueRecordsAggregate rec = new ValueRecordsAggregate();
315.   rec.insertCell(val);
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> sets the 
{% highlight java %}
378. ValueRecordsAggregate vrAgg = (ValueRecordsAggregate)rec;
379. for (Iterator cellIter = vrAgg.getIterator();cellIter.hasNext();) {
{% endhighlight %}

***

