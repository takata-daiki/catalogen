# RecordInputStream @Cluster 2 (in, readshort, recordinputstream)

***

### [Ref3DPtg.java](https://searchcode.com/codesearch/view/15642577/)
> invokes the delegate ' s < code > mark ( int ) < / code > method . @ param as the stream to read from 
{% highlight java %}
75. public Ref3DPtg(RecordInputStream in) {
76.     field_1_index_extern_sheet = in.readShort();
77.     field_2_row          = in.readShort();
78.     field_3_column        = in.readShort();
{% endhighlight %}

***

### [AreaPtg.java](https://searchcode.com/codesearch/view/15642562/)
> invokes the delegate ' s < code > mark ( int ) < / code > method . @ param as the start last the contents to read . 
{% highlight java %}
100. public AreaPtg(RecordInputStream in)
102.     field_1_first_row    = in.readShort();
103.     field_2_last_row     = in.readShort();
104.     field_3_first_column = in.readShort();
105.     field_4_last_column  = in.readShort();
{% endhighlight %}

***

### [ArrayPtg.java](https://searchcode.com/codesearch/view/15642537/)
> this method is being used by the xml signature service engine during pre - sign [ 0 . . . 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 4 @ param . @ param . 
{% highlight java %}
87. public ArrayPtg(RecordInputStream in)
89.   field_1_reserved = in.readByte();
90.   field_2_reserved = in.readByte();
91.   field_3_reserved = in.readByte();
92.   field_4_reserved = in.readByte();
93.   field_5_reserved = in.readByte();
94.   field_6_reserved = in.readByte();
95.   field_7_reserved = in.readByte();
{% endhighlight %}

***

