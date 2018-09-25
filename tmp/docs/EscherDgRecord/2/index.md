# EscherDgRecord @Cluster 2 (dg, escherdgrecord, setlastmsospid)

***

### [DrawingManager.java](https://searchcode.com/codesearch/view/15642363/)
> sets whether the rowcolheadings are shown in a viewer @ param show whether to show rowcolheadings or not 
{% highlight java %}
66. EscherDgRecord dg = new EscherDgRecord();
67. dg.setRecordId( EscherDgRecord.RECORD_ID );
69. dg.setOptions( (short) ( dgId << 4 ) );
70. dg.setNumShapes( 0 );
71. dg.setLastMSOSPID( -1 );
{% endhighlight %}

***

### [DrawingManager2.java](https://searchcode.com/codesearch/view/15642353/)
> sets whether the rowcolheadings are shown in a viewer @ param show whether to show rowcolheadings or not 
{% highlight java %}
68. EscherDgRecord dg = new EscherDgRecord();
69. dg.setRecordId( EscherDgRecord.RECORD_ID );
71. dg.setOptions( (short) ( dgId << 4 ) );
72. dg.setNumShapes( 0 );
73. dg.setLastMSOSPID( -1 );
{% endhighlight %}

***

### [DrawingManager.java](https://searchcode.com/codesearch/view/15642363/)
> this method is being the same as the for a left , right on the right down , of the shape . 
{% highlight java %}
86. EscherDgRecord dg = (EscherDgRecord) dgMap.get(new Short(drawingGroupId));
87. int lastShapeId = dg.getLastMSOSPID();
116.         if (dg.getLastMSOSPID() == -1)
123.             newShapeId = dg.getLastMSOSPID() + 1;
137. dg.setLastMSOSPID(newShapeId);
139. dg.incrementShapeCount();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
> does not have a data column at the given column index . 
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

