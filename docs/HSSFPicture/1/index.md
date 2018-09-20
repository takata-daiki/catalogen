# HSSFPicture @Cluster 1

***

### [PictureShape.java](https://searchcode.com/codesearch/view/15642357/)
{% highlight java %}
76. HSSFPicture shape = (HSSFPicture) hssfShape;
93. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.BLIP__BLIPTODISPLAY, false, true, shape.getPictureIndex() ) );
97. HSSFAnchor userAnchor = shape.getAnchor();
{% endhighlight %}

***

### [HSSFShapeGroup.java](https://searchcode.com/codesearch/view/15642300/)
{% highlight java %}
131. HSSFPicture shape = new HSSFPicture(this, anchor);
132. shape.anchor = anchor;
133. shape.setPictureIndex( pictureIndex );
{% endhighlight %}

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
{% highlight java %}
112. HSSFPicture shape = new HSSFPicture(null, anchor);
113. shape.setPictureIndex( pictureIndex );
114. shape.anchor = anchor;
115. shape.patriarch = this;
{% endhighlight %}

***

