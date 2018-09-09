# BOFRecord @Cluster 1

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
325. BOFRecord bof = (BOFRecord) record;
326. if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
328. } else if (bof.getType() == BOFRecord.TYPE_CHART) {
341. } else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
{% highlight java %}
130. BOFRecord br = (BOFRecord) record;
131. if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
{% endhighlight %}

***

### [ExcelKeywordParser.java](https://searchcode.com/codesearch/view/12440040/)
{% highlight java %}
101. final BOFRecord bof = (BOFRecord) record;
102. if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
107. else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
109.         logger.info("recordsize = " + bof.getRecordSize() + 
111.                 bof.getRequiredVersion());
{% endhighlight %}

***

### [ExcelLanguageCentricParser.java](https://searchcode.com/codesearch/view/12440043/)
{% highlight java %}
99. final BOFRecord bof = (BOFRecord) record;
100. if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
105. else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
107.         logger.info("recordsize = " + bof.getRecordSize() + 
109.                 bof.getRequiredVersion());
{% endhighlight %}

***

### [Excel2003ImportListener.java](https://searchcode.com/codesearch/view/92669296/)
{% highlight java %}
42. BOFRecord bof = (BOFRecord) record;
43. if (bof.getType() == bof.TYPE_WORKBOOK) {
45. } else if (bof.getType() == bof.TYPE_WORKSHEET) {
{% endhighlight %}

***

