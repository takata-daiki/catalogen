# PropertyTable

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
160. PropertyTable properties =
166.           bigBlockSize, data_blocks, properties.getRoot(),
170.     properties.getRoot().getChildren(),
176. getRoot().setStorageClsid(properties.getRoot().getStorageClsid());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
130. PropertyTable properties =
136.   data_blocks, properties.getRoot(),
140.   properties.getRoot().getChildren(), null);
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
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

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
78. private PropertyTable _property_table;
302.     _property_table.preWrite();
306.         new SmallBlockTableWriter(bigBlockSize, _documents, _property_table.getRoot());
354.     header_block_writer.setPropertyStart(_property_table.getStartBlock());
426.         _root = new DirectoryNode(_property_table.getRoot(), this, null);
458.     _property_table.addProperty(document.getDocumentProperty());
469.     _property_table.addProperty(directory);
480.     _property_table.removeProperty(entry.getProperty());
{% endhighlight %}

***

