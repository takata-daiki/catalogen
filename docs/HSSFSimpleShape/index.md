# HSSFSimpleShape

***

### [Cluster 1](./1)
{% highlight java %}
88. HSSFSimpleShape shape = new HSSFSimpleShape(this, anchor);
89. shape.anchor = anchor;
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
76. HSSFSimpleShape simpleShape = (HSSFSimpleShape) hssfShape;
77. switch ( simpleShape.getShapeType() )
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
75. private EscherContainerRecord createSpContainer( HSSFSimpleShape hssfShape, int shapeId )
87.     short shapeType = objTypeToShapeType( hssfShape.getShapeType() );
{% endhighlight %}

***

