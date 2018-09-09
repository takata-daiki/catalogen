# DrawingRecord

***

### [Cluster 1](./1)
{% highlight java %}
162. DrawingRecord lastDrawingRecord = new DrawingRecord();
222.       lastDrawingRecord.processContinueRecord( crec.getData() );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
376. DrawingRecord drawingRecord = (DrawingRecord) records.get( loc );
377. System.arraycopy( drawingRecord.getData(), 0, buffer, offset, drawingRecord.getData().length );
378. offset += drawingRecord.getData().length;
{% endhighlight %}

***

