# XSLFSimpleShape

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
177. public boolean fetch(XSLFSimpleShape shape) {
178.     CTShapeProperties pr = shape.getSpPr();
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
39. /* package */XSLFShadow(CTOuterShadowEffect shape, XSLFSimpleShape parentShape) {
40.     super(shape, parentShape.getSheet());
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
671. XSLFSimpleShape s = (XSLFSimpleShape)sh;
673. Color srsSolidFill = s.getFillColor();
683.     String relId = getSheet().importBlip(blipId, s.getSheet().getPackagePart());
687. Color srcLineColor = s.getLineColor();
693. double srcLineWidth = s.getLineWidth();
699. LineDash srcLineDash = s.getLineDash();
705. LineCap srcLineCap = s.getLineCap();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
37. private XSLFSimpleShape _parent;
48.     double shapeRotation = _parent.getRotation();
49.     if(_parent.getFlipVertical()){
89.     return _parent.getAnchor();
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> code comments is here.
{% highlight java %}
78. private XSLFSimpleShape _shape;
105.     XSLFTheme theme = _shape.getSheet().getTheme();
216.         double rotation = _shape.getRotation();
340.         paint = selectPaint(graphics, obj, phClr, _shape.getSheet().getPackagePart());
368.     _shape.fetchShapeProperty(fetcher);
373.         CTShapeStyle style = _shape.getSpStyle();
384.             XSLFSheet sheet = _shape.getSheet();
433.                 XSLFTheme theme = _shape.getSheet().getTheme();
483.     float lineWidth = (float) _shape.getLineWidth();
486.     LineDash lineDash = _shape.getLineDash();
494.     LineCap lineCap = _shape.getLineCap();
521.     XSLFShadow shadow = _shape.getShadow();
544.     _shape.drawContent(graphics);
558.     CustomGeometry geom = _shape.getGeometry();
581.                 CTPresetGeometry2D prst = _shape.getSpPr().getPrstGeom();
622.     Rectangle2D anchor = _shape.getAnchor();
{% endhighlight %}

***

