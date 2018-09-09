# AreaReference

***

### [Cluster 1](./1)
{% highlight java %}
160. private Object[][] getAreaValueArray(AreaReference ar) {
161.   int cols = Math.abs(ar.getFirstCell().getCol() - ar.getLastCell().getCol()) + 1;
162.   int rows = Math.abs(ar.getFirstCell().getRow() - ar.getLastCell().getRow()) + 1;
163.   CellReference[] crs = ar.getAllReferencedCells();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
291. AreaReference areaReference = new AreaReference(formula);
292. CellReference firstCellReference = areaReference
294. CellReference lastCellReference = areaReference
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
1366. AreaReference area2 = new AreaReference(cell.getCellFormula());
1368. CellRangeAddress cra2 = ExcelUtils.mergeArea(sh, area2.getAllReferencedCells()[0]);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
1315. AreaReference ref = range(wb, nameRange, mapWbNames, sheetNames);
1316. if (ref.isSingleCell()){
1317.   String shName = ref.getFirstCell().getSheetName();
1318.   return  getCell(wb.getSheet(shName), ref.getFirstCell().getRow(), ref.getFirstCell().getCol());
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
348. AreaReference ref = ExcelUtils.getReferanceNameRange(nameCUsing);
349. String shName = ref.getFirstCell().getSheetName();
350. int c = ref.getFirstCell().getCol();
351. int r = ref.getFirstCell().getRow();
355. shName = ref.getFirstCell().getSheetName();
356. c = ref.getFirstCell().getCol();
357. r = ref.getFirstCell().getRow();
368. HSSFSheet ash = wb.getSheet(ref.getFirstCell().getSheetName());
369. int begRow = ref.getFirstCell().getRow();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
595. public static void fill(HSSFSheet sh, AreaReference areaRef, Object valueFill, byte lookIn){
597.   for(CellReference cellRef:areaRef.getAllReferencedCells()){
{% endhighlight %}

***

