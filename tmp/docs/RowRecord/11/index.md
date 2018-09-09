# RowRecord @Cluster 11

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
75. public void insertRow(RowRecord row)
77.     size += row.getRecordSize();
81.     if ((row.getRowNumber() < firstrow) || (firstrow == -1))
83.         firstrow = row.getRowNumber();
85.     if ((row.getRowNumber() > lastrow) || (lastrow == -1))
87.         lastrow = row.getRowNumber();
{% endhighlight %}

***

