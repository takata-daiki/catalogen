# EscherSimpleProperty @Cluster 2

***

### [TextShape.java](https://searchcode.com/codesearch/view/97394395/)
{% highlight java %}
459. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__WRAPTEXT);
460. return prop == null ? WrapSquare : prop.getPropertyValue();
{% endhighlight %}

***

### [TextShape.java](https://searchcode.com/codesearch/view/97394395/)
{% highlight java %}
478. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__TEXTID);
479. return prop == null ? 0 : prop.getPropertyValue();
{% endhighlight %}

***

### [SimpleShape.java](https://searchcode.com/codesearch/view/97394265/)
{% highlight java %}
152. EscherSimpleProperty p = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__NOLINEDRAWDASH);
153. if(p != null && (p.getPropertyValue() & 0x8) == 0) return null;
{% endhighlight %}

***

### [SimpleShape.java](https://searchcode.com/codesearch/view/97394265/)
{% highlight java %}
167. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__LINEDASHING);
168. return prop == null ? Line.PEN_SOLID : prop.getPropertyValue();
{% endhighlight %}

***

### [SimpleShape.java](https://searchcode.com/codesearch/view/97394265/)
{% highlight java %}
199. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__LINESTYLE);
200. return prop == null ? Line.LINE_SIMPLE : prop.getPropertyValue();
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
326. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, propId);
327. return prop == null ? 0 : prop.getPropertyValue();
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
337. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, propId);
338. return prop == null ? defaultValue : prop.getPropertyValue();
{% endhighlight %}

***

### [Picture.java](https://searchcode.com/codesearch/view/97394307/)
{% highlight java %}
117. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.BLIP__BLIPTODISPLAY);
118. return prop == null ? 0 : prop.getPropertyValue();
{% endhighlight %}

***

