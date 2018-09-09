# POIFSReaderEvent

***

### [Cluster 1](./1)
{% highlight java %}
58. public void processPOIFSReaderEvent(final POIFSReaderEvent event) {
63.     dis = event.getStream();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
73. public void processPOIFSReaderEvent(POIFSReaderEvent event)
77.     if (!event.getName().equalsIgnoreCase("PowerPoint Document"))
81.     DocumentInputStream input = event.getStream();
{% endhighlight %}

***

