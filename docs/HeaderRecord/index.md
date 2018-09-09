# HeaderRecord

***

### [Cluster 1](./1)
{% highlight java %}
60. HeaderRecord headerRecord;
73.     String head = headerRecord.getHeader();
190.     headerRecord.setHeader( "&C" + ( center == null ? "" : center ) +
193.     headerRecord.setHeaderLength( (byte) headerRecord.getHeader().length() );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
229. HeaderRecord rec = new HeaderRecord();
230. rec.field_1_header_len = field_1_header_len;
231. rec.field_2_reserved = field_2_reserved;
232. rec.field_3_unicode_flag = field_3_unicode_flag;
233. rec.field_4_header = field_4_header;
{% endhighlight %}

***

