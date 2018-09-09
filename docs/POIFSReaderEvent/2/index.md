# POIFSReaderEvent @Cluster 2

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

### [MSExtractor.java](https://searchcode.com/codesearch/view/48925180/)
{% highlight java %}
142. public void processPOIFSReaderEvent(POIFSReaderEvent event) {
143.   if (!event.getName().startsWith(SummaryInformation.DEFAULT_STREAM_NAME)) {
149.                               PropertySetFactory.create(event.getStream());
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

### [PPTContentDigester.java](https://searchcode.com/codesearch/view/129866876/)
{% highlight java %}
73. public void processPOIFSReaderEvent(POIFSReaderEvent event)
77.     if (!event.getName().equalsIgnoreCase("PowerPoint Document"))
81.     DocumentInputStream input = event.getStream();
{% endhighlight %}

***

### [PowerPointParser.java](https://searchcode.com/codesearch/view/7760072/)
{% highlight java %}
46. public final void processPOIFSReaderEvent(final POIFSReaderEvent event) {
47.   if (!event.getName().equalsIgnoreCase("PowerPoint Document")) {
51.     DocumentInputStream input = event.getStream();
{% endhighlight %}

***

### [MSExtractor.java](https://searchcode.com/codesearch/view/138790528/)
{% highlight java %}
142. public void processPOIFSReaderEvent(POIFSReaderEvent event) {
143.   if (!event.getName().startsWith(SummaryInformation.DEFAULT_STREAM_NAME)) {
149.                               PropertySetFactory.create(event.getStream());
{% endhighlight %}

***

