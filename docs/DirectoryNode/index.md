# DirectoryNode

***

### [Cluster 1](./1)
{% highlight java %}
420. DirectoryNode new_dir =
423. new_dir.setStorageClsid( property.getStorageClsid() );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
88.       final DirectoryNode parent)
97. _path = new POIFSDocumentPath(parent._path, new String[]
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
414. DirectoryNode parent   = (dir == null)
421.         ( DirectoryNode ) parent.createDirectory(name);
448.     parent.createDocument(document);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
57. DirectoryNode parentNode = (DirectoryNode)document.getParent();
61. } else if(parentNode.getFileSystem() != null) {
63. } else if(parentNode.getNFileSystem() != null) {
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
55. protected DirectoryNode directory;
151.    if(directory == null || !directory.hasEntry(setName)) return null;
156.       dis = directory.createDocumentInputStream( directory.getEntry(setName) );
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
61. DirectoryNode dir = (DirectoryNode)entry;
65. if(dir.getName().startsWith(AttachmentChunks.PREFIX)) {
66.    group = new AttachmentChunks(dir.getName());
68. if(dir.getName().startsWith(NameIdChunks.PREFIX)) {
71. if(dir.getName().startsWith(RecipientChunks.PREFIX)) {
72.    group = new RecipientChunks(dir.getName());
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
215. public HWPFDocument(DirectoryNode directory) throws IOException
237.           (DocumentEntry)directory.getEntry(name);
244.   directory.createDocumentInputStream(name).read(_tableStream);
252.         (DocumentEntry)directory.getEntry(STREAM_DATA);
254.     directory.createDocumentInputStream(STREAM_DATA).read(_dataStream);
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
65. private DirectoryNode _parent;
181.         rval = _parent.deleteEntry(this);
206.         rval = _parent.changeName(getName(), newName);
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
560. DirectoryNode root = fs.getRoot();
563.         DocumentInputStream inputStream = root.createDocumentInputStream(entry);
{% endhighlight %}

***

