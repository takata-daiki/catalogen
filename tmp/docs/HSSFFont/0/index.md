# HSSFFont @Cluster 1

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
83. HSSFFont dataCellFont = workBook.createFont();
84. dataCellFont.setFontHeightInPoints((short)10);
85. dataCellFont.setColor(HSSFColor.GREY_80_PERCENT.index);  
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
74. HSSFFont headerCellFont = workBook.createFont();
75. headerCellFont.setFontHeightInPoints((short)10);
76. headerCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
77. headerCellFont.setColor(HSSFColor.WHITE.index);
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
209. HSSFFont f = wb.createFont();
210. f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
108. HSSFFont headingFont = wb.createFont();
109. headingFont.setFontHeightInPoints((short)10);
110. headingFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
111. headingFont.setColor(HSSFColor.WHITE.index);
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
87. HSSFFont hssfFont = theCell.getCellStyle().getFont(getWorkbook());
88. return hssfFont.getUnderline() != 0;
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
92. HSSFFont hssfFont = theCell.getCellStyle().getFont(getWorkbook());
93. return hssfFont.getItalic();
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
82. HSSFFont hssfFont = theCell.getCellStyle().getFont(getWorkbook());
83. return hssfFont.getStrikeout();
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
242. HSSFFont hssfFont = cellStyle.getFont(getWorkbook());
243. short colorIndex = hssfFont.getColor();
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
148. HSSFFont font = cellStyle.getFont(getWorkbook());
154.     font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
157.     font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
91. HSSFFont font2 = workbook.createFont();
92. font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
244. HSSFFont font2 = workbook.createFont();
245. font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
233. HSSFFont font = createFont(workbook, HSSFColor.BLACK.index, HSSFFont.BOLDWEIGHT_BOLD);
234. font.setFontHeightInPoints((short) 12);
235. font.setFontName("微软雅黑");
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
369. HSSFFont font4 = workbook.createFont();
370. font4.setColor(HSSFColor.BLACK.index);
371. font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
80. HSSFFont font = createFont(workbook, HSSFColor.BLACK.index, HSSFFont.BOLDWEIGHT_BOLD);
81. font.setFontHeightInPoints((short) 12);
82. font.setFontName("微软雅黑");
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
412. HSSFFont font = workbook.createFont();
413. font.setColor(color);
414. font.setFontHeightInPoints((short) 12);
415. font.setBoldweight(bold);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
91. final HSSFFont font = workbook.createFont();
92. font.setFontHeightInPoints((short) 10);
93. font.setFontName("Arial");
95.   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
98.   font.setUnderline(HSSFFont.U_DOUBLE);
{% endhighlight %}

***

### [CustomExcelHssfView.java](https://searchcode.com/codesearch/view/73662641/)
{% highlight java %}
86. HSSFFont bold = wb.createFont();
87. bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
88. bold.setColor(HSSFColor.WHITE.index);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
206. HSSFFont font = wb.createFont();
207. font.setColor(HSSFColor.BLUE_GREY.index);
208. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
{% highlight java %}
182. HSSFFont font = wb.createFont();
183. font.setColor(HSSFColor.BLUE_GREY.index);
184. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [SpreadSheetPoiHelper.java](https://searchcode.com/codesearch/view/73882044/)
{% highlight java %}
324. final HSSFFont font = workbook.getFontAt ( cell.getCellStyle ().getFontIndex () );
327.     value.setStrikeThrough ( font.getStrikeout () );
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/122565152/)
{% highlight java %}
194. HSSFFont hFont = (HSSFFont)font;
197.   hFont.setColor(colourIndex);
{% endhighlight %}

***

### [TranslationExportCommand.java](https://searchcode.com/codesearch/view/107150781/)
{% highlight java %}
86. HSSFFont font = wb.createFont();
87. font.setFontName("Trebuchet MS");
88. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [MessageEntryExportCommand.java](https://searchcode.com/codesearch/view/107150784/)
{% highlight java %}
80. HSSFFont font = wb.createFont();
81. font.setFontName("Trebuchet MS");
82. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [DataLoaderXls.java](https://searchcode.com/codesearch/view/73880973/)
{% highlight java %}
108. final HSSFFont font = this.workbook.getFontAt ( style.getFontIndex () );
113. return font.getStrikeout ();
{% endhighlight %}

***

### [ExcelExporterUtil.java](https://searchcode.com/codesearch/view/75361112/)
{% highlight java %}
23. HSSFFont font = workbook.createFont();        
24. font.setColor(HSSFColor.BLACK.index);
25. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
92. private HSSFFont fontWhiteColor;
250.   fontWhiteColor.setColor((short)1);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
373. HSSFFont font2 = wb.createFont();
374. font2.setItalic(true);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
375. HSSFFont font3 = wb.createFont();
376. font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
370. HSSFFont font1 = wb.createFont();
371. font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
372. font1.setFontHeightInPoints((short)10);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
377. HSSFFont font4 = wb.createFont();
378. font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
379. font4.setItalic(true);
380. font4.setFontHeightInPoints((short)8);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/102528302/)
{% highlight java %}
120. private HSSFFont m_fontHeader = null;
161.       m_fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/102528302/)
{% highlight java %}
157. HSSFFont font = null;
167.   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
168.   font.setItalic(true);
{% endhighlight %}

***

