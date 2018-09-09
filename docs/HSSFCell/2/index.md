# HSSFCell @Cluster 2

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
134. HSSFCell dataCell = row.createCell(i);
137. dataCell.setCellStyle(cellStyleData);
139. dataCell.setCellValue(new HSSFRichTextString(dataCellContent));
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
54. HSSFCell cell = row.createCell(i + nextCellOffset);
55. cell.setCellValue(column.getText());
62. cell.setCellStyle(csHeader);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
103. HSSFCell cell = row.createCell(0);
104. cell.setCellStyle(csTitle);
105. cell.setCellValue(title);
122.       cell.setCellStyle(csHeader);
150.         cell.setCellStyle(csIntNum);
151.         cell.setCellValue(Double.parseDouble(value.toString()));
153.         cell.setCellStyle(csDoubleNum);
158.         cell.setCellValue(bd.doubleValue());
160.         cell.setCellStyle(csText);
161.         cell.setCellValue(baseColumn.convertToString(value));
{% endhighlight %}

***

### [ExcelIdentifier.java](https://searchcode.com/codesearch/view/52992680/)
{% highlight java %}
69. protected Object getCellString(HSSFCell cell) {
73.         int cellType = cell.getCellType();
78.             result = cell.getRichStringCellValue().getString();
81.             result = cell.getNumericCellValue();
90.             result = cell.getBooleanCellValue();
{% endhighlight %}

***

### [UpLoadFileWindow.java](https://searchcode.com/codesearch/view/42988393/)
{% highlight java %}
301. private String getStringValue(HSSFCell cell){
303.     int type = cell.getCellType();
305.       return cell.getStringCellValue();
307.       BigDecimal big = new BigDecimal(cell.getNumericCellValue());
{% endhighlight %}

***

### [UpLoadFileWindow.java](https://searchcode.com/codesearch/view/42988393/)
{% highlight java %}
318. private Date getDateValue(HSSFCell cell){
320.     int type = cell.getCellType();
322.       String s =  cell.getStringCellValue();
334.       return cell.getDateCellValue();
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/48925127/)
{% highlight java %}
51. HSSFCell cell;
75.         if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
76.           resultText.append(cell.getStringCellValue()).append(" ");
77.         } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
78.           double d = cell.getNumericCellValue();
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
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

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
124. HSSFCell cell = row.createCell(i);
125. cell.setCellStyle(style);
127. cell.setCellValue(text);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
149. HSSFCell cell = row.createCell(j);
150. cell.setCellStyle(style2);
179.       cell.setCellValue(Double.parseDouble(textValue));
181.       cell.setCellValue(textValue);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
276. HSSFCell cell = row.createCell(i);
277. cell.setCellStyle(style);
279. cell.setCellValue(text);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
304. HSSFCell cell = row.createCell(i);
305. cell.setCellStyle(style2);
346.       cell.setCellStyle(style3);
347.       cell.setCellValue(Double.parseDouble(textValue));
349.       cell.setCellStyle(style4);
352.       cell.setCellValue(textValue);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
367. HSSFCell cell = row.createCell(i);
368. cell.setCellStyle(style2);
378.   cell.setCellValue(richString);
381.   cell.setCellValue(new HSSFRichTextString(""));
384.   cell.setCellFormula(formula);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
69. HSSFCell cell = row.getCell(cellIndex);
70. if (cell!=null && !cell.getStringCellValue().isEmpty()) {
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
181. HSSFCell theCell = theRow.getCell(col);
182. theCell.setCellValue("");
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
166. HSSFCell cell = row.createCell((short) 0);
169. cell.setCellValue(cellValue);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
177. HSSFCell cell = row.createCell(i);
189. cell.setCellStyle(style);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
202. HSSFCell cell = row.createCell(i);
213.   cell.setCellStyle(style);
220.   cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
223.   cell.setCellFormula("SUM(" + str[i] + "3:" + str[i]
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
238. HSSFCell cell = row.createCell(col);
250.   cell.setCellStyle(style);
253.   cell.setCellType(HSSFCell.CELL_TYPE_STRING);
256.   cell.setCellValue((String) value);
259.   cell.setCellValue((Date) value);
261.   cell.setCellValue(((Boolean) value).booleanValue());
263.   cell.setCellValue(((Number) value).doubleValue());
268.   cell.setCellValue(value.toString());
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
323. public void setCellValue(HSSFCell cell, Object value) {
326.     cell.setCellType(HSSFCell.CELL_TYPE_STRING);
329.     cell.setCellValue((String) value);
332.     cell.setCellValue((Date) value);
334.     cell.setCellValue(((Boolean) value).booleanValue());
336.     cell.setCellValue(((Number) value).doubleValue());
341.     cell.setCellValue(value.toString());
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
121. final HSSFCell cell = row.createCell(x);
122. cell.setCellValue(content);
123. cell.setCellStyle(cellStyle);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
130. final HSSFCell cell = row.createCell(x);
131. cell.setCellValue(euroAndCents);
132. cell.setCellStyle(cellStyle);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
138. final HSSFCell cell = row.createCell(x);
139. cell.setCellValue(content);
140. cell.setCellStyle(cellStyle);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
205. protected void createSimpleComment(final HSSFCell cell,
224.   cell.setCellComment(comment);
{% endhighlight %}

***

### [CustomExcelHssfView.java](https://searchcode.com/codesearch/view/73662641/)
{% highlight java %}
102. HSSFCell cell = xlsRow.createCell(colNum++);
103. cell.setCellValue(new HSSFRichTextString(columnHeader));
104. cell.setCellStyle(headerStyle);
{% endhighlight %}

***

### [CustomExcelHssfView.java](https://searchcode.com/codesearch/view/73662641/)
{% highlight java %}
149. protected void writeCell(Object value, HSSFCell cell) {
152.         cell.setCellValue(num.doubleValue());
154.         cell.setCellValue((Date) value);
156.         cell.setCellValue((Calendar) value);
158.         cell.setCellValue(new HSSFRichTextString(escapeColumnValue(value)));
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
196. public static Date getDateCellValue(HSSFCell cell) {
197.     return cell.getDateCellValue();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
209. public static String getStringCellValue(HSSFCell cell) {
210.     return cell.getRichStringCellValue().getString();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
222. public static boolean getBooleanCellValue(HSSFCell cell) {
223.     return cell.getBooleanCellValue();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
235. public static int getIntCellValue(HSSFCell cell) {
236.   return (int) cell.getNumericCellValue();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
248. public static long getLongCellValue(HSSFCell cell) {
249.     return (long) cell.getNumericCellValue();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
261. public static float getFloatCellValue(HSSFCell cell) {
262.     return (float) cell.getNumericCellValue();
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/71407799/)
{% highlight java %}
274. public static double getDoubleCellValue(HSSFCell cell) {
275.     return cell.getNumericCellValue();
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
79. HSSFCell cell = getCell(sheet, 2, 4);
80. cell.setCellValue("Test Value");
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
97. HSSFCell cell = row.getCell((short) 4);
98. assertEquals("Test Value", cell.getStringCellValue());
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
113. HSSFCell cell = getCell(sheet, 2, 4);
114. cell.setCellValue("Test Value");
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
132. HSSFCell cell = row.getCell((short) 0);
133. assertEquals("Test Template", cell.getStringCellValue());
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
148. HSSFCell cell = getCell(sheet, 2, 4);
149. cell.setCellValue("Test Value");
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
167. HSSFCell cell = row.getCell((short) 0);
168. assertEquals("Test Template American English", cell.getStringCellValue());
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
183. HSSFCell cell = getCell(sheet, 2, 4);
184. cell.setCellValue("Test Value");
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
202. HSSFCell cell = row.getCell((short) 0);
203. assertEquals("Test Template auf Deutsch", cell.getStringCellValue());
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
{% highlight java %}
96. HSSFCell aCell = aRow.getCell(selectCol[index]);
98. if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
100.     double d = aCell.getNumericCellValue();
105.     fields[index]="" + (long)aCell.getNumericCellValue();
109.   fields[index]=aCell.getStringCellValue();
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
{% highlight java %}
165. HSSFCell aCell = aRow.getCell(index);
166. if(aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
167.   currstr = aCell.getStringCellValue();
168. } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
169.   val[index-1] = aCell.getNumericCellValue();
{% endhighlight %}

***

### [hybrid.java](https://searchcode.com/codesearch/view/71798584/)
{% highlight java %}
205. public static String cellToString(HSSFCell cell) {
207.     int type = cell.getCellType();
211.             result = cell.getNumericCellValue();
214.             result = cell.getStringCellValue();
222.             result = cell.getBooleanCellValue();
{% endhighlight %}

***

### [hybrid.java](https://searchcode.com/codesearch/view/71798584/)
{% highlight java %}
244. HSSFCell cell = row.createCell(mycol);
246. cell.setCellType(HSSFCell.CELL_TYPE_STRING);
248. cell.setCellValue(xldata[myrow][mycol]);
{% endhighlight %}

***

### [Hybrid_Framework.java](https://searchcode.com/codesearch/view/71798596/)
{% highlight java %}
375. public static String cellToString(HSSFCell cell) {
377.       int type = cell.getCellType();
381.               result = cell.getNumericCellValue();
384.               result = cell.getStringCellValue();
392.               result = cell.getBooleanCellValue();
{% endhighlight %}

***

### [Hybrid_Framework.java](https://searchcode.com/codesearch/view/71798596/)
{% highlight java %}
409. HSSFCell cell = row.createCell(mycol);
410. cell.setCellType(HSSFCell.CELL_TYPE_STRING);
411. cell.setCellValue(xldata[myrow][mycol]);
{% endhighlight %}

***

### [Hybrid_Framework.java](https://searchcode.com/codesearch/view/71798596/)
{% highlight java %}
427. HSSFCell cell = row.createCell(mycol);
428. cell.setCellType(HSSFCell.CELL_TYPE_STRING);
429. cell.setCellValue(xldata[myrow][mycol]);
{% endhighlight %}

***

