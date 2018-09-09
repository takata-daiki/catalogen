# DrawingGroupRecord

***

### [Cluster 1](./1)
{% highlight java %}
2224. DrawingGroupRecord drawingGroup = new DrawingGroupRecord();
2225. drawingGroup.addEscherRecord(dggContainer);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
285. for(DrawingGroupRecord dgr : drawingGroups) {
286.    dgr.decode();
287.    findPictures(dgr.getEscherRecords());
{% endhighlight %}

***

