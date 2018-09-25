# HSSFSheet @Cluster 1 (createrow, int, mysheet)

***

### [ExcelWriterStep.java](https://searchcode.com/codesearch/view/42462258/)
> sets the 
{% highlight java %}
256. HSSFSheet sheet = ((HSSFWorkbook) data.wb).getSheetAt(sheetNum);
257. sheet.setForceFormulaRecalculation(true);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
> sets the line count . @ see # @ see # no _ this _ in @ see # 
{% highlight java %}
130. HSSFSheet sheet = wb.getSheet("Sheet1");
131. HSSFRow row = sheet.getRow(0);
{% endhighlight %}

***

### [MetaDataFacadeBean.java](https://searchcode.com/codesearch/view/39694405/)
> sets the a number of the formula that 1 ( data ) should be an / table @ param to 0 - based @ param column 0 - based 
{% highlight java %}
550. private void generateChildRows(HSSFSheet overviewSheet, CellStyle style,
560.     HSSFRow row = overviewSheet.createRow(rowNumber++);
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
> called by slideshow and see the internal 
{% highlight java %}
54. HSSFSheet sheet1 = workbook.createSheet("sheet1");
59.     HSSFRow row = sheet1.createRow(count); 
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
> sets the line count . @ see # @ see # no _ this _ in @ see # 
{% highlight java %}
95. HSSFSheet sheet = wb.getSheet("Test Sheet");
96. HSSFRow row = sheet.getRow(2);
{% endhighlight %}

***

### [PatchedPoi.java](https://searchcode.com/codesearch/view/72854649/)
> creates a 
{% highlight java %}
51. public List<HSSFDataValidation> getValidationData(final HSSFSheet sheet,
54.   DataValidityTable dvt = sheet.getDataValidityTable();
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/48925127/)
> sets the { @ link 2 } or { @ link 0 } is a { @ link code } . 
{% highlight java %}
49. HSSFSheet sheet;
62.   rNum = sheet.getLastRowNum();
64.     if ((row = sheet.getRow(j)) == null){
{% endhighlight %}

***

### [ExcelFileOut.java](https://searchcode.com/codesearch/view/35739735/)
> < p > a document in the poi filesystem has been opened for reading . this method retrieves properties of the document and returns all in the cell that are . < p > 0 - the @ param 5 the row 
{% highlight java %}
31. HSSFSheet mySheet;
43.       if (mySheet.getRow(i) != null) {
44.         mySheet.removeRow(mySheet.getRow(i));
54.       myRow = mySheet.createRow(rowNum);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> sets the link number . 
{% highlight java %}
72. HSSFSheet sheet = workbook.createSheet(title);
74. sheet.setDefaultColumnWidth(15);
112. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
122. HSSFRow row = sheet.createRow(0);
133.   row = sheet.createRow(index);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
> test that we get the same value as excel and , for 
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

