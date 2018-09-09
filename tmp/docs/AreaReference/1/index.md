# AreaReference @Cluster 1

***

### [ExcelReader.java](https://searchcode.com/codesearch/view/46076963/)
{% highlight java %}
160. private Object[][] getAreaValueArray(AreaReference ar) {
161.   int cols = Math.abs(ar.getFirstCell().getCol() - ar.getLastCell().getCol()) + 1;
162.   int rows = Math.abs(ar.getFirstCell().getRow() - ar.getLastCell().getRow()) + 1;
163.   CellReference[] crs = ar.getAllReferencedCells();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
556. AreaReference areaReferance = new AreaReference("A"+(wRow+1)+":A"+(ExcelUtils.getReferanceNameRange(nameDataRangeE).getFirstCell().getRow()));
557. for(CellReference celRef:areaReferance.getAllReferencedCells()){
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
964. AreaReference areaRef = new AreaReference(keyPageSeries[k] +""+(wRow+1) + ":" + keyPageSeries[k]+""+(rowNameDataRangeE+1));
965. if(!areaRef.isSingleCell()){
966.   for(int col = areaRef.getFirstCell().getCol(); col <= areaRef.getLastCell().getCol(); col++){
968.     for(int row = areaRef.getFirstCell().getRow(); row <= areaRef.getLastCell().getRow(); row++){
969.       if (row == areaRef.getFirstCell().getRow()){
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1989. AreaReference cellAHRef = new AreaReference(cellAH);
1990. if(cellAHRef.isSingleCell() && ExcelUtils.getCell(activeSheet, cellAHRef.getFirstCell().getRow(), cellAHRef.getFirstCell().getCol()).getCellStyle().getWrapText()){
1991.   CellRangeAddress workMergeRef = ExcelUtils.mergeArea(activeSheet, cellAHRef.getFirstCell());
1998.   ExcelUtils.paste(activeSheet, cellAHRef.getFirstCell().getRow(), lastCol + 1, ExcelUtils.copy(activeSheet, workMergeRef), ExcelUtils.xlFormats);
2000.   CellRangeAddress workMergeRef1 = ExcelUtils.mergeArea(activeSheet, new CellReference(cellAHRef.getFirstCell().getRow(), lastCol + 1));
2010.   HSSFCell distCell = ExcelUtils.getCell(activeSheet, cellAHRef.getFirstCell().getRow(), lastCol);
2021.     distCell.setCellFormula(cellAHRef.getFirstCell().formatAsString());
{% endhighlight %}

***

