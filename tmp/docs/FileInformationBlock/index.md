# FileInformationBlock

***

## [Cluster 1](./1)
1 results
> this comment could not be generated...
{% highlight java %}
133. private int savePlex( FileInformationBlock fib, FieldsDocumentPart part,
139.         fib.setFieldsPlcfOffset( part, outputStream.getOffset() );
140.         fib.setFieldsPlcfLength( part, 0 );
151.     fib.setFieldsPlcfOffset( part, start );
152.     fib.setFieldsPlcfLength( part, length );
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
121. private PlexOfCps readPLCF( byte[] tableStream, FileInformationBlock fib,
124.     int start = fib.getFieldsPlcfOffset( documentPart );
125.     int length = fib.getFieldsPlcfLength( documentPart );
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> this comment could not be generated...
{% highlight java %}
83. public void writeListDataTo( FileInformationBlock fib,
87.     fib.setFcPlfLst( startOffset );
113.     fib.setLcbPlfLst( tableStream.getOffset() - startOffset );
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
200. void writeTo( FileInformationBlock fib, HWPFOutputStream outputStream )
204.     fib.setFcPlfLfo( offset );
219.     fib.setLcbPlfLfo( outputStream.getOffset() - offset );
{% endhighlight %}

***

