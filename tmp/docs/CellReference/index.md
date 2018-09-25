# CellReference

***

## [Cluster 1 (if, setzeroheight, sh)](./1)
2 results
> sets all the text from the file @ throws ioexception 
{% highlight java %}
597. for(CellReference cellRef:areaRef.getAllReferencedCells()){
599.     setCellValue(getCell(sh, cellRef.getRow(), cellRef.getCol()), valueFill);
601.     HSSFCell cell = getCell(sh, cellRef.getRow(), cellRef.getCol());
{% endhighlight %}

***

## [Cluster 2 (firstcellreference, getrow, true)](./2)
9 results
> creates an empty workbook object with the specified sheet . 
{% highlight java %}
35. CellReference firstCellReference = areaReference.getFirstCell();
37. return new Range(workbook.getSheet(sheetName), firstCellReference.getCol(), firstCellReference.getRow(), lastCellReference.getCol(), lastCellReference.getRow());
{% endhighlight %}

***

