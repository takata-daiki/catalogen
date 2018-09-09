# CellRangeAddress @Cluster 1

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1025. CellRangeAddress rangeRef = sh.getMergedRegion(i);
1026. if (cellRef.getCol() >= rangeRef.getFirstColumn()  && cellRef.getCol() <= rangeRef.getLastColumn() &&
1027.   cellRef.getRow() >= rangeRef.getFirstRow()  && cellRef.getRow() <= rangeRef.getLastRow()){
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1184. CellRangeAddress cra = buffer.getSrcSheet().getMergedRegion(indexMergeRegion);
1185. if (cra.getFirstRow() >= copyRow1 && cra.getLastRow() <= copyRow2 
1186.     && cra.getFirstColumn() >= copyCol1 && cra.getLastColumn() <= copyCol2){
1187.   int offsetRow = cra.getFirstRow()-copyRow1;
1188.   int offsetCol = cra.getFirstColumn()-copyCol1;
1189.   int countRowMergeRegion = cra.getLastRow()-cra.getFirstRow();
1190.   int countColMergeRegion = cra.getLastColumn()-cra.getFirstColumn();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
825. CellRangeAddress cellReff = workR.getCellRangeAddress(0);
826. if(cellReff.getLastRow()-cellReff.getFirstRow()+1 != kFRowE){
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1991. CellRangeAddress workMergeRef = ExcelUtils.mergeArea(activeSheet, cellAHRef.getFirstCell());
1993. for(int col=workMergeRef.getFirstColumn(); col <=workMergeRef.getLastColumn(); col++){
2019. if (workMergeRef.getLastRow()-workMergeRef.getFirstRow() > 0 || 
2020.     workMergeRef.getLastColumn()-workMergeRef.getFirstColumn() > 0){
2026. if (workMergeRef.getLastRow()-workMergeRef.getFirstRow() > 0 ){
{% endhighlight %}

***

### [ExcelParser.java](https://searchcode.com/codesearch/view/93105691/)
{% highlight java %}
240. CellRangeAddress r = mergedRanges[ i ];
241. if ( r.isInRange( cell.getRowIndex(), cell.getColumnIndex() ) ) {
{% endhighlight %}

***

### [ExcelTools.java](https://searchcode.com/codesearch/view/121321570/)
{% highlight java %}
147. CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
148. if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
151.                       newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
152.                       cellRangeAddress.getFirstColumn(),
153.                       cellRangeAddress.getLastColumn());
{% endhighlight %}

***

### [ExcelTools.java](https://searchcode.com/codesearch/view/121321570/)
{% highlight java %}
222. CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
223. if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
226.                       newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
227.                       cellRangeAddress.getFirstColumn(),
228.                       cellRangeAddress.getLastColumn());
{% endhighlight %}

***

### [ThExtraerNotas.java](https://searchcode.com/codesearch/view/92190361/)
{% highlight java %}
202. CellRangeAddress merged = sheet.getMergedRegion(i);     
203. if (merged.isInRange(rowNum, cellNum)) {     
{% endhighlight %}

***

