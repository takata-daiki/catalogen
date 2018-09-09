# HSSFShapeGroup

***

### [Cluster 1](./1)
{% highlight java %}
75. HSSFShapeGroup group = new HSSFShapeGroup(this, anchor);
76. group.anchor = anchor;
{% endhighlight %}

***

### [Cluster 2](./2)
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

