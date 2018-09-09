# Property @Cluster 2

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/97397929/)
{% highlight java %}
496. Property      property = ( Property ) properties.next();
497. String        name     = property.getName();
502. if (property.isDirectory())
507.     new_dir.setStorageClsid( property.getStorageClsid() );
516.     int           startBlock = property.getStartBlock();
517.     int           size       = property.getSize();
520.     if (property.shouldUseSmallBlocks())
{% endhighlight %}

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/15642276/)
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

