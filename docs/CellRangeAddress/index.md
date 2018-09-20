# CellRangeAddress

***

## [Cluster 1](./1)
6 results
> returns the a 1 - based index of the drawing group . 
{% highlight java %}
138. CellRangeAddress merged = getRangeIfMerged( cell,
142.     Cell topLeft = sheet.getRow( merged.getFirstRow() ).getCell( merged.getFirstColumn() );
{% endhighlight %}

***

## [Cluster 2](./2)
34 results
> sets the 
{% highlight java %}
127. CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());     
131.   CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow()+deltaRows, mergedRegion.getLastRow()+deltaRows, mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());  
{% endhighlight %}

***

