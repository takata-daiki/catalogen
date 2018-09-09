# XWPFTableCell

***

### [Cluster 1](./1)
{% highlight java %}
157. public static CTDecimalNumber getGridSpan( XWPFTableCell cell )
159.     if ( cell.getCTTc().getTcPr() != null )
160.         return cell.getCTTc().getTcPr().getGridSpan();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
495. protected IITextContainer startVisitTableCell( XWPFTableCell cell, IITextContainer tableContainer )
499.     XWPFTableRow row = cell.getTableRow();
503.     CTTcPr tcPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
963. private int getCellIndex( int cellIndex, XWPFTableCell cell )
965.     BigInteger gridSpan = stylesDocument.getTableCellGridSpan( cell.getCTTc().getTcPr() );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
273. protected void visitTableCellBody( XWPFTableCell cell, T tableCellContainer )
276.     List<IBodyElement> bodyElements = cell.getBodyElements();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
84. private void testTableCell( XWPFTableCell cell, XWPFStylesDocument stylesDocument )
86.     XWPFParagraph paragraph = cell.getParagraphs().get( 0 );
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
222. protected XHTMLPageContentBuffer startVisitTableCell( XWPFTableCell tableCell, XHTMLPageContentBuffer tableContainer )
226.     CTTcPr tcPr = tableCell.getCTTc().getTcPr();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
426. public static StringBuilder getStyle( XWPFTableCell tableCell, CTDocDefaults defaults )
430.     CTTcPr tcPr = tableCell.getCTTc().getTcPr();
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
1053. for ( XWPFTableCell mergedCell : vMergeCells )
1055.     List<IBodyElement> bodyElements = mergedCell.getBodyElements();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
506. for ( XWPFTableCell c : cells )
508.     if ( c.getBodyElements().size() != 1 )
512.     IBodyElement element = c.getBodyElements().get( 0 );
{% endhighlight %}

***

