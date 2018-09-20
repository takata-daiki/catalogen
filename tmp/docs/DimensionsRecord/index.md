# DimensionsRecord

***

## [Cluster 1](./1)
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

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
219. DimensionsRecord rec = new DimensionsRecord();
220. rec.field_1_first_row = field_1_first_row;
221. rec.field_2_last_row = field_2_last_row;
222. rec.field_3_first_col = field_3_first_col;
223. rec.field_4_last_col = field_4_last_col;
224. rec.field_5_zero = field_5_zero;
{% endhighlight %}

***

