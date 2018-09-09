# EscherRecord @Cluster 6

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
103. EscherRecord r = iterator.next();
104. remainingBytes += r.getRecordSize();
{% endhighlight %}

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
111. EscherRecord r = iterator.next();
112. pos += r.serialize(pos, data, listener );
{% endhighlight %}

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
123. EscherRecord r = iterator.next();
124. childRecordsSize += r.getRecordSize();
{% endhighlight %}

***

### [UnknownEscherRecord.java](https://searchcode.com/codesearch/view/97383865/)
{% highlight java %}
86. for (EscherRecord r : _childRecords) {
87.     remainingBytes += r.getRecordSize();
{% endhighlight %}

***

### [UnknownEscherRecord.java](https://searchcode.com/codesearch/view/97383865/)
{% highlight java %}
92. for (EscherRecord r : _childRecords) {
93.     pos += r.serialize(pos, data, listener );
{% endhighlight %}

***

