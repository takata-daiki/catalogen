# HSSFCell @Cluster 2 (getcelltype, setcellvalue, thecell)

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
> test < a a ) method in the stream in the stream in the stream . < br > the 2 byte 5 must be a valid string number < / p > 
{% highlight java %}
113. HSSFCell cell = getCell(sheet, 2, 4);
114. cell.setCellValue("Test Value");
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
> test < a a ) method in the stream in the stream in the stream . < br > the 2 byte 5 must be a valid string number < / p > 
{% highlight java %}
79. HSSFCell cell = getCell(sheet, 2, 4);
80. cell.setCellValue("Test Value");
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
> sets the a number of the formula that 1 between 0 and ( the first sheet name ) by param one cell . to set a sheet from an empty workbook . @ param not the set of the sheet to move @ param row the row to end shifting @ param endrow the row to end shifting @ param n the number of rows to shift @ since 3 . 1 4 beta 1 
{% highlight java %}
205. protected void createSimpleComment(final HSSFCell cell,
224.   cell.setCellComment(comment);
{% endhighlight %}

***

### [WorkbookHSSFImpl.java](https://searchcode.com/codesearch/view/72854626/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
240. HSSFCell hssfCell = hssfRow.getCell(setCellValue.getCol());
246.         hssfCell.setCellValue(new HSSFRichTextString(setCellValue.getNewValue().toString()));
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
> test to see if the print areas can be retrieved / created in memory 
{% highlight java %}
127. HSSFCell restriction = horzTitle.createCell(1);
128. restriction.setCellStyle(headingStyle);
129. restriction.setCellValue(new HSSFRichTextString("Restriction"));
{% endhighlight %}

***

### [CustomExcelHssfView.java](https://searchcode.com/codesearch/view/73662641/)
> creates a new document summary information . < p > excel may be used to add a data , " from the underlying xml bean < / p > 
{% highlight java %}
102. HSSFCell cell = xlsRow.createCell(colNum++);
103. cell.setCellValue(new HSSFRichTextString(columnHeader));
104. cell.setCellStyle(headerStyle);
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
> create the a new poi : 0 . . . use this to create a new @ see org . apache . poi . hslf . usermodel . return < code > null < / code > . 
{% highlight java %}
103. HSSFCell headerCell = null;
119.     headerCell.setCellStyle(cellStyleHeader);
120.     headerCell.setCellValue(new HSSFRichTextString(colName));
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
> set the content of this part . 
{% highlight java %}
124. HSSFCell source = horzTitle.createCell(0);
125. source.setCellStyle(headingStyle);
126. source.setCellValue(new HSSFRichTextString("Resource"));
{% endhighlight %}

***

### [ExcelFileOut.java](https://searchcode.com/codesearch/view/35739735/)
> test that we get the same value as excel and , for 
{% highlight java %}
33. HSSFCell myCell = null;
59.           myCell.setCellValue(headers[cellNum]);
61.           myCell.setCellValue(excelData[rowNum-1][cellNum]);  
{% endhighlight %}

***

### [UpLoadFileWindow.java](https://searchcode.com/codesearch/view/42988393/)
> < p > sets the date that was < tt > " 1 " from < / tt > < / ul > for signing the default true < / tt > @ param string the date @ param value the date as a string . @ throws illegalargumentexception if the date date to be given . 
{% highlight java %}
301. private String getStringValue(HSSFCell cell){
303.     int type = cell.getCellType();
305.       return cell.getStringCellValue();
307.       BigDecimal big = new BigDecimal(cell.getNumericCellValue());
{% endhighlight %}

***

### [ExcelIdentifier.java](https://searchcode.com/codesearch/view/52992680/)
> test that error is . @ param . for 
{% highlight java %}
69. protected Object getCellString(HSSFCell cell) {
73.         int cellType = cell.getCellType();
78.             result = cell.getRichStringCellValue().getString();
81.             result = cell.getNumericCellValue();
90.             result = cell.getBooleanCellValue();
{% endhighlight %}

***

### [Hybrid_Framework.java](https://searchcode.com/codesearch/view/71798596/)
> should be called to tell the cell value cache that the specified ( value or formula ) cell has changed . failure to call this method after changing cell values will cause incorrect behaviour of the evaluate ~ methods of this class 
{% highlight java %}
375. public static String cellToString(HSSFCell cell) {
377.       int type = cell.getCellType();
381.               result = cell.getNumericCellValue();
384.               result = cell.getStringCellValue();
392.               result = cell.getBooleanCellValue();
{% endhighlight %}

***

### [hybrid.java](https://searchcode.com/codesearch/view/71798584/)
> should be called to tell the cell value cache that the specified ( value or formula ) cell has changed . failure to call this method after changing cell values will cause incorrect behaviour of the evaluate ~ methods of this class 
{% highlight java %}
205. public static String cellToString(HSSFCell cell) {
207.     int type = cell.getCellType();
211.             result = cell.getNumericCellValue();
214.             result = cell.getStringCellValue();
222.             result = cell.getBooleanCellValue();
{% endhighlight %}

***

### [CustomExcelHssfView.java](https://searchcode.com/codesearch/view/73662641/)
> sets the value of the given paragraph textprop , add if required @ param propname the name of the paragraph textprop @ param value the value to set for the textprop 
{% highlight java %}
149. protected void writeCell(Object value, HSSFCell cell) {
152.         cell.setCellValue(num.doubleValue());
154.         cell.setCellValue((Date) value);
156.         cell.setCellValue((Calendar) value);
158.         cell.setCellValue(new HSSFRichTextString(escapeColumnValue(value)));
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
> test that we get the same value as excel and , for 
{% highlight java %}
165. HSSFCell aCell = aRow.getCell(index);
166. if(aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
167.   currstr = aCell.getStringCellValue();
168. } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
169.   val[index-1] = aCell.getNumericCellValue();
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param @ param 
{% highlight java %}
96. HSSFCell aCell = aRow.getCell(selectCol[index]);
98. if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
100.     double d = aCell.getNumericCellValue();
105.     fields[index]="" + (long)aCell.getNumericCellValue();
109.   fields[index]=aCell.getStringCellValue();
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/48925127/)
> sets the list of colours that are interpolated between . the number must match { @ link # , } , { @ link # of ( ) } 
{% highlight java %}
51. HSSFCell cell;
75.         if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
76.           resultText.append(cell.getStringCellValue()).append(" ");
77.         } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
78.           double d = cell.getNumericCellValue();
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
> test that we get the same value as excel and , for 
{% highlight java %}
43. private HSSFCell theCell;
63.     return theCell.getRowIndex();
67.     return theCell.getColumnIndex();
71.     HSSFComment hssfComment = theCell.getCellComment();
82.     HSSFFont hssfFont = theCell.getCellStyle().getFont(getWorkbook());
97.     if (theCell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
100.     else if (theCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
101.         return Boolean.toString(theCell.getBooleanCellValue());
103.     else if (theCell.getCellType() == HSSFCell.CELL_TYPE_ERROR) {
106.     else if (theCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
107.         return theCell.getCellFormula();
109.     else if (theCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
110.         return Double.toString(theCell.getNumericCellValue());
112.     else if (theCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
113.         return theCell.getRichStringCellValue().getString();
120.         theCell.setCellValue(new HSSFRichTextString(value));
123.         theCell.setCellValue(Boolean.parseBoolean(value));
128.         theCell.setCellFormula(value);
131.         theCell.setCellValue(Double.parseDouble(value));
143.     HSSFCellStyle cellStyle = theCell.getCellStyle();        
146.         theCell.setCellStyle(cellStyle);
163.     HSSFCellStyle cellStyle = theCell.getCellStyle();
195.   HSSFCellStyle cellStyle = theCell.getCellStyle();
212.   theCell.setCellStyle(getFillStyleForColour(colour));
238.         HSSFCellStyle cellStyle = theCell.getCellStyle();
308. return theCell.hashCode();
315.   return cell.theCell.equals(this.theCell);      
341. HSSFSheet sheet = theCell.getSheet();
{% endhighlight %}

***

