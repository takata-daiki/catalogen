# CommonObjectDataSubRecord @Cluster 3

***

### [PictureShape.java](https://searchcode.com/codesearch/view/15642357/)
{% highlight java %}
122. CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
123. c.setObjectType((short) ((HSSFSimpleShape)shape).getShapeType());
125. c.setObjectId((short) ( shapeId ));
126. c.setLocked(true);
127. c.setPrintable(true);
128. c.setAutofill(true);
129. c.setAutoline(true);
131. c.setReserved2( 0x0 );
{% endhighlight %}

***

