# HSSFRow @Cluster 8

***

### [WorkbookHSSFImpl.java](https://searchcode.com/codesearch/view/72854626/)
{% highlight java %}
236. HSSFRow hssfRow = hssfSheet.getRow(setCellValue.getRow());
240. HSSFCell hssfCell = hssfRow.getCell(setCellValue.getCol());
242.     hssfCell = hssfRow.createCell(setCellValue.getCol());
249.         hssfRow.removeCell(hssfCell);
{% endhighlight %}

***

