# XSSFWorkbook @Cluster 2

***

### [WorkbookXSSFImpl.java](https://searchcode.com/codesearch/view/72854562/)
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

### [NucletGenerator.java](https://searchcode.com/codesearch/view/46078634/)
{% highlight java %}
83. private XSSFWorkbook workbook;
390.   final XSSFSheet sheet = workbook.getSheet(XLSX_FILE_SHEET_VERSION);
{% endhighlight %}

***

### [SheetXSSFImplTest.java](https://searchcode.com/codesearch/view/72853788/)
{% highlight java %}
43. XSSFWorkbook wb = new XSSFWorkbook();
44. XSSFSheet sheet = wb.createSheet();
{% endhighlight %}

***

### [ExcelReport.java](https://searchcode.com/codesearch/view/71257075/)
{% highlight java %}
41. XSSFWorkbook wb = new XSSFWorkbook();
44. XSSFSheet dataSheet = wb.createSheet("Data");
52. wb.write(fileOut);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
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

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
{% highlight java %}
74. XSSFWorkbook document = (XSSFWorkbook) extractor.getDocument();
76. for (int i = 0; i < document.getNumberOfSheets(); i++) {
78.     XSSFSheet sheet = (XSSFSheet) document.getSheetAt(i);
79.     xhtml.element("h1", document.getSheetName(i));
{% endhighlight %}

***

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
{% highlight java %}
195. XSSFWorkbook document = (XSSFWorkbook) extractor.getDocument();
197. for (int i = 0; i < document.getNumberOfSheets(); i++) {
198.     XSSFSheet sheet = document.getSheetAt(i);
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
{% highlight java %}
38. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
39. XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen");
42. XSSFFont font = xssfWorkbook.createFont();
46. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
51. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
175. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/)
{% highlight java %}
39. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
40. XSSFSheet sheet = xssfWorkbook.createSheet("Familie");
43. XSSFFont font = xssfWorkbook.createFont();
47. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
52. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
167. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
{% highlight java %}
42. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
43. XSSFSheet sheet = xssfWorkbook
47. XSSFFont font = xssfWorkbook.createFont();
51. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
56. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
229. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
{% highlight java %}
41. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
42. XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen");
45. XSSFFont font = xssfWorkbook.createFont();
49. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
54. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
222. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
{% highlight java %}
40. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
41. XSSFSheet sheet = xssfWorkbook.createSheet("Familien");
44. XSSFFont font = xssfWorkbook.createFont();
48. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
53. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
177. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
{% highlight java %}
38. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
39. XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen");
42. XSSFFont font = xssfWorkbook.createFont();
46. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
51. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
175. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

### [ExcelBetreuerinnen.java](https://searchcode.com/codesearch/view/91974026/)
{% highlight java %}
39. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
40. XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen");
43. XSSFFont font = xssfWorkbook.createFont();
47. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
52. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
209. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

### [ExcelFamilieZahlungenB.java](https://searchcode.com/codesearch/view/91974028/)
{% highlight java %}
39. XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
40. XSSFSheet sheet = xssfWorkbook.createSheet("Familie");
43. XSSFFont font = xssfWorkbook.createFont();
47. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
52. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
167. xssfWorkbook.write(fileOutputStream);
{% endhighlight %}

***

