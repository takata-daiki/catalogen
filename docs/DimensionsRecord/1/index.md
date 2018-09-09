# DimensionsRecord @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
991. DimensionsRecord d = ( DimensionsRecord ) records.get(getDimsLoc());
993. if (col.getColumn() > d.getLastCol())
995.     d.setLastCol(( short ) (col.getColumn() + 1));
997. if (col.getColumn() < d.getFirstCol())
999.     d.setFirstCol(col.getColumn());
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1127. DimensionsRecord d = ( DimensionsRecord ) records.get(getDimsLoc());
1129. if (row.getRowNumber() >= d.getLastRow())
1131.     d.setLastRow(row.getRowNumber() + 1);
1133. if (row.getRowNumber() < d.getFirstRow())
1135.     d.setFirstRow(row.getRowNumber());
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1988. DimensionsRecord retval = new DimensionsRecord();
1990. retval.setFirstCol(( short ) 0);
1991. retval.setLastRow(1);             // one more than it is
1992. retval.setFirstRow(0);
1993. retval.setLastCol(( short ) 1);   // one more than it is
{% endhighlight %}

***

### [DimensionsRecord.java](https://searchcode.com/codesearch/view/15642393/)
{% highlight java %}
219. DimensionsRecord rec = new DimensionsRecord();
220. rec.field_1_first_row = field_1_first_row;
221. rec.field_2_last_row = field_2_last_row;
222. rec.field_3_first_col = field_3_first_col;
223. rec.field_4_last_col = field_4_last_col;
224. rec.field_5_zero = field_5_zero;
{% endhighlight %}

***

