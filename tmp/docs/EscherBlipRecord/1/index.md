# EscherBlipRecord @Cluster 1 (blip, header, tikainputstream)

***

### [DefaultEscherRecordFactory.java](https://searchcode.com/codesearch/view/15642620/)
> sets the 
{% highlight java %}
99. EscherBlipRecord r;
116. r.setRecordId( header.getRecordId() );
117. r.setOptions( header.getOptions() );
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
> @ since 3 . 1 7 beta 1 
{% highlight java %}
531. EscherBlipRecord blip = ((EscherBSERecord) escherRecord).getBlipRecord();
540.    switch (blip.getRecordId()) {
563.    TikaInputStream stream = TikaInputStream.get(blip.getPicturedata());
{% endhighlight %}

***

