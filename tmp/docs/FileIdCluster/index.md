# FileIdCluster

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
92. EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
93. if (c.getDrawingGroupId() == drawingGroupId && c.getNumShapeIdsUsed() != 1024)
95.     int result = c.getNumShapeIdsUsed() + (1024 * (i+1));
96.     c.incrementShapeId();
{% endhighlight %}

***

## [Cluster 2](./2)
5 results
> code comments is here.
{% highlight java %}
106. EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
107. if (c.getDrawingGroupId() == drawingGroupId)
109.     if (c.getNumShapeIdsUsed() != 1024)
112.         c.incrementShapeId();
{% endhighlight %}

***

