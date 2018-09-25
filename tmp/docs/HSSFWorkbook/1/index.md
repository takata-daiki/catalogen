# HSSFWorkbook @Cluster 1 (createcellstyle, getsheetindex, wb)

***

### [SpreadSheetPoiHelper.java](https://searchcode.com/codesearch/view/73882044/)
> read the xml signature 0 and throws excel 
{% highlight java %}
242. final HSSFWorkbook workbook = new HSSFWorkbook ( new FileInputStream ( fileName ) );
244. final Sheet sheet = workbook.getSheetAt ( 0 );
{% endhighlight %}

***

### [UpLoadFileWindow.java](https://searchcode.com/codesearch/view/42988393/)
> sets the document 
{% highlight java %}
180. HSSFWorkbook wb = new HSSFWorkbook(bin);
181. HSSFSheet st = wb.getSheetAt(0);
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
> test that we get the same value as excel and , for 
{% highlight java %}
45. private HSSFWorkbook workbook;
336. return workbook.getSheetName(getSheetIndex());
342. return workbook.getSheetIndex(sheet);
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/48925127/)
> sets the a number of from the spreadsheet , i . e . null ( not a 0 x 1 0 ) @ throws evaluationexception ( # num ! ) if < tt > result < / tt > is < tt > nan < / > or < tt > infinity < / tt > 
{% highlight java %}
44. HSSFWorkbook wb = new HSSFWorkbook(input);
56. sNum = wb.getNumberOfSheets();
59.   if ((sheet = wb.getSheetAt(i)) == null) {
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> dump the record content into < code > in . will 1 
{% highlight java %}
70. HSSFWorkbook workbook = new HSSFWorkbook();
72. HSSFSheet sheet = workbook.createSheet(title);
91. HSSFFont font2 = workbook.createFont();
101. HSSFDataFormat format = workbook.createDataFormat();
203.   workbook.write(out);
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
> create an array of records from an input stream @ param in the stream 
{% highlight java %}
103. HSSFWorkbook wb = new HSSFWorkbook();
105. HSSFSheet sheet = wb.createSheet("Resources Restriction");
107. HSSFCellStyle headingStyle = wb.createCellStyle();
108. HSSFFont headingFont = wb.createFont();
118. HSSFCellStyle rowStyle = wb.createCellStyle();
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
> test that we get the same value as excel and , for 
{% highlight java %}
30. protected final HSSFWorkbook workbook;
62.   sheet = workbook.createSheet(sheetName);
91.   final HSSFFont font = workbook.createFont();
107.   final HSSFCellStyle headerCellStyle = workbook.createCellStyle();
147.   final HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
199.     drawingPatriarch = workbook.getSheet(sheetName)
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
> create the . @ param data the byte array to be in the records @ param offset the offset into the byte array @ return the number of bytes written 
{% highlight java %}
47. HSSFWorkbook    workBook = null;
67.     HSSFSheet sheet = workBook.createSheet();
69.       HSSFCellStyle cellStyleHeader = workBook.createCellStyle();
74.       HSSFFont headerCellFont = workBook.createFont();
80.       HSSFCellStyle cellStyleData = workBook.createCellStyle();
83.       HSSFFont dataCellFont = workBook.createFont();
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
> test that we get the same value as excel and , for 
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

### [ExportToExcel.java](https://searchcode.com/codesearch/view/46011490/)
> test that we get the same value as excel and , for 
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

### [WorkbookHSSFImpl.java](https://searchcode.com/codesearch/view/72854626/)
> test that we get the same value as excel and , for 
{% highlight java %}
50. private HSSFWorkbook workbook;
58.     workbook.createSheet();        
102.     for(int i = 0; i < workbook.getNumberOfNames(); i++) {
103.         HSSFName name = workbook.getNameAt(i);
121.     return workbook.getSheet(name) != null;
125.     int index = workbook.getSheetIndex(name);
127.         workbook.removeSheetAt(index);
140.     if(workbook.getName(name) != null) {
141.         workbook.removeName(name);
143.     HSSFName hssfName = workbook.createName();
149.     workbook.removeName(name);
172.   HSSFSheet hssfSheet = workbook.createSheet(name);
201.     for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
202.         sheets.add(new SheetHSSFImpl(this, workbook.getSheetAt(i)));
208.     HSSFSheet hssfSheet = workbook.getSheet(name);
218.     HSSFSheet hssfSheet = workbook.getSheetAt(index);
230.     workbook.write(stream);        
235.     HSSFSheet hssfSheet = workbook.getSheet(setCellValue.getSheet().getName());
{% endhighlight %}

***

