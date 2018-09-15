# MulRKRecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
384. MulRKRecord mrk = ( MulRKRecord ) retval;
386. realretval = new Record[ mrk.getNumColumns() ];
387. for (int k = 0; k < mrk.getNumColumns(); k++)
391.     nr.setColumn(( short ) (k + mrk.getFirstColumn()));
392.     nr.setRow(mrk.getRow());
393.     nr.setXFIndex(mrk.getXFAt(k));
394.     nr.setValue(mrk.getRKNumberAt(k));
{% endhighlight %}

***

