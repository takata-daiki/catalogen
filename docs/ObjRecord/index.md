# ObjRecord

***

### [Cluster 1](./1)
{% highlight java %}
124. ObjRecord obj = new ObjRecord();
134. obj.addSubRecord( c );
135. obj.addSubRecord( e );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
210. ObjRecord rec = new ObjRecord();
213.     rec.addSubRecord(( (Record) iterator.next() ).clone());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
1041. ObjRecord obj = (ObjRecord)rec;
1042. SubRecord sub = (SubRecord)obj.getSubRecords().get(0);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
74. ObjRecord obj = getObjRecord();
75. List records = obj.getSubRecords();
90. obj.addSubRecord(cmoIdx+1, u);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
62. private ObjRecord record;
89.     Iterator subRecordIter = record.getSubRecords().iterator();
{% endhighlight %}

***

