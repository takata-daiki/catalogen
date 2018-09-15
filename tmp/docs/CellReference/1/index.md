# CellReference @Cluster 1

***

### [ExcelWriterStep.java](https://searchcode.com/codesearch/view/42462258/)
{% highlight java %}
769. CellReference cellRef = new CellReference(data.realStartingCell);
770. data.startingRow = cellRef.getRow();
771. data.startingCol = cellRef.getCol();
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/122565168/)
{% highlight java %}
151. CellReference crFirst = new CellReference( state.currentSheet.getSheetName(), row1, col1, true, true );
153. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/122565168/)
{% highlight java %}
152. CellReference crLast = new CellReference( row2, col2, true, true );
153. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

### [XMLGeneration.java](https://searchcode.com/codesearch/view/110498474/)
{% highlight java %}
292. CellReference firstCellReference = areaReference
301.     String.valueOf(firstCellReference.getCol() + 1));
303.     String.valueOf(firstCellReference.getRow() + 1));
{% endhighlight %}

***

### [XMLGeneration.java](https://searchcode.com/codesearch/view/110498474/)
{% highlight java %}
294. CellReference lastCellReference = areaReference
305.     String.valueOf(lastCellReference.getCol() + 1));
307.     String.valueOf(lastCellReference.getRow() + 1));
{% endhighlight %}

***

### [HyperlinksTest.java](https://searchcode.com/codesearch/view/122565050/)
{% highlight java %}
62. CellReference cr = new CellReference(zeroBasedRow, zeroBasedCol);
63. return cr.formatAsString();
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1023. public static CellRangeAddress  mergeArea(HSSFSheet sh, CellReference cellRef){
1026.     if (cellRef.getCol() >= rangeRef.getFirstColumn()  && cellRef.getCol() <= rangeRef.getLastColumn() &&
1027.       cellRef.getRow() >= rangeRef.getFirstRow()  && cellRef.getRow() <= rangeRef.getLastRow()){
1031.   return new CellRangeAddress(cellRef.getRow(), cellRef.getCol(), cellRef.getRow(), cellRef.getCol());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1067. for(CellReference celRef:celRefs){
1068.   String key =  (celRef.getRow()-offsetRow)+"_"+(celRef.getCol()-offsetCol);
1069.   mapCell.put(key, new ExcelCell(getCell(sh, celRef.getRow(), celRef.getCol())));
{% endhighlight %}

***

### [NamedRangeXSSFImpl.java](https://searchcode.com/codesearch/view/72854588/)
{% highlight java %}
35. CellReference firstCellReference = areaReference.getFirstCell();
37. return new Range(workbook.getSheet(sheetName), firstCellReference.getCol(), firstCellReference.getRow(), lastCellReference.getCol(), lastCellReference.getRow());
{% endhighlight %}

***

### [NamedRangeXSSFImpl.java](https://searchcode.com/codesearch/view/72854588/)
{% highlight java %}
36. CellReference lastCellReference = areaReference.getLastCell();
37. return new Range(workbook.getSheet(sheetName), firstCellReference.getCol(), firstCellReference.getRow(), lastCellReference.getCol(), lastCellReference.getRow());
{% endhighlight %}

***

### [NamedRangeHSSFImpl.java](https://searchcode.com/codesearch/view/72854613/)
{% highlight java %}
41. CellReference firstCellReference = areaReference.getFirstCell();
43. return new Range(workbook.getSheet(sheetName), firstCellReference.getCol(), firstCellReference.getRow(), lastCellReference.getCol(), lastCellReference.getRow());
{% endhighlight %}

***

### [NamedRangeHSSFImpl.java](https://searchcode.com/codesearch/view/72854613/)
{% highlight java %}
42. CellReference lastCellReference = areaReference.getLastCell();
43. return new Range(workbook.getSheet(sheetName), firstCellReference.getCol(), firstCellReference.getRow(), lastCellReference.getCol(), lastCellReference.getRow());
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/64530955/)
{% highlight java %}
156. CellReference crFirst = new CellReference( state.currentSheet.getSheetName(), row1, col1, true, true );
158. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/64530955/)
{% highlight java %}
157. CellReference crLast = new CellReference( row2, col2, true, true );
158. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

### [HyperlinksTest.java](https://searchcode.com/codesearch/view/64531339/)
{% highlight java %}
69. CellReference cr = new CellReference(zeroBasedRow, zeroBasedCol);
70. return cr.formatAsString();
{% endhighlight %}

***

### [HyperlinksTest.java](https://searchcode.com/codesearch/view/126772645/)
{% highlight java %}
47. CellReference cr = new CellReference(zeroBasedRow, zeroBasedCol);
48. return cr.formatAsString();
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/126772718/)
{% highlight java %}
138. CellReference crFirst = new CellReference( state.currentSheet.getSheetName(), row1, col1, true, true );
140. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/126772718/)
{% highlight java %}
139. CellReference crLast = new CellReference( row2, col2, true, true );
140. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

