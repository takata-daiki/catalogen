# XSLFSimpleShape @Cluster 6

***

### [RenderableShape.java](https://searchcode.com/codesearch/view/97406799/)
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

