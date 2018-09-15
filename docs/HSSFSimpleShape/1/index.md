# HSSFSimpleShape @Cluster 1

***

### [AbstractShape.java](https://searchcode.com/codesearch/view/15642354/)
{% highlight java %}
76. HSSFSimpleShape simpleShape = (HSSFSimpleShape) hssfShape;
77. switch ( simpleShape.getShapeType() )
{% endhighlight %}

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
{% highlight java %}
235. HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor(x1, y1, x2, y2) );
236. shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
237. shape.setLineWidth(width);
238. shape.setLineStyleColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
{% endhighlight %}

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
{% highlight java %}
243. HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor(x,y,x+width,y+height) );
244. shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_OVAL);
245. shape.setLineWidth(0);
246. shape.setLineStyleColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
247. shape.setNoFill(true);
{% endhighlight %}

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
{% highlight java %}
369. HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor( x, y, x + width, y + height ) );
370. shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_OVAL);
371. shape.setLineStyle(HSSFShape.LINESTYLE_NONE);
372. shape.setFillColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
373. shape.setLineStyleColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
{% endhighlight %}

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
{% highlight java %}
433. HSSFSimpleShape shape = escherGroup.createShape(new HSSFChildAnchor( x, y, x + width, y + height ) );
434. shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_RECTANGLE);
435. shape.setLineStyle(HSSFShape.LINESTYLE_NONE);
436. shape.setFillColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
437. shape.setLineStyleColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
{% endhighlight %}

***

### [HSSFShapeGroup.java](https://searchcode.com/codesearch/view/15642300/)
{% highlight java %}
88. HSSFSimpleShape shape = new HSSFSimpleShape(this, anchor);
89. shape.anchor = anchor;
{% endhighlight %}

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
{% highlight java %}
97. HSSFSimpleShape shape = new HSSFSimpleShape(null, anchor);
98. shape.anchor = anchor;
{% endhighlight %}

***

