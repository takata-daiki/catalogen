# HSSFTextbox @Cluster 1

***

### [TextboxShape.java](https://searchcode.com/codesearch/view/15642364/)
{% highlight java %}
106. HSSFTextbox shape = hssfShape;
125. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTLEFT, shape.getMarginLeft() ) );
126. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTRIGHT, shape.getMarginRight() ) );
127. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTBOTTOM, shape.getMarginBottom() ) );
128. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTTOP, shape.getMarginTop() ) );
130. HSSFAnchor userAnchor = shape.getAnchor();
{% endhighlight %}

***

### [HSSFShapeGroup.java](https://searchcode.com/codesearch/view/15642300/)
{% highlight java %}
101. HSSFTextbox shape = new HSSFTextbox(this, anchor);
102. shape.anchor = anchor;
{% endhighlight %}

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
{% highlight java %}
145. HSSFTextbox shape = new HSSFTextbox(null, anchor);
146. shape.anchor = anchor;
{% endhighlight %}

***

### [EscherGraphics.java](https://searchcode.com/codesearch/view/15642323/)
{% highlight java %}
311. HSSFTextbox textbox = escherGroup.createTextbox( new HSSFChildAnchor( x, y, x + width, y + height ) );
312. textbox.setNoFill( true );
313. textbox.setLineStyle( HSSFShape.LINESTYLE_NONE );
317. textbox.setString( s );
{% endhighlight %}

***

