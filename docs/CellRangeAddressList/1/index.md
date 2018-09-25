# CellRangeAddressList @Cluster 1 (areas, begrow, iarea)

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> test that we get the same value as excel and , for 
{% highlight java %}
451. CellRangeAddressList rangeList = new CellRangeAddressList();
471.   for(CellRangeAddress crA:rangeListForColumns.get(0)) rangeList.addCellRangeAddress(crA);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> @ param < i > true < / code > if the specified record id normally appears in the range ( s ) may be a < code > null < / code > which was used to 
{% highlight java %}
797. CellRangeAddressList workR, sArea, workFormul;
820.           workR.addCellRangeAddress(yyy, yyy, 0, 0);
823.     if (workR != null && workR.countRanges() > 0){
825.         CellRangeAddress cellReff = workR.getCellRangeAddress(0);
832.         cellReff = workR.getCellRangeAddress(workR.countRanges()-1);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param picture the data to be @ throws illegalargumentexception if the size of the . 
{% highlight java %}
841. CellRangeAddressList areas =  ExcelUtils.getEntireRow(workR);
842. for(int iArea=0; iArea < areas.countRanges(); iArea++)
843.   ExcelUtils.paste(sh, areas.getCellRangeAddress(iArea), buff, ExcelUtils.xlFormats);
855.             areas.getCellRangeAddress(0));
863.   for(int iArea=0; iArea < areas.countRanges(); iArea++){
865.         areas.getCellRangeAddress(iArea));
872.     for(int iArea=0; iArea < areas.countRanges(); iArea++){
878.               areas.getCellRangeAddress(iArea));
{% endhighlight %}

***

