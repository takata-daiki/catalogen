# BOFRecord

***

## [Cluster 1](./1)
1 results
> this comment could not be generated...
{% highlight java %}
42. BOFRecord bof = (BOFRecord) record;
43. if (bof.getType() == bof.TYPE_WORKBOOK) {
45. } else if (bof.getType() == bof.TYPE_WORKSHEET) {
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> called by slideshow and null ( ) is only the same as the string table , but when poi only it is @ param 1 the number of columns that were no function 
{% highlight java %}
325. BOFRecord bof = (BOFRecord) record;
326. if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
328. } else if (bof.getType() == BOFRecord.TYPE_CHART) {
341. } else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> this comment could not be generated...
{% highlight java %}
130. BOFRecord br = (BOFRecord) record;
131. if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
{% endhighlight %}

***

## [Cluster 4](./4)
2 results
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
99. final BOFRecord bof = (BOFRecord) record;
100. if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
105. else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
107.         logger.info("recordsize = " + bof.getRecordSize() + 
109.                 bof.getRequiredVersion());
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> sets the { @ link what } from the specified shape . 
{% highlight java %}
104. BOFRecord bof = (BOFRecord)rec;
105. switch (bof.getType()) {
113.      throw new RuntimeException("Unsupported model type "+bof.getType());
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> set the sheet ' s comments @ param name the sheet 
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

## [Cluster 7](./7)
1 results
> set the sheet ' s comments @ param name the sheet 
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

## [Cluster 8](./8)
1 results
> year of the build that wrote this file @ see # build _ year @ return short build year of the generator of this file 
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

