# HSSFWorkbook

***

### [Cluster 1](./1)
{% highlight java %}
89. private HSSFFont createFont(final HSSFWorkbook workbook,
91.   final HSSFFont font = workbook.createFont();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
30. protected final HSSFWorkbook workbook;
62.   sheet = workbook.createSheet(sheetName);
91.   final HSSFFont font = workbook.createFont();
107.   final HSSFCellStyle headerCellStyle = workbook.createCellStyle();
147.   final HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
199.     drawingPatriarch = workbook.getSheet(sheetName)
{% endhighlight %}

***

### [Cluster 3](./3)
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

### [Cluster 4](./4)
{% highlight java %}
47. public static void writeWorkbook(HSSFWorkbook wb, String fileName) {
51.     wb.write(fos);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
30. HSSFWorkbook myWorkBook;
40.     mySheet = myWorkBook.getSheetAt(0);
47.     myWorkBook.getSheetAt(1).getRow(1).createCell(1).setCellValue(TestHarness.TEST_PERIOD);
50.     mySheet = myWorkBook.createSheet();
67.     myWorkBook.write(out);
{% endhighlight %}

***

