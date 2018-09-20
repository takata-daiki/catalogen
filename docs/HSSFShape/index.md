# HSSFShape

***

## [Cluster 1](./1)
2 results
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param 
{% highlight java %}
197. HSSFShape shape = (HSSFShape) iterator.next();
198. count += shape.countOfAllChildren();
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
77. HSSFShape shape = hssfShape;
96. HSSFAnchor userAnchor = shape.getAnchor();
{% endhighlight %}

***

## [Cluster 3](./3)
2 results
> set the contents of this shape to be a copy of the source shape . < p > the 0 is specified in points . positive values will cause the to and it to be or null if it is not a . @ param value the it ' s id in the byte array to be written . 
{% highlight java %}
77. HSSFShape shape = hssfShape;
93. EscherRecord anchor = createAnchor( shape.getAnchor() );
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
234. for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {  
235.     HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();  
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> @ param shape - @ param shape 
{% highlight java %}
59. public static AbstractShape createShape( HSSFShape hssfShape, int shapeId )
98.     if (hssfShape.getParent() != null)
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> this comment could not be generated...
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

