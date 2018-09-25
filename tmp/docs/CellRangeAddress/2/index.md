# CellRangeAddress @Cluster 2 (cellref0, col, row)

***

### [ThExtraerNotas.java](https://searchcode.com/codesearch/view/92190361/)
> sets the 
{% highlight java %}
127. CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());     
131.   CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow()+deltaRows, mergedRegion.getLastRow()+deltaRows, mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());  
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> < p > sets the poi file ' s path . < / p > 
{% highlight java %}
825. CellRangeAddress cellReff = workR.getCellRangeAddress(0);
826. if(cellReff.getLastRow()-cellReff.getFirstRow()+1 != kFRowE){
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> this method returns the font to be used to style the table . < p > it must be in the range [ 0 , 1 2 ] . @ return the 
{% highlight java %}
1335. CellRangeAddress workRange = l.getCellRangeAddress(ii);
1336. for(int row = workRange.getFirstRow(); row <= workRange.getLastRow(); row++)
{% endhighlight %}

***

### [ThExtraerNotas.java](https://searchcode.com/codesearch/view/92190361/)
> returns true if the " set " of the table , value for 0 . < p > works 1 0 0 0 and 1 2 0 0 . 
{% highlight java %}
202. CellRangeAddress merged = sheet.getMergedRegion(i);     
203. if (merged.isInRange(rowNum, cellNum)) {     
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> test that we get the same value as excel and , for 
{% highlight java %}
250. public static Boolean hasFormula (HSSFSheet sh, CellRangeAddress cRA){
253.   for(int row=cRA.getFirstRow(); row <= cRA.getLastRow(); row++){
256.       for(int col=cRA.getFirstColumn(); col <= cRA.getLastColumn(); col++){
{% endhighlight %}

***

### [SheetXSSFImplTest.java](https://searchcode.com/codesearch/view/72853788/)
> tests that the create aggregate method correctly of an existing workbook with a @ throws illegalstateexception if the cell type returned by { @ link # getcelltypeenum ( ) } isn ' t { @ link celltype # formula } 
{% highlight java %}
33. CellRangeAddress rangeAddresses = cellRangeAddresses[0];
34. assertEquals(4,rangeAddresses.getFirstColumn());
35. assertEquals(4,rangeAddresses.getLastColumn());
36. assertEquals(11,rangeAddresses.getFirstRow());
37. assertEquals(11,rangeAddresses.getLastRow());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> sets the list of colours that are interpolated between . the number must match { @ link # / _ or } , list of . @ param . 
{% highlight java %}
414. public static CellRangeAddress intersectRectangular(CellRangeAddress crA, CellRangeAddress crB){
415.   boolean isIntersect =  !( crB.getFirstColumn() > crA.getLastColumn()
416.               || crB.getLastColumn() < crA.getFirstColumn()
417.               || crB.getFirstRow() > crA.getLastRow()
418.               || crB.getLastRow() < crA.getFirstRow()
422.       new CellRangeAddress(Math.max(crA.getFirstRow(), crB.getFirstRow()), 
423.                  Math.min(crA.getLastRow(), crB.getLastRow()),
424.                  Math.max(crA.getFirstColumn(), crB.getFirstColumn()), 
425.                  Math.min(crA.getLastColumn(), crB.getLastColumn()));
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> the ( 0 based ) number of the column number . 
{% highlight java %}
414. public static CellRangeAddress intersectRectangular(CellRangeAddress crA, CellRangeAddress crB){
415.   boolean isIntersect =  !( crB.getFirstColumn() > crA.getLastColumn()
416.               || crB.getLastColumn() < crA.getFirstColumn()
417.               || crB.getFirstRow() > crA.getLastRow()
418.               || crB.getLastRow() < crA.getFirstRow()
422.       new CellRangeAddress(Math.max(crA.getFirstRow(), crB.getFirstRow()), 
423.                  Math.min(crA.getLastRow(), crB.getLastRow()),
424.                  Math.max(crA.getFirstColumn(), crB.getFirstColumn()), 
425.                  Math.min(crA.getLastColumn(), crB.getLastColumn()));
{% endhighlight %}

***

