# EscherRecord

***

### [Cluster 1](./1)
{% highlight java %}
65. EscherRecord child = recordFactory.createRecord( data, offset );
66. int childBytesWritten = child.fillFields( data, offset, recordFactory );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
560. EscherRecord escherRecord = _blipRecords.get( 0 );
561. switch ( escherRecord.getRecordId() )
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
98. EscherRecord escherRecord = null;
107. escherRecord.setRecordId(recordId);
108. escherRecord.setOptions(options);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
324. EscherRecord r = iterator.next();
328. } else if (r.getRecordId() == recordId){
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
129. for (EscherRecord record : _childRecords) {
130.     children.append( record.toString() );
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
86. for (EscherRecord r : _childRecords) {
87.     remainingBytes += r.getRecordSize();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
529. for(EscherRecord escherRecord : records) {
574.    findPictures(escherRecord.getChildRecords());
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
236. EscherRecord escherRecord = iterator.next();
237. escherRecord.display(w, indent + 1);
{% endhighlight %}

***

