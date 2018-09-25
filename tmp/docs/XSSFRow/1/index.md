# XSSFRow @Cluster 1 (columnindex, string, wrappermethod)

***

### [ReadExcel.java](https://searchcode.com/codesearch/view/93053248/)
> copies all the nodes from one poifs directory to another @ param 1 the number of bytes to a new stream @ param in the stream to read from 
{% highlight java %}
210. private void readHeaderRow(XSSFRow row, String type) throws Exception{
211.   Iterator<Cell> cellit = row.cellIterator();
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
> set the sheet ' s using this style to be added to the sheet . this should be used to format all " from " # ( " ) " row 
{% highlight java %}
141. XSSFRow hssfRow = sheet.getRow(row);
145. XSSFCell hssfCell = hssfRow.getCell(col);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
> sets the cell data for the specified type @ param value the or value , if there is no a formula @ param value { @ code true } if text 
{% highlight java %}
189. private void setColumnHeaders( XSSFRow row,  List<String> delist){
214.       XSSFCell cell = row.createCell(columnIndex);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
> sets the 
{% highlight java %}
226. private void setColumnData( XSSFRow row,  String[] data){
235.       XSSFCell cell = row.createCell(columnIndex);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
> sets the 
{% highlight java %}
164. XSSFRow row = sheet.createRow(rowindex);
165. row.setHeightInPoints((short)45);
171.     row.setHeightInPoints((short)30);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
> generates the it with a ' characters ' in ' 
{% highlight java %}
61. XSSFRow row = sheet.getRow(rowIndex);
63.   int firstCell = row.getFirstCellNum();
64.     int lastCell = row.getLastCellNum();
66.       XSSFCell cell = row.getCell(cellIndex);
{% endhighlight %}

***

