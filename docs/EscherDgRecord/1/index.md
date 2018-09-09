# EscherDgRecord @Cluster 1

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
154. EscherDgRecord dg = (EscherDgRecord) Shape.getEscherChild(dgContainer, EscherDgRecord.RECORD_ID);
156. dg.setOptions((short)(dgId << 4));
175. dg.setNumShapes(1);
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
269. EscherDgRecord dg = _container.getPPDrawing().getEscherDgRecord();
277.     if (c.getDrawingGroupId() == dg.getDrawingGroupId() && c.getNumShapeIdsUsed() != 1024)
281.         dg.setNumShapes( dg.getNumShapes() + 1 );
282.         dg.setLastMSOSPID( result );
290. dgg.addCluster( dg.getDrawingGroupId(), 0, false );
292. dg.setNumShapes( dg.getNumShapes() + 1 );
294. dg.setLastMSOSPID( result );
{% endhighlight %}

***

### [DrawingManager2.java](https://searchcode.com/codesearch/view/15642353/)
{% highlight java %}
68. EscherDgRecord dg = new EscherDgRecord();
69. dg.setRecordId( EscherDgRecord.RECORD_ID );
71. dg.setOptions( (short) ( dgId << 4 ) );
72. dg.setNumShapes( 0 );
73. dg.setLastMSOSPID( -1 );
{% endhighlight %}

***

### [DrawingManager2.java](https://searchcode.com/codesearch/view/15642353/)
{% highlight java %}
97. EscherDgRecord dg = getDrawingGroup(drawingGroupId);
98. dg.setNumShapes( dg.getNumShapes() + 1 );
99. dg.setLastMSOSPID( result );
{% endhighlight %}

***

### [DrawingManager2.java](https://searchcode.com/codesearch/view/15642353/)
{% highlight java %}
109. EscherDgRecord dg = getDrawingGroup(drawingGroupId);
110. dg.setNumShapes( dg.getNumShapes() + 1 );
112. dg.setLastMSOSPID( result );
{% endhighlight %}

***

### [DrawingManager.java](https://searchcode.com/codesearch/view/15642363/)
{% highlight java %}
66. EscherDgRecord dg = new EscherDgRecord();
67. dg.setRecordId( EscherDgRecord.RECORD_ID );
69. dg.setOptions( (short) ( dgId << 4 ) );
70. dg.setNumShapes( 0 );
71. dg.setLastMSOSPID( -1 );
{% endhighlight %}

***

### [DrawingManager.java](https://searchcode.com/codesearch/view/15642363/)
{% highlight java %}
86. EscherDgRecord dg = (EscherDgRecord) dgMap.get(new Short(drawingGroupId));
87. int lastShapeId = dg.getLastMSOSPID();
116.         if (dg.getLastMSOSPID() == -1)
123.             newShapeId = dg.getLastMSOSPID() + 1;
137. dg.setLastMSOSPID(newShapeId);
139. dg.incrementShapeCount();
{% endhighlight %}

***
