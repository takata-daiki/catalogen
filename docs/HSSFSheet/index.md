# HSSFSheet

***

### [Cluster 1](./1)
{% highlight java %}
157. HSSFSheet aSheet = workbook.getSheetAt(numSheets);
159. for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
160.   if (null != aSheet.getRow(rowNumOfSheet)) {
161.     HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
87. HSSFSheet aSheet = workbook.getSheetAt(numSheets);
89. for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
90.   if (null != aSheet.getRow(rowNumOfSheet)) {
91.     HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
31. HSSFSheet mySheet;
43.       if (mySheet.getRow(i) != null) {
44.         mySheet.removeRow(mySheet.getRow(i));
54.       myRow = mySheet.createRow(rowNum);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
235. HSSFSheet hssfSheet = workbook.getSheet(setCellValue.getSheet().getName());
236. HSSFRow hssfRow = hssfSheet.getRow(setCellValue.getRow());
238.     hssfRow = hssfSheet.createRow(setCellValue.getRow());
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
142. public static int getRowNumber(HSSFSheet sheet) {
143.     return sheet.getLastRowNum();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
156. public static HSSFRow getRow(HSSFSheet sheet, int index) {
157.     return sheet.getRow(index);
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
112. HSSFSheet sheet) throws XMLStreamException {
114. List<HSSFDataValidation> validationData = sheet.getDataValidations();
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
114. public void clearValidationData(HSSFSheet sheet) {
115.   sheet.getDataValidityTable().clear();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
51. public List<HSSFDataValidation> getValidationData(final HSSFSheet sheet,
54.   DataValidityTable dvt = sheet.getDataValidityTable();
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
256. HSSFSheet sheet = ((HSSFWorkbook) data.wb).getSheetAt(sheetNum);
257. sheet.setForceFormulaRecalculation(true);
{% endhighlight %}

***

### [Cluster 11](./11)
{% highlight java %}
54. HSSFSheet sheet1 = workbook.createSheet("sheet1");
59.     HSSFRow row = sheet1.createRow(count); 
{% endhighlight %}

***

### [Cluster 12](./12)
{% highlight java %}
550. private void generateChildRows(HSSFSheet overviewSheet, CellStyle style,
560.     HSSFRow row = overviewSheet.createRow(rowNumber++);
{% endhighlight %}

***

### [Cluster 13](./13)
{% highlight java %}
72. HSSFSheet sheet = workbook.createSheet(title);
74. sheet.setDefaultColumnWidth(15);
112. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
122. HSSFRow row = sheet.createRow(0);
133.   row = sheet.createRow(index);
{% endhighlight %}

***

### [Cluster 14](./14)
{% highlight java %}
67. HSSFSheet sheet = workBook.createSheet();
102. HSSFRow headerRow = sheet.createRow(0);
129.   HSSFRow row = sheet.createRow(rowIndex++);
138.     sheet.autoSizeColumn((short)i);
{% endhighlight %}

***

### [Cluster 15](./15)
{% highlight java %}
130. HSSFSheet sheet = wb.getSheet("Sheet1");
131. HSSFRow row = sheet.getRow(0);
{% endhighlight %}

***

### [Cluster 16](./16)
{% highlight java %}
95. HSSFSheet sheet = wb.getSheet("Test Sheet");
96. HSSFRow row = sheet.getRow(2);
{% endhighlight %}

***

### [Cluster 17](./17)
{% highlight java %}
44. HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
46. int rowCount = sheet.getLastRowNum();
52.     HSSFRow row = sheet.getRow(rowIndex);
{% endhighlight %}

***

### [Cluster 18](./18)
{% highlight java %}
42. private HSSFSheet sheet;
61.   int firstRow = sheet.getFirstRowNum();
62.   int lastRow = sheet.getLastRowNum();
64.     HSSFRow row = sheet.getRow(rowIndex);
89.     String oldName = sheet.getSheetName();
129.     int width = (sheet.getColumnWidth(col) / 256) * 6;
146.     for(Iterator<Row> it = sheet.rowIterator(); it.hasNext(); ) {
148.         sheet.removeRow(row);
153.     HSSFRow hssfRow = sheet.getRow(row);
169.         hssfRow = sheet.createRow(row);
179.     HSSFRow theRow = sheet.getRow(row);
211.   DataValidationConstraint constraint=sheet.getDataValidationHelper().createFormulaListConstraint(namedRange);      
226.   DataValidationConstraint constraint = sheet.getDataValidationHelper().createCustomConstraint(formula);
233.   sheet.addValidationData(dataValidation);
{% endhighlight %}

***

### [Cluster 19](./19)
{% highlight java %}
49. HSSFSheet sheet;
62.   rNum = sheet.getLastRowNum();
64.     if ((row = sheet.getRow(j)) == null){
{% endhighlight %}

***

### [Cluster 20](./20)
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

### [Cluster 21](./21)
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

### [Cluster 22](./22)
{% highlight java %}
181. HSSFSheet st = wb.getSheetAt(0);
183. countRows = st.getPhysicalNumberOfRows()-1;
190.   HSSFRow row = st.getRow(i);
{% endhighlight %}

***

### [Cluster 23](./23)
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

### [Cluster 24](./24)
{% highlight java %}
105. HSSFSheet sheet = wb.createSheet("Resources Restriction");
123. HSSFRow horzTitle = sheet.createRow(0);
131. sheet.setColumnWidth(0, 8000);
132. sheet.setColumnWidth(1, 10000);
141.   HSSFRow row = sheet.createRow(i+1);
{% endhighlight %}

***

### [Cluster 25](./25)
{% highlight java %}
196. final HSSFSheet sheet = createSheet ( events, workbook, columns );
203.     final HSSFRow row = sheet.createRow ( i + 1 );
225.     sheet.autoSizeColumn ( i );
{% endhighlight %}

***

### [Cluster 26](./26)
{% highlight java %}
39. private int[] createHeader(HSSFSheet sheet, List<TableColumn<T, ?>> header, int rowIdx, int clmIdx, int[] offset) {
40.   HSSFRow row = sheet.createRow(rowIdx);
61.     sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx + headerOffset[0], i + nextCellOffset, i + headerOffset[1] + nextCellOffset));
{% endhighlight %}

***

### [Cluster 27](./27)
{% highlight java %}
94. HSSFSheet sheet = wb.createSheet();
96. HSSFRow row = sheet.createRow(rowIdx);
100.   sheet.setColumnWidth(i, (int) (258 / 8 * columnWidths.get(i)));
113.   row = sheet.getRow(rowIdx + headerDepth - 1);
117.       sheet.setColumnWidth(i, (int) (258 / 8 * column.getPrefWidth()));
131.   row = sheet.createRow(rowIdx);
{% endhighlight %}

***

### [Cluster 28](./28)
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

### [Cluster 29](./29)
{% highlight java %}
305. private void makeHeader ( final List<Field> columns, final HSSFSheet sheet )
307.     final Font font = sheet.getWorkbook ().createFont ();
312.     final CellStyle style = sheet.getWorkbook ().createCellStyle ();
317.     final HSSFRow row = sheet.createRow ( 0 );
{% endhighlight %}

***

