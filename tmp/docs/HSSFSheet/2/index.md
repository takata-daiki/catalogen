# HSSFSheet @Cluster 2

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
{% highlight java %}
87. HSSFSheet aSheet = workbook.getSheetAt(numSheets);
89. for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
90.   if (null != aSheet.getRow(rowNumOfSheet)) {
91.     HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
{% endhighlight %}

***

