# HSSFShapeGroup

***

## [Cluster 1 (anchor, group, hssfshapegroup)](./1)
1 results
> sets the shape group . 
{% highlight java %}
81. HSSFShapeGroup group = new HSSFShapeGroup(null, anchor);
82. group.anchor = anchor;
{% endhighlight %}

***

## [Cluster 2 (anchor, group, hssfshapegroup)](./2)
1 results
> sets the shape group . 
{% highlight java %}
75. HSSFShapeGroup group = new HSSFShapeGroup(this, anchor);
76. group.anchor = anchor;
{% endhighlight %}

***

## [Cluster 3 (eschercontainerrecord, shape, spgr)](./3)
1 results
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

## [Cluster 4 (eschergroup, hssfchildanchor, new)](./4)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
87. private HSSFShapeGroup escherGroup;
235.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor(x1, y1, x2, y2) );
243.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor(x,y,x+width,y+height) );
257.     HSSFPolygon shape = escherGroup.createPolygon(new HSSFChildAnchor(left,top,right,bottom) );
311.     HSSFTextbox textbox = escherGroup.createTextbox( new HSSFChildAnchor( x, y, x + width, y + height ) );
369.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor( x, y, x + width, y + height ) );
{% endhighlight %}

***

