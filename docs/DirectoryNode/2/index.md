# DirectoryNode @Cluster 2

***

### [DocumentInputStream.java](https://searchcode.com/codesearch/view/97397924/)
{% highlight java %}
57. DirectoryNode parentNode = (DirectoryNode)document.getParent();
61. } else if(parentNode.getFileSystem() != null) {
63. } else if(parentNode.getNFileSystem() != null) {
{% endhighlight %}

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/15642276/)
{% highlight java %}
414. DirectoryNode parent   = (dir == null)
421.         ( DirectoryNode ) parent.createDirectory(name);
448.     parent.createDocument(document);
{% endhighlight %}

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/97397929/)
{% highlight java %}
498. DirectoryNode parent   = (dir == null)
505.         ( DirectoryNode ) parent.createDirectory(name);
534.     parent.createDocument(document);
{% endhighlight %}

***

### [POIDocument.java](https://searchcode.com/codesearch/view/97383067/)
{% highlight java %}
55. protected DirectoryNode directory;
151.    if(directory == null || !directory.hasEntry(setName)) return null;
156.       dis = directory.createDocumentInputStream( directory.getEntry(setName) );
{% endhighlight %}

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

