# POIFSReaderEvent

***

## [Cluster 1](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
46. public final void processPOIFSReaderEvent(final POIFSReaderEvent event) {
47.   if (!event.getName().equalsIgnoreCase("PowerPoint Document")) {
51.     DocumentInputStream input = event.getStream();
{% endhighlight %}

***

## [Cluster 2](./2)
2 results
> this comment could not be generated...
{% highlight java %}
65. public void processPOIFSReaderEvent(final POIFSReaderEvent event) {
67.   if (event == null || event.getName() == null
68.       || !event.getName().startsWith(PPTConstants.POWERPOINT_DOCUMENT)) {
71.                + event.getName());
77.     final DocumentInputStream dis = event.getStream();
{% endhighlight %}

***

## [Cluster 3](./3)
2 results
> this comment could not be generated...
{% highlight java %}
142. public void processPOIFSReaderEvent(POIFSReaderEvent event) {
143.   if (!event.getName().startsWith(SummaryInformation.DEFAULT_STREAM_NAME)) {
149.                               PropertySetFactory.create(event.getStream());
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
73. public void processPOIFSReaderEvent(POIFSReaderEvent event)
77.     if (!event.getName().equalsIgnoreCase("PowerPoint Document"))
81.     DocumentInputStream input = event.getStream();
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
58. public void processPOIFSReaderEvent(final POIFSReaderEvent event) {
63.     dis = event.getStream();
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
333. public void processPOIFSReaderEvent(final POIFSReaderEvent event)
335.     DocumentInputStream istream = event.getStream();
336.     POIFSDocumentPath   path    = event.getPath();
337.     String              name    = event.getName();
{% endhighlight %}

***

