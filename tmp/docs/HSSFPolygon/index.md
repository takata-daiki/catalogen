# HSSFPolygon

***

## [Cluster 1 (anchor, foreground, top)](./1)
4 results
> creates a new . @ param anchor the client anchor describes how this group is attached to the sheet . 
{% highlight java %}
130. HSSFPolygon shape = new HSSFPolygon(null, anchor);
131. shape.anchor = anchor;
{% endhighlight %}

***

## [Cluster 2 (getxpoints, hssfshape, length)](./2)
1 results
> create an record from the record list and sets the @ param record the sid to be picture @ param offset type the shape to use 
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

