# EscherSpRecord

***

## [Cluster 1](./1)
10 results
> sets the 2 - d index of the < code > return < / code > in the < code > graphics 2 d < / code > context . 
{% highlight java %}
131. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
132. spRecord.setOptions((short)((ShapeTypes.PictureFrame << 4) | 0x2));
{% endhighlight %}

***

## [Cluster 2](./2)
2 results
> test that we get the same value as excel and , for 
{% highlight java %}
176. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
177. int flags = spRecord.getFlags();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> this comment could not be generated...
{% highlight java %}
547. EscherSpRecord escherSpRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
548. int shapeId = escherSpRecord.getShapeId();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
97. EscherSpRecord sp = shape.getSpContainer().getChildById(EscherSpRecord.RECORD_ID);
99.     sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_CHILD);
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param p the font to be used if there is a call . 
{% highlight java %}
161. EscherSpRecord spr = null;
171. if(spr != null) spr.setShapeId(allocateShapeId());
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> this comment could not be generated...
{% highlight java %}
462. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
463. if(spRecord != null) spRecord.setShapeId(id);
{% endhighlight %}

***

## [Cluster 7](./7)
4 results
> test that we get the same value as excel and , for 
{% highlight java %}
144. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
145. return spRecord.getShapeType();
{% endhighlight %}

***

