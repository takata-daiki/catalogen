# EscherSpRecord

***

## [Cluster 1](./1)
2 results
> code comments is here.
{% highlight java %}
176. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
177. int flags = spRecord.getFlags();
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
547. EscherSpRecord escherSpRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
548. int shapeId = escherSpRecord.getShapeId();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
97. EscherSpRecord sp = shape.getSpContainer().getChildById(EscherSpRecord.RECORD_ID);
99.     sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_CHILD);
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
161. EscherSpRecord spr = null;
171. if(spr != null) spr.setShapeId(allocateShapeId());
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> code comments is here.
{% highlight java %}
462. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
463. if(spRecord != null) spRecord.setShapeId(id);
{% endhighlight %}

***

## [Cluster 6](./6)
4 results
> code comments is here.
{% highlight java %}
225. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
226. return (spRecord.getFlags()& EscherSpRecord.FLAG_FLIPHORIZ) != 0;
{% endhighlight %}

***

