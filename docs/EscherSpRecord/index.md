# EscherSpRecord

***

### [Cluster 1](./1)
{% highlight java %}
462. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
463. if(spRecord != null) spRecord.setShapeId(id);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
80. EscherSpRecord sp = new EscherSpRecord();
86. sp.setRecordId( EscherSpRecord.RECORD_ID );
88. sp.setOptions( (short) ( ( shapeType << 4 ) | 0x2 ) );
89. sp.setShapeId( shapeId );
90. sp.setFlags( EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
83. EscherSpRecord sp = new EscherSpRecord();
86. sp.setFlags(flags);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
225. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
226. return (spRecord.getFlags()& EscherSpRecord.FLAG_FLIPHORIZ) != 0;
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
176. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
177. int flags = spRecord.getFlags();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
97. EscherSpRecord sp = shape.getSpContainer().getChildById(EscherSpRecord.RECORD_ID);
99.     sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_CHILD);
{% endhighlight %}

***

