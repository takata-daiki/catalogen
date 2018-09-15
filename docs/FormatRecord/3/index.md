# FormatRecord @Cluster 3

***

### [HSSFDataFormat.java](https://searchcode.com/codesearch/view/15642305/)
{% highlight java %}
126. FormatRecord r = (FormatRecord) i.next();
127. if ( formats.size() < r.getIndexCode() + 1 )
129.     formats.setSize( r.getIndexCode() + 1 );
131. formats.set( r.getIndexCode(), r.getFormatString() );
{% endhighlight %}

***

