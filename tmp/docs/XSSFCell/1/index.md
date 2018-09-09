# XSSFCell @Cluster 1

***

### [Table2XLSX.java](https://searchcode.com/codesearch/view/115088748/)
{% highlight java %}
65. final XSSFCell xssfCell = xssfHeaderRow.createCell(information.getSpaltenNr());
66. xssfCell.setCellValue(information.getSpaltenName());
67. xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
{% endhighlight %}

***

### [Table2XLSX.java](https://searchcode.com/codesearch/view/115088748/)
{% highlight java %}
81. final XSSFCell xssfCell = xssfRow.createCell(cell.getColInfo().getSpaltenNr());
84.     xssfCell.setCellValue(cell.getFormattedValue());
85.     xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING); //JIRA MOD-32 CellType in Abh채ngigkeit der ValueClass z.B. Number
91.     xssfCell.setCellValue(cell.getLabel());
92.     xssfCell.setHyperlink(xssfHyperlink);
95.     xssfCell.setCellStyle(hlink_style);
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
{% highlight java %}
167. XSSFCell xssfCell = (XSSFCell) cell;
168. XSSFCellStyle xssfCellStyle = xssfCell.getCellStyle();
{% endhighlight %}

***

### [ExcelReader.java](https://searchcode.com/codesearch/view/14046020/)
{% highlight java %}
105. XSSFCell cell;
121.           int cellType = cell.getCellType();
{% endhighlight %}

***

### [ExcelReader.java](https://searchcode.com/codesearch/view/14046020/)
{% highlight java %}
169. private static String getCellValue(XSSFCell cell, int cellType) {
174.       Date date = cell.getDateCellValue();
178.     returnvalue = String.valueOf(cell.getNumericCellValue());
181.     returnvalue = cell.toString();
184.     returnvalue = cell.getBooleanCellValue() ? "true" : "false";
{% endhighlight %}

***

### [ExcelToDicReader.java](https://searchcode.com/codesearch/view/14046019/)
{% highlight java %}
100. XSSFCell cell;
112.           int cellType = cell.getCellType();
{% endhighlight %}

***

### [ExcelToDicReader.java](https://searchcode.com/codesearch/view/14046019/)
{% highlight java %}
168. private static String get2007CellValue(XSSFCell cell, int cellType) {
173.       Date date = cell.getDateCellValue();
177.     returnvalue = String.valueOf(cell.getNumericCellValue());
180.     returnvalue = cell.toString();
183.     returnvalue = cell.getBooleanCellValue() ? "true" : "false";
{% endhighlight %}

***

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
{% highlight java %}
111. XSSFCell xc = (XSSFCell) cell;
112. String rawValue = xc.getRawValue();
{% endhighlight %}

***

### [FilteredTableContextMenu.java](https://searchcode.com/codesearch/view/115088176/)
{% highlight java %}
302. final XSSFCell xssfCell = xssfHeaderRow.createCell(colNr);
304. xssfCell.setCellValue(columnText);
305. xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
{% endhighlight %}

***

### [FilteredTableContextMenu.java](https://searchcode.com/codesearch/view/115088176/)
{% highlight java %}
317. final XSSFCell xssfCell = xssfRow.createCell(cellNr);
319. xssfCell.setCellValue(s);
320. xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
{% endhighlight %}

***

