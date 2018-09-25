# EscherContainerRecord @Cluster 3 (addchildrecord, dg, spcontainer)

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
802. EscherContainerRecord dggContainer = _documentRecord.getPPDrawingGroup().getDggContainer();
809.   dggContainer.addChildBefore(bstore, EscherOptRecord.RECORD_ID);
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
> test that we get the same value as excel and , for 
{% highlight java %}
91. protected EscherContainerRecord _escherContainer;
144.     EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
{% endhighlight %}

***

### [Picture.java](https://searchcode.com/codesearch/view/97394307/)
> create and create a new stream @ param out the stream to be 
{% highlight java %}
200. EscherContainerRecord bstore = (EscherContainerRecord)Shape.getEscherChild(dggContainer, EscherContainerRecord.BSTORE_CONTAINER);
205. List lst = bstore.getChildRecords();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
215. EscherContainerRecord spgr = null;
229. Iterator<EscherRecord> it = spgr.getChildIterator();
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
591. private void convertShapes( HSSFShapeContainer parent, EscherContainerRecord escherParent, Map shapeToObj )
621.             escherParent.addChildRecord( shapeModel.getSpContainer() );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
629. private void convertGroup( HSSFShapeGroup shape, EscherContainerRecord escherParent, Map shapeToObj )
696.     escherParent.addChildRecord( spgrContainer );
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
253. EscherContainerRecord spgr = (EscherContainerRecord) Shape.getEscherChild(dgContainer, EscherContainerRecord.SPGR_CONTAINER);
254. spgr.addChildRecord(shape.getSpContainer());
{% endhighlight %}

***

### [DefaultEscherRecordFactory.java](https://searchcode.com/codesearch/view/97383906/)
> create the batblocks 
{% highlight java %}
69. EscherContainerRecord r = new EscherContainerRecord();
70. r.setRecordId( recordId );
71. r.setOptions( options );
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
310. EscherContainerRecord spgr = null;
323. List<EscherRecord> lst = spgr.getChildRecords();
325. spgr.setChildRecords(lst);
{% endhighlight %}

***

