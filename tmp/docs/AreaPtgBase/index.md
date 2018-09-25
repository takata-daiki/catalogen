# AreaPtgBase

***

## [Cluster 1 (areaptgbase, getrowindex, oldcell)](./1)
1 results
> sets the line compound style @ param style the style of the paragraph 
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

