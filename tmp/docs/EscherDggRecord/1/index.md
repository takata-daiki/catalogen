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

