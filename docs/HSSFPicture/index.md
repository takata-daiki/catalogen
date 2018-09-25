# HSSFPicture

***

## [Cluster 1 (addescherproperty, blip__bliptodisplay, escherproperties)](./1)
3 results
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param @ since poi 3 . 1 4 - beta 2 
{% highlight java %}
76. HSSFPicture shape = (HSSFPicture) hssfShape;
93. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.BLIP__BLIPTODISPLAY, false, true, shape.getPictureIndex() ) );
97. HSSFAnchor userAnchor = shape.getAnchor();
{% endhighlight %}

***

## [Cluster 2 (getpictureindex, hssfpicture, pic)](./2)
1 results
> creates an empty return the 
{% highlight java %}
237. HSSFPicture pic = (HSSFPicture) shape;  
238. int pictureIndex = pic.getPictureIndex() - 1;  
{% endhighlight %}

***

