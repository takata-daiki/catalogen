# HSSFSheet @Cluster 2 (footer, overviewsheet, rowidx)

***

### [ExportEventsImpl.java](https://searchcode.com/codesearch/view/122444114/)
> track a 
{% highlight java %}
196. final HSSFSheet sheet = createSheet ( events, workbook, columns );
203.     final HSSFRow row = sheet.createRow ( i + 1 );
225.     sheet.autoSizeColumn ( i );
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
> < p > sets the color of the bottom of a range of cells . < / p > @ param type - { @ link cellrangeaddress } range of cells on which colors are set . @ param color - color index from { @ link indexedcolors } used to draw the borders . @ param header - { @ link borderextent } of the borders for which colors are set . valid values are : < ul > < li > borderextent . all < / li > < li > borderextent . vertical < / li > < / ul > 
{% highlight java %}
39. private int[] createHeader(HSSFSheet sheet, List<TableColumn<T, ?>> header, int rowIdx, int clmIdx, int[] offset) {
40.   HSSFRow row = sheet.createRow(rowIdx);
61.     sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx + headerOffset[0], i + nextCellOffset, i + headerOffset[1] + nextCellOffset));
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
> sets the < code > transform < / code > in the < code > graphics 2 d < / code > context . 
{% highlight java %}
105. HSSFSheet sheet = wb.createSheet("Resources Restriction");
123. HSSFRow horzTitle = sheet.createRow(0);
131. sheet.setColumnWidth(0, 8000);
132. sheet.setColumnWidth(1, 10000);
141.   HSSFRow row = sheet.createRow(i+1);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
> sets the < code > transform < / code > in the < code > graphics 2 d < / code > context . 
{% highlight java %}
94. HSSFSheet sheet = wb.createSheet();
96. HSSFRow row = sheet.createRow(rowIdx);
100.   sheet.setColumnWidth(i, (int) (258 / 8 * columnWidths.get(i)));
113.   row = sheet.getRow(rowIdx + headerDepth - 1);
117.       sheet.setColumnWidth(i, (int) (258 / 8 * column.getPrefWidth()));
131.   row = sheet.createRow(rowIdx);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> sets the line number . 
{% highlight java %}
225. HSSFSheet sheet = workbook.createSheet(title);
227. sheet.setDefaultColumnWidth(15);
265. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
274. HSSFRow row = sheet.createRow(0);
287.   row = sheet.createRow(index);
322.         sheet.setColumnWidth(i, (short) (35.7 * 80));
365.   row = sheet.createRow(index + 1);
{% endhighlight %}

***

### [MetaDataFacadeBean.java](https://searchcode.com/codesearch/view/39694405/)
> if you to re an { @ link code } with the 
{% highlight java %}
490. HSSFSheet overviewSheet = wb.createSheet(sheetName);
493. HSSFHeader sheetHeader = overviewSheet.getHeader();
498. Footer footer = overviewSheet.getFooter();
506. Row row = overviewSheet.createRow(0);
515. overviewSheet.createFreezePane(0, 1, 0, 1);
525.     row = overviewSheet.createRow(overviewSheetRow++);
533.     overviewSheet.autoSizeColumn(i);
537. overviewSheet.setFitToPage(true);
538. overviewSheet.setAutobreaks(true);
{% endhighlight %}

***

### [SearchEngineBean.java](https://searchcode.com/codesearch/view/39694394/)
> if you to re an { @ link code } in the cell that is opened with the formula that ( and formula ) are not there . 
{% highlight java %}
517. HSSFSheet overviewSheet = wb.createSheet(sheetName);
520. HSSFHeader sheetHeader = overviewSheet.getHeader();
525. Footer footer = overviewSheet.getFooter();
534. overviewSheet.createFreezePane(0, 1, 0, 1);
536. Row row = overviewSheet.createRow(0);
555.             row = overviewSheet.createRow(overviewSheetRow);
570.                     row = overviewSheet.createRow(overviewSheetRow);
599.     overviewSheet.autoSizeColumn(i);
603. overviewSheet.setFitToPage(true);
604. overviewSheet.setAutobreaks(true);
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
> if you to re an 0 ( table ) cells in only @ param cell the cell to check 
{% highlight java %}
140. HSSFSheet overviewSheet = wb.createSheet(sheetName);
141. Header sheetHeader = overviewSheet.getHeader();
146. overviewSheet.createFreezePane(0, 1, 0, 1);
148. Row row = overviewSheet.createRow(0);
169.     row = overviewSheet.createRow(overviewSheetRow);
192.         row = overviewSheet.createRow(overviewSheetRow);
225.     overviewSheet.groupRow(startRow, endRow);
226.     overviewSheet.setRowGroupCollapsed(startRow, true);
231.     overviewSheet.autoSizeColumn(i);
237. overviewSheet.setAutobreaks(true);
238. overviewSheet.getPrintSetup().setFitWidth((short) 1);
239. overviewSheet.getPrintSetup().setFitHeight((short) 500);
241. Footer footer = overviewSheet.getFooter();
{% endhighlight %}

***

