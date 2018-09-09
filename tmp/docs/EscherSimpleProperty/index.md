# EscherSimpleProperty

***

### [Cluster 1](./1)
{% highlight java %}
114. final EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) o;
116. if ( propertyValue != escherSimpleProperty.propertyValue ) return false;
117. if ( getId() != escherSimpleProperty.getId() ) return false;
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
152. EscherSimpleProperty p = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__NOLINEDRAWDASH);
153. if(p != null && (p.getPropertyValue() & 0x8) == 0) return null;
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
116. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__LINEWIDTH);
117. double width = prop == null ? DEFAULT_LINE_WIDTH : (double)prop.getPropertyValue()/EMU_PER_POINT;
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
290. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__ANCHORTEXT);
315.     valign = prop.getPropertyValue();
{% endhighlight %}

***

