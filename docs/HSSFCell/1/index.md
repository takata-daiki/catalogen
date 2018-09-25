# HSSFCell @Cluster 1 (mycol, textvalue, value)

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
> creates an the document from this workbook , and { @ link # ' } s if it ' s not been removed . 
{% highlight java %}
177. HSSFCell cell = row.createCell(i);
189. cell.setCellStyle(style);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
> cell ranges in the { @ link # _ pointer } array . 
{% highlight java %}
97. HSSFCell cell = row.getCell((short) 4);
98. assertEquals("Test Value", cell.getStringCellValue());
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
> sets the " a cell " attribute of the cell @ since poi 3 . 1 4 - beta 2 
{% highlight java %}
166. HSSFCell cell = row.createCell((short) 0);
169. cell.setCellValue(cellValue);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
> sets the 
{% highlight java %}
69. HSSFCell cell = row.getCell(cellIndex);
70. if (cell!=null && !cell.getStringCellValue().isEmpty()) {
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> creates an the document from this workbook , if there are one . 
{% highlight java %}
276. HSSFCell cell = row.createCell(i);
277. cell.setCellStyle(style);
279. cell.setCellValue(text);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
> creates a 
{% highlight java %}
121. final HSSFCell cell = row.createCell(x);
122. cell.setCellValue(content);
123. cell.setCellStyle(cellStyle);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
> creates an empty string from the given xml , the two ( a byte array ) . 
{% highlight java %}
54. HSSFCell cell = row.createCell(i + nextCellOffset);
55. cell.setCellValue(column.getText());
62. cell.setCellStyle(csHeader);
{% endhighlight %}

***

### [Hybrid_Framework.java](https://searchcode.com/codesearch/view/71798596/)
> test that we can read existing column styles that are not have a shape , or poi type is for to be row 2 , and 1 2 . 
{% highlight java %}
409. HSSFCell cell = row.createCell(mycol);
410. cell.setCellType(HSSFCell.CELL_TYPE_STRING);
411. cell.setCellValue(xldata[myrow][mycol]);
{% endhighlight %}

***

### [hybrid.java](https://searchcode.com/codesearch/view/71798584/)
> test that we can read existing column styles that are not have a shape , or poi type is for to be row 2 , and 1 2 . 
{% highlight java %}
244. HSSFCell cell = row.createCell(mycol);
246. cell.setCellType(HSSFCell.CELL_TYPE_STRING);
248. cell.setCellValue(xldata[myrow][mycol]);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> creates an the document from this workbook , if there are one . 
{% highlight java %}
124. HSSFCell cell = row.createCell(i);
125. cell.setCellStyle(style);
127. cell.setCellValue(text);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
> creates a 
{% highlight java %}
138. final HSSFCell cell = row.createCell(x);
139. cell.setCellValue(content);
140. cell.setCellStyle(cellStyle);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
> creates a 
{% highlight java %}
130. final HSSFCell cell = row.createCell(x);
131. cell.setCellValue(euroAndCents);
132. cell.setCellStyle(cellStyle);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> creates an a cell that is data for this . 
{% highlight java %}
149. HSSFCell cell = row.createCell(j);
150. cell.setCellStyle(style2);
179.       cell.setCellValue(Double.parseDouble(textValue));
181.       cell.setCellValue(textValue);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
> sets the 
{% highlight java %}
202. HSSFCell cell = row.createCell(i);
213.   cell.setCellStyle(style);
220.   cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
223.   cell.setCellFormula("SUM(" + str[i] + "3:" + str[i]
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> test writing a hyperlink with large number of unique strings , open resulting file in excel to check results ! 
{% highlight java %}
367. HSSFCell cell = row.createCell(i);
368. cell.setCellStyle(style2);
378.   cell.setCellValue(richString);
381.   cell.setCellValue(new HSSFRichTextString(""));
384.   cell.setCellFormula(formula);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> creates an a cell that is data for this cell @ throws illegalstateexception if the cell type returned by { @ link # getcelltypeenum ( ) } isn ' t { @ link celltype # formula } @ see org . apache . poi . ss . usermodel . drawing 
{% highlight java %}
304. HSSFCell cell = row.createCell(i);
305. cell.setCellStyle(style2);
346.       cell.setCellStyle(style3);
347.       cell.setCellValue(Double.parseDouble(textValue));
349.       cell.setCellStyle(style4);
352.       cell.setCellValue(textValue);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
> sets the cell value using object type information . @ param cell cell to change @ param value value to set 
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

