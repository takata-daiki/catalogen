# EscherDgRecord @Cluster 1

***

### [DrawingManager2.java](https://searchcode.com/codesearch/view/15642353/)
{% highlight java %}
97. EscherDgRecord dg = getDrawingGroup(drawingGroupId);
98. dg.setNumShapes( dg.getNumShapes() + 1 );
99. dg.setLastMSOSPID( result );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
716. EscherDgRecord dg;
725. drawingGroupId = dg.getDrawingGroupId();
741. sp1.setShapeId( drawingManager.allocateShapeId(dg.getDrawingGroupId()) );
{% endhighlight %}

***

### [DrawingManager2.java](https://searchcode.com/codesearch/view/15642353/)
{% highlight java %}
109. EscherDgRecord dg = getDrawingGroup(drawingGroupId);
110. dg.setNumShapes( dg.getNumShapes() + 1 );
112. dg.setLastMSOSPID( result );
{% endhighlight %}

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
154. EscherDgRecord dg = (EscherDgRecord) Shape.getEscherChild(dgContainer, EscherDgRecord.RECORD_ID);
156. dg.setOptions((short)(dgId << 4));
175. dg.setNumShapes(1);
{% endhighlight %}

***

