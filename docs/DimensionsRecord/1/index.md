# DimensionsRecord @Cluster 1 (col, dims, retval)

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> test that we get the same value as excel and , for 
{% highlight java %}
991. DimensionsRecord d = ( DimensionsRecord ) records.get(getDimsLoc());
993. if (col.getColumn() > d.getLastCol())
995.     d.setLastCol(( short ) (col.getColumn() + 1));
997. if (col.getColumn() < d.getFirstCol())
999.     d.setFirstCol(col.getColumn());
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> test that we get the same value as excel and , for 
{% highlight java %}
1127. DimensionsRecord d = ( DimensionsRecord ) records.get(getDimsLoc());
1129. if (row.getRowNumber() >= d.getLastRow())
1131.     d.setLastRow(row.getRowNumber() + 1);
1133. if (row.getRowNumber() < d.getFirstRow())
1135.     d.setFirstRow(row.getRowNumber());
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> creates the 
{% highlight java %}
1988. DimensionsRecord retval = new DimensionsRecord();
1990. retval.setFirstCol(( short ) 0);
1991. retval.setLastRow(1);             // one more than it is
1992. retval.setFirstRow(0);
1993. retval.setLastCol(( short ) 1);   // one more than it is
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> test that we get the same value as excel and , for 
{% highlight java %}
95. protected DimensionsRecord           dims;
664.     dims.setFirstCol(firstcol);
665.     dims.setFirstRow(firstrow);
666.     dims.setLastCol(lastcol);
667.     dims.setLastRow(lastrow);
{% endhighlight %}

***

