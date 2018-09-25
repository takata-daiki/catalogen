# HSSFPolygon @Cluster 1 (anchor, foreground, top)

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
> creates a new . @ param anchor the client anchor describes how this group is attached to the sheet . 
{% highlight java %}
130. HSSFPolygon shape = new HSSFPolygon(null, anchor);
131. shape.anchor = anchor;
{% endhighlight %}

***

### [HSSFShapeGroup.java](https://searchcode.com/codesearch/view/15642300/)
> creates a new . @ param anchor the client anchor describes how this group is attached to the sheet . 
{% highlight java %}
116. HSSFPolygon shape = new HSSFPolygon(this, anchor);
117. shape.anchor = anchor;
{% endhighlight %}

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
> creates an empty 5 element if one does not already exist 
{% highlight java %}
402. HSSFPolygon shape = escherGroup.createPolygon(new HSSFChildAnchor(left,top,right,bottom) );
403. shape.setPolygonDrawArea(right - left, bottom - top);
404. shape.setPoints(addToAll(xPoints, -left), addToAll(yPoints, -top));
405. shape.setLineStyleColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
406. shape.setFillColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
{% endhighlight %}

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
> creates a new is object to the list of in the right @ param p 
{% highlight java %}
257. HSSFPolygon shape = escherGroup.createPolygon(new HSSFChildAnchor(left,top,right,bottom) );
258. shape.setPolygonDrawArea(right - left, bottom - top);
259. shape.setPoints(addToAll(xPoints, -left), addToAll(yPoints, -top));
260. shape.setLineStyleColor(foreground.getRed(), foreground.getGreen(), foreground.getBlue());
261. shape.setLineWidth(0);
262. shape.setNoFill(true);
{% endhighlight %}

***

