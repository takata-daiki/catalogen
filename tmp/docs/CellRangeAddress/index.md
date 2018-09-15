# CellRangeAddress

***

## [Cluster 1](./1)
34 results
> code comments is here.
{% highlight java %}
147. CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
148. if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
151.                       newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
152.                       cellRangeAddress.getFirstColumn(),
153.                       cellRangeAddress.getLastColumn());
{% endhighlight %}

***

