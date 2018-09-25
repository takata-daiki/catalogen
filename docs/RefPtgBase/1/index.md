# RefPtgBase @Cluster 1 (getcolumnindex, oldcell, refptgbase)

***

### [ExcelTools.java](https://searchcode.com/codesearch/view/121321570/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
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

