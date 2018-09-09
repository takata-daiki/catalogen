# ValueRecordsAggregate

***

### [Cluster 1](./1)
{% highlight java %}
378. ValueRecordsAggregate vrAgg = (ValueRecordsAggregate)rec;
379. for (Iterator cellIter = vrAgg.getIterator();cellIter.hasNext();) {
{% endhighlight %}

***

### [Cluster 2](./2)
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

