# EscherClientDataRecord

***

### [Cluster 1](./1)
{% highlight java %}
82. EscherClientDataRecord clientData = new EscherClientDataRecord();
94. clientData.setRecordId( EscherClientDataRecord.RECORD_ID );
95. clientData.setOptions( (short) 0x0000 );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
60. protected EscherClientDataRecord _clientData;
358.         byte[] data = _clientData.getRemainingData();
374.         _clientData.setRemainingData(out.toByteArray());
{% endhighlight %}

***

