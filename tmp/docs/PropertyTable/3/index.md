# PropertyTable @Cluster 3

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/15642276/)
{% highlight java %}
82. private PropertyTable _property_table;
212.     _property_table.preWrite();
216.         new SmallBlockTableWriter(_documents, _property_table.getRoot());
264.     header_block_writer.setPropertyStart(_property_table.getStartBlock());
336.         _root = new DirectoryNode(_property_table.getRoot(), this, null);
375.     _property_table.addProperty(document.getDocumentProperty());
386.     _property_table.addProperty(directory);
397.     _property_table.removeProperty(entry.getProperty());
{% endhighlight %}

***

