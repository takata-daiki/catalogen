# CellReference @Cluster 3

***

### [ExcelWriterStep.java](https://searchcode.com/codesearch/view/42462258/)
{% highlight java %}
343. CellReference cellRef = new CellReference(reference);
345. String sheetName = cellRef.getSheetName();
357. Row xlsRow = sheet.getRow(cellRef.getRow());
361. Cell styleCell = xlsRow.getCell(cellRef.getCol());
{% endhighlight %}

***

### [ExcelReader.java](https://searchcode.com/codesearch/view/46076963/)
{% highlight java %}
131. private Object getSingleCellValue(CellReference cr) {
135.   String sheetName = cr.getSheetName();
145.   Row row = sheet.getRow(cr.getRow());
147.     Cell cell = row.getCell(cr.getCol());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
597. for(CellReference cellRef:areaRef.getAllReferencedCells()){
599.     setCellValue(getCell(sh, cellRef.getRow(), cellRef.getCol()), valueFill);
601.     HSSFCell cell = getCell(sh, cellRef.getRow(), cellRef.getCol());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
660. for(CellReference cellRef:areaRef.getAllReferencedCells()){
662.     HSSFCell cell = getCell(sh, cellRef.getRow(), cellRef.getCol());
665.       getRow(sh, cellRef.getRow()).setZeroHeight(true);
667.   else getRow(sh, cellRef.getRow()).setZeroHeight(false);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
557. for(CellReference celRef:areaReferance.getAllReferencedCells()){
558.   ExcelUtils.setCellValue(ExcelUtils.getCell(sheet, celRef.getRow(), celRef.getCol()), 0);
{% endhighlight %}

***

### [JUniPrintReportsEngine.java](https://searchcode.com/codesearch/view/60336976/)
{% highlight java %}
97. CellReference paramCellAreaRefFirstCell = paramCellAreaRef.getFirstCell();
98. HSSFCell paramCell = getCell(paramCellSheet, paramCellAreaRefFirstCell.getRow(), paramCellAreaRefFirstCell.getCol());
{% endhighlight %}

***

