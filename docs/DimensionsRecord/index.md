# DimensionsRecord

***

## [Cluster 1 (col, dims, retval)](./1)
4 results
> test that we get the same value as excel and , for 
{% highlight java %}
991. DimensionsRecord d = ( DimensionsRecord ) records.get(getDimsLoc());
993. if (col.getColumn() > d.getLastCol())
995.     d.setLastCol(( short ) (col.getColumn() + 1));
997. if (col.getColumn() < d.getFirstCol())
999.     d.setFirstCol(col.getColumn());
{% endhighlight %}

***

