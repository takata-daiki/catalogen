# HSSFTextbox @Cluster 1 (addescherproperty, escherproperties, eschersimpleproperty)

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
> sets the line end text @ param color the new color 
{% highlight java %}
145. HSSFTextbox shape = new HSSFTextbox(null, anchor);
146. shape.anchor = anchor;
{% endhighlight %}

***

### [HSSFShapeGroup.java](https://searchcode.com/codesearch/view/15642300/)
> sets the line end width @ param color the new < i > null < / code > 
{% highlight java %}
101. HSSFTextbox shape = new HSSFTextbox(this, anchor);
102. shape.anchor = anchor;
{% endhighlight %}

***

### [TextboxShape.java](https://searchcode.com/codesearch/view/15642364/)
> sets the line compound style @ param style new 9 value of the < code > null < / code > object to the default 
{% highlight java %}
106. HSSFTextbox shape = hssfShape;
125. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTLEFT, shape.getMarginLeft() ) );
126. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTRIGHT, shape.getMarginRight() ) );
127. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTBOTTOM, shape.getMarginBottom() ) );
128. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTTOP, shape.getMarginTop() ) );
130. HSSFAnchor userAnchor = shape.getAnchor();
{% endhighlight %}

***

