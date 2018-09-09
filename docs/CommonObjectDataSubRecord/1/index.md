# CommonObjectDataSubRecord @Cluster 1

***

### [SimpleFilledShape.java](https://searchcode.com/codesearch/view/15642355/)
{% highlight java %}
125. CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
126. c.setObjectType( (short) ( (HSSFSimpleShape) shape ).getShapeType() );
127. c.setObjectId( (short) ( shapeId ) );
128. c.setLocked( true );
129. c.setPrintable( true );
130. c.setAutofill( true );
131. c.setAutoline( true );
{% endhighlight %}

***

### [PolygonShape.java](https://searchcode.com/codesearch/view/15642360/)
{% highlight java %}
158. CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
159. c.setObjectType( OBJECT_TYPE_MICROSOFT_OFFICE_DRAWING );
160. c.setObjectId( (short) ( shapeId ) );
161. c.setLocked( true );
162. c.setPrintable( true );
163. c.setAutofill( true );
164. c.setAutoline( true );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
682. CommonObjectDataSubRecord cmo = new CommonObjectDataSubRecord();
683. cmo.setObjectType( CommonObjectDataSubRecord.OBJECT_TYPE_GROUP );
684. cmo.setObjectId( (short) ( shapeId ) );
685. cmo.setLocked( true );
686. cmo.setPrintable( true );
687. cmo.setAutofill( true );
688. cmo.setAutoline( true );
{% endhighlight %}

***

### [TextboxShape.java](https://searchcode.com/codesearch/view/15642364/)
{% highlight java %}
83. CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
84. c.setObjectType( (short) ( (HSSFSimpleShape) shape ).getShapeType() );
85. c.setObjectId( (short) ( shapeId ) );
86. c.setLocked( true );
87. c.setPrintable( true );
88. c.setAutofill( true );
89. c.setAutoline( true );
{% endhighlight %}

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

### [LineShape.java](https://searchcode.com/codesearch/view/15642361/)
{% highlight java %}
121. CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
122. c.setObjectType((short) ((HSSFSimpleShape)shape).getShapeType());
123. c.setObjectId((short) ( shapeId ));
124. c.setLocked(true);
125. c.setPrintable(true);
126. c.setAutofill(true);
127. c.setAutoline(true);
{% endhighlight %}

***

