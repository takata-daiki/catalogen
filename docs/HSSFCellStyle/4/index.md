# HSSFCellStyle @Cluster 4

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
69. HSSFCellStyle cellStyleHeader = workBook.createCellStyle();
70. cellStyleHeader.setWrapText(true);
71. cellStyleHeader.setVerticalAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
72. cellStyleHeader.setFillForegroundColor(HSSFColor.GREEN.index);
73. cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);      
78. cellStyleHeader.setFont(headerCellFont);                
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
80. HSSFCellStyle cellStyleData = workBook.createCellStyle();
81. cellStyleData.setWrapText(true);
82. cellStyleData.setVerticalAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
86. cellStyleData.setFont(dataCellFont);      
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
86. HSSFCellStyle style2 = createStyle(workbook, HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
89. style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
94. style2.setFont(font2);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
239. HSSFCellStyle style2 = createStyle(workbook, HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
242. style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
247. style2.setFont(font2);
372.     style2.setFont(font4);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
398. HSSFCellStyle style = workbook.createCellStyle();
400. style.setFillForegroundColor(fillForegroundColor);
401. style.setFillPattern(fillPattern);
402. style.setBorderBottom(borderBottom);
403. style.setBorderLeft(borderleft);
404. style.setBorderRight(borderright);
405. style.setBorderTop(bordertop);
406. style.setAlignment(alignment);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
107. final HSSFCellStyle headerCellStyle = workbook.createCellStyle();
108. headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
109. headerCellStyle.setFont(headerFont);
110. headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
111. headerCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
112. headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
113. headerCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
114. headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
115. headerCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
147. final HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
148. bodyCellStyle.setFont(bodyCellFont);
149. bodyCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
150. bodyCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
151. bodyCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
152. bodyCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
158. final HSSFCellStyle cellStyle = createBorderedHeaderCellStyle(workbook);
159. cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
166. final HSSFCellStyle cellStyle = createBorderedCellStyle(workbook);
169.   cellStyle.setFont(font);
174. cellStyle.setDataFormat((short) 4);
{% endhighlight %}

***

### [Excel.java](https://searchcode.com/codesearch/view/71678655/)
{% highlight java %}
149. HSSFCellStyle styleUren = workbook.createCellStyle();
150. styleUren.setBorderLeft(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [Excel.java](https://searchcode.com/codesearch/view/71678655/)
{% highlight java %}
152. HSSFCellStyle styleNaam = workbook.createCellStyle();
153. styleNaam.setBorderLeft(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [Excel.java](https://searchcode.com/codesearch/view/71678655/)
{% highlight java %}
155. HSSFCellStyle styleEndOfTasks = workbook.createCellStyle();
156. styleEndOfTasks.setBorderTop(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [PoiExcel.java](https://searchcode.com/codesearch/view/107347819/)
{% highlight java %}
37. HSSFCellStyle style = workbook.createCellStyle();
38. style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
39. style.setBottomBorderColor(HSSFColor.BLACK.index);
40. style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
41. style.setLeftBorderColor(HSSFColor.GREEN.index);
42. style.setBorderRight(HSSFCellStyle.BORDER_THIN);
43. style.setRightBorderColor(HSSFColor.BLUE.index);
44. style.setTopBorderColor(HSSFColor.BLACK.index);
{% endhighlight %}

***

### [PoiExcel.java](https://searchcode.com/codesearch/view/107347819/)
{% highlight java %}
101. HSSFCellStyle style = workbook.createCellStyle();
102. style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
103. style.setBottomBorderColor(HSSFColor.BLACK.index);
104. style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
105. style.setLeftBorderColor(HSSFColor.GREEN.index);
106. style.setBorderRight(HSSFCellStyle.BORDER_THIN);
107. style.setRightBorderColor(HSSFColor.BLUE.index);
108. style.setTopBorderColor(HSSFColor.BLACK.index);
{% endhighlight %}

***

### [ExcelExporterUtil.java](https://searchcode.com/codesearch/view/75361112/)
{% highlight java %}
21. final HSSFCellStyle cellStyle = workbook.createCellStyle();
27. cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);        
28. cellStyle.setFont(font);
{% endhighlight %}

***

### [ExportEventsImpl.java](https://searchcode.com/codesearch/view/122444114/)
{% highlight java %}
184. final HSSFCellStyle dateCellStyle = workbook.createCellStyle ();
185. dateCellStyle.setDataFormat ( dateFormat.getFormat ( "YYYY-MM-DD hh:mm:ss.000" ) );
{% endhighlight %}

***

