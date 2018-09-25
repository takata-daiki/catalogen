# XSSFCell @Cluster 2 (cell, cell1, thecell)

***

### [WorkbookXSSFImpl.java](https://searchcode.com/codesearch/view/72854562/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
257. XSSFCell xssfCell = xssfRow.getCell(setCellValue.getCol());
263.         xssfCell.setCellValue(new XSSFRichTextString(setCellValue.getNewValue().toString()));
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> create and 
{% highlight java %}
121. XSSFCell cell0 = dataRow.createCell(1);
122. cell0.setCellStyle(dataStyle);
123. cell0.setCellValue(new XSSFRichTextString(f.getNummer()+" "+f.getNachname_patient()));
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
> creates an empty 5 element if one does not already exist 
{% highlight java %}
107. XSSFCell cell1 = dataRow.createCell(0);
108. cell1.setCellStyle(dataStyle);
109. cell1.setCellValue(new XSSFRichTextString(b.getAngefangen()));
{% endhighlight %}

***

### [ExcelBetreuerinBetreuung.java](https://searchcode.com/codesearch/view/91974062/)
> create new . if you . . . . 
{% highlight java %}
152. XSSFCell betreuerinnen_cell = dataRow.createCell(0);
153. betreuerinnen_cell.setCellStyle(dataStyle);
154. betreuerinnen_cell.setCellValue(new XSSFRichTextString(""
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
> creates the 
{% highlight java %}
115. XSSFCell cell0 = dataRow.createCell(1);
116. cell0.setCellStyle(dataStyle);
117. cell0.setCellValue(new XSSFRichTextString(listbetreuerin));
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
> creates the 
{% highlight java %}
115. XSSFCell cell0 = dataRow.createCell(1);
116. cell0.setCellStyle(dataStyle);
117. cell0.setCellValue(new XSSFRichTextString(listbetreuerin));
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
> creates an empty field for the data of being embedded . < p > the 
{% highlight java %}
117. XSSFCell cell0 = dataRow.createCell(1);
118. cell0.setCellStyle(dataStyle);
119. cell0.setCellValue(new XSSFRichTextString(listfamilie));
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
> creates the 
{% highlight java %}
112. XSSFCell cell1 = dataRow.createCell(0);
113. cell1.setCellStyle(dataStyle);
114. cell1.setCellValue(new XSSFRichTextString(listBetreuung
{% endhighlight %}

***

### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/)
> creates an empty param right - formatting @ param object the workbook to create 
{% highlight java %}
113. XSSFCell cell1 = dataRow.createCell(0);
114. cell1.setCellStyle(dataStyle);
115. cell1.setCellValue(new XSSFRichTextString(listzahlung
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
> create the . @ param data the byte array to be a - must 
{% highlight java %}
110. XSSFCell cell1 = dataRow.createCell(0);
111. cell1.setCellStyle(dataStyle);
112. cell1.setCellValue(new XSSFRichTextString(listBetreuung
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
> create the . @ param data the byte array to be a - must 
{% highlight java %}
110. XSSFCell cell1 = dataRow.createCell(0);
111. cell1.setCellStyle(dataStyle);
112. cell1.setCellValue(new XSSFRichTextString(listBetreuung
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
> creates an empty field for the format such and sets it and a formula text 
{% highlight java %}
111. XSSFCell cell0 = dataRow.createCell(1);
112. cell0.setCellStyle(dataStyle);
113. cell0.setCellValue(new XSSFRichTextString(b.getNummer()+" "+b.getNachname()));
{% endhighlight %}

***

### [ExcelFamilieZahlungen.java](https://searchcode.com/codesearch/view/91974043/)
> create new . if the , null on formula cells , the number of value is we get one sheet , to data value in in . 
{% highlight java %}
148. XSSFCell familien_cell = dataRow.createCell(0);
149. familien_cell.setCellStyle(dataStyle);
150. familien_cell.setCellValue(new XSSFRichTextString(""
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> tests that the create and sets the font is based on @ param . 
{% highlight java %}
117. XSSFCell cell1f = dataRow.createCell(0);
118. cell1f.setCellStyle(dataStyle);
119. cell1f.setCellValue(new XSSFRichTextString(f.getAufgehort()));
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
> creates an empty field for the data of being embedded . < p > the 
{% highlight java %}
124. XSSFCell cell = dataRow.createCell(j + 2);
125. cell.setCellStyle(dataStyle);
128.   cell.setCellValue(new XSSFRichTextString(""
132.   cell.setCellValue(new XSSFRichTextString(listBetreuung
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
> creates an empty field for the data of being embedded . < p > the 
{% highlight java %}
124. XSSFCell cell = dataRow.createCell(j + 2);
125. cell.setCellStyle(dataStyle);
128.   cell.setCellValue(new XSSFRichTextString(""
132.   cell.setCellValue(new XSSFRichTextString(listBetreuung
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
130. XSSFCell cell1 = dataRow.createCell(j + 2);
131. cell1.setCellStyle(dataStyle);
134.   cell1.setCellValue(new XSSFRichTextString(""
146.   cell1.setCellValue(new XSSFRichTextString(f
153.     cell1.setCellValue(new XSSFRichTextString(neu
338.   cell1.setCellValue(new XSSFRichTextString(f.getArea()));
{% endhighlight %}

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
> test that we get the same value as excel and , for 
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

