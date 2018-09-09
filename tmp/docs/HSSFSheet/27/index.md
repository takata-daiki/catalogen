# HSSFSheet @Cluster 27

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
94. HSSFSheet sheet = wb.createSheet();
96. HSSFRow row = sheet.createRow(rowIdx);
100.   sheet.setColumnWidth(i, (int) (258 / 8 * columnWidths.get(i)));
113.   row = sheet.getRow(rowIdx + headerDepth - 1);
117.       sheet.setColumnWidth(i, (int) (258 / 8 * column.getPrefWidth()));
131.   row = sheet.createRow(rowIdx);
{% endhighlight %}

***

