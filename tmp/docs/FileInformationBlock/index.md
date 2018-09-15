# FileInformationBlock

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
121. private PlexOfCps readPLCF( byte[] tableStream, FileInformationBlock fib,
124.     int start = fib.getFieldsPlcfOffset( documentPart );
125.     int length = fib.getFieldsPlcfLength( documentPart );
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
83. public void writeListDataTo( FileInformationBlock fib,
87.     fib.setFcPlfLst( startOffset );
113.     fib.setLcbPlfLst( tableStream.getOffset() - startOffset );
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
200. void writeTo( FileInformationBlock fib, HWPFOutputStream outputStream )
204.     fib.setFcPlfLfo( offset );
219.     fib.setLcbPlfLfo( outputStream.getOffset() - offset );
{% endhighlight %}

***

