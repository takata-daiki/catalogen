# AttachmentChunks @Cluster 1

***

### [OutlookExtractor.java](https://searchcode.com/codesearch/view/111785566/)
{% highlight java %}
117. for (AttachmentChunks attachment : msg.getAttachmentFiles()) {
122.     if (attachment.attachLongFileName != null) {
123.   filename = attachment.attachLongFileName.getValue();
124.     } else if (attachment.attachFileName != null) {
125.   filename = attachment.attachFileName.getValue();
135.           new ByteArrayInputStream(attachment.attachData.getValue()),
{% endhighlight %}

***

