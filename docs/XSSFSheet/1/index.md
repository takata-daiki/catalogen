# XSSFSheet @Cluster 1

***

### [WorkbookXSSFImpl.java](https://searchcode.com/codesearch/view/72854562/)
{% highlight java %}
252. XSSFSheet xssfSheet = workbook.getSheet(setCellValue.getSheet().getName());
253. XSSFRow xssfRow = xssfSheet.getRow(setCellValue.getRow());
255.     xssfRow = xssfSheet.createRow(setCellValue.getRow());
{% endhighlight %}

***

### [ExcelReport.java](https://searchcode.com/codesearch/view/71257075/)
{% highlight java %}
44. XSSFSheet dataSheet = wb.createSheet("Data");
49.   dataSheet.autoSizeColumn(ii);
{% endhighlight %}

***

### [ExcelReport.java](https://searchcode.com/codesearch/view/71257075/)
{% highlight java %}
56. protected int createHeaderRow(XSSFSheet dataSheet) {
57.   Row row = dataSheet.createRow((short) 0);
61.   Font font = dataSheet.getWorkbook().createFont();
67.   XSSFCellStyle style = dataSheet.getWorkbook().createCellStyle();
{% endhighlight %}

***

### [ExcelReport.java](https://searchcode.com/codesearch/view/71257075/)
{% highlight java %}
85. protected void createDataRows(XSSFSheet dataSheet) {
92.       Row row = dataSheet.createRow((short) rowIndex);
{% endhighlight %}

***

### [Table2XLSX.java](https://searchcode.com/codesearch/view/115088748/)
{% highlight java %}
60. final XSSFSheet xssfSheet = workbook.createSheet(table.getTableName());
62. final XSSFRow xssfHeaderRow = xssfSheet.createRow(0);
77.     final XSSFRow xssfRow = xssfSheet.createRow(row.getRowNr() + 1);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
98. XSSFSheet sheet1 = wb.createSheet("Upper Form");
99. sheet1.setDisplayGridlines(true);
103.   sheet1.setDefaultColumnWidth(30);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
100. XSSFSheet sheet2 = null;
110.   sheet2.setDefaultColumnWidth(30);
111.   sheet2.setDisplayGridlines(true);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
115. XSSFSheet sheet3 = wb.createSheet("Instructions");
116. sheet3.setDefaultColumnWidth(120);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
120. XSSFSheet sheet4 = wb.createSheet("Status");
121. sheet4.setDefaultColumnWidth(50);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
161. private void createSheetData(XSSFSheet sheet,  List<String> delist, String type) throws Exception {
164.    XSSFRow row = sheet.createRow(rowindex);
170.        row = sheet.createRow(i+1);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
287. private void createBatchStatusRow(XSSFSheet sheet, CreationHelper createHelper, XSSFCellStyle cs, short rowindex, String value){
289.   XSSFRow row = sheet.createRow(rowindex);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
299. private void createIntructionRow(XSSFSheet sheet, CreationHelper createHelper, XSSFCellStyle cs, short rowindex, String value){
301.   XSSFRow row = sheet.createRow(rowindex);
{% endhighlight %}

***

### [ReadExcel.java](https://searchcode.com/codesearch/view/93053248/)
{% highlight java %}
196. private void readRows(XSSFSheet sheet, String type) throws Exception{
197.    Iterator<Row> it = sheet.iterator() ;
{% endhighlight %}

***

