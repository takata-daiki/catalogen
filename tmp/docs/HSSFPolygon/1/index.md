# HSSFPolygon @Cluster 1

***

### [PolygonShape.java](https://searchcode.com/codesearch/view/15642360/)
{% highlight java %}
77. private EscherContainerRecord createSpContainer( HSSFPolygon hssfShape, int shapeId )
91.     if (hssfShape.getParent() == null)
97.     opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__RIGHT, false, false, hssfShape.getDrawAreaWidth()));
98.     opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__BOTTOM, false, false, hssfShape.getDrawAreaHeight()));
101.     verticesProp.setNumberOfElementsInArray(hssfShape.getXPoints().length+1);
102.     verticesProp.setNumberOfElementsInMemory(hssfShape.getXPoints().length+1);
104.     for (int i = 0; i < hssfShape.getXPoints().length; i++)
107.         LittleEndian.putShort(data, 0, (short)hssfShape.getXPoints()[i]);
108.         LittleEndian.putShort(data, 2, (short)hssfShape.getYPoints()[i]);
111.     int point = hssfShape.getXPoints().length;
113.     LittleEndian.putShort(data, 0, (short)hssfShape.getXPoints()[0]);
114.     LittleEndian.putShort(data, 2, (short)hssfShape.getYPoints()[0]);
119.     segmentsProp.setNumberOfElementsInArray(hssfShape.getXPoints().length * 2 + 4);
120.     segmentsProp.setNumberOfElementsInMemory(hssfShape.getXPoints().length * 2 + 4);
{% endhighlight %}

***

