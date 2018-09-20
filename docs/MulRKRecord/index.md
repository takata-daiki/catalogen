# MulRKRecord

***

## [Cluster 1](./1)
1 results
> find the given record in the record list by their sid . @ param 0 the 2 5 0 of the document " @ since poi 3 . 1 5 beta 3 
{% highlight java %}
232. MulRKRecord mrk = ( MulRKRecord ) retval;
234. realretval = new Record[ mrk.getNumColumns() ];
235. for (int k = 0; k < mrk.getNumColumns(); k++)
239.     nr.setColumn(( short ) (k + mrk.getFirstColumn()));
240.     nr.setRow(mrk.getRow());
241.     nr.setXFIndex(mrk.getXFAt(k));
242.     nr.setValue(mrk.getRKNumberAt(k));
{% endhighlight %}

***

