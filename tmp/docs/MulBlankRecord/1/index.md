# MulBlankRecord @Cluster 1

***

### [RecordFactory.java](https://searchcode.com/codesearch/view/15642481/)
{% highlight java %}
248. MulBlankRecord mb = ( MulBlankRecord ) retval;
250. realretval = new Record[ mb.getNumColumns() ];
251. for (int k = 0; k < mb.getNumColumns(); k++)
255.     br.setColumn(( short ) (k + mb.getFirstColumn()));
256.     br.setRow(mb.getRow());
257.     br.setXFIndex(mb.getXFAt(k));
{% endhighlight %}

***

