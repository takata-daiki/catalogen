# CellRangeAddress

***

### [Cluster 1](./1)
{% highlight java %}
147. CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
148. if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
151.                       newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
152.                       cellRangeAddress.getFirstColumn(),
153.                       cellRangeAddress.getLastColumn());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
33. CellRangeAddress rangeAddresses = cellRangeAddresses[0];
34. assertEquals(4,rangeAddresses.getFirstColumn());
35. assertEquals(4,rangeAddresses.getLastColumn());
36. assertEquals(11,rangeAddresses.getFirstRow());
37. assertEquals(11,rangeAddresses.getLastRow());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
1335. CellRangeAddress workRange = l.getCellRangeAddress(ii);
1336. for(int row = workRange.getFirstRow(); row <= workRange.getLastRow(); row++)
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
607. public static boolean equalsRectangularAreas(CellRangeAddress cRA1, CellRangeAddress cRA2){
608.   return (cRA1.getLastRow()-cRA1.getFirstRow() == cRA2.getLastRow()-cRA2.getFirstRow() &&
609.       cRA1.getLastColumn()-cRA1.getFirstColumn() == cRA2.getLastColumn()-cRA2.getFirstColumn());
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
653. public static void autoFilter(HSSFSheet sh, CellRangeAddress cellsRef, Object filterValue){
654.   autoFilter(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
655.       new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), filterValue);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
911. CellRangeAddress cRA =  workR.getCellRangeAddress(workR.countRanges()-1);
913.     ExcelUtils.getCell(sh, cRA.getLastRow()+ 1, formatCol));
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
1355. CellRangeAddress cra = new CellRangeAddress(
1373.     cell  =  ExcelUtils.getCell(sh, rowNameDataRangeE, cra.getFirstColumn());
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
613. public static void fill(HSSFSheet sh, CellRangeAddress srcCRA, CellRangeAddress distCRA, byte lookIn){
618.       HSSFRow srcRow = getRow(sh, srcCRA.getFirstRow()+(countRow++));
622.         HSSFCell srcCell = getCell(srcRow, srcCRA.getFirstColumn()+(countCol++));
{% endhighlight %}

***

