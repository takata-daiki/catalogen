# FileIdCluster

***

## [Cluster 1](./1)
1 results
> this comment could not be generated...
{% highlight java %}
276. EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
277. if (c.getDrawingGroupId() == dg.getDrawingGroupId() && c.getNumShapeIdsUsed() != 1024)
279.     int result = c.getNumShapeIdsUsed() + (1024 * (i+1));
280.     c.incrementShapeId();
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
92. EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
93. if (c.getDrawingGroupId() == drawingGroupId && c.getNumShapeIdsUsed() != 1024)
95.     int result = c.getNumShapeIdsUsed() + (1024 * (i+1));
96.     c.incrementShapeId();
{% endhighlight %}

***

## [Cluster 3](./3)
5 results
> test that we get the same value as excel and , for 
{% highlight java %}
272. FileIdCluster f1 = (FileIdCluster) o1;
274. if (f1.getDrawingGroupId() == f2.getDrawingGroupId())
276. if (f1.getDrawingGroupId() < f2.getDrawingGroupId())
{% endhighlight %}

***

