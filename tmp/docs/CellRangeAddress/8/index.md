# CellRangeAddress @Cluster 8

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
613. public static void fill(HSSFSheet sh, CellRangeAddress srcCRA, CellRangeAddress distCRA, byte lookIn){
618.       HSSFRow srcRow = getRow(sh, srcCRA.getFirstRow()+(countRow++));
622.         HSSFCell srcCell = getCell(srcRow, srcCRA.getFirstColumn()+(countCol++));
{% endhighlight %}

***

