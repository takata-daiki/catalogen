# XSSFRow @Cluster 6

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
189. private void setColumnHeaders( XSSFRow row,  List<String> delist){
214.       XSSFCell cell = row.createCell(columnIndex);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
226. private void setColumnData( XSSFRow row,  String[] data){
235.       XSSFCell cell = row.createCell(columnIndex);
{% endhighlight %}

***

### [ReadExcel.java](https://searchcode.com/codesearch/view/93053248/)
{% highlight java %}
210. private void readHeaderRow(XSSFRow row, String type) throws Exception{
211.   Iterator<Cell> cellit = row.cellIterator();
{% endhighlight %}

***

### [ReadExcel.java](https://searchcode.com/codesearch/view/93053248/)
{% highlight java %}
231. private void readDataRow(XSSFRow row,String type) throws Exception{
240.        XSSFCell cell = row.getCell(count);
{% endhighlight %}

***

