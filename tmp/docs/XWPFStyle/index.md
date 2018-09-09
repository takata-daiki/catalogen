# XWPFStyle

***

### [Cluster 1](./1)
{% highlight java %}
323. XWPFStyle style = document.getStyles().getStyle( styleID );
326. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
331.     if ( style.getCTStyle().getRPr() != null )
333.         FontInfos fontInfos = processRPR( style.getCTStyle().getRPr() );
350. CTTblPrBase xwpfTableProperties = style.getCTStyle().getTblPr();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
291. XWPFStyle style = document.getStyles().getStyle( styleID );
300. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
306.     CTStyle ctStyle = style.getCTStyle();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
63. public static CTRPr getRPr( XWPFStyle style )
70.     CTStyle ctStyle = style.getCTStyle();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
722. XWPFStyle tableStyle = super.getXWPFStyle( cell.getTableRow().getTable().getStyleID() );
725.     tableStyleBorders = tableStyle.getCTStyle().getTblPr().getTblBorders();
{% endhighlight %}

***

