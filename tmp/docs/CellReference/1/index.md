# CellReference @Cluster 1 (if, setzeroheight, sh)

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> sets all the text from the file @ throws ioexception 
{% highlight java %}
597. for(CellReference cellRef:areaRef.getAllReferencedCells()){
599.     setCellValue(getCell(sh, cellRef.getRow(), cellRef.getCol()), valueFill);
601.     HSSFCell cell = getCell(sh, cellRef.getRow(), cellRef.getCol());
{% endhighlight %}

***

### [ExcelWriterStep.java](https://searchcode.com/codesearch/view/42462258/)
> sets the 
{% highlight java %}
343. CellReference cellRef = new CellReference(reference);
345. String sheetName = cellRef.getSheetName();
357. Row xlsRow = sheet.getRow(cellRef.getRow());
361. Cell styleCell = xlsRow.getCell(cellRef.getCol());
{% endhighlight %}

***

