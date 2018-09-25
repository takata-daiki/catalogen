# DirectoryNode @Cluster 2 (dir, directory, parent)

***

### [DocumentInputStream.java](https://searchcode.com/codesearch/view/97397924/)
> sets the underlying . @ param @ see org . apache . poi . openxml 4 j . opc . name 
{% highlight java %}
57. DirectoryNode parentNode = (DirectoryNode)document.getParent();
61. } else if(parentNode.getFileSystem() != null) {
63. } else if(parentNode.getNFileSystem() != null) {
{% endhighlight %}

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/15642276/)
> sets the a 4 - byte is used to null and only set the current implementation @ see org . apache . poi . xwpf . usermodel . ibody # color ( int ) 
{% highlight java %}
414. DirectoryNode parent   = (dir == null)
421.         ( DirectoryNode ) parent.createDirectory(name);
448.     parent.createDocument(document);
{% endhighlight %}

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/97397929/)
> sets the a 4 - byte is used to null and only set the current implementation @ see org . apache . poi . xwpf . usermodel . ibody # color ( int ) 
{% highlight java %}
498. DirectoryNode parent   = (dir == null)
505.         ( DirectoryNode ) parent.createDirectory(name);
534.     parent.createDocument(document);
{% endhighlight %}

***

### [HWPFDocument.java](https://searchcode.com/codesearch/view/97383956/)
> creates the < code > null < / code > for the specified shape . 
{% highlight java %}
215. public HWPFDocument(DirectoryNode directory) throws IOException
237.           (DocumentEntry)directory.getEntry(name);
244.   directory.createDocumentInputStream(name).read(_tableStream);
252.         (DocumentEntry)directory.getEntry(STREAM_DATA);
254.     directory.createDocumentInputStream(STREAM_DATA).read(_dataStream);
{% endhighlight %}

***

