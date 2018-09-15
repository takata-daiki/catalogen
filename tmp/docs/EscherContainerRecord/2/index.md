# EscherContainerRecord @Cluster 2

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
91. protected EscherContainerRecord _escherContainer;
144.     EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
261. public static EscherRecord getEscherChild(EscherContainerRecord owner, int recordId){
262.     for ( Iterator<EscherRecord> iterator = owner.getChildIterator(); iterator.hasNext(); )
{% endhighlight %}

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
{% highlight java %}
802. EscherContainerRecord dggContainer = _documentRecord.getPPDrawingGroup().getDggContainer();
809.   dggContainer.addChildBefore(bstore, EscherOptRecord.RECORD_ID);
{% endhighlight %}

***

### [EscherContainerRecord.java](https://searchcode.com/codesearch/view/97383916/)
{% highlight java %}
326. EscherContainerRecord c = (EscherContainerRecord)r;
327. c.getRecordsById(recordId, out );
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
215. EscherContainerRecord spgr = null;
229. Iterator<EscherRecord> it = spgr.getChildIterator();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
253. EscherContainerRecord spgr = (EscherContainerRecord) Shape.getEscherChild(dgContainer, EscherContainerRecord.SPGR_CONTAINER);
254. spgr.addChildRecord(shape.getSpContainer());
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
310. EscherContainerRecord spgr = null;
323. List<EscherRecord> lst = spgr.getChildRecords();
325. spgr.setChildRecords(lst);
{% endhighlight %}

***

### [Picture.java](https://searchcode.com/codesearch/view/97394307/)
{% highlight java %}
200. EscherContainerRecord bstore = (EscherContainerRecord)Shape.getEscherChild(dggContainer, EscherContainerRecord.BSTORE_CONTAINER);
205. List lst = bstore.getChildRecords();
{% endhighlight %}

***

### [DefaultEscherRecordFactory.java](https://searchcode.com/codesearch/view/97383906/)
{% highlight java %}
69. EscherContainerRecord r = new EscherContainerRecord();
70. r.setRecordId( recordId );
71. r.setOptions( options );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
591. private void convertShapes( HSSFShapeContainer parent, EscherContainerRecord escherParent, Map shapeToObj )
621.             escherParent.addChildRecord( shapeModel.getSpContainer() );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
629. private void convertGroup( HSSFShapeGroup shape, EscherContainerRecord escherParent, Map shapeToObj )
696.     escherParent.addChildRecord( spgrContainer );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
702. private EscherRecord findClientData( EscherContainerRecord spContainer )
704.     for ( Iterator iterator = spContainer.getChildRecords().iterator(); iterator.hasNext(); )
{% endhighlight %}

***

