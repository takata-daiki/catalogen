# HSSFFont @Cluster 1 (index, setcolor, short)

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> create a new font and add it to the workbook ' s font table @ return new font object 
{% highlight java %}
91. HSSFFont font2 = workbook.createFont();
92. font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
> set a font for this sheet ( 0 based ) . @ param the font to use @ param font the font to use . 
{% highlight java %}
87. HSSFFont hssfFont = theCell.getCellStyle().getFont(getWorkbook());
88. return hssfFont.getUnderline() != 0;
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
> add the specified shape to the table @ see org . apache . poi . hssf . usermodel . 
{% highlight java %}
209. HSSFFont f = wb.createFont();
210. f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> test that we get the same value as excel and , for 
{% highlight java %}
92. private HSSFFont fontWhiteColor;
250.   fontWhiteColor.setColor((short)1);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> test that we get the same value as excel and , for 
{% highlight java %}
244. HSSFFont font2 = workbook.createFont();
245. font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/102528302/)
> sets the line count . @ see # @ see org . apache . poi . openxml 4 j . opc . packageproperties # index ( and with the type ) 
{% highlight java %}
120. private HSSFFont m_fontHeader = null;
161.       m_fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> ensure that font and color rich text attributes defined in a xssfrichtextstring are passed to xssfsimpleshape . see bugzilla 5 4 9 6 9 . 
{% highlight java %}
373. HSSFFont font2 = wb.createFont();
374. font2.setItalic(true);
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
> initialize the . @ param color the 
{% highlight java %}
82. HSSFFont hssfFont = theCell.getCellStyle().getFont(getWorkbook());
83. return hssfFont.getStrikeout();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> ensure that the relationships collection is not null . 
{% highlight java %}
375. HSSFFont font3 = wb.createFont();
376. font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
> org . apache . poi . openxml 4 j . opc . in # index ( int ) 
{% highlight java %}
206. HSSFFont font = wb.createFont();
207. font.setColor(HSSFColor.BLUE_GREY.index);
208. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> add the specified block to the collection of properties table @ see org . apache . poi . xwpf . usermodel . ibody # getpart ( ) 
{% highlight java %}
233. HSSFFont font = createFont(workbook, HSSFColor.BLACK.index, HSSFFont.BOLDWEIGHT_BOLD);
234. font.setFontHeightInPoints((short) 12);
235. font.setFontName("微软雅黑");
{% endhighlight %}

***

### [ExcelExporterUtil.java](https://searchcode.com/codesearch/view/75361112/)
> org . apache . poi . openxml 4 j . opc . stream to an existing object to a or - shape . 
{% highlight java %}
23. HSSFFont font = workbook.createFont();        
24. font.setColor(HSSFColor.BLACK.index);
25. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
> test that we get the same value as excel and , for 
{% highlight java %}
83. HSSFFont dataCellFont = workBook.createFont();
84. dataCellFont.setFontHeightInPoints((short)10);
85. dataCellFont.setColor(HSSFColor.GREY_80_PERCENT.index);  
{% endhighlight %}

***

### [CustomExcelHssfView.java](https://searchcode.com/codesearch/view/73662641/)
> test that we get the same value as excel and , for 
{% highlight java %}
86. HSSFFont bold = wb.createFont();
87. bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
88. bold.setColor(HSSFColor.WHITE.index);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> ensure that the . @ param this cell to set the formula sheet to 
{% highlight java %}
370. HSSFFont font1 = wb.createFont();
371. font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
372. font1.setFontHeightInPoints((short)10);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> create a new font and add it to the workbook ' s font table @ return new font object 
{% highlight java %}
80. HSSFFont font = createFont(workbook, HSSFColor.BLACK.index, HSSFFont.BOLDWEIGHT_BOLD);
81. font.setFontHeightInPoints((short) 12);
82. font.setFontName("微软雅黑");
{% endhighlight %}

***

### [TranslationExportCommand.java](https://searchcode.com/codesearch/view/107150781/)
> create the . @ param font the font to use . 
{% highlight java %}
86. HSSFFont font = wb.createFont();
87. font.setFontName("Trebuchet MS");
88. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [XPathExcelTemplateService.java](https://searchcode.com/codesearch/view/114533602/)
> org . apache . poi . openxml 4 j . opc . in # index ( int ) 
{% highlight java %}
182. HSSFFont font = wb.createFont();
183. font.setColor(HSSFColor.BLUE_GREY.index);
184. font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
> org . apache . poi . openxml 4 j . opc . stream to an array of the will created column and then the value of the @ param value the boolean value to set this to . 
{% highlight java %}
74. HSSFFont headerCellFont = workBook.createFont();
75. headerCellFont.setFontHeightInPoints((short)10);
76. headerCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
77. headerCellFont.setColor(HSSFColor.WHITE.index);
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
> org . apache . poi . openxml 4 j . opc . stream to an array of the will created column and then the value of the @ param data the byte array to be converted @ param offset a starting offset into the byte array @ return the unsigned short ( 1 6 - bit ) value 
{% highlight java %}
108. HSSFFont headingFont = wb.createFont();
109. headingFont.setFontHeightInPoints((short)10);
110. headingFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
111. headingFont.setColor(HSSFColor.WHITE.index);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
> create the . @ param font the font to apply to this text run . the value of { @ code null } removes the run specific font setting , so the default setting is activated again . 
{% highlight java %}
91. final HSSFFont font = workbook.createFont();
92. font.setFontHeightInPoints((short) 10);
93. font.setFontName("Arial");
95.   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
98.   font.setUnderline(HSSFFont.U_DOUBLE);
{% endhighlight %}

***

