# DimensionsRecord

***

### [Cluster 1](./1)
{% highlight java %}
991. DimensionsRecord d = ( DimensionsRecord ) records.get(getDimsLoc());
993. if (col.getColumn() > d.getLastCol())
995.     d.setLastCol(( short ) (col.getColumn() + 1));
997. if (col.getColumn() < d.getFirstCol())
999.     d.setFirstCol(col.getColumn());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
95. protected DimensionsRecord           dims;
664.     dims.setFirstCol(firstcol);
665.     dims.setFirstRow(firstrow);
666.     dims.setLastCol(lastcol);
667.     dims.setLastRow(lastrow);
{% endhighlight %}

***

