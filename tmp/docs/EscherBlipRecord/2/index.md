# EscherBlipRecord @Cluster 2

***

### [EscherBSERecord.java](https://searchcode.com/codesearch/view/15642608/)
{% highlight java %}
82. private EscherBlipRecord field_12_blipRecord;
116.         bytesRead = field_12_blipRecord.fillFields( data, pos + 36, recordFactory );
133.     return bytesRemaining + 8 + 36 + (field_12_blipRecord == null ? 0 : field_12_blipRecord.getRecordSize()) ;
156.     int blipSize = field_12_blipRecord == null ? 0 : field_12_blipRecord.getRecordSize();
175.         bytesWritten = field_12_blipRecord.serialize( offset + 44, data, new NullEscherSerializationListener() );
193.     return 8 + 1 + 1 + 16 + 2 + 4 + 4 + 4 + 1 + 1 + 1 + 1 + field_12_blipRecord.getRecordSize() + (remainingData == null ? 0 : remainingData.length);
{% endhighlight %}

***

