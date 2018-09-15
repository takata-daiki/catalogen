# EscherSimpleProperty @Cluster 3

***

### [SimpleShape.java](https://searchcode.com/codesearch/view/97394265/)
{% highlight java %}
152. EscherSimpleProperty p = (EscherSimpleProperty)getEscherProperty(opt, EscherProperties.LINESTYLE__NOLINEDRAWDASH);
153. if(p != null && (p.getPropertyValue() & 0x8) == 0) return null;
{% endhighlight %}

***

