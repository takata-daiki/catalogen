# HSSFWorkbook @Cluster 1

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
349. private void outputToFileSystem(String fileName, HSSFWorkbook workbook) throws IOException
353.   workbook.write(fileOutputStream);
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
255. private CellStyle createUserStyle(HSSFWorkbook wb, Font userFont) {
256.     CellStyle style = wb.createCellStyle();
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
271. private CellStyle createStoryStyle(HSSFWorkbook wb, Font font) {
272.     CellStyle style = wb.createCellStyle();
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
285. private CellStyle createStoryCenterStyle(HSSFWorkbook wb) {
286.     CellStyle style = wb.createCellStyle();
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
299. private CellStyle createHeaderStyle(HSSFWorkbook wb, Font headerFont) {
300.     CellStyle header = wb.createCellStyle();
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
313. private CellStyle createHeaderVerticalStyle(HSSFWorkbook wb, Font headerFont) {
314.     CellStyle header = wb.createCellStyle();
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
328. private CellStyle createPercentStyle(HSSFWorkbook wb, Font font) {
329.     CellStyle style = wb.createCellStyle();
330.     CreationHelper creationHelper = wb.getCreationHelper();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
396. private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fillForegroundColor, short fillPattern,
398.   HSSFCellStyle style = workbook.createCellStyle();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
410. private static HSSFFont createFont(HSSFWorkbook workbook, short color, short bold) {
412.   HSSFFont font = workbook.createFont();
{% endhighlight %}

***

### [SpreadSheetPoiHelper.java](https://searchcode.com/codesearch/view/73882044/)
{% highlight java %}
306. private static Map<Header, Value> makeRow ( final HSSFWorkbook workbook, final Map<Integer, Header> headers, final Row row )
324.                 final HSSFFont font = workbook.getFontAt ( cell.getCellStyle ().getFontIndex () );
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
89. private HSSFFont createFont(final HSSFWorkbook workbook,
91.   final HSSFFont font = workbook.createFont();
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
104.   final HSSFWorkbook workbook) {
107. final HSSFCellStyle headerCellStyle = workbook.createCellStyle();
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
144. private HSSFCellStyle createBorderedCellStyle(final HSSFWorkbook workbook) {
147.   final HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
{% endhighlight %}

***

