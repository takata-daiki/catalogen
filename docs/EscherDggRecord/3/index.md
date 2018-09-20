# EscherDggRecord @Cluster 3

***

### [DrawingManager.java](https://searchcode.com/codesearch/view/15642363/)
{% highlight java %}
56. EscherDggRecord dgg;
72.     dgg.addCluster( dgId, 0 );
73.     dgg.setDrawingsSaved( dgg.getDrawingsSaved() + 1 );
98.         dgg.addCluster(drawingGroupId, 1);
104.         for (int i = 0; i < dgg.getFileIdClusters().length; i++)
106.             EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
128.     dgg.setNumShapesSaved(dgg.getNumShapesSaved() + 1);
130.     if (newShapeId >= dgg.getShapeIdMax())
134.         dgg.setShapeIdMax(newShapeId + 1);
156.     for ( int i = 0; i < dgg.getFileIdClusters().length; i++ )
158.         if ( dgg.getFileIdClusters()[i].getDrawingGroupId() == dgId )
166.     int max = dgg.getShapeIdMax();
{% endhighlight %}

***

