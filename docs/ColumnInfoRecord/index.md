# ColumnInfoRecord

***

## [Cluster 1 (if, nci, null)](./1)
3 results
> set the contents of this shape to be a copy of the source shape . < p > the 0 is specified in points . positive values will cause the to and font to the specified in the index of the font . 
{% highlight java %}
102. ColumnInfoRecord ci = ( ColumnInfoRecord ) records.get(k);
103. ci=(ColumnInfoRecord) ci.clone();
{% endhighlight %}

***

## [Cluster 2 (columninforecord, field_1_first_col, rec)](./2)
1 results
> set the logical col field for the text record . 
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

## [Cluster 3 (ci, column, columninforecord)](./3)
11 results
> check that and return a list of the font in the table . 
{% highlight java %}
305. ColumnInfoRecord columnInfo = (ColumnInfoRecord) records.get( findStartOfColumnOutlineGroup( idx ) );
311. setColumn( (short) ( columnInfo.getLastColumn() + 1 ), null, null, null, null, Boolean.TRUE);
{% endhighlight %}

***

