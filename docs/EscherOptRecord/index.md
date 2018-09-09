# EscherOptRecord

***

### [Cluster 1](./1)
{% highlight java %}
81. EscherOptRecord opt = new EscherOptRecord();
91. opt.setRecordId( EscherOptRecord.RECORD_ID );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
277. public static EscherProperty getEscherProperty(EscherOptRecord opt, int propId){
278.    if(opt != null) for ( Iterator iterator = opt.getEscherProperties().iterator(); iterator.hasNext(); )
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
516. EscherOptRecord escherOptRecord = (EscherOptRecord) escherRecord;
517. for(EscherProperty property : escherOptRecord.getEscherProperties()){
{% endhighlight %}

***

