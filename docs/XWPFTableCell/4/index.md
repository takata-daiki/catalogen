# XWPFTableCell @Cluster 4

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
273. protected void visitTableCellBody( XWPFTableCell cell, T tableCellContainer )
276.     List<IBodyElement> bodyElements = cell.getBodyElements();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/12208688/)
{% highlight java %}
216. public static TableWidth getTableWidth( XWPFTableCell cell )
220.     CTTcPr tblPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

### [XWPFStylesDocument.java](https://searchcode.com/codesearch/view/96672666/)
{% highlight java %}
862. public TableCellBorder getTableCellBorderWithConflicts( XWPFTableCell cell, BorderSide borderSide )
883.         XWPFTable table = cell.getTableRow().getTable();
{% endhighlight %}

***

### [XWPFStylesDocument.java](https://searchcode.com/codesearch/view/96672666/)
{% highlight java %}
1111. public TableCellInfo getTableCellInfo( XWPFTableCell cell )
1113.     XWPFTable table = cell.getTableRow().getTable();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
1047. protected void visitTableCellBody( XWPFTableCell cell, List<XWPFTableCell> vMergeCells, T tableCellContainer )
1061.         List<IBodyElement> bodyElements = cell.getBodyElements();
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/96673254/)
{% highlight java %}
247. protected void visitTableCellBody( XWPFTableCell cell, T tableCellContainer )
250.     List<IBodyElement> bodyElements = cell.getBodyElements();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96672636/)
{% highlight java %}
282. public static TableWidth getTableWidth( XWPFTableCell cell )
286.     CTTcPr tblPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96673299/)
{% highlight java %}
219. public static TableWidth getTableWidth( XWPFTableCell cell )
223.     CTTcPr tblPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

### [TableInfo.java](https://searchcode.com/codesearch/view/96672683/)
{% highlight java %}
77. public TableCellInfo getCellInfo( XWPFTableCell cell )
85.     computeCellInfos( cell.getTableRow() );
{% endhighlight %}

***

