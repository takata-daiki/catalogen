# DirectoryNode @Cluster 1

***

### [ImapServiceExtImpl.java](https://searchcode.com/codesearch/view/50611261/)
{% highlight java %}
560. DirectoryNode root = fs.getRoot();
563.         DocumentInputStream inputStream = root.createDocumentInputStream(entry);
{% endhighlight %}

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

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/97397929/)
{% highlight java %}
504. DirectoryNode new_dir =
507. new_dir.setStorageClsid( property.getStorageClsid() );
{% endhighlight %}

***

### [DirectoryNode.java](https://searchcode.com/codesearch/view/15642286/)
{% highlight java %}
88.       final DirectoryNode parent)
97. _path = new POIFSDocumentPath(parent._path, new String[]
{% endhighlight %}

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/15642276/)
{% highlight java %}
420. DirectoryNode new_dir =
423. new_dir.setStorageClsid( property.getStorageClsid() );
{% endhighlight %}

***

### [EntryNode.java](https://searchcode.com/codesearch/view/15642274/)
{% highlight java %}
65. private DirectoryNode _parent;
181.         rval = _parent.deleteEntry(this);
206.         rval = _parent.changeName(getName(), newName);
{% endhighlight %}

***

