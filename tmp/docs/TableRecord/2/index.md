# TableRecord @Cluster 2

***

### [ProdTableInRangeExellExporter.java](https://searchcode.com/codesearch/view/137412593/)
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

