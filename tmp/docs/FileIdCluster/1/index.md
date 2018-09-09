# FileIdCluster @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
276. EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
277. if (c.getDrawingGroupId() == dg.getDrawingGroupId() && c.getNumShapeIdsUsed() != 1024)
279.     int result = c.getNumShapeIdsUsed() + (1024 * (i+1));
280.     c.incrementShapeId();
{% endhighlight %}

***

### [DrawingManager2.java](https://searchcode.com/codesearch/view/15642353/)
{% highlight java %}
92. EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
93. if (c.getDrawingGroupId() == drawingGroupId && c.getNumShapeIdsUsed() != 1024)
95.     int result = c.getNumShapeIdsUsed() + (1024 * (i+1));
96.     c.incrementShapeId();
{% endhighlight %}

***

