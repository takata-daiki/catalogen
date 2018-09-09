# CellRangeAddress @Cluster 3

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
250. public static Boolean hasFormula (HSSFSheet sh, CellRangeAddress cRA){
253.   for(int row=cRA.getFirstRow(); row <= cRA.getLastRow(); row++){
256.       for(int col=cRA.getFirstColumn(); col <= cRA.getLastColumn(); col++){
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
613. public static void fill(HSSFSheet sh, CellRangeAddress srcCRA, CellRangeAddress distCRA, byte lookIn){
616.     for(int row = distCRA.getFirstRow(); row <=distCRA.getLastRow(); row++){
620.       for(int col = distCRA.getFirstColumn(); col <=distCRA.getLastColumn(); col++){
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
966. CellRangeAddress crA  = cellsRefs.getCellRangeAddress(i);
967. for(int row =crA.getFirstRow(); row<=crA.getLastRow(); row++)
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
995. CellRangeAddress crA  = cellsRefs.getCellRangeAddress(i);
996. for(int row =crA.getFirstRow(); row<=crA.getLastRow(); row++){
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1214. CellRangeAddress cra = list.getCellRangeAddress(jj);
1215. for(int row = cra.getFirstRow(); row <= cra.getLastRow(); row++)
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
481. public static CellRangeAddressList getSpecialCells(HSSFSheet sh, CellRangeAddress cellRef0, XlCellType xlCellType){
501.       for(int col = cellRef0.getFirstColumn(); col <= cellRef0.getLastColumn(); col++)
504.           for(int row = cellRef0.getFirstRow(); row <= cellRef0.getLastRow(); row++)
526.       for(int col = cellRef0.getFirstColumn(); col <= cellRef0.getLastColumn(); col++){
528.         for(int row = cellRef0.getFirstRow(); row <= cellRef0.getLastRow(); row++){
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1335. CellRangeAddress workRange = l.getCellRangeAddress(ii);
1336. for(int row = workRange.getFirstRow(); row <= workRange.getLastRow(); row++)
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1368. CellRangeAddress cra2 = ExcelUtils.mergeArea(sh, area2.getAllReferencedCells()[0]);
1370. for (int row = cra2.getFirstRow(); row <=cra2.getLastRow(); row++)
{% endhighlight %}

***

