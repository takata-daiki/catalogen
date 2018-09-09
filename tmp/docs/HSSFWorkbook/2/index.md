# HSSFWorkbook @Cluster 2

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
47. HSSFWorkbook    workBook = null;
67.     HSSFSheet sheet = workBook.createSheet();
69.       HSSFCellStyle cellStyleHeader = workBook.createCellStyle();
74.       HSSFFont headerCellFont = workBook.createFont();
80.       HSSFCellStyle cellStyleData = workBook.createCellStyle();
83.       HSSFFont dataCellFont = workBook.createFont();
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
103. HSSFWorkbook wb = new HSSFWorkbook();
105. HSSFSheet sheet = wb.createSheet("Resources Restriction");
107. HSSFCellStyle headingStyle = wb.createCellStyle();
108. HSSFFont headingFont = wb.createFont();
118. HSSFCellStyle rowStyle = wb.createCellStyle();
{% endhighlight %}

***

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
{% highlight java %}
22. private HSSFWorkbook wb;
94.   HSSFSheet sheet = wb.createSheet();
177.   wb.write(fileOut);
194.   csText = wb.createCellStyle();
201.   csHeader = wb.createCellStyle();
209.   HSSFFont f = wb.createFont();
213.   csTitle = wb.createCellStyle();
215.   f = wb.createFont();
220.   csIntNum = wb.createCellStyle();
221.   csIntNum.setDataFormat(wb.createDataFormat().getFormat("#,##0"));
227.   csDoubleNum = wb.createCellStyle();
228.   csDoubleNum.setDataFormat(wb.createDataFormat().getFormat("#.##"));
{% endhighlight %}

***

### [ExcelIdentifier.java](https://searchcode.com/codesearch/view/52992680/)
{% highlight java %}
22. private HSSFWorkbook workbook;
44.     HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
70. HSSFWorkbook workbook = new HSSFWorkbook();
72. HSSFSheet sheet = workbook.createSheet(title);
91. HSSFFont font2 = workbook.createFont();
101. HSSFDataFormat format = workbook.createDataFormat();
203.   workbook.write(out);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
223. HSSFWorkbook workbook = new HSSFWorkbook();
225. HSSFSheet sheet = workbook.createSheet(title);
244. HSSFFont font2 = workbook.createFont();
254. HSSFDataFormat format = workbook.createDataFormat();
328.         patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
369.     HSSFFont font4 = workbook.createFont();
390.   workbook.write(out);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
40. private HSSFWorkbook hssfWorkbook;
90.     hssfWorkbook.setSheetName(hssfWorkbook.getSheetIndex(sheet), name);
95.     return hssfWorkbook.isSheetHidden(hssfWorkbook.getSheetIndex(sheet));
99.     hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), b);
104.         hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), 2);
107.         hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), false);
113. return hssfWorkbook.isSheetVeryHidden(hssfWorkbook.getSheetIndex(sheet));
134.     return hssfWorkbook.getSheetName(hssfWorkbook.getSheetIndex(sheet));
{% endhighlight %}

***

### [SpreadSheetPoiHelper.java](https://searchcode.com/codesearch/view/73882044/)
{% highlight java %}
242. final HSSFWorkbook workbook = new HSSFWorkbook ( new FileInputStream ( fileName ) );
244. final Sheet sheet = workbook.getSheetAt ( 0 );
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
45. private HSSFWorkbook workbook;
336. return workbook.getSheetName(getSheetIndex());
342. return workbook.getSheetIndex(sheet);
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
30. protected final HSSFWorkbook workbook;
62.   sheet = workbook.createSheet(sheetName);
91.   final HSSFFont font = workbook.createFont();
107.   final HSSFCellStyle headerCellStyle = workbook.createCellStyle();
147.   final HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
199.     drawingPatriarch = workbook.getSheet(sheetName)
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
46. public SheetBuilderBase(final HSSFWorkbook workbook, final String sheetName) {
62.   sheet = workbook.createSheet(sheetName);
{% endhighlight %}

***

