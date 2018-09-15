# XWPFStyle

***

## [Cluster 1](./1)
4 results
> code comments is here.
{% highlight java %}
192. public static CTPPr getPPr( XWPFStyle style )
199.     CTStyle ctStyle = style.getCTStyle();
{% endhighlight %}

***

## [Cluster 2](./2)
2 results
> code comments is here.
{% highlight java %}
291. XWPFStyle style = document.getStyles().getStyle( styleID );
300. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
306.     CTStyle ctStyle = style.getCTStyle();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
323. XWPFStyle style = document.getStyles().getStyle( styleID );
326. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
331.     if ( style.getCTStyle().getRPr() != null )
333.         FontInfos fontInfos = processRPR( style.getCTStyle().getRPr() );
350. CTTblPrBase xwpfTableProperties = style.getCTStyle().getTblPr();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
264. XWPFStyle style = document.getStyles().getStyle( styleID );
267. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
272.     if ( style.getCTStyle().getRPr() != null )
291. CTTblPrBase xwpfTableProperties = style.getCTStyle().getTblPr();
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> code comments is here.
{% highlight java %}
722. XWPFStyle tableStyle = super.getXWPFStyle( cell.getTableRow().getTable().getStyleID() );
725.     tableStyleBorders = tableStyle.getCTStyle().getTblPr().getTblBorders();
{% endhighlight %}

***

