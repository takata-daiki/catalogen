# DirectoryNode @Cluster 7

***

### [HWPFDocument.java](https://searchcode.com/codesearch/view/97383956/)
{% highlight java %}
215. public HWPFDocument(DirectoryNode directory) throws IOException
237.           (DocumentEntry)directory.getEntry(name);
244.   directory.createDocumentInputStream(name).read(_tableStream);
252.         (DocumentEntry)directory.getEntry(STREAM_DATA);
254.     directory.createDocumentInputStream(STREAM_DATA).read(_dataStream);
{% endhighlight %}

***

