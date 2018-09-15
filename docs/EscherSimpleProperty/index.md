# EscherSimpleProperty

***

## [Cluster 1](./1)
4 results
> code comments is here.
{% highlight java %}
167. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__LINEDASHING);
168. return prop == null ? Line.PEN_SOLID : prop.getPropertyValue();
{% endhighlight %}

***

## [Cluster 2](./2)
3 results
> code comments is here.
{% highlight java %}
326. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, propId);
327. return prop == null ? 0 : prop.getPropertyValue();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
152. EscherSimpleProperty p = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__NOLINEDRAWDASH);
153. if(p != null && (p.getPropertyValue() & 0x8) == 0) return null;
{% endhighlight %}

***

## [Cluster 4](./4)
2 results
> code comments is here.
{% highlight java %}
114. final EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) o;
116. if ( propertyValue != escherSimpleProperty.propertyValue ) return false;
117. if ( getId() != escherSimpleProperty.getId() ) return false;
{% endhighlight %}

***

