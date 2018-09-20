# EscherBlipRecord

***

## [Cluster 1](./1)
4 results
> sets the 
{% highlight java %}
99. EscherBlipRecord r;
116. r.setRecordId( header.getRecordId() );
117. r.setOptions( header.getOptions() );
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
55. private EscherBlipRecord field_12_blipRecord;
79.         bytesRead = field_12_blipRecord.fillFields( data, pos + 36, recordFactory );
86.     return bytesRemaining + 8 + 36 + (field_12_blipRecord == null ? 0 : field_12_blipRecord.getRecordSize()) ;
99.     int blipSize = field_12_blipRecord == null ? 0 : field_12_blipRecord.getRecordSize();
118.         bytesWritten = field_12_blipRecord.serialize( offset + 44, data, new NullEscherSerializationListener() );
132.         field_12_size = field_12_blipRecord.getRecordSize();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
82. private EscherBlipRecord field_12_blipRecord;
116.         bytesRead = field_12_blipRecord.fillFields( data, pos + 36, recordFactory );
133.     return bytesRemaining + 8 + 36 + (field_12_blipRecord == null ? 0 : field_12_blipRecord.getRecordSize()) ;
156.     int blipSize = field_12_blipRecord == null ? 0 : field_12_blipRecord.getRecordSize();
175.         bytesWritten = field_12_blipRecord.serialize( offset + 44, data, new NullEscherSerializationListener() );
193.     return 8 + 1 + 1 + 16 + 2 + 4 + 4 + 4 + 1 + 1 + 1 + 1 + field_12_blipRecord.getRecordSize() + (remainingData == null ? 0 : remainingData.length);
{% endhighlight %}

***

