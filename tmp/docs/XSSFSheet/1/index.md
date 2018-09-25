# XSSFSheet @Cluster 1 (01, assertequals, sheet0)

***

### [WorkbookXSSFImplTest.java](https://searchcode.com/codesearch/view/72853773/)
> sets the and used for the user defined and the default font size . < p > works 1 0 0 7 4 0 < p > the type of the string are by _ value ! < / p > 
{% highlight java %}
38. XSSFSheet sheet = workbook.createSheet();
39. XSSFRow row = sheet.createRow(0);
{% endhighlight %}

***

### [XLColorTest.java](https://searchcode.com/codesearch/view/121321469/)
> sets the 
{% highlight java %}
44. XSSFSheet sheet=wb.getSheetAt(0);
45. XSSFCellStyle cellStyle = sheet.getRow(0).getCell(0).getCellStyle();
53. System.out.println("Index: " + sheet.getRow(0).getCell(0).getCellStyle().getIndex());
{% endhighlight %}

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
> test the that method is of the or more type of the formula ' s formula . 
{% highlight java %}
107. XSSFSheet sheet0 = workbook.getSheetAt(0);
108. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
113. assertEquals( 0.7, sheet0.getMargin( Sheet.LeftMargin ), 0.01 );
114. assertEquals( 0.7, sheet0.getMargin( Sheet.RightMargin ), 0.01 );
115. assertEquals( 0.75, sheet0.getMargin( Sheet.TopMargin ), 0.01 );
116. assertEquals( 0.75, sheet0.getMargin( Sheet.BottomMargin ), 0.01 );
{% endhighlight %}

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
> test the that method of a < tt > . < / tt > if it is not . 
{% highlight java %}
49. XSSFSheet sheet0 = workbook.getSheetAt(0);
50. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
55. assertEquals( 0.7 / 2.54, sheet0.getMargin( Sheet.LeftMargin ), 0.01 );
56. assertEquals( 0.7 / 2.54, sheet0.getMargin( Sheet.RightMargin ), 0.01 );
57. assertEquals( 1.7 / 2.54, sheet0.getMargin( Sheet.TopMargin ), 0.01 );
58. assertEquals( 1.7 / 2.54, sheet0.getMargin( Sheet.BottomMargin ), 0.01 );
{% endhighlight %}

***

### [SheetXSSFImplTest.java](https://searchcode.com/codesearch/view/72853788/)
> sets the a number of the number of columns that were previously @ param range - the number of columns to shift by 
{% highlight java %}
44. XSSFSheet sheet = wb.createSheet();
45. List<XSSFDataValidation> dataValidations = sheet.getDataValidations();  //<-- works
49. sheet.createRow(0).createCell(0);    
51. DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
55. sheet.addValidationData(validation);          
57. dataValidations = sheet.getDataValidations();  //<-- raised XmlValueOutOfRangeException  
{% endhighlight %}

***

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
> set the contents of the record back , so it can be written to disk . 
{% highlight java %}
78. XSSFSheet sheet = (XSSFSheet) document.getSheetAt(i);
82. extractHeaderFooter(sheet.getFirstHeader(), xhtml);
83. extractHeaderFooter(sheet.getOddHeader(), xhtml);
84. extractHeaderFooter(sheet.getEvenHeader(), xhtml);
134. extractHeaderFooter(sheet.getFirstFooter(), xhtml);
135. extractHeaderFooter(sheet.getOddFooter(), xhtml);
136. extractHeaderFooter(sheet.getEvenFooter(), xhtml);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
> test that we get the same value as excel and , for 
{% highlight java %}
37. private XSSFSheet sheet;
58.   int firstRow = sheet.getFirstRowNum();
59.   int lastRow = sheet.getLastRowNum();
61.     XSSFRow row = sheet.getRow(rowIndex);
77.     String oldName = sheet.getSheetName();
117.     int width = (sheet.getColumnWidth(col) / 256) * 6;
134.     for(Iterator<Row> it = sheet.rowIterator(); it.hasNext(); ) {
136.         sheet.removeRow(row);
141.     XSSFRow hssfRow = sheet.getRow(row);
157.         hssfRow = sheet.createRow(row);
167.     XSSFRow theRow = sheet.getRow(row);
208.   DataValidationConstraint constraint = sheet.getDataValidationHelper().createCustomConstraint(formula);
209.   DataValidation dataValidation = sheet.getDataValidationHelper().createValidation(constraint, addressList);
210.     sheet.addValidationData(dataValidation);
215.     DataValidationConstraint constraint = sheet.getDataValidationHelper().createFormulaListConstraint(namedRange);
216.     DataValidation dataValidation = sheet.getDataValidationHelper().createValidation(constraint, addressList);
241.   return sheet.getDataValidations();
245.   if (sheet.getCTWorksheet().getDataValidations() != null) {        
246.     for (int i=0;i<sheet.getCTWorksheet().getDataValidations().getCount();i++) {
248.         sheet.getCTWorksheet().getDataValidations().removeDataValidation(0);
{% endhighlight %}

***

