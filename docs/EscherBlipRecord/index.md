# EscherBlipRecord

***

### [Cluster 1](./1)
{% highlight java %}
65. private EscherBlipRecord blip;
84.     return blip.getPicturedata();
94.     switch (blip.getOptions() & FORMAT_MASK)
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
99. EscherBlipRecord r;
116. r.setRecordId( header.getRecordId() );
117. r.setOptions( header.getOptions() );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
531. EscherBlipRecord blip = ((EscherBSERecord) escherRecord).getBlipRecord();
540.    switch (blip.getRecordId()) {
563.    TikaInputStream stream = TikaInputStream.get(blip.getPicturedata());
{% endhighlight %}

***

