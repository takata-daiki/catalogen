# Table @Cluster 1 (col, getrow, int)

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
> sets the top border style for a region of cells by manipulating the cell style of the individual cells on the bottom @ param border the new border @ param region the region that should have the border @ param sheet the sheet that the region is on . @ since poi 3 . 1 6 beta 1 
{% highlight java %}
278. private static int numCol(Table t) {
280. for (int i = 0; i < t.numRows(); i++) {
281.   if (t.getRow(i).numCells() > col)
282.     col = t.getRow(i).numCells();
{% endhighlight %}

***

