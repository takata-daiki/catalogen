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

