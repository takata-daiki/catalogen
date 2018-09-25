# HSSFWorkbook @Cluster 2 (sheetname, storyfont, workbook)

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> dump the record content into < code > the . of the file ( s ) by the specified stream @ param stream the stream to write to @ throws ioexception if the data can ' t be written 
{% highlight java %}
47. public static void writeWorkbook(HSSFWorkbook wb, String fileName) {
51.     wb.write(fos);
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
> set a single paragraph of this sheet 
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
> an integer that specifies the format id to be used to style the datetime . 
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

