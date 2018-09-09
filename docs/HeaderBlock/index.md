# HeaderBlock

***

### [Cluster 1](./1)
{% highlight java %}
135. HeaderBlock header_block;
140.     bigBlockSize = header_block.getBigBlockSize();
152. new BlockAllocationTableReader(header_block.getBigBlockSize(),
153.                                header_block.getBATCount(),
154.                                header_block.getBATArray(),
155.                                header_block.getXBATCount(),
156.                                header_block.getXBATIndex(),
167.         header_block.getSBATStart()
172.     header_block.getPropertyStart()
{% endhighlight %}

***

