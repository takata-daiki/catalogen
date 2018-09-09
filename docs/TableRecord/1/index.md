# TableRecord @Cluster 1

***

### [ProdTableInRangeExellExporter.java](https://searchcode.com/codesearch/view/137412593/)
{% highlight java %}
150. for (TableRecord record : produceItems) {
154.   String key = record.itemName + " " + record.oper;
158.   cell.setCellValue(record.itemName);
161.   cell.setCellValue(record.oper);
170.   cell.setCellValue(record.UOMId);
{% endhighlight %}

***

### [ProdTableInRangeExellExporter.java](https://searchcode.com/codesearch/view/137412593/)
{% highlight java %}
174. for (TableRecord record : writeOffItems) {
178.   String key = record.itemName + " " + record.oper;
182.   cell.setCellValue(record.itemName);
185.   cell.setCellValue(record.oper);
194.   cell.setCellValue(record.UOMId);
{% endhighlight %}

***

