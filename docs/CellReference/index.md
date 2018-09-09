# CellReference

***

### [Cluster 1](./1)
{% highlight java %}
62. CellReference cr = new CellReference(zeroBasedRow, zeroBasedCol);
63. return cr.formatAsString();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
292. CellReference firstCellReference = areaReference
301.     String.valueOf(firstCellReference.getCol() + 1));
303.     String.valueOf(firstCellReference.getRow() + 1));
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
343. CellReference cellRef = new CellReference(reference);
345. String sheetName = cellRef.getSheetName();
357. Row xlsRow = sheet.getRow(cellRef.getRow());
361. Cell styleCell = xlsRow.getCell(cellRef.getCol());
{% endhighlight %}

***

