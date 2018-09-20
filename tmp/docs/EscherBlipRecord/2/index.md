# EscherBlipRecord @Cluster 2

***

### [EscherBSERecord.java](https://searchcode.com/codesearch/view/97383903/)
{% highlight java %}
55. private EscherBlipRecord field_12_blipRecord;
79.         bytesRead = field_12_blipRecord.fillFields( data, pos + 36, recordFactory );
86.     return bytesRemaining + 8 + 36 + (field_12_blipRecord == null ? 0 : field_12_blipRecord.getRecordSize()) ;
99.     int blipSize = field_12_blipRecord == null ? 0 : field_12_blipRecord.getRecordSize();
118.         bytesWritten = field_12_blipRecord.serialize( offset + 44, data, new NullEscherSerializationListener() );
132.         field_12_size = field_12_blipRecord.getRecordSize();
{% endhighlight %}

***

