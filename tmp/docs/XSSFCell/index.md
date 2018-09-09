# XSSFCell

***

### [Cluster 1](./1)
{% highlight java %}
111. XSSFCell xc = (XSSFCell) cell;
112. String rawValue = xc.getRawValue();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
66. XSSFCell cell = row.getCell(cellIndex);
67. if (cell!=null && !cell.getStringCellValue().isEmpty()) {
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
35. private XSSFCell theCell;
59.     return theCell.getRowIndex();
63.     return theCell.getColumnIndex();
67.     XSSFComment xssfComment = theCell.getCellComment();
78.     XSSFFont xssfFont = theCell.getCellStyle().getFont();
83.   XSSFFont xssfFont = theCell.getCellStyle().getFont();
93.     if (theCell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
96.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
97.         return Boolean.toString(theCell.getBooleanCellValue());
99.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_ERROR) {
102.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
103.         return theCell.getCellFormula();
105.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
106.         return Double.toString(theCell.getNumericCellValue());
108.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
109.         return theCell.getRichStringCellValue().getString();
116.         theCell.setCellValue(new XSSFRichTextString(value));
119.         theCell.setCellValue(Boolean.parseBoolean(value));
124.         theCell.setCellFormula(value);
127.         theCell.setCellValue(Double.parseDouble(value));
139.     XSSFCellStyle cellStyle = theCell.getCellStyle();        
142.         theCell.setCellStyle(cellStyle);
159.     XSSFCellStyle cellStyle = theCell.getCellStyle();
211.   XSSFCellStyle cellStyle = theCell.getCellStyle();
249.   theCell.setCellStyle(style);
275.       XSSFColor colour = theCell.getCellStyle().getFont().getXSSFColor();
316. return theCell.hashCode();
323.   return cell.theCell.equals(this.theCell);      
341. XSSFSheet sheet = theCell.getSheet();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
257. XSSFCell xssfCell = xssfRow.getCell(setCellValue.getCol());
263.         xssfCell.setCellValue(new XSSFRichTextString(setCellValue.getNewValue().toString()));
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
110. XSSFCell cell1 = dataRow.createCell(0);
111. cell1.setCellStyle(dataStyle);
112. cell1.setCellValue(new XSSFRichTextString(listBetreuung
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
89. XSSFCell headerCell1 = headerRow.createCell(1);
90. headerCell1.setCellStyle(headerStyle);
91. headerCell1.setCellValue("Betreuerinnen");
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
40. XSSFCell cell = row.createCell(0);
41. cell.setCellValue("hello world");
{% endhighlight %}

***

