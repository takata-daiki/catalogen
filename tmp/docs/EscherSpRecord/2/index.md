# EscherSpRecord @Cluster 2

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
153. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
154. spRecord.setShapeType( (short) type );
155. spRecord.setVersion( (short) 0x2 );
{% endhighlight %}

***

### [Picture.java](https://searchcode.com/codesearch/view/97394307/)
{% highlight java %}
131. EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
132. spRecord.setOptions((short)((ShapeTypes.PictureFrame << 4) | 0x2));
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
634. EscherSpRecord sp = new EscherSpRecord();
649. sp.setRecordId( EscherSpRecord.RECORD_ID );
650. sp.setOptions( (short) 0x0002 );
652. sp.setShapeId( shapeId );
654.     sp.setFlags( EscherSpRecord.FLAG_GROUP | EscherSpRecord.FLAG_HAVEANCHOR );
656.     sp.setFlags( EscherSpRecord.FLAG_GROUP | EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_CHILD );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
720. EscherSpRecord sp1 = new EscherSpRecord();
739. sp1.setRecordId( EscherSpRecord.RECORD_ID );
740. sp1.setOptions( (short) 0x0002 );
741. sp1.setShapeId( drawingManager.allocateShapeId(dg.getDrawingGroupId()) );
742. sp1.setFlags( EscherSpRecord.FLAG_GROUP | EscherSpRecord.FLAG_PATRIARCH );
{% endhighlight %}

***

### [PolygonShape.java](https://searchcode.com/codesearch/view/15642360/)
{% highlight java %}
82. EscherSpRecord sp = new EscherSpRecord();
88. sp.setRecordId( EscherSpRecord.RECORD_ID );
89. sp.setOptions( (short) ( ( EscherAggregate.ST_DONUT << 4 ) | 0x2 ) );
90. sp.setShapeId( shapeId );
92.     sp.setFlags( EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
94.     sp.setFlags( EscherSpRecord.FLAG_CHILD | EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
{% endhighlight %}

***

### [PictureShape.java](https://searchcode.com/codesearch/view/15642357/)
{% highlight java %}
79. EscherSpRecord sp = new EscherSpRecord();
86. sp.setRecordId( EscherSpRecord.RECORD_ID );
87. sp.setOptions( (short) ( (EscherAggregate.ST_PICTUREFRAME << 4) | 0x2 ) );
89. sp.setShapeId( shapeId );
90. sp.setFlags( EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
99.     sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_FLIPHORIZ);
101.     sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_FLIPVERT);
{% endhighlight %}

***

### [LineShape.java](https://searchcode.com/codesearch/view/15642361/)
{% highlight java %}
80. EscherSpRecord sp = new EscherSpRecord();
87. sp.setRecordId( EscherSpRecord.RECORD_ID );
88. sp.setOptions( (short) ( (EscherAggregate.ST_LINE << 4) | 0x2 ) );
90. sp.setShapeId( shapeId );
91. sp.setFlags( EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
98.     sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_FLIPHORIZ);
100.     sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_FLIPVERT);
{% endhighlight %}

***

### [TextboxShape.java](https://searchcode.com/codesearch/view/15642364/)
{% highlight java %}
109. EscherSpRecord sp = new EscherSpRecord();
117. sp.setRecordId( EscherSpRecord.RECORD_ID );
118. sp.setOptions( (short) ( ( EscherAggregate.ST_TEXTBOX << 4 ) | 0x2 ) );
120. sp.setShapeId( shapeId );
121. sp.setFlags( EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
{% endhighlight %}

***

### [SimpleFilledShape.java](https://searchcode.com/codesearch/view/15642355/)
{% highlight java %}
80. EscherSpRecord sp = new EscherSpRecord();
86. sp.setRecordId( EscherSpRecord.RECORD_ID );
88. sp.setOptions( (short) ( ( shapeType << 4 ) | 0x2 ) );
89. sp.setShapeId( shapeId );
90. sp.setFlags( EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE );
{% endhighlight %}

***

