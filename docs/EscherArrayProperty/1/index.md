# EscherArrayProperty @Cluster 1 (crec, drawingrecord, lastdrawingrecord)

***

### [PolygonShape.java](https://searchcode.com/codesearch/view/15642360/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param @ param 
{% highlight java %}
100. EscherArrayProperty verticesProp = new EscherArrayProperty(EscherProperties.GEOMETRY__VERTICES, false, new byte[0] );
101. verticesProp.setNumberOfElementsInArray(hssfShape.getXPoints().length+1);
102. verticesProp.setNumberOfElementsInMemory(hssfShape.getXPoints().length+1);
103. verticesProp.setSizeOfElements(0xFFF0);
109.     verticesProp.setElement(i, data);
115. verticesProp.setElement(point, data);
{% endhighlight %}

***

