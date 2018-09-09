# RecordInputStream @Cluster 2

***

### [EventRecordFactory.java](https://searchcode.com/codesearch/view/15642343/)
{% highlight java %}
294. RecordInputStream recStream = new RecordInputStream(in);
296. while (recStream.hasNextRecord()) {
297.   recStream.nextRecord();
{% endhighlight %}

***

### [EventRecordFactory.java](https://searchcode.com/codesearch/view/15642343/)
{% highlight java %}
341. public static Record [] createRecord(RecordInputStream in)
349.             ( Constructor ) recordsMap.get(new Short(in.getSid()));
{% endhighlight %}

***

### [ArrayPtg.java](https://searchcode.com/codesearch/view/15642537/)
{% highlight java %}
101. public void readTokenValues(RecordInputStream in) {      
102.     token_1_columns = (short)(0x00ff & in.readByte());
103.     token_2_rows = in.readShort();
115.         byte grbit = in.readByte();
117.       token_3_arrayValues[x][y] = new Double(in.readDouble());
121.           token_3_arrayValues[x][y] = in.readUnicodeString();
{% endhighlight %}

***

