# XSSFWorkbook

***

## [Cluster 1](./1)
1 results
> code comments is here.
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

## [Cluster 2](./2)
15 results
> code comments is here.
{% highlight java %}
74. XSSFWorkbook document = (XSSFWorkbook) extractor.getDocument();
76. for (int i = 0; i < document.getNumberOfSheets(); i++) {
78.     XSSFSheet sheet = (XSSFSheet) document.getSheetAt(i);
79.     xhtml.element("h1", document.getSheetName(i));
{% endhighlight %}

***

