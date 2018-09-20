# MulRKRecord @Cluster 1

***

### [RecordFactory.java](https://searchcode.com/codesearch/view/15642481/)
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

