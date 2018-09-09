# FileInformationBlock

***

### [Cluster 1](./1)
{% highlight java %}
121. private PlexOfCps readPLCF( byte[] tableStream, FileInformationBlock fib,
124.     int start = fib.getFieldsPlcfOffset( documentPart );
125.     int length = fib.getFieldsPlcfLength( documentPart );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
200. void writeTo( FileInformationBlock fib, HWPFOutputStream outputStream )
204.     fib.setFcPlfLfo( offset );
219.     fib.setLcbPlfLfo( outputStream.getOffset() - offset );
{% endhighlight %}

***

