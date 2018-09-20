# XWPFStyle

***

## [Cluster 1](./1)
2 results
> set whether the this is properties @ param set true to make that the text should be wrapped . must be one of the < code > wrap < / code > constants defined in this class . 
{% highlight java %}
75. public static CTRPr getRPr( XWPFStyle style )
82.     CTStyle ctStyle = style.getCTStyle();
{% endhighlight %}

***

## [Cluster 2](./2)
4 results
> set styleid @ param styleid 
{% highlight java %}
462. public static CTPPr getPPr( XWPFStyle style )
469.     CTStyle ctStyle = style.getCTStyle();
{% endhighlight %}

***

## [Cluster 3](./3)
2 results
> set the formula expression to use for the bottom border 
{% highlight java %}
289. XWPFStyle style = document.getStyles().getStyle( styleID );
298. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
304.     CTStyle ctStyle = style.getCTStyle();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
323. XWPFStyle style = document.getStyles().getStyle( styleID );
326. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
331.     if ( style.getCTStyle().getRPr() != null )
333.         FontInfos fontInfos = processRPR( style.getCTStyle().getRPr() );
350. CTTblPrBase xwpfTableProperties = style.getCTStyle().getTblPr();
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param @ see org . apache . poi . hslf . usermodel . null # < code > - 1 < / code > which or < code > null < / code > . 
{% highlight java %}
264. XWPFStyle style = document.getStyles().getStyle( styleID );
267. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
272.     if ( style.getCTStyle().getRPr() != null )
291. CTTblPrBase xwpfTableProperties = style.getCTStyle().getTblPr();
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> this comment could not be generated...
{% highlight java %}
722. XWPFStyle tableStyle = super.getXWPFStyle( cell.getTableRow().getTable().getStyleID() );
725.     tableStyleBorders = tableStyle.getCTStyle().getTblPr().getTblBorders();
{% endhighlight %}

***

