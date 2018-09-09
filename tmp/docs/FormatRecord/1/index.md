# FormatRecord @Cluster 1

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
2021.   FormatRecord r = (FormatRecord)iterator.next();
2022.   if (r.getFormatString().equals(format)) {
2023. return r.getIndexCode();
{% endhighlight %}

***

### [HSSFDataFormat.java](https://searchcode.com/codesearch/view/15642305/)
{% highlight java %}
126. FormatRecord r = (FormatRecord) i.next();
127. if ( formats.size() < r.getIndexCode() + 1 )
129.     formats.setSize( r.getIndexCode() + 1 );
131. formats.set( r.getIndexCode(), r.getFormatString() );
{% endhighlight %}

***

