# POIFSReaderEvent @Cluster 1

***

### [ContentReaderListener.java](https://searchcode.com/codesearch/view/48925118/)
{% highlight java %}
65. public void processPOIFSReaderEvent(final POIFSReaderEvent event) {
67.   if (event == null || event.getName() == null
68.       || !event.getName().startsWith(PPTConstants.POWERPOINT_DOCUMENT)) {
71.                + event.getName());
77.     final DocumentInputStream dis = event.getStream();
{% endhighlight %}

***

### [ContentReaderListener.java](https://searchcode.com/codesearch/view/138791632/)
{% highlight java %}
65. public void processPOIFSReaderEvent(final POIFSReaderEvent event) {
67.   if (event == null || event.getName() == null
68.       || !event.getName().startsWith(PPTConstants.POWERPOINT_DOCUMENT)) {
71.                + event.getName());
77.     final DocumentInputStream dis = event.getStream();
{% endhighlight %}

***

