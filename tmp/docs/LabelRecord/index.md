# LabelRecord

***

### [Cluster 1](./1)
{% highlight java %}
368. LabelRecord label = (LabelRecord) record;
369. addTextCell(record, label.getValue());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
322. LabelRecord oldrec = ( LabelRecord ) rec;
327.     workbook.addSSTString(new UnicodeString(oldrec.getValue()));
329. newrec.setRow(oldrec.getRow());
330. newrec.setColumn(oldrec.getColumn());
331. newrec.setXFIndex(oldrec.getXFIndex());
{% endhighlight %}

***

