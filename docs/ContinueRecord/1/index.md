# ContinueRecord @Cluster 1 (c2, crec, getdata)

***

### [TextObjectRecord.java](https://searchcode.com/codesearch/view/15642370/)
> creates the < code > null < / code > which holds shape data for this sheet 
{% highlight java %}
117. ContinueRecord c2 = createContinue2();
156. int bytesWritten3 = c2.serialize( pos, data );
{% endhighlight %}

***

### [HSSFEventFactory.java](https://searchcode.com/codesearch/view/15642337/)
> called by slideshow ater a new sheet is created 
{% highlight java %}
218. ContinueRecord crec = (ContinueRecord)recs[0];
222.   lastDrawingRecord.processContinueRecord( crec.getData() );
227.   ((DrawingGroupRecord)lastRec).processContinueRecord(crec.getData());
{% endhighlight %}

***

