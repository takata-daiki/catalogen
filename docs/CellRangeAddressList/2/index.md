# CellRangeAddressList @Cluster 2

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
797. CellRangeAddressList workR, sArea, workFormul;
820.           workR.addCellRangeAddress(yyy, yyy, 0, 0);
823.     if (workR != null && workR.countRanges() > 0){
825.         CellRangeAddress cellReff = workR.getCellRangeAddress(0);
832.         cellReff = workR.getCellRangeAddress(workR.countRanges()-1);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1358. CellRangeAddressList list = ExcelUtils.getSpecialCells(sh, cra, ExcelUtils.XlCellType.xlCellTypeFormulas);
1359. if (list != null && list.getSize() >0){
1360.   for(int jj=0; jj < list.countRanges(); jj++){
1361.   CellRangeAddress crb = list.getCellRangeAddress(jj);  
{% endhighlight %}

***

