# CellRangeAddress @Cluster 6

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
911. CellRangeAddress cRA =  workR.getCellRangeAddress(workR.countRanges()-1);
913.     ExcelUtils.getCell(sh, cRA.getLastRow()+ 1, formatCol));
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

