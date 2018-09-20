# LabelRecord

***

## [Cluster 1](./1)
1 results
> sets the auto series field value . true = text is where 
{% highlight java %}
302. LabelRecord rec = new LabelRecord();
303. rec.field_1_row = field_1_row;
304. rec.field_2_column = field_2_column;
305. rec.field_3_xf_index = field_3_xf_index;
306. rec.field_4_string_len = field_4_string_len;
307. rec.field_5_unicode_flag = field_5_unicode_flag;
308. rec.field_6_value = field_6_value;
{% endhighlight %}

***

## [Cluster 2](./2)
3 results
> sets the 
{% highlight java %}
368. LabelRecord label = (LabelRecord) record;
369. addTextCell(record, label.getValue());
{% endhighlight %}

***

