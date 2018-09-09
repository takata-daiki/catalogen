# EscherContainerRecord

***

### [Cluster 1](./1)
{% highlight java %}
91. protected EscherContainerRecord _escherContainer;
144.     EscherSpRecord spRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
153. EscherContainerRecord dgContainer = (EscherContainerRecord)getSheetContainer().getPPDrawing().getEscherRecords()[0];
160. for (EscherContainerRecord c : dgContainer.getChildContainers()) {
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
579. EscherContainerRecord dgContainer = (EscherContainerRecord) getEscherRecord( 0 );
581. for ( int i = 0; i < dgContainer.getChildRecords().size(); i++ )
582.     if ( dgContainer.getChild( i ).getRecordId() == EscherContainerRecord.SPGR_CONTAINER )
583.         spgrContainer = (EscherContainerRecord) dgContainer.getChild( i );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
2260. EscherContainerRecord dggContainer = (EscherContainerRecord) drawingGroup.getEscherRecord( 0 );
2262. if (dggContainer.getChild( 1 ).getRecordId() == EscherContainerRecord.BSTORE_CONTAINER )
2264.     bstoreContainer = (EscherContainerRecord) dggContainer.getChild( 1 );
2270.     dggContainer.getChildRecords().add( 1, bstoreContainer );
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
261. public static EscherRecord getEscherChild(EscherContainerRecord owner, int recordId){
262.     for ( Iterator<EscherRecord> iterator = owner.getChildIterator(); iterator.hasNext(); )
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
326. EscherContainerRecord c = (EscherContainerRecord)r;
327. c.getRecordsById(recordId, out );
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
2191. EscherContainerRecord bstoreContainer = null;
2195.     bstoreContainer.setRecordId( EscherContainerRecord.BSTORE_CONTAINER );
2196.     bstoreContainer.setOptions( (short) ( (escherBSERecords.size() << 4) | 0xF ) );
2200.         bstoreContainer.addChildRecord( escherRecord );
{% endhighlight %}

***

### [Cluster 8](./8)
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

### [Cluster 9](./9)
{% highlight java %}
79. EscherContainerRecord spContainer = new EscherContainerRecord();
84. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
85. spContainer.setOptions( (short) 0x000F );
97. spContainer.addChildRecord( sp );
98. spContainer.addChildRecord( opt );
99. spContainer.addChildRecord( anchor );
100. spContainer.addChildRecord( clientData );
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
69. EscherContainerRecord r = new EscherContainerRecord();
70. r.setRecordId( recordId );
71. r.setOptions( options );
{% endhighlight %}

***

### [Cluster 11](./11)
{% highlight java %}
215. EscherContainerRecord spgr = null;
229. Iterator<EscherRecord> it = spgr.getChildIterator();
{% endhighlight %}

***

### [Cluster 12](./12)
{% highlight java %}
200. EscherContainerRecord bstore = (EscherContainerRecord)Shape.getEscherChild(dggContainer, EscherContainerRecord.BSTORE_CONTAINER);
205. List lst = bstore.getChildRecords();
{% endhighlight %}

***

### [Cluster 13](./13)
{% highlight java %}
591. private void convertShapes( HSSFShapeContainer parent, EscherContainerRecord escherParent, Map shapeToObj )
621.             escherParent.addChildRecord( shapeModel.getSpContainer() );
{% endhighlight %}

***

### [Cluster 14](./14)
{% highlight java %}
702. private EscherRecord findClientData( EscherContainerRecord spContainer )
704.     for ( Iterator iterator = spContainer.getChildRecords().iterator(); iterator.hasNext(); )
{% endhighlight %}

***

