# XSSFSheet @Cluster 2 (datasheet, row, setdefaultcolumnwidth)

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
> create an or the given cell range from the specified cell range . @ param with the rows of the sheet in the table . @ param sheet the sheet to @ param column the column to not 
{% highlight java %}
120. XSSFSheet sheet4 = wb.createSheet("Status");
121. sheet4.setDefaultColumnWidth(50);
{% endhighlight %}

***

### [ExcelReport.java](https://searchcode.com/codesearch/view/71257075/)
> if the supplied record is not set , the default ( value ) will be more by the property ' s position . 
{% highlight java %}
44. XSSFSheet dataSheet = wb.createSheet("Data");
49.   dataSheet.autoSizeColumn(ii);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
> sets the content - / mime - type @ see org . apache . poi . hslf . usermodel . null # 
{% highlight java %}
115. XSSFSheet sheet3 = wb.createSheet("Instructions");
116. sheet3.setDefaultColumnWidth(120);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
> sets whether the rowcolheadings are shown in a viewer @ param show whether to show rowcolheadings or not 
{% highlight java %}
98. XSSFSheet sheet1 = wb.createSheet("Upper Form");
99. sheet1.setDisplayGridlines(true);
103.   sheet1.setDefaultColumnWidth(30);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
> sets the 
{% highlight java %}
100. XSSFSheet sheet2 = null;
110.   sheet2.setDefaultColumnWidth(30);
111.   sheet2.setDisplayGridlines(true);
{% endhighlight %}

***

### [ExcelReport.java](https://searchcode.com/codesearch/view/71257075/)
> create a new . @ param the current < code > null < / code > which holds shape data for this sheet ( optional sheet index ) @ param a the should be called to column or not the sheet . 
{% highlight java %}
56. protected int createHeaderRow(XSSFSheet dataSheet) {
57.   Row row = dataSheet.createRow((short) 0);
61.   Font font = dataSheet.getWorkbook().createFont();
67.   XSSFCellStyle style = dataSheet.getWorkbook().createCellStyle();
{% endhighlight %}

***

