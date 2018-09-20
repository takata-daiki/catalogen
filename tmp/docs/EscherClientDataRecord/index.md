# EscherClientDataRecord

***

## [Cluster 1](./1)
1 results
> @ throws nullpointerexception if cell 1 is null ( fixed position ) @ see org . apache . poi . ss . usermodel . clientanchor # or ( short ) 
{% highlight java %}
60. protected EscherClientDataRecord _clientData;
358.         byte[] data = _clientData.getRemainingData();
374.         _clientData.setRemainingData(out.toByteArray());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
383. EscherClientDataRecord cldata = new EscherClientDataRecord();
384. cldata.setOptions((short)0xF);
426. cldata.setRemainingData(out.toByteArray());
{% endhighlight %}

***

## [Cluster 3](./3)
6 results
> create a new ctworkbook with all values set to default 
{% highlight java %}
84. EscherClientDataRecord clientData = new EscherClientDataRecord();
139. clientData.setRecordId( EscherClientDataRecord.RECORD_ID );
140. clientData.setOptions( (short) 0x0000 );
{% endhighlight %}

***

