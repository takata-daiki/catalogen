# SSTRecord

***

### [Cluster 1](./1)
{% highlight java %}
178. private SSTRecord sstRecord;
374.         UnicodeString unicode = sstRecord.getString(sst.getSSTIndex());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
103. protected SSTRecord        sst         = null;
703.   return sst.addString(string);
715.     UnicodeString retval = sst.getString(str);
798.                 record = sst.createExtSSTRecord(sstPos + offset);
837.                 retval += sst.calcExtSSTRecordSize();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
273. SSTRecord other = (SSTRecord) o;
275. return ( ( field_1_num_strings == other
276.         .field_1_num_strings ) && ( field_2_num_unique_strings == other
278.         .equals( other.field_3_strings ) );
{% endhighlight %}

***

