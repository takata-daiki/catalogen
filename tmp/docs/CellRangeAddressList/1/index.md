# CellRangeAddressList @Cluster 1

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
451. CellRangeAddressList rangeList = new CellRangeAddressList();
471.   for(CellRangeAddress crA:rangeListForColumns.get(0)) rangeList.addCellRangeAddress(crA);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1211. public static void hiddenRows(HSSFSheet sh, CellRangeAddressList list, boolean hide){
1213.     for(int jj=0; jj<list.countRanges(); jj++){
1214.       CellRangeAddress cra = list.getCellRangeAddress(jj);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
992. public static CellRangeAddressList getEntireRow(CellRangeAddressList cellsRefs){
994.   for(int i=0; i<cellsRefs.countRanges(); i++){
995.     CellRangeAddress crA  = cellsRefs.getCellRangeAddress(i);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
963. public static CellRangeAddressList getEntireColumn(CellRangeAddressList cellsRefs){
965.   for(int i=0; i<cellsRefs.countRanges(); i++){
966.     CellRangeAddress crA  = cellsRefs.getCellRangeAddress(i);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1332. CellRangeAddressList l = ExcelUtils.getEntireRow(list);
1334.   for(int ii=0; ii<l.countRanges(); ii++){
1335.     CellRangeAddress workRange = l.getCellRangeAddress(ii);
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

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
893. CellRangeAddressList workR, sArea;
906.         workR.addCellRangeAddress(yyy, yyy, 0, 0);
911.       CellRangeAddress cRA =  workR.getCellRangeAddress(workR.countRanges()-1);
918.       for(int i=0; i< workR.countRanges(); i++){
920.           begRow = workR.getCellRangeAddress(i).getLastRow()+1; 
924.           sh.groupRow(begRow, workR.getCellRangeAddress(i).getFirstRow()-1);
925.           begRow = workR.getCellRangeAddress(i).getLastRow()+1;
929.         for(int i=0; i< workR.countRanges(); i++){
930.           cRA = ExcelUtils.intersectRectangular(workR.getCellRangeAddress(i), 
{% endhighlight %}

***

