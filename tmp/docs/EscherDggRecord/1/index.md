# EscherDggRecord @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
268. EscherDggRecord dgg = _slideShow.getDocumentRecord().getPPDrawingGroup().getEscherDggRecord();
271. dgg.setNumShapesSaved( dgg.getNumShapesSaved() + 1 );
274. for (int i = 0; i < dgg.getFileIdClusters().length; i++)
276.     EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
283.         if (result >= dgg.getShapeIdMax())
284.             dgg.setShapeIdMax( result + 1 );
290. dgg.addCluster( dg.getDrawingGroupId(), 0, false );
291. dgg.getFileIdClusters()[dgg.getFileIdClusters().length-1].incrementShapeId();
293. int result = (1024 * dgg.getFileIdClusters().length);
295. if (result >= dgg.getShapeIdMax())
296.     dgg.setShapeIdMax( result + 1 );
{% endhighlight %}

***

### [DrawingManager2.java](https://searchcode.com/codesearch/view/15642353/)
{% highlight java %}
57. EscherDggRecord dgg;
75.     dgg.addCluster( dgId, 0 );
76.     dgg.setDrawingsSaved( dgg.getDrawingsSaved() + 1 );
87.     dgg.setNumShapesSaved( dgg.getNumShapesSaved() + 1 );
90.     for (int i = 0; i < dgg.getFileIdClusters().length; i++)
92.         EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
100.             if (result >= dgg.getShapeIdMax())
101.                 dgg.setShapeIdMax( result + 1 );
107.     dgg.addCluster( drawingGroupId, 0 );
108.     dgg.getFileIdClusters()[dgg.getFileIdClusters().length-1].incrementShapeId();
111.     int result = (1024 * dgg.getFileIdClusters().length);
113.     if (result >= dgg.getShapeIdMax())
114.         dgg.setShapeIdMax( result + 1 );
134.     for ( int i = 0; i < dgg.getFileIdClusters().length; i++ )
136.         if ( dgg.getFileIdClusters()[i].getDrawingGroupId() == dgId )
144.     int max = dgg.getShapeIdMax();
{% endhighlight %}

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

