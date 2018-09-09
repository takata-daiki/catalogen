# BOFRecord

***

### [Cluster 1](./1)
{% highlight java %}
325. BOFRecord bof = (BOFRecord) record;
326. if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
328. } else if (bof.getType() == BOFRecord.TYPE_CHART) {
341. } else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
{% endhighlight %}

***

### [Cluster 2](./2)
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

### [Cluster 3](./3)
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

### [Cluster 4](./4)
{% highlight java %}
104. BOFRecord bof = (BOFRecord)rec;
105. switch (bof.getType()) {
113.      throw new RuntimeException("Unsupported model type "+bof.getType());
{% endhighlight %}

***

