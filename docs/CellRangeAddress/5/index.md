# CellRangeAddress @Cluster 5

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
587. public static void fill(HSSFSheet sh, CellRangeAddress cellsRef, Object valueFill, byte lookIn){
588.   fill(sh, new  AreaReference(new CellReference(cellsRef.getFirstRow(), cellsRef.getFirstColumn()),
589.       new CellReference(cellsRef.getLastRow(), cellsRef.getLastColumn())), valueFill, lookIn);
{% endhighlight %}

***

### [ThExtraerNotas.java](https://searchcode.com/codesearch/view/92190361/)
{% highlight java %}
127. CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());     
131.   CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow()+deltaRows, mergedRegion.getLastRow()+deltaRows, mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());  
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

