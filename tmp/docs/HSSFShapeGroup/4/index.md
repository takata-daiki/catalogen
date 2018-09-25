# HSSFShapeGroup @Cluster 4 (eschergroup, hssfchildanchor, new)

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
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

