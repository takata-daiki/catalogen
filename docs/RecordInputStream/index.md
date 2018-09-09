# RecordInputStream

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
294. RecordInputStream recStream = new RecordInputStream(in);
296. while (recStream.hasNextRecord()) {
297.   recStream.nextRecord();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
138. public static Ptg createPtg(RecordInputStream in)
140.     byte id     = in.readByte();
{% endhighlight %}

***

