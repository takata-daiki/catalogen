# XSSFWorkbook @Cluster 1 (font, getsheetindex, workbook)

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
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

