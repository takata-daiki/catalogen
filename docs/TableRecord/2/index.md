# TableRecord @Cluster 2

***

### [ProdTableInRangeExellExporter.java](https://searchcode.com/codesearch/view/137412593/)
{% highlight java %}
132. TableRecord record = new TableRecord();
133. record.itemName = prodItem.getName();
136. record.UOMId = prodItem.getUOMId();
137. record.oper = "Произведено";
138. String key = record.itemName + " " + record.oper;
{% endhighlight %}

***

