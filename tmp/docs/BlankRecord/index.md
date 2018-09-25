# BlankRecord

***

## [Cluster 1 (blankrecord, field_1_row, rec)](./1)
1 results
> set the logical col number for the last cell this row ( 0 based index ) 
{% highlight java %}
324. BlankRecord rec = new BlankRecord();
325. rec.field_1_row = field_1_row;
326. rec.field_2_col = field_2_col;
327. rec.field_3_xf = field_3_xf;
{% endhighlight %}

***

## [Cluster 2 (blankrecord, br, mb)](./2)
1 results
> sets the a number of the number of the that contains the comment @ param row the row that the region is on . 
{% highlight java %}
405. BlankRecord br = new BlankRecord();
407. br.setColumn(( short ) (k + mb.getFirstColumn()));
408. br.setRow(mb.getRow());
409. br.setXFIndex(mb.getXFAt(k));
{% endhighlight %}

***

## [Cluster 3 (blankrecord, brec, case)](./3)
1 results
> sets the 
{% highlight java %}
155. BlankRecord brec = (BlankRecord) record;
157. thisRow = brec.getRow();
158. thisColumn = brec.getColumn();
{% endhighlight %}

***

