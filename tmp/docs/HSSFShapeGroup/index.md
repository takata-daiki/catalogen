# HSSFShapeGroup

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
75. HSSFShapeGroup group = new HSSFShapeGroup(this, anchor);
76. group.anchor = anchor;
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
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

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
87. private HSSFShapeGroup escherGroup;
235.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor(x1, y1, x2, y2) );
243.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor(x,y,x+width,y+height) );
257.     HSSFPolygon shape = escherGroup.createPolygon(new HSSFChildAnchor(left,top,right,bottom) );
311.     HSSFTextbox textbox = escherGroup.createTextbox( new HSSFChildAnchor( x, y, x + width, y + height ) );
369.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor( x, y, x + width, y + height ) );
{% endhighlight %}

***

