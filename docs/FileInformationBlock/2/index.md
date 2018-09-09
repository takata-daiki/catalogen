# FileInformationBlock @Cluster 2

***

### [ListTables.java](https://searchcode.com/codesearch/view/97384153/)
{% highlight java %}
83. public void writeListDataTo( FileInformationBlock fib,
87.     fib.setFcPlfLst( startOffset );
113.     fib.setLcbPlfLst( tableStream.getOffset() - startOffset );
{% endhighlight %}

***

### [PlfLfo.java](https://searchcode.com/codesearch/view/97384147/)
{% highlight java %}
200. void writeTo( FileInformationBlock fib, HWPFOutputStream outputStream )
204.     fib.setFcPlfLfo( offset );
219.     fib.setLcbPlfLfo( outputStream.getOffset() - offset );
{% endhighlight %}

***

