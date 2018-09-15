# TableRecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
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

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
132. TableRecord record = new TableRecord();
133. record.itemName = prodItem.getName();
136. record.UOMId = prodItem.getUOMId();
137. record.oper = "Произведено";
138. String key = record.itemName + " " + record.oper;
{% endhighlight %}

***

