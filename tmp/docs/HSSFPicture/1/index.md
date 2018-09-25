# HSSFPicture @Cluster 1 (addescherproperty, blip__bliptodisplay, escherproperties)

***

### [PictureShape.java](https://searchcode.com/codesearch/view/15642357/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param @ since poi 3 . 1 4 - beta 2 
{% highlight java %}
76. HSSFPicture shape = (HSSFPicture) hssfShape;
93. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.BLIP__BLIPTODISPLAY, false, true, shape.getPictureIndex() ) );
97. HSSFAnchor userAnchor = shape.getAnchor();
{% endhighlight %}

***

### [HSSFShapeGroup.java](https://searchcode.com/codesearch/view/15642300/)
> creates a new ( and name " : / / has the " if " row " if the the sheet is already in @ param row the row to creates the @ param value the value to creates @ throws illegalargumentexception if the index is outside of the range ( 0 . . . 
{% highlight java %}
131. HSSFPicture shape = new HSSFPicture(this, anchor);
132. shape.anchor = anchor;
133. shape.setPictureIndex( pictureIndex );
{% endhighlight %}

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
> creates a new ( if to the specified cell ) @ param sheet the sheet to creates the . 
{% highlight java %}
112. HSSFPicture shape = new HSSFPicture(null, anchor);
113. shape.setPictureIndex( pictureIndex );
114. shape.anchor = anchor;
115. shape.patriarch = this;
{% endhighlight %}

***

