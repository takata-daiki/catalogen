# HeaderBlockWriter @Cluster 1

***

### [POIFSFileSystem.java](https://searchcode.com/codesearch/view/15642276/)
{% highlight java %}
258. HeaderBlockWriter header_block_writer = new HeaderBlockWriter(512);
260.     header_block_writer.setBATBlocks(bat.countBlocks(),
264. header_block_writer.setPropertyStart(_property_table.getStartBlock());
267. header_block_writer.setSBATStart(sbtw.getSBAT().getStartBlock());
270. header_block_writer.setSBATBlockCount(sbtw.getSBATBlockCount());
{% endhighlight %}

***

