# XWPFTableRow @Cluster 1

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
332. protected void startVisitTableRow( XWPFTableRow row, Object tableContainer, int rowIndex, boolean headerRow )
338.     XWPFTable table = row.getTable();
{% endhighlight %}

***

### [PdfMapper.java](https://searchcode.com/codesearch/view/96673019/)
{% highlight java %}
1060. XWPFTableRow row = cell.getTableRow();
1061. XWPFTable table = row.getTable();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
504. XWPFTableRow row = cell.getTableRow();
505. List<XWPFTableCell> cells = row.getTableCells();
{% endhighlight %}

***

### [WordExport.java](https://searchcode.com/codesearch/view/134954814/)
{% highlight java %}
201. final XWPFTableRow row = rowIter.next();
202. final List<XWPFTableCell> cellList = row.getTableCells();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96672636/)
{% highlight java %}
188. public static int getNumberOfColumns( XWPFTableRow row )
197.     List<XWPFTableCell> tableCellsOffFirstRow = row.getTableCells();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96672636/)
{% highlight java %}
236. private static Collection<Float> computeColWidths( XWPFTableRow row )
239.     List<XWPFTableCell> cells = row.getTableCells();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
687. XWPFTableRow row = cell.getTableRow();
781. int height = row.getHeight();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96673299/)
{% highlight java %}
173. private static Collection<Float> computeColWidths( XWPFTableRow row )
176.     List<XWPFTableCell> cells = row.getTableCells();
{% endhighlight %}

***

### [AbstractTableRowValueProvider.java](https://searchcode.com/codesearch/view/96672903/)
{% highlight java %}
77. protected String[] getStyleID( XWPFTableRow row )
79.     return new String[] { row.getTable().getStyleID() };
{% endhighlight %}

***

### [AbstractTableRowValueProvider.java](https://searchcode.com/codesearch/view/96672903/)
{% highlight java %}
40. public CTTrPr getTrPr( XWPFTableRow row )
42.     return row.getCtRow().getTrPr();
{% endhighlight %}

***

### [TableCellVerticalAlignmentTestCase.java](https://searchcode.com/codesearch/view/96672468/)
{% highlight java %}
75. private void testTableRow( XWPFTableRow row, XWPFStylesDocument stylesDocument )
77.     List<XWPFTableCell> cells = row.getTableCells();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/12208688/)
{% highlight java %}
170. private static Collection<Float> computeColWidths( XWPFTableRow row )
173.     List<XWPFTableCell> cells = row.getTableCells();
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
254. protected void visitTableRow( XWPFTableRow row, T tableContainer )
258.     List<XWPFTableCell> cells = row.getTableCells();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
1015. XWPFTableRow row = null;
1021.     c = row.getCell( cellIndex );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
499. XWPFTableRow row = cell.getTableRow();
549. int height = row.getHeight();
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/96673254/)
{% highlight java %}
222. protected void visitTableRow( XWPFTableRow row, T tableContainer, boolean firstRow, boolean lastRow )
228.     List<XWPFTableCell> cells = row.getTableCells();
{% endhighlight %}

***

### [AbstractTableRowExValueProvider.java](https://searchcode.com/codesearch/view/96672915/)
{% highlight java %}
40. public CTTblPrEx getTblPrEx( XWPFTableRow row )
42.     return row.getCtRow().getTblPrEx();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96672636/)
{% highlight java %}
154. private static int getNbColumnsToIgnore( XWPFTableRow row, boolean before )
156.     CTTrPr trPr = row.getCtRow().getTrPr();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
374. XWPFTableRow row = cell.getTableRow();
375. XWPFTable table = row.getTable();
{% endhighlight %}

***

### [TableInfo.java](https://searchcode.com/codesearch/view/96672683/)
{% highlight java %}
90. private void computeCellInfos( XWPFTableRow row )
94.         nbColumns = XWPFTableUtil.computeColWidths( row.getTable() ).length;
104.     CTRow ctRow = row.getCtRow();
113.             XWPFTableCell cell = row.getTableCell( tc );
126.                 XWPFTableCell cell = new XWPFTableCell( ctTc, row, row.getTable().getBody() );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
618. protected void visitTableRow( XWPFTableRow row, IITextContainer tableContainer, boolean firstRow, boolean lastRow )
627.     List<XWPFTableCell> cells = row.getTableCells();
640.         CTRow ctRow = row.getCtRow();
649.                 XWPFTableCell cell = row.getTableCell( tc );
660.                     XWPFTableCell cell = new XWPFTableCell( ctTc, row, row.getTable().getBody() );
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
862. protected void visitTableRow( XWPFTableRow row, float[] colWidths, T tableContainer, boolean firstRow,
876.     List<XWPFTableCell> cells = row.getTableCells();
890.         CTRow ctRow = row.getCtRow();
899.                 XWPFTableCell cell = row.getTableCell( tc );
918.                     XWPFTableCell cell = new XWPFTableCell( ctTc, row, row.getTable().getBody() );
{% endhighlight %}

***

