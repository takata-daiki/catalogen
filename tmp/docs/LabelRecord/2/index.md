# LabelRecord @Cluster 2

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
322. LabelRecord oldrec = ( LabelRecord ) rec;
327.     workbook.addSSTString(new UnicodeString(oldrec.getValue()));
329. newrec.setRow(oldrec.getRow());
330. newrec.setColumn(oldrec.getColumn());
331. newrec.setXFIndex(oldrec.getXFIndex());
{% endhighlight %}

***

### [LabelRecord.java](https://searchcode.com/codesearch/view/15642496/)
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

