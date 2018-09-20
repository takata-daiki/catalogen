# HSSFCellStyle @Cluster 6

***

### [TranslationExportCommand.java](https://searchcode.com/codesearch/view/107150781/)
{% highlight java %}
58. HSSFCellStyle hidden;
77.   hidden.setLocked(true);
{% endhighlight %}

***

### [Excel.java](https://searchcode.com/codesearch/view/71678655/)
{% highlight java %}
152. HSSFCellStyle styleNaam = workbook.createCellStyle();
153. styleNaam.setBorderLeft(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/61719045/)
{% highlight java %}
352. private void putRow(HSSFCellStyle cellStyle, HSSFCellStyle cellStyleD, HSSFCellStyle cellStyleN, 
356.   cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
238. HSSFCellStyle cellStyle = theCell.getCellStyle();
242. HSSFFont hssfFont = cellStyle.getFont(getWorkbook());
{% endhighlight %}

***

### [Excel.java](https://searchcode.com/codesearch/view/71678655/)
{% highlight java %}
155. HSSFCellStyle styleEndOfTasks = workbook.createCellStyle();
156. styleEndOfTasks.setBorderTop(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
217. HSSFCellStyle style = wb.createCellStyle();
218. style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
163. HSSFCellStyle cellStyle = theCell.getCellStyle();
167. HSSFFont hssfFont = cellStyle.getFont(getWorkbook());
{% endhighlight %}

***

### [ExportEventsImpl.java](https://searchcode.com/codesearch/view/122444114/)
{% highlight java %}
184. final HSSFCellStyle dateCellStyle = workbook.createCellStyle ();
185. dateCellStyle.setDataFormat ( dateFormat.getFormat ( "YYYY-MM-DD hh:mm:ss.000" ) );
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
{% highlight java %}
41. HSSFCellStyle newStyle = (HSSFCellStyle) style;
42. HSSFFont font = newStyle.getFont(workbook);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
229. HSSFCellStyle style = createStyle(workbook, HSSFColor.LIME.index, HSSFCellStyle.SOLID_FOREGROUND,
237. style.setFont(font);
{% endhighlight %}

***

### [TranslationExportCommand.java](https://searchcode.com/codesearch/view/107150781/)
{% highlight java %}
85. HSSFCellStyle style = wb.createCellStyle();
89.   style.setFont(font);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/66638946/)
{% highlight java %}
357. private void putRow(HSSFCellStyle cellStyle, HSSFCellStyle cellStyleD, HSSFCellStyle cellStyleN, 
361.   cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
{% endhighlight %}

***

### [MessageEntryExportCommand.java](https://searchcode.com/codesearch/view/107150784/)
{% highlight java %}
79. HSSFCellStyle style = wb.createCellStyle();
83.   style.setFont(font);
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
{% highlight java %}
68. HSSFCellStyle newStyle = (HSSFCellStyle) style;
69. HSSFFont font = newStyle.getFont(workbook);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
76. HSSFCellStyle style = createStyle(workbook, HSSFColor.LIME.index, HSSFCellStyle.SOLID_FOREGROUND,
84. style.setFont(font);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
158. final HSSFCellStyle cellStyle = createBorderedHeaderCellStyle(workbook);
159. cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
{% endhighlight %}

***

### [Excel.java](https://searchcode.com/codesearch/view/71678655/)
{% highlight java %}
149. HSSFCellStyle styleUren = workbook.createCellStyle();
150. styleUren.setBorderLeft(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [MessageEntryExportCommand.java](https://searchcode.com/codesearch/view/107150784/)
{% highlight java %}
52. HSSFCellStyle hidden;
71.   hidden.setLocked(true);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
204. HSSFCellStyle style = wb.createCellStyle();
205. style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
209. style.setFont(font);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
97. HSSFCellStyle style3 = createStyle(workbook, HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
100. style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
102. style3.setDataFormat(format.getFormat("0.000"));
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
225. HSSFCellStyle style = styles.get(colour);            
229.   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND );
230.   style.setFillForegroundColor(col.getIndex());
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
2027. HSSFCellStyle defaultCellStyle2 = wb.createCellStyle();
2028. defaultCellStyle2.cloneStyleFrom(distCell.getCellStyle());
2029. defaultCellStyle2.setWrapText(false);
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
105. HSSFCellStyle style4 = createStyle(workbook, HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
108. style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
109. style4.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
{% endhighlight %}

***

### [ExcelExporterUtil.java](https://searchcode.com/codesearch/view/75361112/)
{% highlight java %}
21. final HSSFCellStyle cellStyle = workbook.createCellStyle();
27. cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);        
28. cellStyle.setFont(font);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
258. HSSFCellStyle style4 = createStyle(workbook, HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
261. style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
262. style4.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
250. HSSFCellStyle style3 = createStyle(workbook, HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
253. style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
255. style3.setDataFormat(format.getFormat("0.000"));
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/122565152/)
{% highlight java %}
211. HSSFCellStyle cellStyle = (HSSFCellStyle)style;
214.   cellStyle.setFillForegroundColor(colourIndex);
215.   cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
25. private HSSFCellStyle csTitle;
214.   csTitle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
217.   csTitle.setFont(f);
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
143. HSSFCellStyle cellStyle = theCell.getCellStyle();        
148. HSSFFont font = cellStyle.getFont(getWorkbook());
151.     cellStyle.setFont(font);
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
118. HSSFCellStyle rowStyle = wb.createCellStyle();
119. rowStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
120. rowStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
121. rowStyle.setWrapText(true);
{% endhighlight %}

***

### [TranslationExportCommand.java](https://searchcode.com/codesearch/view/107150781/)
{% highlight java %}
56. HSSFCellStyle editable;
72.   editable.setLocked(false);
73.   editable.setWrapText(true);
74.   editable.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
{% endhighlight %}

***

### [MessageEntryExportCommand.java](https://searchcode.com/codesearch/view/107150784/)
{% highlight java %}
48. HSSFCellStyle locked;
61.   locked.setLocked(true);
62.   locked.setWrapText(true);
63.   locked.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
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

### [CustomExcelHssfView.java](https://searchcode.com/codesearch/view/73662641/)
{% highlight java %}
83. HSSFCellStyle headerStyle = wb.createCellStyle();
84. headerStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
85. headerStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
89. headerStyle.setFont(bold);
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

### [Report.java](https://searchcode.com/codesearch/view/111804187/)
{% highlight java %}
48. private HSSFCellStyle cellStyle;
170.   cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
171.   cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
172.   cellStyle.setBorderRight(CellStyle.BORDER_THIN);
{% endhighlight %}

***

### [TranslationExportCommand.java](https://searchcode.com/codesearch/view/107150781/)
{% highlight java %}
54. HSSFCellStyle locked;
67.   locked.setLocked(true);
68.   locked.setWrapText(true);
69.   locked.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
{% endhighlight %}

***

### [MessageEntryExportCommand.java](https://searchcode.com/codesearch/view/107150784/)
{% highlight java %}
50. HSSFCellStyle editable;
66.   editable.setLocked(false);
67.   editable.setWrapText(true);
68.   editable.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
179. HSSFCellStyle style = wb.createCellStyle();
180. style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
185. style.setFont(font);
186. style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/61719045/)
{% highlight java %}
265. HSSFCellStyle cellStyleT = book.createCellStyle();
266. cellStyleT.setWrapText(true);
267. cellStyleT.setAlignment(HSSFCellStyle.ALIGN_CENTER);
268. cellStyleT.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
269. cellStyleT.setFont(fontT);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/66638946/)
{% highlight java %}
183. HSSFCellStyle cellStyle = book.createCellStyle();
184. cellStyle.setWrapText(true);
185. cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
186. cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
189. cellStyle.setFont(font);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
276. HSSFCellStyle style = book.createCellStyle();
304.     style.setDataFormat(format.getFormat(cellFormat));
313.     style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
315.     style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
317.     style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/61719045/)
{% highlight java %}
65. HSSFCellStyle cellStyle = book.createCellStyle();
66. cellStyle.setWrapText(true);
67. cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
68. cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
69. cellStyle.setFont(font);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/61719045/)
{% highlight java %}
183. HSSFCellStyle cellStyle = book.createCellStyle();
184. cellStyle.setWrapText(true);
185. cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
186. cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
189. cellStyle.setFont(font);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/66638946/)
{% highlight java %}
265. HSSFCellStyle cellStyleT = book.createCellStyle();
266. cellStyleT.setWrapText(true);
267. cellStyleT.setAlignment(HSSFCellStyle.ALIGN_CENTER);
268. cellStyleT.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
269. cellStyleT.setFont(fontT);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/66638946/)
{% highlight java %}
65. HSSFCellStyle cellStyle = book.createCellStyle();
66. cellStyle.setWrapText(true);
67. cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
68. cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
69. cellStyle.setFont(font);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
23. protected HSSFCellStyle csText;
195.   csText.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
196.   csText.setBorderBottom(HSSFCellStyle.BORDER_THIN);
197.   csText.setBorderLeft(HSSFCellStyle.BORDER_THIN);
198.   csText.setBorderRight(HSSFCellStyle.BORDER_THIN);
199.   csText.setBorderTop(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

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

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
107. HSSFCellStyle headingStyle = wb.createCellStyle();
112. headingStyle.setFont(headingFont);
113. headingStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
114. headingStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
115. headingStyle.setFillForegroundColor(HSSFColor.GREEN.index);
116. headingStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
27. private HSSFCellStyle csDoubleNum;
228.   csDoubleNum.setDataFormat(wb.createDataFormat().getFormat("#.##"));
229.   csDoubleNum.setBorderBottom(HSSFCellStyle.BORDER_THIN);
230.   csDoubleNum.setBorderLeft(HSSFCellStyle.BORDER_THIN);
231.   csDoubleNum.setBorderRight(HSSFCellStyle.BORDER_THIN);
232.   csDoubleNum.setBorderTop(HSSFCellStyle.BORDER_THIN);
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

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
26. protected HSSFCellStyle csIntNum;
221.   csIntNum.setDataFormat(wb.createDataFormat().getFormat("#,##0"));
222.   csIntNum.setBorderBottom(HSSFCellStyle.BORDER_THIN);
223.   csIntNum.setBorderLeft(HSSFCellStyle.BORDER_THIN);
224.   csIntNum.setBorderRight(HSSFCellStyle.BORDER_THIN);
225.   csIntNum.setBorderTop(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [Report.java](https://searchcode.com/codesearch/view/111804187/)
{% highlight java %}
49. private HSSFCellStyle captionStyle;
176.   captionStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
177.   captionStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
178.   captionStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
179.   captionStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
180.   captionStyle.setAlignment(CellStyle.ALIGN_CENTER);
181.   captionStyle.setWrapText(true);
183.   captionStyle.setFont(font);
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

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/122565152/)
{% highlight java %}
151. HSSFCellStyle hStyle = (HSSFCellStyle)style;
159.       hStyle.setBorderTop(hBorderStyle);
160.       hStyle.setTopBorderColor(colourIndex);
164.       hStyle.setBorderLeft(hBorderStyle);
165.       hStyle.setLeftBorderColor(colourIndex);
169.       hStyle.setBorderRight(hBorderStyle);
170.       hStyle.setRightBorderColor(colourIndex);
174.       hStyle.setBorderBottom(hBorderStyle);
175.       hStyle.setBottomBorderColor(colourIndex);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
24. protected HSSFCellStyle csHeader;
202.   csHeader.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
203.   csHeader.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
204.   csHeader.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
205.   csHeader.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
206.   csHeader.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
207.   csHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
208.   csHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
211.   csHeader.setFont(f);
{% endhighlight %}

***

