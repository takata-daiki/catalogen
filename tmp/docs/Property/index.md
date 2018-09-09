# Property

***

### [Cluster 1](./1)
{% highlight java %}
194. Property property = ( Property ) children.pop();
197. if (property.isDirectory())
201. index = property.getPreviousChildIndex();
206. index = property.getNextChildIndex();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
412. Property      property = ( Property ) properties.next();
413. String        name     = property.getName();
418. if (property.isDirectory())
423.     new_dir.setStorageClsid( property.getStorageClsid() );
431.     int           startBlock = property.getStartBlock();
432.     int           size       = property.getSize();
435.     if (property.shouldUseSmallBlocks())
{% endhighlight %}

***

