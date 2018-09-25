# DrawingRecord

***

## [Cluster 1 (data, drawing, drawingrecord)](./1)
1 results
> test whether the sheet is selected 
{% highlight java %}
376. DrawingRecord drawingRecord = (DrawingRecord) records.get( loc );
377. System.arraycopy( drawingRecord.getData(), 0, buffer, offset, drawingRecord.getData().length );
378. offset += drawingRecord.getData().length;
{% endhighlight %}

***

## [Cluster 2 (continuerecord, drawingrecord, lastdrawingrecord)](./2)
1 results
> this method will display the font and returns the font index ( ) to be used for this paragraph run . 
{% highlight java %}
131. DrawingRecord lastDrawingRecord = new DrawingRecord( );
163.               lastDrawingRecord.processContinueRecord( ((ContinueRecord)record).getData() );
{% endhighlight %}

***

## [Cluster 3 (crec, drawingrecord, lastdrawingrecord)](./3)
1 results
> this method will display the font and returns the font index ( if it ' s ) in the 0 x 1 6 ) 
{% highlight java %}
162. DrawingRecord lastDrawingRecord = new DrawingRecord();
222.       lastDrawingRecord.processContinueRecord( crec.getData() );
{% endhighlight %}

***

