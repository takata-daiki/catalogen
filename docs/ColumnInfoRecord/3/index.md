# ColumnInfoRecord @Cluster 3

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
305. ColumnInfoRecord columnInfo = (ColumnInfoRecord) records.get( findStartOfColumnOutlineGroup( idx ) );
311. setColumn( (short) ( columnInfo.getLastColumn() + 1 ), null, null, null, null, Boolean.TRUE);
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
485. ColumnInfoRecord ci;
489.     if ((ci.getFirstColumn() <= column)
490.             && (column <= ci.getLastColumn()))
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
217. ColumnInfoRecord nextColumnInfo = (ColumnInfoRecord) records.get( idx + 1 );
218. if (columnInfo.getLastColumn() + 1 == nextColumnInfo.getFirstColumn())
220.     if (nextColumnInfo.getOutlineLevel() < level)
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
181. ColumnInfoRecord columnInfo = (ColumnInfoRecord) records.get( idx );
182. int level = columnInfo.getOutlineLevel();
186.     if (columnInfo.getLastColumn() + 1 == nextColumnInfo.getFirstColumn())
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
185. ColumnInfoRecord nextColumnInfo = (ColumnInfoRecord) records.get( idx + 1 );
186. if (columnInfo.getLastColumn() + 1 == nextColumnInfo.getFirstColumn())
188.     if (nextColumnInfo.getOutlineLevel() < level)
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
326. ColumnInfoRecord columnInfo = getColInfo( startIdx );
343.         if (columnInfo.getOutlineLevel() == getColInfo(i).getOutlineLevel())
349. setColumn( (short) ( columnInfo.getLastColumn() + 1 ), null, null, null, null, Boolean.FALSE);
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
155. ColumnInfoRecord columnInfo = (ColumnInfoRecord) records.get( idx );
156. int level = columnInfo.getOutlineLevel();
160.     if (columnInfo.getFirstColumn() - 1 == prevColumnInfo.getLastColumn())
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
159. ColumnInfoRecord prevColumnInfo = (ColumnInfoRecord) records.get( idx - 1 );
160. if (columnInfo.getFirstColumn() - 1 == prevColumnInfo.getLastColumn())
162.     if (prevColumnInfo.getOutlineLevel() < level)
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1860. ColumnInfoRecord ci = null;
1866.         if ((ci.getFirstColumn() <= column)
1867.                 && (column <= ci.getLastColumn())) {
1873. retval = (ci != null) ? ci.getXFIndex() : 0xF;
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1899. ColumnInfoRecord ci     = null;
1906.         if ((ci.getFirstColumn() <= column)
1907.                 && (column <= ci.getLastColumn()))
1916.     retval = ci.getHidden();
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
209. public ColumnInfoRecord writeHidden( ColumnInfoRecord columnInfo, int idx, boolean hidden )
211.     int level = columnInfo.getOutlineLevel();
214.         columnInfo.setHidden( hidden );
218.             if (columnInfo.getLastColumn() + 1 == nextColumnInfo.getFirstColumn())
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1817. ColumnInfoRecord ci     = null;
1825.         if ((ci.getFirstColumn() <= column)
1826.                 && (column <= ci.getLastColumn()))
1835.     retval = ci.getColumnWidth();
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
503. ColumnInfoRecord previousCol = (ColumnInfoRecord) records.get( columnIdx - 1);
505. boolean adjacentColumns = previousCol.getLastColumn() == currentCol.getFirstColumn() - 1;
510.         previousCol.getXFIndex() == currentCol.getXFIndex() &&
511.         previousCol.getOptions() == currentCol.getOptions() &&
512.         previousCol.getColumnWidth() == currentCol.getColumnWidth();
516.     previousCol.setLastColumn( currentCol.getLastColumn() );
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
504. ColumnInfoRecord currentCol = (ColumnInfoRecord) records.get( columnIdx );
505. boolean adjacentColumns = previousCol.getLastColumn() == currentCol.getFirstColumn() - 1;
510.         previousCol.getXFIndex() == currentCol.getXFIndex() &&
511.         previousCol.getOptions() == currentCol.getOptions() &&
512.         previousCol.getColumnWidth() == currentCol.getColumnWidth();
516.     previousCol.setLastColumn( currentCol.getLastColumn() );
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
431. ColumnInfoRecord nci = ( ColumnInfoRecord ) createColInfo();
432. nci.setFirstColumn(column);
433. nci.setLastColumn(column);
434. nci.setOptions(ci.getOptions());
435. nci.setXFIndex(ci.getXFIndex());
440. nci.setFirstColumn((short)(column+1));
441. nci.setLastColumn(lastcolumn);
444. nci.setColumnWidth(ci.getColumnWidth());
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
371.   ColumnInfoRecord ci = null;
377.       if ((ci.getFirstColumn() <= column)
378.               && (column <= ci.getLastColumn()))
387. boolean styleChanged = xfIndex != null && ci.getXFIndex() != xfIndex.shortValue();
388.       boolean widthChanged = width != null && ci.getColumnWidth() != width.shortValue();
389.       boolean levelChanged = level != null && ci.getOutlineLevel() != level.intValue();
390.       boolean hiddenChanged = hidden != null && ci.getHidden() != hidden.booleanValue();
391.       boolean collapsedChanged = collapsed != null && ci.getCollapsed() != collapsed.booleanValue();
397.       else if ((ci.getFirstColumn() == column)
398.                && (ci.getLastColumn() == column))
403.                || (ci.getLastColumn() == column))
407.           if (ci.getFirstColumn() == column)
409.               ci.setFirstColumn(( short ) (column + 1));
413.               ci.setLastColumn(( short ) (column - 1));
419.           nci.setOptions(ci.getOptions());
420.           nci.setXFIndex(ci.getXFIndex());
428.           short lastcolumn = ci.getLastColumn();
429.           ci.setLastColumn(( short ) (column - 1));
444.           nci.setColumnWidth(ci.getColumnWidth());
{% endhighlight %}

***

