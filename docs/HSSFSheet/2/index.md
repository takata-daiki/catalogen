# HSSFSheet @Cluster 2

***

### [ExportEventsImpl.java](https://searchcode.com/codesearch/view/122444114/)
{% highlight java %}
196. final HSSFSheet sheet = createSheet ( events, workbook, columns );
203.     final HSSFRow row = sheet.createRow ( i + 1 );
225.     sheet.autoSizeColumn ( i );
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
39. private int[] createHeader(HSSFSheet sheet, List<TableColumn<T, ?>> header, int rowIdx, int clmIdx, int[] offset) {
40.   HSSFRow row = sheet.createRow(rowIdx);
61.     sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx + headerOffset[0], i + nextCellOffset, i + headerOffset[1] + nextCellOffset));
{% endhighlight %}

***

### [UpLoadFileWindow.java](https://searchcode.com/codesearch/view/42988393/)
{% highlight java %}
181. HSSFSheet st = wb.getSheetAt(0);
183. countRows = st.getPhysicalNumberOfRows()-1;
190.   HSSFRow row = st.getRow(i);
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
{% highlight java %}
87. HSSFSheet aSheet = workbook.getSheetAt(numSheets);
89. for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
90.   if (null != aSheet.getRow(rowNumOfSheet)) {
91.     HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
105. HSSFSheet sheet = wb.createSheet("Resources Restriction");
123. HSSFRow horzTitle = sheet.createRow(0);
131. sheet.setColumnWidth(0, 8000);
132. sheet.setColumnWidth(1, 10000);
141.   HSSFRow row = sheet.createRow(i+1);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
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

### [ExportEventsImpl.java](https://searchcode.com/codesearch/view/122444114/)
{% highlight java %}
271. final HSSFSheet sheet = workbook.createSheet ( Messages.ExportImpl_ExcelSheet_Name );
273. final HSSFHeader header = sheet.getHeader ();
277. final HSSFFooter footer = sheet.getFooter ();
284. final HSSFPrintSetup printSetup = sheet.getPrintSetup ();
290. sheet.setAutoFilter ( new CellRangeAddress ( 0, 0, 0, columns.size () - 1 ) );
291. sheet.createFreezePane ( 0, 1 );
292. sheet.setFitToPage ( true );
293. sheet.setAutobreaks ( true );
297. sheet.setMargin ( Sheet.LeftMargin, 0.25 );
298. sheet.setMargin ( Sheet.RightMargin, 0.25 );
299. sheet.setMargin ( Sheet.TopMargin, 0.25 );
300. sheet.setMargin ( Sheet.BottomMargin, 0.5 );
{% endhighlight %}

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
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

