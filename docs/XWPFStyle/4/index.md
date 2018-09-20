# XWPFStyle @Cluster 4

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

