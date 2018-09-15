# CellRangeAddress @Cluster 1

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
250. public static Boolean hasFormula (HSSFSheet sh, CellRangeAddress cRA){
253.   for(int row=cRA.getFirstRow(); row <= cRA.getLastRow(); row++){
256.       for(int col=cRA.getFirstColumn(); col <= cRA.getLastColumn(); col++){
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
613. public static void fill(HSSFSheet sh, CellRangeAddress srcCRA, CellRangeAddress distCRA, byte lookIn){
616.     for(int row = distCRA.getFirstRow(); row <=distCRA.getLastRow(); row++){
620.       for(int col = distCRA.getFirstColumn(); col <=distCRA.getLastColumn(); col++){
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
653. public static void autoFilter(HSSFSheet sh, CellRangeAddress cellsRef, Object filterValue){
654.   autoFilter(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
655.       new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), filterValue);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
691. public static void replace(HSSFSheet sh, CellRangeAddress cellsRef, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
692.   replace(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
693.       new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), valueSearch, valueReplace, lookIn, lookAt, searchDirection);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
710. public static List<HSSFCell> findAll(HSSFSheet sh, CellRangeAddress cellsRef, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
711.   return findAll(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
712.       new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), valueSearch, lookIn, lookAt, searchDirection);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
751. public static HSSFCell find(HSSFSheet sh, CellRangeAddress cellsRef, Object valueSearch, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection){
752.   return find(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
753.       new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())),
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
966. CellRangeAddress crA  = cellsRefs.getCellRangeAddress(i);
967. for(int row =crA.getFirstRow(); row<=crA.getLastRow(); row++)
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
995. CellRangeAddress crA  = cellsRefs.getCellRangeAddress(i);
996. for(int row =crA.getFirstRow(); row<=crA.getLastRow(); row++){
{% endhighlight %}

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
1039. public static ExcelBuffer copy (HSSFSheet sh, CellRangeAddress cellsRef){
1040.   return  copy (sh, new AreaReference(new CellReference(cellsRef.getFirstRow(),  cellsRef.getFirstColumn()), 
1041.                       new CellReference(cellsRef.getLastRow(),  cellsRef.getLastColumn())));
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
414. public static CellRangeAddress intersectRectangular(CellRangeAddress crA, CellRangeAddress crB){
415.   boolean isIntersect =  !( crB.getFirstColumn() > crA.getLastColumn()
416.               || crB.getLastColumn() < crA.getFirstColumn()
417.               || crB.getFirstRow() > crA.getLastRow()
418.               || crB.getLastRow() < crA.getFirstRow()
422.       new CellRangeAddress(Math.max(crA.getFirstRow(), crB.getFirstRow()), 
423.                  Math.min(crA.getLastRow(), crB.getLastRow()),
424.                  Math.max(crA.getFirstColumn(), crB.getFirstColumn()), 
425.                  Math.min(crA.getLastColumn(), crB.getLastColumn()));
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1084. public static void paste(HSSFSheet sh, CellRangeAddress cellsRef, ExcelBuffer buffer ){
1085.   paste (sh, new AreaReference(new CellReference(cellsRef.getFirstRow(),  cellsRef.getFirstColumn()), 
1086.       new CellReference(cellsRef.getLastRow(),  cellsRef.getLastColumn())),  buffer, xlAll);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1117. public static void paste(HSSFSheet sh, CellRangeAddress cellsRef, ExcelBuffer buffer, byte typePaste){
1118.   paste (sh, new AreaReference(new CellReference(cellsRef.getFirstRow(),  cellsRef.getFirstColumn()), 
1119.       new CellReference(cellsRef.getLastRow(),  cellsRef.getLastColumn())),  buffer, typePaste);
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

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1214. CellRangeAddress cra = list.getCellRangeAddress(jj);
1215. for(int row = cra.getFirstRow(); row <= cra.getLastRow(); row++)
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
414. public static CellRangeAddress intersectRectangular(CellRangeAddress crA, CellRangeAddress crB){
415.   boolean isIntersect =  !( crB.getFirstColumn() > crA.getLastColumn()
416.               || crB.getLastColumn() < crA.getFirstColumn()
417.               || crB.getFirstRow() > crA.getLastRow()
418.               || crB.getLastRow() < crA.getFirstRow()
422.       new CellRangeAddress(Math.max(crA.getFirstRow(), crB.getFirstRow()), 
423.                  Math.min(crA.getLastRow(), crB.getLastRow()),
424.                  Math.max(crA.getFirstColumn(), crB.getFirstColumn()), 
425.                  Math.min(crA.getLastColumn(), crB.getLastColumn()));
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
431. public static CellRangeAddress unionRectangular(CellRangeAddress crA, CellRangeAddress crB){
432.   boolean isUnion = (crB.getFirstColumn() == crA.getFirstColumn() && 
433.       crB.getLastColumn() == crA.getLastColumn() &&
434.      (Math.abs(crB.getLastRow()- crA.getFirstRow()) == 1 || 
435.          Math.abs(crB.getFirstRow()- crA.getLastRow())==1)) ||
436.      (crB.getFirstRow() == crA.getFirstRow() && crB.getLastRow() == crA.getLastRow() &&
437.      (Math.abs(crB.getLastColumn() - crA.getFirstColumn()) == 1 || 
438.          Math.abs(crB.getFirstColumn()-crA.getLastColumn())==1));
441.       new CellRangeAddress(Math.min(crA.getFirstRow(), crB.getFirstRow()), 
442.                  Math.max(crA.getLastRow(), crB.getLastRow()),
443.                  Math.min(crA.getFirstColumn(), crB.getFirstColumn()), 
444.                  Math.max(crA.getLastColumn(), crB.getLastColumn()));
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
431. public static CellRangeAddress unionRectangular(CellRangeAddress crA, CellRangeAddress crB){
432.   boolean isUnion = (crB.getFirstColumn() == crA.getFirstColumn() && 
433.       crB.getLastColumn() == crA.getLastColumn() &&
434.      (Math.abs(crB.getLastRow()- crA.getFirstRow()) == 1 || 
435.          Math.abs(crB.getFirstRow()- crA.getLastRow())==1)) ||
436.      (crB.getFirstRow() == crA.getFirstRow() && crB.getLastRow() == crA.getLastRow() &&
437.      (Math.abs(crB.getLastColumn() - crA.getFirstColumn()) == 1 || 
438.          Math.abs(crB.getFirstColumn()-crA.getLastColumn())==1));
441.       new CellRangeAddress(Math.min(crA.getFirstRow(), crB.getFirstRow()), 
442.                  Math.max(crA.getLastRow(), crB.getLastRow()),
443.                  Math.min(crA.getFirstColumn(), crB.getFirstColumn()), 
444.                  Math.max(crA.getLastColumn(), crB.getLastColumn()));
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
481. public static CellRangeAddressList getSpecialCells(HSSFSheet sh, CellRangeAddress cellRef0, XlCellType xlCellType){
501.       for(int col = cellRef0.getFirstColumn(); col <= cellRef0.getLastColumn(); col++)
504.           for(int row = cellRef0.getFirstRow(); row <= cellRef0.getLastRow(); row++)
526.       for(int col = cellRef0.getFirstColumn(); col <= cellRef0.getLastColumn(); col++){
528.         for(int row = cellRef0.getFirstRow(); row <= cellRef0.getLastRow(); row++){
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
587. public static void fill(HSSFSheet sh, CellRangeAddress cellsRef, Object valueFill, byte lookIn){
588.   fill(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
589.       new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), valueFill, lookIn);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
607. public static boolean equalsRectangularAreas(CellRangeAddress cRA1, CellRangeAddress cRA2){
608.   return (cRA1.getLastRow()-cRA1.getFirstRow() == cRA2.getLastRow()-cRA2.getFirstRow() &&
609.       cRA1.getLastColumn()-cRA1.getFirstColumn() == cRA2.getLastColumn()-cRA2.getFirstColumn());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
607. public static boolean equalsRectangularAreas(CellRangeAddress cRA1, CellRangeAddress cRA2){
608.   return (cRA1.getLastRow()-cRA1.getFirstRow() == cRA2.getLastRow()-cRA2.getFirstRow() &&
609.       cRA1.getLastColumn()-cRA1.getFirstColumn() == cRA2.getLastColumn()-cRA2.getFirstColumn());
{% endhighlight %}

***

### [SheetXSSFImplTest.java](https://searchcode.com/codesearch/view/72853788/)
{% highlight java %}
33. CellRangeAddress rangeAddresses = cellRangeAddresses[0];
34. assertEquals(4,rangeAddresses.getFirstColumn());
35. assertEquals(4,rangeAddresses.getLastColumn());
36. assertEquals(11,rangeAddresses.getFirstRow());
37. assertEquals(11,rangeAddresses.getLastRow());
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
1335. CellRangeAddress workRange = l.getCellRangeAddress(ii);
1336. for(int row = workRange.getFirstRow(); row <= workRange.getLastRow(); row++)
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1368. CellRangeAddress cra2 = ExcelUtils.mergeArea(sh, area2.getAllReferencedCells()[0]);
1370. for (int row = cra2.getFirstRow(); row <=cra2.getLastRow(); row++)
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
127. CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());     
131.   CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow()+deltaRows, mergedRegion.getLastRow()+deltaRows, mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());  
{% endhighlight %}

***

### [ThExtraerNotas.java](https://searchcode.com/codesearch/view/92190361/)
{% highlight java %}
202. CellRangeAddress merged = sheet.getMergedRegion(i);     
203. if (merged.isInRange(rowNum, cellNum)) {     
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
223. for (CellRangeAddress address : validation.getRegions().getCellRangeAddresses()) {
225.     validationList.add(new ValidationImpl(formula1, this, address.getFirstColumn(), address.getLastColumn(), address.getFirstRow(), address.getLastRow()));
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
239. for (CellRangeAddress address : validation.getRegions().getCellRangeAddresses()) {
240.     validationList.add(new ValidationImpl(validation.getConstraint().getFormula1(), this, address.getFirstColumn(), address.getLastColumn(), address.getFirstRow(), address.getLastRow()));
{% endhighlight %}

***

