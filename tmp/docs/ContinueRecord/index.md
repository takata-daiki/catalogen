# ContinueRecord

***

### [Cluster 1](./1)
{% highlight java %}
117. ContinueRecord c2 = createContinue2();
156. int bytesWritten3 = c2.serialize( pos, data );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
218. ContinueRecord crec = (ContinueRecord)recs[0];
222.   lastDrawingRecord.processContinueRecord( crec.getData() );
227.   ((DrawingGroupRecord)lastRec).processContinueRecord(crec.getData());
{% endhighlight %}

***

