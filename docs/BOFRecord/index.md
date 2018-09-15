# BOFRecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
325. BOFRecord bof = (BOFRecord) record;
326. if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
328. } else if (bof.getType() == BOFRecord.TYPE_CHART) {
341. } else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
130. BOFRecord br = (BOFRecord) record;
131. if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
{% endhighlight %}

***

## [Cluster 3](./3)
2 results
> code comments is here.
{% highlight java %}
101. final BOFRecord bof = (BOFRecord) record;
102. if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
107. else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
109.         logger.info("recordsize = " + bof.getRecordSize() + 
111.                 bof.getRequiredVersion());
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
104. BOFRecord bof = (BOFRecord)rec;
105. switch (bof.getType()) {
113.      throw new RuntimeException("Unsupported model type "+bof.getType());
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> code comments is here.
{% highlight java %}
1411. BOFRecord retval = new BOFRecord();
1413. retval.setVersion(( short ) 0x600);
1414. retval.setType(( short ) 0x010);
1417. retval.setBuild(( short ) 0x0dbb);
1418. retval.setBuildYear(( short ) 1996);
1419. retval.setHistoryBitMask(0xc1);
1420. retval.setRequiredVersion(0x6);
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> code comments is here.
{% highlight java %}
853. BOFRecord retval = new BOFRecord();
855. retval.setVersion(( short ) 0x600);
856. retval.setType(( short ) 5);
857. retval.setBuild(( short ) 0x10d3);
860. retval.setBuildYear(( short ) 1996);
861. retval.setHistoryBitMask(0x41);   // was c1 before verify
862. retval.setRequiredVersion(0x6);
{% endhighlight %}

***

## [Cluster 7](./7)
1 results
> code comments is here.
{% highlight java %}
331. BOFRecord rec = new BOFRecord();
332. rec.field_1_version = field_1_version;
333. rec.field_2_type = field_2_type;
334. rec.field_3_build = field_3_build;
335. rec.field_4_year = field_4_year;
336. rec.field_5_history = field_5_history;
337. rec.field_6_rversion = field_6_rversion;
{% endhighlight %}

***

