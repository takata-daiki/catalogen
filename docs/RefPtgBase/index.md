# RefPtgBase

***

### [Cluster 1](./1)
{% highlight java %}
96. RefPtgBase refPtgBase = (RefPtgBase) ptg;
98. if (refPtgBase.isRowRelative()) {
99.   refPtgBase.setRow(
100.     (short) (newCell.getRowIndex() - (oldCell.getRowIndex() - refPtgBase.getRow())));
103. if (refPtgBase.isColRelative()) {
104.   refPtgBase.setColumn(
105.     (short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - refPtgBase.getColumn())));
{% endhighlight %}

***

