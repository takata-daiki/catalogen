# PropertyTable

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
123. PropertyTable properties =
129.     .getSmallDocumentBlocks(data_blocks, properties
131.             .getSBATStart()), data_blocks, properties.getRoot()
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
130. PropertyTable properties =
136.   data_blocks, properties.getRoot(),
140.   properties.getRoot().getChildren(), null);
{% endhighlight %}

***

