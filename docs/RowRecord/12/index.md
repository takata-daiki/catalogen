# RowRecord @Cluster 12

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1122. public void addRow(RowRecord row)
1129.     if (row.getRowNumber() >= d.getLastRow())
1131.         d.setLastRow(row.getRowNumber() + 1);
1133.     if (row.getRowNumber() < d.getFirstRow())
1135.         d.setFirstRow(row.getRowNumber());
1139.      RowRecord existingRow = rows.getRow(row.getRowNumber());
{% endhighlight %}

***

