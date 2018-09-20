# EscherBlipRecord @Cluster 1

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
531. EscherBlipRecord blip = ((EscherBSERecord) escherRecord).getBlipRecord();
540.    switch (blip.getRecordId()) {
563.    TikaInputStream stream = TikaInputStream.get(blip.getPicturedata());
{% endhighlight %}

***

### [DefaultEscherRecordFactory.java](https://searchcode.com/codesearch/view/97383906/)
{% highlight java %}
77. EscherBlipRecord r;
92. r.setRecordId( recordId );
93. r.setOptions( options );
{% endhighlight %}

***

### [HSSFPictureData.java](https://searchcode.com/codesearch/view/15642299/)
{% highlight java %}
65. private EscherBlipRecord blip;
84.     return blip.getPicturedata();
94.     switch (blip.getOptions() & FORMAT_MASK)
{% endhighlight %}

***

### [DefaultEscherRecordFactory.java](https://searchcode.com/codesearch/view/15642620/)
{% highlight java %}
99. EscherBlipRecord r;
116. r.setRecordId( header.getRecordId() );
117. r.setOptions( header.getOptions() );
{% endhighlight %}

***

