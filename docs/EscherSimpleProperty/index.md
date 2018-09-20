# EscherSimpleProperty

***

## [Cluster 1](./1)
8 results
> returns whether this run of text should be formatted as ( text ) or not 
{% highlight java %}
290. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__ANCHORTEXT);
315.     valign = prop.getPropertyValue();
{% endhighlight %}

***

## [Cluster 2](./2)
4 results
> gets whether the object is embedded or linked . 
{% highlight java %}
459. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.TEXT__WRAPTEXT);
460. return prop == null ? WrapSquare : prop.getPropertyValue();
{% endhighlight %}

***

## [Cluster 3](./3)
3 results
> gets whether the object is embedded or linked . 
{% highlight java %}
326. EscherSimpleProperty prop = (EscherSimpleProperty)getEscherProperty(opt, propId);
327. return prop == null ? 0 : prop.getPropertyValue();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
152. EscherSimpleProperty p = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__NOLINEDRAWDASH);
153. if(p != null && (p.getPropertyValue() & 0x8) == 0) return null;
{% endhighlight %}

***

## [Cluster 5](./5)
2 results
> returns < code > true < / code > if the specified record id . 
{% highlight java %}
91. final EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) o;
93. if ( propertyValue != escherSimpleProperty.propertyValue ) return false;
94. if ( getId() != escherSimpleProperty.getId() ) return false;
{% endhighlight %}

***

