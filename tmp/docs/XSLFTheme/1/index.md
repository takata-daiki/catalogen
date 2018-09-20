# XSLFTheme @Cluster 1

***

### [RenderableShape.java](https://searchcode.com/codesearch/view/97406799/)
{% highlight java %}
433. XSLFTheme theme = _shape.getSheet().getTheme();
434. XmlObject lnProps = theme.getXmlObject().
{% endhighlight %}

***

### [XSLFTextRun.java](https://searchcode.com/codesearch/view/97406808/)
{% highlight java %}
272. final XSLFTheme theme = _p.getParentShape().getSheet().getTheme();
280.                 typeface = theme.getMajorFont();
282.                 typeface = theme.getMinorFont();
{% endhighlight %}

***

### [XSLFBackground.java](https://searchcode.com/codesearch/view/97406821/)
{% highlight java %}
78. XSLFTheme theme = getSheet().getTheme();
80.         theme.getXmlObject().getThemeElements().getFmtScheme().getBgFillStyleLst();
83. fill = rShape.selectPaint(graphics, bgStyle, phClr, theme.getPackagePart());
{% endhighlight %}

***

