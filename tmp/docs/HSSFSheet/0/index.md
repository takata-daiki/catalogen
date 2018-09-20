# HSSFSheet @Cluster 1

***

### [MetaDataFacadeBean.java](https://searchcode.com/codesearch/view/39694405/)
{% highlight java %}
550. private void generateChildRows(HSSFSheet overviewSheet, CellStyle style,
560.     HSSFRow row = overviewSheet.createRow(rowNumber++);
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
67. HSSFSheet sheet = workBook.createSheet();
102. HSSFRow headerRow = sheet.createRow(0);
129.   HSSFRow row = sheet.createRow(rowIndex++);
138.     sheet.autoSizeColumn((short)i);
{% endhighlight %}

***

### [ExcelWriterStep.java](https://searchcode.com/codesearch/view/42462258/)
{% highlight java %}
256. HSSFSheet sheet = ((HSSFWorkbook) data.wb).getSheetAt(sheetNum);
257. sheet.setForceFormulaRecalculation(true);
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/48925127/)
{% highlight java %}
49. HSSFSheet sheet;
62.   rNum = sheet.getLastRowNum();
64.     if ((row = sheet.getRow(j)) == null){
{% endhighlight %}

***

### [WorkbookHSSFImpl.java](https://searchcode.com/codesearch/view/72854626/)
{% highlight java %}
235. HSSFSheet hssfSheet = workbook.getSheet(setCellValue.getSheet().getName());
236. HSSFRow hssfRow = hssfSheet.getRow(setCellValue.getRow());
238.     hssfRow = hssfSheet.createRow(setCellValue.getRow());
{% endhighlight %}

***

### [ExcelFileOut.java](https://searchcode.com/codesearch/view/35739735/)
{% highlight java %}
31. HSSFSheet mySheet;
43.       if (mySheet.getRow(i) != null) {
44.         mySheet.removeRow(mySheet.getRow(i));
54.       myRow = mySheet.createRow(rowNum);
{% endhighlight %}

***

### [ExcelIdentifier.java](https://searchcode.com/codesearch/view/52992680/)
{% highlight java %}
44. HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
46. int rowCount = sheet.getLastRowNum();
52.     HSSFRow row = sheet.getRow(rowIndex);
{% endhighlight %}

***

### [PatchedPoi.java](https://searchcode.com/codesearch/view/72854649/)
{% highlight java %}
114. public void clearValidationData(HSSFSheet sheet) {
115.   sheet.getDataValidityTable().clear();
{% endhighlight %}

***

### [PatchedPoi.java](https://searchcode.com/codesearch/view/72854649/)
{% highlight java %}
51. public List<HSSFDataValidation> getValidationData(final HSSFSheet sheet,
54.   DataValidityTable dvt = sheet.getDataValidityTable();
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
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

### [ExportEventsImpl.java](https://searchcode.com/codesearch/view/122444114/)
{% highlight java %}
305. private void makeHeader ( final List<Field> columns, final HSSFSheet sheet )
307.     final Font font = sheet.getWorkbook ().createFont ();
312.     final CellStyle style = sheet.getWorkbook ().createCellStyle ();
317.     final HSSFRow row = sheet.createRow ( 0 );
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
72. HSSFSheet sheet = workbook.createSheet(title);
74. sheet.setDefaultColumnWidth(15);
112. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
122. HSSFRow row = sheet.createRow(0);
133.   row = sheet.createRow(index);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
200. HSSFSheet sheet = wb.getSheet("Sheet1");
201. HSSFRow row = sheet.getRow(0);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
130. HSSFSheet sheet = wb.getSheet("Sheet1");
131. HSSFRow row = sheet.getRow(0);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
165. HSSFSheet sheet = wb.getSheet("Sheet1");
166. HSSFRow row = sheet.getRow(0);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
95. HSSFSheet sheet = wb.getSheet("Test Sheet");
96. HSSFRow row = sheet.getRow(2);
{% endhighlight %}

***

### [XMLGeneration.java](https://searchcode.com/codesearch/view/110498474/)
{% highlight java %}
112. HSSFSheet sheet) throws XMLStreamException {
114. List<HSSFDataValidation> validationData = sheet.getDataValidations();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
142. public static int getRowNumber(HSSFSheet sheet) {
143.     return sheet.getLastRowNum();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
156. public static HSSFRow getRow(HSSFSheet sheet, int index) {
157.     return sheet.getRow(index);
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
{% highlight java %}
54. HSSFSheet sheet1 = workbook.createSheet("sheet1");
59.     HSSFRow row = sheet1.createRow(count); 
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
{% highlight java %}
157. HSSFSheet aSheet = workbook.getSheetAt(numSheets);
159. for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
160.   if (null != aSheet.getRow(rowNumOfSheet)) {
161.     HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
{% endhighlight %}

***

