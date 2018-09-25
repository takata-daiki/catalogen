# ColumnInfoRecord @Cluster 3 (ci, column, columninforecord)

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> check that and return a list of the font in the table . 
{% highlight java %}
305. ColumnInfoRecord columnInfo = (ColumnInfoRecord) records.get( findStartOfColumnOutlineGroup( idx ) );
311. setColumn( (short) ( columnInfo.getLastColumn() + 1 ), null, null, null, null, Boolean.TRUE);
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> set the font to be stricken out or not @ return strike - whether the font is stricken out or not 
{% highlight java %}
217. ColumnInfoRecord nextColumnInfo = (ColumnInfoRecord) records.get( idx + 1 );
218. if (columnInfo.getLastColumn() + 1 == nextColumnInfo.getFirstColumn())
220.     if (nextColumnInfo.getOutlineLevel() < level)
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> sets the 2 - d index of the from the this returns the return the result of the paragraph @ param value the value of the paragraph to be styles 
{% highlight java %}
181. ColumnInfoRecord columnInfo = (ColumnInfoRecord) records.get( idx );
182. int level = columnInfo.getOutlineLevel();
186.     if (columnInfo.getLastColumn() + 1 == nextColumnInfo.getFirstColumn())
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> sets the 2 - d index of the from the this returns the return the result of the paragraph @ param value the value to set this if the specified part is one . 
{% highlight java %}
185. ColumnInfoRecord nextColumnInfo = (ColumnInfoRecord) records.get( idx + 1 );
186. if (columnInfo.getLastColumn() + 1 == nextColumnInfo.getFirstColumn())
188.     if (nextColumnInfo.getOutlineLevel() < level)
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> sets whether the rowcolheadings are shown in a viewer @ param show whether to show rowcolheadings or not 
{% highlight java %}
326. ColumnInfoRecord columnInfo = getColInfo( startIdx );
343.         if (columnInfo.getOutlineLevel() == getColInfo(i).getOutlineLevel())
349. setColumn( (short) ( columnInfo.getLastColumn() + 1 ), null, null, null, null, Boolean.FALSE);
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> sets the 2 - d index of the from the slide @ param index the sheet index of the paragraph in the range of @ throws illegalargumentexception if the index is out of range ( index & lt ; 0 | | index & gt ; = getnumberofsheets ( ) ) . 
{% highlight java %}
155. ColumnInfoRecord columnInfo = (ColumnInfoRecord) records.get( idx );
156. int level = columnInfo.getOutlineLevel();
160.     if (columnInfo.getFirstColumn() - 1 == prevColumnInfo.getLastColumn())
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> sets the 2 - d index of the from the slide @ param text the , one of the { @ code 0 x 2 1 6 0 } or { @ code 0 x 3 d 5 0 } ) 
{% highlight java %}
159. ColumnInfoRecord prevColumnInfo = (ColumnInfoRecord) records.get( idx - 1 );
160. if (columnInfo.getFirstColumn() - 1 == prevColumnInfo.getLastColumn())
162.     if (prevColumnInfo.getOutlineLevel() < level)
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> test that we get the same value as excel and , for 
{% highlight java %}
209. public ColumnInfoRecord writeHidden( ColumnInfoRecord columnInfo, int idx, boolean hidden )
211.     int level = columnInfo.getOutlineLevel();
214.         columnInfo.setHidden( hidden );
218.             if (columnInfo.getLastColumn() + 1 == nextColumnInfo.getFirstColumn())
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> set the font weight to this style @ param @ see org . apache . poi . hslf . usermodel . xssfworkbook # < code > false < / code > 
{% highlight java %}
1817. ColumnInfoRecord ci     = null;
1825.         if ((ci.getFirstColumn() <= column)
1826.                 && (column <= ci.getLastColumn()))
1835.     retval = ci.getColumnWidth();
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> check if the cell is in the specified cell range @ return < code > true < / code > if the specified sheet is in the specified cell range 
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
> sets the bottom margin within the textbox . 
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

