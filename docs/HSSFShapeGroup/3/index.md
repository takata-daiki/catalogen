# HSSFShapeGroup @Cluster 3 (eschercontainerrecord, shape, spgr)

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> @ param < code > true < / code > if the specified record id normally appears in the range ( s ) may be a valid name . 
{% highlight java %}
629. private void convertGroup( HSSFShapeGroup shape, EscherContainerRecord escherParent, Map shapeToObj )
645.     spgr.setRectX1( shape.getX1() );
646.     spgr.setRectY1( shape.getY1() );
647.     spgr.setRectX2( shape.getX2() );
648.     spgr.setRectY2( shape.getY2() );
653.     if (shape.getAnchor() instanceof HSSFClientAnchor)
662.     anchor = ConvertAnchor.createAnchor( shape.getAnchor() );
{% endhighlight %}

***

