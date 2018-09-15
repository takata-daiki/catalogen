# XWPFTableCell

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
506. for ( XWPFTableCell c : cells )
508.     if ( c.getBodyElements().size() != 1 )
512.     IBodyElement element = c.getBodyElements().get( 0 );
{% endhighlight %}

***

## [Cluster 2](./2)
3 results
> code comments is here.
{% highlight java %}
165. public static CTTblWidth getWidth( XWPFTableCell cell )
167.     return cell.getCTTc().getTcPr().getTcW();
{% endhighlight %}

***

## [Cluster 3](./3)
3 results
> code comments is here.
{% highlight java %}
157. public static CTDecimalNumber getGridSpan( XWPFTableCell cell )
159.     if ( cell.getCTTc().getTcPr() != null )
160.         return cell.getCTTc().getTcPr().getGridSpan();
{% endhighlight %}

***

## [Cluster 4](./4)
18 results
> code comments is here.
{% highlight java %}
273. protected void visitTableCellBody( XWPFTableCell cell, T tableCellContainer )
276.     List<IBodyElement> bodyElements = cell.getBodyElements();
{% endhighlight %}

***

