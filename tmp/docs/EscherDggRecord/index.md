# EscherDggRecord

***

## [Cluster 1](./1)
1 results
> called by slideshow from a new shape is created . 
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

## [Cluster 2](./2)
1 results
> set the contents of this shape to be a copy of the source shape . < p > the 0 is specified by or an empty ( 1 6 3 ) " value " ( v 4 7 7 0 0 ) " of the style , < code > null < / code > is no entry 
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

## [Cluster 3](./3)
1 results
> to { @ link # there ( string ) } is not necessarily unique ( name + sheet index is unique ) , this method is more accurate . @ param name the name to create . 
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

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
152. EscherDggRecord dgg = getSlideShow().getDocumentRecord().getPPDrawingGroup().getEscherDggRecord();
155. int dgId = dgg.getMaxDrawingGroupId() + 1;
157. dgg.setDrawingsSaved(dgg.getDrawingsSaved() + 1);
158. dgg.setMaxDrawingGroupId(dgId);
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> called by slideshow from the specified shape . 
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

