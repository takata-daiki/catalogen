# HSSFWorkbook @Cluster 2

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
313. private CellStyle createHeaderVerticalStyle(HSSFWorkbook wb, Font headerFont) {
314.     CellStyle header = wb.createCellStyle();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
410. private static HSSFFont createFont(HSSFWorkbook workbook, short color, short bold) {
412.   HSSFFont font = workbook.createFont();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
396. private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fillForegroundColor, short fillPattern,
398.   HSSFCellStyle style = workbook.createCellStyle();
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
349. private void outputToFileSystem(String fileName, HSSFWorkbook workbook) throws IOException
353.   workbook.write(fileOutputStream);
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
271. private CellStyle createStoryStyle(HSSFWorkbook wb, Font font) {
272.     CellStyle style = wb.createCellStyle();
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
144. private HSSFCellStyle createBorderedCellStyle(final HSSFWorkbook workbook) {
147.   final HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
104.   final HSSFWorkbook workbook) {
107. final HSSFCellStyle headerCellStyle = workbook.createCellStyle();
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
255. private CellStyle createUserStyle(HSSFWorkbook wb, Font userFont) {
256.     CellStyle style = wb.createCellStyle();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
47. public static void writeWorkbook(HSSFWorkbook wb, String fileName) {
51.     wb.write(fos);
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
299. private CellStyle createHeaderStyle(HSSFWorkbook wb, Font headerFont) {
300.     CellStyle header = wb.createCellStyle();
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
89. private HSSFFont createFont(final HSSFWorkbook workbook,
91.   final HSSFFont font = workbook.createFont();
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
285. private CellStyle createStoryCenterStyle(HSSFWorkbook wb) {
286.     CellStyle style = wb.createCellStyle();
{% endhighlight %}

***

### [SpreadSheetPoiHelper.java](https://searchcode.com/codesearch/view/73882044/)
{% highlight java %}
306. private static Map<Header, Value> makeRow ( final HSSFWorkbook workbook, final Map<Integer, Header> headers, final Row row )
324.                 final HSSFFont font = workbook.getFontAt ( cell.getCellStyle ().getFontIndex () );
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
328. private CellStyle createPercentStyle(HSSFWorkbook wb, Font font) {
329.     CellStyle style = wb.createCellStyle();
330.     CreationHelper creationHelper = wb.getCreationHelper();
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
116. HSSFWorkbook wb = new HSSFWorkbook();
121. Font headerFont = wb.createFont();
125. Font userFont = wb.createFont();
129. Font storyFont = wb.createFont();
140. HSSFSheet overviewSheet = wb.createSheet(sheetName);
234. wb.setRepeatingRowsAndColumns(0, 0, 0, 0, 0);
235. wb.setPrintArea(0, 0, 8, 0, overviewSheetRow);
247.     wb.write(baos);
{% endhighlight %}

***

### [SearchEngineBean.java](https://searchcode.com/codesearch/view/39694394/)
{% highlight java %}
483. HSSFWorkbook wb = new HSSFWorkbook();
488. Font storyFont = wb.createFont();
493. CellStyle style = wb.createCellStyle();
504. CreationHelper createHelper = wb.getCreationHelper();
505. CellStyle dateStyle = wb.createCellStyle();
517. HSSFSheet overviewSheet = wb.createSheet(sheetName);
602. wb.setRepeatingRowsAndColumns(0, 0, 0, 0, 0);
608.     wb.write(baos);
{% endhighlight %}

***

### [MetaDataFacadeBean.java](https://searchcode.com/codesearch/view/39694405/)
{% highlight java %}
457. HSSFWorkbook wb = new HSSFWorkbook();
461. Font storyFont = wb.createFont();
466. CellStyle style = wb.createCellStyle();
477. CreationHelper createHelper = wb.getCreationHelper();
478. CellStyle dateStyle = wb.createCellStyle();
490. HSSFSheet overviewSheet = wb.createSheet(sheetName);
536. wb.setRepeatingRowsAndColumns(0, 0, 0, 0, 0);
542.     wb.write(baos);
{% endhighlight %}

***

