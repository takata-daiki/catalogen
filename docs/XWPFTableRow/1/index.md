# XWPFTableRow @Cluster 1 (ctrow, gettable, xwpftablecell)

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
> set a boolean value for the boldness to use . 
{% highlight java %}
332. protected void startVisitTableRow( XWPFTableRow row, Object tableContainer, int rowIndex, boolean headerRow )
338.     XWPFTable table = row.getTable();
{% endhighlight %}

***

### [PdfMapper.java](https://searchcode.com/codesearch/view/96673019/)
> set a boolean value for the boldness to use . 
{% highlight java %}
1060. XWPFTableRow row = cell.getTableRow();
1061. XWPFTable table = row.getTable();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
> set a boolean value for the boldness to use . 
{% highlight java %}
504. XWPFTableRow row = cell.getTableRow();
505. List<XWPFTableCell> cells = row.getTableCells();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
> sets the and re - fit column to the an value of the in the part . @ param sheetindex the number of columns in the table @ 
{% highlight java %}
687. XWPFTableRow row = cell.getTableRow();
781. int height = row.getHeight();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
> sets the and re - fit column to the an value of the in the part . @ param sheetindex the number of columns in the table @ 
{% highlight java %}
499. XWPFTableRow row = cell.getTableRow();
549. int height = row.getHeight();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96672636/)
> sets the maximum number of columns @ param and maximum that maximum value is set to 
{% highlight java %}
154. private static int getNbColumnsToIgnore( XWPFTableRow row, boolean before )
156.     CTTrPr trPr = row.getCtRow().getTrPr();
{% endhighlight %}

***

### [TableInfo.java](https://searchcode.com/codesearch/view/96672683/)
> sets the 
{% highlight java %}
90. private void computeCellInfos( XWPFTableRow row )
94.         nbColumns = XWPFTableUtil.computeColWidths( row.getTable() ).length;
104.     CTRow ctRow = row.getCtRow();
113.             XWPFTableCell cell = row.getTableCell( tc );
126.                 XWPFTableCell cell = new XWPFTableCell( ctTc, row, row.getTable().getBody() );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
> sets desktop region ' s are the workbook 1 2 0 based on the name < p > the name matching is case insensitive . @ param name the name to specified as in the original function . @ param name the name to check for value in the workbook 
{% highlight java %}
618. protected void visitTableRow( XWPFTableRow row, IITextContainer tableContainer, boolean firstRow, boolean lastRow )
627.     List<XWPFTableCell> cells = row.getTableCells();
640.         CTRow ctRow = row.getCtRow();
649.                 XWPFTableCell cell = row.getTableCell( tc );
660.                     XWPFTableCell cell = new XWPFTableCell( ctTc, row, row.getTable().getBody() );
{% endhighlight %}

***

