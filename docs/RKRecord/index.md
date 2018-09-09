# RKRecord

***

### [Cluster 1](./1)
{% highlight java %}
369. RKRecord     rk  = ( RKRecord ) retval;
372. num.setColumn(rk.getColumn());
373. num.setRow(rk.getRow());
374. num.setXFIndex(rk.getXFIndex());
375. num.setValue(rk.getRKNumber());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
384. RKRecord rk = (RKRecord) record;
385. addCell(record, new NumberCell(rk.getRKNumber(), format));
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
313. RKRecord rec = new RKRecord();
314. rec.field_1_row = field_1_row;
315. rec.field_2_col = field_2_col;
316. rec.field_3_xf_index = field_3_xf_index;
317. rec.field_4_rk_number = field_4_rk_number;
{% endhighlight %}

***

