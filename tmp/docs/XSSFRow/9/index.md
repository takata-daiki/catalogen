# XSSFRow @Cluster 9

***

### [WorkbookXSSFImpl.java](https://searchcode.com/codesearch/view/72854562/)
{% highlight java %}
253. XSSFRow xssfRow = xssfSheet.getRow(setCellValue.getRow());
257. XSSFCell xssfCell = xssfRow.getCell(setCellValue.getCol());
259.     xssfCell = xssfRow.createCell(setCellValue.getCol());
266.         xssfRow.removeCell(xssfCell);
{% endhighlight %}

***

