# MulBlankRecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
400. MulBlankRecord mb = ( MulBlankRecord ) retval;
402. realretval = new Record[ mb.getNumColumns() ];
403. for (int k = 0; k < mb.getNumColumns(); k++)
407.     br.setColumn(( short ) (k + mb.getFirstColumn()));
408.     br.setRow(mb.getRow());
409.     br.setXFIndex(mb.getXFAt(k));
{% endhighlight %}

***

