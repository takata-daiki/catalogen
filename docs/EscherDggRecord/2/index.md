# EscherDggRecord @Cluster 2

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

