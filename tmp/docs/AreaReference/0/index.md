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

### [HyperlinksTest.java](https://searchcode.com/codesearch/view/122565050/)
{% highlight java %}
46. AreaReference ref = new AreaReference( namedRange.getRefersToFormula() );
49.   assertTrue( ref.isSingleCell() );
50.   assertEquals( row1, ref.getFirstCell().getRow() );
51.   assertEquals( col1, ref.getFirstCell().getCol() );
54.   assertEquals( row1, Math.min( ref.getFirstCell().getRow(), ref.getLastCell().getRow() ) );
55.   assertEquals( col1, Math.min( ref.getFirstCell().getCol(), ref.getLastCell().getCol() ) );
56.   assertEquals( row2, Math.max( ref.getFirstCell().getRow(), ref.getLastCell().getRow() ) );
57.   assertEquals( col2, Math.max( ref.getFirstCell().getCol(), ref.getLastCell().getCol() ) );
{% endhighlight %}

***

### [FormulaTest.java](https://searchcode.com/codesearch/view/121321561/)
{% highlight java %}
78. AreaReference aref = new AreaReference(aNamedCell.getRefersToFormula());
79. CellReference[] crefs = aref.getAllReferencedCells();
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
244. public static Boolean hasFormula (HSSFSheet sh, AreaReference areaRef){
245.   return hasFormula(sh, new CellRangeAddress(areaRef.getFirstCell().getRow(), areaRef.getLastCell().getRow(),
246.       areaRef.getFirstCell().getCol(), areaRef.getLastCell().getCol()));
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1315. AreaReference ref = range(wb, nameRange, mapWbNames, sheetNames);
1316. if (ref.isSingleCell()){
1317.   String shName = ref.getFirstCell().getSheetName();
1318.   return  getCell(wb.getSheet(shName), ref.getFirstCell().getRow(), ref.getFirstCell().getCol());
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1366. AreaReference area2 = new AreaReference(cell.getCellFormula());
1368. CellRangeAddress cra2 = ExcelUtils.mergeArea(sh, area2.getAllReferencedCells()[0]);
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
1633. AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
1634. name_WorkSheet  =  areaDataBeg.getFirstCell().getSheetName();
1636. rowWork = areaDataBeg.getFirstCell().getRow();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1354. for(AreaReference area:hideFewRow){
1356.       area.getFirstCell().getRow(), area.getLastCell().getRow(),
1357.       area.getFirstCell().getCol(), area.getLastCell().getCol());
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1325. for(AreaReference area:hideOneRow){
1327.       area.getFirstCell().getRow(), area.getLastCell().getRow(),
1328.       area.getFirstCell().getCol(), area.getLastCell().getCol()), crB);
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

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
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

### [HyperlinksTest.java](https://searchcode.com/codesearch/view/64531339/)
{% highlight java %}
53. AreaReference ref = new AreaReference( namedRange.getRefersToFormula() );
56.   assertTrue( ref.isSingleCell() );
57.   assertEquals( row1, ref.getFirstCell().getRow() );
58.   assertEquals( col1, ref.getFirstCell().getCol() );
61.   assertEquals( row1, Math.min( ref.getFirstCell().getRow(), ref.getLastCell().getRow() ) );
62.   assertEquals( col1, Math.min( ref.getFirstCell().getCol(), ref.getLastCell().getCol() ) );
63.   assertEquals( row2, Math.max( ref.getFirstCell().getRow(), ref.getLastCell().getRow() ) );
64.   assertEquals( col2, Math.max( ref.getFirstCell().getCol(), ref.getLastCell().getCol() ) );
{% endhighlight %}

***

### [HyperlinksTest.java](https://searchcode.com/codesearch/view/126772645/)
{% highlight java %}
31. AreaReference ref = new AreaReference( namedRange.getRefersToFormula() );
34.   assertTrue( ref.isSingleCell() );
35.   assertEquals( row1, ref.getFirstCell().getRow() );
36.   assertEquals( col1, ref.getFirstCell().getCol() );
39.   assertEquals( row1, Math.min( ref.getFirstCell().getRow(), ref.getLastCell().getRow() ) );
40.   assertEquals( col1, Math.min( ref.getFirstCell().getCol(), ref.getLastCell().getCol() ) );
41.   assertEquals( row2, Math.max( ref.getFirstCell().getRow(), ref.getLastCell().getRow() ) );
42.   assertEquals( col2, Math.max( ref.getFirstCell().getCol(), ref.getLastCell().getCol() ) );
{% endhighlight %}

***

### [XMLGeneration.java](https://searchcode.com/codesearch/view/110498474/)
{% highlight java %}
291. AreaReference areaReference = new AreaReference(formula);
292. CellReference firstCellReference = areaReference
294. CellReference lastCellReference = areaReference
{% endhighlight %}

***

### [NamedRangeXSSFImpl.java](https://searchcode.com/codesearch/view/72854588/)
{% highlight java %}
34. AreaReference areaReference = new AreaReference(formula);
35. CellReference firstCellReference = areaReference.getFirstCell();
36. CellReference lastCellReference = areaReference.getLastCell();
{% endhighlight %}

***

### [NamedRangeHSSFImpl.java](https://searchcode.com/codesearch/view/72854613/)
{% highlight java %}
40. AreaReference areaReference = new AreaReference(formula);
41. CellReference firstCellReference = areaReference.getFirstCell();
42. CellReference lastCellReference = areaReference.getLastCell();
{% endhighlight %}

***

### [JUniPrintReportsEngine.java](https://searchcode.com/codesearch/view/60336976/)
{% highlight java %}
96. AreaReference paramCellAreaRef = new AreaReference(paramCellName.getRefersToFormula());
97. CellReference paramCellAreaRefFirstCell = paramCellAreaRef.getFirstCell();
{% endhighlight %}

***

### [JUniPrintReportsEngine.java](https://searchcode.com/codesearch/view/60336976/)
{% highlight java %}
110. AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
111. int dataBegRow = areaDataBeg.getFirstCell().getRow();
112. int dataBegCol = areaDataBeg.getFirstCell().getCol();
{% endhighlight %}

***

### [ReportSheetImpl.java](https://searchcode.com/codesearch/view/128473368/)
{% highlight java %}
104. AreaReference aref = new AreaReference(nm.getRefersToFormula());
108. band.c1 = aref.getFirstCell().getCol();
109. band.c2 = aref.getLastCell().getCol();
110. band.r1 = aref.getFirstCell().getRow();
111. band.r2 = aref.getLastCell().getRow();
112. band.wholeCols = aref.isWholeColumnReference();
{% endhighlight %}

***

