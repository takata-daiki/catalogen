# XSSFWorkbook

***

### [Cluster 1](./1)
{% highlight java %}
74. XSSFWorkbook document = (XSSFWorkbook) extractor.getDocument();
76. for (int i = 0; i < document.getNumberOfSheets(); i++) {
78.     XSSFSheet sheet = (XSSFSheet) document.getSheetAt(i);
79.     xhtml.element("h1", document.getSheetName(i));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
63. private XSSFWorkbook workbook;
71.     workbook.createSheet();
85.     workbook.write(stream);        
89.     for (int i=0; i<workbook.getNumberOfSheets();i++) {
90.       workbook.getSheetAt(i).getColumnHelper().cleanColumns();
118.     for(int i = 0; i < workbook.getNumberOfNames(); i++) {
119.         XSSFName name = workbook.getNameAt(i);
137.     return workbook.getSheet(name) != null;
142.     int index = workbook.getSheetIndex(name);
144.         workbook.removeSheetAt(index);
162.     if(workbook.getName(name) != null) {
163.         workbook.removeName(name);
165.     Name xssfName = workbook.createName();
171.     workbook.removeName(name);
194.   XSSFSheet xssfSheet = workbook.createSheet(name);
223.     for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
224.         sheets.add(new SheetXSSFImpl(this, workbook.getSheetAt(i)));
230.     XSSFSheet xssfSheet = workbook.getSheet(name);
240.     XSSFSheet xssfSheet = workbook.getSheetAt(index);
252.     XSSFSheet xssfSheet = workbook.getSheet(setCellValue.getSheet().getName());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
35. private XSSFWorkbook hssfWorkbook;       
78.     hssfWorkbook.setSheetName(hssfWorkbook.getSheetIndex(sheet), name);
83.     return hssfWorkbook.isSheetHidden(hssfWorkbook.getSheetIndex(sheet));
87.     hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), b);
92.         hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), 2);
95.         hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), false);
101. return hssfWorkbook.isSheetVeryHidden(hssfWorkbook.getSheetIndex(sheet));
122.     return hssfWorkbook.getSheetName(hssfWorkbook.getSheetIndex(sheet));
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
37. private XSSFWorkbook workbook;
47.     XSSFFont font = workbook.getFontAt((short) 0);
141.         cellStyle = workbook.createCellStyle();
146.         font = workbook.createFont();
336. return workbook.getSheetName(getSheetIndex());
342. return workbook.getSheetIndex(sheet);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
83. private XSSFWorkbook workbook;
390.   final XSSFSheet sheet = workbook.getSheet(XLSX_FILE_SHEET_VERSION);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
38. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
39. XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen");
42. XSSFFont font = xssfWorkbook.createFont();
46. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
51. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
175. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
43. XSSFWorkbook wb = new XSSFWorkbook();
44. XSSFSheet sheet = wb.createSheet();
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
41. XSSFWorkbook wb = new XSSFWorkbook();
44. XSSFSheet dataSheet = wb.createSheet("Data");
52. wb.write(fileOut);
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
37. XSSFWorkbook workbook = new XSSFWorkbook();
38. XSSFSheet sheet = workbook.createSheet();
42. workbook.getSheetAt(0).getColumnHelper().getColumn(0, false);
43. assertEquals("hello world",workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
44. assertEquals(2048,workbook.getSheetAt(0).getColumnWidth(0));
52.     workbook.write(stream);        
56.     assertEquals("hello world",workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
58.     workbook.getSheetAt(0).getColumnHelper().getColumn(0, false);
59. assertEquals(2048,workbook.getSheetAt(0).getColumnWidth(0));        
{% endhighlight %}

***

