# XSSFRow @Cluster 1

***

### [ReadExcel.java](https://searchcode.com/codesearch/view/93053248/)
{% highlight java %}
210. private void readHeaderRow(XSSFRow row, String type) throws Exception{
211.   Iterator<Cell> cellit = row.cellIterator();
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
167. XSSFRow theRow = sheet.getRow(row);
169.     XSSFCell theCell = theRow.getCell(col);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
141. XSSFRow hssfRow = sheet.getRow(row);
145. XSSFCell hssfCell = hssfRow.getCell(col);
{% endhighlight %}

***

### [FilteredTableContextMenu.java](https://searchcode.com/codesearch/view/115088176/)
{% highlight java %}
311. final XSSFRow xssfRow = xssfSheet.createRow(rowNr);
317.     final XSSFCell xssfCell = xssfRow.createCell(cellNr);
{% endhighlight %}

***

### [Table2XLSX.java](https://searchcode.com/codesearch/view/115088748/)
{% highlight java %}
77. final XSSFRow xssfRow = xssfSheet.createRow(row.getRowNr() + 1);
81.         final XSSFCell xssfCell = xssfRow.createCell(cell.getColInfo().getSpaltenNr());
{% endhighlight %}

***

### [ReadExcel.java](https://searchcode.com/codesearch/view/93053248/)
{% highlight java %}
231. private void readDataRow(XSSFRow row,String type) throws Exception{
240.        XSSFCell cell = row.getCell(count);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
189. private void setColumnHeaders( XSSFRow row,  List<String> delist){
214.       XSSFCell cell = row.createCell(columnIndex);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
226. private void setColumnData( XSSFRow row,  String[] data){
235.       XSSFCell cell = row.createCell(columnIndex);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
164. XSSFRow row = sheet.createRow(rowindex);
165. row.setHeightInPoints((short)45);
171.     row.setHeightInPoints((short)30);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
155. XSSFRow hssfRow = sheet.getRow(row);
159. XSSFCell cell = hssfRow.getCell(col);
161.     cell = hssfRow.createCell(col);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
61. XSSFRow row = sheet.getRow(rowIndex);
63.   int firstCell = row.getFirstCellNum();
64.     int lastCell = row.getLastCellNum();
66.       XSSFCell cell = row.getCell(cellIndex);
{% endhighlight %}

***

### [WorkbookXSSFImpl.java](https://searchcode.com/codesearch/view/72854562/)
{% highlight java %}
253. XSSFRow xssfRow = xssfSheet.getRow(setCellValue.getRow());
257. XSSFCell xssfCell = xssfRow.getCell(setCellValue.getCol());
259.     xssfCell = xssfRow.createCell(setCellValue.getCol());
266.         xssfRow.removeCell(xssfCell);
{% endhighlight %}

***

