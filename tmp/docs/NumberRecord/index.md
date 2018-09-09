# NumberRecord

***

### [Cluster 1](./1)
{% highlight java %}
374. NumberRecord nrec = null;
384. nrec.setColumn(col);
387.     nrec.setValue(getNumericCellValue());
389. nrec.setXFIndex(styleIndex);
390. nrec.setRow(row);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
893. NumberRecord rec = new NumberRecord();
896. rec.setRow(row);
897. rec.setColumn(col);
898. rec.setValue(value);
899. rec.setXFIndex(( short ) 0x0f);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
327. NumberRecord rec = new NumberRecord();
328. rec.field_1_row = field_1_row;
329. rec.field_2_col = field_2_col;
330. rec.field_3_xf = field_3_xf;
331. rec.field_4_value = field_4_value;
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
134. final NumberRecord numrec = (NumberRecord) record;
135. logger.warn("Cell [" + numrec.getRow() + "," + 
136.         numrec.getColumn() + 
138.         numrec.getValue() + ". Ignoring value");
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
233. NumberRecord numrec = (NumberRecord) record;
235. curRow = thisRow = numrec.getRow();
236. thisColumn = numrec.getColumn();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
389. NumberRecord nr = new NumberRecord();
391. nr.setColumn(( short ) (k + mrk.getFirstColumn()));
392. nr.setRow(mrk.getRow());
393. nr.setXFIndex(mrk.getXFAt(k));
394. nr.setValue(mrk.getRKNumberAt(k));
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
370. NumberRecord num = new NumberRecord();
372. num.setColumn(rk.getColumn());
373. num.setRow(rk.getRow());
374. num.setXFIndex(rk.getXFIndex());
375. num.setValue(rk.getRKNumber());
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
196. NumberRecord rec = new NumberRecord();
198. rec.setColumn(getColumn());
199. rec.setRow(getRow());
200. rec.setValue(getRKNumber());
201. rec.setXFIndex(getXFIndex());
202. return rec.serialize(offset, data);
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
66. NumberRecord numrec = (NumberRecord) record;
69. if(numrec.getRow() == 0) {
72. } else if(numrec.getColumn() == 0) { //第一列
74.     current.setId(Double.valueOf(numrec.getValue()).longValue());
75. } else if(numrec.getColumn() == 1) {//第二列
76.     current.setContent(String.valueOf(Double.valueOf(numrec.getValue()).longValue()));
{% endhighlight %}

***

