# CellRangeAddressList

***

### [Cluster 1](./1)
{% highlight java %}
451. CellRangeAddressList rangeList = new CellRangeAddressList();
471.   for(CellRangeAddress crA:rangeListForColumns.get(0)) rangeList.addCellRangeAddress(crA);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
797. CellRangeAddressList workR, sArea, workFormul;
820.           workR.addCellRangeAddress(yyy, yyy, 0, 0);
823.     if (workR != null && workR.countRanges() > 0){
825.         CellRangeAddress cellReff = workR.getCellRangeAddress(0);
832.         cellReff = workR.getCellRangeAddress(workR.countRanges()-1);
{% endhighlight %}

***

### [Cluster 3](./3)
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

