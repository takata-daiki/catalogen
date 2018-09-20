# XSSFWorkbook @Cluster 2

***

### [WorkbookXSSFImplTest.java](https://searchcode.com/codesearch/view/72853773/)
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

