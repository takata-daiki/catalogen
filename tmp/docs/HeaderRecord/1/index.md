# HeaderRecord @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1636. HeaderRecord retval = new HeaderRecord();
1638. retval.setHeaderLength(( byte ) 0);
1639. retval.setHeader(null);
{% endhighlight %}

***

### [HSSFHeader.java](https://searchcode.com/codesearch/view/15642332/)
{% highlight java %}
60. HeaderRecord headerRecord;
73.     String head = headerRecord.getHeader();
190.     headerRecord.setHeader( "&C" + ( center == null ? "" : center ) +
193.     headerRecord.setHeaderLength( (byte) headerRecord.getHeader().length() );
{% endhighlight %}

***

### [HSSFHeader.java](https://searchcode.com/codesearch/view/15642332/)
{% highlight java %}
70. protected HSSFHeader( HeaderRecord headerRecord )
73.     String head = headerRecord.getHeader();
{% endhighlight %}

***

