# TableRecord

***

### [Cluster 1](./1)
{% highlight java %}
150. for (TableRecord record : produceItems) {
154.   String key = record.itemName + " " + record.oper;
158.   cell.setCellValue(record.itemName);
161.   cell.setCellValue(record.oper);
170.   cell.setCellValue(record.UOMId);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
107. TableRecord record = new TableRecord();
108. record.itemName = item.getName();
109. record.UOMId = item.getUOMId();
112.     record.oper = "Списано";
114.     record.oper = "Произведено";
116.   record.oper = "Списано";
118. String key = record.itemName + " " + record.oper;
121.   if (record.oper.equals("Произведено"))
{% endhighlight %}

***

