# HSSFShape

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
77. HSSFShape shape = hssfShape;
96. HSSFAnchor userAnchor = shape.getAnchor();
{% endhighlight %}

***

## [Cluster 2](./2)
2 results
> code comments is here.
{% highlight java %}
77. HSSFShape shape = hssfShape;
93. EscherRecord anchor = createAnchor( shape.getAnchor() );
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
234. for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {  
235.     HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();  
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
59. public static AbstractShape createShape( HSSFShape hssfShape, int shapeId )
98.     if (hssfShape.getParent() != null)
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> code comments is here.
{% highlight java %}
137. protected int addStandardOptions( HSSFShape shape, EscherOptRecord opt )
141.     if ( shape.isNoFill() )
150.     opt.addEscherProperty( new EscherRGBProperty( EscherProperties.FILL__FILLCOLOR, shape.getFillColor() ) );
152.     opt.addEscherProperty( new EscherRGBProperty( EscherProperties.LINESTYLE__COLOR, shape.getLineStyleColor() ) );
154.     if (shape.getLineWidth() != HSSFShape.LINEWIDTH_DEFAULT)
156.         opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.LINESTYLE__LINEWIDTH, shape.getLineWidth()));
159.     if (shape.getLineStyle() != HSSFShape.LINESTYLE_SOLID)
161.         opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.LINESTYLE__LINEDASHING, shape.getLineStyle()));
163.         if (shape.getLineStyle() == HSSFShape.LINESTYLE_NONE)
{% endhighlight %}

***

