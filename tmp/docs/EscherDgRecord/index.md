# EscherDgRecord

***

### [Cluster 1](./1)
{% highlight java %}
68. EscherDgRecord dg = new EscherDgRecord();
69. dg.setRecordId( EscherDgRecord.RECORD_ID );
71. dg.setOptions( (short) ( dgId << 4 ) );
72. dg.setNumShapes( 0 );
73. dg.setLastMSOSPID( -1 );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
716. EscherDgRecord dg;
725. drawingGroupId = dg.getDrawingGroupId();
741. sp1.setShapeId( drawingManager.allocateShapeId(dg.getDrawingGroupId()) );
{% endhighlight %}

***

