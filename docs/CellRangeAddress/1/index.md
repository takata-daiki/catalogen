# CellRangeAddress @Cluster 1

***

### [ExcelParser.java](https://searchcode.com/codesearch/view/93105691/)
{% highlight java %}
138. CellRangeAddress merged = getRangeIfMerged( cell,
142.     Cell topLeft = sheet.getRow( merged.getFirstRow() ).getCell( merged.getFirstColumn() );
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
911. CellRangeAddress cRA =  workR.getCellRangeAddress(workR.countRanges()-1);
913.     ExcelUtils.getCell(sh, cRA.getLastRow()+ 1, formatCol));
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1355. CellRangeAddress cra = new CellRangeAddress(
1373.     cell  =  ExcelUtils.getCell(sh, rowNameDataRangeE, cra.getFirstColumn());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
613. public static void fill(HSSFSheet sh, CellRangeAddress srcCRA, CellRangeAddress distCRA, byte lookIn){
618.       HSSFRow srcRow = getRow(sh, srcCRA.getFirstRow()+(countRow++));
622.         HSSFCell srcCell = getCell(srcRow, srcCRA.getFirstColumn()+(countCol++));
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1361. CellRangeAddress crb = list.getCellRangeAddress(jj);  
1362. wRHidden = ExcelUtils.getRow(sh, crb.getFirstRow()).getZeroHeight();
1363. HSSFCell cell = ExcelUtils.getCell(sh, crb.getFirstRow(), crb.getFirstColumn());
1378.   AreaReference area3 = new AreaReference(crb.formatAsString());
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
2000. CellRangeAddress workMergeRef1 = ExcelUtils.mergeArea(activeSheet, new CellReference(cellAHRef.getFirstCell().getRow(), lastCol + 1));
2009. HSSFCell srcCell = ExcelUtils.getCell(activeSheet, workMergeRef1.getFirstRow(), workMergeRef1.getFirstColumn());
2014. for(int row=workMergeRef1.getFirstRow(); row <=workMergeRef1.getLastRow(); row++)
2015.   for(int col=workMergeRef1.getFirstColumn(); col <=workMergeRef1.getLastColumn(); col++)
{% endhighlight %}

***

