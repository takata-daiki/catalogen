# EscherRecord @Cluster 5

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
236. EscherRecord escherRecord = iterator.next();
237. escherRecord.display(w, indent + 1);
{% endhighlight %}

***

### [Picture.java](https://searchcode.com/codesearch/view/97384428/)
{% highlight java %}
560. EscherRecord escherRecord = _blipRecords.get( 0 );
561. switch ( escherRecord.getRecordId() )
{% endhighlight %}

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
80. EscherRecord child = recordFactory.createRecord(data, offset);
81. int childBytesWritten = child.fillFields(data, offset, recordFactory);
{% endhighlight %}

***

### [UnknownEscherRecord.java](https://searchcode.com/codesearch/view/97383865/)
{% highlight java %}
65. EscherRecord child = recordFactory.createRecord( data, offset );
66. int childBytesWritten = child.fillFields( data, offset, recordFactory );
{% endhighlight %}

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
111. EscherRecord r = iterator.next();
112. pos += r.serialize(pos, data, listener );
{% endhighlight %}

***

### [UnknownEscherRecord.java](https://searchcode.com/codesearch/view/97383865/)
{% highlight java %}
86. for (EscherRecord r : _childRecords) {
87.     remainingBytes += r.getRecordSize();
{% endhighlight %}

***

### [SimpleShape.java](https://searchcode.com/codesearch/view/97394265/)
{% highlight java %}
93. EscherRecord anchor;
104.     anchor.fillFields(header, 0, null);
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
529. for(EscherRecord escherRecord : records) {
574.    findPictures(escherRecord.getChildRecords());
{% endhighlight %}

***

### [UnknownEscherRecord.java](https://searchcode.com/codesearch/view/97383865/)
{% highlight java %}
92. for (EscherRecord r : _childRecords) {
93.     pos += r.serialize(pos, data, listener );
{% endhighlight %}

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
103. EscherRecord r = iterator.next();
104. remainingBytes += r.getRecordSize();
{% endhighlight %}

***

### [UnknownEscherRecord.java](https://searchcode.com/codesearch/view/97383865/)
{% highlight java %}
129. for (EscherRecord record : _childRecords) {
130.     children.append( record.toString() );
{% endhighlight %}

***

### [UnknownEscherRecord.java](https://searchcode.com/codesearch/view/97383865/)
{% highlight java %}
157. EscherRecord record = iterator.next();
158. builder.append(record.toXml(tab+"\t"));
{% endhighlight %}

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
123. EscherRecord r = iterator.next();
124. childRecordsSize += r.getRecordSize();
{% endhighlight %}

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
295. EscherRecord record = iterator.next();
296. builder.append(record.toXml(tab+"\t"));
{% endhighlight %}

***

### [SimpleShape.java](https://searchcode.com/codesearch/view/97394265/)
{% highlight java %}
347. EscherRecord r = Shape.getEscherChild(getSpContainer(), EscherClientDataRecord.RECORD_ID);
351.     byte[] data = r.serialize();
353.     r.fillFields(data, 0, new DefaultEscherRecordFactory());
{% endhighlight %}

***

### [DefaultEscherRecordFactory.java](https://searchcode.com/codesearch/view/97383906/)
{% highlight java %}
98. EscherRecord escherRecord = null;
107. escherRecord.setRecordId(recordId);
108. escherRecord.setOptions(options);
{% endhighlight %}

***

