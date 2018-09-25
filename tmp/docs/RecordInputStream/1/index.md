# RecordInputStream @Cluster 1 (hasnextrecord, recordinputstream, recstream)

***

### [EventRecordFactory.java](https://searchcode.com/codesearch/view/15642343/)
> create an array of records from an input stream @ return the array of bytes that were used by the 
{% highlight java %}
294. RecordInputStream recStream = new RecordInputStream(in);
296. while (recStream.hasNextRecord()) {
297.   recStream.nextRecord();
{% endhighlight %}

***

