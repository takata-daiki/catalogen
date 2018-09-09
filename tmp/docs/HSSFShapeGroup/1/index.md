# HSSFShapeGroup @Cluster 1

***

### [HSSFShapeGroup.java](https://searchcode.com/codesearch/view/15642300/)
{% highlight java %}
75. HSSFShapeGroup group = new HSSFShapeGroup(this, anchor);
76. group.anchor = anchor;
{% endhighlight %}

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
{% highlight java %}
87. private HSSFShapeGroup escherGroup;
235.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor(x1, y1, x2, y2) );
243.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor(x,y,x+width,y+height) );
257.     HSSFPolygon shape = escherGroup.createPolygon(new HSSFChildAnchor(left,top,right,bottom) );
311.     HSSFTextbox textbox = escherGroup.createTextbox( new HSSFChildAnchor( x, y, x + width, y + height ) );
369.     HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor( x, y, x + width, y + height ) );
{% endhighlight %}

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
{% highlight java %}
81. HSSFShapeGroup group = new HSSFShapeGroup(null, anchor);
82. group.anchor = anchor;
{% endhighlight %}

***

