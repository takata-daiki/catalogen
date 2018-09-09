# XWPFStyle @Cluster 1

***

### [StyleEngineForIText.java](https://searchcode.com/codesearch/view/12208690/)
{% highlight java %}
323. XWPFStyle style = document.getStyles().getStyle( styleID );
326. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
331.     if ( style.getCTStyle().getRPr() != null )
333.         FontInfos fontInfos = processRPR( style.getCTStyle().getRPr() );
350. CTTblPrBase xwpfTableProperties = style.getCTStyle().getTblPr();
{% endhighlight %}

***

### [StyleEngineForIText.java](https://searchcode.com/codesearch/view/96673306/)
{% highlight java %}
264. XWPFStyle style = document.getStyles().getStyle( styleID );
267. CTPPr xwpfParagraphProperties = style.getCTStyle().getPPr();
272.     if ( style.getCTStyle().getRPr() != null )
291. CTTblPrBase xwpfTableProperties = style.getCTStyle().getTblPr();
{% endhighlight %}

***

