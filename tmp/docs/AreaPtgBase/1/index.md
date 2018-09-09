# AreaPtgBase @Cluster 1

***

### [ExcelTools.java](https://searchcode.com/codesearch/view/121321570/)
{% highlight java %}
109. AreaPtgBase areaPtgBase = (AreaPtgBase) ptg;
111. if (areaPtgBase.isFirstRowRelative() && areaPtgBase.getFirstRow() > oldCell.getRowIndex()) {
112.   areaPtgBase.setFirstRow(
113.     (short) (newCell.getRowIndex() - (oldCell.getRowIndex() - areaPtgBase.getFirstRow())));
116. if (areaPtgBase.isLastRowRelative()) {
117.   areaPtgBase.setLastRow(
118.     (short) (newCell.getRowIndex() - (oldCell.getRowIndex() - areaPtgBase.getLastRow())));
121. if (areaPtgBase.isFirstColRelative()) {
122.   areaPtgBase.setFirstColumn(
123.     (short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - areaPtgBase.getFirstColumn())));
126. if (areaPtgBase.isLastColRelative()) {
127.   areaPtgBase.setLastColumn(
128.     (short) (newCell.getColumnIndex() - (oldCell.getColumnIndex() - areaPtgBase.getLastColumn())));
{% endhighlight %}

***

