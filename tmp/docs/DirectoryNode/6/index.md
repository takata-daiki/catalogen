# DirectoryNode @Cluster 6

***

### [POIFSChunkParser.java](https://searchcode.com/codesearch/view/88636100/)
{% highlight java %}
61. DirectoryNode dir = (DirectoryNode)entry;
65. if(dir.getName().startsWith(AttachmentChunks.PREFIX)) {
66.    group = new AttachmentChunks(dir.getName());
68. if(dir.getName().startsWith(NameIdChunks.PREFIX)) {
71. if(dir.getName().startsWith(RecipientChunks.PREFIX)) {
72.    group = new RecipientChunks(dir.getName());
{% endhighlight %}

***

