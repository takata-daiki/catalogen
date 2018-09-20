# XWPFTableCell @Cluster 5

***

### [PdfMapper.java](https://searchcode.com/codesearch/view/96673019/)
{% highlight java %}
1055. protected IITextContainer startVisitTableCell( final XWPFTableCell cell, IITextContainer pdfTableContainer,
1060.     XWPFTableRow row = cell.getTableRow();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
494. XWPFTableCell cell = (XWPFTableCell) body;
504. XWPFTableRow row = cell.getTableRow();
{% endhighlight %}

***

### [XWPFStylesDocument.java](https://searchcode.com/codesearch/view/96672666/)
{% highlight java %}
862. public TableCellBorder getTableCellBorderWithConflicts( XWPFTableCell cell, BorderSide borderSide )
883.         XWPFTable table = cell.getTableRow().getTable();
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

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/12208688/)
{% highlight java %}
216. public static TableWidth getTableWidth( XWPFTableCell cell )
220.     CTTcPr tblPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
1047. protected void visitTableCellBody( XWPFTableCell cell, List<XWPFTableCell> vMergeCells, T tableCellContainer )
1061.         List<IBodyElement> bodyElements = cell.getBodyElements();
{% endhighlight %}

***

### [TableCellVerticalAlignmentTestCase.java](https://searchcode.com/codesearch/view/96672468/)
{% highlight java %}
84. private void testTableCell( XWPFTableCell cell, XWPFStylesDocument stylesDocument )
86.     XWPFParagraph paragraph = cell.getParagraphs().get( 0 );
{% endhighlight %}

***

### [TableInfo.java](https://searchcode.com/codesearch/view/96672683/)
{% highlight java %}
143. private int getCellIndex( int cellIndex, XWPFTableCell cell )
145.     BigInteger gridSpan = stylesDocument.getTableCellGridSpan( cell.getCTTc().getTcPr() );
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
1001. private List<XWPFTableCell> getVMergedCells( XWPFTableCell cell, int rowIndex, int cellIndex )
1017.             XWPFTable table = cell.getTableRow().getTable();
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
273. protected void visitTableCellBody( XWPFTableCell cell, T tableCellContainer )
276.     List<IBodyElement> bodyElements = cell.getBodyElements();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
963. private int getCellIndex( int cellIndex, XWPFTableCell cell )
965.     BigInteger gridSpan = stylesDocument.getTableCellGridSpan( cell.getCTTc().getTcPr() );
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/96673254/)
{% highlight java %}
247. protected void visitTableCellBody( XWPFTableCell cell, T tableCellContainer )
250.     List<IBodyElement> bodyElements = cell.getBodyElements();
{% endhighlight %}

***

### [XWPFStylesDocument.java](https://searchcode.com/codesearch/view/96672666/)
{% highlight java %}
1111. public TableCellInfo getTableCellInfo( XWPFTableCell cell )
1113.     XWPFTable table = cell.getTableRow().getTable();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
368. protected Object startVisitTableCell( XWPFTableCell cell, Object tableContainer, boolean firstRow, boolean lastRow,
374.     XWPFTableRow row = cell.getTableRow();
379.     CTTcPr tcPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
495. protected IITextContainer startVisitTableCell( XWPFTableCell cell, IITextContainer tableContainer )
499.     XWPFTableRow row = cell.getTableRow();
503.     CTTcPr tcPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
682. protected IITextContainer startVisitTableCell( XWPFTableCell cell, IITextContainer tableContainer,
687.     XWPFTableRow row = cell.getTableRow();
693.     CTTcPr tcPr = cell.getCTTc().getTcPr();
722.         XWPFStyle tableStyle = super.getXWPFStyle( cell.getTableRow().getTable().getStyleID() );
728.         CTTblBorders tableBorders = XWPFTableUtil.getTblBorders( cell.getTableRow().getTable() );
{% endhighlight %}

***

