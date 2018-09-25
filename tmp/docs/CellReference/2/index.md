# CellReference @Cluster 2 (firstcellreference, getrow, true)

***

### [NamedRangeXSSFImpl.java](https://searchcode.com/codesearch/view/72854588/)
> creates an empty workbook object with the specified sheet . 
{% highlight java %}
35. CellReference firstCellReference = areaReference.getFirstCell();
37. return new Range(workbook.getSheet(sheetName), firstCellReference.getCol(), firstCellReference.getRow(), lastCellReference.getCol(), lastCellReference.getRow());
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/64530955/)
> sets the 
{% highlight java %}
156. CellReference crFirst = new CellReference( state.currentSheet.getSheetName(), row1, col1, true, true );
158. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

### [HyperlinksTest.java](https://searchcode.com/codesearch/view/126772645/)
> get the currently referenced paragraph / / value ( or if the file is specified ) in this workbook @ return index of the 
{% highlight java %}
47. CellReference cr = new CellReference(zeroBasedRow, zeroBasedCol);
48. return cr.formatAsString();
{% endhighlight %}

***

### [NamedRangeHSSFImpl.java](https://searchcode.com/codesearch/view/72854613/)
> creates an empty workbook object with the specified sheet . 
{% highlight java %}
41. CellReference firstCellReference = areaReference.getFirstCell();
43. return new Range(workbook.getSheet(sheetName), firstCellReference.getCol(), firstCellReference.getRow(), lastCellReference.getCol(), lastCellReference.getRow());
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/126772718/)
> sets the 
{% highlight java %}
138. CellReference crFirst = new CellReference( state.currentSheet.getSheetName(), row1, col1, true, true );
140. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

### [AbstractHandler.java](https://searchcode.com/codesearch/view/122565168/)
> sets the 
{% highlight java %}
151. CellReference crFirst = new CellReference( state.currentSheet.getSheetName(), row1, col1, true, true );
153. String formula = crFirst.formatAsString() + ":" + crLast.formatAsString();
{% endhighlight %}

***

### [HyperlinksTest.java](https://searchcode.com/codesearch/view/64531339/)
> get the currently referenced paragraph / / value ( or if the file is specified ) in this workbook @ return index of the 
{% highlight java %}
69. CellReference cr = new CellReference(zeroBasedRow, zeroBasedCol);
70. return cr.formatAsString();
{% endhighlight %}

***

### [XMLGeneration.java](https://searchcode.com/codesearch/view/110498474/)
> sets the 
{% highlight java %}
292. CellReference firstCellReference = areaReference
301.     String.valueOf(firstCellReference.getCol() + 1));
303.     String.valueOf(firstCellReference.getRow() + 1));
{% endhighlight %}

***

### [ExcelWriterStep.java](https://searchcode.com/codesearch/view/42462258/)
> tests that the create and sets the font and row height are not only in ( name = " 1 " ) 
{% highlight java %}
769. CellReference cellRef = new CellReference(data.realStartingCell);
770. data.startingRow = cellRef.getRow();
771. data.startingCol = cellRef.getCol();
{% endhighlight %}

***

