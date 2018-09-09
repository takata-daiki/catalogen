# FileInformationBlock @Cluster 1

***

### [FieldsTables.java](https://searchcode.com/codesearch/view/88635600/)
{% highlight java %}
121. private PlexOfCps readPLCF( byte[] tableStream, FileInformationBlock fib,
124.     int start = fib.getFieldsPlcfOffset( documentPart );
125.     int length = fib.getFieldsPlcfLength( documentPart );
{% endhighlight %}

***

### [FieldsTables.java](https://searchcode.com/codesearch/view/88635600/)
{% highlight java %}
133. private int savePlex( FileInformationBlock fib, FieldsDocumentPart part,
139.         fib.setFieldsPlcfOffset( part, outputStream.getOffset() );
140.         fib.setFieldsPlcfLength( part, 0 );
151.     fib.setFieldsPlcfOffset( part, start );
152.     fib.setFieldsPlcfLength( part, length );
{% endhighlight %}

***

