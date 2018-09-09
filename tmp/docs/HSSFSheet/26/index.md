# HSSFSheet @Cluster 26

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
39. private int[] createHeader(HSSFSheet sheet, List<TableColumn<T, ?>> header, int rowIdx, int clmIdx, int[] offset) {
40.   HSSFRow row = sheet.createRow(rowIdx);
61.     sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx + headerOffset[0], i + nextCellOffset, i + headerOffset[1] + nextCellOffset));
{% endhighlight %}

***

