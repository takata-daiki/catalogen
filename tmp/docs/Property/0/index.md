# Property @Cluster 1

***

### [POIFSReader.java](https://searchcode.com/codesearch/view/15642289/)
{% highlight java %}
250. Property property = ( Property ) properties.next();
251. String   name     = property.getName();
253. if (property.isDirectory())
267.     int      startBlock = property.getStartBlock();
272.         int           size     = property.getSize();
275.         if (property.shouldUseSmallBlocks())
{% endhighlight %}

***

