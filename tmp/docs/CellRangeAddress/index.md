# CellRangeAddress

***

## [Cluster 1 (areareference, crb, excelutils)](./1)
2 results
> returns the a 1 - based index of the drawing group . 
{% highlight java %}
138. CellRangeAddress merged = getRangeIfMerged( cell,
142.     Cell topLeft = sheet.getRow( merged.getFirstRow() ).getCell( merged.getFirstColumn() );
{% endhighlight %}

***

## [Cluster 2 (cellref0, col, row)](./2)
8 results
> sets the 
{% highlight java %}
127. CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());     
131.   CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow()+deltaRows, mergedRegion.getLastRow()+deltaRows, mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());  
{% endhighlight %}

***

