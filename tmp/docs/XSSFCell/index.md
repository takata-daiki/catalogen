# XSSFCell

***

## [Cluster 1 (headercell1, headercell6, row)](./1)
94 results
> should be called whenever there are changes to input cells in the evaluated workbook . 
{% highlight java %}
100. XSSFCell cell;
112.           int cellType = cell.getCellType();
{% endhighlight %}

***

## [Cluster 2 (cell, cell1, thecell)](./2)
18 results
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
257. XSSFCell xssfCell = xssfRow.getCell(setCellValue.getCol());
263.         xssfCell.setCellValue(new XSSFRichTextString(setCellValue.getNewValue().toString()));
{% endhighlight %}

***

