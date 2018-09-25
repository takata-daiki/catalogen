# NumberRecord @Cluster 1 (field_1_row, nrec, rec)

***

### [NumberRecord.java](https://searchcode.com/codesearch/view/15642464/)
> sets the auto series field value . excel 5 only ( true ) 
{% highlight java %}
327. NumberRecord rec = new NumberRecord();
328. rec.field_1_row = field_1_row;
329. rec.field_2_col = field_2_col;
330. rec.field_3_xf = field_3_xf;
331. rec.field_4_value = field_4_value;
{% endhighlight %}

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
> test that we get the same value as excel and , for 
{% highlight java %}
374. NumberRecord nrec = null;
384. nrec.setColumn(col);
387.     nrec.setValue(getNumericCellValue());
389. nrec.setXFIndex(styleIndex);
390. nrec.setRow(row);
{% endhighlight %}

***

