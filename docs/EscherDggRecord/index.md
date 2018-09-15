# EscherDggRecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
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

## [Cluster 2](./2)
1 results
> code comments is here.
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

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
152. EscherDggRecord dgg = getSlideShow().getDocumentRecord().getPPDrawingGroup().getEscherDggRecord();
155. int dgId = dgg.getMaxDrawingGroupId() + 1;
157. dgg.setDrawingsSaved(dgg.getDrawingsSaved() + 1);
158. dgg.setMaxDrawingGroupId(dgId);
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
2178. EscherDggRecord dgg = new EscherDggRecord();
2184. dgg.setRecordId(EscherDggRecord.RECORD_ID);
2185. dgg.setOptions((short)0x0000);
2186. dgg.setShapeIdMax(1024);
2187. dgg.setNumShapesSaved(0);
2188. dgg.setDrawingsSaved(0);
2189. dgg.setFileIdClusters(new EscherDggRecord.FileIdCluster[] {} );
{% endhighlight %}

***

