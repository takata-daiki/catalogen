# DBCellRecord

***

### [Cluster 1](./1)
{% highlight java %}
236. DBCellRecord cellRecord = new DBCellRecord();
244.     cellRecord.addCellOffset((short)cellRefOffset);
249. cellRecord.setRowOffset(pos - rowStartPos);
250. pos += cellRecord.serialize(pos, data);
{% endhighlight %}

***

