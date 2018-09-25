# EscherOptRecord @Cluster 1 (eschershapepathproperty, eschersimpleproperty, false)

***

### [SimpleShape.java](https://searchcode.com/codesearch/view/97394265/)
> the example from the a { @ link 
{% highlight java %}
89. EscherOptRecord opt = new EscherOptRecord();
90. opt.setRecordId(EscherOptRecord.RECORD_ID);
{% endhighlight %}

***

### [LineShape.java](https://searchcode.com/codesearch/view/15642361/)
> creates the . @ param set the stream to write to 
{% highlight java %}
81. EscherOptRecord opt = new EscherOptRecord();
92. opt.setRecordId( EscherOptRecord.RECORD_ID );
93. opt.addEscherProperty( new EscherShapePathProperty( EscherProperties.GEOMETRY__SHAPEPATH, EscherShapePathProperty.COMPLEX ) );
94. opt.addEscherProperty( new EscherBoolProperty( EscherProperties.LINESTYLE__NOLINEDRAWDASH, 1048592 ) );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> creates the picture data . 
{% highlight java %}
635. EscherOptRecord opt = new EscherOptRecord();
657. opt.setRecordId( EscherOptRecord.RECORD_ID );
658. opt.setOptions( (short) 0x0023 );
659. opt.addEscherProperty( new EscherBoolProperty( EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 0x00040004 ) );
660. opt.addEscherProperty( new EscherBoolProperty( EscherProperties.GROUPSHAPE__PRINT, 0x00080000 ) );
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
> creates the . @ param null 
{% highlight java %}
2179. EscherOptRecord opt = new EscherOptRecord();
2203. opt.setRecordId((short) 0xF00B);
2204. opt.setOptions((short) 0x0033);
2205. opt.addEscherProperty( new EscherBoolProperty(EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 524296) );
2206. opt.addEscherProperty( new EscherRGBProperty(EscherProperties.FILL__FILLCOLOR, 0x08000041) );
2207. opt.addEscherProperty( new EscherRGBProperty(EscherProperties.LINESTYLE__COLOR, 134217792) );
{% endhighlight %}

***

### [TextboxShape.java](https://searchcode.com/codesearch/view/15642364/)
> test that we get the same value as excel and , for 
{% highlight java %}
110. EscherOptRecord opt = new EscherOptRecord();
122. opt.setRecordId( EscherOptRecord.RECORD_ID );
124. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTID, 0 ) );
125. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTLEFT, shape.getMarginLeft() ) );
126. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTRIGHT, shape.getMarginRight() ) );
127. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTBOTTOM, shape.getMarginBottom() ) );
128. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.TEXT__TEXTTOP, shape.getMarginTop() ) );
{% endhighlight %}

***

### [PolygonShape.java](https://searchcode.com/codesearch/view/15642360/)
> creates the . if this sheet already exists , it is removed record 
{% highlight java %}
83. EscherOptRecord opt = new EscherOptRecord();
95. opt.setRecordId( EscherOptRecord.RECORD_ID );
96. opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.TRANSFORM__ROTATION, false, false, 0));
97. opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__RIGHT, false, false, hssfShape.getDrawAreaWidth()));
98. opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__BOTTOM, false, false, hssfShape.getDrawAreaHeight()));
99. opt.addEscherProperty(new EscherShapePathProperty(EscherProperties.GEOMETRY__SHAPEPATH, EscherShapePathProperty.COMPLEX));
116. opt.addEscherProperty(verticesProp);
130. opt.addEscherProperty(segmentsProp);
131. opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__FILLOK, false, false, 0x00010001));
132. opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINESTARTARROWHEAD, false, false, 0x0));
133. opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINEENDARROWHEAD, false, false, 0x0));
134. opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINEENDCAPSTYLE, false, false, 0x0));
{% endhighlight %}

***

