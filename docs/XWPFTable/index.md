# XWPFTable

***

### [Cluster 1](./1)
{% highlight java %}
338. XWPFTable table = row.getTable();
339. AttributesImpl attributes = createClassAttribute( table.getStyleID() );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
1017. XWPFTable table = cell.getTableRow().getTable();
1018. for ( int i = rowIndex + 1; i < table.getRows().size(); i++ )
1020.     row = table.getRow( i );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
613. private void applyStyles( XWPFTable ele, IStylableElement<XWPFTable> element )
616.     CTString tblStyle = ele.getCTTbl().getTblPr().getTblStyle();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
237. protected void visitTableBody( XWPFTable table, T tableContainer )
241.     List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
439. protected IITextContainer startVisitTable( XWPFTable table, IITextContainer tableContainer )
443.     CTString str = table.getCTTbl().getTblPr().getTblStyle();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
64. public static float[] computeColWidths( XWPFTable table )
73.     CTTblGrid grid = table.getCTTbl().getTblGrid();
82.         List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
131. public static int getNumberOfColumnFromFirstRow( XWPFTable table )
136.     int numberOfRows = table.getNumberOfRows();
139.         XWPFTableRow firstRow = table.getRow( 0 );
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
409. protected IITextContainer startVisitTable( XWPFTable table, IITextContainer pdfContainer )
439.     if ( table.getCTTbl() != null )
441.         if ( table.getCTTbl().getTblPr().getTblBorders() != null )
443.             CTBorder bottom = table.getCTTbl().getTblPr().getTblBorders().getBottom();
448.             CTBorder left = table.getCTTbl().getTblPr().getTblBorders().getLeft();
453.             CTBorder top = table.getCTTbl().getTblPr().getTblBorders().getTop();
458.             CTBorder right = table.getCTTbl().getTblPr().getTblBorders().getRight();
{% endhighlight %}

***

