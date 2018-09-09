# FileIdCluster

***

### [Cluster 1](./1)
{% highlight java %}
92. EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
93. if (c.getDrawingGroupId() == drawingGroupId && c.getNumShapeIdsUsed() != 1024)
95.     int result = c.getNumShapeIdsUsed() + (1024 * (i+1));
96.     c.incrementShapeId();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
231. public int compare(FileIdCluster f1, FileIdCluster f2) {
232.     if (f1.getDrawingGroupId() == f2.getDrawingGroupId()) {
235.     if (f1.getDrawingGroupId() < f2.getDrawingGroupId()) {
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
272. FileIdCluster f1 = (FileIdCluster) o1;
274. if (f1.getDrawingGroupId() == f2.getDrawingGroupId())
276. if (f1.getDrawingGroupId() < f2.getDrawingGroupId())
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
106. EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
107. if (c.getDrawingGroupId() == drawingGroupId)
109.     if (c.getNumShapeIdsUsed() != 1024)
112.         c.incrementShapeId();
{% endhighlight %}

***

