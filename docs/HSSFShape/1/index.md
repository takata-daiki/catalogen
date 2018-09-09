# HSSFShape @Cluster 1

***

### [AbstractShape.java](https://searchcode.com/codesearch/view/15642354/)
{% highlight java %}
59. public static AbstractShape createShape( HSSFShape hssfShape, int shapeId )
98.     if (hssfShape.getParent() != null)
{% endhighlight %}

***

### [AbstractShape.java](https://searchcode.com/codesearch/view/15642354/)
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

