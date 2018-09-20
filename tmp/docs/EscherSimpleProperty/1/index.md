# EscherSimpleProperty @Cluster 1

***

### [TextShape.java](https://searchcode.com/codesearch/view/97394395/)
{% highlight java %}
290. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__ANCHORTEXT);
315.     valign = prop.getPropertyValue();
{% endhighlight %}

***

### [TextShape.java](https://searchcode.com/codesearch/view/97394395/)
{% highlight java %}
435. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__TEXTTOP);
436. int val = prop == null ? EMU_PER_INCH/20 : prop.getPropertyValue();
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
417. EscherSimpleProperty op = (EscherSimpleProperty)getEscherProperty(opt, opacityProperty);
419. int opacity = op == null ? defaultOpacity : op.getPropertyValue();
{% endhighlight %}

***

### [SimpleShape.java](https://searchcode.com/codesearch/view/97394265/)
{% highlight java %}
116. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__LINEWIDTH);
117. double width = prop == null ? DEFAULT_LINE_WIDTH : (double)prop.getPropertyValue()/EMU_PER_POINT;
{% endhighlight %}

***

### [TextShape.java](https://searchcode.com/codesearch/view/97394395/)
{% highlight java %}
411. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__TEXTRIGHT);
412. int val = prop == null ? EMU_PER_INCH/10 : prop.getPropertyValue();
{% endhighlight %}

***

### [TextShape.java](https://searchcode.com/codesearch/view/97394395/)
{% highlight java %}
386. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__TEXTLEFT);
387. int val = prop == null ? EMU_PER_INCH/10 : prop.getPropertyValue();
{% endhighlight %}

***

### [TextShape.java](https://searchcode.com/codesearch/view/97394395/)
{% highlight java %}
361. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__TEXTBOTTOM);
362. int val = prop == null ? EMU_PER_INCH/20 : prop.getPropertyValue();
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
381. EscherSimpleProperty p = (EscherSimpleProperty)getEscherProperty(opt, colorProperty);
384. int val = p == null ? defaultColor : p.getPropertyValue();
{% endhighlight %}

***

