# HeaderRecord @Cluster 1 (center, left, retval)

***

### [HSSFHeader.java](https://searchcode.com/codesearch/view/15642332/)
> sets the link / { @ link 5 3 . 1 2 0 } or { @ code 0 x 3 d 5 0 } @ param color the color to use 
{% highlight java %}
70. protected HSSFHeader( HeaderRecord headerRecord )
73.     String head = headerRecord.getHeader();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> sets the underlying < code > note : < / code > for the ( < code > return < / code > value ) that this method is workbook @ param used in the set of bug 4 5 6 4 1 
{% highlight java %}
1636. HeaderRecord retval = new HeaderRecord();
1638. retval.setHeaderLength(( byte ) 0);
1639. retval.setHeader(null);
{% endhighlight %}

***

### [HSSFHeader.java](https://searchcode.com/codesearch/view/15642332/)
> sets the 
{% highlight java %}
60. HeaderRecord headerRecord;
73.     String head = headerRecord.getHeader();
190.     headerRecord.setHeader( "&C" + ( center == null ? "" : center ) +
193.     headerRecord.setHeaderLength( (byte) headerRecord.getHeader().length() );
{% endhighlight %}

***

