# HSSFCell @Cluster 1

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
132. HSSFCell cell = row.getCell((short) 0);
133. assertEquals("Test Template", cell.getStringCellValue());
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
177. HSSFCell cell = row.createCell(i);
189. cell.setCellStyle(style);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
202. HSSFCell cell = row.getCell((short) 0);
203. assertEquals("Test Template auf Deutsch", cell.getStringCellValue());
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
167. HSSFCell cell = row.getCell((short) 0);
168. assertEquals("Test Template American English", cell.getStringCellValue());
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
166. HSSFCell cell = row.createCell((short) 0);
169. cell.setCellValue(cellValue);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
69. HSSFCell cell = row.getCell(cellIndex);
70. if (cell!=null && !cell.getStringCellValue().isEmpty()) {
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
142. HSSFCell c1 = row.createCell(0);
143. c1.setCellStyle(rowStyle);
144. c1.setCellValue(new HSSFRichTextString(p[0]));
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
276. HSSFCell cell = row.createCell(i);
277. cell.setCellStyle(style);
279. cell.setCellValue(text);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
121. final HSSFCell cell = row.createCell(x);
122. cell.setCellValue(content);
123. cell.setCellStyle(cellStyle);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
54. HSSFCell cell = row.createCell(i + nextCellOffset);
55. cell.setCellValue(column.getText());
62. cell.setCellStyle(csHeader);
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
145. HSSFCell c2 = row.createCell(1);
146. c2.setCellStyle(rowStyle);
147. c2.setCellValue(new HSSFRichTextString(p[1]));
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
134. HSSFCell dataCell = row.createCell(i);
137. dataCell.setCellStyle(cellStyleData);
139. dataCell.setCellValue(new HSSFRichTextString(dataCellContent));
{% endhighlight %}

***

### [Hybrid_Framework.java](https://searchcode.com/codesearch/view/71798596/)
{% highlight java %}
427. HSSFCell cell = row.createCell(mycol);
428. cell.setCellType(HSSFCell.CELL_TYPE_STRING);
429. cell.setCellValue(xldata[myrow][mycol]);
{% endhighlight %}

***

### [Hybrid_Framework.java](https://searchcode.com/codesearch/view/71798596/)
{% highlight java %}
409. HSSFCell cell = row.createCell(mycol);
410. cell.setCellType(HSSFCell.CELL_TYPE_STRING);
411. cell.setCellValue(xldata[myrow][mycol]);
{% endhighlight %}

***

### [hybrid.java](https://searchcode.com/codesearch/view/71798584/)
{% highlight java %}
244. HSSFCell cell = row.createCell(mycol);
246. cell.setCellType(HSSFCell.CELL_TYPE_STRING);
248. cell.setCellValue(xldata[myrow][mycol]);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
124. HSSFCell cell = row.createCell(i);
125. cell.setCellStyle(style);
127. cell.setCellValue(text);
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
130. final HSSFCell cell = row.createCell(x);
131. cell.setCellValue(euroAndCents);
132. cell.setCellStyle(cellStyle);
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

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
202. HSSFCell cell = row.createCell(i);
213.   cell.setCellStyle(style);
220.   cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
223.   cell.setCellFormula("SUM(" + str[i] + "3:" + str[i]
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

