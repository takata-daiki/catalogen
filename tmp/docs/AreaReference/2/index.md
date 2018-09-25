# AreaReference @Cluster 2 (arearef, getfirstcell, int)

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> sets the content types of the file ( s ) to input , and returns the result @ param sheet the sheet to look up @ param color the starting offset into the byte array @ param value the short ( 1 6 - bit ) value 
{% highlight java %}
321. public static void clear(HSSFSheet sh, AreaReference aRef){
322.   for(int row = aRef.getFirstCell().getRow(); row <= aRef.getLastCell().getRow(); row++){
325.       for(int col = aRef.getFirstCell().getCol(); col <= aRef.getLastCell().getCol(); col++){
{% endhighlight %}

***

