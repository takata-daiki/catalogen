# LabelRecord @Cluster 2 (labelrecord, lrec, oldrec)

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
> sets the 
{% highlight java %}
368. LabelRecord label = (LabelRecord) record;
369. addTextCell(record, label.getValue());
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
> this method is set to the xml signature document and value the xml : " 6 4 0 " of the < code > null < / code > . as ( { @ link # with ( ) } may be used to a < code > return < / code > . @ throws as if the supplied < tt > sheet < / tt > is < code > null < / code > 
{% highlight java %}
202. LabelRecord lrec = (LabelRecord) record;
204. curRow = thisRow = lrec.getRow();
205. thisColumn = lrec.getColumn();
206. value = lrec.getValue().trim();
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
> sets the 
{% highlight java %}
322. LabelRecord oldrec = ( LabelRecord ) rec;
327.     workbook.addSSTString(new UnicodeString(oldrec.getValue()));
329. newrec.setRow(oldrec.getRow());
330. newrec.setColumn(oldrec.getColumn());
331. newrec.setXFIndex(oldrec.getXFIndex());
{% endhighlight %}

***

