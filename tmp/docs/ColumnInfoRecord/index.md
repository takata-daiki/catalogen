# ColumnInfoRecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
329. ColumnInfoRecord rec = new ColumnInfoRecord();
330. rec.field_1_first_col = field_1_first_col;
331. rec.field_2_last_col = field_2_last_col;
332. rec.field_3_col_width = field_3_col_width;
333. rec.field_4_xf_index = field_4_xf_index;
334. rec.field_5_options = field_5_options;
335. rec.field_6_reserved = field_6_reserved;
{% endhighlight %}

***

## [Cluster 2](./2)
16 results
> code comments is here.
{% highlight java %}
1817. ColumnInfoRecord ci     = null;
1825.         if ((ci.getFirstColumn() <= column)
1826.                 && (column <= ci.getLastColumn()))
1835.     retval = ci.getColumnWidth();
{% endhighlight %}

***

