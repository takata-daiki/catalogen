# XSSFWorkbook

***

## [Cluster 1](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
37. private XSSFWorkbook workbook;
47.     XSSFFont font = workbook.getFontAt((short) 0);
141.         cellStyle = workbook.createCellStyle();
146.         font = workbook.createFont();
336. return workbook.getSheetName(getSheetIndex());
342. return workbook.getSheetIndex(sheet);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> tests reading a file containing this package . 
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

## [Cluster 3](./3)
15 results
> creates the 
{% highlight java %}
43. XSSFWorkbook wb = new XSSFWorkbook();
44. XSSFSheet sheet = wb.createSheet();
{% endhighlight %}

***

