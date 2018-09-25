# EscherContainerRecord @Cluster 2 (bstore, dggcontainer, spcontainer1)

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> create and create a new sheet from the sheet and save the @ param was the < code > true < / code > if the line is inside a group , < code > false < / code > otherwise 
{% highlight java %}
631. EscherContainerRecord spgrContainer = new EscherContainerRecord();
639. spgrContainer.setRecordId( EscherContainerRecord.SPGR_CONTAINER );
640. spgrContainer.setOptions( (short) 0x000F );
674. spgrContainer.addChildRecord( spContainer );
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
> create and create a new sheet from the data , or create if it isn ' t has a valid record . 
{% highlight java %}
2191. EscherContainerRecord bstoreContainer = null;
2195.     bstoreContainer.setRecordId( EscherContainerRecord.BSTORE_CONTAINER );
2196.     bstoreContainer.setOptions( (short) ( (escherBSERecords.size() << 4) | 0xF ) );
2200.         bstoreContainer.addChildRecord( escherRecord );
{% endhighlight %}

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
> create the escher records associated with this sheet . 
{% highlight java %}
800. EscherContainerRecord bstore;
807.   bstore.setRecordId(EscherContainerRecord.BSTORE_CONTAINER);
811.   Iterator<EscherRecord> iter = bstore.getChildIterator();
845. bstore.addChildRecord(bse);
846. int count = bstore.getChildRecords().size();
847. bstore.setOptions((short) ((count << 4) | 0xF));
{% endhighlight %}

***

### [PolygonShape.java](https://searchcode.com/codesearch/view/15642360/)
> test that we get the same value as excel and , for 
{% highlight java %}
58. private EscherContainerRecord spContainer;
86.     spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
87.     spContainer.setOptions( (short) 0x000F );
142.     spContainer.addChildRecord( sp );
143.     spContainer.addChildRecord( opt );
144.     spContainer.addChildRecord( anchor );
145.     spContainer.addChildRecord( clientData );
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
2177. EscherContainerRecord dggContainer = new EscherContainerRecord();
2182. dggContainer.setRecordId((short) 0xF000);
2183. dggContainer.setOptions((short) 0x000F);
2215. dggContainer.addChildRecord(dgg);
2217.     dggContainer.addChildRecord( bstoreContainer );
2218. dggContainer.addChildRecord(opt);
2219. dggContainer.addChildRecord(splitMenuColors);
{% endhighlight %}

***

### [LineShape.java](https://searchcode.com/codesearch/view/15642361/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
79. EscherContainerRecord spContainer = new EscherContainerRecord();
85. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
86. spContainer.setOptions( (short) 0x000F );
105. spContainer.addChildRecord(sp);
106. spContainer.addChildRecord(opt);
107. spContainer.addChildRecord(anchor);
108. spContainer.addChildRecord(clientData);
{% endhighlight %}

***

### [PictureShape.java](https://searchcode.com/codesearch/view/15642357/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
78. EscherContainerRecord spContainer = new EscherContainerRecord();
84. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
85. spContainer.setOptions( (short) 0x000F );
106. spContainer.addChildRecord(sp);
107. spContainer.addChildRecord(opt);
108. spContainer.addChildRecord(anchor);
109. spContainer.addChildRecord(clientData);
{% endhighlight %}

***

### [LineShape.java](https://searchcode.com/codesearch/view/15642361/)
> test that we get the same value as excel and , for 
{% highlight java %}
56. private EscherContainerRecord spContainer;
85.     spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
86.     spContainer.setOptions( (short) 0x000F );
105.     spContainer.addChildRecord(sp);
106.     spContainer.addChildRecord(opt);
107.     spContainer.addChildRecord(anchor);
108.     spContainer.addChildRecord(clientData);
{% endhighlight %}

***

### [PictureShape.java](https://searchcode.com/codesearch/view/15642357/)
> test that we get the same value as excel and , for 
{% highlight java %}
55. private EscherContainerRecord spContainer;
84.     spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
85.     spContainer.setOptions( (short) 0x000F );
106.     spContainer.addChildRecord(sp);
107.     spContainer.addChildRecord(opt);
108.     spContainer.addChildRecord(anchor);
109.     spContainer.addChildRecord(clientData);
{% endhighlight %}

***

### [SimpleFilledShape.java](https://searchcode.com/codesearch/view/15642355/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
54. private EscherContainerRecord spContainer;
84.     spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
85.     spContainer.setOptions( (short) 0x000F );
97.     spContainer.addChildRecord( sp );
98.     spContainer.addChildRecord( opt );
99.     spContainer.addChildRecord( anchor );
100.     spContainer.addChildRecord( clientData );
{% endhighlight %}

***

### [PolygonShape.java](https://searchcode.com/codesearch/view/15642360/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
81. EscherContainerRecord spContainer = new EscherContainerRecord();
86. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
87. spContainer.setOptions( (short) 0x000F );
142. spContainer.addChildRecord( sp );
143. spContainer.addChildRecord( opt );
144. spContainer.addChildRecord( anchor );
145. spContainer.addChildRecord( clientData );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> create and create a new 3 and sheet . 
{% highlight java %}
632. EscherContainerRecord spContainer = new EscherContainerRecord();
641. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
642. spContainer.setOptions( (short) 0x000F );
675. spContainer.addChildRecord( spgr );
676. spContainer.addChildRecord( sp );
677. spContainer.addChildRecord( opt );
678. spContainer.addChildRecord( anchor );
679. spContainer.addChildRecord( clientData );
{% endhighlight %}

***

