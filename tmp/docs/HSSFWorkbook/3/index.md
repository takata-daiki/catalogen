# HSSFWorkbook @Cluster 3

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
116. HSSFWorkbook wb = new HSSFWorkbook();
121. Font headerFont = wb.createFont();
125. Font userFont = wb.createFont();
129. Font storyFont = wb.createFont();
140. HSSFSheet overviewSheet = wb.createSheet(sheetName);
234. wb.setRepeatingRowsAndColumns(0, 0, 0, 0, 0);
235. wb.setPrintArea(0, 0, 8, 0, overviewSheetRow);
247.     wb.write(baos);
{% endhighlight %}

***

### [MetaDataFacadeBean.java](https://searchcode.com/codesearch/view/39694405/)
{% highlight java %}
457. HSSFWorkbook wb = new HSSFWorkbook();
461. Font storyFont = wb.createFont();
466. CellStyle style = wb.createCellStyle();
477. CreationHelper createHelper = wb.getCreationHelper();
478. CellStyle dateStyle = wb.createCellStyle();
490. HSSFSheet overviewSheet = wb.createSheet(sheetName);
536. wb.setRepeatingRowsAndColumns(0, 0, 0, 0, 0);
542.     wb.write(baos);
{% endhighlight %}

***

### [SearchEngineBean.java](https://searchcode.com/codesearch/view/39694394/)
{% highlight java %}
483. HSSFWorkbook wb = new HSSFWorkbook();
488. Font storyFont = wb.createFont();
493. CellStyle style = wb.createCellStyle();
504. CreationHelper createHelper = wb.getCreationHelper();
505. CellStyle dateStyle = wb.createCellStyle();
517. HSSFSheet overviewSheet = wb.createSheet(sheetName);
602. wb.setRepeatingRowsAndColumns(0, 0, 0, 0, 0);
608.     wb.write(baos);
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/48925127/)
{% highlight java %}
44. HSSFWorkbook wb = new HSSFWorkbook(input);
56. sNum = wb.getNumberOfSheets();
59.   if ((sheet = wb.getSheetAt(i)) == null) {
{% endhighlight %}

***

### [UpLoadFileWindow.java](https://searchcode.com/codesearch/view/42988393/)
{% highlight java %}
180. HSSFWorkbook wb = new HSSFWorkbook(bin);
181. HSSFSheet st = wb.getSheetAt(0);
{% endhighlight %}

***

### [WorkbookHSSFImpl.java](https://searchcode.com/codesearch/view/72854626/)
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

