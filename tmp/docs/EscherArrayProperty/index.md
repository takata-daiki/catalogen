# EscherArrayProperty

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
100. EscherArrayProperty verticesProp = new EscherArrayProperty(EscherProperties.GEOMETRY__VERTICES, false, new byte[0] );
101. verticesProp.setNumberOfElementsInArray(hssfShape.getXPoints().length+1);
102. verticesProp.setNumberOfElementsInMemory(hssfShape.getXPoints().length+1);
103. verticesProp.setSizeOfElements(0xFFF0);
109.     verticesProp.setElement(i, data);
115. verticesProp.setElement(point, data);
{% endhighlight %}

***

